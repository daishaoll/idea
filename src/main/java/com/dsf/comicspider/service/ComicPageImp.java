package com.dsf.comicspider.service;

import com.dsf.comicspider.dao.ComicPageDao;
import com.dsf.comicspider.pojo.ComicNumber;
import com.dsf.comicspider.pojo.ComicPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 戴少峰
 * @version 1.0
 * @className ComicPageImp
 * @date 2021/4/20-0:27
 */
@Service
public class ComicPageImp implements ComicPageService {
    @Autowired
    private ComicPageDao comicPageDao;
    @Override
    public void save(List<ComicPage> comicPageList) {
        comicPageDao.save(comicPageList);
    }

    @Override
    public List<ComicPage> findAll() {
        return comicPageDao.findAll();
    }
}
