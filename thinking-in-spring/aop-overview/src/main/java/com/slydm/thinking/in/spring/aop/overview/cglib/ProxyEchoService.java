package com.slydm.thinking.in.spring.aop.overview.cglib;

import com.slydm.thinking.in.spring.aop.overview.cglib.helper.EchoService;

/**
 * 静态代理实现类
 *
 * @author wangcong@gmail.com(wangcong) 2021/2/19 17:38
 */
public class ProxyEchoService implements EchoService {

  private final EchoService echoService;

  public ProxyEchoService(
      EchoService echoService) {
    this.echoService = echoService;
  }

//  @Override
//  public String echo(String message) throws IllegalArgumentException {
//    return this.echo(new String[]{message})[0];
//  }

  @Override
  public String[] echo(String[] message) throws NullPointerException {

    long startTime = System.currentTimeMillis();
    String[] result = echoService.echo(message);
    long costTime = System.currentTimeMillis() - startTime;
    System.out.println("echo 方法执行的实现：" + costTime + " ms.");
    return result;
  }
}
