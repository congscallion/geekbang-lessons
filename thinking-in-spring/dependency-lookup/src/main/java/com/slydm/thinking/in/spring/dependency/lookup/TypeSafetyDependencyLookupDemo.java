package com.slydm.thinking.in.spring.dependency.lookup;

import com.slydm.thinking.in.spring.ioc.overview.domain.User;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author wangcymy@gmail.com(wangcong) 2020/10/28 下午9:05
 */
public class TypeSafetyDependencyLookupDemo {

  public static void main(String[] args) {
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

    applicationContext.register(TypeSafetyDependencyLookupDemo.class);

    applicationContext.refresh();

    // 演示 BeanFactory#getBean 方法的安全性  是否安全：否
    displayBeanFactoryGetBean(applicationContext);
    // 演示 ObjectFactory#getObject 方法的安全性  是否安全: 否
    displayObjectFactoryGetObject(applicationContext);
    // 演示 ObjectProvider#getBeanProvider 方法的安全性 是否安全：是
    displayObjectProviderIfAvailable(applicationContext);
    // 演示 ObjectProvider stream api 安全性
    displayObjectProviderStreamOps(applicationContext);

    // 演示ListableBeanFactoryGetBeansOfType 方法的安全性 是否安全:是
    displayListableBeanFactoryGetBeansOfType(applicationContext);

    applicationContext.close();


  }

  private static void displayObjectProviderStreamOps(BeanFactory beanFactory) {

    ObjectProvider<User> objectProvider = beanFactory.getBeanProvider(User.class);
    printBeanException("displayObjectProviderStreamOps",
        () -> objectProvider.forEach(System.out::println));
  }

  private static void displayListableBeanFactoryGetBeansOfType(
      ListableBeanFactory applicationContext) {

    printBeanException("displayListableBeanFactoryGetBeansOfType",
        () -> applicationContext.getBeansOfType(User.class));

  }

  private static void displayObjectProviderIfAvailable(BeanFactory beanFactory) {

    // ObjectProvider -> ObjectFactory
    ObjectProvider<User> objectProvider = beanFactory.getBeanProvider(User.class);
    printBeanException("displayObjectProviderIfAvailable", () -> objectProvider.getIfAvailable());

  }

  private static void displayObjectFactoryGetObject(BeanFactory beanFactory) {

    // ObjectProvider -> ObjectFactory
    ObjectFactory<User> userObjectFactory = beanFactory.getBeanProvider(User.class);
    printBeanException("displayObjectFactoryGetObject", () -> userObjectFactory.getObject());

  }

  private static void printBeanException(String source, Runnable runnable) {

    System.err.println();
    System.err.println("Source from: " + source);
    try {
      runnable.run();
    } catch (BeansException ex) {
      ex.printStackTrace();
    }
  }


  private static void displayBeanFactoryGetBean(BeanFactory beanFactory) {

    printBeanException("displayBeanFactoryGetBean", () -> beanFactory.getBean(User.class));

  }


}
