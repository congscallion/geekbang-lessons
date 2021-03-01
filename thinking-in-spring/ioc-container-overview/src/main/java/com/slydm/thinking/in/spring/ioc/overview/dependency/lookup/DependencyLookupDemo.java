package com.slydm.thinking.in.spring.ioc.overview.dependency.lookup;


import com.slydm.thinking.in.spring.ioc.overview.annotation.Super;
import com.slydm.thinking.in.spring.ioc.overview.domain.User;
import java.util.Map;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 1. 通过名称的方式查找依赖 2. 通过类型查找
 *
 * @author wangcymy@gmail.com(wangcong) 2020/10/19 下午11:14
 */
public class DependencyLookupDemo {

  public static void main(String[] args) {

    BeanFactory beanFactory = new ClassPathXmlApplicationContext(
        "classpath:/META-INF/dependency-lookup-context.xml");

    // 按照类型查找
    lookupByType(beanFactory);
    // 按照类型查找集合对象
    lookupCollectionByType(beanFactory);
    // 根据注解查找
    lookupByAnnotationType(beanFactory);
    // 实时查找
    lookupInRealTime(beanFactory);
    // 延迟查找
    lookupInLazy(beanFactory);

  }

  private static void lookupByAnnotationType(BeanFactory beanFactory) {
    if (beanFactory instanceof ListableBeanFactory) {
      ListableBeanFactory listableBeanFactory = (ListableBeanFactory) beanFactory;

      Map<String, User> users = (Map) listableBeanFactory.getBeansWithAnnotation(Super.class);
      System.out.println("查找标注 @Super 的User对象: " + users);
    }
  }

  private static void lookupCollectionByType(BeanFactory beanFactory) {

    if (beanFactory instanceof ListableBeanFactory) {
      ListableBeanFactory listableBeanFactory = (ListableBeanFactory) beanFactory;

      Map<String, User> users = listableBeanFactory.getBeansOfType(User.class);
      System.out.println("查找到的所有User对象: " + users);

    }

  }

  private static void lookupByType(BeanFactory beanFactory) {

    User user = beanFactory.getBean(User.class);
    System.out.println("实时查找: " + user);


  }

  private static void lookupInRealTime(BeanFactory beanFactory) {
    User user = (User) beanFactory.getBean("user");
    System.out.println("实时查找: " + user);
  }

  private static void lookupInLazy(BeanFactory beanFactory) {
    ObjectFactory<User> objectFactory = (ObjectFactory<User>) beanFactory.getBean("objectFactory");
    User user = objectFactory.getObject();
    System.out.println("延迟查找: " + user);
  }


}
