package slydm.geektimes.training.ioc;

import io.github.classgraph.ClassInfo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import slydm.geektimes.training.beans.factory.Aware;
import slydm.geektimes.training.beans.factory.BeanFactoryAware;
import slydm.geektimes.training.beans.factory.BeanPostProcessor;
import slydm.geektimes.training.beans.factory.InstantiationAwareBeanPostProcessor;
import slydm.geektimes.training.beans.factory.ObjectFactory;
import slydm.geektimes.training.beans.factory.SmartInstantiationAwareBeanPostProcessor;
import slydm.geektimes.training.context.annotation.DestructionAwareBeanPostProcessor;
import slydm.geektimes.training.core.BeanDefinition;
import slydm.geektimes.training.core.BeanDefinitionRegistry;
import slydm.geektimes.training.exception.BeanCreationException;
import slydm.geektimes.training.exception.BeanCurrentlyInCreationException;
import slydm.geektimes.training.exception.BeansException;
import slydm.geektimes.training.exception.NoSuchBeanDefinitionException;
import slydm.geektimes.training.util.Assert;
import slydm.geektimes.training.util.StringUtils;

/**
 * 默认的IOC容器实现
 *
 * @author wangcymy@gmail.com(wangcong) 2021/3/24 16:18
 */
public class DefaultListableBeanFactory implements ConfigurableListableBeanFactory, BeanDefinitionRegistry {

  /**
   * 注册的 {@link BeanDefinition} 对象
   */
  private Map<String, BeanDefinition> beanDefinitionMap = new LinkedHashMap<>();

  /**
   * 按注册顺序保存的 beanName
   */
  private volatile List<String> beanDefinitionNames = new ArrayList<>(256);

  /**
   * 已初始化完成可消耗使用的 Bean 集合
   */
  private final Map<String, Object> singletonObjects = new HashMap<>(256);

  /**
   * 未初始化或初始化部分的 Bean集合。 如果从现循环依赖，该集合中的Bean可能由singletonFactories集合转移过来。
   */
  private final Map<String, Object> infantObjects = new HashMap<>(256);

  /**
   * 已实例化但未初始化的 Bean集合
   */
  private final Map<String, ObjectFactory<?>> singletonFactories = new HashMap<>(16);

  /**
   * 正在实例化的bean名称集合
   */
  private final Set<String> singletonsCurrentlyInCreation = Collections.newSetFromMap(new ConcurrentHashMap<>(16));

  /**
   * BeanPostProcessors to apply in createBean.
   */
  private final List<BeanPostProcessor> beanPostProcessors = new CopyOnWriteArrayList<>();

  /**
   * Indicates whether any InstantiationAwareBeanPostProcessors have been registered.
   */
  private volatile boolean hasInstantiationAwareBeanPostProcessors;

  @Override
  public boolean containsBeanDefinition(String beanName) {
    return beanDefinitionNames.contains(beanName);
  }

  @Override
  public void preInstantiateSingletons() throws BeansException {

    List<String> beanNames = new ArrayList<>(this.beanDefinitionNames);
    for (String beanName : beanNames) {
      getBean(beanName);
    }
  }

  @Override
  public Object getBean(String name) throws BeansException {
    return getBean(name, null);
  }

  @Override
  public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
    return doGetBean(name, requiredType);
  }


  protected <T> T doGetBean(final String name, Class<T> requiredType) throws BeansException {

    Object sharedInstance = getSingleton(name, true);

    // 当 bean 未开始实例化，则开始实例化
    if (sharedInstance == null) {

      // 检查 bean 是否已在创建中
      try {
        beforeSingletonCreation(name);
        sharedInstance = createBean(name);
        addSingleton(name, sharedInstance);
      } finally {
        afterSingletonCreation(name);
      }
    }

    return (T) sharedInstance;
  }


  /**
   * 循环依赖解决细节如下,此处实现与 spring 可以说一模一样（便还是有稍微区别，但不在本方法中，在整体实现上），代码也是复制的， 描述是按spring的实现写的:
   * <p>
   * <li> 当 Bean 实例化后(即通过 new 或者 其它方式创建对象，所有属性未赋值的状态), 创建 {@link ObjectFactory} 的匿名实现，并放入 singletonFactories 集合中。
   * <li> Bean的实例完成初始化逻辑，在初始化的过程中会完成依赖注入的校验。
   * 1. 当前初始化Bean不依赖其它Bean，或者依赖的Bean都已初始化，直接完成初始化。 最后将初始化完成的Bean放入singletonObjects集合， 且从singletonFactories 集合中删除当前Bean
   * Name关联的 ObjectFactory实例。<br> 2. 依赖的 Bean 未创建时。<br> a. 递归调用 getBean() 方法完成 Bean的创建，最后Bean注入的依赖的对象中。<br> b.
   * 依赖的Bean在创建过程中，发现其依赖的Bean在singletonsCurrentlyInCreation集合中，表示出现了循环依赖。 则此时，未完成初始化的Bean都在singletonFactories 集合中。
   * 所以根据beanName从该集合中取出 {@link ObjectFactory}实例，并调用该实例的 getObject()方法获取Bean的实例。<br> c. 需要注意： <br> c.1. 此处通过
   * getObject()方法获取的Bean实例可能与原来开始创建的实例不一致，因为实现完全可以创建新的实例返回。<br> c.2. 在依赖处理阶段，getObject() 方法获取的Bean还没有经过 BeanPostProcess
   * 接口的callback方法“洗礼”。但经过了 SmartInstantiationAwareBeanPostProcessor 接口 的 getEarlyBeanReference() 方法“洗礼”。
   * AOP的增强就是通过实现这个接口的这个方法来增强Bean的。<br>
   */
  protected Object getSingleton(String beanName, boolean allowEarlyReference) {
    Object singletonObject = this.singletonObjects.get(beanName);
    if (singletonObject == null && isSingletonCurrentlyInCreation(beanName)) {
      synchronized (this.singletonObjects) {
        singletonObject = this.infantObjects.get(beanName);
        if (singletonObject == null && allowEarlyReference) {
          ObjectFactory<?> singletonFactory = this.singletonFactories.get(beanName);
          if (singletonFactory != null) {
            singletonObject = singletonFactory.getObject();
            this.infantObjects.put(beanName, singletonObject);
            this.singletonFactories.remove(beanName);
          }
        }
      }
    }
    return singletonObject;
  }

  protected void addSingleton(String beanName, Object singletonObject) {
    synchronized (this.singletonObjects) {
      this.singletonObjects.put(beanName, singletonObject);
      this.singletonFactories.remove(beanName);
      this.infantObjects.remove(beanName);
    }
  }

  protected void beforeSingletonCreation(String beanName) {
    if (!this.singletonsCurrentlyInCreation.add(beanName)) {
      throw new BeanCurrentlyInCreationException(beanName);
    }
  }

  protected void afterSingletonCreation(String beanName) {
    if (!this.singletonsCurrentlyInCreation.remove(beanName)) {
      throw new IllegalStateException("Singleton '" + beanName + "' isn't currently in creation");
    }
  }


  /**
   * 创建 Bean 实例，并填充属性值
   */
  protected Object createBean(String beanName) {

    // 创建 Bean 实例
    BeanDefinition beanDefinition = getBeanDefinition(beanName);
    Object bean = createBeanInstance(beanName, beanDefinition);

    // 将 bean 放入 singletonFactories 用于循环依赖，当bean未完成初始化又存在被其它bean依赖时，从该集合中获取bean实例
    singletonFactories.put(beanName, () -> getEarlyBeanReference(beanName, beanDefinition, bean));

    // Initialize the bean instance.
    Object exposedObject = bean;
    try {
      populateBean(beanName, beanDefinition, exposedObject);
      exposedObject = initializeBean(beanName, exposedObject, beanDefinition);
    } catch (Throwable ex) {
      if (ex instanceof BeanCreationException && beanName.equals(((BeanCreationException) ex).getBeanName())) {
        throw (BeanCreationException) ex;
      } else if (ex instanceof BeansException) {
        throw (BeansException) ex;
      } else {
        throw new BeanCreationException(beanName + " Initialization of bean failed", ex);
      }
    }

    /*
        通过 getSingleton() 从集合中再次取出与bean相关联的bean,注意，此处 allowEarlyReference 参数为false. 因此，只会从
        singletonObjects 集合与 infantObjects 集合中查找Bean.

        1. 找不到，没有发生循环依赖。
        2. 找到，肯定发生循环依赖，当前bean 被从 singletonFactories 集合转移到了 infantObjects 集合。

            a. 此时返回bean是经过getEarlyBeanReference()方法处理过的，有可能已经不是原来创建的那个对象。
            b. 验证是否一致。
            c. 一致，继续执行。
            d. 不一致。 说明循环依赖中，依赖当前bean的实例注入了getEarlyBeanReference()返回的与当前bean不一致的bean.
            e. 本实现中，直接异常。没有原因，仅是为了实现简单。
            f. spring 实现稍微复杂点儿，检查了被依赖的bean是否实依赖等等。
     */
    Object singleton = getSingleton(beanName, false);
    if (null != singleton && exposedObject != singleton) {
      throw new BeanCreationException(beanName + ": Initialization of bean state conflict!");
    }
    return exposedObject;
  }

  /**
   * 创建 Bean 实例, 这里搞简单一点儿，不用像spring一样通过 InstantiationStrategy接口 策略创建
   *
   * <p>
   * 先看扩展接口是否返回bean实例，扩展接口返回bean实例则实例该实例且该实例不再执行后续的初始化流程。
   */
  protected Object createBeanInstance(String beanName, BeanDefinition mbd) {

    Class<?> clz;
    try {
      clz = getClassloader().loadClass(mbd.getBeanClass().toString());
    } catch (ClassNotFoundException e) {
      throw new BeansException(e.getMessage());
    }

    Object bean = null;
    if (!mbd.isSynthetic() && hasInstantiationAwareBeanPostProcessors()) {

      bean = applyBeanPostProcessorsBeforeInstantiation(clz, beanName);
      if (bean != null) {
        bean = applyBeanPostProcessorsAfterInitialization(bean, beanName);
      }
    }

    if (null != bean) {
      return bean;
    }

    try {
      return clz.newInstance();
    } catch (Exception e) {
      throw new BeansException(e.getMessage());
    }
  }

  private Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName)
      throws BeansException {

    Object result = existingBean;
    for (BeanPostProcessor processor : getBeanPostProcessors()) {
      Object current = processor.postProcessBeforeInitialization(result, beanName);
      if (current == null) {
        return result;
      }
      result = current;
    }
    return result;
  }


  private Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) {
    Object result = existingBean;
    for (BeanPostProcessor processor : getBeanPostProcessors()) {
      Object current = processor.postProcessAfterInitialization(result, beanName);
      if (current == null) {
        return result;
      }
      result = current;
    }
    return result;
  }

  private Object applyBeanPostProcessorsBeforeInstantiation(Class<?> clz, String beanName) {

    for (BeanPostProcessor bp : getBeanPostProcessors()) {
      if (bp instanceof InstantiationAwareBeanPostProcessor) {
        InstantiationAwareBeanPostProcessor ibp = (InstantiationAwareBeanPostProcessor) bp;
        Object result = ibp.postProcessBeforeInstantiation(clz, beanName);
        if (result != null) {
          return result;
        }
      }
    }
    return null;
  }

  protected ClassLoader getClassloader() {
    ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
    if (contextClassLoader == null) {
      contextClassLoader = ClassLoader.getSystemClassLoader();
    }
    return contextClassLoader;
  }

  protected void populateBean(String beanName, BeanDefinition mbd, Object bean) {

    boolean continueWithPropertyPopulation = true;
    if (!mbd.isSynthetic() && hasInstantiationAwareBeanPostProcessors()) {
      for (BeanPostProcessor bp : getBeanPostProcessors()) {
        if (bp instanceof InstantiationAwareBeanPostProcessor) {
          InstantiationAwareBeanPostProcessor ibp = (InstantiationAwareBeanPostProcessor) bp;
          if (!ibp.postProcessAfterInstantiation(bean, beanName)) {
            continueWithPropertyPopulation = false;
            break;
          }
        }
      }
    }

    if (!continueWithPropertyPopulation) {
      return;
    }

    // TODO 静态属性注入待添加, 目前只处理 @javax.annotation.Resource 注解
    if (!mbd.isSynthetic() && hasInstantiationAwareBeanPostProcessors()) {
      for (BeanPostProcessor bp : getBeanPostProcessors()) {
        if (bp instanceof InstantiationAwareBeanPostProcessor) {
          InstantiationAwareBeanPostProcessor ibp = (InstantiationAwareBeanPostProcessor) bp;
          ibp.postProcessProperties(bean, beanName);
        }
      }
    }
  }

  protected Object initializeBean(final String beanName, final Object bean, BeanDefinition mbd) {

    invokeAwareMethods(beanName, bean);

    Object wrappedBean = bean;
    if (mbd == null || !mbd.isSynthetic()) {
      wrappedBean = applyBeanPostProcessorsBeforeInitialization(wrappedBean, beanName);
    }

    try {
      invokeInitMethods(beanName, wrappedBean, mbd);
    } catch (Throwable ex) {
      // ignore any thing...
    }

    if (mbd == null || !mbd.isSynthetic()) {
      wrappedBean = applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName);
    }

    return wrappedBean;
  }


  protected void invokeInitMethods(String beanName, final Object bean, BeanDefinition mbd) {
    // TODO 不实现spring中的 InitializingBean 接口功能，也不实现自定义 InitMethod 功能

  }

  private void invokeAwareMethods(final String beanName, final Object bean) {
    if (bean instanceof Aware) {

      if (bean instanceof BeanFactoryAware) {
        ((BeanFactoryAware) bean).setBeanFactory(this);
      }
    }
  }


  protected Object getEarlyBeanReference(String beanName, BeanDefinition mbd, Object bean) {

    Object exposedObject = bean;
    if (!mbd.isSynthetic() && hasInstantiationAwareBeanPostProcessors()) {
      for (BeanPostProcessor bp : getBeanPostProcessors()) {
        if (bp instanceof SmartInstantiationAwareBeanPostProcessor) {
          SmartInstantiationAwareBeanPostProcessor ibp = (SmartInstantiationAwareBeanPostProcessor) bp;
          exposedObject = ibp.getEarlyBeanReference(exposedObject, beanName);
        }
      }
    }
    return exposedObject;
  }

  @Override
  public String[] getBeanNamesForType(Class<?> type) {

    List<String> result = new ArrayList<>();
    for (String beanDefinitionName : beanDefinitionNames) {

      BeanDefinition beanDefinition = getBeanDefinition(beanDefinitionName);

      if (StringUtils.equals(beanDefinition.getBeanClass().toString(), type.getName())) {
        result.add(beanDefinitionName);
      }

      for (ClassInfo supInter : beanDefinition.getAllInterfaces()) {
        if (StringUtils.equals(supInter.getName(), type.getName())) {
          result.add(beanDefinitionName);
        }
      }

      for (ClassInfo supClass : beanDefinition.getAllSuperClasses()) {
        if (StringUtils.equals(supClass.getName(), type.getName())) {
          result.add(beanDefinitionName);
        }
      }
    }

    return StringUtils.toStringArray(result);
  }

  @Override
  public String[] getBeanDefinitionNames() {
    return StringUtils.toStringArray(this.beanDefinitionNames);
  }

  protected boolean hasInstantiationAwareBeanPostProcessors() {
    return this.hasInstantiationAwareBeanPostProcessors;
  }

  public List<BeanPostProcessor> getBeanPostProcessors() {
    return this.beanPostProcessors;
  }

  @Override
  public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
    Assert.notNull(beanPostProcessor, "BeanPostProcessor must not be null");
    // Remove from old position, if any
    this.beanPostProcessors.remove(beanPostProcessor);
    // Track whether it is instantiation/destruction aware
    if (beanPostProcessor instanceof InstantiationAwareBeanPostProcessor) {
      this.hasInstantiationAwareBeanPostProcessors = true;
    }
    // Add to end of list
    this.beanPostProcessors.add(beanPostProcessor);
  }

  @Override
  public void destroySingletons() {

    String[] singletonBeanNames;
    synchronized (this.singletonObjects) {
      singletonBeanNames = StringUtils.toStringArray(this.singletonObjects.keySet());
    }
    for (int i = singletonBeanNames.length - 1; i >= 0; i--) {
      destroySingleton(singletonBeanNames[i]);
    }

    clearSingletonCache();
  }


  public void destroySingleton(String beanName) {

    destroyBean(beanName, singletonObjects.get(beanName));

    // Remove a registered singleton of the given name, if any.
    removeSingleton(beanName);
  }

  protected void destroyBean(String beanName, Object bean) {
    for (BeanPostProcessor processor : getBeanPostProcessors()) {
      if (processor instanceof DestructionAwareBeanPostProcessor) {
        ((DestructionAwareBeanPostProcessor) processor).postProcessBeforeDestruction(bean, beanName);
      }
    }
  }

  protected void removeSingleton(String beanName) {
    synchronized (this.singletonObjects) {
      this.singletonObjects.remove(beanName);
      this.singletonFactories.remove(beanName);
      this.infantObjects.remove(beanName);
    }
  }


  protected void clearSingletonCache() {
    synchronized (this.singletonObjects) {
      this.singletonObjects.clear();
      this.singletonFactories.clear();
      this.infantObjects.clear();
    }
  }

  /**
   * 判断 bean 是否处于实例化阶段
   */
  public boolean isSingletonCurrentlyInCreation(String beanName) {
    return this.singletonsCurrentlyInCreation.contains(beanName);
  }

  @Override
  public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
    if (containsBeanDefinition(beanName)) {
      throw new BeansException(beanName + ", has existed!");
    }
    beanDefinitionMap.put(beanName, beanDefinition);
    beanDefinitionNames.add(beanName);
  }

  @Override
  public BeanDefinition getBeanDefinition(String beanName) throws NoSuchBeanDefinitionException {

    BeanDefinition bd = this.beanDefinitionMap.get(beanName);
    if (bd == null) {
//      if (logger.isTraceEnabled()) {
//        logger.trace("No bean named '" + beanName + "' found in " + this);
//      }
      throw new NoSuchBeanDefinitionException(beanName);
    }
    return bd;
  }
}
