package slydm.geektimes.training.microprofile.rest.client;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

/**
 * @author wangcymy@gmail.com(wangcong) 2021/4/7 19:37
 */
public class ClientRequestHeaders {

  private MultivaluedMap<String, Object> headers = new MultivaluedHashMap<>();
  private Set<MediaType> mediaTypes = new LinkedHashSet<>();
  private Set<Locale> locales = new LinkedHashSet<>();
  private Set<String> encodings = new LinkedHashSet<>();
  private Set<Cookie> cookies = new LinkedHashSet<>();
  private CacheControl cacheControl;

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

  private String toHeaderString(Object val) {
    return val.toString();
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

}