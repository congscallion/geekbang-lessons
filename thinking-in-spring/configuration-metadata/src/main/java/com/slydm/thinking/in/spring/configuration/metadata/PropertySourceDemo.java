package com.slydm.thinking.in.spring.configuration.metadata;

import com.slydm.thinking.in.spring.ioc.overview.domain.User;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.SystemEnvironmentPropertySource;

/**
 * 外部化配置示例
 *
 * spring application 初始化时，会创建
 * {@link PropertiesPropertySource} name=systemProperties (默认优先级最高)
 * {@link SystemEnvironmentPropertySource} name=systemEnvironment
 *
 * ...
 * PropertySource 注解 加载的外部化配置
 * ...
 *
 * 通过 api 方式添加或修改 {@link org.springframework.core.env.PropertySource}. 这种方式可以改变 spring 默认行为。
 * 如{@link #main(String[])} 方法中一样，通过 api 的方式，添加一个MapPropertySource实例且使其优先级最高。
 *
 * @author wangcymy@gmail.com(wangcong) 2020/12/28 9:56 AM
 */
@PropertySource(value = "classpath:/META-INF/user-bean-definitions.properties", encoding = "utf-8")
public class PropertySourceDemo {

  /**
   * user.name 是 Java Properties 默认存在的。
   */
  @Bean
  public User user(@Value("${user.id}") Long id, @Value("${user.name}") String name) {
    User user = new User();
    user.setId(id);
    user.setName(name);
    return user;
  }


  public static void main(String[] args) {

    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

    /**
     *  通过 api 的方式 扩展 Environment 中的 PropertySources
     *  注意: 添加 PropertySource 操作必须在 refresh 方法之前完成
     */
    Map<String, Object> propertiesSource = new HashMap<>(16);
    propertiesSource.put("user.name", "胡一刀");
    MapPropertySource propertySource = new MapPropertySource("first-property-source",
        propertiesSource);
    context.getEnvironment().getPropertySources().addFirst(propertySource);

    // 注册配置类
    context.register(PropertySourceDemo.class);

    //启动应用上下文
    context.refresh();

    // beanName 和 bean 映射
    Map<String, User> usersMap = context.getBeansOfType(User.class);
    for (Map.Entry<String, User> entry : usersMap.entrySet()) {
      System.out.printf("User Bean name : %s , content : %s \n", entry.getKey(), entry.getValue());
    }

    for (org.springframework.core.env.PropertySource pv : context.getEnvironment()
        .getPropertySources()) {
      System.out.println();
      System.out.printf(pv.getName() + " ->%n");
      System.out.println(pv.getSource());
      System.out.printf("<- %s %n", pv.getName());
    }

    //关闭应用上下文
    context.close();


  }

}
