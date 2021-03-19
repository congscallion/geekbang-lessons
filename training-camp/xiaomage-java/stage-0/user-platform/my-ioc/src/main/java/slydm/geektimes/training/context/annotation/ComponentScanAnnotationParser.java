package slydm.geektimes.training.context.annotation;

import java.io.IOException;
import java.util.Set;

/**
 * {@link ComponentScan} 组件扫描器解析类
 *
 * @author 72089101@vivo.com(wangcong) 2021/3/19 17:01
 */
public class ComponentScanAnnotationParser {

  public Set<Class> parse(String basePackage) throws IOException {

    ClassPathClassScanner scanner = new ClassPathClassScanner(basePackage);
    return scanner.scan();
  }

}
