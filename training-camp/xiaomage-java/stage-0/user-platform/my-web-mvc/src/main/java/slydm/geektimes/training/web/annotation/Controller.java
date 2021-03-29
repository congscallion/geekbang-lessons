package slydm.geektimes.training.web.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import slydm.geektimes.training.context.annotation.Component;

/**
 * @author wangcymy@gmail.com(wangcong) 2021/3/29 17:59
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Controller {

  /**
   * 指定 controller名称
   */
  String value() default "";

}
