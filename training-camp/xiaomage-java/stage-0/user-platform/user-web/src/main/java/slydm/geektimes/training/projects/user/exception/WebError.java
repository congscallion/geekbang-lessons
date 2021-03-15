package slydm.geektimes.training.projects.user.exception;

/**
 * @author wangcymy@gmail.com(wangcong) 2021/3/12 15:21
 */
public enum WebError implements ErrorCode {

  LOGIN_ERR("Web-00", "用户名或密码错误");

  private final String code;
  private final String describe;

  private WebError(String code, String describe) {
    this.code = code;
    this.describe = describe;
  }

  @Override
  public String getCode() {
    return this.code;
  }

  @Override
  public String getDescription() {
    return this.describe;
  }

  @Override
  public String toString() {
    return String.format("Code:[%s], Describe:[%s]", this.code,
        this.describe);
  }

}
