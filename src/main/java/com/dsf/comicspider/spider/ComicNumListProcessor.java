package com.dsf.comicspider.spider;

import com.dsf.comicspider.pojo.ComicNumber;
import com.dsf.comicspider.pojo.ComicPage;
import com.dsf.comicspider.service.ComicInfoService;
import com.dsf.comicspider.service.ComicNumberService;
import com.dsf.comicspider.service.ComicPageService;
import com.dsf.comicspider.utils.StringUtils;

import com.google.common.collect.Queues;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.weaver.ast.Var;
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
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

/**
 * @author 戴少峰
 * @version 1.0
 * @className ComicNumListProcessor
 * @date 2021/4/29-14:17
 */
@Component
@Aspect
public class ComicNumListProcessor implements PageProcessor{

    @Autowired
    private ComicInfoService comicInfoService;
    @Autowired
    private ComicPageService comicPageService;
    @Autowired
    private ComicNumberService comicNumberService;
    @Autowired
   private ComicNumListProcessor comicNumListProcessor;
    private Queue<ComicNumber> comicNumberQueue = new LinkedList<>();
    private Logger logger = LoggerFactory.getLogger(getClass());
    private ReentrantLock lock = new ReentrantLock();
    private HashSet<Integer> pidSet = new HashSet<>();
    @Override
    public void process(Page page) {
        Html html = page.getHtml();
        String url = page.getUrl().toString();
        addComicNumber(page, html, url);

    }

    /**
     * 添加漫画的页码
     *
     * @param page 框架的页面类{@link Page}
     * @param html 爬取到源码页面
     * @param url  页码列表所在的页面
     * @return void
     * @author 戴少峰
     * @date 2021/4/15
     */

    private void addComicNumber(Page page, Html html, String url) {
        String nextComicNumberLink = html.css("a.button-success + a").links().toString();
        String currentComicNumberLink = html.css("a.button-success").links().toString();
        String comicNumber = StringUtils.find("_(\\d*).html", currentComicNumberLink);
        String comicFullName = html.css("h1").regex(">(\\S*)\\s*<").toString();
        if (nextComicNumberLink != null && !nextComicNumberLink.isEmpty()) {
            Integer pid = page.getRequest().getExtra("pid");
            page.addTargetRequest(new Request(nextComicNumberLink).putExtra("pid", pid));
            String comicNameAndPage = comicFullName.contains("(") ? StringUtils.find("(.*)\\(", comicFullName) : comicFullName;
            String comicName = StringUtils.find("(\\D*)", comicNameAndPage);
            String comicPage = StringUtils.find("\\D*(.*)", comicNameAndPage);
            String comicImgSuffix = html.css("script").regex("mhurl=\"(.*\\.jpg)").toString();
            String comicImgLink = "http://www-mipengine-org.mipcdn.com/i/p3.manhuapan.com/" + comicImgSuffix + "?name=" + comicName + "&page=" + comicPage + "&number=" + comicNumber;
            logger.debug("图片地址为：{}", comicImgLink);
            saveComicNumber(comicImgLink, Integer.valueOf(comicNumber), pid);
        }


    }


    public void saveComicNumber(String imgUrl, Integer number, Integer pid) {
        ComicNumber comicNumber = new ComicNumber();
        comicNumber.setPid(pid);
        comicNumber.setUrl(imgUrl);
        comicNumber.setNumber(number);
        this.pidSet.add(pid);
        synchronized(this) {
            comicNumberQueue.offer(comicNumber);
            if (comicNumberQueue.size() == 10) {
                comicNumberService.save(new ArrayList<>(comicNumberQueue));
                comicNumberQueue.clear();
            }
            logger.debug("漫画图片列表：{}", comicNumber);
        }
    }
    @Pointcut("execution(* com.dsf.comicspider.spider.ComicNumListProcessor.comicNumListSpider())")
    public void pointCut() {}

    @After("pointCut()")
    public void savecomicNumberQueue() {
        System.out.println("hello");

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

    public void comicNumListSpider() {
        List<ComicPage> comicPageList = comicPageService.findAll();
        Spider spider = new Spider(comicNumListProcessor);
        AtomicInteger count = new AtomicInteger();
        comicPageList.forEach((comicPage) -> {
            if (comicPage.getStatus() == 0 && count.getAndIncrement() < 10) {
                spider.addRequest(new Request(comicPage.getUrl()).putExtra("pid", comicPage.getId()));
            }
        });

        spider.setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(1000000)))
                .thread(10)
                .run();
        comicNumberService.save(new ArrayList<>(this.comicNumberQueue));
        this.pidSet.forEach((id) -> comicPageService.updateByStatus(id, 1));
        System.out.println("完毕");
    }
}
