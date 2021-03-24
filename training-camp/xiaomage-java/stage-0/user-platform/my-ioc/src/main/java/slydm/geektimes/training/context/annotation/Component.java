package slydm.geektimes.training.context.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标记标注该注解的类为一个组件，在classpath下可以被自动发现，扫描，注册
 *
 * @author 72089101@vivo.com(wangcong) 2021/3/24 9:50
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Component {

  /**
   * 组件逻辑名，当不指定时注册该类时会自动生成
   */
  String value() default "";
}
