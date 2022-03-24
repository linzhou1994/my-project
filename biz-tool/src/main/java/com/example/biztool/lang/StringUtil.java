package com.example.biztool.lang;

import org.apache.commons.lang3.StringUtils;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author linzhou
 * @ClassName StringUtils.java
 * @createTime 2022年03月24日 16:23:00
 * @Description
 */
public class StringUtil {
    /**
     * 获取key中变量
     *
     * @param str 需要提取参数的字符串
     * @param patternStr 正则表达式
     * @return
     */
    public static Set<String> getParams(String str, String patternStr) {
//        Pattern pattern = Pattern.compile("\\{.*?}");
        Pattern pattern = Pattern.compile(patternStr);

        return getParams(str,pattern);
    }
    public static Set<String> getParams(String str, Pattern pattern) {
        Set<String> fieldNames = new HashSet<>();

        //生成匹配器，输入待匹配字符序列
        Matcher m = pattern.matcher(str);
        //注意！find()一次，就按顺序扫描到了一个匹配的字符串，此时group()返回的就是该串。
        while (m.find()) {
            //打印匹配的子串
            String group = m.group();

            if (StringUtils.isNotBlank(group) && group.length() > 2) {
                group = group.substring(1, group.length() - 1);
                fieldNames.add(group);
            }
        }

        return fieldNames;
    }

    /**
     * 首字母大写
     *
     * @param letter
     * @return
     */
    public static String upperFirstLatter(String letter) {
        char[] chars = letter.toCharArray();
        if (chars[0] >= 'a' && chars[0] <= 'z') {
            chars[0] = (char) (chars[0] - 32);
        }
        return new String(chars);
    }
}
