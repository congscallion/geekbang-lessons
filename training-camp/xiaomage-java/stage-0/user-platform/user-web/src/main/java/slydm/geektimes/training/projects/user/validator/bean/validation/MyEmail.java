package slydm.geektimes.training.projects.user.validator.bean.validation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Email;

/**
 * 自定义邮件校验注解
 *
 * @author wangcymy@gmail.com(wangcong) 2021/3/13 11:06
 * @see Email
 */
@Documented
@Constraint(validatedBy = MyEmailAnnotationValidator.class)
@Target({FIELD})
@Retention(RUNTIME)
public @interface MyEmail {

  String message() default "自定义邮件格式只支持*@myEmail.xyz";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  String regexp() default "*@myEmil.xyz";

}
