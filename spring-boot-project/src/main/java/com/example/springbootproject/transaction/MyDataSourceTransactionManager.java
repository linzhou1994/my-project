package com.example.springbootproject.transaction;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.DefaultTransactionStatus;

import javax.sql.DataSource;

/**
 * @author linzhou
 * @ClassName MyDataSourceTransactionManager.java
 * @createTime 2022年03月08日 17:47:00
 * @Description
 */
@Component
@Slf4j
public class MyDataSourceTransactionManager extends DataSourceTransactionManager {

    public MyDataSourceTransactionManager(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    protected Object doGetTransaction() {
        log.info("doGetTransaction:{}",Thread.currentThread().getId());
        return super.doGetTransaction();
    }

    @Override
    protected void doCommit(DefaultTransactionStatus status) {

        log.info("doCommit:{}",Thread.currentThread().getId());
        super.doCommit(status);
    }

    @Override
    protected void doRollback(DefaultTransactionStatus status) {
        log.info("doRollback:{}",Thread.currentThread().getId());
        super.doRollback(status);
    }
}
