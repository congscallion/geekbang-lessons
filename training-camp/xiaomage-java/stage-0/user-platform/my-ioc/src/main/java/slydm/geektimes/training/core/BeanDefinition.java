package slydm.geektimes.training.core;

import io.github.classgraph.AnnotationInfo;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.FieldInfo;
import io.github.classgraph.MethodInfo;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

/**
 * IOC 容器中Bean的描述信息
 *
 * @author wangcymy@gmail.com(wangcong) 2021/3/19 17:13
 */
public interface BeanDefinition {

  /**
   * 获取 bean class full name
   */
  Class getBeanClass();

  /**
   * 类注解列表
   */
  List<AnnotationInfo> getClassAnnotationList();

  /**
   * 带有注解的字段列表
   */
  Set<FieldInfo> getAnnotationFieldList();

  /**
   * 根据字段元信息 获取字段实例
   */
  Field getFieldByFiledInfo(FieldInfo fieldInfo);


  /**
   * 根据方法元信息获取方法
   */
  Method getMethodByMethodInfo(MethodInfo methodInfo);

  /**
   * 带有注解的方法列表
   */
  Set<MethodInfo> getAnnotationMethodList();

  /**
   * 获取 bean 实现的接口列表
   */
  List<ClassInfo> getAllInterfaces();

  /**
   * 获取 bean 直接或间接父类列表
   */
  List<ClassInfo> getAllSuperClasses();


  boolean isSynthetic();

}
