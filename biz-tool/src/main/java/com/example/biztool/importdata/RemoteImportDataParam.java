package com.example.biztool.importdata;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author linzhou
 * @ClassName RemoteImportDataParam.java
 * @createTime 2021年11月03日 19:39:00
 * @Description
 */
@Getter
@Setter
public class RemoteImportDataParam implements Serializable {
    private static final long serialVersionUID = -6304257586291956986L;
    /**
     * 文件名称
     */
    private String fileName;
    /**
     * 文件地址
     */
    private String importFileUrl;
    /**
     * 导入类型
     * @see ImportTypeEnum
     */
    private Integer type;

    /**
     * 操作人id
     */
    private Long userId;

}
