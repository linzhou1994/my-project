package com.example.biztool.importdata;


import java.io.Serializable;
import java.util.Date;

/**
 * 导入记录表
 */
public class ImportRecord implements Serializable {
    /**
     * 主键
     */
    private Long id;

    /**
     * 导入类型 1-员工导入 2-角色员工导入
     */
    private Integer importType;

    /**
     * 上传文件名称
     */
    private String fileName;

    /**
     * 上传文件url
     */
    private String importFileUrl;

    /**
     * 导入数量
     */
    private Integer totalNum;

    /**
     * 成功数量
     */
    private Integer successNum;

    /**
     * 失败数量
     */
    private Integer failNum;

    /**
     * 失败文件名称
     */
    private String failFileName;

    /**
     * 失败文件url
     */
    private String failFileUrl;

    /**
     * 状态 0-处理完毕 1-处理中
     */
    private Byte state;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 修改时间
     */
    private Date gmtModified;

    /**
     * 操作人->企业管理员
     */
    private Long createdBy;

    /**
     * 修改人
     */
    private Long modifiedBy;

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    public Long getId() {
        return id;
    }

    /**
     * 主键
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 导入类型 1-员工导入 2-角色员工导入
     */
    public Integer getImportType() {
        return importType;
    }

    /**
     * 导入类型 1-员工导入 2-角色员工导入
     */
    public void setImportType(Integer importType) {
        this.importType = importType;
    }

    /**
     * 上传文件url
     */
    public String getImportFileUrl() {
        return importFileUrl;
    }

    /**
     * 上传文件url
     */
    public void setImportFileUrl(String importFileUrl) {
        this.importFileUrl = importFileUrl;
    }

    /**
     * 导入数量
     */
    public Integer getTotalNum() {
        return totalNum;
    }

    /**
     * 导入数量
     */
    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    /**
     * 成功数量
     */
    public Integer getSuccessNum() {
        return successNum;
    }

    /**
     * 成功数量
     */
    public void setSuccessNum(Integer successNum) {
        this.successNum = successNum;
    }

    /**
     * 失败数量
     */
    public Integer getFailNum() {
        return failNum;
    }

    /**
     * 失败数量
     */
    public void setFailNum(Integer failNum) {
        this.failNum = failNum;
    }

    /**
     * 失败文件url
     */
    public String getFailFileUrl() {
        return failFileUrl;
    }

    /**
     * 失败文件url
     */
    public void setFailFileUrl(String failFileUrl) {
        this.failFileUrl = failFileUrl;
    }

    /**
     * 状态 0-处理完毕 1-处理中
     */
    public Byte getState() {
        return state;
    }

    /**
     * 状态 0-处理完毕 1-处理中
     */
    public void setState(Byte state) {
        this.state = state;
    }

    /**
     * 创建时间
     */
    public Date getGmtCreate() {
        return gmtCreate;
    }

    /**
     * 创建时间
     */
    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    /**
     * 修改时间
     */
    public Date getGmtModified() {
        return gmtModified;
    }

    /**
     * 修改时间
     */
    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    /**
     * 操作人->企业管理员
     */
    public Long getCreatedBy() {
        return createdBy;
    }

    /**
     * 操作人->企业管理员
     */
    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * 修改人
     */
    public Long getModifiedBy() {
        return modifiedBy;
    }

    /**
     * 修改人
     */
    public void setModifiedBy(Long modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFailFileName() {
        return failFileName;
    }

    public void setFailFileName(String failFileName) {
        this.failFileName = failFileName;
    }
}