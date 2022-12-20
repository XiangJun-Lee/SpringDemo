package com.example.springdemo.a01;

import org.springframework.context.ApplicationEvent;

/**
 * @author leelixiangjun
 * @date 2022/8/2 22:56
 */
public class UserRegisteredEvent extends ApplicationEvent {
    public UserRegisteredEvent(Object source) {
        super(source);
    }
}
