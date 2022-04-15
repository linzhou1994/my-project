package my.map;

import org.apache.commons.collections4.CollectionUtils;

import java.util.*;

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
 * @date : 2022/4/15 10:52
 * @author: linzhou
 * @description : CollectionKeyTreeMapNode
 */

public class CollectionKeyTreeMapNode<E,T> {

    private CollectionKeyTreeMap<E,T> tree;

    private E key;

    private Set<E> keySet;

    private CollectionKeyTreeMapNode<E,T> fatherNode;

    private Map<E, CollectionKeyTreeMapNode<E,T>> keyCode2childNodeMap = new HashMap<>();

    private T data;

    /**
     * 用于根节点的创建
     *
     * @param tree
     */
    protected CollectionKeyTreeMapNode(CollectionKeyTreeMap<E,T> tree) {
        keySet = new HashSet<>();
        this.tree = tree;
    }

    /**
     * 用于非叶子节点的创建
     *
     * @param tree
     * @param key
     * @param keySet
     * @param fatherNode
     */
    protected CollectionKeyTreeMapNode(CollectionKeyTreeMap<E,T> tree, CollectionKeyTreeMapNode<E,T> fatherNode,
                                       E key, Set<E> keySet) {
        this.tree = tree;
        this.key = key;
        this.keySet = keySet;
        this.fatherNode = fatherNode;
    }

    /**
     * 用于叶子节点的创建
     *
     * @param tree
     * @param key
     * @param keySet
     * @param data
     * @param fatherNode
     */
    protected CollectionKeyTreeMapNode(CollectionKeyTreeMap<E,T> tree, CollectionKeyTreeMapNode<E,T> fatherNode,
                                       E key, Set<E> keySet, T data) {
        this.tree = tree;
        this.key = key;
        this.keySet = keySet;
        this.data = data;
        this.fatherNode = fatherNode;
    }

    /**
     * 向当前节点添加一个data
     * <p>
     * 如果data对应的keyList已经在节点树种存在则会返回false
     *
     * @param keyList data的key集合
     * @param index   已经遍历到keyList的第几个元素
     * @param data
     * @return 本次创建的所有节点集合
     */
    public List<CollectionKeyTreeMapNode<E,T>> addKeyCollectionNode(List<E> keyList, int index, T data) {
        List<CollectionKeyTreeMapNode<E,T>> rlt = new ArrayList<>();
        if (CollectionUtils.isEmpty(keyList) || index >= keyList.size()) {
            return rlt;
        }

        E key = keyList.get(index);
        CollectionKeyTreeMapNode<E,T> childNode = keyCode2childNodeMap.get(key);

        if (keyList.size() == index + 1) {
            //如果index+1等于keyList.size()，则说明已经遍历到最后一个key了，需要增加叶子节点来存储data
            //但是如果childNode！=null，那么久没办法新增一个叶子节点，说明keyList在当前节点所在的树中存在父集，返回false
            //如果childNode == null，则新增一个叶子节点
            if (childNode == null) {
                Set<E> newChildNodekeySet = new HashSet<>(keySet);
                newChildNodekeySet.add(key);
                CollectionKeyTreeMapNode<E,T> newChildNode = new CollectionKeyTreeMapNode<>(tree, this, key, newChildNodekeySet, data);
                addChild0(newChildNode);
                rlt.add(newChildNode);
            }
        } else {
            //如果还没到最后一个key，则继续想当前节点的孩子节点遍历
            if (childNode == null) {
                //如果当前遍历的key没有在当前节点下找到孩子，则新增当前key对应非叶子节点的孩子
                Set<E> newChildNodekeySet = new HashSet<>(keySet);
                newChildNodekeySet.add(key);
                childNode = new CollectionKeyTreeMapNode<>(tree, this, key, newChildNodekeySet);
                addChild0(childNode);
                rlt.add(childNode);
            }
            //向孩子中插入
            List<CollectionKeyTreeMapNode<E,T>> childRlt = childNode.addKeyCollectionNode(keyList, index + 1, data);
            rlt.addAll(childRlt);
        }
        return rlt;
    }

    private void addChild0(CollectionKeyTreeMapNode<E,T> newChildNode) {
        keyCode2childNodeMap.put(newChildNode.getKey(), newChildNode);
    }

    /**
     * 查找当前节点下所有指定集合的子集的数据并返回
     *
     * @param fatherCollection 父集
     * @return 子集集合
     */
    protected List<T> getAllChildCollection(List<E> fatherCollection) {
        List<T> rlt = new ArrayList<>();
        //如果当前是带数据的叶子节点则返回data数据
        if (isDataLeafNode()) {
            rlt.add(data);
        } else {
            if (CollectionUtils.isNotEmpty(fatherCollection)) {
                //如果不是叶子节点则遍历当前节点的孩子节点
                for (int index = 0; index < fatherCollection.size(); index++) {

                    E key = fatherCollection.get(index);
                    CollectionKeyTreeMapNode<E,T> childNode = keyCode2childNodeMap.get(key);

                    if (childNode != null) {
                        List<E> newFatherCollection = fatherCollection.subList(index + 1, fatherCollection.size());
                        rlt.addAll(childNode.getAllChildCollection(newFatherCollection));
                    }
                }
            }
        }
        return rlt;
    }

    /**
     * 查找一个指定集合的父集的数据并返回
     *
     * @param childCollection
     * @return 父集包含的数据（如果找不到则返回null）
     */
    protected T getOneFatherCollection(List<E> childCollection) {
        //判断当前的keySet是childCollection的父集
        if (keySet.containsAll(childCollection)) {
            return getOneKeyCollectionNode();
        } else {
            return null;
        }
    }

    /**
     * 获取当前节点下的任意一个keyCollectionNode并返回
     *
     * @return
     */
    private T getOneKeyCollectionNode() {
        //如果当前节点是叶子节点则返回data
        if (isDataLeafNode()) {
            return data;
        } else {
            //如果不是则递归孩子节点获取data
            List<CollectionKeyTreeMapNode<E,T>> childNodeList = new ArrayList<>(keyCode2childNodeMap.values());
            for (CollectionKeyTreeMapNode<E,T> childNode : childNodeList) {
                T data = childNode.getOneKeyCollectionNode();
                if (data != null) {
                    return data;
                }
            }
        }
        return null;
    }


    /**
     * 删除指定key集合的节点
     *
     * @param keyList
     * @return 返回删除的节点集合（如果返回集合为空则删除失败）
     */
    protected List<CollectionKeyTreeMapNode<E,T>> removeKeyCollectionNode(List<E> keyList) {
        List<CollectionKeyTreeMapNode<E,T>> rlt = new ArrayList<>();
        if (CollectionUtils.isEmpty(keyList)) {
            return rlt;
        }

        E nextChildKey = keyList.get(0);
        CollectionKeyTreeMapNode<E,T> nextChildNode = keyCode2childNodeMap.get(nextChildKey);

        if (nextChildNode != null) {

            if (keyList.size() == 1 && nextChildNode.isDataLeafNode()) {

                CollectionKeyTreeMapNode<E,T> removeChildNode = removeChildNode(nextChildKey);
                rlt.add(removeChildNode);

            } else if (keyList.size() > 1) {

                List<CollectionKeyTreeMapNode<E,T>> childNodeRemoveRlt = nextChildNode.removeKeyCollectionNode(keyList.subList(1, keyList.size()));
                if (CollectionUtils.isNotEmpty(childNodeRemoveRlt)) {
                    rlt.addAll(childNodeRemoveRlt);
                    if (nextChildNode.isLeafNode()) {
                        CollectionKeyTreeMapNode<E,T> removeChildNode = removeChildNode(nextChildKey);
                        rlt.add(removeChildNode);
                    }
                }

            }

        }
        return rlt;
    }

    /**
     * 删除一个指定key的孩子节点
     *
     * @param removeChildNodeKey
     * @return
     */
    private CollectionKeyTreeMapNode<E,T> removeChildNode(E removeChildNodeKey) {
        return keyCode2childNodeMap.remove(removeChildNodeKey);
    }


    protected List<T> getAllData() {
        List<T> rlt = new ArrayList<>();
        if (isLeafNode()){
            if (data!=null){
                rlt.add(data);
            }
        }else {
            Collection<CollectionKeyTreeMapNode<E, T>> childNodes = keyCode2childNodeMap.values();
            for (CollectionKeyTreeMapNode<E, T> childNode : childNodes) {
                rlt.addAll(childNode.getAllData());
            }
        }
        return rlt;
    }

    /**
     * 判断当前节点是否是叶子节点
     *
     * @return
     */
    protected boolean isLeafNode() {
        return keyCode2childNodeMap.isEmpty();
    }

    /**
     * 判断当前节点是否是存储数据的叶子节点
     *
     * @return
     */
    protected boolean isDataLeafNode() {
        return isLeafNode() && data != null;
    }


    protected E getKey() {
        return key;
    }

    protected Set<E> getKeySet() {
        return new HashSet<>(keySet);
    }

    protected T getData() {
        return data;
    }

}