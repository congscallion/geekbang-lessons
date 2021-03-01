package com.slydm.thinking.in.spring.aop.overview.cglib;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * jdk 动态代理 示例
 *
 * @author wangcymy@gmail.com(wangcong) 2021/2/9 17:33
 */
public class JdkDynamicProxyDemo {

  public static void main(String[] args) {
    helper();
    dynamicProxy();
  }

  static void helper() {
    // 该设置用于输出jdk动态代理产生的类
    System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
  }

  static void dynamicProxy() {

    Map target = new HashMap();
    Map instance = (Map) Proxy
        .newProxyInstance(ClassLoader.getSystemClassLoader(), new Class[]{Map.class},
            new MyInvocationHandler(target));

    instance.put(1, 2);
    instance.put("hello", "dynamic proxy");

    System.out.println(instance.get(1));
    System.out.println(instance.get("hello"));

  }

  static class MyInvocationHandler implements InvocationHandler {

    private Map target;

    public MyInvocationHandler(Map target) {
      this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
      long start = System.currentTimeMillis();
      System.out.printf("Invoked method: %s", method.getName());
      Object result = method.invoke(target, args);
      System.out.printf(", cost: %s%n", (System.currentTimeMillis() - start));
      return result;
    }
  }


}
