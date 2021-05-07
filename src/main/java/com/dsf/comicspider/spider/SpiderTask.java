package com.dsf.comicspider.spider;

import com.dsf.comicspider.pojo.ComicInfo;
import com.dsf.comicspider.pojo.ComicNumber;
import com.dsf.comicspider.pojo.ComicPage;
import com.dsf.comicspider.service.ComicInfoService;
import com.dsf.comicspider.service.ComicNumberService;
import com.dsf.comicspider.service.ComicPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.BloomFilterDuplicateRemover;
import us.codecraft.webmagic.scheduler.QueueScheduler;

import javax.sound.midi.Soundbank;
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
    private ComicListProcessor comicListProcessor;
    @Autowired
    private ComicPageListProcessor comicPageListProcessor;
    @Autowired
    private ComicNumListProcessor comicNumListProcessor;
    @Autowired
    private DownloadImgProcessor downloadImgProcessor;
    @Autowired
    private ComicInfoService comicInfoService;
    @Autowired
    private ComicPageService comicPageService;
    @Autowired
    private ComicNumberService comicNumberService;

    @Scheduled(initialDelay = 10, fixedDelay = 100000)
    public void spiderTask() throws InterruptedException {
       comicListProcessor.comicListSpider();
        System.out.println("开始page");
       comicPageListProcessor.comicPageListSpider();
        System.out.println("开始num");
       comicNumListProcessor.comicNumListSpider();
        System.out.println("开始img");
       downloadImgProcessor.downloadImg();
    }

}
