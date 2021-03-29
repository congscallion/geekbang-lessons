package slydm.geektimes.training.projects.user.validator.bean.validation;


import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 表单
 *
 * @author wnagcymy@gmail.com(wangcong) 2021/3/13 10:26
 */
//@GroupSequence({BasicInfo.class, AdvanceInfo.class})
public class RegistrationForm {

  @NotBlank(groups = BasicInfo.class)
  private String firstName;
  @NotBlank(groups = BasicInfo.class)
  private String lastName;
  @MyEmail(groups = BasicInfo.class)
  private String email;
  @NotEmpty(groups = BasicInfo.class)
  private String phone;

  @NotBlank(groups = {BasicInfo.class, AdvanceInfo.class})
  private String captcha;

  @NotBlank(groups = AdvanceInfo.class)
  private String street;

  @NotBlank(groups = AdvanceInfo.class)
  private String houseNumber;

  @NotEmpty(groups = AdvanceInfo.class)
  private String zipCode;

  @NotBlank(groups = AdvanceInfo.class)
  private String city;

  @NotBlank(groups = AdvanceInfo.class)
  private String contry;

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getCaptcha() {
    return captcha;
  }

  public void setCaptcha(String captcha) {
    this.captcha = captcha;
  }

  public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public String getHouseNumber() {
    return houseNumber;
  }

  public void setHouseNumber(String houseNumber) {
    this.houseNumber = houseNumber;
  }

  public String getZipCode() {
    return zipCode;
  }

  public void setZipCode(String zipCode) {
    this.zipCode = zipCode;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getContry() {
    return contry;
  }

  public void setContry(String contry) {
    this.contry = contry;
  }
}
