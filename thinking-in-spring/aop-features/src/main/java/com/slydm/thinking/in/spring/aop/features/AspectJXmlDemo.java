package com.slydm.thinking.in.spring.aop.features;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * AspectJ 示例 in spring, 基于 xml
 *
 * @author wangcymy@gmail.com(wangcong) 2021/2/20 17:43
 */

@Aspect
public class AspectJXmlDemo {

  public static void main(String[] args) {

    String location = "classpath:/META-INF/spring-aspectj-context.xml";
    ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(location);

    AspectJXmlDemo aspectJXmlDemo = context.getBean("aspectJXmlDemo", AspectJXmlDemo.class);
    System.out.println(aspectJXmlDemo);

    NotVeryUsefulAspect notVeryUsefulAspect = context
        .getBean("notVeryUsefulAspect", NotVeryUsefulAspect.class);
    System.out.println(notVeryUsefulAspect);

    context.close();
  }

  @Aspect
  static class NotVeryUsefulAspect {

  }

}


