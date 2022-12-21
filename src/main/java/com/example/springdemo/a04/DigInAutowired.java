package com.example.springdemo.a04;

import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.annotation.InjectionMetadata;
import org.springframework.beans.factory.config.DependencyDescriptor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.ContextAnnotationAutowireCandidateResolver;
import org.springframework.core.MethodParameter;
import org.springframework.core.env.StandardEnvironment;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author leelixiangjun
 * @date 2022/12/21 17:43
 * @desc AutowiredAnnotationBeanPostProcessor 运行分析
 */
public class DigInAutowired {
    public static void main(String[] args) throws Throwable {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        // 👇 默认入参中的对象就是成品的实例化对象，不会在进行构造过程，初始化过程，依赖注入过程
        beanFactory.registerSingleton("bean2", new Bean2());
        beanFactory.registerSingleton("bean3", new Bean3());
        // 👇 保证能解析@Value
        beanFactory.setAutowireCandidateResolver(new ContextAnnotationAutowireCandidateResolver());
        // 👇 ${} 解析器
        beanFactory.addEmbeddedValueResolver(new StandardEnvironment()::resolvePlaceholders);


        // 1. 查找哪些属性/方法加了@Autowired，这称之为InjectionMetaData
        AutowiredAnnotationBeanPostProcessor processor = new AutowiredAnnotationBeanPostProcessor();
        processor.setBeanFactory(beanFactory);

        Bean1 bean1 = new Bean1();
        // 👇 此时没有任何依赖注入
//        System.out.println(bean1);
        // 👇 执行依赖注入 @Autowired @Value
//        processor.postProcessProperties(null, bean1,"bean1");
        // 👇 此时 bean2 bean3 home 被注入
//        System.out.println(bean1);

        // 👇 processor.postProcessProperties 具体实现过程
        Method findAutowiringMetadata = AutowiredAnnotationBeanPostProcessor.class
                .getDeclaredMethod("findAutowiringMetadata", String.class, Class.class, PropertyValues.class);
        findAutowiringMetadata.setAccessible(true);
        // 👇 获取 bean1 上加了 @Autowired @Value 的成员变量，方法参数信息
        InjectionMetadata metadata = (InjectionMetadata) findAutowiringMetadata.invoke(processor, "bean1", Bean1.class, null);

        // 2. 调用 InjectionMetadata inject方法 来进行依赖注入，注入时，按类型查找值
        metadata.inject(bean1,"bean1",null);

        // 3.如何按照类型查找值【inject内部查找值的逻辑👇】
        Field bean3 = Bean1.class.getDeclaredField("bean3");
        DependencyDescriptor dd1 = new DependencyDescriptor(bean3,false);
        Object o1 = beanFactory.doResolveDependency(dd1, null, null, null);
        System.out.println(o1);

        Method setBean2 = Bean1.class.getDeclaredMethod("setBean2", Bean2.class);
        DependencyDescriptor dd2 = new DependencyDescriptor(new MethodParameter(setBean2,0),false);
        Object o2 = beanFactory.doResolveDependency(dd2, null, null, null);
        System.out.println(o2);

        Method setHome = Bean1.class.getDeclaredMethod("setHome", String.class);
        DependencyDescriptor dd3 = new DependencyDescriptor(new MethodParameter(setHome,0),true);
        Object o3 = beanFactory.doResolveDependency(dd3, null, null, null);
        System.out.println(o3);

    }
}
