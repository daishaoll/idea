package com.dsf.comicspider.pojo;/**
 * @author 戴少峰
 * @create 2021-04-11 18:24
 */

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 *@ClaseeNme:ComicInfo
 *@Description：
 *@Author：戴少峰
 *@Date：2021/4/11-18:24
 *@Version： 1.0
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
public class ComicInfo {

    private Integer id;
    private String name;
    private String introduction;
    private Integer page;
    private Integer status;
    private String cover;
    private String url;
    private Date updateTime;

    public ComicInfo() {
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ComicInfo comicInfo = (ComicInfo) o;

        return url != null ? url.equals(comicInfo.url) : comicInfo.url == null;
    }

    @Override
    public int hashCode() {
        return url != null ? url.hashCode() : 0;
    }
}
