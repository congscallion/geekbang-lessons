package slydm.geektimes.training.context.annotation;

import io.github.classgraph.AnnotationInfo;
import io.github.classgraph.FieldInfo;
import java.beans.Introspector;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;
import javax.inject.Named;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.config.spi.Converter;
import slydm.geektimes.training.beans.factory.BeanFactoryAware;
import slydm.geektimes.training.beans.factory.InstantiationAwareBeanPostProcessor;
import slydm.geektimes.training.core.BeanDefinition;
import slydm.geektimes.training.core.BeanDefinitionRegistry;
import slydm.geektimes.training.exception.BeanCreationException;
import slydm.geektimes.training.exception.BeansException;
import slydm.geektimes.training.ioc.BeanFactory;
import slydm.geektimes.training.ioc.ConfigurableListableBeanFactory;
import slydm.geektimes.training.util.ClassUtils;
import slydm.geektimes.training.util.StringUtils;

/**
 * 处理自动注入且仅支持字段注入.
 * 有且仅有当 JSR-330 'javax.inject.Inject' 注解存在，才会处理microProfile的{@link ConfigProperty}注解
 *
 * 支持的注入类型如下:
 * <p>
 * <li> JSR-330's {@link javax.inject.Inject @Inject} 注解注入实例
 * <li> JSR-330's {@link javax.inject.Inject @Inject} 注解注入配置属性
 * <li> MicroProfile {@link ConfigProperty} 注解注入配置属性
 *
 * @author wangcymy@gmail.com(wangcong) 2021/4/6 10:16
 */
public class AutowiredAnnotationBeanPostProcessor implements InstantiationAwareBeanPostProcessor, BeanFactoryAware {

  /**
   * bean name
   */
  public static final String AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME =
      "org.springframework.context.annotation.internalAutowiredAnnotationProcessor";

  private Logger logger = Logger.getLogger(AutowiredAnnotationBeanPostProcessor.class.getName());

  private final List<Class<? extends Annotation>> autowiredAnnotationTypes = new ArrayList<>(4);

  private ConfigurableListableBeanFactory beanFactory;

  private Config config;

  public AutowiredAnnotationBeanPostProcessor() {
    try {
      this.autowiredAnnotationTypes.add((Class<? extends Annotation>)
          ClassUtils.forName("javax.inject.Inject", AutowiredAnnotationBeanPostProcessor.class.getClassLoader()));
      logger.config("JSR-330 'javax.inject.Inject' annotation found and supported for autowiring");
      this.autowiredAnnotationTypes.add(ConfigProperty.class);
      config = ConfigProvider.getConfig();
    } catch (ClassNotFoundException ex) {
      // JSR-330 API not available - simply skip.
    }
  }


  @Override
  public Object postProcessProperties(Object bean, String beanName) throws BeansException {

    if (this.autowiredAnnotationTypes.isEmpty()) {
      return bean;
    }

    BeanDefinition beanDefinition = getBeanDefinition(beanName);

    Set<FieldInfo> configSourcesInjectList = new HashSet<>();
    Set<FieldInfo> beanSourcesInjectList = new HashSet<>();

    beanDefinition.getAnnotationFieldList()
        .stream().filter(fieldInfo -> fieldInfo.hasAnnotation(autowiredAnnotationTypes.get(0).getName()))
        .forEach(fieldInfo -> {
          if (fieldInfo.hasAnnotation(autowiredAnnotationTypes.get(1).getName())) {
            configSourcesInjectList.add(fieldInfo);
          } else {
            beanSourcesInjectList.add(fieldInfo);
          }
        });

    doInjectConfig(bean, beanDefinition, configSourcesInjectList);
    doInjectBean(bean, beanDefinition, beanSourcesInjectList);

    return bean;
  }

  /**
   * 注入 Bean 实例
   */
  private void doInjectBean(Object bean, BeanDefinition beanDefinition, Set<FieldInfo> beanSourcesInjectList) {

    beanSourcesInjectList.stream().forEach(fieldInfo -> {
      String dependencyBeanName = lookupBeanName(fieldInfo);
      try {
        Object dependencyBean = beanFactory.getBean(dependencyBeanName);
        Field dependencyField = beanDefinition.getFieldByFiledInfo(fieldInfo);
        dependencyField.setAccessible(true);
        dependencyField.set(bean, dependencyBean);
      } catch (Exception e) {
        if (e instanceof BeansException) {
          throw (BeansException) e;
        }
        throw new BeansException(e.getMessage());
      }
    });

  }

  /**
   * 优先使用 {@link Named} 注解指定的名称，未指定，使用字段类型首字母小写作为bean名称
   */
  private String lookupBeanName(FieldInfo fieldInfo) {

    String dependencyBeanName = "";
    AnnotationInfo annotationInfo = fieldInfo.getAnnotationInfo(Named.class.getName());
    if (annotationInfo != null) {
      dependencyBeanName = annotationInfo.getParameterValues().getValue("value").toString();
    }

    if (StringUtils.hasText(dependencyBeanName)) {
      return dependencyBeanName;
    }

    String className = fieldInfo.getTypeDescriptor().toString();
    String shortName = ClassUtils.getShortName(className);
    return Introspector.decapitalize(shortName);
  }


  /**
   * 给字段注入配置属性值
   */
  private void doInjectConfig(Object bean, BeanDefinition beanDefinition, Set<FieldInfo> configSourcesInjectList) {

    configSourcesInjectList.stream().forEach(fieldInfo -> {

      AnnotationInfo annotationInfo = fieldInfo.getAnnotationInfo(this.autowiredAnnotationTypes.get(1).getName());

      String propertyName = annotationInfo.getParameterValues().getValue("name").toString();

      try {
        Field needInjectField = beanDefinition.getFieldByFiledInfo(fieldInfo);
        Class<?> typeClass = needInjectField.getType();
        Object value = config.getValue(propertyName, typeClass);

        if (value == null) {

          String defaultVal = annotationInfo.getParameterValues().getValue("defaultValue").toString();
          if (StringUtils.equals(defaultVal, ConfigProperty.UNCONFIGURED_VALUE)) {
            throw new BeanCreationException("not found config property: " + propertyName);
          }

          Optional<? extends Converter<?>> converterOptional = config.getConverter(typeClass);
          if (converterOptional.isPresent()) {
            Converter<?> converter = converterOptional.get();
            value = converter.convert(defaultVal);
          }
        }

        needInjectField.setAccessible(true);
        needInjectField.set(bean, value);
      } catch (Exception e) {
        if (e instanceof BeansException) {
          throw (BeansException) e;
        }
        throw new BeansException(e.getMessage());
      }

    });

  }

  @Override
  public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
    this.beanFactory = (ConfigurableListableBeanFactory) beanFactory;
  }

  protected BeanDefinitionRegistry getRegistry() {
    return (BeanDefinitionRegistry) beanFactory;
  }

  protected BeanDefinition getBeanDefinition(String beanName) {
    return getRegistry().getBeanDefinition(beanName);
  }
}
