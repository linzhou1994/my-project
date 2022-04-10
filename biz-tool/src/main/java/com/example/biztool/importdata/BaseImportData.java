package com.example.biztool.importdata;

import com.alibaba.excel.annotation.ExcelProperty;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * @author linzhou
 * @ClassName BaseImportData.java
 * @createTime 2021年11月18日 13:45:00
 * @Description 导入基础类
 */
public class BaseImportData implements Serializable {
    @ExcelProperty("失败原因")
    private String errorMessage;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        if(StringUtils.isBlank(this.errorMessage)){
            this.errorMessage = errorMessage;
        }
    }
}
