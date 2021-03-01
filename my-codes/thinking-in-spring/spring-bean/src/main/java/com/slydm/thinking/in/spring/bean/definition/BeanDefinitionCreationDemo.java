/*
 *
 *  * Licensed to the Apache Software Foundation (ASF) under one or more
 *  * contributor license agreements.  See the NOTICE file distributed with
 *  * this work for additional information regarding copyright ownership.
 *  * The ASF licenses this file to You under the Apache License, Version 2.0
 *  * (the "License"); you may not use this file except in compliance with
 *  * the License.  You may obtain a copy of the License at
 *  *
 *  *     http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package com.slydm.thinking.in.spring.bean.definition;

import com.slydm.thinking.in.spring.ioc.overview.domain.User;
import java.util.Map;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.AnnotatedBeanDefinitionReader;
import org.springframework.context.annotation.Scope;

/**
 * {@link org.springframework.beans.factory.config.BeanDefinition} 构建示例
 *
 * @author wangcymy@gmail.com(wangcong) 2020/10/23 下午10:00
 */
public class BeanDefinitionCreationDemo {

  public static void main(String[] args) {

    // 1.通过 BeanDefinitionBuilder 构建
    BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder
        .genericBeanDefinition(User.class);

    beanDefinitionBuilder.addPropertyValue("id", 1l)
        .addPropertyValue("name", "wangcong");

    BeanDefinition beanDefinition = beanDefinitionBuilder.getBeanDefinition();
    System.out.println(beanDefinition);

    // 2.通过 AbstractBeanDefinition 以及派生类
    GenericBeanDefinition genericBeanDefinition = new GenericBeanDefinition();
    // 设置bean类型
    genericBeanDefinition.setBeanClass(User.class);

    // 通过 MutablePropertyValues 批量操作属性
    MutablePropertyValues propertyValues = new MutablePropertyValues();
    propertyValues.add("id", 2l).add("name", "wangcong2");
    // 设置属性
    genericBeanDefinition.setPropertyValues(propertyValues);
    System.out.println(genericBeanDefinition);

    DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
    beanFactory.registerBeanDefinition("user", beanDefinition);
    beanFactory.registerBeanDefinition("user2", genericBeanDefinition);

    AnnotatedBeanDefinitionReader reader = new AnnotatedBeanDefinitionReader(beanFactory);
    reader.register(A.class);
    reader.register(B.class);

    User user = beanFactory.getBean("user", User.class);
    System.out.println("user 对象:" + user);
    User user2 = beanFactory.getBean("user", User.class);
    System.out.println("user2 对象:" + user2);
    Map<String, User> beansOfType = beanFactory.getBeansOfType(User.class);
    System.out.println("User类型所有Beans:" + beansOfType);

    System.out.println(
        "prototype 范围的 A 类型: " + (beanFactory.getBean(A.class) == beanFactory.getBean(A.class)));
    System.out.println(
        "singleton 范围的 B 类型: " + (beanFactory.getBean(B.class) == beanFactory.getBean(B.class)));


  }


  @Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
  static class A {

    private String email;

    public A() {
    }

    public A(String email) {
      this.email = email;
    }

    public String getEmail() {
      return email;
    }

    public void setEmail(String email) {
      this.email = email;
    }

    @Override
    public String toString() {
      return "A{" +
          "email='" + email + '\'' +
          '}';
    }
  }


  static class B {

    private String email;

    public B() {
    }

    public B(String email) {
      this.email = email;
    }

    public String getEmail() {
      return email;
    }

    public void setEmail(String email) {
      this.email = email;
    }

    @Override
    public String toString() {
      return "B{" +
          "email='" + email + '\'' +
          '}';
    }
  }

}
