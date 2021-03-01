package com.slydm.thinking.in.spring.bean.lifecycle;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotatedBeanDefinitionReader;

/**
 * {@AnnotatedBeanDefinitionReader}  示例
 *
 * @author wangcymy@gmail.com(wangcong) 2020/12/9 下午11:15
 */
public class AnnotatedBeanDefinitionParsingDemo {

  public static void main(String[] args) {

    DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

    AnnotatedBeanDefinitionReader beanDefinitionReader = new AnnotatedBeanDefinitionReader(
        beanFactory);

    int beanDefinitionCountBefore = beanFactory.getBeanDefinitionCount();

    beanDefinitionReader.registerBean(AnnotatedBeanDefinitionParsingDemo.class);

    int beanDefinitionCountAfter = beanFactory.getBeanDefinitionCount();
    int beanDefinitionCount = beanDefinitionCountAfter - beanDefinitionCountBefore;

    System.out.println("加载的 beanDefinitions 数量: " + beanDefinitionCount);

    /**
     * Bean name 生成规则 {@link AnnotationBeanNameGenerator}
     */
    AnnotatedBeanDefinitionParsingDemo demo = beanFactory
        .getBean("annotatedBeanDefinitionParsingDemo", AnnotatedBeanDefinitionParsingDemo.class);

    System.out.println(demo);


  }

}
