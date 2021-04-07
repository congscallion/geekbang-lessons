package slydm.geektimes.training.microprofile.rest.client;

import java.security.KeyStore;
import java.util.Map;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Configuration;

/**
 * {@link ClientBuilder} 实现
 *
 * @author wangcymy@gmail.com(wangcong) 2021/4/7 16:55
 */
public class MyClientBuilderImpl extends ClientBuilder {

  @Override
  public ClientBuilder withConfig(Configuration config) {
    return null;
  }

  @Override
  public ClientBuilder sslContext(SSLContext sslContext) {
    return null;
  }

  @Override
  public ClientBuilder keyStore(KeyStore keyStore, char[] password) {
    return null;
  }

  @Override
  public ClientBuilder trustStore(KeyStore trustStore) {
    return null;
  }

  @Override
  public ClientBuilder hostnameVerifier(HostnameVerifier verifier) {
    return null;
  }

  @Override
  public Client build() {
    return createClient();
  }


  protected Client createClient() {
    return new MyClientImpl();
  }

  @Override
  public Configuration getConfiguration() {
    return null;
  }

  @Override
  public ClientBuilder property(String name, Object value) {
    return null;
  }

  @Override
  public ClientBuilder register(Class<?> componentClass) {
    return null;
  }

  @Override
  public ClientBuilder register(Class<?> componentClass, int priority) {
    return null;
  }

  @Override
  public ClientBuilder register(Class<?> componentClass, Class<?>... contracts) {
    return null;
  }

  @Override
  public ClientBuilder register(Class<?> componentClass, Map<Class<?>, Integer> contracts) {
    return null;
  }

  @Override
  public ClientBuilder register(Object component) {
    return null;
  }

  @Override
  public ClientBuilder register(Object component, int priority) {
    return null;
  }

  @Override
  public ClientBuilder register(Object component, Class<?>... contracts) {
    return null;
  }

  @Override
  public ClientBuilder register(Object component, Map<Class<?>, Integer> contracts) {
    return null;
  }
}
