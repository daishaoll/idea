package com.dsf.comicspider.service;

import com.dsf.comicspider.dao.ComicPageDao;
import com.dsf.comicspider.pojo.ComicInfo;
import com.dsf.comicspider.pojo.ComicNumber;
import com.dsf.comicspider.pojo.ComicPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 戴少峰
 * @version 1.0
 * @className ComicPageImp
 * @date 2021/4/20-0:27
 */
@Service
public class ComicPageImpl implements ComicPageService {
    @Autowired
    private ComicPageDao comicPageDao;
    private volatile List<ComicPage> comicPageList;
    @Override
    public void save(List<ComicPage> comicPageList) {
        if (this.comicPageList == null) {
            synchronized (this) {
                if (this.comicPageList == null) {
                    this.comicPageList = this.findAll();
                }
            }
        }
        List<ComicPage> saveComicPageList = new ArrayList<>();
        for (ComicPage value : comicPageList) {
            if (this.comicPageList.stream().noneMatch((comicPage) -> comicPage.equals(value))) {
                saveComicPageList.add(value);
            }
        }
        if (saveComicPageList.size() > 0) {
            comicPageDao.save(saveComicPageList);
        }

    }

    @Override
    public List<ComicPage> findAll() {
        return comicPageDao.findAll();
    }

    @Override
    public List<ComicPage> findByNumber(String number) {
        return comicPageDao.findByNumber(number);
    }

    @Override
    public void updateByStatus(Integer id, Integer status) {
        comicPageDao.updateByStatus(id, status);
    }
}
