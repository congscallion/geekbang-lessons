package slydm.geektimes.training.beans.factory;

import slydm.geektimes.training.exception.BeansException;

/**
 * @author wangcymy@gmail.com(wangcong) 2021/3/27 16:59
 */
public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor {


  /**
   *
   */
  default Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
    return null;
  }

  /**
   *
   */
  default boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
    return true;
  }

  /**
   *
   */
  default Object postProcessProperties(Object bean, String beanName)
      throws BeansException {

    return bean;
  }

}
