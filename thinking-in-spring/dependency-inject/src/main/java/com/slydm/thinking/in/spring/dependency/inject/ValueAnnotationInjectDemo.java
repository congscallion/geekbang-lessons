package com.slydm.thinking.in.spring.dependency.inject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * {@link Value} 注解注入功能演示
 *
 * @author wangcymy@gmail.com(wangcong) 2021/4/2 14:23
 */
public class ValueAnnotationInjectDemo {


  @Value("${os.name}")
  private String platform;

  public static void main(String[] args) {

    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

    applicationContext.register(ValueAnnotationInjectDemo.class);
    applicationContext.refresh();

    ValueAnnotationInjectDemo bean = applicationContext.getBean(ValueAnnotationInjectDemo.class);
    System.out.println(bean.platform);

    applicationContext.close();


  }


}
