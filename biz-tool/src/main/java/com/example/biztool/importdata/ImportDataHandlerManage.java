package com.example.biztool.importdata;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author linzhou
 * @ClassName ImportDataHandlerManage.java
 * @createTime 2021年11月03日 19:52:00
 * @Description
 */
@Service
public class ImportDataHandlerManage {

    @Autowired
    private List<ImportDataHandler> handlerList;

    private Map<Integer, ImportDataHandler> type2handlerMap;

    @PostConstruct
    public void init() {
        type2handlerMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(handlerList)){
            for (ImportDataHandler importDataHandler : handlerList) {
                type2handlerMap.put(importDataHandler.getType().getType(), importDataHandler);
            }
        }
    }


    public ImportDataHandler getImportDataHandler(ImportTypeEnum type) {
        if (type == null) {
            return null;
        }
        return getImportDataHandler(type.getType());
    }

    public ImportDataHandler getImportDataHandler(Integer type) {
        if (type == null) {
            return null;
        }
        return type2handlerMap.get(type);
    }

}
