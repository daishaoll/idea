package com.dsf.comicspider.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @className PageNumber
 * @author 戴少峰
 * @version 1.0
 * @date 2021/4/16-15:33
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
public class ComicNumber {

    private Integer id;
    private Integer pid;
    private Integer number;
    private String url;
    private String localUrl;

    public ComicNumber() {
    }


}
