package com.slydm.thinking.in.spring.aop.features.interceptor;

import com.slydm.thinking.in.spring.aop.features.AopXmlDemo;
import com.slydm.thinking.in.spring.aop.overview.cglib.helper.EchoService;
import java.lang.reflect.Method;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * {@link EchoService} 方法拦截器示例
 *
 * @author wangcymy@gmail.com(wangcong) 2021/2/20 18:34
 * @see EchoService
 * @see AopXmlDemo
 */
public class EchoServiceMethodInterceptor implements MethodInterceptor {

  @Override
  public Object invoke(MethodInvocation invocation) throws Throwable {

    Method method = invocation.getMethod();
    System.out.println("拦截 EchoService的方法: " + method);
    return invocation.proceed();
  }
}
