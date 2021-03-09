package slydm.geektimes.training.projects.user.web.enums;

/**
 * @author wangcymy@gmail.com(wangcong) 2021/3/9 23:50
 */
public enum UserType {

  NORMAL,
  VIP;

  UserType() { // 枚举中构造器是 private

  }

  public static void main(String[] args) {
    UserType.VIP.ordinal();
  }
}
