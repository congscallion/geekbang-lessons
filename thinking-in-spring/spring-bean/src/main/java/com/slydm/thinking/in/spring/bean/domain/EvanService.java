package com.slydm.thinking.in.spring.bean.domain;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wangcymy@gmail.com(wangcong) 2021/1/18 16:43
 */
public class EvanService {

  @Autowired
  TomService tomService;

  public void showTomServiceResult() {
    System.out.println(tomService);
  }


}
