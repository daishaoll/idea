package com.dsf.comicspider;

import com.dsf.comicspider.pojo.ComicPage;
import com.dsf.comicspider.service.ComicPageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
