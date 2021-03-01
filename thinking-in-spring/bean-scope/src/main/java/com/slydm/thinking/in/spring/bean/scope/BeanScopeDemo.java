package com.slydm.thinking.in.spring.bean.scope;

import com.slydm.thinking.in.spring.ioc.overview.domain.User;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

/**
 * spring bean scope 示例
 *
 * <p> spring 容器没有办法管理prototype Bean 的完整生命周期，也没有办法记录实例的存在。销毁回调方法将不会执行，
 * 可以利用 BeanPostProcessor 进行清扫工作。
 *
 * <p> Singleton Bean 无论依赖查找还是依赖注入，均为同一个对象。
 * Prototype Bean 无论依赖查找还是依赖注入，均为新生成的对象。
 *
 * <p> 如果依赖注入集合类型的对象 ， Singleton Bean 和 Prototype Bean 均会存在一个； Prototype Bean 有别于
 * 其它地方的依赖注入 Prototype Bean.
 *
 * <p> 无论是 Singleton 还是 Prototype Bean 均会执行初始化方法回调； 不过仅 Singleton Bean 会执行销毁方法回调。
 *
 * @author wangcymy@gmail.com(wangcong) 2020/11/19 下午10:03
 */
public class BeanScopeDemo implements DisposableBean {

  @Autowired
  @Qualifier("singletonUser")
  private User singletonUser;
  @Autowired
  @Qualifier("prototypeUser")
  private User prototypeUser;
  @Autowired
  @Qualifier("prototypeUser")
  private User prototypeUser1;
  @Autowired
  @Qualifier("prototypeUser")
  private User prototypeUser2;
  @Autowired
  private Map<String, User> users;
  @Autowired
  private ConfigurableListableBeanFactory beanFactory;

  @Bean
  public static User singletonUser() {
    return createUser();
  }

  @Bean
  @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
  public static User prototypeUser() {
    return createUser();
  }

  private static User createUser() {
    try {
      TimeUnit.MICROSECONDS.sleep(1);
    } catch (InterruptedException e) {
    }
    User user = new User();
    user.setId(System.currentTimeMillis());
    return user;

  }

  public static void main(String[] args) {

    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
    applicationContext.register(BeanScopeDemo.class);

    applicationContext.addBeanFactoryPostProcessor(beanFactory -> {

      /**
       * 不建议这样做，  这里销毁 bean spring 容器就彻底找不到该 Bean, 可能会引起其它异常。
       *
       * 可以通过实现{@link DisposableBean}接口，在 destroy() 方法中销毁 Prototype Bean
       *
       */
      beanFactory.addBeanPostProcessor(new BeanPostProcessor() {
        @Override
        public Object postProcessAfterInitialization(Object bean, String beanName)
            throws BeansException {

          System.out.printf("%s Bean 名称：%s 在初始化后回调...%n", bean.getClass().getName(), beanName);

          return bean;
        }
      });

    });

    applicationContext.refresh();

    scopedBeansByLookup(applicationContext);
    scopedBeansByInject(applicationContext);

    applicationContext.close();


  }

  private static void scopedBeansByInject(AnnotationConfigApplicationContext applicationContext) {

    BeanScopeDemo beanScopeDemo = applicationContext.getBean(BeanScopeDemo.class);

    System.out.println("beanScopeDemo.singletonUser=" + beanScopeDemo.singletonUser);
    System.out.println("beanScopeDemo.prototypeUser=" + beanScopeDemo.prototypeUser);
    System.out.println("beanScopeDemo.prototypeUser1=" + beanScopeDemo.prototypeUser1);
    System.out.println("beanScopeDemo.prototypeUser2=" + beanScopeDemo.prototypeUser2);
    System.out.println("beanScopeDemo.users=" + beanScopeDemo.users);


  }

  private static void scopedBeansByLookup(AnnotationConfigApplicationContext applicationContext) {

    for (int i = 0; i < 3; i++) {
      User singletonUser = applicationContext.getBean("singletonUser", User.class);
      System.out.println("singletonUser=" + singletonUser);

      User prototypeUser = applicationContext.getBean("prototypeUser", User.class);
      System.out.println("prototypeUser=" + prototypeUser);
    }

  }


  @Override
  public void destroy() throws Exception {

    System.out.println("当前 BeanScopeDemo Bean 正在销毁中...");

    this.prototypeUser.destroy();
    this.prototypeUser1.destroy();
    this.prototypeUser2.destroy();
    for (Map.Entry<String, User> entry : users.entrySet()) {

      String beanName = entry.getKey();
      BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);
      if (beanDefinition.isPrototype()) {
        User user = entry.getValue();
        user.destroy();
      }

    }
    System.out.println("当前 BeanScopeDemo Bean 销毁完成");


  }
}
