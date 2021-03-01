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

package com.slydm.thinking.in.spring.ioc.overview.container;

import com.slydm.thinking.in.spring.ioc.overview.domain.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * 注解能力 {@link ApplicationContext} 作为IOC容器
 *
 * @author wangcymy@gmail.com(wangcong) 2020/10/23 下午9:12
 */
public class AnnotationApplicationContextAsIocContainer {

  public static void main(String[] args) {

    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

    applicationContext.register(AnnotationApplicationContextAsIocContainer.class);
    applicationContext.refresh();

    User user = applicationContext.getBean(User.class);

    System.out.println(user);

    // 关闭应用
    applicationContext.close();

  }

  @Bean
  public User user() {
    User user = new User();
    user.setId(1l);
    user.setName("wangcong");

    return user;
  }

}
