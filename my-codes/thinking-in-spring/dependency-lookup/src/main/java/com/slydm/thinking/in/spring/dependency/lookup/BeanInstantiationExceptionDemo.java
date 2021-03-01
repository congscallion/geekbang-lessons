package com.slydm.thinking.in.spring.dependency.lookup;

import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * {@link org.springframework.beans.BeanInstantiationException} 示例
 *
 * @author wangcymy@gmail.com(wangcong) 2020/10/28 下午9:53
 */
public class BeanInstantiationExceptionDemo {

  public static void main(String[] args) {

    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

    BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition();
    AbstractBeanDefinition beanDefinition = beanDefinitionBuilder.getBeanDefinition();
    beanDefinition.setBeanClass(CharSequence.class);

    applicationContext.registerBeanDefinition("errorBean", beanDefinition);

    applicationContext.refresh();

    applicationContext.close();


  }

}
