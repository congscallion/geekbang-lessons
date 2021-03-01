package com.slydm.thinking.in.spring.aop.overview.cglib;

import java.lang.reflect.Method;

/**
 * （方法返回）后置拦截器
 *
 * @author wangcymy@gmail.com(wangcong) 2021/2/19 9:46
 */
public interface AfterReturnInterceptor {

  /**
   * 后置执行
   *
   * @param returnResult 执行方法返回结果
   */
  Object after(Object proxy, Method method, Object[] args, Object returnResult);

}
