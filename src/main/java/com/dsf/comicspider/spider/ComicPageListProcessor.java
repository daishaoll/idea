package com.dsf.comicspider.spider;

import com.dsf.comicspider.pojo.ComicInfo;
import com.dsf.comicspider.pojo.ComicPage;
import com.dsf.comicspider.service.ComicInfoService;
import com.dsf.comicspider.service.ComicPageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.BloomFilterDuplicateRemover;
import us.codecraft.webmagic.scheduler.QueueScheduler;
import us.codecraft.webmagic.selector.Html;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 戴少峰
 * @version 1.0
 * @className ComicPageListProcessor
 * @date 2021/4/29-14:16
 */
@Component
public class ComicPageListProcessor implements PageProcessor {

    @Autowired
    private ComicInfoService comicInfoService;
    @Autowired
    private ComicPageService comicPageService;
    @Autowired
    private ComicPageListProcessor comicPageListProcessor;
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void process(Page page) {
        Html html = page.getHtml();
        String url = page.getUrl().toString();
        addComicPage(page, html, url);
    }

    /**
     * 添加漫画的话数
     *
     * @param page 框架的页面类{@link Page}
     * @param html 爬取的源页面
     * @return void
     * @author 戴少峰
     * @date 2021/4/15
     */
    private void addComicPage(Page page, Html html, String url) {
        List<String> comicPageLinkList = html.css(".pure-u-1-2.pure-u-lg-1-4").links().all();
        List<String> comicPageNameList = html.css(".pure-u-1-2.pure-u-lg-1-4>a", "text").all();
        Integer pid = page.getRequest().getExtra("pid");
        ArrayList<ComicPage> comicPageList = new ArrayList<>();
        for(int i = 0; i < comicPageLinkList.size(); i++) {
            ComicPage comicPage = new ComicPage();
            comicPage.setPid(pid);
            comicPage.setUrl(comicPageLinkList.get(i));
            comicPage.setNumber(comicPageNameList.get(i));
            comicPage.setUpdateTime(new Date());
            comicPageList.add(comicPage);
            logger.debug("爬出到的漫画话数列表为：{}", comicPage.getNumber());
        }

        comicPageService.save(comicPageList);
    }

    @Override
    public Site getSite() {
        return Site.me()
                // 设置链接超时时间
                .setTimeOut(5000)
                // 设置重连间隔
                .setRetrySleepTime(1000 * 3)
                // 设置重连次数
                .setRetryTimes(3)
                // 设置解析页面的编码格式
                .setCharset("utf-8")
                // 添加请求头
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36");
    }

    public void comicPageListSpider() {
        List<ComicInfo> comicInfoList = comicInfoService.findAll();

        Spider spider = new Spider(comicPageListProcessor);
        AtomicInteger count = new AtomicInteger();
        comicInfoList.forEach((comicInfo) -> {
            if (comicInfo.getStatus() == 0) {
                spider.addRequest(new Request(comicInfo.getUrl()).putExtra("pid", comicInfo.getId()));
            }
        });

        spider.setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(1000000)))
                .thread(10)
                .run();
        System.out.println("完毕");
    }
}
