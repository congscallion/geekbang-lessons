package com.slydm.thinking.in.spring.dependency.lookup;

import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * {@link NoUniqueBeanDefinitionException} 异常演示
 *
 * @author wangcymy@gmail.com(wangcong) 2020/10/28 下午9:49
 */
public class NoUniqueBeanDefinitionExceptionDemo {

  public static void main(String[] args) {

    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

    applicationContext.register(NoUniqueBeanDefinitionExceptionDemo.class);

    applicationContext.refresh();

    try {
      applicationContext.getBean(String.class);
    } catch (NoUniqueBeanDefinitionException ex) {

      System.err.printf("当前 spring 上下文中存在 %d 个 %s 类型的bean,失败原因: %s%n",
          ex.getNumberOfBeansFound(), ex.getBeanType(), ex.getMessage());

    }

    applicationContext.close();


  }


  @Bean
  public String bean1() {
    return "1";
  }

  @Bean
  public String bean2() {
    return "2";
  }

  @Bean
  public String bean3() {
    return "3";
  }

}
