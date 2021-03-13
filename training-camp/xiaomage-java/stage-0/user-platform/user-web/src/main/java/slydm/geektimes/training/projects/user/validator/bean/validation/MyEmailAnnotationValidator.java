package slydm.geektimes.training.projects.user.validator.bean.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 自定义 邮件 校验器
 * <p>
 * 自定义邮件校验格式默认只支持: *@myEmail.xyz
 *
 * <p>
 * {@link MyEmail} 可配置，但是会被忽略，主要是方便以后改
 *
 * @author 72089101@vivo.com(wangcong) 2021/3/13 11:08
 */
public class MyEmailAnnotationValidator implements ConstraintValidator<MyEmail, String> {

  private static final String DEFAULT_EMAIL_PATTERN = "\\w+@myEmail.xyz";

  private final static Pattern DEFAULT_PATTERN = Pattern.compile(DEFAULT_EMAIL_PATTERN);

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {

    Matcher matcher = DEFAULT_PATTERN.matcher(value);
    if (!matcher.matches()) {
      return false;
    }
    return true;
  }
}
