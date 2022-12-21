package com.example.springdemo.a03;

import java.util.ArrayList;
import java.util.List;

/**
 * @author leelixiangjun
 * @date 2022/12/21 15:19
 */
public class TestMethodTemplate {
    public static void main(String[] args) {
        MyBeanFactory myBeanFactory = new MyBeanFactory();
        myBeanFactory.addProcessor((Object bean) -> System.out.println(bean + "对@Autowired进行处理"));
        myBeanFactory.addProcessor((Object bean) -> System.out.println(bean + "对@Resource进行处理"));
        myBeanFactory.getBean();
    }

    /**
     * 模板方法 Template Method Pattern
     * **固定不变的部分，具体实现
     * **不能确定的部分，抽象成接口
     */
    static class MyBeanFactory {

        private List<BeanPostProcessor> processors = new ArrayList<>();

        public Object getBean() {
            Object bean = new Object();
            System.out.println("构造" + bean);
            System.out.println("依赖注入" + bean);
            for (BeanPostProcessor processor : processors) {
                processor.inject(bean);
            }
            System.out.println("初始化" + bean);
            return bean;
        }

        public void addProcessor(BeanPostProcessor processor) {
            processors.add(processor);
        }
    }

    static interface BeanPostProcessor {
        public void inject(Object bean); // 对依赖注入进行扩展

    }
}
