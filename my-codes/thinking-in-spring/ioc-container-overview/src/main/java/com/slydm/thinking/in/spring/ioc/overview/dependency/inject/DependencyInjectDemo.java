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

package com.slydm.thinking.in.spring.ioc.overview.dependency.inject;

import com.slydm.thinking.in.spring.ioc.overview.repository.UserRepository;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 依赖注入示例
 *
 * @author wangcymy@gmail.com(wangcong) 2020/10/22 下午9:15
 */
public class DependencyInjectDemo {

  public static void main(String[] args) {

    BeanFactory beanFactory = new ClassPathXmlApplicationContext(
        "classpath:META-INF/dependency-inject-context.xml");

    UserRepository userRepository = beanFactory.getBean(UserRepository.class);
    System.out.println(userRepository.getUser());

    // 容器注入的bean factory 与当前 bean factory 不一样
    System.out.println(userRepository.getBeanFactory() == beanFactory);
    System.out.println(userRepository.getBeanFactory());

    // application context 与 bean facotry 一样
    System.out.println(beanFactory == userRepository.getObjectFactory().getObject());

  }

}
