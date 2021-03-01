package com.slydm.thinking.in.spring.dependency.sources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 单列对象作为依赖来源示例
 *
 * @author wangcymy@gmail.com(wangcong) 2020/11/18 下午10:36
 */
public class SingletonDependencySourceDemo {

  @Autowired
  private UserType userType;


  public static void main(String[] args) {

    // 创建 spring 应用上下文容器
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

    // 注册 Configuration Class (配置类)
    applicationContext.register(SingletonDependencySourceDemo.class);

    ConfigurableListableBeanFactory beanFactory = applicationContext.getBeanFactory();
    // 注册一个枚举对象（单例）
    beanFactory.registerSingleton("userType", UserType.VIP);

    // 启动 spring 应用上下文
    applicationContext.refresh();

    SingletonDependencySourceDemo demo = applicationContext
        .getBean(SingletonDependencySourceDemo.class);

    System.out.println("demo.userType=" + demo.userType);

    UserType userType = applicationContext.getBean("userType", UserType.class);
    System.out.println("userType=" + userType);

    // 关窗 spring 应用上下文
    applicationContext.close();


  }

  enum UserType {

    VIP,
    SUPPER,
    NORMAL

  }

}
