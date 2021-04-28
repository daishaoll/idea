package com.dsf.comicspider.service;

import com.dsf.comicspider.pojo.ComicInfo;

import java.util.List;

/**
 * @author 戴少峰
 * @create 2021-04-11 18:49
 */
public interface ComicInfoService {
    List<ComicInfo> findByName(String name);

    List<ComicInfo> findAll();

    void save(List<ComicInfo> comicInfoList);
}
