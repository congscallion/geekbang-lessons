package slydm.geektimes.training.projects.user.validator.bean.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import slydm.geektimes.training.projects.user.domin.User;

/**
 * @author 72089101@vivo.com(wangcong) 2021/3/12 10:59
 */
public class UserValidAnnotationValidator implements ConstraintValidator<UserValid, User> {

  private int idRange;

  @Override
  public void initialize(UserValid annotation) {
    idRange = annotation.idRange();
  }

  @Override
  public boolean isValid(User user, ConstraintValidatorContext context) {

    if (null == user.getId() || user.getId().toString().length() < idRange) {
      return false;
    }

    return true;
  }
}
