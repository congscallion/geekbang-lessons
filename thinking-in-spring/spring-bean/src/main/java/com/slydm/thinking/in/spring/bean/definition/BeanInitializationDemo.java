package com.slydm.thinking.in.spring.bean.definition;

import com.slydm.thinking.in.spring.bean.factory.DefaultUserFactory;
import com.slydm.thinking.in.spring.bean.factory.UserFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

/**
 * @author wangcymy@gmail.com(wangcong) 2020/10/25 下午4:36
 */
@Configuration
public class BeanInitializationDemo {

  public static void main(String[] args) {
    // 创建 spring 应用上下文容器
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

    // 注册 Configuration Class (配置类)
    applicationContext.register(BeanInitializationDemo.class);

    // 启动 spring 应用上下文
    applicationContext.refresh();

    /**
     * 非延迟初始化在 spring 应用上下文启动时初始化
     * 延迟初始化在 spring 应用上下文启动后，bean使用时 (触发了该bean的依赖查找) 初始化
     */

    System.out.println("spring 应用上下文初始化完成...");

    // 依赖查找
    applicationContext.getBean(UserFactory.class);

    System.out.println("spring 应用上下文准备关闭...");

    // 关窗 spring 应用上下文
    applicationContext.close();

    System.out.println("spring 应用上下文已关闭...");
  }

  @Lazy
  @Bean(initMethod = "initUserFactory", destroyMethod = "doDestroy")
  public UserFactory userFactory() {
    return new DefaultUserFactory();
  }

}
