package com.example.springdemo.a02;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author leelixiangjun
 * @date 2022/12/20 20:41
 */
public class TestBeanFactory {

    public static void main(String[] args) {
        // 定义beanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        // 定义bean(class, scope,初始化，销毁)
        AbstractBeanDefinition singleton = BeanDefinitionBuilder.genericBeanDefinition(Config.class).setScope("singleton").getBeanDefinition();
        // 注册BeanDefinition
        beanFactory.registerBeanDefinition("config", singleton);
        for (String name : beanFactory.getBeanDefinitionNames()) {
            System.out.println(name);
        }
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>");

        // 给 BeanFactory 添加一些常用的后处理器, 【只是在beanFactory中添加了后处理器】
        AnnotationConfigUtils.registerAnnotationConfigProcessors(beanFactory);
        for (String name : beanFactory.getBeanDefinitionNames()) {
            System.out.println(name);
        }
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>");

        // BeanFactory 后处理器，补充了一些 bean 定义
        beanFactory.getBeansOfType(BeanFactoryPostProcessor.class).values()
                .forEach(beanFactoryPostProcessor -> beanFactoryPostProcessor.postProcessBeanFactory(beanFactory));
        for (String name : beanFactory.getBeanDefinitionNames()) {
            System.out.println(name);
        }
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>");

        // @Autowired 并没有解析，需要Bean后处理器去解析
//        System.out.println(beanFactory.getBean(Bean1.class).getBean2());
        // Bean后处理器，针对 bean 的生命周期的各个阶段提供扩展，例如@Autowired @Resource... 【建立beanFactory与后处理器之间的联系】
        beanFactory.getBeansOfType(BeanPostProcessor.class).values().forEach(beanFactory::addBeanPostProcessor);
        for (String name : beanFactory.getBeanDefinitionNames()) {
            System.out.println(name);
        }
        System.out.println("-------------------------------------");
        // 延时去实例化对象的，只有用到了才调用构造方法。在使用之前，在beanFactory中只存储的bean的定义
        System.out.println(beanFactory.getBean(Bean1.class).getBean2());
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>");

        // 准备好所有单例
        beanFactory.preInstantiateSingletons();

        /*
        总结:
            a beanFactory 不会做的事
                1.不会主动调用 BeanFactory 后处理器
                2.不会主动调用 Bean 后处理器
                3.不会初始化单例
                4.不会解析BeanFactory，还不会解析${}, #{}
             ↑ ApplicationContext会把上述的事情做好。
            b bean 后处理器会有排序的逻辑
         */



    }

    @Configuration
    static class Config {

        @Bean
        public Bean1 bean1() {
            return new Bean1();
        }

        @Bean
        public Bean2 bean2() {
            return new Bean2();
        }

    }

    static class Bean1 {

        public static final Logger log = LoggerFactory.getLogger(Bean1.class);

        private Bean1 bean1;

        @Autowired
        private Bean2 bean2;

        public Bean1() {
            log.info("构造 Bean1()");
        }

        public Bean2 getBean2() {
            return bean2;
        }
    }

    static class Bean2 {

        public static final Logger log = LoggerFactory.getLogger(Bean2.class);

        private Bean2 bean2;

        public Bean2() {
            log.info("构造 Bean2()");
        }

    }
}
