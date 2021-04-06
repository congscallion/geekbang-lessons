package slydm.geektimes.training.core.env;

import static org.junit.Assert.assertEquals;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.spi.ConfigBuilder;
import org.eclipse.microprofile.config.spi.ConfigProviderResolver;
import org.junit.Before;
import org.junit.Test;

/**
 * @author wangcymy@gmail.com(wangcong) 2021/4/2 16:41
 */
public class ConfigSourcesPropertyResolverTest {

  ConfigSourcesPropertyResolver propertyResolver;

  @Before
  public void init() {
    ConfigProviderResolver providerResolver = ConfigProviderResolver.instance();
    ConfigBuilder builder = providerResolver.getBuilder();
    Config config = builder.addDefaultSources()
        .addDiscoveredSources()
        .addDiscoveredConverters()
        .build();
    propertyResolver = new ConfigSourcesPropertyResolver(config);
  }


  @Test
  public void testContainsProperty() {
    assertEquals(true, propertyResolver.containsProperty("os.name"));
  }

  @Test
  public void testGetProperty() {
    assertEquals("Windows 10", propertyResolver.getProperty("os.name"));
    assertEquals("Default", propertyResolver.getProperty("os.name.os", "Default"));
    Double val = propertyResolver.getProperty("java.class.version", Double.class);
    assertEquals(52.0D, val, 0);
  }

  @Test
  public void testResolvePlaceholders() {

    assertEquals("Windows 10", propertyResolver.resolvePlaceholders("${os.name}"));
    assertEquals("1.8.0_201", propertyResolver.resolvePlaceholders("${java.version}"));


  }


}
