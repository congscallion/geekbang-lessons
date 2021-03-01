package com.slydm.thinking.in.spring.event;

import static java.util.concurrent.Executors.newSingleThreadExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

/**
 * 注解驱动异步事件处理器示例
 *
 * @author wangcymy@gmail.com(wangcong) 2021/1/19 10:32
 */
// 激活 Spring 异步特性
@EnableAsync
public class AnnotatedAsyncEventHandlerDemo {


  public static void main(String[] args) {

    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

    // 1. 注册当前类作为 Configuration Class
    context.register(AnnotatedAsyncEventHandlerDemo.class);

    // 2.启动 Spring 应用上下文
    context.refresh(); // 初始化 ApplicationEventMulticaster

    // 3. 发布自定义 Spring 事件
    context.publishEvent(new MySpringEvent("Hello,World"));

    // 4. 关闭 Spring 应用上下文（ContextClosedEvent）
    context.close();


  }

  // 同步 -> 异步
  @Async
  @EventListener
  public void onEvent(MySpringEvent event) {
    System.out
        .printf("[%s] - %s - %s%n", Thread.currentThread().getName(), "onEvent",
            event.getSource());
  }


  @Bean
  public Executor taskExecutor() {
    ExecutorService taskExecutor = newSingleThreadExecutor(
        new CustomizableThreadFactory("my-spring-event-thread-pool-async"));
    return taskExecutor;
  }

}
