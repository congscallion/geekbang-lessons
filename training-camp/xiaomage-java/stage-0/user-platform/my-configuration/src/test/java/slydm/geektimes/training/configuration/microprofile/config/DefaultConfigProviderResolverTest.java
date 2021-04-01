package slydm.geektimes.training.configuration.microprofile.config;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigValue;
import org.eclipse.microprofile.config.spi.ConfigBuilder;
import org.eclipse.microprofile.config.spi.ConfigProviderResolver;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author wangcymy@gmail.com(wangcong) 2021/4/1 11:43
 */
public class DefaultConfigProviderResolverTest {

  @Test
  public void test1() {

    ConfigProviderResolver resolver = ConfigProviderResolver.instance();

    ConfigBuilder builder = resolver.getBuilder();
    Config config = builder.addDefaultSources()
        .addDiscoveredSources()
        .addDiscoveredConverters()
        .forClassLoader(getClass().getClassLoader())
        .build();
    resolver.registerConfig(config, getClass().getClassLoader());

    Config config1 = resolver.getConfig(getClass().getClassLoader());
    ConfigValue configValue = config1.getConfigValue("os.version");

    Assert.assertEquals("10.0", configValue.getValue());

  }

}
