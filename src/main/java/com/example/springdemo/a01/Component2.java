package com.example.springdemo.a01;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author leelixiangjun
 * @date 2022/8/1 23:42
 */
@Component
public class Component2 {

    private static final Logger log = LoggerFactory.getLogger(Component2.class);

    @EventListener
    public void register(UserRegisteredEvent event){
        log.info("{}",event);
        log.info("注册成功");
        UserDao source = (UserDao) event.getSource();
        if (source.getMessageType().equals(2)){
            log.info("【邮件】用户注册成功，用户信息{}",source);
        }
    }
}
