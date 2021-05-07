package com.dsf.comicspider.service;

import com.dsf.comicspider.dao.ComicNumberDao;
import com.dsf.comicspider.pojo.ComicNumber;

import java.util.List;

/**
 * @author 戴少峰
 * @version 1.0
 * @className ComicNumberService
 * @date 2021/4/16-15:48
 */
public interface ComicNumberService {
    /**
     * @see ComicNumberDao
     * @param comicNumber 漫画页码实体类
     * @return void
     * @author 戴少峰
     * @date 2021/4/16
     */

    List<ComicNumber> findAll();

    void save(List<ComicNumber> comicNumberList);

    void updateById(ComicNumber comicNumber);


}
