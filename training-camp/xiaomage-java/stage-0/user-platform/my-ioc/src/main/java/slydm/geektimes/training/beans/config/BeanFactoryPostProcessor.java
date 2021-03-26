package slydm.geektimes.training.beans.config;

import slydm.geektimes.training.ioc.ConfigurableListableBeanFactory;

/**
 * 在容器启动阶段且未加载用户配置的bean之前提供配置或修改容器状态的能力
 *
 * @author wangcymy@gmail.com(wangcong) 2021/3/26 17:47
 */
@FunctionalInterface
public interface BeanFactoryPostProcessor {

  /**
   * 在容器启动阶段且未加载用户配置的bean之前提供配置或修改容器状态的能力
   */
  void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory);

}
