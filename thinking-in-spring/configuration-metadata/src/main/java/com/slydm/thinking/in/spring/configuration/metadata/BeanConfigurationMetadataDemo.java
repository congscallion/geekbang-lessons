package com.slydm.thinking.in.spring.configuration.metadata;

import com.slydm.thinking.in.spring.ioc.overview.domain.User;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

/**
 * Bean 配置元信息示例
 *
 * @author wangcymy@gmail.com(wangcong) 12/21/20 10:14 PM
 */
public class BeanConfigurationMetadataDemo {

  public static void main(String[] args) {

    BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder
        .genericBeanDefinition(User.class);
    beanDefinitionBuilder.addPropertyValue("name", "wangcong");
    // 获取 AbstractBeanDefinition
    AbstractBeanDefinition beanDefinition = beanDefinitionBuilder.getBeanDefinition();

    // 附加属性(不影响 Bean 实例化\ 属性赋值)
    beanDefinition.setAttribute("name", "slydm");
    // 可以帮助程序判断 当前 BeanDefinition 来自哪里 (辅助作用)
    beanDefinition.setSource(BeanConfigurationMetadataDemo.class);

    DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

    /**
     * 根据 Bean 名称和类型判断是否修改， 主面已添加了 beanDefinition attribute,
     * 下面修改逻辑中使用上述添加的 attribute, 此处相当于传递了元数据，它并不影响 bean 的初始化，即在 bean 的默认生命周期
     * 中不会使用 attribute 的值。
     */
//    beanFactory.addBeanPostProcessor(new BeanPostProcessor() {
//      @Override
//      public Object postProcessAfterInitialization(Object bean, String beanName)
//          throws BeansException {
//
//        if (ObjectUtils.nullSafeEquals("user", beanName) && User.class.equals(bean.getClass())) {
//
//          BeanDefinition bd = beanFactory.getBeanDefinition(beanName);
//          // slydm Attribute 属性 （存储） 上下文
//          String name = (String) bd.getAttribute("name");
//
//          User user = (User) bean;
//          user.setName(name);
//        }
//
//        return bean;
//      }
//    });

    /**
     * 通过 BeanDefinition 的 source 判断是否需要修改
     */
    beanFactory.addBeanPostProcessor(new BeanPostProcessor() {
      @Override
      public Object postProcessAfterInitialization(Object bean, String beanName)
          throws BeansException {

        // 通过 source 判断是否需要对BeanDefinition 进行修改
        if (BeanConfigurationMetadataDemo.class.equals(beanDefinition.getScope())) {

          BeanDefinition bd = beanFactory.getBeanDefinition(beanName);
          // slydm Attribute 属性 （存储） 上下文
          String name = (String) bd.getAttribute("name");

          User user = (User) bean;
          user.setName(name);
        }

        return bean;
      }
    });

    beanFactory.registerBeanDefinition("user", beanDefinition);

    User user = beanFactory.getBean("user", User.class);
    System.out.println(user);

  }

}
