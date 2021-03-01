package com.slydm.thinking.in.spring.dependency.lookup;

import com.slydm.thinking.in.spring.ioc.overview.domain.User;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

/**
 * 通过 {@link ObjectProvider} 查找依赖
 *
 * @author wangcymy@gmail.com(wangcong) 2020/10/26 下午10:04
 */
public class ObjectProviderDemo {

  public static void main(String[] args) {

    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

    applicationContext.register(ObjectProviderDemo.class);

    applicationContext.refresh();

    lookupByObjectProvider(applicationContext);

    lookupIfAvaiable(applicationContext);

    lookupByStreamOps(applicationContext);

    applicationContext.close();


  }

  private static void lookupByStreamOps(AnnotationConfigApplicationContext applicationContext) {

    ObjectProvider<String> objectProvider = applicationContext.getBeanProvider(String.class);

    Iterable<String> stringIterable = objectProvider;

    for (String string : stringIterable) {
      System.out.println(string);
    }

    objectProvider.stream().forEach(System.out::println);

  }

  private static void lookupIfAvaiable(AnnotationConfigApplicationContext applicationContext) {

    ObjectProvider<User> superUserProvider = applicationContext.getBeanProvider(User.class);
    System.out.println(superUserProvider.getIfAvailable());

    User user = superUserProvider.getIfAvailable(User::createUser);
    System.out.println(user);


  }

  private static void lookupByObjectProvider(
      AnnotationConfigApplicationContext applicationContext) {
    ObjectProvider<String> beanProvider = applicationContext.getBeanProvider(String.class);
    System.out.println(beanProvider.getObject());
  }

  @Bean
  @Primary
  public String helloworld() {
    return "Hello,World";
  }

  @Bean
  public String hello() {
    return "Hello, hello";
  }

}
