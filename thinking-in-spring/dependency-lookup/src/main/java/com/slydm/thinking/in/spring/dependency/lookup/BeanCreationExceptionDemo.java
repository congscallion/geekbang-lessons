package com.slydm.thinking.in.spring.dependency.lookup;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * {@link org.springframework.beans.factory.BeanCreationException} 示例
 *
 * @author wangcymy@gmail.com(wangcong) 2020/10/28 下午9:57
 */
public class BeanCreationExceptionDemo {

  public static void main(String[] args) {

    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

    BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder
        .genericBeanDefinition(Mybatis.class);

    applicationContext
        .registerBeanDefinition("errorBean", beanDefinitionBuilder.getBeanDefinition());

    applicationContext.refresh();

    applicationContext.close();

  }

  static class Mybatis implements InitializingBean {

    @PostConstruct
    public void init() throws Throwable {
      throw new Throwable("init : for error");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
      throw new Exception("afterPropertiesSet : for error");
    }
  }

}
