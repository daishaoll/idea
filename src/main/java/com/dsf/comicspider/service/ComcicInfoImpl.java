package com.dsf.comicspider.service;

import com.dsf.comicspider.dao.ComicInfoDao;
import com.dsf.comicspider.pojo.ComicInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import com.dsf.comicspider.dao.ComicInfoDao;
import com.dsf.comicspider.pojo.ComicInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 *@ClaseeNme:ComcicInfoImp
 *@Description：
 *
 *@Author：戴少峰
 *@Date：2021/4/11-18:50
 *@Version： 1.0
 */
@Service
public class ComcicInfoImpl implements ComicInfoService {
    @Autowired
    private ComicInfoDao comicInfoDao;
    private volatile List<ComicInfo> comicInfoList;


    @Override
    public List<ComicInfo> findByName(String name) {
        return comicInfoDao.findByName(name);
    }

    @Override
    public List<ComicInfo> findAll() {
        List<ComicInfo> comicInfoList =  comicInfoDao.findAll();
        return comicInfoList;
    }

    @Override
    public void save(List<ComicInfo> comicInfoList) {
        if (this.comicInfoList == null) {
            synchronized (this) {
                if (this.comicInfoList == null) {
                    this.comicInfoList = this.findAll();
                }
            }
        }
        List<ComicInfo> saveList = new ArrayList<>();
        for (ComicInfo value : comicInfoList) {
            if (this.comicInfoList.stream().noneMatch((comicInfo) -> comicInfo.equals(value))) {
                saveList.add(value);
            }
        }
        if (saveList.size() > 0) {
            comicInfoDao.save(saveList);
        }


    }
}
