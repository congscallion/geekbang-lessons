package slydm.geektimes.training.configuration.microprofile.config.converter;

import org.eclipse.microprofile.config.spi.Converter;

/**
 * 将类型转换器包装为具有优先级
 *
 * @author wangcymy@gmail.com(wangcong) 2021/4/1 11:06
 */
public class PrioritizedConverter<T> implements Converter<T>, Comparable<PrioritizedConverter> {

  private final Converter<T> converter;

  private final int priority;

  public PrioritizedConverter(Converter<T> converter, int priority) {
    this.converter = converter;
    this.priority = priority;
  }

  @Override
  public T convert(String value) throws IllegalArgumentException, NullPointerException {
    return converter.convert(value);
  }

  public int getPriority() {
    return priority;
  }

  public Converter<T> getConverter() {
    return converter;
  }

  @Override
  public int compareTo(PrioritizedConverter other) {
    return Integer.compare(other.getPriority(), this.getPriority());
  }
}
