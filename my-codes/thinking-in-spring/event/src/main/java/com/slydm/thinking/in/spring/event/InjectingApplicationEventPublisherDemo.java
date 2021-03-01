package com.slydm.thinking.in.spring.event;

import javax.annotation.PostConstruct;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.core.ResolvableType;

/**
 * 注入{@link ApplicationEventPublisher} 示例
 *
 *
 * <p>
 * 注意，本类实现了 {@link ApplicationEventPublisherAware}
 * 接口企图接收 {@link ApplicationEventPublisher} 实例回调, 但是又实现了 {@link BeanPostProcessor} 接口，这会导致
 * {@link ApplicationEventPublisherAware#setApplicationEventPublisher(ApplicationEventPublisher)} 方法会在
 * {@link AbstractApplicationContext#initApplicationEventMulticaster()} 方法之前回调，此时 spring 事件相关Bean未实例化。
 * 此时发布事件会异常。
 *
 * <p>
 * 为什么会导致 {@link ApplicationEventPublisherAware#setApplicationEventPublisher(ApplicationEventPublisher)} 方法提前被调用？
 * 原因: 在 容器启动阶段, {@link AbstractApplicationContext#prepareBeanFactory(ConfigurableListableBeanFactory)} 方法会向容器注入
 * {@link ApplicationContextAwareProcessor} 实例。接着在 {@link AbstractApplicationContext#registerBeanPostProcessors(ConfigurableListableBeanFactory)}
 * 方法同注册 {@link BeanPostProcessor}实例。　{@link BeanPostProcessor} 实例通过 {@link AbstractBeanFactory#getBean(String)} 方法获得。
 * 由于{@link InjectingApplicationEventPublisherDemo} 类也实现了{@link BeanPostProcessor}接口，因此，本类也会被实例化。
 * 在实例化过程{@link AbstractAutowireCapableBeanFactory#initializeBean(java.lang.String, java.lang.Object, org.springframework.beans.factory.support.RootBeanDefinition)}
 * 方法内，会完成{@link AbstractAutowireCapableBeanFactory#applyBeanPostProcessorsBeforeInitialization(java.lang.Object, java.lang.String)}
 * 回调。 此时，由于 {@link ApplicationContextAwareProcessor} 也是 {@link BeanPostProcessor}，
 * 因此 {@link ApplicationContextAwareProcessor#postProcessBeforeInitialization(Object, String)}方法会被触发。
 * 该方法检查到{@link InjectingApplicationEventPublisherDemo}实现了{@link ApplicationEventPublisherAware} 接口，
 * 因此触发{@link ApplicationEventPublisherAware#setApplicationEventPublisher(ApplicationEventPublisher)} 方法被调用。
 *
 *
 * <p>
 * 以上问题在 spring 4.1 之后得到解决。
 * {@link AbstractApplicationContext#publishEvent(Object, ResolvableType)}发布事件时，当 ApplicationEventMulticaster
 * 没有实例化，就把当前事件发布事件存放到 {@link AbstractApplicationContext#earlyApplicationEvents} 容器中。
 * 等到 ApplicationEventMulticaster 实例化后。 在{@link AbstractApplicationContext#registerListeners()}方法中回放所有事件。
 *
 * <p>
 * 总体解决思路为，在事件发布器实例化前，将要发布的事件保存到一个 Set 容器中，等事件发布器实例化后，再从容器中取出事件，依次发布。
 *
 * @author wangcymy@gmail.com(wangcong) 2021/1/18 11:14
 */
public class InjectingApplicationEventPublisherDemo implements ApplicationContextAware,
    ApplicationEventPublisherAware, BeanPostProcessor {

  @Autowired
  private ApplicationContext applicationContext;

  @Autowired
  private ApplicationEventPublisher applicationEventPublisher;


  @PostConstruct
  public void init() {

    applicationEventPublisher
        .publishEvent(new MySpringEvent("The event from @Autowired ApplicationEventPublisher"));

    applicationContext
        .publishEvent(new MySpringEvent("The event from @Autowired ApplicationContext"));


  }


  public static void main(String[] args) {

    AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

    context.register(InjectingApplicationEventPublisherDemo.class);

    context.addApplicationListener(new MySpringEventListener());

    context.refresh();

    context.close();

  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    applicationContext
        .publishEvent(new MySpringEvent("The event from ApplicationContextAware"));
  }

  @Override
  public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
    applicationEventPublisher
        .publishEvent(new MySpringEvent("The event from ApplicationEventPublisherAware"));
  }
}
