package my.map;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
 * @date : 2022/4/15 10:53
 * @author: linzhou
 * @description : CollectionKeyTreeMapNodeCache
 */

public class CollectionKeyTreeMapNodeCache<E,T> {

    private E key;

    private ListMultimap<E, CollectionKeyTreeMapNode<E, T>> key2MultiItemKeyCollectionIndexNodeCache;

    protected CollectionKeyTreeMapNodeCache(E keyKey) {
        this.key = keyKey;
        init();
    }

    protected void init() {
        key2MultiItemKeyCollectionIndexNodeCache = ArrayListMultimap.create();
    }

    protected void add(CollectionKeyTreeMapNode<E, T> indexNode) {
        if (indexNode != null) {
            Set<E> keySet = indexNode.getKeySet();
            for (E key : keySet) {
                key2MultiItemKeyCollectionIndexNodeCache.put(key, indexNode);
            }
        }
    }

    protected void remove(CollectionKeyTreeMapNode<E, T> indexNode) {
        if (indexNode != null) {
            Set<E> keySet = indexNode.getKeySet();
            for (E key : keySet) {
                key2MultiItemKeyCollectionIndexNodeCache.remove(key, indexNode);
            }
        }
    }

    protected List<CollectionKeyTreeMapNode<E, T>> getSimilarIndexNode(Collection<E> keySet) {
        if (CollectionUtils.isEmpty(keySet)) {
            return null;
        }
        List<CollectionKeyTreeMapNode<E, T>> rlt = null;
        Set<E> keySetCopy = new HashSet<>(keySet);
        for (E key : keySetCopy) {
            List<CollectionKeyTreeMapNode<E, T>> keyRlt = key2MultiItemKeyCollectionIndexNodeCache.get(key);
            if (rlt == null || keyRlt.size() < rlt.size()) {
                rlt = keyRlt;
            }
        }
        return rlt;
    }

}

