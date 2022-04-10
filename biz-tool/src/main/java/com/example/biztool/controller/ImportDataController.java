package com.example.biztool.controller;

import com.example.biztool.exception.BizException;
import com.example.biztool.file.FileUtil;
import com.example.biztool.importdata.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;

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
 * @date : 2022/4/10 20:38
 * @author: linzhou
 * @description : ImportDataController
 */
@RestController
@RequestMapping("/import")
public class ImportDataController {

    @Autowired
    private ImportDataHandlerManage importDataHandlerManage;

    @RequestMapping("/data")
    public void importData() throws BizException, IOException {
        ImportDataHandler importDataHandler = importDataHandlerManage.getImportDataHandler(ImportTypeEnum.TEST);

        RemoteImportDataParam remoteImportDataParam = new RemoteImportDataParam();
        remoteImportDataParam.setFileName("test.xlsx");
        remoteImportDataParam.setImportFileUrl("");
        remoteImportDataParam.setType(ImportTypeEnum.TEST.getType());
        remoteImportDataParam.setUserId(0L);

        Resource tmpResource = new ClassPathResource("importdata/importTest.xls");
        File file = tmpResource.getFile();


        ImportDataContext importDataContext = new ImportDataContext(remoteImportDataParam, FileUtil.getMockMultipartFile(file));

        importDataHandler.createImportRecord(importDataContext);
        importDataHandler.importData(importDataContext);
    }


}
