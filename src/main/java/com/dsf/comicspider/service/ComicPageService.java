package com.dsf.comicspider.service;

import com.dsf.comicspider.pojo.ComicNumber;
import com.dsf.comicspider.pojo.ComicPage;

import java.util.List;

/**
 * @author 戴少峰
 * @version 1.0
 * @className ComicPageService
 * @date 2021/4/20-0:25
 */
public interface ComicPageService{
    /**
     * 保存漫画话数
     * @param comicPageList 漫画话数列表{@link ComicPage}
     * @return void
     * @author 戴少峰
     * @date 2021/4/20
     */
    void save(List<ComicPage> comicPageList);

    List<ComicPage> findAll();
}
