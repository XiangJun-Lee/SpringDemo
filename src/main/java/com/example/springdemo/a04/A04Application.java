package com.example.springdemo.a04;

import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.boot.context.properties.ConfigurationPropertiesBindingPostProcessor;
import org.springframework.context.annotation.CommonAnnotationBeanPostProcessor;
import org.springframework.context.annotation.ContextAnnotationAutowireCandidateResolver;
import org.springframework.context.support.GenericApplicationContext;

/**
 * @author leelixiangjun
 * @date 2022/12/21 16:56
 */
public class A04Application {
    public static void main(String[] args) {
        // 👇 GenericApplicationContext 是一个【干净】的容器
        GenericApplicationContext context = new GenericApplicationContext();
        // 👇 用原始方法注册三个bean
        context.registerBean("bean1", Bean1.class);
        context.registerBean("bean2", Bean2.class);
        context.registerBean("bean3", Bean3.class);

        context.registerBean("bean4", Bean4.class);

        // 👇 设置一个自动装配候选者解析器，默认的无法解析@Value
        context.getDefaultListableBeanFactory().setAutowireCandidateResolver(new ContextAnnotationAutowireCandidateResolver());
        // 👇 解析 @Autowired @Value【执行时机：依赖注入】
        context.registerBean(AutowiredAnnotationBeanPostProcessor.class);
        // 👇 解析 @Resource【执行时机：依赖注入】 @PostConstruct【执行时机：初始化之前】 @PreDestroy【执行时机：销毁之前】
        context.registerBean(CommonAnnotationBeanPostProcessor.class);
        // 👇 解析 @ConfigurationProperties【执行时机：依赖注入】
        ConfigurationPropertiesBindingPostProcessor.register(context.getDefaultListableBeanFactory());

        // 👇 初始化容器
        context.refresh(); // 执行beanFactory后处理器，添加bean后处理器，初始化所有单例
        System.out.println(context.getBean(Bean1.class));
        System.out.println(context.getBean(Bean4.class));
        // 👇销毁容器
        context.close();

        /*
            总结：
                1. @Autowired 等注解的解析属于 bean 生命周期阶段(依赖注入，初始化)的扩展功能
                2. 这些扩展功能由于 bean 后处理来完成
         */
    }
}
