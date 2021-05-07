package com.dsf.comicspider.dao;

import java.util.List;

/**
 * @author 戴少峰
 * @version 1.0
 * @className BaseDao
 * @date 2021/5/4-0:27
 */
public interface BaseDao<E> {

    List<E> findAll();
    void save(List<E> list);
}
