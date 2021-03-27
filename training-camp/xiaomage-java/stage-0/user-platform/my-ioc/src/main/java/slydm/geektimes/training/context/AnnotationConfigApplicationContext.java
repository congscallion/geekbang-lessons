package slydm.geektimes.training.context;

import slydm.geektimes.training.beans.config.BeanFactoryPostProcessor;
import slydm.geektimes.training.context.annotation.AnnotatedBeanDefinitionReader;
import slydm.geektimes.training.core.BeanDefinition;
import slydm.geektimes.training.exception.BeansException;
import slydm.geektimes.training.exception.NoSuchBeanDefinitionException;
import slydm.geektimes.training.ioc.ConfigurableListableBeanFactory;
import slydm.geektimes.training.ioc.DefaultListableBeanFactory;

/**
 * 应用程序上下文
 *
 * @author wangcymy@gmail.com(wangcong) 2021/3/24 16:27
 */
public class AnnotationConfigApplicationContext implements ApplicationContext {

  private DefaultListableBeanFactory beanFactory;

  private final AnnotatedBeanDefinitionReader reader;

  public AnnotationConfigApplicationContext() {
    this.beanFactory = getBeanFactory();
    this.reader = new AnnotatedBeanDefinitionReader(beanFactory);
  }

  public void refresh() {
    prepareRefresh();

    // Prepare the bean factory for use in this context.
    prepareBeanFactory(beanFactory);

    try {
      // Allows post-processing of the bean factory in context subclasses.
      postProcessBeanFactory(beanFactory);

      // Invoke factory processors registered as beans in the context.
      invokeBeanFactoryPostProcessors(beanFactory);

      // Register bean processors that intercept bean creation.
      registerBeanPostProcessors(beanFactory);

      // Initialize message source for this context.
      initMessageSource();

      // Initialize event multicaster for this context.
      initApplicationEventMulticaster();

      // Initialize other special beans in specific context subclasses.
      onRefresh();

      // Check for listener beans and register them.
      registerListeners();

      // Instantiate all remaining (non-lazy-init) singletons.
      initializationALlSingletonBean(beanFactory);

      // Last step: publish corresponding event.
      finishRefresh();

    } catch (BeansException ex) {

//      if (logger.isWarnEnabled()) {
//        logger.warn("Exception encountered during context initialization - " +
//            "cancelling refresh attempt: " + ex);
//      }

      // Destroy already created singletons to avoid dangling resources.
      destroyBeans();

      // Reset 'active' flag.
      cancelRefresh(ex);

      // Propagate exception to caller.
      throw ex;

    } finally {
      // Reset common introspection caches in Spring's core, since we
      // might not ever need metadata for singleton beans anymore...
      resetCommonCaches();
    }
  }

  protected void resetCommonCaches() {

  }

  protected void cancelRefresh(BeansException ex) {

  }

  protected void finishRefresh() {

  }

  protected void registerListeners() {

  }

  protected void onRefresh() {
  }

  protected void initApplicationEventMulticaster() {
  }

  protected void initMessageSource() {
  }

  protected void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory) {

  }

  protected void invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory beanFactory) {

    String[] beanNamesForType = beanFactory.getBeanNamesForType(BeanFactoryPostProcessor.class);

    for (String beanName : beanNamesForType) {

      BeanFactoryPostProcessor postProcessor = beanFactory.getBean(beanName, BeanFactoryPostProcessor.class);
      postProcessor.postProcessBeanFactory(beanFactory);
    }

  }

  protected void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {
  }

  protected void prepareBeanFactory(DefaultListableBeanFactory beanFactory) {
  }

  protected void prepareRefresh() {
  }

  public void register(Class<?> annotatedClasses) {
    this.reader.register(annotatedClasses);
  }

  public void start() {

  }

  public void destroyBeans() {
    beanFactory.processPreDestroy();
  }

  protected DefaultListableBeanFactory getBeanFactory() {
    return new DefaultListableBeanFactory();
  }

  private void initializationALlSingletonBean(DefaultListableBeanFactory beanFactory) {
    beanFactory.preInstantiateSingletons();
  }

  @Override
  public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
    beanFactory.registerBeanDefinition(beanName, beanDefinition);
  }

  @Override
  public BeanDefinition getBeanDefinition(String beanName) throws NoSuchBeanDefinitionException {
    return beanFactory.getBeanDefinition(beanName);
  }

  @Override
  public boolean containsBeanDefinition(String beanName) {
    return beanFactory.containsBeanDefinition(beanName);
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
