package com.example.springbootproject.controller;

import com.example.springbootproject.entity.User2Entity;
import com.example.springbootproject.entity.UserEntity;
import com.example.springbootproject.mapper.UserMapper;
import com.example.springbootproject.service.UserService;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

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
 * //         ????????????           ??????BUG           ????????????              //
 * //          ??????:                                                  //
 * //                 ?????????????????????????????????????????????;                      //
 * //                 ?????????????????????????????????????????????.                      //
 * //                 ?????????????????????????????????????????????;                      //
 * //                 ?????????????????????????????????????????????.                      //
 * //                 ?????????????????????????????????????????????;                      //
 * //                 ?????????????????????????????????????????????.                      //
 * //                 ?????????????????????????????????????????????;                      //
 * //                 ??????????????????????????????????????????????                      //
 * ////////////////////////////////////////////////////////////////////
 *
 * @date : 2022/3/10 20:33
 * @author: linzhou
 * @description : UserController
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @RequestMapping("set")
    public int setUser(@RequestParam("name") String name) {

        return userService.setUser(name);
    }
    @RequestMapping("set2")
    public int setUser2(String name) {
        RLock lock = redissonClient.getLock("123456");
        lock.lock();
        try {
            return userService.setUser(name);
        }   finally {
            lock.unlock();
        }
    }
    @RequestMapping("getByName")
    public List<Long> getByName(@RequestParam("name")String name) {
        RLock lock = redissonClient.getLock("123456");
        lock.lock();
        try {
            return userService.selectByName(name);
        }   finally {
            lock.unlock();
        }
    }
}
