package slydm.geektimes.training.core;

import io.github.classgraph.AnnotationInfo;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.FieldInfo;
import io.github.classgraph.MethodInfo;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 代表 {@link ClassInfo} 对象的 {@link BeanDefinition} 实例
 *
 * @author wangcymy@gmail.com(wangcong) 2021/3/24 15:58
 */
public class ClassPathScannedBeanDefinition implements BeanDefinition {

  private Object beanClass;

  /**
   * 类注解列表
   */
  private List<AnnotationInfo> classAnnotationList;

  /**
   * 带有注解的字段列表
   */
  private List<FieldInfo> annotationFieldList;

  /**
   * 带有注解的方法列表
   */
  private List<MethodInfo> annotationMethodList;

  /**
   * 所有实现的接口
   */
  private List<ClassInfo> allInterfaces;

  /**
   * 直接或间接父类
   */
  List<ClassInfo> allSuperClasses;

  public ClassPathScannedBeanDefinition(ClassInfo classInfo) {
    this.beanClass = classInfo.getName();

    List<FieldInfo> annotationFieldList = classInfo.getDeclaredFieldInfo()
        .filter(fieldInfo -> notEmpty(fieldInfo.getAnnotationInfo()))
        .stream().collect(Collectors.toList());
    this.annotationFieldList = annotationFieldList;

    List<MethodInfo> annotationMethodList = classInfo.getMethodInfo()
        .filter(methodInfo -> notEmpty(methodInfo.getAnnotationInfo()))
        .stream().collect(Collectors.toList());
    this.annotationMethodList = annotationMethodList;

    this.classAnnotationList = classInfo.getAnnotationInfo().stream().collect(Collectors.toList());

    // 处理所有父类
    this.allSuperClasses = classInfo.getSuperclasses().stream().collect(Collectors.toList());

    // 处理所有父接口
    this.allInterfaces = classInfo.getInterfaces().stream().collect(Collectors.toList());
  }

  private boolean notEmpty(Collection collection) {
    return collection != null && !collection.isEmpty();
  }

  public List<AnnotationInfo> getClassAnnotationList() {
    return classAnnotationList;
  }

  public List<FieldInfo> getAnnotationFieldList() {
    return annotationFieldList;
  }

  public List<MethodInfo> getAnnotationMethodList() {
    return annotationMethodList;
  }

  @Override
  public Object getBeanClass() {
    return beanClass;
  }


  @Override
  public List<ClassInfo> getAllInterfaces() {
    return allInterfaces;
  }

  public List<ClassInfo> getAllSuperClasses() {
    return allSuperClasses;
  }

  @Override
  public boolean isSynthetic() {
    return false;
  }

}
