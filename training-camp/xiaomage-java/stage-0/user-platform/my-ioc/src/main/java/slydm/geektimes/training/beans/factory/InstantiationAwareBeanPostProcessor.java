package slydm.geektimes.training.beans.factory;

import slydm.geektimes.training.exception.BeansException;

/**
 * @author wangcymy@gmail.com(wangcong) 2021/3/27 16:59
 */
public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor {


  /**
   * bean 实例创建前提供回调。 可以返回 Bean 实例对象， 如果返回非空对象，将使用该实例与 beanName 绑定，且不执行bean的后续初始化流程。
   */
  default Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
    return null;
  }

  /**
   * bean 实例初始化前提供回调。返回false, 将导致当前 BeanPostProcessor 实例之后的 BeanPostProcessor 实例的postProcessAfterInstantiation()
   * 不能得到执行机会。
   */
  default boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
    return true;
  }

  /**
   * bean 属性处理回调，此回调时机发生在 bean 实例化阶段完成。
   *
   * // TODO 需要注入静态属性特性等接入 ，microprofile后再实现方法签名也等到接入时再调整
   */
  default Object postProcessProperties(Object bean, String beanName)
      throws BeansException {

    return bean;
  }

}
