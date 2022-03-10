package com.example.springbootproject.transaction;

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
public class MyDataSourceTransactionManager extends DataSourceTransactionManager {

    public MyDataSourceTransactionManager(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    protected Object doGetTransaction() {
        System.out.println("doGetTransaction:{}"+Thread.currentThread().getId());
        return super.doGetTransaction();
    }

    @Override
    protected void doCommit(DefaultTransactionStatus status) {

        System.out.println("doCommit:{}"+Thread.currentThread().getId());
        super.doCommit(status);
    }

    @Override
    protected void doRollback(DefaultTransactionStatus status) {
        System.out.println("doRollback:{}"+Thread.currentThread().getId());
        super.doRollback(status);
    }
}
