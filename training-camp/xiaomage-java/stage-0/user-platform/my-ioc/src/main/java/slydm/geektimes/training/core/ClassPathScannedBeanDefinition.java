package slydm.geektimes.training.core;

import io.github.classgraph.ClassInfo;

/**
 * @author 72089101@vivo.com(wangcong) 2021/3/24 15:58
 */
public class ClassPathScannedBeanDefinition implements BeanDefinition {

  private ClassInfo classInfo;

  public ClassPathScannedBeanDefinition(ClassInfo classInfo) {
    this.classInfo = classInfo;
  }

  @Override
  public String getBeanName() {
    return lowerFirstChar(classInfo.getSimpleName());
  }

  public ClassInfo getClassInfo() {
    return classInfo;
  }

  public void setClassInfo(ClassInfo classInfo) {
    this.classInfo = classInfo;
  }

  private String lowerFirstChar(String str) {
    StringBuilder sb = new StringBuilder(str);
    String first = sb.charAt(0) + "";
    sb.replace(0, 1, first.toLowerCase());
    return sb.toString();
  }

}
