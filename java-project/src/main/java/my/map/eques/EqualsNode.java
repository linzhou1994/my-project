package my.map.eques;

import com.example.biztool.collection.ListSubIterator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * ////////////////////////////////////////////////////////////////////
 * //                          _ooOoo_                               //
 * //                         o8888888o                              //
 * //                         88" . "88                              //
 * //                         (| ^_^ |)                              //
 * //                         O\  =  /O                              //
 * //                      ____/`---'\____                           //
 * //                    .'  \\|     |//  `.                         //
 * //                   /  \\|||  :  |||//  \                        //
 * //                  /  _||||| -:- |||||-  \                       //
 * //                  |   | \\\  -  /// |   |                       //
 * //                  | \_|  ''\---/''  |   |                       //
 * //                  \  .-\__  `-`  ___/-. /                       //
 * //                ___`. .'  /--.--\  `. . ___                     //
 * //              ."" '<  `.___\_<|>_/___.'  >'"".                  //
 * //            | | :  `- \`.;`\ _ /`;.`/ - ` : | |                 //
 * //            \  \ `-.   \_ __\ /__ _/   .-` /  /                 //
 * //      ========`-.____`-.___\_____/___.-`____.-'========         //
 * //                           `=---='                              //
 * //      ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^        //
 * //         佛祖保佑           永无BUG           永不修改              //
 * //          佛曰:                                                  //
 * //                 写字楼里写字间，写字间里程序员;                      //
 * //                 程序人员写程序，又拿程序换酒钱.                      //
 * //                 酒醒只在网上坐，酒醉还来网下眠;                      //
 * //                 酒醉酒醒日复日，网上网下年复年.                      //
 * //                 但愿老死电脑间，不愿鞠躬老板前;                      //
 * //                 奔驰宝马贵者趣，公交自行程序员.                      //
 * //                 别人笑我忒疯癫，我笑自己命太贱;                      //
 * //                 不见满街漂亮妹，哪个归得程序员?                      //
 * ////////////////////////////////////////////////////////////////////
 *
 * @date : 2022/12/4 17:06
 * @author: linzhou
 * @description : EquesNode
 */
public class EqualsNode<E> {

    private boolean data;
    private boolean[] isNot = new boolean[10];

    private Map<E, EqualsNode<E>> keyCode2childNodeMap = new HashMap<>();

    public EqualsNode(boolean data) {
        this.data = data;
    }

    public boolean put(List<E> data, int index) {

        if (data.size() <= index) {
            return true;
        }
        E e = data.get(index);
        EqualsNode<E> eEquesNode = keyCode2childNodeMap.get(e);
        if (Objects.isNull(eEquesNode)) {
            eEquesNode = new EqualsNode<>(data.size() - 1 == index);
            EqualsNode<E> node = keyCode2childNodeMap.putIfAbsent(e, eEquesNode);
            if (Objects.nonNull(node)) {
                eEquesNode = node;
            }
        }
        return eEquesNode.put(data, ++index);
    }

    private boolean find(List<E> dataList, int index, int threadIndex) {
        if (dataList.size() <= index) {
            return false;
        }
        E e = dataList.get(index);
        EqualsNode<E> eEqualsNode = keyCode2childNodeMap.get(e);
        if (Objects.isNull(eEqualsNode)) {
            return false;
        }
        if (dataList.size() - 1 == index) {
            if (eEqualsNode.data) {
                eEqualsNode.isNot[threadIndex] = Boolean.TRUE;
                isNot[threadIndex] = Boolean.TRUE;
            }
            return eEqualsNode.data;
        } else {
            boolean b = eEqualsNode.find(dataList, ++index, threadIndex);
            if (b) {
                isNot[threadIndex] = Boolean.TRUE;
            }
            return b;
        }
    }

    public void retainAll(Collection<List<E>> dataList, int threadIndex) {
        Long createTreeStarTiem = System.currentTimeMillis();
        Iterator<List<E>> it = dataList.iterator();
        while (it.hasNext()) {
            List<E> next = it.next();
            find(next, 0, threadIndex);
            //让gc回收对象
            it.remove();
        }
        System.out.println("run time : " + (System.currentTimeMillis() - createTreeStarTiem) + "ms");
        createTreeStarTiem = System.currentTimeMillis();
        remove(threadIndex);
        System.out.println("run time : " + (System.currentTimeMillis() - createTreeStarTiem) + "ms");
    }

    private void remove(int threadIndex) {
        if (keyCode2childNodeMap.isEmpty()) {
            return;
        }
        Iterator<Map.Entry<E, EqualsNode<E>>> it = keyCode2childNodeMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<E, EqualsNode<E>> eEqualsNodeEntry = it.next();
            EqualsNode<E> value = eEqualsNodeEntry.getValue();
            if (!value.isNot[threadIndex]) {
                it.remove();
            } else {
                value.remove(threadIndex);
            }
        }
    }

    public int size() {
        if (keyCode2childNodeMap.isEmpty()) {
            return 1;
        }
        int size = 0;
        for (EqualsNode<E> value : keyCode2childNodeMap.values()) {
            size += value.size();
        }
        return size;
    }

    public static class MyConcurrentHashMap<K, V> extends ConcurrentHashMap<K, V> {

    }


    public static void main(String[] args) {
        test2();
    }

    private static void test1() {
        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        EqualsNode<Integer> tree = new EqualsNode<>(false);
        List<List<Integer>> keySets = getKeyLists();
        long createTreeStarTiem = System.currentTimeMillis();
        ListSubIterator<List<Integer>> it = new ListSubIterator<>(keySets, 1000000);
        //存储线程的返回值
        List<Future<Boolean>> results = new LinkedList<>();
        while (it.hasNext()) {
            List<List<Integer>> next = it.next();
            Future<Boolean> submit = threadPool.submit(new Callable<Boolean>() {

                @Override
                public Boolean call() throws Exception {
                    long createTreeStarTiem1 = System.currentTimeMillis();
                    for (List<Integer> integers : next) {
                        tree.put(integers, 0);
                    }
                    System.out.println("=====》run time : " + (System.currentTimeMillis() - createTreeStarTiem1) + "ms");
                    return true;
                }
            });
            results.add(submit);
        }
        for (Future<Boolean> result : results) {
            try {
                result.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        System.out.println("run time : " + (System.currentTimeMillis() - createTreeStarTiem) + "ms");
        List<List<Integer>> keySets1 = getKeyLists();
        createTreeStarTiem = System.currentTimeMillis();
        tree.retainAll(keySets1, 0);
        System.out.println("run time : " + (System.currentTimeMillis() - createTreeStarTiem) + "ms");
        int i = 0;
    }
    private static void test2() {
        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        EqualsNode<Integer> tree = new EqualsNode<>(false);
        Set<List<Integer>> keySets = getKeySets();
        long createTreeStarTiem = System.currentTimeMillis();
        //存储线程的返回值
        for (List<Integer> keySet : keySets) {
            tree.put(keySet, 0);
        }
        System.out.println("run time : " + (System.currentTimeMillis() - createTreeStarTiem) + "ms");
        List<List<Integer>> keySets1 = getKeyLists();
        createTreeStarTiem = System.currentTimeMillis();
        tree.retainAll(keySets1, 0);
        System.out.println("run time : " + (System.currentTimeMillis() - createTreeStarTiem) + "ms");
        int i = 0;
    }

    private static List<List<Integer>> getKeyLists() {
        List<List<Integer>> skuSetList = new ArrayList<>(10000000);
        for (int i = 0; i < 10000000; i++) {
            int skuSetSize = 3;
            List<Integer> skuSet = new ArrayList<>();
            for (int j = 0; j < skuSetSize; j++) {
                int skuCode = (int) (Math.random() * 1000);
                skuSet.add(skuCode);
            }
            skuSetList.add(skuSet);
        }
        return skuSetList;
    }

    private static Set<List<Integer>> getKeySets() {
        Set<List<Integer>> skuSetList = new HashSet<>(10000000);
        for (int i = 0; i < 10000000; i++) {
            int skuSetSize = 3;
            List<Integer> skuSet = new ArrayList<>();
            for (int j = 0; j < skuSetSize; j++) {
                int skuCode = (int) (Math.random() * 1000);
                skuSet.add(skuCode);
            }
            skuSetList.add(skuSet);
        }
        return skuSetList;
    }

    private static Set<Integer> getKeySet() {
        Set<Integer> skuSetList = new HashSet<>(10000000);
        for (int i = 0; i < 10000000; i++) {
            int skuCode = (int) (Math.random() * 1000000000);
            skuSetList.add(skuCode);
        }
        return skuSetList;
    }
}
