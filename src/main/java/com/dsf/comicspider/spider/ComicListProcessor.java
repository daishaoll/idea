package com.dsf.comicspider.spider;

import com.dsf.comicspider.pojo.ComicInfo;
import com.dsf.comicspider.pojo.ComicNumber;
import com.dsf.comicspider.service.ComicInfoService;
import com.dsf.comicspider.service.ComicNumberService;
import com.dsf.comicspider.service.ComicPageService;
import com.dsf.comicspider.utils.StringUtils;
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

/**
 * @author 戴少峰
 * @version 1.0
 * @className ComicListPageProcessor
 * @date 2021/4/29-14:15
 */
@Component
public class ComicListProcessor implements PageProcessor {

    @Autowired
    private ComicInfoService comicInfoService;
    @Autowired
    private ComicListProcessor comicListProcessor;
    private Logger logger = LoggerFactory.getLogger(getClass());


    @Override
    public void process(Page page) {
        Html html = page.getHtml();
        String url = page.getUrl().toString();
        addComicList(page, html, url);

    }

    /**
     * 添加网站漫画名的列表
     *
     * @param page 框架的类面类{@link Page}
     * @param html 爬取到源码页面
     * @return void
     * @author 戴少峰
     * @date 2021/4/15
     */

    private void addComicList(Page page, Html html, String url) {
        if (StringUtils.pattern(".com/$", url)) {
            List<String> comicLinkList = html.css("div.round>li:first-of-type a").links().all();
            List<String> comicNameList = html.css("div.round>li:last-of-type a", "text").all();
            List<ComicInfo> comicInfoList = new ArrayList<>();

            for (int i = 0; i < comicLinkList.size(); i++) {
                ComicInfo comicInfo = new ComicInfo();
                comicInfo.setName(comicNameList.get(i));
                comicInfo.setUrl(comicLinkList.get(i));
                comicInfo.setUpdateTime(new Date());
                comicInfoList.add(comicInfo);
            }
            logger.debug("爬取到的漫画列表为:", comicInfoList);
            comicInfoService.save(comicInfoList);
        }
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
    public void comicListSpider() {
        Spider spider = new Spider(comicListProcessor)
                .addUrl("https://manhua.fzdm.com/")
                .setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(1000000)))
                .thread(10);
        spider.run();
    }
}
