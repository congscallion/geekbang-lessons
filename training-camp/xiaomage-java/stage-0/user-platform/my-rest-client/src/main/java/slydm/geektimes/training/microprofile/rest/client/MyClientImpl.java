package slydm.geektimes.training.microprofile.rest.client;

import java.net.URI;
import java.util.Map;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.UriBuilder;

/**
 * @author wangcymy@gmail.com(wangcong) 2021/4/7 16:53
 */
public class MyClientImpl implements Client {

  protected boolean closed;

  @Override
  public void close() {
    this.closed = true;
  }

  public void abortIfClosed() {
    if (isClosed()) {
      throw new IllegalStateException("client is closed.");
    }
  }

  public boolean isClosed() {
    return closed;
  }


  @Override
  public WebTarget target(String uri) {
    abortIfClosed();
    if (uri == null) {
      throw new NullPointerException("uri was null");
    }
    return createClientWebTarget(this, uri);
  }

  @Override
  public WebTarget target(URI uri) {
    abortIfClosed();
    if (uri == null) {
      throw new NullPointerException("uri was null");
    }
    return createClientWebTarget(this, uri);
  }

  @Override
  public WebTarget target(UriBuilder uriBuilder) {
    abortIfClosed();
    if (uriBuilder == null) {
      throw new NullPointerException("uriBuilder was null");
    }
    return createClientWebTarget(this, uriBuilder);
  }

  @Override
  public WebTarget target(Link link) {
    abortIfClosed();
    if (link == null) {
      throw new NullPointerException("link was null");
    }
    URI uri = link.getUri();
    return createClientWebTarget(this, uri);
  }


  protected MyWebTarget createClientWebTarget(MyClientImpl myClient, String uri) {
    return new MyWebTarget(myClient, uri);
  }

  protected MyWebTarget createClientWebTarget(MyClientImpl myClient, URI uri) {
    return new MyWebTarget(myClient, uri);
  }

  protected MyWebTarget createClientWebTarget(MyClientImpl myClient, UriBuilder uriBuilder) {
    return new MyWebTarget(myClient, uriBuilder);
  }


  @Override
  public Builder invocation(Link link) {
    return null;
  }

  @Override
  public SSLContext getSslContext() {
    return null;
  }

  @Override
  public HostnameVerifier getHostnameVerifier() {
    return null;
  }

  @Override
  public Configuration getConfiguration() {
    return null;
  }

  @Override
  public Client property(String name, Object value) {
    return null;
  }

  @Override
  public Client register(Class<?> componentClass) {
    return null;
  }

  @Override
  public Client register(Class<?> componentClass, int priority) {
    return null;
  }

  @Override
  public Client register(Class<?> componentClass, Class<?>... contracts) {
    return null;
  }

  @Override
  public Client register(Class<?> componentClass, Map<Class<?>, Integer> contracts) {
    return null;
  }

  @Override
  public Client register(Object component) {
    return null;
  }

  @Override
  public Client register(Object component, int priority) {
    return null;
  }

  @Override
  public Client register(Object component, Class<?>... contracts) {
    return null;
  }

  @Override
  public Client register(Object component, Map<Class<?>, Integer> contracts) {
    return null;
  }
}
