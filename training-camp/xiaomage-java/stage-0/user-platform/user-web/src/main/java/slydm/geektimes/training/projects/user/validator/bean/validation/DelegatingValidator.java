package slydm.geektimes.training.projects.user.validator.bean.validation;

import java.util.Set;
import javax.annotation.PostConstruct;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.executable.ExecutableValidator;
import javax.validation.metadata.BeanDescriptor;
import slydm.geektimes.training.context.annotation.Component;

/**
 * 静态代理 {@link Validator} 实现
 *
 * @author wangcymy@gmail.com(wangcong) 2021/3/12 11:48
 */
@Component
public class DelegatingValidator implements Validator {

  private Validator validator;

  @PostConstruct
  public void init() {
    ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    this.validator = validatorFactory.getValidator();
  }

  @Override
  public <T> Set<ConstraintViolation<T>> validate(T object, Class<?>... groups) {
    return validator.validate(object, groups);
  }

  @Override
  public <T> Set<ConstraintViolation<T>> validateProperty(T object, String propertyName, Class<?>... groups) {
    return validator.validateProperty(object, propertyName, groups);
  }

  @Override
  public <T> Set<ConstraintViolation<T>> validateValue(Class<T> beanType, String propertyName, Object value,
      Class<?>... groups) {
    return validator.validateValue(beanType, propertyName, value, groups);
  }

  @Override
  public BeanDescriptor getConstraintsForClass(Class<?> clazz) {
    return validator.getConstraintsForClass(clazz);
  }

  @Override
  public <T> T unwrap(Class<T> type) {
    return validator.unwrap(type);
  }

  @Override
  public ExecutableValidator forExecutables() {
    return validator.forExecutables();
  }
}
