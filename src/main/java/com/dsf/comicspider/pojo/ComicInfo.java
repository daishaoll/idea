package com.dsf.comicspider.pojo;/**
 * @author 戴少峰
 * @create 2021-04-11 18:24
 */

/**
 *@ClaseeNme:ComicInfo
 *@Description：
 *@Author：戴少峰
 *@Date：2021/4/11-18:24
 *@Version： 1.0
 */
public class ComicInfo {

    private Integer id;
    private String name;
    private String introduction;
    private Integer page;
    private String status;
    private String cover;
    private String url;

    public ComicInfo() {
    }

    public ComicInfo(Integer id, String name, String introduction, Integer page, String status, String cover, String url) {
        this.id = id;
        this.name = name;
        this.introduction = introduction;
        this.page = page;
        this.status = status;
        this.cover = cover;
        this.url = url;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "ComicInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", introduction='" + introduction + '\'' +
                ", page=" + page +
                ", status='" + status + '\'' +
                ", cover='" + cover + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
