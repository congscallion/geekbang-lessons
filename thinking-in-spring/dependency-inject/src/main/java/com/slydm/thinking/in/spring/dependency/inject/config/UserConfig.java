package com.slydm.thinking.in.spring.dependency.inject.config;

import com.slydm.thinking.in.spring.dependency.inject.annotation.UserGroup;
import com.slydm.thinking.in.spring.ioc.overview.domain.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wangcymy@gmail.com(wangcong) 2020/11/2 下午10:43
 */
@Configuration
public class UserConfig {

  private static User createUser(Long id) {
    User user = new User();
    user.setId(id);
    return user;
  }


  @Bean
  @Qualifier
  public User user1() {
    return createUser(7L);
  }

  @Bean
  @Qualifier
  public User user2() {
    return createUser(8L);
  }

  @Bean
  @UserGroup
  public User user3() {
    return createUser(9L);
  }

  @Bean
  @UserGroup
  public User user4() {
    return createUser(10L);
  }

}
