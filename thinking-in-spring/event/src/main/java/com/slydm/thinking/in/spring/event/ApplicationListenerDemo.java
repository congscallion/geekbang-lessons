package com.slydm.thinking.in.spring.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.AnnotationConfigRegistry;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.ContextStoppedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * {@link EventListener} 示例
 * <p>
 * <li>支持多 {@link ApplicationEvent} 类型
 * <li>注解只能配置在方法
 * <li>支持异步执行
 * <li>支持泛型类事件
 * <li>支持{@link Order}顺序控制
 *
 *<p> 注册方式:
 * <li>通过 {@link AbstractApplicationContext#addApplicationListener(ApplicationListener)} 添加
 * <li>通过 {@link AnnotationConfigRegistry#register(Class[])} 注册 bean
 * <li>通过 {@link EventListener} 注解，注册方法可处理 {@link ApplicationEvent} 事件
 *
 * @author wangcymy@gmail.com(wangcong) 2021/1/16 11:09
 * @see ApplicationEventPublisher
 * @see ApplicationEventMulticaster
 */
@EnableAsync
public class ApplicationListenerDemo implements ApplicationEventPublisherAware {

  public static void main(String[] args) {

    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

    context.register(ApplicationListenerDemo.class);

    // 方法一：基于 Spring 接口：向 Spring 应用上下文注册事件
    // a 方法：基于 ConfigurableApplicationContext API 实现
    context.addApplicationListener(new ApplicationListener<ApplicationEvent>() {
      @Override
      public void onApplicationEvent(ApplicationEvent event) {
        print(event, "applicationListener 接收到 spring 事件");
      }
    });

    // b 方法：基于 ApplicationListener 注册为 Spring Bean
    // 通过 Configuration Class 来注册
    context.register(MyApplicationListener.class);

    // 方法二：基于 Spring 注解：@org.springframework.context.event.EventListener

    // 启动 Spring 应用上下文
    context.refresh();

    // 启动 Spring 上下文
    context.start();
    // 停止 Spring 上下文
    context.stop();

    // 关闭 Spring 应用上下文
    context.close();

  }

  @Override
  public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
    applicationEventPublisher.publishEvent(new ApplicationEvent("Hello,World") {
    });
  }

  static class MyApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
      print(event, "MyApplicationListener 接收到 spring 事件");
    }
  }


  //通过注解添加监听器
  @Order(2)
  @EventListener
  public void allEvent1(ApplicationEvent event) {
    print(event, "allEvent1 接收到 spring 事件");
  }

  @Order(3)
  @EventListener
  public void allEvent2(ApplicationEvent event) {
    print(event, "allEvent2 接收到 spring 事件");
  }

  @Order(1)
  @EventListener
  public void allEvent3(ApplicationEvent event) {
    print(event, "allEvent3 接收到 spring 事件");
  }


  @EventListener
  public void refreshed(ContextRefreshedEvent event) {
    print(event, "refreshed 接收到 spring 事件");
  }

  @Async
  @EventListener
  public void refreshedAsAsync(ContextRefreshedEvent event) {
    print(event, "refreshedAsAsync 接收到 spring 事件");
  }

  @EventListener
  public void started(ContextStartedEvent event) {
    print(event, "start 接收到 spring 事件");
  }

  @EventListener
  public void stopped(ContextStoppedEvent event) {
    print(event, "stopped 接收到 spring 事件");
  }

  @EventListener
  public void closed(ContextClosedEvent event) {
    print(event, "closed 接收到 spring 事件");
  }


  private static void print(ApplicationEvent event, String topic) {
    System.out
        .printf("[%s] - %s - %s%n", Thread.currentThread().getName(), topic,
            event.getSource());
  }
}
