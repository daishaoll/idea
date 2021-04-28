package com.dsf.comicspider.spider;

import com.dsf.comicspider.pojo.ComicNumber;
import com.dsf.comicspider.service.ComicNumberService;
import net.sf.cglib.proxy.Enhancer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.BloomFilterDuplicateRemover;
import us.codecraft.webmagic.scheduler.QueueScheduler;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * @author 戴少峰
 * @version 1.0
 * @className SpiderTask
 * @date 2021/4/19-14:55
 */
@Component
public class SpiderTask {
    @Autowired
    private Comic comic;
    @Autowired
    private ComicNumberService comicNumberService;
    @Scheduled(initialDelay = 1000,fixedDelay = 500 * 1000)
    public void startSpider() {
//        Enhancer enhancer = new Enhancer();
//        enhancer.setSuperclass(Spider.class);
//        enhancer.setCallback(new SpiderInterceptor());
//        Spider spider = (Spider)enhancer.create(new Class[]{PageProcessor.class}, new Object[]{comic});
        Spider spider = new Spider(comic);
        Logger logger = LoggerFactory.getLogger(getClass());
        logger.error("hello");
        spider.addUrl("https://manhua.fzdm.com/")
                .setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(1000000)))
                .thread(10)
                .run();
        Queue<ComicNumber> comicNumberQueue = Comic.getComicNumberQueue();
        List<ComicNumber> comicNumberList = new ArrayList<>(comicNumberQueue);
        comicNumberService.save(comicNumberList);
        long begin = System.currentTimeMillis();
        System.out.println("完毕");
        System.out.println(System.currentTimeMillis() - begin);


    }
}
