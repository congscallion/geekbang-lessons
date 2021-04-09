package slydm.geektimes.training.microprofile.rest.client;

import javax.ws.rs.core.UriBuilder;
import org.junit.Test;

/**
 * @author wangcymy@gmail.com(wangcong) 2021/4/9 18:29
 */
public class MyUriBuilderImplTest {

  @Test
  public void testUri() {
    UriBuilder uri = new MyUriBuilderImpl().uri("http://127.0.0.1:8080/users/add");
    System.out.println(uri);
  }

}
