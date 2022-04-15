package my.map;

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
 * @date : 2022/4/15 10:47
 * @author: linzhou
 * @description : CollectionKeyTreeMap
 */

import org.apache.commons.collections4.CollectionUtils;

import java.io.IOException;
import java.util.*;

/**
 * @作者 林周
 * @时间 2019/9/2
 * @描述
 */
public class CollectionKeyTreeMap<E, T> {

    private final CollectionKeyTreeMapNode<E, T> rootNode;

    private final Map<E, CollectionKeyTreeMapNodeCache<E, T>> keyCode2IndexNodeListMap;

    public CollectionKeyTreeMap() {
        rootNode = new CollectionKeyTreeMapNode<E, T>(this);
        keyCode2IndexNodeListMap = new HashMap<>();
    }

    /**
     * 查找子集
     *
     * @param fatherCollection 父集数据
     * @return
     */
    public List<T> getAllChildCollection(Collection<E> fatherCollection) {
        if (CollectionUtils.isEmpty(fatherCollection)) {
            return new ArrayList<>();
        }
        List<E> sortKeyList = getSortKeyList(fatherCollection);
        List<T> rlt = rootNode.getAllChildCollection(sortKeyList);
        return rlt;
    }

    /**
     * 查找父集
     *
     * @param childCollection 子集数据
     * @return
     */
    public T getOneFatherCollection(Set<E> childCollection) {
        if (CollectionUtils.isEmpty(childCollection)) {
            return null;
        }
        //对key集合排序
        List<E> sortKeyList = getSortKeyList(childCollection);
        //找到排序后的最后一个key
        E lastkeyCode = sortKeyList.get(sortKeyList.size() - 1);
        //找到最后一个key对应的indexNodeCache
        CollectionKeyTreeMapNodeCache<E, T> indexNodeCache = keyCode2IndexNodeListMap.get(lastkeyCode);
        if (indexNodeCache == null) {
            return null;
        }
        //找到相似且结果集数量最少的集合
        List<CollectionKeyTreeMapNode<E, T>> indexNodeList = indexNodeCache.getSimilarIndexNode(sortKeyList);
        if (CollectionUtils.isEmpty(indexNodeList)) {
            return null;
        }

        T rlt = null;
        for (CollectionKeyTreeMapNode<E, T> indexNode : indexNodeList) {
            rlt = indexNode.getOneFatherCollection(sortKeyList);
            if (rlt != null) {
                break;
            }
        }
        return rlt;
    }

    /**
     * 添加数据
     *
     * @param value
     * @return
     */
    public boolean put(Collection<E> key, T value) {
        if (value == null) {
            return false;
        }
        List<E> sortKeyList = getSortKeyList(key);
        List<CollectionKeyTreeMapNode<E, T>> newIndexNodeList = rootNode.addKeyCollectionNode(sortKeyList, 0, value);
        registeIndexNodeList(newIndexNodeList);
        return CollectionUtils.isNotEmpty(newIndexNodeList);

    }

    /**
     * 移除数据
     *
     * @param key
     * @return
     */
    public boolean remove(Collection<E> key) {
        List<E> sortKeyList = getSortKeyList(key);
        List<CollectionKeyTreeMapNode<E, T>> rlt = rootNode.removeKeyCollectionNode(sortKeyList);
        logOutIndexNodeList(rlt);
        return CollectionUtils.isNotEmpty(rlt);
    }

    /**
     * 获取所有value
     *
     * @return
     */
    public List<T> getValues() {
        return rootNode.getAllData();
    }


    /**
     * 删除的节点，要注销掉在树中的缓存
     *
     * @param indexNodeList 删除的节点的集合
     */
    private void logOutIndexNodeList(List<CollectionKeyTreeMapNode<E, T>> indexNodeList) {
        if (CollectionUtils.isNotEmpty(indexNodeList)) {
            for (CollectionKeyTreeMapNode<E, T> indexNode : indexNodeList) {
                logOutIndexNode(indexNode);
            }
        }
    }

    private void logOutIndexNode(CollectionKeyTreeMapNode<E, T> indexNode) {
        if (indexNode != null) {
            CollectionKeyTreeMapNodeCache<E, T> indexNodeCache = keyCode2IndexNodeListMap.get(indexNode.getKey());
            indexNodeCache.remove(indexNode);
        }
    }

    /**
     * 新增的节点在树的缓存中注册
     *
     * @param newIndexNodeList 新增的节点集合
     */
    private void registeIndexNodeList(List<CollectionKeyTreeMapNode<E, T>> newIndexNodeList) {
        if (CollectionUtils.isNotEmpty(newIndexNodeList)) {
            for (CollectionKeyTreeMapNode<E, T> newIndexNode : newIndexNodeList) {
                registeIndexNode(newIndexNode);
            }
        }
    }

    private void registeIndexNode(CollectionKeyTreeMapNode<E, T> newIndexNode) {
        if (newIndexNode != null) {
            E nodeKey = newIndexNode.getKey();
            CollectionKeyTreeMapNodeCache<E, T> indexNodeCache = keyCode2IndexNodeListMap.get(nodeKey);
            if (indexNodeCache == null) {
                indexNodeCache = new CollectionKeyTreeMapNodeCache<>(nodeKey);
                keyCode2IndexNodeListMap.put(nodeKey, indexNodeCache);
            }
            indexNodeCache.add(newIndexNode);
        }
    }

    /**
     * 对keyCodeSet进行排序后返回
     * 按照keycode从小到大排序
     *
     * @param keyCodeSet
     * @return
     */
    private List<E> getSortKeyList(Collection<E> keyCodeSet) {
        List<E> sortKeyList = new ArrayList<>(keyCodeSet);
        sortKeyList.sort(Comparator.comparing(Object::toString));
        return sortKeyList;
    }

    public static void main(String[] args) throws IOException {
        test2();
    }

    private static void test() throws IOException {
        CollectionKeyTreeMap<String, Set<String>> tree = new CollectionKeyTreeMap<>();

        Set<String> skuSet1 = new HashSet<>();
        skuSet1.add("sku1");
        skuSet1.add("sku3");
        skuSet1.add("sku7");
        skuSet1.add("sku2");
        Set<String> skuSet2 = new HashSet<>();
        skuSet2.add("sku1");
        skuSet2.add("sku4");
        skuSet2.add("sku6");
        skuSet2.add("sku2");
        Set<String> skuSet3 = new HashSet<>();
        skuSet3.add("sku8");
        skuSet3.add("sku3");
        skuSet3.add("sku7");
        skuSet3.add("sku2");
        Set<String> skuSet4 = new HashSet<>();
        skuSet4.add("sku3");
        skuSet4.add("sku7");
        skuSet4.add("sku5");
        Set<String> skuSet5 = new HashSet<>();
        skuSet5.add("sku3");
        skuSet5.add("sku5");
        Set<String> skuSet6 = new HashSet<>();
        skuSet6.add("sku3");
        skuSet6.add("sku7");

        System.out.println(tree.put(skuSet1, skuSet1));
        System.out.println(tree.put(skuSet2, skuSet2));
        System.out.println(tree.put(skuSet3, skuSet3));
        System.out.println(tree.put(skuSet4, skuSet4));
        System.out.println(tree.put(skuSet5, skuSet5));
        System.out.println(tree.put(skuSet6, skuSet6));

        System.out.println("getAllData:" + tree.getValues());

        //查找fatherCollection的所有子集
        Set<String> fatherCollection = new HashSet<>();
        fatherCollection.add("sku1");
        fatherCollection.add("sku2");
        fatherCollection.add("sku3");
        fatherCollection.add("sku7");
        fatherCollection.add("sku5");
        fatherCollection.add("sku8");
        List<Set<String>> allChildCollection = tree.getAllChildCollection(fatherCollection);
        System.out.println(allChildCollection);
        //查找childCollection的一个父集
        Set<String> childCollection = new HashSet<>();
        childCollection.add("sku1");
        childCollection.add("sku2");
        childCollection.add("sku3");
        Set<String> oneFatherCollection = tree.getOneFatherCollection(childCollection);
        System.out.println(oneFatherCollection);
        System.out.println(tree.remove(skuSet2));
        System.out.println(tree.remove(skuSet3));
    }

    private static void test2() throws IOException {
        Set<Set<String>> keySetList = getKeySets();

        System.out.println(keySetList.size());

        List<Set<String>> findChilds = new ArrayList<>();
        Long createTreeStarTiem = System.currentTimeMillis();
        CollectionKeyTreeMap<String, Set<String>> tree = new CollectionKeyTreeMap();

        for (Set<String> stringSet : keySetList) {
            List<Set<String>> allChildCollection = tree.getAllChildCollection(stringSet);
            Set<String> oneFatherCollection = tree.getOneFatherCollection(stringSet);

            if (oneFatherCollection != null) {
                findChilds.add(stringSet);
            } else if (CollectionUtils.isNotEmpty(allChildCollection)) {
                findChilds.addAll(allChildCollection);
                allChildCollection.forEach(key -> tree.remove(key));
                tree.put(stringSet, stringSet);
            } else {
                tree.put(stringSet, stringSet);
            }
        }
        System.out.println("run time : " + (System.currentTimeMillis() - createTreeStarTiem) + "ms");
        List<Set<String>> values = tree.getValues();
        System.out.println(tree.getValues().size());
        System.out.println("findChilds：" + findChilds.size());
        boolean success = true;
        //校验结果集中是否有父子集关系
        createTreeStarTiem = System.currentTimeMillis();
        A:
        for (int i = 0; i < values.size(); i++) {
            if (i % 1000 == 0) {
                System.out.println(i);
            }
            Set<String> stringSet = values.get(i);
            for (int j = i + 1; j < values.size(); j++) {
                Set<String> stringSet2 = values.get(j);
                if (stringSet.containsAll(stringSet2) || stringSet2.containsAll(stringSet)) {
                    success = false;
                    break A;
                }
            }
        }
        System.out.println("run time : " + (System.currentTimeMillis() - createTreeStarTiem) + "ms");
        System.out.println(success);

        //校验子集中能否在结果集中找到父集
        success = true;
        createTreeStarTiem = System.currentTimeMillis();
        for (int i = 0; i < findChilds.size(); i++) {
            if (i % 1000 == 0) {
                System.out.println(i);
            }
            Set<String> stringSet = findChilds.get(i);
            boolean findfather = false;
            for (int j = 0; j < values.size(); j++) {
                Set<String> stringSet2 = values.get(j);
                if (stringSet2.containsAll(stringSet)) {
                    findfather = true;
                    break;
                }
            }
            if (!findfather) {
                Set<String> oneFatherCollection = tree.getOneFatherCollection(stringSet);
                System.out.println(oneFatherCollection);
                System.out.println(stringSet);
                System.out.println(oneFatherCollection.containsAll(stringSet));
                success = false;
                break;
            }
        }
        System.out.println("run time : " + (System.currentTimeMillis() - createTreeStarTiem) + "ms");
        System.out.println(success);

    }

    private static Set<Set<String>> getKeySets() {
        Set<Set<String>> skuSetList = new HashSet<>(50000);
        for (int i = 0; i < 30000; i++) {
            int skuSetSize = (int) (Math.random() * 9 + 1);
            Set<String> skuSet = new HashSet<>();
            for (int j = 0; j < skuSetSize; j++) {
                int skuCode = (int) (Math.random() * 1000);
                skuSet.add(String.valueOf(skuCode));
            }
            skuSetList.add(skuSet);
        }
        return skuSetList;
    }

}
