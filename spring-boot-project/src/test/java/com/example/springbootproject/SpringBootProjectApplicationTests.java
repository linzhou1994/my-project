package com.example.springbootproject;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;



@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = SpringBootProjectApplication.class)
@AutoConfigureMockMvc
class SpringBootProjectApplicationTests {

    @Autowired
    DataSourceTransactionManager dataSourceTransactionManager;
    @Autowired
    TransactionDefinition transactionDefinition;

    @Test
    @Rollback(false)
    @Transactional(rollbackFor = NullPointerException.class)
    public void contextLoads() {

        //手动开启事务！
        TransactionStatus transactionStatus = dataSourceTransactionManager.getTransaction(transactionDefinition);
        //提交
        dataSourceTransactionManager.commit(transactionStatus);
        //最好是放在catch 里面,防止程序异常而事务一直卡在哪里未提交
        dataSourceTransactionManager.rollback(transactionStatus);

    }

}
