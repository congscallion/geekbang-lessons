package com.slydm.thinking.in.spring.resource;

import com.slydm.thinking.in.spring.resource.util.ResourceUtils;
import java.io.IOException;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Stream;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.PathMatcher;

/**
 * 自定义 {@link ResourcePatternResolver} 示例
 *
 * @author wangcymy@gmail.com(wangcong) 2020/12/28 14:31
 */
public class CustomizedResourcePatternResolverDemo {

  public static void main(String[] args) throws IOException {

    // 读取当前 package 对应的所有的 .java 文件
    // *.java
    String currentPackagePath = System.getProperty("user.dir")
        + "/resource/src/main/java/com/slydm/thinking/in/spring/resource/";

    String locationPattern = currentPackagePath + "*.java";
    PathMatchingResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver(
        new FileSystemResourceLoader());

    resourcePatternResolver.setPathMatcher(new JavaFilePathMatcher());

    Resource[] resources = resourcePatternResolver.getResources(locationPattern);

    Stream.of(resources).map(ResourceUtils::getContent).forEach(System.out::println);

  }


  static class JavaFilePathMatcher implements PathMatcher {


    @Override
    public boolean isPattern(String path) {
      return path.endsWith(".java");
    }

    @Override
    public boolean match(String pattern, String path) {
      return path.endsWith(".java");
    }

    @Override
    public boolean matchStart(String pattern, String path) {
      return false;
    }

    @Override
    public String extractPathWithinPattern(String pattern, String path) {
      return null;
    }

    @Override
    public Map<String, String> extractUriTemplateVariables(String pattern, String path) {
      return null;
    }

    @Override
    public Comparator<String> getPatternComparator(String path) {
      return null;
    }

    @Override
    public String combine(String pattern1, String pattern2) {
      return null;
    }
  }


}
