package slydm.geektimes.training.ioc;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import slydm.geektimes.training.core.BeanDefinition;
import slydm.geektimes.training.core.BeanDefinitionRegistry;
import slydm.geektimes.training.exception.BeansException;

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
   * 注册的所有单例 bean
   */
  private final Map<String, Object> singletonObjects = new HashMap<>(256);

  /**
   * 通过 new 创建且未装配的 bean
   */
  private final Map<String, Object> infantObjects = new HashMap<>(256);

  @Override
  public boolean containsBeanDefinition(String beanName) {
    return beanDefinitionNames.contains(beanName);
  }

  @Override
  public void preInstantiateSingletons() throws BeansException {

    List<String> beanNames = new ArrayList<>(this.beanDefinitionNames);
    for (String beanName : beanNames) {
      createBean(beanName);
    }

    // process dependency
    processDependency();

    // process callback after dependency processed
    processPostConstruct();

  }


  /**
   * process {@link PostConstruct}
   */
  private void processPostConstruct() {
    Iterator<Entry<String, Object>> iterator = singletonObjects.entrySet().iterator();

    while (iterator.hasNext()) {

      Entry<String, Object> entry = iterator.next();
      String beanName = entry.getKey();
      Object bean = entry.getValue();

      BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
      beanDefinition.getAnnotationMethodList()
          .stream()
          .filter(methodInfo -> methodInfo.hasAnnotation(PostConstruct.class.getName()))
          .forEach(methodInfo -> {
            try {
              Method method = bean.getClass().getMethod(methodInfo.getName());
              method.invoke(bean);
            } catch (Exception e) {
              throw new BeansException(e.getMessage());
            }
          });
    }

  }

  /**
   * process {@link PreDestroy}
   */
  public void processPreDestroy() {
    Iterator<Entry<String, Object>> iterator = singletonObjects.entrySet().iterator();

    while (iterator.hasNext()) {

      Entry<String, Object> entry = iterator.next();
      String beanName = entry.getKey();
      Object bean = entry.getValue();

      BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
      beanDefinition.getAnnotationMethodList()
          .stream()
          .filter(methodInfo -> methodInfo.hasAnnotation(PreDestroy.class.getName()))
          .forEach(methodInfo -> {
            try {
              Method method = bean.getClass().getMethod(methodInfo.getName());
              method.invoke(bean);
            } catch (Exception e) {
              throw new BeansException(e.getMessage());
            }
          });
      iterator.remove();
      cleanBean(beanName);
    }
  }

  private void cleanBean(String beanName) {

    beanDefinitionMap.remove(beanName);
    beanDefinitionNames.remove(beanName);
  }

  private void createBean(String beanName) {
    BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
    Class<?> clz;
    try {
      clz = getClassloader().loadClass(beanDefinition.getBeanClass().toString());
    } catch (ClassNotFoundException e) {
      throw new BeansException(e.getMessage());
    }

    try {
      Object bean = clz.newInstance();
      infantObjects.put(beanName, bean);
    } catch (Exception e) {
      throw new BeansException(e.getMessage());
    }
  }


  /**
   * process {@link Resource}
   */
  private void processDependency() {

    Iterator<Entry<String, Object>> iterator = infantObjects.entrySet().iterator();
    while (iterator.hasNext()) {

      Entry<String, Object> entry = iterator.next();
      String beanName = entry.getKey();
      Object bean = entry.getValue();

      BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
      beanDefinition.getAnnotationFieldList().stream()
          .filter(fieldInfo -> fieldInfo.hasAnnotation(Resource.class.getName()))
          .forEach(field -> {

            String dependencyBeanName = field.getAnnotationInfo().get(0).getParameterValues().getValue("name")
                .toString();
            Object dependencyBean = lookupBean(dependencyBeanName);
            try {
              Field dependencyField = bean.getClass().getDeclaredField(field.getName());
              dependencyField.setAccessible(true);
              dependencyField.set(bean, dependencyBean);
            } catch (Exception e) {
              throw new BeansException(e.getMessage());
            }
          });

      singletonObjects.put(beanName, bean);
      iterator.remove();
    }

  }

  private Object lookupBean(String beanName) {
    Object o = infantObjects.get(beanName);
    return o == null ? singletonObjects.get(beanName) : o;
  }


  @Override
  public Object getBean(String name) throws BeansException {
    return doGetBean(name);
  }

  @Override
  public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
    return requiredType.cast(doGetBean(name));
  }

  protected Object doGetBean(final String name) throws BeansException {

    if (singletonObjects.containsKey(name)) {
      return singletonObjects.get(name);
    } else {
      throw new BeansException(name + " not found.");
    }
  }

  protected ClassLoader getClassloader() {
    return ClassLoader.getSystemClassLoader();
  }

  @Override
  public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
    if (containsBeanDefinition(beanName)) {
      throw new BeansException(beanName + ", has exists!");
    }
    beanDefinitionMap.put(beanName, beanDefinition);
    beanDefinitionNames.add(beanName);
  }
}
