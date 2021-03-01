package com.slydm.thinking.in.spring.aop.features;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;

/**
 * AspectJ 示例 基于 api 方式
 *
 * @author wangcymy@gmail.com(wangcong) 2021/3/1 11:53
 */
public class AspectJAnnotationUsingApiDemo {

  public static void main(String[] args) {

    // 创建被代理对象, HashMap
    AspectJProxyFactory proxyFactory = new AspectJProxyFactory(new HashMap<>(8));

    proxyFactory.addAdvice(new MethodBeforeAdvice() {
      @Override
      public void before(Method method, Object[] args, Object target) throws Throwable {
        if ("put".equals(method.getName()) && args.length == 2) {
          System.out.printf("当前存放是, key: %s, value: %s%n", args[0], args[1]);
        }
      }
    });

    Map<String, Object> proxy = proxyFactory.getProxy();
    proxy.put("1", "A");

    System.out.println(proxy.get("1"));


  }

}
