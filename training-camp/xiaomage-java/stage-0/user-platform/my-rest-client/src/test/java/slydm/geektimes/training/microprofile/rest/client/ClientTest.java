package slydm.geektimes.training.microprofile.rest.client;

import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.junit.Test;
import slydm.geektimes.training.microprofile.rest.engines.URLConnectionEngine;

/**
 * @author wangcymy@gmail.com(wangcong) 2021/4/9 11:37
 */
public class ClientTest {

  @Test
  public void testGetText() {

    ClientBuilder clientBuilder = ClientBuilder.newBuilder();

    Client client = clientBuilder.build();

    Response response = client.target("https://www.baidu.com")
        .request()
        .get();

    System.out.println(response.readEntity(String.class));

  }

  @Test
  public void test2() {

    MyClientBuilderImpl clientBuilder = (MyClientBuilderImpl) ClientBuilder.newBuilder();
    Client client = clientBuilder
        .httpEngine(new URLConnectionEngine())
        .build();

    String p2 = "{\"name\":\"Li\",\"age\":20}";

    Response response = client
        .target("http://127.0.0.1:8080/users/add")
        .request()
        .post(Entity.entity(p2, MediaType.APPLICATION_JSON_TYPE));
    System.out.println(response.readEntity(String.class));
  }


  @Test
  public void test3() {

    MyClientBuilderImpl clientBuilder = (MyClientBuilderImpl) ClientBuilder.newBuilder();
    Client client = clientBuilder
        .httpEngine(new URLConnectionEngine())
        .build();

    Map<String, Object> p2 = new HashMap<>();
    p2.put("name", "Ar");
    p2.put("age", 30);

    Response response = client
        .target("http://127.0.0.1:8080/users/add")
        .request()
        .post(Entity.entity(p2, MediaType.APPLICATION_JSON_TYPE));
    System.out.println(response.readEntity(Map.class));
  }

  /**
   *
   *以下测试在内网完成, 后端接口在公司内部服务，因此，提交前已删除,运行不起来，很正常
   *
   *
   */


  /**
   * 测试cookie
   */
  @Test
  public void test4() {

    Response response = ClientBuilder.newBuilder().build()
        .target("")
        .request()
        .get();

    System.out.println(response.getCookies());
  }


  /**
   * post 测试请求头
   */
  @Test
  public void test5() {

    Map<String, Object> p2 = new HashMap<>();
    p2.put("uuid", "");
    p2.put("password", "");

    Response response = ClientBuilder.newBuilder().build()
        .target("")
        .request()
        .buildPost(Entity.entity(p2, MediaType.APPLICATION_JSON_TYPE))
        .invoke();

    System.out.println(response.getCookies());
    System.out.println(response.readEntity(String.class));
  }


  /**
   * post 测试请求头
   */
  @Test
  public void test6() {

    Map<String, Object> p2 = new HashMap<>();
    p2.put("uuid", "");
    p2.put("password", "");

    Response response = ClientBuilder.newBuilder().build()
        .target("")
        .request()
        .buildPost(Entity.entity(p2, MediaType.APPLICATION_JSON_TYPE))
        .invoke();

    System.out.println(response.getCookies());
    Map map = response.readEntity(Map.class);
    System.out.println(map);
  }


  @Test
  public void testMediaType() {
    MediaType jsonType = MediaType.APPLICATION_JSON_TYPE;
    System.out.println(jsonType);
  }

}
