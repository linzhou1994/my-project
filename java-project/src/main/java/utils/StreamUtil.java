package utils;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * CopyRight : <company domain>
 * Project :  my-project
 * Comments : <对此类的描述，可以引用系统设计中的描述>
 * JDK version : JDK1.8
 * Create Date : 2022-06-09 16:48
 *
 * @author : linzhou
 * @version : 1.0
 * @since : 1.0
 */
public class StreamUtil {

    /**
     * 过滤集合
     *
     * @param c
     * @param predicate
     * @param <T>
     * @return
     */
    public static <T> List<T> filter2List(Collection<T> c, Predicate<? super T> predicate) {
        if (Objects.isNull(c) || c.isEmpty()) {
            return new ArrayList<>();
        }
        if (Objects.isNull(predicate)) {
            return new ArrayList<>(c);
        }
        return c.stream().filter(predicate).collect(Collectors.toList());
    }

    /**
     * 过滤集合
     *
     * @param c
     * @param predicate
     * @param <T>
     * @return
     */
    public static <T> Set<T> filter2Set(Collection<T> c, Predicate<? super T> predicate) {
        if (Objects.isNull(c) || c.isEmpty()) {
            return new HashSet<>();
        }
        if (Objects.isNull(predicate)) {
            return new HashSet<>(c);
        }
        return c.stream().filter(predicate).collect(Collectors.toSet());
    }

    /**
     * 集合类型转换
     *
     * @param c
     * @param mapper
     * @param <T>
     * @param <R>
     * @return
     */
    public static <T, R> List<R> getList(Collection<T> c, Function<? super T, ? extends R> mapper) {
        if (Objects.isNull(c) || c.isEmpty()) {
            return new ArrayList<>();
        }
        return c.stream().map(mapper).collect(Collectors.toList());
    }

    /**
     * 集合过滤并类型转换
     *
     * @param c
     * @param mapper
     * @param <T>
     * @param <R>
     * @return
     */
    public static <T, R> List<R> getList(Collection<T> c, Predicate<? super T> predicate, Function<? super T, ? extends R> mapper) {
        if (Objects.isNull(c) || c.isEmpty()) {
            return new ArrayList<>();
        }
        return c.stream().filter(predicate).map(mapper).collect(Collectors.toList());
    }

    /**
     * 集合类型转换
     *
     * @param c
     * @param mapper
     * @param <T>
     * @param <R>
     * @return
     */
    public static <T, R> Set<R> getSet(Collection<T> c, Function<? super T, ? extends R> mapper) {
        if (Objects.isNull(c) || c.isEmpty()) {
            return new HashSet<>();
        }
        return c.stream().map(mapper).collect(Collectors.toSet());
    }

    /**
     * 集合过滤并类型转换
     *
     * @param c
     * @param mapper
     * @param <T>
     * @param <R>
     * @return
     */
    public static <T, R> Set<R> getSet(Collection<T> c, Predicate<? super T> predicate, Function<? super T, ? extends R> mapper) {
        if (Objects.isNull(c) || c.isEmpty()) {
            return new HashSet<>();
        }
        return c.stream().filter(predicate).map(mapper).collect(Collectors.toSet());
    }

    /**
     * 集合转map
     *
     * @param c
     * @param mapper
     * @param <T>
     * @param <R>
     * @return
     */
    public static <T, R> Map<R, T> getMap(Collection<T> c, Function<? super T, ? extends R> mapper) {
        if (Objects.isNull(c) || c.isEmpty()) {
            return new HashMap<>();
        }
        return c.stream().collect(Collectors.toMap(mapper, o -> o));
    }

    /**
     * 集合过滤并转map
     *
     * @param c
     * @param mapper
     * @param <T>
     * @param <R>
     * @return
     */
    public static <T, R> Map<R, T> getMap(Collection<T> c, Predicate<? super T> predicate, Function<? super T, ? extends R> mapper) {
        if (Objects.isNull(c) || c.isEmpty()) {
            return new HashMap<>();
        }
        return c.stream().filter(predicate).collect(Collectors.toMap(mapper, o -> o));
    }

    /**
     * 集合分组
     *
     * @param c
     * @param mapper
     * @param <T>
     * @param <R>
     * @return
     */
    public static <T, R> ListMultimap<R, T> groupingBy(Collection<T> c, Function<? super T, ? extends R> mapper) {
        if (Objects.isNull(c) || c.isEmpty()) {
            return ArrayListMultimap.create();
        }
        Map<R, List<T>> mapList = c.stream().collect(Collectors.groupingBy(mapper));
        return toListMultimap(mapList);
    }

    /**
     * 集合过滤并分组
     *
     * @param c
     * @param mapper
     * @param <T>
     * @param <R>
     * @return
     */
    public static <T, R> ListMultimap<R, T> groupingBy(Collection<T> c, Predicate<? super T> predicate, Function<? super T, ? extends R> mapper) {
        if (Objects.isNull(c) || c.isEmpty()) {
            return ArrayListMultimap.create();
        }
        Map<R, List<T>> mapList = c.stream().filter(predicate).collect(Collectors.groupingBy(mapper));
        return toListMultimap(mapList);
    }

    public static <T, R> ListMultimap<T, R> toListMultimap(Map<T, List<R>> mapList) {
        ListMultimap<T, R> listMultimap = ArrayListMultimap.create();
        if (Objects.isNull(mapList) || mapList.isEmpty()) {
            return listMultimap;
        }

        for (T key : mapList.keySet()) {
            List<R> valueList = mapList.get(key);
            listMultimap.putAll(key, valueList);
        }
        return listMultimap;
    }
}
