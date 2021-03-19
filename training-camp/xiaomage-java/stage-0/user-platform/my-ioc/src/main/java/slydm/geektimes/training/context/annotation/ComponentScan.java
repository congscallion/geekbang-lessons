package slydm.geektimes.training.context.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 组件扫描注解
 *
 * @author 72089101@vivo.com(wangcong) 2021/3/19 16:58
 * @since 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface ComponentScan {


  /**
   * 扫描基础包路径
   */
  String basePackage() default ".";

}
