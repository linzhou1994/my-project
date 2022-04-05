package guava.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author linzhou
 * @ClassName LoadingCacheTest.java
 * @createTime 2022年03月17日 15:00:00
 * @Description
 */
public class LoadingCacheTest {

    private static LoadingCache<String, String> graphs = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .expireAfterWrite(3, TimeUnit.SECONDS)
            .expireAfterAccess(1,TimeUnit.SECONDS)
            .build(
                    new CacheLoader<String, String>() {
                        @Override
                        public String load(String key) {
                            return null;
                        }
                    });

    public static void main(String[] args) {
        graphs.put("key1","key1Value");
        graphs.put("key2","key2Value");

        for (int i = 0; i < 100; i++) {
            try {
                System.out.println(graphs.get("key1"));
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            try {
                System.out.println(graphs.get("key2"));
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            try {
                Thread.sleep(500L);
                System.out.println(i+"=========500ms===============");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }


}
