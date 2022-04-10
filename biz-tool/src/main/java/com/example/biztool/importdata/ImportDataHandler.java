package com.example.biztool.importdata;



import com.example.biztool.exception.BizException;

import java.io.IOException;

/**
 * @author linzhou
 * @ClassName ImportDataHandler.java
 * @createTime 2021年11月03日 19:45:00
 * @Description
 */
public interface ImportDataHandler<E extends ImportRecord, T extends BaseImportData> {
    /**
     * 导入类型
     *
     * @return
     */
    ImportTypeEnum getType();

    /**
     * 保存导入记录
     * @param context
     * @return 导入记录id
     */
    Long createImportRecord(ImportDataContext context) throws BizException, IOException;

    /**
     * 执行导入
     *
     * @param context
     */
    void importData(ImportDataContext context) throws IOException, BizException;
}
