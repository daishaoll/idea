package com.dsf.comicspider.pojo;

/**
 * @className PageNumber
 * @author 戴少峰
 * @version 1.0
 * @date 2021/4/16-15:33
 */
public class ComicNumber {

    private Integer id;
    private Integer pid;
    private Integer number;
    private String url;

    public ComicNumber() {
    }

    public ComicNumber(Integer id, Integer pid, Integer number, String url) {
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

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
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
        return "PageNumber{" +
                "id=" + id +
                ", pid=" + pid +
                ", number=" + number +
                ", url='" + url + '\'' +
                '}';
    }
}
