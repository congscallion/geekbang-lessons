package com.slydm.thinking.in.spring.bean.definition;

import com.slydm.thinking.in.spring.bean.domain.EvanService;
import com.slydm.thinking.in.spring.bean.domain.PersonService;
import com.slydm.thinking.in.spring.bean.domain.TomService;
import org.springframework.beans.factory.annotation.AnnotatedGenericBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author wangcymy@gmail.com(wangcong) 2021/1/18 16:47
 */
public class BeanDefinitionDependencyDemo {

  public static void main(String[] args) {

    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

    context.getBeanFactory().registerSingleton("tomService", new TomService());
    context.register(PersonService.class);
    context.registerBeanDefinition("evanService", getEvanServiceBeanDefinition3());

    context.refresh();

    context.getBean(PersonService.class).showEvanServiceResult();

    context.close();

  }

  private static BeanDefinition getEvanServiceBeanDefinition() {
    BeanDefinition beanDefinition = new AnnotatedGenericBeanDefinition(EvanService.class);
    return beanDefinition;
  }

  private static BeanDefinition getEvanServiceBeanDefinition2() {
    BeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(EvanService.class)
        .getBeanDefinition();
    return beanDefinition;
  }

  private static BeanDefinition getEvanServiceBeanDefinition3() {
    BeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition()
        .getBeanDefinition();
    beanDefinition.setBeanClassName("com.slydm.thinking.in.spring.bean.domain.EvanService");
    return beanDefinition;
  }

}
