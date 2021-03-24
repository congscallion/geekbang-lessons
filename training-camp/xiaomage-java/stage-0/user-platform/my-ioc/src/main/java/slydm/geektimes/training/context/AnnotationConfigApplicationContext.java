package slydm.geektimes.training.context;

import slydm.geektimes.training.context.annotation.ComponentScanAnnotationParser;
import slydm.geektimes.training.context.annotation.support.ComponentScanAttr;
import slydm.geektimes.training.core.BeanDefinition;
import slydm.geektimes.training.exception.BeansException;
import slydm.geektimes.training.ioc.DefaultListableBeanFactory;

/**
 * @author wangcymy@gmail.com(wangcong) 2021/3/24 16:27
 */
public class AnnotationConfigApplicationContext implements ApplicationContext {

  private DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
  private ComponentScanAnnotationParser parser = new ComponentScanAnnotationParser(beanFactory);


  public void start() {
    ComponentScanAttr componentScan = new ComponentScanAttr();
    componentScan.setBasePackage("");
    componentScan.setExcludePackages(new String[]{});
    parser.parse(componentScan);

    initializationALlSingletonBean(beanFactory);
  }

  public void close() {
    beanFactory.processPreDestroy();
  }

  private void initializationALlSingletonBean(DefaultListableBeanFactory beanFactory) {
    beanFactory.preInstantiateSingletons();
  }


  @Override
  public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
    beanFactory.registerBeanDefinition(beanName, beanDefinition);
  }


  @Override
  public Object getBean(String name) throws BeansException {
    return beanFactory.getBean(name);
  }

  @Override
  public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
    return beanFactory.getBean(name, requiredType);
  }
}
