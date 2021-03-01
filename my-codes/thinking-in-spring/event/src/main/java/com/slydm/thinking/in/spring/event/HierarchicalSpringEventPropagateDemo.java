package com.slydm.thinking.in.spring.event;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.event.ApplicationContextEvent;

/**
 * spring 层次性 {@link ApplicationContext} 事件传播示例
 *
 * <p>
 * ApplicationContext 具有层次性，其层次性在事件上表现为:
 * <li>子 ApplicationContext 发布某个事件，会导致其父 ApplicationContext也会发布该事件，直到 Root ApplicationContext.
 * <li>父 ApplicationContext发布某个事件，不会导致子类 ApplicationContext发布该事件。
 * <li>在有层次性的 ApplicationContext 环境下，发布事件时，由于事件会被传播到父 ApplicationContext，需要特别注意事件的重复消费。
 *
 * @author wangcymy@gmail.com(wangcong) 2021/1/16 17:56
 */
public class HierarchicalSpringEventPropagateDemo {

  public static void main(String[] args) {

    AnnotationConfigApplicationContext parent = new AnnotationConfigApplicationContext();
    parent.setId("parent-context");
    parent.register(MyApplicationListener.class);

    AnnotationConfigApplicationContext current = new AnnotationConfigApplicationContext();
    current.setId("current-context");
    current.setParent(parent);
    current.register(MyApplicationListener.class);

    parent.refresh(); // 输出1次[main]-[parent-context]-ContextRefreshedEvent

    current.refresh(); // 输出2次[main]-[current-context]-ContextRefreshedEvent

    MyApplicationListener parentListener = parent.getBean(MyApplicationListener.class);
    MyApplicationListener currentListener = current.getBean(MyApplicationListener.class);

    System.out.println(parentListener == currentListener); // false


    current.start(); // 输出2次[main]-[current-context]-ContextStartedEvent

    current.stop(); // 输出2次[main]-[current-context]-ContextStoppedEvent

    current.close(); // 输出2次[main]-[current-context]-ContextClosedEvent

    parent.close();// 输出1次[main]-[parent-context]-ContextClosedEvent


  }

  static class MyApplicationListener implements ApplicationListener<ApplicationContextEvent> {

    @Override
    public void onApplicationEvent(ApplicationContextEvent event) {
      System.out.printf("[%s]-[%s]-%s%n", Thread.currentThread().getName(),
          event.getApplicationContext().getId(),
          event.getClass().getSimpleName());
    }
  }

}
