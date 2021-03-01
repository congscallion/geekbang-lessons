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

/**
 * {@link Errors} 示例
 *
 * @author wangcymy@gmail.com(wangcong) 2021/1/7 8:38
 */
public class ErrorsMessageDemo {

  public static void main(String[] args) {

    User user = new User();
    // Errors 实例 BeanPropertyBindingResult
    Errors errors = new BeanPropertyBindingResult(user, "user");

    // 生成 ObjectError, 针对 Object 实例
    errors.reject("user.properties.not.null");

    // 生成 FieldError, 针对 Field
    errors.rejectValue("name", "name.required");

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
    messageSource.addMessage("user.properties.not.null", Locale.getDefault(), "user 对象所有属性不能为空");
    messageSource.addMessage("name.required", Locale.getDefault(), "the name of user not be null");
    return messageSource;
  }


}
