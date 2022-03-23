package com.example.biztool.exception;

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
 * //         佛祖保佑           永无BUG           永不修改             //
 * //          佛曰:                                                  //
 * //                 写字楼里写字间，写字间里程序员;                     //
 * //                 程序人员写程序，又拿程序换酒钱.                     //
 * //                 酒醒只在网上坐，酒醉还来网下眠;                     //
 * //                 酒醉酒醒日复日，网上网下年复年.                     //
 * //                 但愿老死电脑间，不愿鞠躬老板前;                     //
 * //                 奔驰宝马贵者趣，公交自行程序员.                     //
 * //                 别人笑我忒疯癫，我笑自己命太贱;                     //
 * //                 不见满街漂亮妹，哪个归得程序员?                     //
 * ////////////////////////////////////////////////////////////////////
 *
 * @date : 2021/12/12 23:53
 * @author: linzhou
 * @description : BizException
 */
public class BizException extends Exception{
    private String code;
    private String msg;

    public BizException(String message) {
        super(message);
    }

    public String getCode() {
        return code;
    }

    public BizException setCode(String code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public BizException setMsg(String msg) {
        this.msg = msg;
        return this;
    }
}
