package com.dsf.comicspider.spider;

import com.dsf.comicspider.pojo.ComicNumber;
import com.dsf.comicspider.pojo.ComicPage;
import com.dsf.comicspider.service.ComicNumberService;
import com.dsf.comicspider.service.ComicPageService;
import com.dsf.comicspider.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.BloomFilterDuplicateRemover;
import us.codecraft.webmagic.scheduler.QueueScheduler;

import java.util.List;
import java.util.UUID;

/**
 * @author 戴少峰
 * @version 1.0
 * @className DownloadImgProcessor
 * @date 2021/4/29-21:31
 */
@Component
public class DownloadImgProcessor implements PageProcessor {
    @Autowired
    private ComicNumberService comicNumberService;
    @Autowired
    private ComicPageService comicPageService;
    @Autowired
    private DownloadImgProcessor downloadImgProcessor;
    @Override
    public void process(Page page) {
        String url = page.getUrl().toString();
        String comicName = StringUtils.find("name=(.*)&page", url);
        String comicPage = StringUtils.find("page=(.*)&number", url);

        String temp = StringUtils.find("number=(\\d*)", url);
        String comicNumber = String.format("%03d", Integer.valueOf(temp));
        String path = "c:\\漫画/" + comicName + "\\" + comicPage + "\\" +
                UUID.randomUUID().toString() + comicNumber + ".jpg" ;
        page.putField(path, page.getBytes());
        int id = page.getRequest().getExtra("id");
        ComicNumber updateComicNumber = new ComicNumber();
        updateComicNumber.setId(id);
        updateComicNumber.setLocalUrl(path);
        comicNumberService.updateById(updateComicNumber);

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

    public void downloadImg() {
        List<ComicNumber> comicNumList = comicNumberService.findAll();
        Spider spider = new Spider(downloadImgProcessor);
        for (ComicNumber comicNumber : comicNumList) {
            if (comicNumber.getPid() < 2) {
                spider.addRequest(new Request(comicNumber.getUrl()).setBinaryContent(true).putExtra("id", comicNumber.getId()));
            }
            }

        spider.setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(1000000)))
                .thread(10)
                .addPipeline(new FileDownloadPipeline())
                .run();
        System.out.println("完毕");
    }
}
