package com.dsf.comicspider.dao;

import com.dsf.comicspider.pojo.ComicInfo;

import java.util.List;

/**
 * @author 戴少峰
 * @create 2021-04-11 18:45
 */
public interface ComicInfoDao extends BaseDao<ComicInfo>{
    List<ComicInfo> findByName(String name);

}
