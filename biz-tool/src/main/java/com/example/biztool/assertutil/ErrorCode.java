package com.example.biztool.assertutil;

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
 * @date : 2021/12/12 23:52
 * @author: linzhou
 * @description : ErrorCode
 */
public class ErrorCode {
    public static final ErrorCode DEFAULT_ERROR= new ErrorCode("000000","系统未知异常");
    public static final ErrorCode REQUEST_FREQUENTLY= new ErrorCode("999999","请求过于频繁");
    private String code;
    private String msg;

    public ErrorCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
