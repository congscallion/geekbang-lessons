package slydm.geektimes.training.microprofile.rest.client;

import java.lang.reflect.Method;
import java.net.URI;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriBuilderException;

/**
 * @author wangcymy@gmail.com(wangcong) 2021/4/7 17:08
 */
public class MyUriBuilderImpl extends UriBuilder {

  public static final Pattern opaqueUri = Pattern.compile("^([^:/?#{]+):([^/].*)");
  public static final Pattern hierarchicalUri = Pattern
      .compile("^(([^:/?#{]+):)?(//([^/?#]*))?([^?#]*)(\\?([^#]*))?(#(.*))?");

  private static final Pattern hostPortPattern = Pattern.compile("([^/:]+):(\\d+)");
  private static final Pattern squareHostBrackets = Pattern
      .compile("(\\[(([0-9A-Fa-f]{0,4}:){2,7})([0-9A-Fa-f]{0,4})%?.*\\]):(\\d+)");

  private String host;
  private String scheme;
  private int port = -1;

  private String userInfo;
  private String path;
  private String query;
  private String fragment;
  private String ssp;
  private String authority;


  @Override
  public UriBuilder clone() {
    return null;
  }

  @Override
  public UriBuilder uri(URI uri) {
    return null;
  }

  @Override
  public UriBuilder uri(String uriTemplate) {
    return uriTemplate(uriTemplate);
  }

  public UriBuilder uriTemplate(CharSequence uriTemplate) {
    if (uriTemplate == null) {
      throw new IllegalArgumentException("uriTemplate parameter is null");
    }
    Matcher opaque = opaqueUri.matcher(uriTemplate);
    if (opaque.matches()) {
      this.authority = null;
      this.host = null;
      this.port = -1;
      this.userInfo = null;
      this.query = null;
      this.scheme = opaque.group(1);
      this.ssp = opaque.group(2);
      return this;
    } else {
      Matcher match = hierarchicalUri.matcher(uriTemplate);
      if (match.matches()) {
        ssp = null;
        return parseHierarchicalUri(uriTemplate, match);
      }
    }
    throw new IllegalArgumentException("Illegal uri template: " + uriTemplate);
  }

  protected UriBuilder parseHierarchicalUri(CharSequence uriTemplate, Matcher match) {
    boolean scheme = match.group(2) != null;
    if (scheme) {
      this.scheme = match.group(2);
    }
    String authority = match.group(4);
    if (authority != null) {
      this.authority = null;
      String host = match.group(4);
      int at = host.indexOf('@');
      if (at > -1) {
        String user = host.substring(0, at);
        host = host.substring(at + 1);
        this.userInfo = user;
      }

      Matcher hostPortMatch = hostPortPattern.matcher(host);
      if (hostPortMatch.matches()) {
        this.host = hostPortMatch.group(1);
        try {
          this.port = Integer.parseInt(hostPortMatch.group(2));
        } catch (NumberFormatException e) {
          throw new IllegalArgumentException("Illegal uri template:" + uriTemplate, e);
        }
      } else {
        if (host.startsWith("[")) {
          // Must support an IPv6 hostname of format "[::1]" or [0:0:0:0:0:0:0:0]
          // and IPv6 link-local format [fe80::1234%1] [ff08::9abc%interface10]
          Matcher bracketsMatch = squareHostBrackets.matcher(host);
          if (bracketsMatch.matches()) {
            host = bracketsMatch.group(1);
            try {
              this.port = Integer.parseInt(bracketsMatch.group(5));
            } catch (NumberFormatException e) {
              throw new IllegalArgumentException("Illegal uri template:" + uriTemplate, e);
            }
          }
        }
        this.host = host;
      }
    }
    if (match.group(5) != null) {
      String group = match.group(5);
      if (!scheme && !"".equals(group) && !group.startsWith("/") && group.indexOf(':') > -1 &&
          group.indexOf('/') > -1 && group.indexOf(':') < group.indexOf('/')) {
        throw new IllegalArgumentException("Illegal uri template:" + uriTemplate);
      }
      if (!"".equals(group)) {
        replacePath(group);
      }
    }
    if (match.group(7) != null) {
      replaceQuery(match.group(7));
    }
    if (match.group(9) != null) {
      fragment(match.group(9));
    }
    return this;
  }


  @Override
  public UriBuilder scheme(String scheme) {
    return null;
  }

  @Override
  public UriBuilder schemeSpecificPart(String ssp) {
    return null;
  }

  @Override
  public UriBuilder userInfo(String ui) {
    return null;
  }

  @Override
  public UriBuilder host(String host) {
    return null;
  }

  @Override
  public UriBuilder port(int port) {
    return null;
  }

  @Override
  public UriBuilder replacePath(String path) {
    return null;
  }

  @Override
  public UriBuilder path(String path) {
    return null;
  }

  @Override
  public UriBuilder path(Class resource) {
    return null;
  }

  @Override
  public UriBuilder path(Class resource, String method) {
    return null;
  }

  @Override
  public UriBuilder path(Method method) {
    return null;
  }

  @Override
  public UriBuilder segment(String... segments) {
    return null;
  }

  @Override
  public UriBuilder replaceMatrix(String matrix) {
    return null;
  }

  @Override
  public UriBuilder matrixParam(String name, Object... values) {
    return null;
  }

  @Override
  public UriBuilder replaceMatrixParam(String name, Object... values) {
    return null;
  }

  @Override
  public UriBuilder replaceQuery(String query) {
    return null;
  }

  @Override
  public UriBuilder queryParam(String name, Object... values) {
    return null;
  }

  @Override
  public UriBuilder replaceQueryParam(String name, Object... values) {
    return null;
  }

  @Override
  public UriBuilder fragment(String fragment) {
    return null;
  }

  @Override
  public UriBuilder resolveTemplate(String name, Object value) {
    return null;
  }

  @Override
  public UriBuilder resolveTemplate(String name, Object value, boolean encodeSlashInPath) {
    return null;
  }

  @Override
  public UriBuilder resolveTemplateFromEncoded(String name, Object value) {
    return null;
  }

  @Override
  public UriBuilder resolveTemplates(Map<String, Object> templateValues) {
    return null;
  }

  @Override
  public UriBuilder resolveTemplates(Map<String, Object> templateValues, boolean encodeSlashInPath)
      throws IllegalArgumentException {
    return null;
  }

  @Override
  public UriBuilder resolveTemplatesFromEncoded(Map<String, Object> templateValues) {
    return null;
  }

  @Override
  public URI buildFromMap(Map<String, ?> values) {
    return null;
  }

  @Override
  public URI buildFromMap(Map<String, ?> values, boolean encodeSlashInPath)
      throws IllegalArgumentException, UriBuilderException {
    return null;
  }

  @Override
  public URI buildFromEncodedMap(Map<String, ?> values) throws IllegalArgumentException, UriBuilderException {
    return null;
  }

  @Override
  public URI build(Object... values) throws IllegalArgumentException, UriBuilderException {
    return null;
  }

  @Override
  public URI build(Object[] values, boolean encodeSlashInPath) throws IllegalArgumentException, UriBuilderException {
    return null;
  }

  @Override
  public URI buildFromEncoded(Object... values) throws IllegalArgumentException, UriBuilderException {
    return null;
  }

  @Override
  public String toTemplate() {
    return null;
  }
}
