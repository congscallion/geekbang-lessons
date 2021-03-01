package com.slydm.thinking.in.spring.aop.overview.cglib.helper;

/**
 * @author wangcymy@gmail.com(wangcong) 2021/2/19 9:20
 */
public interface EchoService {

//  String echo(String message) throws IllegalArgumentException;

  String[] echo(String[] message) throws NullPointerException;

}
