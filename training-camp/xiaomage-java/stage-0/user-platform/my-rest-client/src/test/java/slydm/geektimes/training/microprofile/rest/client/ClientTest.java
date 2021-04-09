package slydm.geektimes.training.microprofile.rest.client;

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
  public void testMediaType() {
    MediaType jsonType = MediaType.APPLICATION_JSON_TYPE;
    System.out.println(jsonType);
  }

}
