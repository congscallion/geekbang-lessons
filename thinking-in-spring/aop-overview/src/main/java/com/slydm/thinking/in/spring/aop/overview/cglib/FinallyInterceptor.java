package com.slydm.thinking.in.spring.aop.overview.cglib;

import java.lang.reflect.Method;

/**
 * 最终执行后置拦截器
 *
 * @author wangcymy@gmail.com(wangcong) 2021/2/19 9:45
 */
public interface FinallyInterceptor {

  /**
   * 最终执行
   *
   * @param returnResult 执行方法返回结果
   */
  Object finalize(Object proxy, Method method, Object[] args, Object returnResult);

}
