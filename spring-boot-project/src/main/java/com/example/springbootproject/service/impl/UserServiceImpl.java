package com.example.springbootproject.service.impl;

import com.example.springbootproject.entity.User2Entity;
import com.example.springbootproject.entity.UserEntity;
import com.example.springbootproject.mapper.UserMapper;
import com.example.springbootproject.service.UserService;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;

/**
 * ////////////////////////////////////////////////////////////////////
 * //                          _ooOoo_                               //
 * //                         o8888888o                              //
 * //                         88" . "88                              //
 * //                         (| ^_^ |)                              //
 * //                         O\  =  /O                              //
 * //                      ____/`---'\____                           //
 * //                    .'  \\|     |//  `.                         //
 * //                   /  \\|||  :  |||//  \                        //
 * //                  /  _||||| -:- |||||-  \                       //
 * //                  |   | \\\  -  /// |   |                       //
 * //                  | \_|  ''\---/''  |   |                       //
 * //                  \  .-\__  `-`  ___/-. /                       //
 * //                ___`. .'  /--.--\  `. . ___                     //
 * //              ."" '<  `.___\_<|>_/___.'  >'"".                  //
 * //            | | :  `- \`.;`\ _ /`;.`/ - ` : | |                 //
 * //            \  \ `-.   \_ __\ /__ _/   .-` /  /                 //
 * //      ========`-.____`-.___\_____/___.-`____.-'========         //
 * //                           `=---='                              //
 * //      ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^        //
 * //         佛祖保佑           永无BUG           永不修改              //
 * //          佛曰:                                                  //
 * //                 写字楼里写字间，写字间里程序员;                      //
 * //                 程序人员写程序，又拿程序换酒钱.                      //
 * //                 酒醒只在网上坐，酒醉还来网下眠;                      //
 * //                 酒醉酒醒日复日，网上网下年复年.                      //
 * //                 但愿老死电脑间，不愿鞠躬老板前;                      //
 * //                 奔驰宝马贵者趣，公交自行程序员.                      //
 * //                 别人笑我忒疯癫，我笑自己命太贱;                      //
 * //                 不见满街漂亮妹，哪个归得程序员?                      //
 * ////////////////////////////////////////////////////////////////////
 *
 * @date : 2022/3/10 20:30
 * @author: linzhou
 * @description : UserServiceImpl
 */
@Service
@Log4j2
public class UserServiceImpl implements UserService , InitializingBean, DisposableBean {
    @Autowired
    private UserMapper userMapper;

    @PostConstruct
    public void postConstruct(){
        log.info("UserServiceImpl----->postConstruct");
    }


    @PreDestroy
    public void preDestroy(){
        log.info("UserServiceImpl----->preDestroy");
    }


    @Override
    public void destroy() throws Exception {
        log.info("UserServiceImpl----->DisposableBean.destroy");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("UserServiceImpl----->InitializingBean.afterPropertiesSet");
    }

    @Override
    @Transactional
    public int setUser(String name) {
        UserEntity userEntity = new UserEntity();
        userEntity.setName(name);
        return userMapper.insert(userEntity);
    }

    @Override
    @Transactional
    public int setUser2(String name) {
        UserEntity userEntity = new UserEntity();
        userEntity.setName(name);
        userMapper.insert(userEntity);

        throw new NullPointerException();
    }

    @Override
    public List<Long> selectByName(String name) {
        return userMapper.selectByName(name);
    }

}
