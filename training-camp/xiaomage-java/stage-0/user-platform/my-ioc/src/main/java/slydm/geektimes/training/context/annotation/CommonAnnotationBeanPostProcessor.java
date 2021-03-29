package slydm.geektimes.training.context.annotation;

import io.github.classgraph.AnnotationParameterValueList;
import io.github.classgraph.FieldInfo;
import java.beans.Introspector;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import slydm.geektimes.training.beans.factory.BeanFactoryAware;
import slydm.geektimes.training.beans.factory.InstantiationAwareBeanPostProcessor;
import slydm.geektimes.training.core.BeanDefinition;
import slydm.geektimes.training.core.BeanDefinitionRegistry;
import slydm.geektimes.training.exception.BeansException;
import slydm.geektimes.training.ioc.BeanFactory;
import slydm.geektimes.training.ioc.DefaultListableBeanFactory;
import slydm.geektimes.training.util.ClassUtils;
import slydm.geektimes.training.util.StringUtils;

/**
 * 处理 jsr-250 依赖相关的注解。
 *
 * <p>
 * 本实现不具有太多意义， 特别是在本工程中， 这三个注解的处理完全可以在 {@link DefaultListableBeanFactory}类中直接处理。
 * 但实现在这个类，纯粹是为了熟悉spring的扩展机制，了解spring的实现，以及其细节。
 *
 * <p>
 * <li> {@link javax.annotation.Resource}
 * <li> {@link javax.annotation.PostConstruct}
 * <li> {@link javax.annotation.PreDestroy}
 *
 * @author wangcymy@gmail.com(wangcong) 2021/3/28 0:01
 */
public class CommonAnnotationBeanPostProcessor implements InstantiationAwareBeanPostProcessor,
    DestructionAwareBeanPostProcessor, BeanFactoryAware, Serializable {

  private transient BeanFactory beanFactory;

  /**
   * 处理 jsr250 注解的 Bean名称
   */
  public static final String COMMON_ANNOTATION_PROCESSOR_BEAN_NAME =
      "slydm.geektimes.training.context.annotation.internalCommonAnnotationProcessor";


  @Override
  public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
    this.beanFactory = beanFactory;
  }


  /**
   * 处理 {@link PostConstruct} 注解
   */
  @Override
  public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
    invokedMethodOnAnnotation(bean, beanName, "javax.annotation.PostConstruct");
    return bean;
  }

  /**
   * 处理 {@link PreDestroy} 注解
   */
  @Override
  public void postProcessBeforeDestruction(Object bean, String beanName) throws BeansException {
    invokedMethodOnAnnotation(bean, beanName, "javax.annotation.PreDestroy");
  }

  /**
   * 处理 {@link Resource} 注解
   */
  @Override
  public Object postProcessProperties(Object bean, String beanName) throws BeansException {

    BeanDefinition beanDefinition = getBeanDefinition(beanName);

    beanDefinition.getAnnotationFieldList().stream()
        .filter(fieldInfo -> fieldInfo.hasAnnotation(Resource.class.getName()))
        .forEach(field -> {

          String dependencyBeanName = lookupBeanName(field);
          try {
            Object dependencyBean = beanFactory.getBean(dependencyBeanName);
            Field dependencyField = bean.getClass().getDeclaredField(field.getName());
            dependencyField.setAccessible(true);
            dependencyField.set(bean, dependencyBean);
          } catch (Exception e) {
            if (e instanceof BeansException) {
              throw (BeansException) e;
            }
            throw new BeansException(e.getMessage());
          }
        });
    return bean;
  }


  /**
   * 优化使用 {@link Resource} 注解指定的名称，未指定，使用字段类型首字母小写作为bean名称
   */
  private String lookupBeanName(FieldInfo fieldInfo) {

    String dependencyBeanName = "";
    AnnotationParameterValueList parameterValues = fieldInfo.getAnnotationInfo().get(0).getParameterValues();
    if (parameterValues.containsName("name")) {
      dependencyBeanName = parameterValues.getValue("name").toString();
    }

    if (StringUtils.hasText(dependencyBeanName)) {
      return dependencyBeanName;
    }

    String className = fieldInfo.getTypeDescriptor().toString();
    String shortName = ClassUtils.getShortName(className);
    return Introspector.decapitalize(shortName);
  }


  private void invokedMethodOnAnnotation(Object bean, String beanName, String annotationName) {

    BeanDefinition beanDefinition = getBeanDefinition(beanName);
    beanDefinition.getAnnotationMethodList()
        .stream()
        .filter(methodInfo -> methodInfo.hasAnnotation(annotationName))
        .forEach(methodInfo -> {
          try {
            Method method = bean.getClass().getMethod(methodInfo.getName());
            method.invoke(bean);
          } catch (Exception e) {
            throw new BeansException(e.getMessage());
          }
        });
  }


  protected BeanDefinitionRegistry getRegistry() {
    return (BeanDefinitionRegistry) beanFactory;
  }

  protected BeanDefinition getBeanDefinition(String beanName) {
    return getRegistry().getBeanDefinition(beanName);
  }

}
