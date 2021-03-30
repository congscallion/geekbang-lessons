package slydm.geektimes.training.context.annotation.support;

import io.github.classgraph.AnnotationInfo;
import io.github.classgraph.MethodInfo;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import slydm.geektimes.training.beans.config.BeanFactoryPostProcessor;
import slydm.geektimes.training.context.annotation.Bean;
import slydm.geektimes.training.context.annotation.ComponentScan;
import slydm.geektimes.training.context.annotation.ComponentScanAnnotationParser;
import slydm.geektimes.training.context.annotation.Configuration;
import slydm.geektimes.training.core.BeanDefinition;
import slydm.geektimes.training.core.BeanDefinitionRegistry;
import slydm.geektimes.training.core.MethodBeanDefinition;
import slydm.geektimes.training.ioc.ConfigurableListableBeanFactory;
import slydm.geektimes.training.util.StringUtils;

/**
 * 处理 {@link ComponentScan} 注解类。
 *
 * TODO 暂时不像spring一样处理 Configuration 类，等以后有需求再添加
 *
 * @author wangcymy@gmail.com(wangcong) 2021/3/26 17:45
 */
public class ConfigurationClassPostProcessor implements BeanFactoryPostProcessor {

  /**
   * 默认的解析注解配置的类 Bean名称
   */
  public static final String CONFIGURATION_ANNOTATION_PROCESSOR_BEAN_NAME =
      "slydm.geektimes.training.context.annotation.internalConfigurationAnnotationProcessor";


  @Override
  public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {

    String[] beanDefinitionNames = beanFactory.getBeanDefinitionNames();

    BeanDefinitionRegistry registry = (BeanDefinitionRegistry) beanFactory;
    List<AnnotationInfo> componentScans = new ArrayList<>();
    Set<MethodInfo> beanMethods = new HashSet<>();
    Set<BeanDefinition> registerBeanDefinitions = new HashSet<>();
    for (String beanDefinitionName : beanDefinitionNames) {

      BeanDefinition beanDefinition = registry.getBeanDefinition(beanDefinitionName);
      registerBeanDefinitions.add(beanDefinition);

      // 没有 @Configuration 注解不处理
      Optional<AnnotationInfo> configurationAnn = hasSpecialAnnotationOnClass(beanDefinition, Configuration.class);
      if (!configurationAnn.isPresent()) {
        continue;
      }

      // 搜集 @ComponentScan 注解
      Optional<AnnotationInfo> optAnn = hasSpecialAnnotationOnClass(beanDefinition, ComponentScan.class);
      if (optAnn.isPresent()) {
        componentScans.add(optAnn.get());
      }
    }

    // 处理 @ComponentScan 注解
    Set<BeanDefinition> scanEdBeanDefinitionSet = processComponentScans(componentScans, registry);
    // 合并扫描到 beanDefinition
    registerBeanDefinitions.addAll(scanEdBeanDefinitionSet);

    // 处理 @Bean 注解
    processBeanMethods(scanEdBeanDefinitionSet, registry);

  }

  /**
   * 注册 {@link Bean} 注解定义的实例
   */
  private void processBeanMethods(Set<BeanDefinition> allBeanDefinitions, BeanDefinitionRegistry registry) {

    for (BeanDefinition beanDefinition : allBeanDefinitions) {

      // 没有 @Configuration 注解不处理
      Optional<AnnotationInfo> configurationAnn = hasSpecialAnnotationOnClass(beanDefinition, Configuration.class);
      if (!configurationAnn.isPresent()) {
        continue;
      }

      beanDefinition.getAnnotationMethodList()
          .stream()
          .filter(methodInfo -> methodInfo.hasAnnotation(Bean.class.getName()))
          .forEach(methodInfo -> {

            Method beanAnnotationMethod = beanDefinition.getMethodByMethodInfo(methodInfo);

            MethodBeanDefinition innerBeanDefinition = new MethodBeanDefinition();
            innerBeanDefinition.setMethodName(methodInfo.getName());
            innerBeanDefinition.setAnnotationInfo(methodInfo.getAnnotationInfo());

            innerBeanDefinition.setMethod(beanAnnotationMethod);
            innerBeanDefinition.setBeanClass(beanAnnotationMethod.getReturnType());
            innerBeanDefinition.setOwnerClass(beanAnnotationMethod.getDeclaringClass());

            // 注册 @Bean 定义
            registry.registerBeanDefinition(deterBeanName(innerBeanDefinition), innerBeanDefinition);
          });
    }

  }

  private String deterBeanName(MethodBeanDefinition beanDefinition) {
    AnnotationInfo annotationInfo = beanDefinition.getAnnotationInfo()
        .filter(an -> an.getName().equals(Bean.class.getName()))
        .get(0);

    String name = "";
    Object obj = annotationInfo.getParameterValues().getValue("name");
    if (null != obj && StringUtils.hasText(obj.toString())) {
      name = obj.toString();
      return name;
    }

    if (!StringUtils.hasText(name)) {
      name = beanDefinition.getMethodName();
    }
    return name;
  }

  /**
   * 扫描 {@link ComponentScan} 注解指定的包路径
   */
  private Set<BeanDefinition> processComponentScans(List<AnnotationInfo> componentScans,
      BeanDefinitionRegistry registry) {
    Set<BeanDefinition> beanDefinitionSet = new HashSet<>();
    for (AnnotationInfo annotationInfo : componentScans) {

      String basePackage = annotationInfo.getParameterValues().getValue("basePackage").toString();
      String[] excludePackages = (String[]) annotationInfo.getParameterValues().getValue("excludePackages");

      ComponentScanAnnotationParser parser = new ComponentScanAnnotationParser(registry);
      beanDefinitionSet.addAll(parser.parse(new ComponentScanAttr(basePackage, excludePackages)));
    }
    return beanDefinitionSet;
  }

  /**
   * 判断 BeanDefinition 类是否包含指定的注解
   */
  private Optional<AnnotationInfo> hasSpecialAnnotationOnClass(BeanDefinition beanDefinition,
      Class<?> annotationClass) {

    List<AnnotationInfo> classAnnotationList = beanDefinition.getClassAnnotationList();
    Optional<AnnotationInfo> first = classAnnotationList.stream()
        .filter(annotationInfo -> StringUtils.equals(annotationInfo.getName(), annotationClass.getName()))
        .findFirst();
    return first;
  }

}
