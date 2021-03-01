package com.slydm.thinking.in.spring.aop.overview.cglib;

import com.slydm.thinking.in.spring.aop.overview.cglib.helper.DefaultEchoService;
import com.slydm.thinking.in.spring.aop.overview.cglib.helper.EchoService;

/**
 * 静态代理示例
 *
 * @author wangcymy@gmail.com(wangcong) 2021/2/19 17:37
 */
public class StaticProxyDemo {

  public static void main(String[] args) {

    EchoService echoService = new ProxyEchoService(new DefaultEchoService());

    echoService.echo(new String[]{"Tom", "Jek"});

  }

}
