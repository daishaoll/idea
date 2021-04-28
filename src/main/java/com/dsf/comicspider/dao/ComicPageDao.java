package com.dsf.comicspider.dao;

import com.dsf.comicspider.pojo.ComicNumber;
import com.dsf.comicspider.pojo.ComicPage;

import java.util.List;

/**
 * @author 戴少峰
 * @version 1.0
 * @className ComicPageDao
 * @date 2021/4/19-23:49
 */
public interface ComicPageDao {

    /**
     * 保存漫画的话数
     * @param comicPageList  漫画话数列表 {@link ComicPage}
     * @return void
     * @author 戴少峰
     * @date 2021/4/19
     */
    void save(List<ComicPage> comicPageList);

    List<ComicPage> findAll();
}
