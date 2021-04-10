package slydm.geektimes.training.microprofile.rest.client;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.RuntimeDelegate;
import javax.ws.rs.ext.RuntimeDelegate.HeaderDelegate;

/**
 * @author wangcymy@gmail.com(wangcong) 2021/4/7 19:37
 */
public class ClientRequestHeaders {

  protected MultivaluedMap<String, Object> headers = new MultivaluedHashMap<>();

  public void accept(MediaType... mediaTypes) {

    String accept = (String) headers.getFirst(HttpHeaders.ACCEPT);
    StringBuilder builder = buildAcceptString(accept, mediaTypes);
    headers.putSingle(HttpHeaders.ACCEPT, builder.toString());
  }

  public void setMediaType(MediaType mediaType) {
    if (mediaType == null) {
      headers.remove(HttpHeaders.CONTENT_TYPE);
      return;
    }
    headers.putSingle(HttpHeaders.CONTENT_TYPE, mediaType);
  }

  public void acceptLanguage(Locale... locales) {

    String accept = (String) headers.getFirst(HttpHeaders.ACCEPT_LANGUAGE);
    StringBuilder builder = buildAcceptString(accept, locales);
    headers.putSingle(HttpHeaders.ACCEPT_LANGUAGE, builder.toString());
  }

  public void setLanguage(Locale language) {
    if (this.getHeader(HttpHeaders.CONTENT_LANGUAGE) != null) {
      return;
    }
    if (language == null) {
      headers.remove(HttpHeaders.CONTENT_LANGUAGE);
      return;
    }
    headers.putSingle(HttpHeaders.CONTENT_LANGUAGE, language);
  }

  public String getHeader(String name) {
    List vals = headers.get(name);
    if (vals == null) {
      return null;
    }
    StringBuilder builder = new StringBuilder();
    boolean first = true;
    for (Object val : vals) {
      if (first) {
        first = false;
      } else {
        builder.append(",");
      }
      builder.append(toHeaderString(val));
    }
    return builder.toString();
  }

  public void acceptEncoding(String... encodings) {

    String accept = (String) headers.getFirst(HttpHeaders.ACCEPT_ENCODING);
    StringBuilder builder = buildAcceptString(accept, encodings);
    headers.putSingle(HttpHeaders.ACCEPT_ENCODING, builder.toString());
  }

  public void cookie(Cookie cookie) {
    if (!(Cookie.class.equals(cookie.getClass()))) {
      cookie = new Cookie(cookie.getName(), cookie.getValue(), cookie.getPath(), cookie.getDomain(),
          cookie.getVersion());
    }
    headers.add(HttpHeaders.COOKIE, cookie);
  }


  private StringBuilder buildAcceptString(String accept, Object[] items) {
    StringBuilder builder = new StringBuilder();
    if (accept != null) {
      builder.append(accept).append(", ");
    }

    boolean isFirst = true;
    for (Object l : items) {
      if (isFirst) {
        isFirst = false;
      } else {
        builder.append(", ");
      }
      builder.append(toHeaderString(l));
    }
    return builder;
  }

  private String toHeaderString(Object obj) {
    if (obj instanceof String) {
      return (String) obj;
    }

    HeaderDelegate delegate = RuntimeDelegate.getInstance().createHeaderDelegate(obj.getClass());
    if (delegate != null) {
      return delegate.toString(obj);
    } else {
      return obj.toString();
    }
  }

  public void cacheControl(CacheControl cacheControl) {
    headers.putSingle(HttpHeaders.CACHE_CONTROL, cacheControl);
  }

  public void header(String name, Object value) {
    if (value == null) {
      headers.remove(name);
      return;
    }
    if (name.equalsIgnoreCase(HttpHeaders.ACCEPT)) {
      accept(MediaType.valueOf(toHeaderString(value)));
    } else if (name.equalsIgnoreCase(HttpHeaders.ACCEPT_ENCODING)) {
      acceptEncoding(toHeaderString(value));
    } else if (name.equalsIgnoreCase(HttpHeaders.ACCEPT_LANGUAGE)) {
      acceptLanguage(Locale.forLanguageTag(toHeaderString(value)));
    } else {
      headers.add(name, value);
    }
  }

  public void setHeaders(MultivaluedMap<String, Object> newHeaders) {
    headers.clear();
    if (newHeaders == null) {
      return;
    }
    headers.putAll(newHeaders);
  }


  public MultivaluedMap<String, String> asMap() {

    MultivaluedMap<String, String> map = new MultivaluedHashMap<>();
    for (Map.Entry<String, List<Object>> entry : headers.entrySet()) {
      for (Object obj : entry.getValue()) {
        map.add(entry.getKey(), toHeaderString(obj));
      }
    }
    return map;
  }
}
