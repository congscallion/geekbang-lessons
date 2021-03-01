package com.slydm.thinking.in.spring.bean.lifecycle;

import com.slydm.thinking.in.spring.ioc.overview.domain.SuperUser;
import com.slydm.thinking.in.spring.ioc.overview.domain.User;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Bean 实例化生命周期示例
 *
 * @author wangcymy@gmail.com(wangcong) 2020/12/13 下午5:16
 */
public class BeanInstantiationLifecycleDemo {

  public static void main(String[] args) {

    executeBeanFactory();

    System.out.println("-------------------------------------");

    executeApplicationContext();

  }

  public static void executeBeanFactory() {

    DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
    //  type 1:
    // 添加 BeanPostProcessor 实现
    // beanFactory.addBeanPostProcessor(new MyInstantiationAwareBeanPostProcessor());
    // type2: register to spring ioc container

    // xml reader
    XmlBeanDefinitionReader definitionReader = new XmlBeanDefinitionReader(beanFactory);

    // 加载 xml 资源文件
    String[] locations = {"META-INF/dependency-lookup-context.xml",
        "META-INF/bean-contructor-dependency-injection.xml"};

    definitionReader.loadBeanDefinitions(locations);

    int beanDefinitions = definitionReader.loadBeanDefinitions(locations);

    System.out.println("已加载 BeanDefinition 数量: " + beanDefinitions);

    User user = beanFactory.getBean("user", User.class);
    System.out.println(user);

    SuperUser superUser = beanFactory.getBean("superUser", SuperUser.class);
    System.out.println(superUser);

    UserHolder userHolder = beanFactory.getBean("userHolder", UserHolder.class);
    System.out.println(userHolder);


  }


  public static void executeApplicationContext() {

    ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext();
    // 加载 xml 资源文件
    String[] locations = {"META-INF/dependency-lookup-context.xml",
        "META-INF/bean-contructor-dependency-injection.xml"};
    applicationContext.setConfigLocations(locations);

    applicationContext.refresh();

    User user = applicationContext.getBean("user", User.class);
    System.out.println(user);

    SuperUser superUser = applicationContext.getBean("superUser", SuperUser.class);
    System.out.println(superUser);

    UserHolder userHolder = applicationContext.getBean("userHolder", UserHolder.class);
    System.out.println(userHolder);

    applicationContext.close();


  }

}

