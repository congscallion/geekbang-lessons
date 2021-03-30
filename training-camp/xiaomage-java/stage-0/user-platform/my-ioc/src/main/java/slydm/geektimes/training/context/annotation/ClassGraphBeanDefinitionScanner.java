package slydm.geektimes.training.context.annotation;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import slydm.geektimes.training.Main;
import slydm.geektimes.training.core.BeanDefinition;
import slydm.geektimes.training.core.ClassPathScannedBeanDefinition;
import slydm.geektimes.training.helper.ListLoadedClassesAgent;

/**
 * 使用 {@link ClassGraph} 实现的类路径组件扫描器
 *
 * @author wangcymy@gmail.com(wangcong) 2021/3/24 9:56
 */
public class ClassGraphBeanDefinitionScanner {

  /**
   * 描述路径
   */
  private String basePackage;

  /**
   * 不需要扫描的路径
   */
  private String[] excludePackages;


  public ClassGraphBeanDefinitionScanner(String basePackage, String[] excludePackages) {
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
          .filter(classInfo -> !classInfo.isAbstract() && !classInfo.isInterface() && !classInfo.isAnnotation())
          .map(ci -> {

            ClassPathScannedBeanDefinition beanDefinition = new ClassPathScannedBeanDefinition(ci);
            return beanDefinition;
          })
          .collect(Collectors.toSet());

      return beanDefinitionSet;
    }

  }


  /**
   * 以下为测试类， 主要是想看下，{@link ClassGraph} 扫描的类是否会被加载到 JVM 中。 结论是:否
   */
  public static void main(String[] args) {

    ClassGraphBeanDefinitionScanner scanner = new ClassGraphBeanDefinitionScanner(
        "slydm.geektimes.training", new String[]{});
    scanner.scan();

    System.out.println("==============================================");
    System.out.println();

    printClassesLoadedBy("BOOTSTRAP");
    printClassesLoadedBy("SYSTEM");
    printClassesLoadedBy("EXTENSION");

    try (ScanResult result = new ClassGraph().acceptClasses(Main.class.getName()).scan()) {

      ClassInfoList allClasses = result.getAllClasses();
      System.out.println(allClasses);

    }

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
