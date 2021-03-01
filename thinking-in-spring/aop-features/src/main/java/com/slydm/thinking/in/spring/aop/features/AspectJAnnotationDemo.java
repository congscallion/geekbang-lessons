package com.slydm.thinking.in.spring.aop.features;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * AspectJ 示例 in spring, 基于注解
 *
 * @author wangcymy@gmail.com(wangcong) 2021/2/20 17:34
 */
@Configuration
@EnableAspectJAutoProxy
@Aspect
public class AspectJAnnotationDemo {

  public static void main(String[] args) {

    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
    context.register(AspectJAnnotationDemo.class);

    context.refresh();

    AspectJAnnotationDemo aspectJDemo = context.getBean("aspectJAnnotationDemo", AspectJAnnotationDemo.class);
    System.out.println(aspectJDemo);

    context.close();
  }

}
