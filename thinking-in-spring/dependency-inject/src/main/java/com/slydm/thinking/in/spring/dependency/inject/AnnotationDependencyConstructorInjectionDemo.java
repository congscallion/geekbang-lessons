package com.slydm.thinking.in.spring.dependency.inject;

import com.slydm.thinking.in.spring.ioc.overview.domain.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 基于 注解 依赖 构造器注入示例
 *
 * @author wangcymy@gmail.com(wangcong) 2020/10/29 下午9:54
 */
@Configuration
//@ImportResource("classpath:/META-INF/dependency-lookup-context.xml")
public class AnnotationDependencyConstructorInjectionDemo {

  public static void main(String[] args) {

    // 创建 spring 应用上下文容器
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

    // 注册 Configuration Class (配置类)
    applicationContext.register(AnnotationDependencyConstructorInjectionDemo.class);

    // 启动 spring 应用上下文
    applicationContext.refresh();

    testBeanMethodDirectInvoke(applicationContext);

    UserHolder userHolder = applicationContext.getBean("userHolder", UserHolder.class);
    System.out.println(userHolder);

    // 关窗 spring 应用上下文
    applicationContext.close();

  }

  /**
   * 验证 {@Bean} 方法直接调用产生的对象与通过方法参数引用是否一直
   *
   * <p>
   *
   * 在类中声明 @Configuration 注解，则该类中的所有@Bean标注的方法，都会被cglib提升，且方法返回结果会被缓存。后序执行多少该方法都会相同的结果。
   * 但是只能提升非静态的方法。
   *
   * <br>
   * 类中未声明 @Configuration 注解，@Bean标注的方法则不会提升。通过方法参数注入依赖@Bean实例时，使用同一个bean. 直接调用方法则每次都会执行方法返回新的bean.
   *
   * <br>
   * 结果可以执行本类的main方法查看。 去  @Configuration 或不去， 观察输出。
   */
  private static void testBeanMethodDirectInvoke(ApplicationContext beanFactory) {

    User user = beanFactory.getBean("user", User.class);
    Tom tom = beanFactory.getBean("tom", Tom.class);

    AllTom allTom = beanFactory.getBean("allTom", AllTom.class);
    System.out.println("allTom.getUser() == user : " + (allTom.getUser() == user));
    System.out.println("allTom.getTom() == tom : " + (allTom.getTom() == tom));

    System.out.println("=============================");

    BllTom bllTom = beanFactory.getBean("bllTom", BllTom.class);
    System.out.println("bllTom.getUser() == user : " + (bllTom.getUser() == user));
    System.out.println("bllTom.getTom() == tom : " + (bllTom.getTom() == tom));

  }

//  @Bean
//  public UserHolder userHolder(User user) {
//    return new UserHolder(user);
//  }

  @Bean
  public User user() {
    User user = new User();
    user.setName("wangcong");
    user.setId(2L);
    return user;
  }

  @Bean
  public Tom tom() {
    return new Tom("tom");
  }


  @Bean
  public AllTom allTom() {
    AllTom allTom = new AllTom();
    allTom.setTom(tom());
    allTom.setUser(user());
    return allTom;
  }

  @Bean
  public BllTom bllTom(User user, Tom tom) {
    BllTom bllTom = new BllTom();
    bllTom.setUser(user);
    bllTom.setTom(tom);
    return bllTom;
  }


  static class BllTom {

    private User user;
    private Tom tom;

    public User getUser() {
      return user;
    }

    public void setUser(User user) {
      this.user = user;
    }

    public Tom getTom() {
      return tom;
    }

    public void setTom(Tom tom) {
      this.tom = tom;
    }
  }


  static class AllTom {

    private User user;
    private Tom tom;

    public User getUser() {
      return user;
    }

    public void setUser(User user) {
      this.user = user;
    }

    public Tom getTom() {
      return tom;
    }

    public void setTom(Tom tom) {
      this.tom = tom;
    }
  }


  static class Tom {

    private String name;

    public Tom() {
    }

    public Tom(String name) {
      this.name = name;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }
  }


}
