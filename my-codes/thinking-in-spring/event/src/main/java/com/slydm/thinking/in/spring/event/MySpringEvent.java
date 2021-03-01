package com.slydm.thinking.in.spring.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author wangcymy@gmail.com(wangcong) 2021/1/18 10:54
 */
public class MySpringEvent extends ApplicationEvent {

  public MySpringEvent(String message) {
    super(message);
  }

  @Override
  public String getSource() {
    return (String) super.getSource();
  }

  public String getMessage() {
    return getSource();
  }
}
