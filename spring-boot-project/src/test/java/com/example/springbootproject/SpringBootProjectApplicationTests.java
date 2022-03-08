package com.example.springbootproject;

import com.example.springbootproject.entity.User;
import com.example.springbootproject.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = SpringBootProjectApplication.class)
@AutoConfigureMockMvc
class SpringBootProjectApplicationTests {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private List<DataSource> dataSource;

    @Test
    @Rollback(false)
    @Transactional(rollbackFor = NullPointerException.class)
    public void contextLoads() {

        User user = new User();
        user.setName("user1");
        int insert = userMapper.insert(user);
//        if (insert > 0) {
//            throw new NullPointerException("null");
//        }

        User user2 = new User();
        user2.setName("user2");
        userMapper.insert(user2);
    }

}
