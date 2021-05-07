package com.dsf.comicspider.spider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.pipeline.FilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.BloomFilterDuplicateRemover;
import us.codecraft.webmagic.scheduler.QueueScheduler;
import us.codecraft.webmagic.selector.Html;

import javax.sql.DataSource;
import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author 戴少峰
 * @version 1.0
 * @className Test
 * @date 2021/4/29-16:50
 */
@Component
public class Test implements PageProcessor {

    private int a = 10;
    private int b = 10;
    private int c = 10;
    public static void main(String[] args) throws Exception, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, FileNotFoundException {
        Human human = new Human();
        System.out.println(human);
        Human human1 = human.test3();
        System.out.println(human1 == human);
        System.out.println(human1.a);


    }

    @Override
    public void process(Page page) {
        Html html = page.getHtml();
        String url = page.getUrl().toString();
        page.putField("hello", page.getBytes());
//        page.putField("1", "2");


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

    interface a {}

}
interface b {

}
interface c extends Test.a,b {

}