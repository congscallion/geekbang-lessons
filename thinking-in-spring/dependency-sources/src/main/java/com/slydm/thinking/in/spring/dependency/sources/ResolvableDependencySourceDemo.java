package com.slydm.thinking.in.spring.dependency.sources;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author wangcymy@gmail.com(wangcong) 2020/11/18 下午10:49
 */
public class ResolvableDependencySourceDemo {

  @Autowired
  private String hello;


  public static void main(String[] args) {

    // 创建 spring 应用上下文容器
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

    // 注册 Configuration Class (配置类)
    applicationContext.register(ResolvableDependencySourceDemo.class);

    ConfigurableListableBeanFactory beanFactory = applicationContext.getBeanFactory();
    // 注册一个枚举对象（单例）
    beanFactory.registerResolvableDependency(String.class, "Hello,World");

    // 启动 spring 应用上下文
    applicationContext.refresh();

    ResolvableDependencySourceDemo demo = applicationContext
        .getBean(ResolvableDependencySourceDemo.class);
    System.out.println("demo.hello=" + demo.hello);

    try {
      /**
       * String 类型的bean 可以注入到当前类，但不能依赖查找。
       */
      String hello = applicationContext.getBean(String.class);
      System.out.println("hello bean=" + hello);

    } catch (NoSuchBeanDefinitionException ex) {
      System.err.println(ex.getMessage());
    }

    // 关窗 spring 应用上下文
    applicationContext.close();

  }

}
