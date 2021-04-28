package com.dsf.comicspider.service;

import com.dsf.comicspider.dao.ComicInfoDao;
import com.dsf.comicspider.pojo.ComicInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import com.dsf.comicspider.dao.ComicInfoDao;
import com.dsf.comicspider.pojo.ComicInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *@ClaseeNme:ComcicInfoImp
 *@Description：
 *
 *@Author：戴少峰
 *@Date：2021/4/11-18:50
 *@Version： 1.0
 */
@Service
public class ComcicInfoImp implements ComicInfoService {
    @Autowired
    private ComicInfoDao comicInfoDao;
    @Override
    public List<ComicInfo> findByName(String name) {
        return comicInfoDao.findByName(name);
    }

    @Override
    public List<ComicInfo> findAll() {
        return comicInfoDao.findAll();
    }

    @Override
    public void save(List<ComicInfo> comicInfoList) {
        comicInfoDao.save(comicInfoList);
    }
}
