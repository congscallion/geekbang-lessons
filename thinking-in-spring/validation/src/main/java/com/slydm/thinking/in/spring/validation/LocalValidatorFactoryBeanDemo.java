package com.slydm.thinking.in.spring.validation;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.HibernateValidatorFactory;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

/**
 * {@link LocalValidatorFactoryBean} 示例
 *
 * @author wangcymy@gmail.com(wangcong) 2021/1/7 11:06
 */
public class LocalValidatorFactoryBeanDemo {

  public static void main(String[] args) {

    simpleValidation();
    printSplitLine();
    simpleValidationWithCustomProvider();

  }

  private static void printSplitLine() {
    System.out.println();
    System.out.println("=================================");
    System.out.println();
  }


  public static void simpleValidation() {

    LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
    validator.afterPropertiesSet();

    ValidPerson person = new ValidPerson();
    Set<ConstraintViolation<ValidPerson>> result = validator.validate(person);
    for (ConstraintViolation<ValidPerson> cv : result) {
      String path = cv.getPropertyPath().toString();
      if ("name".equals(path) || "address.street".equals(path)) {
        System.out.println((cv.getConstraintDescriptor().getAnnotation() instanceof NotNull));
      } else {
        System.out.println("Invalid constraint violation with path '" + path + "'");
      }
    }

    Validator nativeValidator = validator.unwrap(Validator.class);
    System.out.println(nativeValidator.getClass().getName());

    ValidatorFactory validatorFactory = validator.unwrap(ValidatorFactory.class);
    System.out.println(validatorFactory instanceof HibernateValidatorFactory);

    HibernateValidatorFactory hibernateValidatorFactory = validator
        .unwrap(HibernateValidatorFactory.class);
    System.out.println(hibernateValidatorFactory instanceof HibernateValidatorFactory);

    validator.destroy();

  }

  public static void simpleValidationWithCustomProvider() {

    LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
    validator.setProviderClass(HibernateValidator.class);
    validator.afterPropertiesSet();

    ValidPerson person = new ValidPerson();
    Set<ConstraintViolation<ValidPerson>> result = validator.validate(person);
    for (ConstraintViolation<ValidPerson> cv : result) {
      String path = cv.getPropertyPath().toString();
      if ("name".equals(path) || "address.street".equals(path)) {
        System.out.println((cv.getConstraintDescriptor().getAnnotation() instanceof NotNull));
      } else {
        System.out.println("Invalid constraint violation with path '" + path + "'");
      }
    }

    Validator nativeValidator = validator.unwrap(Validator.class);
    System.out.println(nativeValidator.getClass().getName());

    ValidatorFactory validatorFactory = validator.unwrap(ValidatorFactory.class);
    System.out.println(validatorFactory instanceof HibernateValidatorFactory);

    HibernateValidatorFactory hibernateValidatorFactory = validator
        .unwrap(HibernateValidatorFactory.class);
    System.out.println(hibernateValidatorFactory instanceof HibernateValidatorFactory);

    validator.destroy();

  }


  public static class ValidPerson {

    @NotNull
    private String name;

    @Valid
    private ValidAddress address = new ValidAddress();

    @Valid
    private List<ValidAddress> addressList = new LinkedList<>();

    @Valid
    private Set<ValidAddress> addressSet = new LinkedHashSet<>();

    public boolean expectsAutowiredValidator = false;

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public ValidAddress getAddress() {
      return address;
    }

    public void setAddress(ValidAddress address) {
      this.address = address;
    }

    public List<ValidAddress> getAddressList() {
      return addressList;
    }

    public void setAddressList(List<ValidAddress> addressList) {
      this.addressList = addressList;
    }

    public Set<ValidAddress> getAddressSet() {
      return addressSet;
    }

    public void setAddressSet(Set<ValidAddress> addressSet) {
      this.addressSet = addressSet;
    }
  }


  public static class ValidAddress {

    @NotNull
    private String street;

    public String getStreet() {
      return street;
    }

    public void setStreet(String street) {
      this.street = street;
    }
  }

}
