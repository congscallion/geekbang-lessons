package slydm.geektimes.training.context.annotation;

import static slydm.geektimes.training.context.annotation.CommonAnnotationBeanPostProcessor.COMMON_ANNOTATION_PROCESSOR_BEAN_NAME;
import static slydm.geektimes.training.context.annotation.support.ConfigurationClassPostProcessor.CONFIGURATION_ANNOTATION_PROCESSOR_BEAN_NAME;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ScanResult;
import slydm.geektimes.training.context.annotation.support.ConfigurationClassPostProcessor;
import slydm.geektimes.training.core.BeanDefinition;
import slydm.geektimes.training.core.BeanDefinitionRegistry;
import slydm.geektimes.training.core.ClassPathScannedBeanDefinition;
import slydm.geektimes.training.ioc.AnnotationBeanNameGenerator;
import slydm.geektimes.training.ioc.BeanNameGenerator;

/**
 * 负责从类注解中读取 bean 元信息, 主要功能是负责注入 {@link ConfigurationClassPostProcessor} 类。
 *
 * @author wangcymy@gmail.com(wangcong) 2021/3/26 16:28
 */
public class AnnotatedBeanDefinitionReader {

  private BeanDefinitionRegistry beanDefinitionRegistry;
  private BeanNameGenerator beanNameGenerator = new AnnotationBeanNameGenerator();

  public AnnotatedBeanDefinitionReader(BeanDefinitionRegistry beanDefinitionRegistry) {
    this.beanDefinitionRegistry = beanDefinitionRegistry;

    /**
     * 模仿 spring, 向容器中注册注解配置处理类
     */
    registerAnnotationConfigProcessors(beanDefinitionRegistry);

  }

  /**
   * 向容器中注册配置处理类
   */
  private void registerAnnotationConfigProcessors(BeanDefinitionRegistry registry) {
    if (!registry.containsBeanDefinition(CONFIGURATION_ANNOTATION_PROCESSOR_BEAN_NAME)) {
      Class clz = ConfigurationClassPostProcessor.class;
      BeanDefinition beanDefinition = convertClassToBeanDefinition(clz);
      registry.registerBeanDefinition(CONFIGURATION_ANNOTATION_PROCESSOR_BEAN_NAME, beanDefinition);
    }

    if (!registry.containsBeanDefinition(COMMON_ANNOTATION_PROCESSOR_BEAN_NAME)) {
      BeanDefinition def = convertClassToBeanDefinition(CommonAnnotationBeanPostProcessor.class);
      registry.registerBeanDefinition(COMMON_ANNOTATION_PROCESSOR_BEAN_NAME, def);
    }

  }

  /**
   * 将 Bean
   */
  public void registerBean(BeanDefinition beanDefinition) {
    beanDefinitionRegistry.registerBeanDefinition(beanNameGenerator.generateBeanName(beanDefinition), beanDefinition);
  }

  /**
   * 注册 注解类
   */
  public void register(Class<?> annotatedClass) {

    BeanDefinition beanDefinition = convertClassToBeanDefinition(annotatedClass);
    registerBean(beanDefinition);
  }


  private BeanDefinition convertClassToBeanDefinition(Class<?> clz) {
    return getBeanDefinitionFromPath(clz.getName());
  }

  private BeanDefinition getBeanDefinitionFromPath(String fullName) {
    try (ScanResult result = new ClassGraph().acceptClasses(fullName)
        .ignoreFieldVisibility()
        .enableFieldInfo()
        .enableAnnotationInfo()
        .enableMethodInfo()
        .scan()) {

      ClassInfo classInfo = result.getClassInfo(fullName);
      ClassPathScannedBeanDefinition beanDefinition = new ClassPathScannedBeanDefinition(classInfo);
      return beanDefinition;
    } catch (Exception ex) {
      System.out.println(ex);
      throw ex;
    }
  }

}
