package slydm.geektimes.training.core;

import io.github.classgraph.AnnotationInfoList;
import io.github.classgraph.FieldInfo;
import io.github.classgraph.MethodInfo;
import java.util.List;

/**
 * @author wangcymy@gmail.com(wangcong) 2021/3/24 15:58
 */
public class ClassPathScannedBeanDefinition implements BeanDefinition {

  private String beanName;
  private Object beanClass;

  /**
   * 类注解列表
   */
  private AnnotationInfoList classAnnotationList;

  /**
   * 带有注解的字段列表
   */
  private List<FieldInfo> annotationFieldList;

  /**
   * 带有注解的方法列表
   */
  private List<MethodInfo> annotationMethodList;

  public ClassPathScannedBeanDefinition(Object beanClass) {
    this.beanClass = beanClass;
  }

  public AnnotationInfoList getClassAnnotationList() {
    return classAnnotationList;
  }

  public void setClassAnnotationList(AnnotationInfoList classAnnotationList) {
    this.classAnnotationList = classAnnotationList;
  }

  public List<FieldInfo> getAnnotationFieldList() {
    return annotationFieldList;
  }

  public void setAnnotationFieldList(List<FieldInfo> annotationFieldList) {
    this.annotationFieldList = annotationFieldList;
  }

  public List<MethodInfo> getAnnotationMethodList() {
    return annotationMethodList;
  }

  public void setAnnotationMethodList(List<MethodInfo> annotationMethodList) {
    this.annotationMethodList = annotationMethodList;
  }

  @Override
  public String getBeanName() {
    return beanName;
  }

  @Override
  public Object getBeanClass() {
    return beanClass;
  }

  public void setBeanName(String beanName) {
    this.beanName = beanName;
  }
}
