package com.slydm.thinking.in.spring.ioc.overview.domain;

import com.slydm.thinking.in.spring.ioc.overview.enums.City;
import java.util.List;
import java.util.Properties;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.springframework.beans.factory.BeanNameAware;

/**
 * @author wangcymy@gmail.com(wangcong) 2020/10/19 下午11:08
 */
public class User implements BeanNameAware {

  private Long id;
  private String name;
  private String beanName;
  private City city;
  private List<City> workCities;
  private List<City> lifeCities;
  private String configFileLocation;
  private Company company;
  private Properties context;

  public Properties getContext() {
    return context;
  }

  public void setContext(Properties context) {
    this.context = context;
  }

  public Company getCompany() {
    return company;
  }

  public void setCompany(Company company) {
    this.company = company;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public City getCity() {
    return city;
  }

  public void setCity(City city) {
    this.city = city;
  }

  public List<City> getWorkCities() {
    return workCities;
  }

  public void setWorkCities(
      List<City> workCities) {
    this.workCities = workCities;
  }

  public List<City> getLifeCities() {
    return lifeCities;
  }

  public void setLifeCities(
      List<City> lifeCities) {
    this.lifeCities = lifeCities;
  }

  public String getConfigFileLocation() {
    return configFileLocation;
  }

  public void setConfigFileLocation(String configFileLocation) {
    this.configFileLocation = configFileLocation;
  }


  public static User createUser() {
    User user = new User();
    user.setId(10L);
    user.setName("wangcong10");

    return user;
  }

  @Override
  public void setBeanName(String name) {
    this.beanName = name;
  }

  @PostConstruct
  public void init() {
    System.out.println("User Bean [" + beanName + "] 初始化...");
  }

  @PreDestroy
  public void destroy() {
    System.out.println("User Bean [" + beanName + "] 销毁中...");
  }


  @Override
  public String toString() {
    return "User{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", beanName='" + beanName + '\'' +
        ", city=" + city +
        ", workCities=" + workCities +
        ", lifeCities=" + lifeCities +
        ", configFileLocation='" + configFileLocation + '\'' +
        ", company=" + company +
        ", context=" + context +
        '}';
  }
}
