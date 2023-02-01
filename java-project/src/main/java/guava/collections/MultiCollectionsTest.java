package guava.collections;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.Table;

/**
 * @author linzhou
 * @ClassName MultiCollections.java
 * @createTime 2022年03月17日 15:16:00
 * @Description
 */
public class MultiCollectionsTest {

    private static ListMultimap<String,String> listMultimap = ArrayListMultimap.create();

    private static SetMultimap<String,String> setMultimap = HashMultimap.create();

    private static Table<Class<?>, Class<?>, String> paramClass2ReturnClass2convertors = HashBasedTable.create();

    public static void main(String[] args) {
        Object[] bytes = new Byte[]{'1','2','3',' '};
        for (Object aByte : bytes) {
            boolean b = aByte instanceof Number;
            System.out.println("输出"+ b);
        }

    }
}
