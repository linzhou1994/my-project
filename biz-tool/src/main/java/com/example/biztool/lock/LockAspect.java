//package com.example.biztool.lock;
//
//
//
//import com.example.biztool.assertutil.AssertUtil;
//import com.example.biztool.assertutil.ErrorCode;
//import com.example.biztool.exception.BizException;
//import org.apache.commons.collections4.CollectionUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.springframework.core.annotation.Order;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//import java.lang.reflect.Method;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.Iterator;
//import java.util.Map;
//import java.util.Objects;
//import java.util.Set;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
///**
// * Order(2) 让这个切片在事务之后执行, 保证事务提交之后再解锁
// *
// * @author linzhou
// * @ClassName LockAspect.java
// * @createTime 2021年12月17日 15:19:00
// * @Description
// */
//@Aspect
//@Component
//@Order(2)
//public class LockAspect {
//    /**
//     * 读取redisKey中的变量名称的正则表达式
//     * "asdfsd{name}dsfds{value}" =[{"name"},{"value"}]
//     */
//    private static final Pattern FIELD_NAME_PATTERN = Pattern.compile("\\{.*?}");
//
//
//    @Resource(name = "redisTemplate")
//    private RedisAtomicClient redisAtomicClient;
//
//
//    @Around("@annotation(lock)")
//    public Object lock(ProceedingJoinPoint joinPoint, Lock lock) throws Throwable {
//        Object[] args = joinPoint.getArgs();
//        if (Objects.isNull(lock) || Objects.isNull(args) || args.length == 0) {
//            return joinPoint.proceed();
//        }
//
//        RedisKeyEnum[] keys = lock.keys();
//        if (Objects.isNull(keys) || keys.length == 0) {
//            return joinPoint.proceed();
//        }
//        RedisKeyEnum key = keys[0];
//        //获取分布式锁的key
//        String redisKey = getRedisKey(args, key);
//        LockUtil.LockCache lockCache = LockUtil.getLockCache();
//        if (lockCache.containsKey(redisKey)) {
//            //如果当前线程已经获得了这把锁 则直接执行方法,不需要继续加锁
//            return joinPoint.proceed();
//        }
//
//        long expireSeconds = lock.expireSeconds();
//        if (expireSeconds <= 0) {
//            expireSeconds = 2;
//        }
//        try (RedisLock redisLock = redisAtomicClient.getLock(redisKey, expireSeconds)) {
////            log.info("LockAspect lock success is {}, lock key:{},expireSeconds:{}", Objects.nonNull(redisLock), redisKey, expireSeconds);
////            AssertUtil.isNotNull(redisLock, ErrorCode.REQUEST_FREQUENTLY);
//            lockCache.put(redisKey, redisLock);
//            return joinPoint.proceed();
//        } catch (BizException bizException) {
//            throw bizException;
//        } catch (Exception e) {
////            log.warn("LockAspect lock error key:{},expireSeconds:{}", redisKey, expireSeconds, e);
////            throw new BizException(ErrorCode.REQUEST_FREQUENTLY.getMsg()).withCode(ErrorCode.REQUEST_FREQUENTLY.getCode());
//        } finally {
//            lockCache.remove(redisKey);
//        }
//    }
//
//    /**
//     * 获取分布式锁的key
//     *
//     * @param args 方法执行参数
//     * @param key  分布式锁的key
//     * @return
//     */
//    private String getRedisKey(Object[] args, RedisKeyEnum key) {
//        //获取key中变量对应的值的键值对
//        Map<String, String> fieldName2Value = getFieldName2ValueMap(args, key);
//
//        String redisKey = key.getTemplate();
//
//        for (Map.Entry<String, String> entry : fieldName2Value.entrySet()) {
//            redisKey = redisKey.replace("{" + entry.getKey() + "}", "{" + entry.getValue() + "}");
//        }
//        return redisKey;
//    }
//
//    /**
//     * 获取key中变量对应的值的键值对
//     *
//     * @param args
//     * @param key
//     * @return
//     */
//    private Map<String, String> getFieldName2ValueMap(Object[] args, RedisKeyEnum key) {
//        //获取key中变量
//        Set<String> fieldNames = getFieldNames(key);
//
//        Map<String, String> fieldName2Value = new HashMap<>(fieldNames.size());
//        for (Object arg : args) {
//            if (CollectionUtils.isEmpty(fieldNames)) {
//                break;
//            }
//            getFieldName2Value(arg, fieldName2Value, fieldNames);
//        }
//        return fieldName2Value;
//    }
//
//    private void getFieldName2Value(Object arg, Map<String, String> fieldName2Value, Set<String> fieldNames) {
//
//        if (Objects.isNull(arg)) {
//            return;
//        }
//
//        Class<?> aClass = arg.getClass();
//
//        Iterator<String> iterator = fieldNames.iterator();
//        while (iterator.hasNext()) {
//            String fieldName = iterator.next();
//            Object o = getFieldValue(arg, aClass, fieldName);
//            if (Objects.nonNull(o)) {
//                fieldName2Value.put(fieldName, String.valueOf(o));
//                iterator.remove();
//            }
//        }
//    }
//
//    private Object getFieldValue(Object arg, Class<?> aClass, String fieldName) {
//        try {
//
//            String methodName = getMethodName(fieldName);
//
//            Method method = aClass.getMethod(methodName);
//
//            return method.invoke(arg);
//        } catch (Exception e) {
//            return null;
//        }
//
//    }
//
//    /**
//     * 获取get方法名称
//     *
//     * @param fieldName
//     * @return
//     */
//    private String getMethodName(String fieldName) {
//        return "get" + upperFirstLatter(fieldName);
//    }
//
//
//    /**
//     * 首字母大写
//     *
//     * @param letter
//     * @return
//     */
//    public String upperFirstLatter(String letter) {
//        char[] chars = letter.toCharArray();
//        if (chars[0] >= 'a' && chars[0] <= 'z') {
//            chars[0] = (char) (chars[0] - 32);
//        }
//        return new String(chars);
//    }
//
//
//    /**
//     * 获取key中变量
//     *
//     * @param key
//     * @return
//     */
//    private Set<String> getFieldNames(RedisKeyEnum key) {
//        Set<String> fieldNames = new HashSet<>();
//
//        //生成匹配器，输入待匹配字符序列
//        Matcher m = FIELD_NAME_PATTERN.matcher(key.getTemplate());
//        //注意！find()一次，就按顺序扫描到了一个匹配的字符串，此时group()返回的就是该串。
//        while (m.find()) {
//            //打印匹配的子串
//            String group = m.group();
//
//            if (StringUtils.isNotBlank(group) && group.length() > 2) {
//                group = group.substring(1, group.length() - 1);
//                fieldNames.add(group);
//            }
//        }
//
//        return fieldNames;
//    }
//
//}
