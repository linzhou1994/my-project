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
//public class LockUtil {
//
//    private static final ThreadLocal<LockCache> local = new ThreadLocal() {
//        @Override
//        protected LockCache initialValue() {
//            return new LockCache();
//        }
//    };
//
//    public static LockCache getLockCache(){
//        return local.get();
//    }
//
//
//    public static class LockCache {
//        private Map<String, RedisLock> redisLockMap;
//
//        public LockCache() {
//            redisLockMap = new HashMap<>();
//        }
//
//        public boolean containsKey(String key) {
//            return redisLockMap.containsKey(key);
//        }
//
//        public RedisLock get(String key) {
//            return redisLockMap.get(key);
//        }
//
//        public void put(String key, RedisLock redisLock) {
//            redisLockMap.put(key, redisLock);
//        }
//
//        public void remove(String key) {
//            redisLockMap.remove(key);
//        }
//
//
//    }
//}
