package slydm.geektimes.training.beans.factory;

import slydm.geektimes.training.exception.BeansException;

/**
 * @author wangcymy@gmail.com(wangcong) 2021/3/27 17:03
 */
public interface SmartInstantiationAwareBeanPostProcessor extends InstantiationAwareBeanPostProcessor {

  /**
   *
   */
  default Object getEarlyBeanReference(Object bean, String beanName) throws BeansException {
    return bean;
  }

}
