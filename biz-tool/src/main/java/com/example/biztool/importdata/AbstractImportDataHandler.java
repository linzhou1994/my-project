package com.example.biztool.importdata;


import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.example.biztool.collection.ListSubIterator;
import com.example.biztool.exception.BizException;
import com.example.biztool.math.MathUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author linzhou
 * @ClassName AbsImportDataHandler.java
 * @createTime 2021年11月17日 12:08:00
 * @Description
 */
@Slf4j
public abstract class AbstractImportDataHandler<E extends ImportRecord, T extends BaseImportData> implements ImportDataHandler<E, T> {
    /**
     * 默认批量处理数量
     */
    private static final Integer DEFAULT_BATCH_SIZE = 500;
    /**
     * 不分批处理
     */
    protected static final Integer NOT_BATCH_SIZE = 0;
//
//    @Autowired
//    private ImportDataHandlerManage importDataHandlerManage;


    /**
     * 读取文件页
     *
     * @return
     */
    public int getSheet() {
        return 0;
    }

    /**
     * 从第几行开始读
     *
     * @return
     */
    public int getHeadRowNumber() {
        return 1;
    }

    /**
     * 批量处理数量
     *
     * @return NOT_BATCH_SIZE(0)为不分批处理
     */
    protected int getBatchSize() {
        return DEFAULT_BATCH_SIZE;
    }


    @Override
    public Long createImportRecord(ImportDataContext context) throws BizException, IOException {
        RemoteImportDataParam param = context.getParam();
        E importRecord = createImportRecordObject();
        context.setImportRecord(importRecord);
        //获取导入总数
        setTotalNum(context);
        importRecord.setImportType(context.getTypeEnum().getType());
        importRecord.setImportFileUrl(param.getImportFileUrl());
        importRecord.setTotalNum(context.getTotalNum());
        importRecord.setSuccessNum(0);
        importRecord.setFailNum(0);
        importRecord.setState(ImportStateEnum.LOADING.getType());
        importRecord.setCreatedBy(param.getUserId());
        importRecord.setFailFileUrl("");
        importRecord.setFileName(context.getFileName());

        return saveImportRecord(context, importRecord);
    }


    @Override
    public void importData(ImportDataContext context) throws IOException {
        Class<T> dataClass = getImportDataClass();
        List<T> dataList = getData(context, dataClass);

        cache(context, dataList);
        //计算批量处理数量
        int batchSize = getBatchSize();
        if (batchSize <= NOT_BATCH_SIZE) {
            //如果批量处理数量小于0,则一次处理完,不分批
            batchSize = context.getTotalNum();
        }
//        AbstractImportDataHandler importDataHandler = (AbstractImportDataHandler) importDataHandlerManage.getImportDataHandler(context.getTypeEnum());
        AbstractImportDataHandler<E,T> importDataHandler = this;

        //分批处理导入数据
        ListSubIterator<T> ls = new ListSubIterator<>(dataList, batchSize);
        while (ls.hasNext()) {
            List<T> next = ls.next();
            try {
                //为了走事务切片
                importDataHandler.doImportData(context, next);
            } catch (Exception e) {
                log.error("importData error,type:{},id:{}", context.getTypeEnum().getDesc(), context.getImportRecord().getId(), e);
                setErrorMsg(next);
            } finally {
                doFinally(next);
            }
            refreshProgress(context, next);
        }
        //生成失败文件
        finishImportData(context, dataList, dataClass);
    }


    /**
     * 获取导入数据包装类对象
     *
     * @return
     */
    protected Class<T> getImportDataClass() {
        Type genericSuperclass1 = getClass().getGenericSuperclass();
        ParameterizedType genericSuperclass = (ParameterizedType) genericSuperclass1;
        Type[] actualTypeArguments = genericSuperclass.getActualTypeArguments();
        return (Class<T>) actualTypeArguments[1];
    }

    /**
     * 获取导入记录类对象
     *
     * @return
     */
    protected Class<E> getImportRecordClass() {
        Type genericSuperclass1 = getClass().getGenericSuperclass();
        ParameterizedType genericSuperclass = (ParameterizedType) genericSuperclass1;
        Type[] actualTypeArguments = genericSuperclass.getActualTypeArguments();
        return (Class<E>) actualTypeArguments[0];
    }


    /**
     * 创建导入记录对象
     *
     * @return
     * @throws BizException
     */
    protected E createImportRecordObject() throws BizException {
        try {
            Class<E> importRecordClass = getImportRecordClass();
            return importRecordClass.newInstance();
        } catch (Exception e) {
            throw new BizException("创建导入记录对象失败");
        }
    }


    /**
     * 全局缓存设置
     *
     * @param context  上下文
     * @param dataList 所有数据
     */
    protected void cache(ImportDataContext context, List<T> dataList) {
    }


    /**
     * 导入数据后的处理
     *
     * @param next
     */
    protected void doFinally(List<T> next) {
    }

    /**
     * 设置异常错误信息
     *
     * @param next
     */
    private void setErrorMsg(List<T> next) {
        for (T t : next) {
            if (StringUtils.isBlank(t.getErrorMessage())) {
                t.setErrorMessage("请检查数据");
            }
        }
    }


    /**
     * 结束导入
     *
     * @param context   上下文
     * @param dataList  数据源
     * @param dataClass
     */
    protected void finishImportData(ImportDataContext context, List<T> dataList, Class<T> dataClass) {
        List<T> errorData = dataList.stream().filter(this::failDataFilter)
                .collect(Collectors.toList());
        //保存失败文件地址
        ImportRecord record = context.getImportRecord();
        record.setState(ImportStateEnum.FINISH.getType());
        String exportTemplateFilePath = getExportTemplateFilePath();
        if (CollectionUtils.isNotEmpty(errorData) && StringUtils.isNotBlank(exportTemplateFilePath)) {
            try {
                //如果有失败数据,则创建失败文件
                String fileName = failFileName(context);
                // 指定导出的模版文件
                Resource tmpResource = new ClassPathResource(exportTemplateFilePath);

                // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
                // 如果这里想使用03 则 传入excelType参数即可
                EasyExcel.write(fileName, dataClass)
                        .withTemplate(tmpResource.getInputStream())
                        .sheet()
                        .doFill(errorData);

                File errorFile = new File(fileName);
                // 上传失败文件
                String failFileUrl = uploadFailFile(errorFile);
                errorFile.delete();

                record.setFailFileUrl(failFileUrl);
                String failFileName = context.getFileName().replaceFirst(".", "失败数据.");
                record.setFailFileName(failFileName);
            } catch (Exception e) {
                log.error("importData createFailFile error,id:{}", context.getImportRecord().getId(), e);
            }
        }
        updateImportRecord(record);
    }


    /**
     * 生成失败文件过滤
     *
     * @param t
     * @return
     */
    protected boolean failDataFilter(T t) {
        return StringUtils.isNotBlank(t.getErrorMessage());
    }

    /**
     * 失败文件名称
     *
     * @param context
     * @return
     */
    protected String failFileName(ImportDataContext context) {
        return "失败数据" + System.currentTimeMillis() + ".xlsx";
    }


    /**
     * 计算本次处理的成功数量
     *
     * @param next
     * @return
     */
    protected int getSuccessNum(List<T> next) {
        return next.stream().filter(t -> StringUtils.isBlank(t.getErrorMessage()))
                .collect(Collectors.toList()).size();
    }


    /**
     * 获取数据
     *
     * @param context
     * @param tClass
     * @return
     * @throws IOException
     */
    public List<T> getData(ImportDataContext context, Class<T> tClass) throws IOException {
        MultipartFile file = context.getFile();

        // 因为存在两行表头，所以headRowNumber从2开始
        List<T> dataList = EasyExcelFactory.read(file.getInputStream())
                .sheet(getSheet())
                .headRowNumber(getHeadRowNumber())
                .head(tClass).doReadSync();

        context.setTotalNum(dataList.size());
        return dataList;
    }

    /**
     * 设置总数量
     *
     * @param context
     */
    private void setTotalNum(ImportDataContext context) throws IOException {
        ExcelReader excelReader = EasyExcelFactory.read(context.getFile().getInputStream()).build().read(new ReadSheet(getSheet()));
        int totalNum = excelReader.analysisContext().readRowHolder().getRowIndex() - getHeadRowNumber() + 1;
        context.setTotalNum(totalNum);
    }

    /**
     * 属性进度条
     *
     * @param context
     * @param next
     */
    protected void refreshProgress(ImportDataContext context, List<T> next) {
        //本次处理的数据
        int batchSize = next.size();
        //本次处理的成功数量
        int addSuccessNum = getSuccessNum(next);
        ImportRecord record = context.getImportRecord();
        int successNum = MathUtil.add(record.getSuccessNum(), addSuccessNum);
        int failNum = MathUtil.add(record.getFailNum(), batchSize - addSuccessNum);
        //设置进度
        record.setSuccessNum(successNum);
        record.setFailNum(failNum);

        updateImportRecord(record);
    }

    /**
     * 将导入记录保存到db中
     *
     * @param context
     * @param importRecord
     * @return 导入记录id
     */
    protected abstract Long saveImportRecord(ImportDataContext context, E importRecord);

    /**
     * 逻辑业务处理 保存到db
     *
     * @param context  上下文
     * @param dataList 本次需要处理的数量
     */
    public abstract void doImportData(ImportDataContext context, List<T> dataList);

    /**
     * 上传失败文件
     *
     * @param errorFile
     * @return 失败文件访问地址
     */
    protected abstract String uploadFailFile(File errorFile);

    /**
     * 更新导入记录
     *
     * @param importRecord
     */
    protected abstract void updateImportRecord(ImportRecord importRecord);

    /**
     * 获取失败文件模版路径
     *
     * @return
     */
    protected abstract String getExportTemplateFilePath();

}
