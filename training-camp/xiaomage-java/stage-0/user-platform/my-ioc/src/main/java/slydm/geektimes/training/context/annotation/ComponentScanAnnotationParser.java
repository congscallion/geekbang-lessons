package slydm.geektimes.training.context.annotation;

import java.util.Set;
import slydm.geektimes.training.context.annotation.support.ComponentScanAttr;
import slydm.geektimes.training.core.BeanDefinition;
import slydm.geektimes.training.core.BeanDefinitionRegistry;
import slydm.geektimes.training.ioc.AnnotationBeanNameGenerator;
import slydm.geektimes.training.ioc.BeanNameGenerator;

/**
 * {@link ComponentScan} 组件扫描器解析类
 *
 * @author wangcymy@gmail.com(wangcong) 2021/3/19 17:01
 */
public class ComponentScanAnnotationParser {

  private BeanDefinitionRegistry beanDefinitionRegistry;

  private BeanNameGenerator beanNameGenerator = new AnnotationBeanNameGenerator();


  public ComponentScanAnnotationParser(BeanDefinitionRegistry beanDefinitionRegistry) {
    this.beanDefinitionRegistry = beanDefinitionRegistry;
  }

  public Set<BeanDefinition> parse(ComponentScanAttr componentScan) {

    ClassPathComponentWithClassGraphScanner scanner = new ClassPathComponentWithClassGraphScanner(
        componentScan.getBasePackage(),
        componentScan.getExcludePackages());
    Set<BeanDefinition> beanDefinitionSet = scanner.scan();

    beanDefinitionSet.stream()
        .forEach(bdf -> beanDefinitionRegistry.registerBeanDefinition(beanNameGenerator.generateBeanName(bdf), bdf));

    return beanDefinitionSet;
  }

}
