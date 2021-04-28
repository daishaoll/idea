package com.dsf.comicspider.pojo;/**
 * @author 戴少峰
 * @create 2021-04-11 18:26
 */

/**
 *@ClaseeNme:ComicPage
 *@Description：
 *@Author：戴少峰
 *@Date：2021/4/11-18:26
 *@Version： 1.0
 */
public class ComicPage {

    private Integer id;
    private Integer pid;
    private String number;
    private String url;

    public ComicPage() {
    }

    public ComicPage(Integer id, Integer pid, String number, String url) {
        this.id = id;
        this.pid = pid;
        this.number = number;
        this.url = url;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "ComicPage{" +
                "id=" + id +
                ", pid=" + pid +
                ", number=" + number +
                ", url='" + url + '\'' +
                '}';
    }
}
