package slydm.geektimes.training.microprofile.rest.client;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

/**
 * @author wangcymy@gmail.com(wangcong) 2021/4/7 17:04
 */
public class MyWebTarget implements WebTarget {

  private MyClientImpl client;
  protected UriBuilder uriBuilder;

  protected MyWebTarget(final MyClientImpl client) {
    this.client = client;
  }


  public MyWebTarget(final MyClientImpl client, final String uri) {
    this(client);
    uriBuilder = uriBuilderFromUri(uri);
  }


  public MyWebTarget(final MyClientImpl client, final URI uri) {
    this(client);
    uriBuilder = uriBuilderFromUri(uri);
  }

  public MyWebTarget(final MyClientImpl client, final UriBuilder uriBuilder) {
    this(client);
    this.uriBuilder = uriBuilder.clone();
  }


  private static UriBuilder uriBuilderFromUri(URI uri) {
    return new MyUriBuilderImpl().uri(uri);
  }

  private static UriBuilder uriBuilderFromUri(String uri) {
    return new MyUriBuilderImpl().uri(uri);
  }


  @Override
  public URI getUri() {
    client.abortIfClosed();
    return uriBuilder.build();
  }

  @Override
  public UriBuilder getUriBuilder() {
    client.abortIfClosed();
    return uriBuilder.clone();
  }

  @Override
  public WebTarget path(String path) {

    client.abortIfClosed();
    if (path == null) {
      throw new NullPointerException("path was null");
    }
    UriBuilder copy = uriBuilder.clone().path(path);
    return newInstance(client, copy);
  }


  @Override
  public WebTarget resolveTemplate(String name, Object value) {

    client.abortIfClosed();
    if (name == null) {
      throw new NullPointerException("name was null");
    }
    if (value == null) {
      throw new NullPointerException("value was null");
    }
    UriBuilder copy = uriBuilder.clone().resolveTemplate(name, value);
    MyWebTarget target = newInstance(client, copy);
    return target;
  }

  @Override
  public WebTarget resolveTemplate(String name, Object value, boolean encodeSlashInPath) {

    client.abortIfClosed();
    if (name == null) {
      throw new NullPointerException("name was null");
    }
    if (value == null) {
      throw new NullPointerException("value was null");
    }
    UriBuilder copy = uriBuilder.clone().resolveTemplate(name, value, encodeSlashInPath);
    MyWebTarget target = newInstance(client, copy);
    return target;
  }

  @Override
  public WebTarget resolveTemplateFromEncoded(String name, Object value) {

    client.abortIfClosed();
    if (name == null) {
      throw new NullPointerException("name was null");
    }
    if (value == null) {
      throw new NullPointerException("value was null");
    }
    UriBuilder copy = uriBuilder.clone().resolveTemplateFromEncoded(name, value);
    WebTarget target = newInstance(client, copy);
    return target;
  }

  @Override
  public WebTarget resolveTemplates(Map<String, Object> templateValues) {

    client.abortIfClosed();
    if (templateValues == null) {
      throw new NullPointerException("templateValues was null");
    }
    if (templateValues.isEmpty()) {
      return this;
    }
    Map<String, Object> vals = new HashMap<String, Object>();
    for (Map.Entry<String, Object> entry : templateValues.entrySet()) {
      if (entry.getKey() == null || entry.getValue() == null) {
        throw new NullPointerException("templateValues entry was null");
      }
      vals.put(entry.getKey(), entry.getValue());
    }
    UriBuilder copy = uriBuilder.clone().resolveTemplates(vals);
    WebTarget target = newInstance(client, copy);
    return target;
  }

  @Override
  public WebTarget resolveTemplates(Map<String, Object> templateValues, boolean encodeSlashInPath) {

    client.abortIfClosed();
    if (templateValues == null) {
      throw new NullPointerException("templateValues was null");
    }
    if (templateValues.isEmpty()) {
      return this;
    }
    Map<String, Object> vals = new HashMap<String, Object>();
    for (Map.Entry<String, Object> entry : templateValues.entrySet()) {
      if (entry.getKey() == null || entry.getValue() == null) {
        throw new NullPointerException("templateValues entry was null");
      }
      vals.put(entry.getKey(), entry.getValue());
    }
    UriBuilder copy = uriBuilder.clone().resolveTemplates(vals, encodeSlashInPath);
    WebTarget target = newInstance(client, copy);
    return target;
  }

  @Override
  public WebTarget resolveTemplatesFromEncoded(Map<String, Object> templateValues) {

    client.abortIfClosed();
    if (templateValues == null) {
      throw new NullPointerException("templateValues was null");
    }
    if (templateValues.isEmpty()) {
      return this;
    }
    Map<String, Object> vals = new HashMap<String, Object>();
    for (Map.Entry<String, Object> entry : templateValues.entrySet()) {
      if (entry.getKey() == null || entry.getValue() == null) {
        throw new NullPointerException("templateValues entry was null");
      }
      vals.put(entry.getKey(), entry.getValue());
    }
    UriBuilder copy = uriBuilder.clone().resolveTemplatesFromEncoded(vals);
    WebTarget target = newInstance(client, copy);
    return target;
  }

  @Override
  public WebTarget matrixParam(String name, Object... values) {

    client.abortIfClosed();
    if (name == null) {
      throw new NullPointerException("name was null");
    }
    UriBuilder copy = uriBuilder.clone();
    copy.matrixParam(name, values);
    return newInstance(client, copy);
  }

  @Override
  public WebTarget queryParam(String name, Object... values) {

    client.abortIfClosed();
    if (name == null) {
      throw new NullPointerException("name was null");
    }
    UriBuilder copy = uriBuilder.clone();
    copy.queryParam(name, values);
    return newInstance(client, copy);
  }


  protected MyWebTarget newInstance(MyClientImpl client, UriBuilder uriBuilder) {
    return new MyWebTarget(client, uriBuilder);
  }


  @Override
  public Invocation.Builder request() {

    client.abortIfClosed();
    ClientInvocationBuilder builder = createClientInvocationBuilder(client, uriBuilder.build());
    builder.setTarget(this);
    return builder;
  }

  @Override
  public Builder request(String... acceptedResponseTypes) {
    return request().accept(acceptedResponseTypes);
  }

  @Override
  public Builder request(MediaType... acceptedResponseTypes) {
    return request().accept(acceptedResponseTypes);
  }

  protected ClientInvocationBuilder createClientInvocationBuilder(Client client, URI uri) {
    return new ClientInvocationBuilder(client, uri);
  }

  @Override
  public Configuration getConfiguration() {
    return null;
  }

  @Override
  public WebTarget property(String name, Object value) {
    return null;
  }

  @Override
  public WebTarget register(Class<?> componentClass) {
    return null;
  }

  @Override
  public WebTarget register(Class<?> componentClass, int priority) {
    return null;
  }

  @Override
  public WebTarget register(Class<?> componentClass, Class<?>... contracts) {
    return null;
  }

  @Override
  public WebTarget register(Class<?> componentClass, Map<Class<?>, Integer> contracts) {
    return null;
  }

  @Override
  public WebTarget register(Object component) {
    return null;
  }

  @Override
  public WebTarget register(Object component, int priority) {
    return null;
  }

  @Override
  public WebTarget register(Object component, Class<?>... contracts) {
    return null;
  }

  @Override
  public WebTarget register(Object component, Map<Class<?>, Integer> contracts) {
    return null;
  }
}
