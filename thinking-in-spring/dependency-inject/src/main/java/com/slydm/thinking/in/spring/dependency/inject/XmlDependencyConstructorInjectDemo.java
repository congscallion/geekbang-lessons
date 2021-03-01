package com.slydm.thinking.in.spring.dependency.inject;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

/**
 * 基于 xml 资源的依赖 构造器注入示例
 *
 * @author wangcymy@gmail.com(wangcong) 2020/10/29 下午9:40
 */
public class XmlDependencyConstructorInjectDemo {

  public static void main(String[] args) {

    DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

    XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);

    String location = "classpath:/META-INF/dependency-constructor-injection.xml";

    // 加载 xml 资源，解析生成 BeanDefinition
    xmlBeanDefinitionReader.loadBeanDefinitions(location);

    UserHolder userHolder = beanFactory.getBean("userHolder", UserHolder.class);

    System.out.println(userHolder);

  }

}
