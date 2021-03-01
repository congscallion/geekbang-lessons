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

package com.slydm.thinking.in.spring.ioc.overview.repository;

import com.slydm.thinking.in.spring.ioc.overview.domain.User;
import java.util.Collection;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.ApplicationContext;

/**
 * @author wangcymy@gmail.com(wangcong) 2020/10/22 下午9:13
 */
public class UserRepository {

  private Collection<User> user;

  private BeanFactory beanFactory;

  private ObjectFactory<ApplicationContext> objectFactory;

  public Collection<User> getUser() {
    return user;
  }

  public void setUser(Collection<User> user) {
    this.user = user;
  }

  public BeanFactory getBeanFactory() {
    return beanFactory;
  }

  public void setBeanFactory(BeanFactory beanFactory) {
    this.beanFactory = beanFactory;
  }

  public ObjectFactory<ApplicationContext> getObjectFactory() {
    return objectFactory;
  }

  public void setObjectFactory(
      ObjectFactory<ApplicationContext> objectFactory) {
    this.objectFactory = objectFactory;
  }
}
