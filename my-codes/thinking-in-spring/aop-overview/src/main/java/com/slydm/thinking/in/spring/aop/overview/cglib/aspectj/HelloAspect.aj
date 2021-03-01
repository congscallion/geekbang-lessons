package com.slydm.thinking.in.spring.aop.overview.cglib.aspectj;

/**
 *
 * aspect 文件，定义 pointcut 与 advice
 *
 *@author wangcymy@gmail.com(wangcong) 2021/2/20 14:19
 */
public aspect HelloAspect {

  pointcut HelloWorldPointCut():
      execution(void HelloWorld.main(..));

  before():HelloWorldPointCut(){
    System.out.println("before main method...");
  }


}
