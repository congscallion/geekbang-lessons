package com.slydm.thinking.in.spring.dependency.inject.classloader;

/**
 * @author wangcymy@gmail.com(wangcong) 2021/2/18 15:25
 */
public class C extends AbstractPlugin implements I {

  private A a;
  
  public C(A a) {
    this.a = a;
  }

  public A getA() {
    return a;
  }


  @Override
  public void doThing2() {
    System.out.println(a);
  }

  @Override
  public void doThing() {
    System.out.println(a);
  }
}
