package slydm.geektimes.training.test;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.MethodInfo;
import io.github.classgraph.ScanResult;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import slydm.geektimes.training.context.annotation.Bean;
import slydm.geektimes.training.core.BeanDefinition;
import slydm.geektimes.training.core.ClassPathScannedBeanDefinition;

/**
 * @author wangcymy@gmail.com(wangcong) 2021/3/30 10:03
 */
public class GCMain {

  public static void main(String[] args) throws InterruptedException {

    List<BeanDefinition> beanDefinitionList = new ArrayList<>();
    try (ScanResult result = new ClassGraph().enableClassInfo().enableAnnotationInfo()
        .enableMethodInfo()
        .enableFieldInfo()
        .ignoreFieldVisibility()
        .ignoreMethodVisibility()
        .ignoreClassVisibility()
        .acceptClasses(GCMain.class.getName())
        .scan()) {

      ClassInfo classInfo = result.getClassInfo(GCMain.class.getName());
      BeanDefinition beanDefinition = new ClassPathScannedBeanDefinition(classInfo);
      beanDefinitionList.add(beanDefinition);
    }

    // 保证 ScanResult 彻底关闭
    TimeUnit.SECONDS.sleep(5);

    Set<MethodInfo> annotationMethodList = beanDefinitionList.get(0).getAnnotationMethodList();
    for (MethodInfo methodInfo : annotationMethodList) {
      System.out.println(methodInfo.getAnnotationInfo());
    }

  }

  private static MyMethodInfo toMyMethodInfo(MethodInfo methodInfo) {
    MyMethodInfo myMethodInfo = new MyMethodInfo();
    myMethodInfo.setAnnotationInfo(methodInfo.getAnnotationInfo());
    myMethodInfo.setDeclaringClassName(methodInfo.getClassName());
    myMethodInfo.setDefault(methodInfo.isDefault());
    myMethodInfo.setHasBody(methodInfo.hasBody());
    myMethodInfo.setModifiers(methodInfo.getModifiers());
    myMethodInfo.setName(methodInfo.getName());
    myMethodInfo.setParameterInfo(methodInfo.getParameterInfo());
    myMethodInfo.setTypeDescriptor(methodInfo.getTypeDescriptor());
    myMethodInfo.setTypeDescriptorStr(methodInfo.getTypeDescriptorStr());
    myMethodInfo.setTypeSignature(methodInfo.getTypeSignature());
    myMethodInfo.setTypeSignatureStr(methodInfo.getTypeSignatureStr());
    return myMethodInfo;
  }


  @Bean
  String hello() {
    return "hello";
  }

  @Bean
  public List names() {
    return Arrays.asList("a", "b", "c");
  }


}
