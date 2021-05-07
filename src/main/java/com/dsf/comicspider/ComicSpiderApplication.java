package com.dsf.comicspider;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.dsf.comicspider.dao")
@EnableScheduling

public class ComicSpiderApplication {
    public static void main(String[] args) {
        SpringApplication.run(ComicSpiderApplication.class, args);


    }


}
