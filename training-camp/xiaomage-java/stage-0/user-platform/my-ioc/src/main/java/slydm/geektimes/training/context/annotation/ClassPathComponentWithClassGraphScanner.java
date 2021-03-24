package slydm.geektimes.training.context.annotation;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import slydm.geektimes.training.core.BeanDefinition;
import slydm.geektimes.training.core.ClassPathScannedBeanDefinition;
import slydm.geektimes.training.helper.ListLoadedClassesAgent;

/**
 * 使用 {@link ClassGraph} 实现的类路径组件扫描器
 *
 * @author wangcymy@gmail.com(wangcong) 2021/3/24 9:56
 */
public class ClassPathComponentWithClassGraphScanner {

  /**
   * 描述路径
   */
  private String basePackage;

  /**
   * 不需要扫描的路径
   */
  private String[] excludePackages;


  public ClassPathComponentWithClassGraphScanner(String basePackage, String[] excludePackages) {
    this.basePackage = basePackage;
    this.excludePackages = excludePackages;
  }


  public Set<BeanDefinition> scan() {

    try (ScanResult result = new ClassGraph().enableClassInfo().enableAnnotationInfo()
        .enableMethodInfo()
        .enableFieldInfo()
        .ignoreFieldVisibility()
        .acceptPackages(basePackage)
        .rejectPackages(excludePackages)
        .scan()) {

      Set<BeanDefinition> beanDefinitionSet = result
          .getClassesWithAnnotation(Component.class.getName())
          .stream()
          .map(ci -> new ClassPathScannedBeanDefinition(ci))
          .collect(Collectors.toSet());

      return beanDefinitionSet;
//
//      for (ClassInfo classInfo : classInfos) {
//
//        FieldInfoList declaredFieldInfo = classInfo.getDeclaredFieldInfo();
//        for (FieldInfo fieldInfo : declaredFieldInfo) {
//          System.out.println(fieldInfo);
//        }
//
//        FieldInfoList fieldInfoList = classInfo.getFieldInfo();
//
//        fieldInfoList.stream()
//            .filter(fieldInfo -> fieldInfo.hasAnnotation(Resource.class.getName()))
//            .forEach(fi -> System.out.println(fi));
//
//        for (FieldInfo fieldInfo : fieldInfoList) {
//          System.out.println(fieldInfo);
//        }
//
//        MethodInfoList methodAndConstructorInfo = classInfo.getMethodAndConstructorInfo();
//        for (MethodInfo methodInfo : methodAndConstructorInfo) {
//          System.out.println(methodInfo);
//
//          AnnotationInfoList annotationInfo = methodInfo.getAnnotationInfo();
//          for (AnnotationInfo annotation : annotationInfo) {
//            System.out.println(annotation);
//          }
//        }
//
//        ClassInfoList methodAnnotations = classInfo.getMethodAnnotations();
//        for (ClassInfo methodAnnotation : methodAnnotations) {
//          System.out.println(methodAnnotation);
//        }
//
//        ClassInfoList fieldAnnotations = classInfo.getFieldAnnotations();
//        for (ClassInfo fieldAnnotation : fieldAnnotations) {
//          System.out.println(fieldAnnotation);
//        }
//
//        AnnotationInfo annotationInfo = classInfo.getAnnotationInfo(String.valueOf(Resource.class));
//        System.out.println(annotationInfo);
//
//        System.out.println(classInfo.getClass());
//        System.out.println(classInfo.getName());
//      }
    }

  }

  public static void main(String[] args) {

    ClassPathComponentWithClassGraphScanner scanner = new ClassPathComponentWithClassGraphScanner(
        "slydm.geektimes.training", new String[]{});
    scanner.scan();

    System.out.println("==============================================");
    System.out.println();

    printClassesLoadedBy("BOOTSTRAP");
    printClassesLoadedBy("SYSTEM");
    printClassesLoadedBy("EXTENSION");

  }

  private static void printClassesLoadedBy(String classLoaderType) {
    System.out.println(classLoaderType + " ClassLoader : ");
    Class<?>[] classes = ListLoadedClassesAgent.listLoadedClasses(classLoaderType);
    System.out.println("length: " + classes.length);
    Arrays.asList(classes)
        .stream()
        .filter(clz -> clz != null)
        .map(clz -> clz.getName())
        .filter(clz -> clz.startsWith("slydm."))
        .forEach(clazz -> System.out.println(clazz));

    /*

     从输出中可以看出，classgraph 虽然扫描了class并且获取到class的元数据信息，但是并没有把class加载到jvm
     output:

    ==============================================

    BOOTSTRAP ClassLoader :
    length: 862
    SYSTEM ClassLoader :
    length: 364
    slydm.geektimes.training.context.annotation.Component
    slydm.geektimes.training.context.annotation.ClassPathComponentWithClassGraphScanner
    slydm.geektimes.training.helper.ListLoadedClassesAgent
    EXTENSION ClassLoader :
    length: 10*/

  }

}
