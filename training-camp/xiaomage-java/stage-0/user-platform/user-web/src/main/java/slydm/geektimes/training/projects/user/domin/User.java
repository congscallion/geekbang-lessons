package slydm.geektimes.training.projects.user.domin;

import static javax.persistence.GenerationType.AUTO;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import slydm.geektimes.training.projects.user.validator.bean.validation.UserValid;

/**
 * @author wangcymy@gmail.com(wangcong) 2021/3/9 23:48
 */
@Entity
@Table(name = "users")
@UserValid(idRange = 5)
public class User implements Serializable {

  @Id
  @GeneratedValue(strategy = AUTO)
  private Long id;

  @Column
  private String name;

  @Column
  private String password;

  @Column
  private String email;

  @Column
  private String phoneNumber;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  @Override
  public String toString() {
    return "User{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", password='" + password + '\'' +
        ", email='" + email + '\'' +
        ", phoneNumber='" + phoneNumber + '\'' +
        '}';
  }
}
