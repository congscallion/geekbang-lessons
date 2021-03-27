package slydm.geektimes.training.beans.factory;

import slydm.geektimes.training.exception.BeansException;

/**
 * @author wangcymy@gmail.com(wangcong) 2021/3/27 16:57
 */
public interface BeanPostProcessor {

  /**
   *
   */
  default Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
    return bean;
  }

  /**
   *
   */
  default Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
    return bean;
  }


}
