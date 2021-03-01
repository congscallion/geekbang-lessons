package com.slydm.thinking.in.spring.resource;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.support.EncodedResource;

/**
 * 带有字符编码的 {@link FileSystemResource} 示例
 *
 * @author wangcymy@gmail.com(wangcong) 2020/12/28 14:06
 */
public class EncodedFileSystemResourceDemo {

  public static void main(String[] args) throws IOException {

    String currentJavaFilePath = System.getProperty("user.dir")
        + "/resource/src/main/java/com/slydm/thinking/in/spring/resource/EncodedFileSystemResourceDemo.java";
    File file = new File(currentJavaFilePath);
    FileSystemResource fileSystemResource = new FileSystemResource(file);
    EncodedResource encodedResource = new EncodedResource(fileSystemResource, "UTF-8");

    try (Reader reader = encodedResource.getReader()) {
      System.out.println(IOUtils.toString(reader));
    }

  }

}
