package slydm.geektimes.training.projects.user.validator.bean.validation;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.GroupSequence;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import slydm.geektimes.training.projects.user.domin.User;

/**
 * @author wangcymy@gmail.com(wangcong) 2021/3/12 11:19
 */
public class BeanValidationDemo {

  public static void main(String[] args) {
//    basic();
    group1();
//    group2();
//    group3();

//    groupOrder();


  }

  /**
   * 同时校验多组时，指定校验顺序
   *
   * <p>
   * <li> 方式一: 在被校验的对象上声明 {@link GroupSequence} 注解@GroupSequence({BasicInfo.class, AdvanceInfo.class})
   *
   * <li> 方式二: 创建一个接口，在接口上声明 {@link GroupSequence} 注解
   *
   * @see RegistrationForm
   * @see CompleteInfo
   */
  private static void groupOrder() {

    RegistrationForm form = buildRegistrationFormWithBasicInfo();
    form.setFirstName("");

    ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    Validator validator = validatorFactory.getValidator();
    Set<ConstraintViolation<RegistrationForm>> violations = validator.validate(form, CompleteInfo.class);
    violations.forEach(action -> {
      System.out.println(action.getPropertyPath() + ":" + action.getMessage());
      System.out.println();
    });

    form = buildRegistrationFormWithBasicInfo();
    populateAdvanceInfo(form);
    violations = validator.validate(form, CompleteInfo.class);
    violations.forEach(action -> {
      System.out.println(action.getPropertyPath() + ":" + action.getMessage());
      System.out.println();
    });

  }


  /**
   * 校验分组 {@link BasicInfo}
   */
  private static void group1() {
    ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    Validator validator = validatorFactory.getValidator();

    RegistrationForm form = buildRegistrationFormWithBasicInfo();
    form.setFirstName(" ");
    form.setPhone(" ");
    form.setEmail("abcs@myEmail.xyz");

    Set<ConstraintViolation<RegistrationForm>> violations = validator.validate(form, BasicInfo.class);

    violations.forEach(action -> {
      System.out.println(action.getPropertyPath() + ":" + action.getMessage());
      System.out.println();
    });

  }


  /**
   * 校验分组 {@link AdvanceInfo}
   */
  private static void group2() {
    ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    Validator validator = validatorFactory.getValidator();

    RegistrationForm form = buildRegistrationFormWithAdvanceInfo();
    form.setZipCode(" ");
    form.setCaptcha(" ");

    Set<ConstraintViolation<RegistrationForm>> violations = validator.validate(form, AdvanceInfo.class);

    violations.forEach(action -> {
      System.out.println(action.getPropertyPath() + ":" + action.getMessage());
      System.out.println();
    });

  }


  /**
   * 校验分组 {@link BasicInfo},{@link AdvanceInfo}
   */
  private static void group3() {
    ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    Validator validator = validatorFactory.getValidator();

    RegistrationForm form = buildRegistrationFormWithBasicInfo();
    populateAdvanceInfo(form);

    form.setCaptcha("");

    Set<ConstraintViolation<RegistrationForm>> violations = validator.validate(form, BasicInfo.class);

    violations.forEach(action -> {
      System.out.println(action.getPropertyPath() + ":" + action.getMessage());
      System.out.println();
    });

    violations = validator.validate(form, AdvanceInfo.class);

    violations.forEach(action -> {
      System.out.println(action.getPropertyPath() + ":" + action.getMessage());
      System.out.println();
    });

  }

  private static RegistrationForm buildRegistrationFormWithBasicInfo() {
    RegistrationForm form = new RegistrationForm();
    form.setFirstName("devender");
    form.setLastName("kumar");
    form.setEmail("anyemail@yopmail.com");
    form.setPhone("12345");
    form.setCaptcha("Y2HAhU5T");
    return form;
  }

  private static RegistrationForm populateAdvanceInfo(RegistrationForm form) {
    form.setCity("Berlin");
    form.setContry("DE");
    form.setStreet("alexa str.");
    form.setZipCode("19923");
    form.setHouseNumber("2a");
    form.setCaptcha("Y2HAhU5T");
    return form;
  }

  private static RegistrationForm buildRegistrationFormWithAdvanceInfo() {
    RegistrationForm form = new RegistrationForm();
    return populateAdvanceInfo(form);
  }


  /**
   * {@link Validator} 基础 API 示例
   */
  private static void basic() {
    ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    Validator validator = validatorFactory.getValidator();

    User user = new User();
    user.setId(1L);

    Set<ConstraintViolation<User>> violations = validator.validate(user);
    violations.forEach(c -> {
      System.out.println(c.getMessage());
    });
  }

}
