package slydm.geektimes.training.projects.user.function;

/**
 * @author wangcymy@gmail.com(wangcong) 2021/3/10 0:23
 */
@FunctionalInterface
public interface ThrowableFunction<T, R> {

  /**
   * Applies this function to the given argument.
   *
   * @param t the function argument
   * @return the function result
   * @throws Throwable if met with any error
   */
  R apply(T t) throws Throwable;

}
