package slydm.geektimes.training.core;

import io.github.classgraph.AnnotationInfo;
import io.github.classgraph.AnnotationInfoList;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.FieldInfo;
import io.github.classgraph.MethodInfo;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import slydm.geektimes.training.context.annotation.Bean;

/**
 * 通过 {@link Bean} 注解定义的 Bean.
 *
 * @author wangcymy@gmail.com(wangcong) 2021/3/30 11:55
 */
public class MethodBeanDefinition implements BeanDefinition {

  private Class beanClass;
  private String methodName;
  private AnnotationInfoList annotationInfo;
  private Class ownerClass;
  private Method method;

  @Override
  public Class getBeanClass() {
    return beanClass;
  }

  @Override
  public List<AnnotationInfo> getClassAnnotationList() {
    return Collections.EMPTY_LIST;
  }

  @Override
  public List<FieldInfo> getAnnotationFieldList() {
    return Collections.EMPTY_LIST;
  }

  @Override
  public Method getMethodByMethodInfo(MethodInfo methodInfo) {
    return null;
  }

  @Override
  public Set<MethodInfo> getAnnotationMethodList() {
    return Collections.EMPTY_SET;
  }

  @Override
  public List<ClassInfo> getAllInterfaces() {
    return Collections.EMPTY_LIST;
  }

  @Override
  public List<ClassInfo> getAllSuperClasses() {
    return Collections.EMPTY_LIST;
  }

  @Override
  public boolean isSynthetic() {
    return false;
  }

  public void setBeanClass(Class beanClass) {
    this.beanClass = beanClass;
  }

  public String getMethodName() {
    return methodName;
  }

  public void setMethodName(String methodName) {
    this.methodName = methodName;
  }

  public AnnotationInfoList getAnnotationInfo() {
    return annotationInfo;
  }

  public void setAnnotationInfo(AnnotationInfoList annotationInfo) {
    this.annotationInfo = annotationInfo;
  }

  public Class getOwnerClass() {
    return ownerClass;
  }

  public void setOwnerClass(Class ownerClass) {
    this.ownerClass = ownerClass;
  }

  public Method getMethod() {
    return method;
  }

  public void setMethod(Method method) {
    this.method = method;
  }
}
