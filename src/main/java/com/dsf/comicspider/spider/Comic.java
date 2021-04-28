package com.dsf.comicspider.spider;

import com.dsf.comicspider.pojo.ComicInfo;
import com.dsf.comicspider.pojo.ComicNumber;
import com.dsf.comicspider.pojo.ComicPage;
import com.dsf.comicspider.service.ComicInfoService;
import com.dsf.comicspider.service.ComicNumberImp;
import com.dsf.comicspider.service.ComicNumberService;
import com.dsf.comicspider.service.ComicPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.testng.annotations.Test;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.BloomFilterDuplicateRemover;
import us.codecraft.webmagic.scheduler.QueueScheduler;
import us.codecraft.webmagic.selector.Html;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 戴少峰
 * @version 1.0
 * @date 2021/4/11-20:30
 */
@Component
public class Comic implements PageProcessor {

    @Autowired
    private ComicInfoService comicInfoService;
    @Autowired
    private ComicPageService comicPageService;
    @Autowired
    private ComicNumberService comicNumberService;
    private static Map<String, Integer> numberAndIdMap;
    private ThreadLocal<Integer> threadLocal= new ThreadLocal<>();
    private static Queue<ComicNumber> comicNumberQueue = new LinkedList<>();
    private static Integer no1 = 0;
    private static Integer no2 = 0;
    private static int sleepTime = 0;

    public static Queue<ComicNumber> getComicNumberQueue() {
        return comicNumberQueue;
    }

    @Override
    public void process(Page page) {
        Html html = page.getHtml();
        String url = page.getUrl().toString();
        addComicList(page, html, url);
        addComicPage(page, html, url);
        addComicNumber(page, html, url);
        downloadImg(page, url);
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
        if (pattern(".com/$", url)) {
            List<String> comicLinkList = html.css("div#mhmain").links().all();
            List<String> comicNameList = html.css("div.round>li:last-of-type a", "text").all();
            List<ComicInfo> comicInfoList = new ArrayList<>();

            for (int i = 0; i < comicLinkList.size(); i++) {
                comicLinkList.remove(i);
            }
            for (int i = 0; i < comicLinkList.size(); i++) {
                ComicInfo comicInfo = new ComicInfo();
                comicInfo.setName(comicNameList.get(i));
                comicInfo.setUrl(comicLinkList.get(i));
                comicInfoList.add(comicInfo);
            }
            comicInfoService.save(comicInfoList);
            if (!comicLinkList.isEmpty()) {
                System.out.println(comicLinkList);
                page.addTargetRequest(comicLinkList.get(1));
                page.addTargetRequest(comicLinkList.get(3));
                page.addTargetRequest(comicLinkList.get(5));
            }
        }
    }

    public void saveComicList() {
        
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
        if (this.pattern("com/\\d+/$", url)) {
            sleepTime += 10000;
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            List<String> comicPageLinkList = html.css(".pure-u-1-2.pure-u-lg-1-4").links().all();
            List<String> comicPageNameList = html.css(".pure-u-1-2.pure-u-lg-1-4>a", "text").all();
            List<ComicInfo> comicInfoList = comicInfoService.findAll();
            Map<String, Integer> idAndUrlMap = new HashMap<>();
            for (ComicInfo comicInfo : comicInfoList) {
                idAndUrlMap.put(comicInfo.getUrl(), comicInfo.getId());
            }
            if (idAndUrlMap.containsKey(url)) {
                ArrayList<ComicPage> comicPageList = new ArrayList<>();
                for(int i = 0; i < comicPageLinkList.size(); i++) {
                    ComicPage comicPage = new ComicPage();
                    comicPage.setPid(idAndUrlMap.get(url));
                    comicPage.setUrl(comicPageLinkList.get(i));
                    comicPage.setNumber(comicPageNameList.get(i));
                    comicPageList.add(comicPage);
                }
                comicPageService.save(comicPageList);
                System.out.println("话数" + no1++);

            }

            if (!comicPageLinkList.isEmpty()) {
                int count = 0;
                for (String val : comicPageLinkList) {
                    if (count++ < 5) {
                        page.addTargetRequest(val);
                    } else {
                        break;
                    }
                }
            }
        }
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

    public void addComicNumber(Page page, Html html, String url) {
        if (pattern("/\\d*/\\w*/$", url) || pattern(".html$", url)) {
            String nextComicNumberLink = html.css("a.button-success + a").links().toString();
            String currentComicNumberLink = html.css("a.button-success").links().toString();
            String comicNumber = find("_(\\d*).html", currentComicNumberLink);
            if (nextComicNumberLink != null && !nextComicNumberLink.isEmpty()) {
                page.addTargetRequest(nextComicNumberLink);
            }
            String comicFullName = html.css("h1").regex(">(\\S*)\\s*<").toString();
            String comicName = comicFullName.contains("(") ? find("(.*)\\(", comicFullName) : comicFullName;
            if (comicName != null) {
                String comicImgSuffix = html.css("script").regex("mhurl=\"(.*\\.jpg)").toString();
                String comicImgLink = "http://www-mipengine-org.mipcdn.com/i/p3.manhuapan.com/" + comicImgSuffix + "?name=" + comicName + "&number=" + comicNumber;
                page.addTargetRequest(comicImgLink);
            }
        }
    }

    /**
     * 将爬取到的图片下载到本地
     *
     * @param page 框架的页面类{@link Page}
     * @param url  图片的uri
     * @return void
     * @author 戴少峰
     * @date 2021/4/15
     */
    public void downloadImg(Page page, String url) {
        if (url.contains(".jpg")) {
            String path = "C:\\漫画\\" + find("name=(.*)&", url);
            int number = Integer.valueOf(String.format("%03d", Integer.valueOf(find("number=(.*)", url))));
            String fileName = System.currentTimeMillis() + (number + ".jpg");
            byte[] content = page.getBytes();
            saveFile(path, fileName, content);
            saveUrl(path + "\\" + fileName, number, url);
        }
    }

    /**
     * 将内容保存到本地
     *
     * @param path     文件路径
     * @param fileName 文件名
     * @param content  文件内容（byte数组）
     * @return void
     * @author 戴少峰
     * @date 2021/4/15
     */

    public void saveFile(String path, String fileName, byte[] content) {
        FileOutputStream fos = null;
        try {
            File file = new File(path, fileName);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            if (!file.exists()) {
                fos = new FileOutputStream(file, false);
                fos.write(content);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void saveUrl(String filePath, Integer number, String url) {
        System.out.println("页码" + no2++);
        if (threadLocal.get() == null) {
            threadLocal.set(0);
        }
        Integer count = threadLocal.get();
        ComicNumber comicNumber = new ComicNumber();
        synchronized (this) {
            if (numberAndIdMap == null) {
                numberAndIdMap = new HashMap<>(300);
                List<ComicPage> comicPageList = comicPageService.findAll();
                for (ComicPage comicPage : comicPageList) {
                    numberAndIdMap.put(comicPage.getNumber(), comicPage.getId());
                }
            }
        }
        String numberName = find("name=(.*)&", url);

        if (numberAndIdMap.containsKey(numberName)) {
            comicNumber.setPid(numberAndIdMap.get(numberName));
        }
        comicNumber.setUrl(filePath);
        comicNumber.setNumber(number);
        comicNumberQueue.offer(comicNumber);
        threadLocal.set(threadLocal.get() + 1);
        if (threadLocal.get() == 5) {
            List<ComicNumber> comicNumberList = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                comicNumberList.add(comicNumberQueue.poll());
            }
            this.comicNumberService.save(comicNumberList);
            threadLocal.set(0);
        }

    }

    public void saveComicInfo() {

    }


    /**
     * 通过正则表达式，判断字符串是否符合一定的规则  <br>
     *
     * @param regex   正则表达式
     * @param content 待验证的字符串
     * @return boolean
     * @author 戴少峰
     * @date 2021/4/15
     */

    public boolean pattern(String regex, String content) {
        Pattern p = Pattern.compile(regex);
        Matcher matcher = p.matcher(content);
        return matcher.find();
    }

    /**
     * 通过正则表达式，提取相关内容
     *
     * @param regex   正则表达式
     * @param content 待匹配的内容
     * @return java.lang.String 提取到的字符串
     * @author 戴少峰
     * @date 2021/4/15
     */
    public String find(String regex, String content) {
        Pattern p = Pattern.compile(regex);
        Matcher matcher = p.matcher(content);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
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
//    @Scheduled(initialDelay = 1000,fixedDelay = 500 * 1000)
//    public void startSpider() {
//        spider.run();
//
//    }

    @Test
    public void test() {

        System.out.println(pattern("/\\d*/[0-9a-z]*/$",
                "https://manhua.fzdm.com/1/brc56/"));
    }

    @Test
    public void test1() {
        String comicNumberLink = "https://manhua.fzdm.com/1/brc56/index_40.html";
        int prefix = comicNumberLink.indexOf("_");
        int suffix = comicNumberLink.indexOf(".html");
        String comicNumber = (String) comicNumberLink.subSequence(prefix + 1, suffix);
        System.out.println(comicNumber);
    }

    @Test
    public void test2() {
        String url = "https://manhua.fzdm.com/1/brc56/index_40.html?name=231&number=02";
        System.out.println(find("number=(.*)", url));
        String fileName = String.format("%s", find("number=(.*)", url));
        System.out.println(fileName);
    }

    @Test
    public void test3() {
        String regexx = "\\([^\\)]*\\)";//匹配小括号
        String xiaokuohao = "(ab)=(cd)>(dg)";
        Pattern comp = Pattern.compile(regexx);
        Matcher mat = comp.matcher(xiaokuohao);
        while (mat.find()) {
            String group = mat.group();
            System.out.print(group + ";");
        }
    }

    @Test
    public void test4() {

        if (threadLocal.get() == null) {
            threadLocal.set(0);
        }
        Integer a = threadLocal.get();
        a++;
        System.out.println(threadLocal.get());
    }

}
