package slydm.geektimes.training.context.annotation.support;

import io.github.classgraph.AnnotationInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import slydm.geektimes.training.beans.config.BeanFactoryPostProcessor;
import slydm.geektimes.training.context.annotation.ComponentScan;
import slydm.geektimes.training.context.annotation.ComponentScanAnnotationParser;
import slydm.geektimes.training.core.BeanDefinition;
import slydm.geektimes.training.core.BeanDefinitionRegistry;
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
   * 默认的解析注解配置的类
   */
  public static final String CONFIGURATION_ANNOTATION_PROCESSOR_BEAN_NAME =
      "slydm.geektimes.training.context.annotation.internalConfigurationAnnotationProcessor";


  @Override
  public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {

    String[] beanDefinitionNames = beanFactory.getBeanDefinitionNames();

    BeanDefinitionRegistry registry = (BeanDefinitionRegistry) beanFactory;
    List<AnnotationInfo> componentScans = new ArrayList<>();
    for (String beanDefinitionName : beanDefinitionNames) {

      BeanDefinition beanDefinition = registry.getBeanDefinition(beanDefinitionName);
      Optional<AnnotationInfo> optAnn = hasSpecialAnnotation(beanDefinition, ComponentScan.class);
      if (optAnn.isPresent()) {
        componentScans.add(optAnn.get());
      }
    }

    for (AnnotationInfo annotationInfo : componentScans) {

      String basePackage = annotationInfo.getParameterValues().getValue("basePackage").toString();
      String[] excludePackages = (String[]) annotationInfo.getParameterValues().getValue("excludePackages");

      ComponentScanAnnotationParser parser = new ComponentScanAnnotationParser(registry);
      parser.parse(new ComponentScanAttr(basePackage, excludePackages));
    }
  }


  /**
   * 判断 BeanDefinition 类是否包含指定的注解
   */
  private Optional<AnnotationInfo> hasSpecialAnnotation(BeanDefinition beanDefinition, Class<?> annotationClass) {

    List<AnnotationInfo> classAnnotationList = beanDefinition.getClassAnnotationList();
    Optional<AnnotationInfo> first = classAnnotationList.stream()
        .filter(annotationInfo -> StringUtils.equals(annotationInfo.getName(), annotationClass.getName()))
        .findFirst();
    return first;
  }

}
