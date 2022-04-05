//package com.example.biztool.lock;
//
//
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * @author linzhou
// * @ClassName LockUtil.java
// * @createTime 2021年12月20日 10:24:00
// * @Description
// */
//public class LockUtil<T> {
//
//    private static final ThreadLocal<LockCache> local = new ThreadLocal() {
//        @Override
//        protected LockCache initialValue() {
//            return new LockCache();
//        }
//    };
//
//    public static LockCache getLockCache() {
//        return local.get();
//    }
//
//
//    public static class LockCache<T> {
//        private Map<String, LockDetail<T>> redisLockMap;
//
//        public LockCache() {
//            redisLockMap = new HashMap<>();
//        }
//
//        public boolean containsKey(String key) {
//            return redisLockMap.containsKey(key);
//        }
//
//        public T get(String key) {
//            return redisLockMap.get(key).getLock();
//        }
//
//        public void put(String key, T redisLock) {
//            redisLockMap.put(key, redisLock);
//        }
//
//        public void remove(String key) {
//            redisLockMap.remove(key);
//        }
//
//
//    }
//
//    public static class LockDetail<T> {
//        private String key;
//        private Integer lockNum;
//        private T lock;
//
//        public LockDetail(String key, T lock) {
//            this.key = key;
//            this.lockNum = 1;
//            this.lock = lock;
//        }
//
//        public String getKey() {
//            return key;
//        }
//
//        public void setKey(String key) {
//            this.key = key;
//        }
//
//        public Integer getLockNum() {
//            return lockNum;
//        }
//
//        public void setLockNum(Integer lockNum) {
//            this.lockNum = lockNum;
//        }
//
//        public T getLock() {
//            return lock;
//        }
//
//        public void setLock(T lock) {
//            this.lock = lock;
//        }
//    }
//}
