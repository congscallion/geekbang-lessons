package slydm.geektimes.training.ioc;

import io.github.classgraph.AnnotationInfo;
import java.beans.Introspector;
import slydm.geektimes.training.context.annotation.Component;
import slydm.geektimes.training.core.BeanDefinition;

/**
 * @author wangcymy@gmail.com(wangcong) 2021/3/24 18:27
 */
public class AnnotationBeanNameGenerator implements BeanNameGenerator {

  @Override
  public String generateBeanName(BeanDefinition definition) {

    String beanName = determineBeanNameFromAnnotation(definition);
    if (beanName.isEmpty()) {
      beanName = buildDefaultBeanName(definition);
    }
    return beanName;
  }

  protected String determineBeanNameFromAnnotation(BeanDefinition definition) {

    AnnotationInfo annotationInfo = definition.getClassAnnotationList()
        .filter(annotation -> annotation.getName().equals(Component.class.getName()))
        .get(0);
    Object value = annotationInfo.getParameterValues().getValue("value");
    return null == value ? "" : value.toString();
  }

  protected String buildDefaultBeanName(BeanDefinition definition) {
    String beanClassName = definition.getBeanName();
    return Introspector.decapitalize(beanClassName);
  }
}
