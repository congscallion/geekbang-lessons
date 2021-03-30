package slydm.geektimes.training.core;

import io.github.classgraph.AnnotationInfo;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.FieldInfo;
import io.github.classgraph.MethodInfo;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 代表 {@link ClassInfo} 对象的 {@link BeanDefinition} 实例
 *
 * @author wangcymy@gmail.com(wangcong) 2021/3/24 15:58
 */
public class ClassPathScannedBeanDefinition implements BeanDefinition {

  private final Class beanClass;

  /**
   * 类注解列表
   */
  private final List<AnnotationInfo> classAnnotationList;

  /**
   * 带有注解的字段列表
   */
  private final List<FieldInfo> annotationFieldList;

  /**
   * 带有注解的方法列表
   */
  private final Set<MethodInfo> annotationMethodList;

  /**
   * 所有实现的接口
   */
  private final List<ClassInfo> allInterfaces;

  /**
   * 直接或间接父类
   */
  private final List<ClassInfo> allSuperClasses;

  /**
   * 方法与方法元信息映射
   */
  private final Map<MethodInfo, Method> methodInfoMethodMap;

  public ClassPathScannedBeanDefinition(ClassInfo classInfo) {
    this.beanClass = classInfo.loadClass();

    List<FieldInfo> annotationFieldList = classInfo.getDeclaredFieldInfo()
        .filter(fieldInfo -> notEmpty(fieldInfo.getAnnotationInfo()))
        .stream().collect(Collectors.toList());
    this.annotationFieldList = annotationFieldList;

    Map<MethodInfo, Method> methodInfoMethodMap = classInfo.getMethodInfo()
        .filter(methodInfo -> notEmpty(methodInfo.getAnnotationInfo()))
        .stream()
        .collect(Collectors.toMap(m -> m, m -> m.loadClassAndGetMethod()));

    this.methodInfoMethodMap = methodInfoMethodMap;

    this.annotationMethodList = methodInfoMethodMap.keySet();

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

  @Override
  public Method getMethodByMethodInfo(MethodInfo methodInfo) {
    return this.methodInfoMethodMap.get(methodInfo);
  }

  public Set<MethodInfo> getAnnotationMethodList() {
    return annotationMethodList;
  }

  @Override
  public Class getBeanClass() {
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
