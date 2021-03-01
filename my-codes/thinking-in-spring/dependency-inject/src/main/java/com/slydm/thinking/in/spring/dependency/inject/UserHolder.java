package com.slydm.thinking.in.spring.dependency.inject;

import com.slydm.thinking.in.spring.ioc.overview.domain.User;

/**
 * @author wangcymy@gmail.com(wangcong) 2020/10/29 下午9:40
 */
public class UserHolder {

  private User user;

  public UserHolder() {
  }

  public UserHolder(User user) {
    this.user = user;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  @Override
  public String toString() {
    return "UserHolder{" +
        "user=" + user +
        '}';
  }
}
