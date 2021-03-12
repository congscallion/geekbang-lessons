//package slydm.geektimes.training.projects.user.validator.bean.validation;
//
//import java.util.Set;
//import javax.validation.ConstraintViolation;
//import javax.validation.Validation;
//import javax.validation.Validator;
//import javax.validation.ValidatorFactory;
//import slydm.geektimes.training.projects.user.domin.User;
//
///**
// * @author 72089101@vivo.com(wangcong) 2021/3/12 11:19
// */
//public class BeanValidationDemo {
//
//  public static void main(String[] args) {
//
//    ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
//
//    Validator validator = validatorFactory.getValidator();
//
//    User user = new User();
//    user.setId(1L);
//
//    Set<ConstraintViolation<User>> violations = validator.validate(user);
//    violations.forEach(c -> {
//      System.out.println(c.getMessage());
//    });
//  }
//
//}
