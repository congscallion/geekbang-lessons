package com.slydm.thinking.in.spring.bean.lifecycle;

import com.slydm.thinking.in.spring.ioc.overview.domain.User;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.PropertiesBeanDefinitionReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.EncodedResource;

/**
 * Bean 元信息配置示例
 *
 @author wangcymy@gmail.com(wangcong) 2020/12/13 下午5:16
 */
public class BeanMetadataConfigurationDemo {

  public static void main(String[] args) {

    DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

    // 实例化基于 properties 资源 BeanDefinitionReader
    PropertiesBeanDefinitionReader beanDefinitionReader = new PropertiesBeanDefinitionReader(
        beanFactory);
    String location = "META-INF/user.properties";

    //  基于 ClassPath 加载 properties 资源
    ClassPathResource resource = new ClassPathResource(location);
    // 指定字符编码 UTF-8
    EncodedResource encodedResource = new EncodedResource(resource, "UTF-8");

    int beanDefinitions = beanDefinitionReader.loadBeanDefinitions(encodedResource);
    System.out.println("加载的 beanDefinitions 数量: " + beanDefinitions);

    // 通过 Bean Id 和类型进行依赖查找
    User user = beanFactory.getBean(User.class);
    System.out.println("user = " + user);


  }

}
