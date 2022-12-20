package com.example.springdemo.a01;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author leelixiangjun
 * @date 2022/8/1 23:42
 */
@Component
public class Component1 {

    private static final Logger log = LoggerFactory.getLogger(Component1.class);

    @Resource
    private ConfigurableApplicationContext context;

    public void userRegister(){
        log.info("用户注册");
        UserDao user = new UserDao();
        user.setId(0);
        user.setName("lee");
        user.setPhone(1234567890L);
        user.setMessageType(2);
        context.publishEvent(new UserRegisteredEvent(user));
    }
}
