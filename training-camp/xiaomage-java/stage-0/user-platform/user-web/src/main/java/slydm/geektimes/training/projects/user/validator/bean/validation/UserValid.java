//package slydm.geektimes.training.projects.user.validator.bean.validation;
//
//import java.lang.annotation.ElementType;
//import java.lang.annotation.Retention;
//import java.lang.annotation.RetentionPolicy;
//import java.lang.annotation.Target;
//import javax.validation.Constraint;
//import javax.validation.Payload;
//
///**
// * @author 72089101@vivo.com(wangcong) 2021/3/12 10:56
// */
//@Target(ElementType.TYPE)
//@Retention(RetentionPolicy.RUNTIME)
//@Constraint(validatedBy = UserValidAnnotationValidator.class)
//public @interface UserValid {
//
//  int idRange() default 0;
//
//  String message() default "user 信息校验不通过";
//
//  Class<?>[] groups() default { };
//
//  Class<? extends Payload>[] payload() default { };
//
//}
