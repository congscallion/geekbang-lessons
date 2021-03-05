package com.slydm.thinking.in.spring.aop.features;

import com.slydm.thinking.in.spring.aop.features.interceptor.EchoServiceMethodInterceptor;
import com.slydm.thinking.in.spring.aop.overview.cglib.helper.DefaultEchoService;
import com.slydm.thinking.in.spring.aop.overview.cglib.helper.EchoService;
import org.springframework.aop.framework.ProxyFactory;

/**
 * {@link ProxyFactory} 示例
 *
 * @author 72089101@vivo.com(wangcong) 2021/3/1 16:38
 */
public class ProxyFactoryDemo {

  public static void main(String[] args) {

    // 被代理对象
    DefaultEchoService defaultEchoService = new DefaultEchoService();

    // 创建 ProxyFactory 实例
    ProxyFactory proxyFactory = new ProxyFactory(defaultEchoService);
    // 添加 advise
    proxyFactory.addAdvice(new EchoServiceMethodInterceptor());
    // 获取代理对象
    EchoService echoService = (EchoService) proxyFactory.getProxy();

    echoService.echo(new String[]{"hello"});

  }

}
