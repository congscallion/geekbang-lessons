package com.slydm.thinking.in.spring.aop.overview.cglib;

import java.lang.reflect.Method;

/**
 * 异常拦截器
 *
 * @author wangcymy@gmail.com(wangcong) 2021/2/19 9:45
 */
public interface ExceptionInterceptor {

  /**
   * @param throwable 异常信息
   */
  void intercept(Object proxy, Method method, Object[] args, Throwable throwable);

}
