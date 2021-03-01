package com.slydm.thinking.in.spring.bean.definition;

import com.slydm.thinking.in.spring.ioc.overview.domain.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author wangcymy@gmail.com(wangcong) 2020/10/25 下午3:37
 */
public class BeanInstantiationDemo {

  public static void main(String[] args) {

    BeanFactory beanFactory = new ClassPathXmlApplicationContext(
        "classpath:/META-INF/bean-instantiation-context.xml");

    User staticUser = beanFactory.getBean("user-by-static-method", User.class);
    User instanceUser = beanFactory.getBean("user-by-instance-method", User.class);
    User factoryBeanUser = beanFactory.getBean("user-by-factory-bean", User.class);
    System.out.println(staticUser);
    System.out.println(instanceUser);
    System.out.println(factoryBeanUser);
    System.out.println(instanceUser == staticUser);
    System.out.println(factoryBeanUser == staticUser);
    System.out.println(factoryBeanUser == instanceUser);


  }

}
