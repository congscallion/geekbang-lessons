package com.slydm.thinking.in.spring.event;

import static org.springframework.context.support.AbstractApplicationContext.APPLICATION_EVENT_MULTICASTER_BEAN_NAME;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

/**
 * 异步事件处理器示例
 *
 * @author wangcymy@gmail.com(wangcong) 2021/1/19 10:11
 */
public class AsyncEventHandlerDemo {

  public static void main(String[] args) {

    GenericApplicationContext context = new GenericApplicationContext();

    context.addApplicationListener(new MySpringEventListener());

    context.refresh();

    ApplicationEventMulticaster applicationEventMulticaster = context
        .getBean(APPLICATION_EVENT_MULTICASTER_BEAN_NAME, ApplicationEventMulticaster.class);

    if (applicationEventMulticaster instanceof SimpleApplicationEventMulticaster) {

      SimpleApplicationEventMulticaster simpleApplicationEventMulticaster
          = (SimpleApplicationEventMulticaster) applicationEventMulticaster;

      ExecutorService executorService = Executors
          .newSingleThreadExecutor(new CustomizableThreadFactory("my-spring-event-pool"));

      // 同步切换为异步
      simpleApplicationEventMulticaster.setTaskExecutor(executorService);

      // 注册容器关闭监听器，当容器关闭时，关闭线程池
      simpleApplicationEventMulticaster.addApplicationListener(
          new ApplicationListener<ContextClosedEvent>() {
            @Override
            public void onApplicationEvent(ContextClosedEvent event) {
              if (!executorService.isShutdown()) {
                executorService.shutdown();
              }
            }
          });

      // 事件异常处理器
      simpleApplicationEventMulticaster.setErrorHandler(e -> {
        System.err
            .printf("[%s] - %s - %s%n", Thread.currentThread().getName(), "setErrorHandler",
                e.getMessage());
      });

    }

    // 故意导出异常测试异常处理器
    context.addApplicationListener(new ApplicationListener<MySpringEvent>() {
      @Override
      public void onApplicationEvent(MySpringEvent event) {
        throw new RuntimeException("故意抛出异常");
      }
    });

    // 发普通事件
    context.publishEvent(new MySpringEvent("Hello,2021"));

    // 关闭应用上下文
    context.close();
  }


}
