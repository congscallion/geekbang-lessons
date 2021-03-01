package com.slydm.thinking.in.spring.bean.lifecycle;

import com.slydm.thinking.in.spring.ioc.overview.domain.User;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

/**
 * @author wangcymy@gmail.com(wangcong) 2020/12/13 下午6:11
 */
public class UserHolder implements BeanNameAware, BeanClassLoaderAware, BeanFactoryAware,
    EnvironmentAware, InitializingBean, SmartInitializingSingleton, DisposableBean {

  private final User user;
  private int number;
  private String description;
  private String beanName;
  private ClassLoader classLoader;
  private BeanFactory beanFactory;
  private Environment environment;

  public UserHolder(User user) {
    this.user = user;
  }

  public int getNumber() {
    return number;
  }

  public void setNumber(int number) {
    this.number = number;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  @PostConstruct
  public void initPostConstruct() {
    this.description = "the user holder V4";
    System.out.println("initPostConstruct:description=" + description);

  }

  @Override
  public void afterPropertiesSet() throws Exception {

    this.description = "the user holder V5";
    System.out.println("afterPropertiesSet:description=" + description);
  }

  public void init() {
    this.description = "the user holder V6";
    System.out.println("init:description=" + description);
  }

  @PreDestroy
  public void preDestroy() {
    this.description = "the user holder V10";
    System.out.println("preDestroy:description=" + description);
  }

  @Override
  public void destroy() throws Exception {
    this.description = "the user holder V11";
    System.out.println("destroy:description=" + description);
  }

  public void doDestroy() throws Exception {
    this.description = "the user holder V12";
    System.out.println("doDestroy:description=" + description);
  }


  @Override
  public String toString() {
    return "UserHolder{" +
        "user=" + user +
        ", number=" + number +
        ", description='" + description + '\'' +
        ", beanName='" + beanName + '\'' +
        '}';
  }

  @Override
  public void setBeanName(String name) {
    this.beanName = name;
  }

  @Override
  public void setBeanClassLoader(ClassLoader classLoader) {
    this.classLoader = classLoader;
  }

  @Override
  public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
    this.beanFactory = beanFactory;
  }


  @Override
  public void setEnvironment(Environment environment) {
    this.environment = environment;
  }


  @Override
  public void afterSingletonsInstantiated() {
    this.description = "the user holder V8";
    System.out.println("afterSingletonsInstantiated:description=" + description);
  }


}
