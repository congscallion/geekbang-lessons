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

import static org.springframework.beans.factory.support.BeanDefinitionBuilder.genericBeanDefinition;

import com.slydm.thinking.in.spring.bean.definition.AnnotationBeanDefinitionDemo.Config;
import com.slydm.thinking.in.spring.ioc.overview.domain.User;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

/**
 * 注解能力 {@link org.springframework.context.ApplicationContext} 作为IOC容器。 并使用API的方式注册 Bean。
 *
 * @author wangcymy@gmail.com(wangcong) 2020/10/24 下午10:49
 */

@Import(Config.class)
public class AnnotationBeanDefinitionDemo {

  public static void main(String[] args) {

    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
    applicationContext.register(AnnotationBeanDefinitionDemo.class);
    applicationContext.refresh();

    registerBeanDefinition(applicationContext, "user3", User.class);
    registerBeanDefinition(applicationContext, User.class);

    System.out.println("Config 类型的所有 Beans:" + applicationContext.getBeansOfType(Config.class));
    System.out.println("User 类型的所有 Beans: " + applicationContext.getBeansOfType(User.class));

    applicationContext.close();
  }

  /**
   * 以命名方式注入
   */
  public static void registerBeanDefinition(BeanDefinitionRegistry registry, String beanName,
      Class<?> beanClass) {

    BeanDefinitionBuilder beanDefinitionBuilder = genericBeanDefinition(beanClass);
    beanDefinitionBuilder.addPropertyValue("id", 3L)
        .addPropertyValue("name", "wangcong3");
    registry.registerBeanDefinition(beanName, beanDefinitionBuilder.getBeanDefinition());
  }

  /**
   * 以非命名方式注入
   */
  public static void registerBeanDefinition(BeanDefinitionRegistry registry, Class<?> beanClass) {

    BeanDefinitionBuilder beanDefinitionBuilder = genericBeanDefinition(beanClass);
    beanDefinitionBuilder.addPropertyValue("id", 2L)
        .addPropertyValue("name", "wangcong2");
    BeanDefinitionReaderUtils
        .registerWithGeneratedName(beanDefinitionBuilder.getBeanDefinition(), registry);
  }


  @Component
  public static class Config {

    @Bean(name = {"user", "wangcong-user"})
    public User user() {
      User user = new User();
      user.setName("wangcong");
      user.setId(1L);
      return user;
    }

  }

}
