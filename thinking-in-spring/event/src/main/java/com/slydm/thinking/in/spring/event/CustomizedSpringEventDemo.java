package com.slydm.thinking.in.spring.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.support.GenericApplicationContext;

/**
 * 自定义 Spring 事件示例
 *
 * @author wangcymy@gmail.com(wangcong) 2021/1/18 11:00
 */
public class CustomizedSpringEventDemo {

  public static void main(String[] args) {

    GenericApplicationContext applicationContext = new GenericApplicationContext();

    applicationContext.addApplicationListener(new MySpringEventListener());
    applicationContext.addApplicationListener(new ApplicationListener<ApplicationEvent>() {
      @Override
      public void onApplicationEvent(ApplicationEvent event) {
        System.out.println("Event : " + event);
      }
    });

    applicationContext.refresh();

    applicationContext.publishEvent(new MySpringEvent("hello,world!"));
    applicationContext.publishEvent(new MySpringEvent("hello,2021!"));

    applicationContext.close();


  }

}
