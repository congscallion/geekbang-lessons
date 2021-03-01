package com.slydm.thinking.in.spring.conversion;

import com.slydm.thinking.in.spring.ioc.overview.domain.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author wangcymy@gmail.com(wangcong) 2021/1/8 18:13
 */
public class SpringCustomPropertyEditorDemo {

  public static void main(String[] args) {

    ConfigurableApplicationContext applicationContext = new ClassPathXmlApplicationContext(
        "classpath:/META-INF/property-editor-context.xml");

    User user = applicationContext.getBean("user", User.class);
    System.out.println(user);

    applicationContext.close();
  }


}
