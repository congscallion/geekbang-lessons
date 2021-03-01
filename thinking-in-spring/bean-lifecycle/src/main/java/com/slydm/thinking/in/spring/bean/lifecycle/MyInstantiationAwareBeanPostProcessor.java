package com.slydm.thinking.in.spring.bean.lifecycle;

import com.slydm.thinking.in.spring.ioc.overview.domain.SuperUser;
import com.slydm.thinking.in.spring.ioc.overview.domain.User;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;

/**
 * @author wangcymy@gmail.com(wangcong) 12/17/20 10:41 PM
 */
class MyInstantiationAwareBeanPostProcessor implements
    InstantiationAwareBeanPostProcessor {


  /**
   * 如果本方法返回非空对象，spring ioc 容器将不在对 BeanDefinition 执行默认的创建 Bean 流程
   */
  @Override
  public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName)
      throws BeansException {

    if (ObjectUtils.nullSafeEquals("superUser", beanName) && SuperUser.class.equals(beanClass)) {

      // 把配置完成 SuperUser 覆盖
      return new SuperUser();
    }

    // 保持 Spring IOC 容器的操作
    return null;
  }


  /**
   * 这个方法最主要的功能是决定一个 Bean 是会通过 Spring IOC 容器的默认属性填充流程填充 Bean.
   *
   * 本方法，默认返回true;表示需要属性填；false表示不会填充属性.
   */
  @Override
  public boolean postProcessAfterInstantiation(Object bean, String beanName)
      throws BeansException {
    // User
    if (ObjectUtils.nullSafeEquals("user", beanName) && User.class.equals(bean.getClass())) {

      return false;
    }

    // 保持 Spring IOC 容器的操作
    return true;
  }


  @Validated
  public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName)
      throws BeansException {

    if (ObjectUtils.nullSafeEquals("userHolder", beanName) && UserHolder.class
        .equals(bean.getClass())) {

      MutablePropertyValues propertyValues;
      if (null != pvs) {
        propertyValues = (MutablePropertyValues) pvs;
      } else {
        propertyValues = new MutablePropertyValues();
      }

      propertyValues.addPropertyValue("number", 1);

      if (propertyValues.contains("description")) {
        propertyValues.removePropertyValue("description");
        propertyValues.addPropertyValue("description", "the user holder V2");
        System.out.println("postProcessProperties:description=" + "the user holder V2");
      }

      return pvs;

    }

    return null;
  }


  @Override
  public Object postProcessBeforeInitialization(Object bean, String beanName)
      throws BeansException {

    if (ObjectUtils.nullSafeEquals("userHolder", beanName) && UserHolder.class
        .equals(bean.getClass())) {

      UserHolder userHolder = (UserHolder) bean;

      userHolder.setDescription("the user holder V3");
      System.out.println("postProcessBeforeInitialization:description=" + "the user holder V3");
    }

    return bean;
  }


  @Override
  public Object postProcessAfterInitialization(Object bean, String beanName)
      throws BeansException {

    if (ObjectUtils.nullSafeEquals("userHolder", beanName) && UserHolder.class
        .equals(bean.getClass())) {

      UserHolder userHolder = (UserHolder) bean;

      userHolder.setDescription("the user holder V7");
      System.out.println("postProcessAfterInitialization:description=" + "the user holder V7");
    }

    return bean;
  }

}
