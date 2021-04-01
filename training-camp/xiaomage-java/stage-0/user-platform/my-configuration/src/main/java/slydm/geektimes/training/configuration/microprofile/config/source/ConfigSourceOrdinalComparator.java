package slydm.geektimes.training.configuration.microprofile.config.source;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;
import java.util.function.Function;
import org.eclipse.microprofile.config.spi.ConfigSource;

/**
 * {@link ConfigSource} 优先级比较器，数值越小，优先级载高
 *
 * @author wangcymy@gmail.com(wangcong) 2021/4/1 9:36
 */
public class ConfigSourceOrdinalComparator<T extends ConfigSource> implements Comparator<T> {

  /**
   * Singleton instance {@link ConfigSourceOrdinalComparator}
   */
  public static final Comparator<ConfigSource> INSTANCE = new ConfigSourceOrdinalComparator();

  private ConfigSourceOrdinalComparator() {
  }

  @Override
  public int compare(ConfigSource o1, ConfigSource o2) {
    return Integer.compare(o1.getOrdinal(), o2.getOrdinal());
  }

  public static <T, U extends Integer> Comparator<T> comparing(Function<? super T, ? extends U> keyExtractor) {
    Objects.requireNonNull(keyExtractor);

    return (Comparator<T> & Serializable) (c1, c2) -> Integer.compare(keyExtractor.apply(c1), keyExtractor.apply(c2));
  }
}
