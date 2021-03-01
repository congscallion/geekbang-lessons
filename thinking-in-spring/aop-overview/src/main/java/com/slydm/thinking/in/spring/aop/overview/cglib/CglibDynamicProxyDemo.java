package com.slydm.thinking.in.spring.aop.overview.cglib;

import com.slydm.thinking.in.spring.aop.overview.cglib.helper.DefaultEchoService;
import com.slydm.thinking.in.spring.aop.overview.cglib.helper.EchoService;
import java.lang.reflect.Method;
import net.sf.cglib.core.DebuggingClassWriter;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

/**
 * cglib 动态代理示例
 *
 * @author wangcymy@gmail.com(wangcong) 2021/2/19 17:59
 */
public class CglibDynamicProxyDemo {

  public static void main(String[] args) {

    System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "D:\\class");

    Enhancer enhancer = new Enhancer();
    // 指定需要代理的类
    enhancer.setSuperclass(DefaultEchoService.class);
    // 指定需要代理的接口
     enhancer.setInterfaces(new Class[]{EchoService.class});

    enhancer.setCallback(new MethodInterceptor() {
      @Override
      public Object intercept(Object source, Method method, Object[] args, MethodProxy methodProxy)
          throws Throwable {

        long startTime = System.currentTimeMillis();
        Object result = methodProxy.invokeSuper(source, args);
        long costTime = System.currentTimeMillis() - startTime;
        System.out.println("echo 方法执行的实现：" + costTime + " ms.");
        return result;
      }
    });

    EchoService echoService = (EchoService) enhancer.create();
    echoService.echo(new String[]{"hi"});
  }

}
