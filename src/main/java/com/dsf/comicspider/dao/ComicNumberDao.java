package com.dsf.comicspider.dao;

import com.dsf.comicspider.pojo.ComicNumber;

import java.util.List;

/**
 * @className ComicNumberDao
 * @author 戴少峰
 * @version 1.0
 * @date 2021/4/16-15:37
 */
public interface ComicNumberDao {
    /**
     * 保存漫画页码
     * @param comicNumber 漫画页码实体类
     * @return void
     * @author 戴少峰
     * @date 2021/4/16
     */
    void save(List<ComicNumber> comicNumberList);
}
