package com.slydm.thinking.in.spring.resource;

import java.io.IOException;
import java.io.Reader;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;

/**
 * 带有字符编码的 {@link FileSystemResourceLoader} 示例
 *
 * @author wangcymy@gmail.com(wangcong) 2020/12/28 14:14
 */
public class EncodedFileSystemResourceLoaderDemo {

  public static void main(String[] args) throws IOException {

    String currentJavaFilePath = System.getProperty("user.dir")
        + "/resource/src/main/java/com/slydm/thinking/in/spring/resource/EncodedFileSystemResourceLoaderDemo.java";

    // or  都可以
    currentJavaFilePath = "/" + System.getProperty("user.dir")
        + "/resource/src/main/java/com/slydm/thinking/in/spring/resource/EncodedFileSystemResourceLoaderDemo.java";

    FileSystemResourceLoader resourceLoader = new FileSystemResourceLoader();
    Resource resource = resourceLoader.getResource(currentJavaFilePath);
    EncodedResource encodedResource = new EncodedResource(resource, "UTF-8");

    try (Reader reader = encodedResource.getReader()) {
      System.out.println(IOUtils.toString(reader));
    }

  }
}
