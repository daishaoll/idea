package com.dsf.comicspider.service;

import com.dsf.comicspider.dao.ComicNumberDao;
import com.dsf.comicspider.pojo.ComicNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 戴少峰
 * @version 1.0
 * @className ComicNumberImp
 * @date 2021/4/16-15:51
 */
@Service
public class ComicNumberImpl implements ComicNumberService {

    @Autowired
    private ComicNumberDao comicNumberDao;

    @Override
    public List<ComicNumber> findAll() {
        return comicNumberDao.findAll();
    }

    @Override
    public void save(List<ComicNumber> comicNumberList) {
        comicNumberDao.save(comicNumberList);
    }

    @Override
    public void updateById(ComicNumber comicNumber) {
        comicNumberDao.updateById(comicNumber);
    }

}
