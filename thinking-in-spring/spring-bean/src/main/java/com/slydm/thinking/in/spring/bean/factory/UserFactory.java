package com.slydm.thinking.in.spring.bean.factory;

import com.slydm.thinking.in.spring.ioc.overview.domain.User;

/**
 * @author wangcymy@gmail.com(wangcong) 2020/10/25 下午3:34
 */
public interface UserFactory {

  default User createUser() {
    return User.createUser();
  }

}
