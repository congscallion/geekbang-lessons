package com.slydm.thinking.in.spring.bean.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author wangcymy@gmail.com(wangcong) 2021/1/18 16:43
 */
@Component
public class PersonService {

  @Autowired
  EvanService evanService;

  public void showEvanServiceResult() {
    System.out.println(evanService);
    System.out.println(evanService.tomService);
  }


}
