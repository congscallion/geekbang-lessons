package com.slydm.thinking.in.spring.configuration.metadata;

import com.slydm.thinking.in.spring.ioc.overview.domain.User;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

/**
 * 基于 Java 注解 Spring IoC 容器元信息配置示例
 *
 * @author wangcymy@gmail.com(wangcong) 2020/12/28 10:53 AM
 */
@ImportResource("classpath:/META-INF/dependency-lookup-context.xml")
@Import(User.class)
@PropertySource("classpath:/META-INF/user-bean-definitions.properties") // Java 8+ @Repeatable 支持
@PropertySource("classpath:/META-INF/user-bean-definitions.properties")
// 两种配置方式等效
/*@PropertySources({
    @PropertySource("classpath:/META-INF/user-bean-definitions.properties"),
    @PropertySource("classpath:/META-INF/user-bean-definitions.properties")
})*/
public class AnnotatedSpringIocContainerMetadataConfigurationDemo {


  /**
   * user.name 是 Java Properties 默认存在，当前用户
   */
  @Bean
  public User configuredUser(@Value("${user.id}") Long id, @Value("${user.name}") String name) {
    User user = new User();
    user.setId(id);
    user.setName(name);
    return user;
  }

  public static void main(String[] args) {
    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
    // 注册当前类作为 Configuration Class
    context.register(AnnotatedSpringIocContainerMetadataConfigurationDemo.class);
    // 启动 Spring 应用上下文
    context.refresh();
    // beanName 和 bean 映射
    Map<String, User> usersMap = context.getBeansOfType(User.class);
    for (Map.Entry<String, User> entry : usersMap.entrySet()) {
      System.out.printf("User Bean name : %s , content : %s \n", entry.getKey(), entry.getValue());
    }
    // 关闭 Spring 应用上下文
    context.close();
  }


}
