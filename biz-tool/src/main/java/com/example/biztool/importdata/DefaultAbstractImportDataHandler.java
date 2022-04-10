package com.example.biztool.importdata;

import java.io.File;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

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
 * @date : 2022/4/10 19:59
 * @author: linzhou
 * @description : DefaultAbstractImportDataHandler
 */
public abstract class DefaultAbstractImportDataHandler<T extends BaseImportData>  extends AbstractImportDataHandler<ImportRecord, T> {

    /**
     * 获取导入数据包装类对象
     *
     * @return
     */
    @Override
    protected Class<T> getImportDataClass() {
        Type genericSuperclass1 = getClass().getGenericSuperclass();
        ParameterizedType genericSuperclass = (ParameterizedType) genericSuperclass1;
        Type[] actualTypeArguments = genericSuperclass.getActualTypeArguments();
        return (Class<T>) actualTypeArguments[0];
    }

    /**
     * 获取导入记录类对象
     *
     * @return
     */
    @Override
    protected Class getImportRecordClass() {
        return ImportRecord.class;
    }

    @Override
    protected Long saveImportRecord(ImportDataContext context, ImportRecord importRecord) {
        importRecord.setId(1L);
        return importRecord.getId();
    }

    @Override
    protected String uploadFailFile(File errorFile) {
        return "null";
    }

    @Override
    protected void updateImportRecord(ImportRecord importRecord) {
    }
}
