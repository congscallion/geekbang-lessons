package slydm.geektimes.training.projects.user.exception;

/**
 * @author 72089101@vivo.com(wangcong) 2021/3/12 15:26
 */
public interface ErrorCode {

  // 错误码编号
  String getCode();

  // 错误码描述
  String getDescription();

  String toString();

}
