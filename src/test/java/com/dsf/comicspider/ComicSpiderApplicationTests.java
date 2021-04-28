package com.dsf.comicspider;

import com.dsf.comicspider.pojo.ComicPage;
import com.dsf.comicspider.service.ComicNumberImp;
import com.dsf.comicspider.service.ComicNumberService;
import com.dsf.comicspider.service.ComicPageImp;
import com.dsf.comicspider.service.ComicPageService;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;
import us.codecraft.webmagic.Page;

import java.util.List;

@SpringBootTest
class ComicSpiderApplicationTests {
    @Autowired
    private ComicPageService comicPageService;
    @Test
    void contextLoads() {
        List<ComicPage> all = comicPageService.findAll();
        System.out.println(all);

    }

}
