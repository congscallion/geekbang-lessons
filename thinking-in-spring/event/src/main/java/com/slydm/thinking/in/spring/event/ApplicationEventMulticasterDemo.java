package com.slydm.thinking.in.spring.event;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

/**
 * {@link ApplicationEventMulticaster} 示例
 *
 * {@link ApplicationEventMulticaster} 唯一实现类 {@link SimpleApplicationEventMulticaster}
 *
 * <p>
 * 当 {@link SimpleApplicationEventMulticaster#taskExecutor}属性不为空时，使用异步模式发布事件；为空时，使用同步模式发布事件。
 *
 * 在 Spring 环境中，只声明一个 BeanName 为 "taskExecutor" 的{@link Executor} 实例，容器会自动装到 {@link SimpleApplicationEventMulticaster}
 * 实例上。即可完成异步模式配置
 *
 * @author wangcymy@gmail.com(wangcong) 2021/1/18 17:38
 * @see ApplicationEventMulticasterDemo
 */
public class ApplicationEventMulticasterDemo {


  public static void main(String[] args) throws InterruptedException {

    SimpleApplicationEventMulticaster multicaster = new SimpleApplicationEventMulticaster();

    multicaster.addApplicationListener(new MySpringEventListener());
    multicaster.addApplicationListener(new MySpringEventListener());

    ExecutorService executorService = Executors
        .newSingleThreadExecutor(new CustomizableThreadFactory("my-spring-event-pool"));

    multicaster.setTaskExecutor(executorService);

    multicaster.multicastEvent(new MySpringEvent("hello,world!"));

    boolean b = executorService.awaitTermination(2, TimeUnit.SECONDS);
    if (!b) {
      executorService.shutdownNow();
    }

  }
}
