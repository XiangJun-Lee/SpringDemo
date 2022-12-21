package com.example.springdemo.a03;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author leelixiangjun
 * @date 2022/12/21 14:55
 */
@Component
public class LifeCycleBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(LifeCycleBean.class);

    public LifeCycleBean() {
        LOGGER.info("构造 LifeCycleBean()");
    }

    @Autowired
    public void autowired(@Value("${JAVA_HOME}") String name) {
        LOGGER.info("依赖注入 name:{}", name);
    }

    @PostConstruct
    public void init(){
        LOGGER.info("初始化");
    }

    @PreDestroy
    public void destroy(){
        LOGGER.info("销毁");
    }
}
