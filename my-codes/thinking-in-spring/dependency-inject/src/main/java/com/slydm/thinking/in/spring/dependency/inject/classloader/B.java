package com.slydm.thinking.in.spring.dependency.inject.classloader;

/**
 * @author wangcymy@gmail.com(wangcong) 2021/2/18 15:25
 */
public class B {

  private A a;

  public B(A a) {
    this.a = a;
  }

  public A getA() {
    return a;
  }
}
