package com.slydm.thinking.in.spring.ioc.overview.domain;

/**
 * @author wangcymy@gmail.com(wangcong) 2021/1/8 11:05
 */
public class Company {

  private String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "Company{" +
        "name='" + name + '\'' +
        '}';
  }
}
