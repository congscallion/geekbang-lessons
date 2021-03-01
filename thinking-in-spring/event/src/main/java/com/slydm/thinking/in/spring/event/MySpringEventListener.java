package com.slydm.thinking.in.spring.event;

import org.springframework.context.ApplicationListener;

/**
 * 自定义 spring 事件监听器
 *
 * @author wangcymy@gmail.com(wangcong) 2021/1/18 10:56
 */
public class MySpringEventListener implements ApplicationListener<MySpringEvent> {

  @Override
  public void onApplicationEvent(MySpringEvent event) {
    print(event, "MySpringEventListener 接收到事件");
  }

  private static void print(MySpringEvent event, String topic) {
    System.out
        .printf("[%s] - %s - %s%n", Thread.currentThread().getName(), topic, event.getMessage());
  }
}
