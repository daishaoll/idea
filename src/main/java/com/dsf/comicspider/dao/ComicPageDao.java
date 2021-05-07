package com.dsf.comicspider.dao;

import com.dsf.comicspider.pojo.ComicNumber;
import com.dsf.comicspider.pojo.ComicPage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 戴少峰
 * @version 1.0
 * @className ComicPageDao
 * @date 2021/4/19-23:49
 */
public interface ComicPageDao extends BaseDao<ComicPage> {




    List<ComicPage> findByNumber(String number);

    void updateByStatus(@Param("id") Integer id, @Param("status") Integer status);
}
