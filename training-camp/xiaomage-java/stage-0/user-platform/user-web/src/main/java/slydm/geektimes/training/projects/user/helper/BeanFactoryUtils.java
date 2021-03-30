package slydm.geektimes.training.projects.user.helper;

import slydm.geektimes.training.beans.factory.BeanFactoryAware;
import slydm.geektimes.training.context.annotation.Component;
import slydm.geektimes.training.exception.BeansException;
import slydm.geektimes.training.ioc.BeanFactory;

/**
 * @author 72089101@vivo.com(wangcong) 2021/3/30 15:44
 */
@Component
public class BeanFactoryUtils implements BeanFactoryAware {

  private static BeanFactory beanFactory;

  public static <T> T getBean(String name, Class<T> type) {
    return beanFactory.getBean(name, type);
  }


  @Override
  public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
    this.beanFactory = beanFactory;
  }
}
