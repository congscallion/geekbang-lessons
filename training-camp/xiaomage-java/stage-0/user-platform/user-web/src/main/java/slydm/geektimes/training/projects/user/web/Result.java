package slydm.geektimes.training.projects.user.web;

import java.io.Serializable;

/**
 * @author wangcymy@gmail.com(wangcong) 2021/3/11 16:50
 */
public class Result<T> implements Serializable {

  private static final int STATUS_200 = 200;
  private static final String DEFAULT_SUCCESS_MESSAGE = "default.success.message";

  private static final int STATUS_500 = 500;
  private static final String DEFAULT_ERROR_MESSAGE = "default.error.message";

  private T data;
  private int status;
  private String message;

  public Result(T data, int status, String message) {
    this.data = data;
    this.status = status;
    this.message = message;
  }

  public static Result success() {
    return new Result(null, STATUS_200, DEFAULT_SUCCESS_MESSAGE);
  }

  public static Result success(String message) {
    return new Result(null, STATUS_200, message);
  }

  public static <T> Result success(T data) {
    return new Result(data, STATUS_200, DEFAULT_SUCCESS_MESSAGE);
  }

  public static <T> Result success(T data, String message) {
    return new Result(data, STATUS_200, message);
  }


  public static Result error() {
    return new Result(null, STATUS_500, DEFAULT_ERROR_MESSAGE);
  }

  public static Result error(String message) {
    return new Result(null, STATUS_500, message);
  }

  public static <T> Result error(T data) {
    return new Result(data, STATUS_500, DEFAULT_ERROR_MESSAGE);
  }

  public static <T> Result error(T data, String message) {
    return new Result(data, STATUS_500, message);
  }

}
