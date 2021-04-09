package slydm.geektimes.training.microprofile.rest.client;

import java.net.URI;
import java.util.Locale;
import java.util.stream.Stream;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.client.AsyncInvoker;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

/**
 * @author wangcymy@gmail.com(wangcong) 2021/4/7 18:05
 */
public class ClientInvocationBuilder implements Invocation.Builder {

  protected ClientInvocation invocation;
  private final URI uri;
  private WebTarget target;

  public ClientInvocationBuilder(Client client, URI uri) {
    invocation = createClientInvocation(client, uri);
    this.uri = uri;
  }

  protected ClientInvocation createClientInvocation(Client client, URI uri) {
    return new ClientInvocation(client, uri, new ClientRequestHeaders());
  }

  protected ClientInvocation createClientInvocation(ClientInvocation invocation) {
    return new ClientInvocation(invocation);
  }


  @Override
  public Invocation build(String method) {
    return build(method, null);
  }

  @Override
  public Invocation build(String method, Entity<?> entity) {

    invocation.setMethod(method);
    invocation.setEntity(entity);
    return createClientInvocation(this.invocation);
  }

  @Override
  public Invocation buildGet() {
    return build(HttpMethod.GET);
  }

  @Override
  public Invocation buildDelete() {
    return build(HttpMethod.DELETE);
  }

  @Override
  public Invocation buildPost(Entity<?> entity) {
    return build(HttpMethod.POST, entity);
  }

  @Override
  public Invocation buildPut(Entity<?> entity) {
    return build(HttpMethod.PUT, entity);
  }

  @Override
  public AsyncInvoker async() {
    return null;
  }

  public ClientRequestHeaders getHeaders() {
    return invocation.headers;
  }

  @Override
  public Builder accept(String... mediaTypes) {
    return accept(Stream.of(mediaTypes).map(MediaType::valueOf).toArray(MediaType[]::new));
  }

  @Override
  public Builder accept(MediaType... mediaTypes) {
    getHeaders().accept(mediaTypes);
    return this;
  }


  @Override
  public Builder acceptLanguage(String... locales) {
    return acceptLanguage(Stream.of(locales).map(Locale::forLanguageTag).toArray(Locale[]::new));
  }

  @Override
  public Builder acceptLanguage(Locale... locales) {
    getHeaders().acceptLanguage(locales);
    return this;
  }


  @Override
  public Builder acceptEncoding(String... encodings) {
    getHeaders().acceptEncoding(encodings);
    return this;
  }

  @Override
  public Builder cookie(Cookie cookie) {
    if (!(Cookie.class.equals(cookie.getClass()))) {
      cookie = new Cookie(cookie.getName(), cookie.getValue(), cookie.getPath(), cookie.getDomain(),
          cookie.getVersion());
    }
    getHeaders().cookie(cookie);
    return this;
  }

  @Override
  public Builder cookie(String name, String value) {
    return cookie(new Cookie(name, value));
  }

  @Override
  public Builder cacheControl(CacheControl cacheControl) {
    getHeaders().cacheControl(cacheControl);
    return this;
  }

  @Override
  public Builder header(String name, Object value) {
    getHeaders().header(name, value);
    return this;
  }

  @Override
  public Builder headers(MultivaluedMap<String, Object> headers) {
    getHeaders().setHeaders(headers);
    return this;
  }

  @Override
  public Builder property(String name, Object value) {
    invocation.property(name, value);
    return this;
  }

  @Override
  public Response get() {
    return buildGet().invoke();
  }

  @Override
  public <T> T get(Class<T> responseType) {
    return buildGet().invoke(responseType);
  }

  @Override
  public <T> T get(GenericType<T> responseType) {
    return buildGet().invoke(responseType);
  }

  @Override
  public Response put(Entity<?> entity) {
    return buildPut(entity).invoke();
  }

  @Override
  public <T> T put(Entity<?> entity, Class<T> responseType) {
    return buildPut(entity).invoke(responseType);
  }

  @Override
  public <T> T put(Entity<?> entity, GenericType<T> responseType) {
    return buildPut(entity).invoke(responseType);
  }

  @Override
  public Response post(Entity<?> entity) {
    return buildPost(entity).invoke();
  }

  @Override
  public <T> T post(Entity<?> entity, Class<T> responseType) {
    return buildPost(entity).invoke(responseType);
  }

  @Override
  public <T> T post(Entity<?> entity, GenericType<T> responseType) {
    return buildPost(entity).invoke(responseType);
  }

  @Override
  public Response delete() {
    return buildDelete().invoke();
  }

  @Override
  public <T> T delete(Class<T> responseType) {
    return buildDelete().invoke(responseType);
  }

  @Override
  public <T> T delete(GenericType<T> responseType) {
    return buildDelete().invoke(responseType);
  }

  @Override
  public Response head() {
    return build(HttpMethod.HEAD).invoke();
  }

  @Override
  public Response options() {
    return build(HttpMethod.OPTIONS).invoke();
  }

  @Override
  public <T> T options(Class<T> responseType) {
    return build(HttpMethod.OPTIONS).invoke(responseType);
  }

  @Override
  public <T> T options(GenericType<T> responseType) {
    return build(HttpMethod.OPTIONS).invoke(responseType);
  }

  @Override
  public Response trace() {
    return build("TRACE").invoke();
  }

  @Override
  public <T> T trace(Class<T> responseType) {
    return build("TRACE").invoke(responseType);
  }

  @Override
  public <T> T trace(GenericType<T> responseType) {
    return build("TRACE").invoke(responseType);
  }

  @Override
  public Response method(String name) {
    return build(name).invoke();
  }

  @Override
  public <T> T method(String name, Class<T> responseType) {
    return build(name).invoke(responseType);
  }

  @Override
  public <T> T method(String name, GenericType<T> responseType) {
    return build(name).invoke(responseType);
  }

  @Override
  public Response method(String name, Entity<?> entity) {
    return build(name, entity).invoke();
  }

  @Override
  public <T> T method(String name, Entity<?> entity, Class<T> responseType) {
    return build(name, entity).invoke(responseType);
  }

  @Override
  public <T> T method(String name, Entity<?> entity, GenericType<T> responseType) {
    return build(name, entity).invoke(responseType);
  }


  public void setTarget(WebTarget target) {
    this.target = target;
  }
}
