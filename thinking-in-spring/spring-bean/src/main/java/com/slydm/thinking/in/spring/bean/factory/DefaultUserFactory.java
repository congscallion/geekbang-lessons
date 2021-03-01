package com.slydm.thinking.in.spring.bean.factory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * {@line UserFactory} 实现
 *
 * @author wangcymy@gmail.com(wangcong) 2020/10/25 下午3:43
 */
public class DefaultUserFactory implements UserFactory, InitializingBean, DisposableBean {

  @PostConstruct
  public void init(){
    System.out.println("@PostConstruct : UserFactory 初始化中... ");
  }

  public void initUserFactory(){
    System.out.println("自定义初始化方法 initUserFactory() : UserFactory 初始化中...");
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    System.out.println("InitializingBean#afterPropertiesSet() : UserFactory 初始化中...");
  }

  @PreDestroy
  public void preDestroy(){
    System.out.println("@PreDestroy : UserFactory 销毁中... ");
  }

  @Override
  public void destroy() throws Exception {
    System.out.println("DisposableBean#destroy : UserFactory 销毁中... ");
  }

  public void doDestroy() throws Exception {
    System.out.println("自定义销毁方法 doDestroy() : UserFactory 销毁中... ");
  }
}
