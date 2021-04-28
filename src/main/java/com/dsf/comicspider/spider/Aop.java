package com.dsf.comicspider.spider;

import com.dsf.comicspider.service.ComicNumberService;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.testng.annotations.Test;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.scheduler.BloomFilterDuplicateRemover;
import us.codecraft.webmagic.scheduler.QueueScheduler;

/**
 * @author 戴少峰
 * @version 1.0
 * @className Aop
 * @date 2021/4/18-2:20
 */

//@Aspect
//@Component
public class Aop {
//    @Autowired
//    public Spider spider;

    @Pointcut("execution(* us.codecraft.webmagic.Spider.close(..))")
    public void pointcut() {
    }
    @Before("pointcut()")
    public void before() {
        System.out.println("hello.before");
    }
    @After("pointcut()")
    public void afeter() {
        System.out.println("hello.after");
    }


}
