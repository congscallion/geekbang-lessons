package com.slydm.thinking.in.spring.configuration.metadata;

import org.springframework.beans.factory.xml.NamespaceHandler;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * "users.xsd" {@link NamespaceHandler} 实现
 *
 * @author wangcymy@gmail.com(wangcong) 2020/12/28 11:34
 */
public class UsersNamespaceHandler extends NamespaceHandlerSupport {

  @Override
  public void init() {
    // 将 "user" 元素注册对应的 BeanDefinitionParser 实现
    registerBeanDefinitionParser("user", new UserBeanDefinitionParser());
  }

}
