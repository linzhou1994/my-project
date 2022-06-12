package strategy.manager;



import strategy.SaveOrderStrategy;
import strategy.factory.SaveOrderStrategyFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
 * @date : 2022/6/11 14:10
 * @author: linzhou
 * @description : SaveOrderStrategyManager
 */
public class SaveOrderStrategyManager {
    private static final Map<Integer, SaveOrderStrategy<?, ?>> SAVE_ORDER_STRATEGY_MAP = new HashMap<>();


    public static SaveOrderStrategy<?, ?> getSaveOrderStrategy(int type) {
        SaveOrderStrategy<?, ?> saveOrderStrategy = SAVE_ORDER_STRATEGY_MAP.get(type);
        if (Objects.isNull(saveOrderStrategy)) {
            saveOrderStrategy = SaveOrderStrategyFactory.getSaveOrderStrategy(type);
            SAVE_ORDER_STRATEGY_MAP.put(type, saveOrderStrategy);
        }
        return saveOrderStrategy;
    }
}
