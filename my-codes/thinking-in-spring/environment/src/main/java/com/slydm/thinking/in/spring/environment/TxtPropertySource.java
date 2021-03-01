package com.slydm.thinking.in.spring.environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.util.StringUtils;

/**
 * 自定义实现一个数据源为 txt 文件 {@link PropertySource} 示例
 *
 * 通过自定义实现，可以帮助理解 PropertySource 是如何设计以及如何工作的。
 *
 * @author wangcymy@gmail.com(wangcong) 2021/2/1 11:14
 */
public class TxtPropertySource extends EnumerablePropertySource<File> {

  private List<String> names = new LinkedList<>();
  private List<String> values = new LinkedList<>();
  private String encoding = "UTF-8";
  private String valueSeparator = ":";


  public TxtPropertySource(String name, File txtFile) {
    super(name, txtFile);

    parseTxtFile(txtFile);
  }


  public TxtPropertySource(String name, File txtFile, String encoding) {
    super(name, txtFile);

    if (txtFile == null) {
      throw new IllegalArgumentException("file can not null");
    }
    this.encoding = encoding;

    parseTxtFile(txtFile);
  }

  public TxtPropertySource(String name, File txtFile, String encoding, String valueSeparator) {
    super(name, txtFile);

    if (txtFile == null) {
      throw new IllegalArgumentException("file can not null");
    }

    String fileName = txtFile.getName();
    if (!StringUtils.endsWithIgnoreCase(fileName, ".txt")) {
      throw new IllegalArgumentException("only support txt file");
    }

    this.encoding = encoding;
    this.valueSeparator = valueSeparator;

    parseTxtFile(txtFile);
  }


  private void parseTxtFile(File txtFile) {

    try (BufferedReader reader = Files
        .newBufferedReader(txtFile.toPath(), Charset.forName(encoding))) {

      String line;
      while ((line = reader.readLine()) != null) {

        String[] split = StringUtils.split(line, valueSeparator);
        names.add(split[0]);
        values.add(split[1]);
      }


    } catch (IOException e) {
      e.printStackTrace();
    }


  }

  @Override
  public String[] getPropertyNames() {
    return this.names.toArray(new String[]{});
  }

  @Override
  public Object getProperty(String name) {
    int idx;
    if ((idx = names.indexOf(name)) != -1) {
      return values.get(idx);
    }
    return null;
  }
}
