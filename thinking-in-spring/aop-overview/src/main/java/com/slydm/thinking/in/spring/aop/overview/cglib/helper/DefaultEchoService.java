package com.slydm.thinking.in.spring.aop.overview.cglib.helper;

/**
 * @author wangcymy@gmail.com(wangcong) 2021/2/19 9:20
 */
public class DefaultEchoService implements EchoService {

//  @Override
//  public String echo(String message) throws IllegalArgumentException {
//    return message;
//  }

  @Override
  public String[] echo(String[] message) throws NullPointerException {
    return message;
  }
}
