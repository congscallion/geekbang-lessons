package com.slydm.thinking.in.spring.validation;

import com.slydm.thinking.in.spring.ioc.overview.domain.User;
import java.util.List;
import java.util.Locale;
import org.springframework.context.MessageSource;
import org.springframework.context.support.StaticMessageSource;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

/**
 * 自定义 {@link Validator} 示例
 *
 * @author wangcymy@gmail.com(wangcong) 2021/1/7 10:31
 * @see LocalValidatorFactoryBean
 * @see MethodValidationPostProcessor
 */
public class ValidatorDemo {

  public static void main(String[] args) {

    UserValidator validator = new UserValidator();
    User user = new User();
    if (validator.supports(user.getClass())) {

      Errors errors = new BeanPropertyBindingResult(user, "user");
      validator.validate(user, errors);

      // 获取全局异常， reject 创建的异常
      List<ObjectError> globalErrors = errors.getGlobalErrors();
      print(globalErrors);

      // 获取Field异常， rejectValue 创建的异常
      List<FieldError> fieldErrors = errors.getFieldErrors();
      print(fieldErrors);

      // 所有异常
      List<ObjectError> allErrors = errors.getAllErrors();
      print(allErrors);

    }


  }

  private static void print(List<? extends ObjectError> errors) {

    System.out.println();
    MessageSource messageSource = createMessageSource();
    for (ObjectError error : errors) {
      String message = messageSource
          .getMessage(error.getCode(), new Object[0], Locale.getDefault());
      System.out.println(message);
    }
    System.out.println("====================================");

  }


  private static MessageSource createMessageSource() {
    StaticMessageSource messageSource = new StaticMessageSource();
    messageSource.addMessage("id.required", Locale.getDefault(), "the id of user not be null");
    messageSource.addMessage("name.required", Locale.getDefault(), "the name of user not be null");
    return messageSource;
  }


  static class UserValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
      return User.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

      ValidationUtils.rejectIfEmptyOrWhitespace(errors, "id", "id.required");
      ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "name.required");

    }
  }

}
