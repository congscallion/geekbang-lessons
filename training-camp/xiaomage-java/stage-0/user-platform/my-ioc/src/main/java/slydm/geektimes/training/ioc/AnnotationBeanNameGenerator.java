package slydm.geektimes.training.ioc;

import io.github.classgraph.AnnotationInfo;
import java.beans.Introspector;
import java.util.Optional;
import slydm.geektimes.training.context.annotation.Component;
import slydm.geektimes.training.core.BeanDefinition;
import slydm.geektimes.training.util.ClassUtils;

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

    Optional<AnnotationInfo> first = definition.getClassAnnotationList().stream()
        .filter(annotation -> annotation.getName().equals(Component.class.getName())).findFirst();
    Object value = null;
    if (first.isPresent()) {
      value = first.get().getParameterValues().getValue("value");
    }
    return null == value ? "" : value.toString();
  }

  protected String buildDefaultBeanName(BeanDefinition definition) {
    String beanClassName = definition.getBeanClass().toString();
    String shortClassName = ClassUtils.getShortName(beanClassName);
    return Introspector.decapitalize(shortClassName);
  }
}
