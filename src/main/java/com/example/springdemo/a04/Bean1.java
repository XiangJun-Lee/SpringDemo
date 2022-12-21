package com.example.springdemo.a04;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

/**
 * @author leelixiangjun
 * @date 2022/12/21 16:57
 */
public class Bean1 {

    private static final Logger log = LoggerFactory.getLogger(Bean1.class);

    private Bean2 bean2;

    @Autowired
    private Bean3 bean3;

    private String home;

    @Autowired
    public void setBean2(Bean2 bean2) {
        log.info("@Autowired 生效：{}", bean2);
        this.bean2 = bean2;
    }

    @Autowired
    public void setHome(@Value("${JAVA_HOME}") String home) {
        log.info("@Value 生效: {}", home);
        this.home = home;
    }

    @Resource
    public void setBean3(Bean3 bean3) {
        log.info("@Resource 生效:{}", bean3);
        this.bean3 = bean3;
    }

    @PostConstruct
    public void init() {
        log.info("@PostConstruct 生效");
    }

    @PreDestroy
    public void destroy() {
        log.info("@PreDestroy 生效");
    }


    @Override
    public String toString() {
        return "Bean1{" +
                "bean2=" + bean2 +
                ", bean3=" + bean3 +
                ", home='" + home + '\'' +
                '}';
    }

}
