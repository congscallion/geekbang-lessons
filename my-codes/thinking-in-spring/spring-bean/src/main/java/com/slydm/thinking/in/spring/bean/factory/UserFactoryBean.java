package com.slydm.thinking.in.spring.bean.factory;

import com.slydm.thinking.in.spring.ioc.overview.domain.User;
import org.springframework.beans.factory.FactoryBean;

/**
 * {@link User} Bean 的 {@link org.springframework.beans.factory.FactoryBean} 的实现
 *
 * @author wangcymy@gmail.com(wangcong) 2020/10/25 下午3:52
 */
public class UserFactoryBean implements FactoryBean<User> {

  @Override
  public User getObject() throws Exception {
    return User.createUser();
  }

  @Override
  public Class<?> getObjectType() {
    return User.class;
  }
}
