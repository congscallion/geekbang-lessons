package slydm.geektimes.training.configuration.microprofile.config.source;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

/**
 * @author wangcymy@gmail.com(wangcong) 2021/4/1 11:48
 */
public class MapConfigSourceTest {

  private MapConfigSource configSource;

  @Before
  public void init() {
    Map<String, String> initData = new HashMap<>();
    initData.put("k1", "v1");
    initData.put("k2", "v2");
    configSource = new MapConfigSource("Map", 100, initData);
  }

  @Test
  public void testGetName() {
    assertEquals("Map", configSource.getName());
  }

  @Test
  public void testGetValue() {
    assertEquals("v1", configSource.getValue("k1"));
    assertEquals("v2", configSource.getValue("k2"));
  }

  @Test
  public void testGetOrdinal() {
    assertEquals(100, configSource.getOrdinal());
  }

  @Test
  public void testGetPropertyNames() {
    assertThat(configSource.getPropertyNames(), hasItems("k1", "k2"));
  }

  @Test
  public void testGetProperties() {
    Map<String, String> properties = configSource.getProperties();
    assertThat(properties.keySet().size(), is(2));
    assertThat(properties.keySet(), hasItems("k1", "k2"));
    assertThat(properties.values().size(), is(2));
    assertThat(properties.values(), hasItems("v1", "v2"));
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testNotAllowedUpdate() {
    Map<String, String> properties = configSource.getProperties();
    properties.put("k3", "V3");
  }


}
