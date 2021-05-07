package com.dsf.comicspider.utils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 戴少峰
 * @version 1.0
 * @className StringUtils
 * @date 2021/4/29-14:24
 */
public class StringUtils {

    /**
     * 通过正则表达式，判断字符串是否符合一定的规则  <br>
     *
     * @param regex   正则表达式
     * @param content 待验证的字符串
     * @return boolean
     * @author 戴少峰
     * @date 2021/4/15
     */

    public static boolean pattern(String regex, String content) {
        Pattern p = Pattern.compile(regex);
        Matcher matcher = p.matcher(content);
        return matcher.find();
    }

    /**
     * 通过正则表达式，提取相关内容
     *
     * @param regex   正则表达式
     * @param content 待匹配的内容
     * @return java.lang.String 提取到的字符串
     * @author 戴少峰
     * @date 2021/4/15
     */
    public static String find(String regex, String content) {
        Pattern p = Pattern.compile(regex);
        Matcher matcher = p.matcher(content);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

}

