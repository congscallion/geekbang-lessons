package com.slydm.thinking.in.spring.aop.overview.cglib;

import java.lang.reflect.Method;

/**
 * 前置拦截器
 *
 * @author wangcymy@gmail.com(wangcong) 2021/2/19 9:44
 */
public interface BeforeInterceptor {

  /**
   * 前置执行
   */
  Object before(Object proxy, Method method, Object[] args);

}
