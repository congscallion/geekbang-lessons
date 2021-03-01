package com.slydm.thinking.in.spring.aop.overview.cglib;

import com.slydm.thinking.in.spring.aop.overview.cglib.helper.URLClassPath;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Paths;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import sun.misc.Resource;

/**
 * 自定义类加载示例
 *
 * @author wangcymy@gmail.com(wangcong) 2021/2/10 11:28
 */
public class CustomClassLoaderDemo {

  public static void main(String[] args) {

    try {
      MyClassLoader classLoader = new MyClassLoader();
      Class<?> aClass = classLoader.findClass(
          "com.slydm.thinking.in.spring.aop.overview.cglib.TargetFilterDemo");
      System.out.println(aClass.getSimpleName());

      // 同一个类加载器使用两种方式加载同一个类, jvm抛出异常并退出
      // java.lang.LinkageError: loader (instance of  com/slydm/thinking/in/spring/aop/overview/cglib/TargetFilterDemo$MyClassLoader): attempted  duplicate class definition for name: "com/slydm/thinking/in/spring/aop/overview/cglib/TargetFilterDemo"
//      Class<?> aClass2 = classLoader
//          .findClass2("com.slydm.thinking.in.spring.aop.overview.cglib.TargetFilterDemo");
//      System.out.println(aClass2.getSimpleName());

      MyClassLoader classLoader2 = new MyClassLoader();
      Class<?> bClass = classLoader2
          .findClass2("com.slydm.thinking.in.spring.aop.overview.cglib.TargetFilterDemo");
      System.out.println(bClass.getSimpleName());

      Class<?> sClass = classLoader.loadClass("java.lang.String");
      System.out.println(sClass.getSimpleName());
      System.out.println(sClass.getClassLoader());
      System.out.println(String.class.getClassLoader());

      // 使用 JarFile api 从 rt.jar 文件中读取 String.class 文件
      byte[] bytes = loadClassByteFromJar("java.lang.String");
      System.out.println(bytes.length);

      // 使用 URLClassPath api 从 rt.jar 文件中读取 String.class 文件
      Resource resource = classLoader.loadClassFromFile2("java.lang.String");
      System.out.println(resource.getBytes().length == bytes.length);

      // 加载rt.jar中的类
      // java.lang.SecurityException: Prohibited package name: java.lang
      Class<?> tmp = classLoader.findClass2("java.lang.String");
      System.out.println(tmp.getSimpleName());

      // 使用 URLClassLoader 加载 rt.jar 中的类
      // java.lang.SecurityException: Prohibited package name: java.lang
      String java_home = System.getenv("java_home");
      String jre_path = java_home + "/jre/lib";
      String rt_path = jre_path + "/rt.jar";
      URL url = Paths.get(rt_path).toUri().toURL();
      MyURLClassLoader urlClassLoader = new MyURLClassLoader(new URL[]{url});
      Class<?> temp = urlClassLoader.findClass("java.lang.String");
      System.out.println(temp.getSimpleName());

    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  /**
   * 从 Jar 文件中读取文件内容,比如一个类
   *
   * @param fileName 文件名,比如 java.lang.String
   * @return 文件内容字节数组
   */
  private static byte[] loadClassByteFromJar(String fileName) {
    try {
      JarFile jarFile = new JarFile(System.getenv("java_home") + "/jre/lib/rt.jar");
      String name = fileName.replace('.', '/').concat(".class");
      ZipEntry entry = jarFile.getEntry(name);
      InputStream is = jarFile.getInputStream(entry);
      ByteArrayOutputStream bis = new ByteArrayOutputStream();
      byte[] buf = new byte[1024];
      int len;
      while ((len = is.read(buf)) != -1) {
        bis.write(buf, 0, len);
      }
      is.close();
      bis.close();
      return bis.toByteArray();
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }



  static class MyClassLoader extends ClassLoader {

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {

      byte[] b = loadClassFromFile(name);
      return defineClass(name, b, 0, b.length);
    }

    protected Class<?> findClass2(String name) throws Exception {

      Resource res = loadClassFromFile2(name);
      byte[] b = res.getBytes();
      return defineClass(name, b, 0, b.length);
    }

    private Resource loadClassFromFile2(String fileName) {
      URL[] urls;
      try {
        URL url = new URL("file://C:/workspace/thinking-in-spring/aop-overview/target/classes/");
        URL url2 = new URL("file:/C:/Install/Java/jdk1.8.0_201/jre/lib/rt.jar");
        urls = new URL[]{url, url2};
      } catch (MalformedURLException e) {
        e.printStackTrace();
        urls = new URL[]{};
      }
      URLClassPath ucp = new URLClassPath(urls, null);
      String path = fileName.replace('.', '/').concat(".class");
      Resource res = ucp.getResource(path, false);
      return res;
    }

    private byte[] loadClassFromFile(String fileName) {
      InputStream inputStream = getClass().getClassLoader().getResourceAsStream(
          fileName.replace('.', File.separatorChar) + ".class");

      byte[] buffer;
      ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
      int nextValue = 0;
      try {
        while ((nextValue = inputStream.read()) != -1) {
          byteStream.write(nextValue);
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
      buffer = byteStream.toByteArray();
      return buffer;
    }

  }

  static class MyURLClassLoader extends URLClassLoader {

    public MyURLClassLoader(URL[] urls) {
      super(urls);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
      return super.findClass(name);
    }
  }


}
