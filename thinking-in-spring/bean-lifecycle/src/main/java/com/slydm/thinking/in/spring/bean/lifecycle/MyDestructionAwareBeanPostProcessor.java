package com.slydm.thinking.in.spring.bean.lifecycle;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.DestructionAwareBeanPostProcessor;
import org.springframework.util.ObjectUtils;

/**
 * @author wangcymy@gmail.com(wangcong) 12/19/20 12:20 AM
 */
public class MyDestructionAwareBeanPostProcessor implements DestructionAwareBeanPostProcessor {

  @Override
  public void postProcessBeforeDestruction(Object bean, String beanName) throws BeansException {
    if (ObjectUtils.nullSafeEquals("userHolder", beanName) && UserHolder.class
        .equals(bean.getClass())) {

      UserHolder userHolder = (UserHolder) bean;

      userHolder.setDescription("the user holder V9");
      System.out.println("postProcessBeforeDestruction:description=" + "the user holder V9");
    }
  }
}
