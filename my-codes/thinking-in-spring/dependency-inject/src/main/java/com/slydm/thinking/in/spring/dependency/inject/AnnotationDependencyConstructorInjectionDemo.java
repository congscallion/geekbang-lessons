package com.slydm.thinking.in.spring.dependency.inject;

import com.slydm.thinking.in.spring.ioc.overview.domain.User;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;

/**
 * 基于 注解 依赖 构造器注入示例
 *
 * @author wangcymy@gmail.com(wangcong) 2020/10/29 下午9:54
 */
@ImportResource("classpath:/META-INF/dependency-lookup-context.xml")
public class AnnotationDependencyConstructorInjectionDemo {

  public static void main(String[] args) {

    // 创建 spring 应用上下文容器
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

    // 注册 Configuration Class (配置类)
    applicationContext.register(AnnotationDependencyConstructorInjectionDemo.class);

    // 启动 spring 应用上下文
    applicationContext.refresh();

    UserHolder userHolder = applicationContext.getBean("userHolder", UserHolder.class);
    System.out.println(userHolder);

    // 关窗 spring 应用上下文
    applicationContext.close();

  }


  @Bean
  public UserHolder userHolder(User user) {
    return new UserHolder(user);
  }

}
