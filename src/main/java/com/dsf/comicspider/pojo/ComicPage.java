package com.dsf.comicspider.pojo;/**
 * @author 戴少峰
 * @create 2021-04-11 18:26
 */

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 *@ClaseeNme:ComicPage
 *@Description：
 *@Author：戴少峰
 *@Date：2021/4/11-18:26
 *@Version： 1.0
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
public class ComicPage {

    private Integer id;
    private Integer pid;
    private String number;
    private String url;
    private Integer status;
    private Date updateTime;

    public ComicPage() {
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ComicPage comicPage = (ComicPage) o;

        return url != null ? url.equals(comicPage.url) : comicPage.url == null;
    }

    @Override
    public int hashCode() {
        return url != null ? url.hashCode() : 0;
    }
}
