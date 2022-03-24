package com.example.biztool.spring;

import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author linzhou
 * @ClassName BeanUtil.java
 * @createTime 2022年03月24日 18:03:00
 * @Description
 */
public class BeanUtil {

    public static <T> T copy(Object o, Class<T> clazz) {
        if (Objects.isNull(o)) {
            return null;
        }
        try {
            T t = clazz.newInstance();
            BeanUtils.copyProperties(o, t);
            return t;
        } catch (IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> List<T> copyList(List list, Class<T> clazz) {
        if (Objects.isNull(list) || list.isEmpty()) {
            return Collections.emptyList();
        }
        List<T> rlt = new ArrayList<>(list.size());

        for (Object o : list) {
            rlt.add(copy(o, clazz));
        }
        return rlt;
    }
}
