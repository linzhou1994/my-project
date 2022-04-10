package com.example.biztool.importdata;


import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author linzhou
 * @ClassName ImportDataContext.java
 * @createTime 2021年11月03日 19:47:00
 * @Description 导入上下文
 */
@Getter
@Setter
public class ImportDataContext {

    private RemoteImportDataParam param;
    /**
     * 导入文件
     */
    private MultipartFile file;

    /**
     * 导入总行数量
     */
    private Integer totalNum;
    /**
     * 全局数据缓存
     */
    private Object cache;
    /**
     * 导入记录
     */
    private ImportRecord importRecord;

    public ImportDataContext(RemoteImportDataParam param, MultipartFile file) throws IOException {
        this.param = param;
        this.file = file;

    }

    public String getImportFileUrl() {
        return param.getImportFileUrl();
    }

    public ImportTypeEnum getTypeEnum() {
        return ImportTypeEnum.getImportDataTypeEnum(param.getType());
    }

    public Integer getTotalNum() {
        return totalNum == null ? 0 : totalNum;
    }

    public String getFileName() {
        return param.getFileName();
    }

    public Long getUserId() {
        return param.getUserId();
    }

    public Object getCache() {
        return cache;
    }

    public void setCache(Object cache) {
        this.cache = cache;
    }


    public <T extends ImportRecord> T getImportRecord() {
        return (T) importRecord;
    }

    public void setImportRecord(ImportRecord importRecord) {
        this.importRecord = importRecord;
    }
}
