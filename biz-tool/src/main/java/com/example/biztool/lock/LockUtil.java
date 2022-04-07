package com.example.biztool.lock;


import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author linzhou
 * @ClassName LockUtil.java
 * @createTime 2021年12月20日 10:24:00
 * @Description
 */
public class LockUtil<T> {

    private static final ThreadLocal<LockCache> local = new ThreadLocal() {
        @Override
        protected LockCache initialValue() {
            return new LockCache();
        }
    };

    public static boolean lock(String lockKey) {
        LockCache lockCache = local.get();
        return lockCache.lock(lockKey);
    }

    public static void unlock(String lockKey) {
        LockCache lockCache = local.get();
        lockCache.unlock(lockKey);
    }

    public static class LockCache<T> {
        private Map<String, LockDetail<T>> redisLockMap;

        public LockCache() {
            redisLockMap = new HashMap<>();
        }

        public boolean lock(String lockKey) {
            LockDetail<T> lockDetail = redisLockMap.get(lockKey);
            if (lockDetail == null) {
                lockDetail = new LockDetail<>(lockKey);
                redisLockMap.put(lockKey, lockDetail);
            }
            return lockDetail.lock();
        }

        public void unlock(String lockKey) {
            LockDetail<T> lockDetail = redisLockMap.get(lockKey);
            if (lockDetail != null) {
                lockDetail.unlock();
                if (lockDetail.canRemove()) {
                    redisLockMap.remove(lockKey);
                }
            }
        }
    }


    public static class LockDetail<T> {
        /**
         * 锁key
         */
        private String key;
        /**
         * 可重入锁次数
         */
        private Integer lockNum;
        /**
         * 锁
         */
        private T lock;
        /**
         * 获取到锁的线程
         */
        private Long threadId;

        public LockDetail(String key) {
            this.key = key;
            this.lockNum = 0;
            this.threadId = Thread.currentThread().getId();
        }

        public boolean lock() {
            if (!isCurrentThread()) {
                return false;
            }
            if (Objects.isNull(lock)) {
                //TODO 获取锁
            }
            lockNum += 1;
            return true;
        }

        public void unlock() {
            if (isCurrentThread()) {
                lockNum -= 1;
                if (lockNum == 0) {
                    lock = null;
                    //TODO 删除锁
                }
            }
        }

        /**
         * 是否是当前线程获取到的锁
         */
        public boolean isCurrentThread() {
            return Thread.currentThread().getId() == threadId;
        }

        /**
         * 判断是否允许删除锁
         */
        public boolean canRemove() {
            return lockNum == 0;
        }
    }
}
