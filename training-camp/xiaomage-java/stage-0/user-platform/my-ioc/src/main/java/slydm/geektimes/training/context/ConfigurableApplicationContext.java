package slydm.geektimes.training.context;

import slydm.geektimes.training.exception.BeansException;

/**
 * @author wangcymy@gmail.com(wangcong) 2021/3/29 17:49
 */
public interface ConfigurableApplicationContext extends ApplicationContext {

  /**
   * 刷新容器， 容器将会执行完成的初始化工作
   */
  void refresh() throws BeansException, IllegalStateException;


}
