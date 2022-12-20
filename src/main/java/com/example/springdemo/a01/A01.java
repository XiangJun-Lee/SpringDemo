package com.example.springdemo.a01;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultSingletonBeanRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Locale;
import java.util.Map;

/**
 * @author leelixiangjun
 * @date 2022/8/1 23:23
 */
@SpringBootApplication
public class A01 {

    private static final Logger log = LoggerFactory.getLogger(A01.class);

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException, IOException {
        ConfigurableApplicationContext context = SpringApplication.run(A01.class, args);

        /*
         1.到底什么是BeanFactory
          - TA 是 ApplicationContext的父接口
          - TA 是 Spring的核心容器，主要的 ApplicationContext 实现都[组合]了他的功能
         */
        System.out.println(context);

        /*
         2. BeanFactory 能干点什么
            - 表面上只有 getBean
            - 实际上 控制反转，基本的依赖注入， 以及 Bean 的生命周期的各种功能，都由它的实现类提供。
         */
        Field singletonObjects = DefaultSingletonBeanRegistry.class.getDeclaredField("singletonObjects");
        singletonObjects.setAccessible(true);
        ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
        Map<String, Object> map = (Map<String, Object>) singletonObjects.get(beanFactory);
        map.entrySet().stream().filter(e->e.getKey().startsWith("component"))
                .forEach(e -> System.out.println(e.getKey() + "=" + e.getValue()));
        System.out.println("-----------------------------------------");

        /*
         3. ApplicationContext 比 BeanFactory 多什么
         */
        // 3.1 MessageSource 国际化支持
        System.out.println(context.getMessage("hi", null, Locale.CHINA));
        System.out.println(context.getMessage("hi", null, Locale.ENGLISH));
        System.out.println(context.getMessage("hi", null, Locale.JAPANESE));
        System.out.println("-----------------------------------------");

        // 3.2 ResourcePatternResolver 用于解析资源文件的策略接口
        Resource resource = context.getResource("classpath:application.properties");
        System.out.println(IOUtils.toString(resource.getInputStream(),"utf-8"));
        Resource[] resources = context.getResources("classpath*:META-INF/spring.factories");
        for (Resource rs : resources) {
            System.out.println(IOUtils.toString(rs.getInputStream(),"utf-8"));
        }
        System.out.println("-----------------------------------------");

        // 3.3 EnvironmentCapable 处理环境信息 不区分大小写
        System.out.println(context.getEnvironment().getProperty("java_home"));
        System.out.println(context.getEnvironment().getProperty("server.port"));
        System.out.println("-----------------------------------------");

        // 3.4 ApplicationEventPublisher 异步的事件处理方式
        context.getBean(Component1.class).userRegister();

        /*
         4. 总结
           - BeanFactory 与 ApplicationContext 并不仅仅是简单的接口继承关系，ApplicationContext 组合并扩展了 BeanFactory 的功能
           - 使用 ApplicationContext 中的 ApplicationEventPublisher 可以实现代码之间的解耦
         */

        /*
         练习： 使用 事件方式 和 AOP 方式 分别时间 用户注册 与注册成功之间的接口
         */
    }
}
