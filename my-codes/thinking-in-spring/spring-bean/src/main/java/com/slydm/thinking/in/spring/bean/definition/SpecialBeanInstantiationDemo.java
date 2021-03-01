package com.slydm.thinking.in.spring.bean.definition;

import com.slydm.thinking.in.spring.bean.factory.DefaultUserFactory;
import com.slydm.thinking.in.spring.bean.factory.UserFactory;
import java.util.Iterator;
import java.util.ServiceLoader;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 特殊 Bean 实例化示例
 *
 * @author wangcymy@gmail.com(wangcong) 2020/10/25 下午4:04
 */
public class SpecialBeanInstantiationDemo {

  public static void main(String[] args) {

    ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
        "classpath:/META-INF/special-bean-instantiation-context.xml");

    AutowireCapableBeanFactory beanFactory = applicationContext
        .getAutowireCapableBeanFactory();

    UserFactory userFactory = beanFactory.createBean(DefaultUserFactory.class);
    System.out.println(userFactory.createUser());

    ServiceLoader<UserFactory> userFactoryServiceLoader = beanFactory
        .getBean("userFactoryServiceLoader", ServiceLoader.class);

    displayServiceLoader(userFactoryServiceLoader);

    serviceLoaderDemo();

  }

  private static void serviceLoaderDemo() {
    ServiceLoader<UserFactory> serviceLoader = ServiceLoader
        .load(UserFactory.class, Thread.currentThread().getContextClassLoader());
    displayServiceLoader(serviceLoader);
  }


  private static void displayServiceLoader(ServiceLoader<UserFactory> serviceLoader) {
    Iterator<UserFactory> iterator = serviceLoader.iterator();
    while (iterator.hasNext()) {
      UserFactory userFactory = iterator.next();
      System.out.println(userFactory.createUser());
    }

  }

}
