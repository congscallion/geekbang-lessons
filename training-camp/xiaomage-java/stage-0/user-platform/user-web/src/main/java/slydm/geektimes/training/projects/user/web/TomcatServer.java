//package slydm.geektimes.training.projects.user.web;
//
//import java.io.File;
//import java.util.logging.Logger;
//import org.apache.catalina.LifecycleException;
//import org.apache.catalina.WebResourceRoot;
//import org.apache.catalina.core.StandardContext;
//import org.apache.catalina.startup.Tomcat;
//import org.apache.catalina.webresources.DirResourceSet;
//import org.apache.catalina.webresources.StandardRoot;
//
///**
// * 代表一个Tomcat实例
// *
// * @author wangcymy@gmail.com(wangcong) 2021/3/5 14:35
// */
//public class TomcatServer implements Server {
//
//  private static final Logger logger = Logger.getLogger(TomcatServer.class.getName());
//
//  private static final String DEFAULT_HOST = "localhost";
//  private static final int DEFAULT_PORT = 8080;
//  private static final String DEFAULT_CONTEXT_PATH = "/app";
//  private static final String DOC_BASE = ".";
//  private static final String WEB_APP_MOUNT = "/WEB-INF/classes";
//  private static final String INTERNAL_PATH = "/";
//
//
//  @Override
//  public void run(String[] args) throws LifecycleException {
//    Tomcat tomcat = new Tomcat();
//
//    //The port that we should run on can be set into an environment variable
//    //Look for that variable and default to 8080 if it isn't there.
//    String webPort = System.getenv("PORT");
//    if (webPort == null || webPort.isEmpty()) {
//      webPort = "8080";
//    }
//
//    tomcat.setPort(Integer.valueOf(webPort));
//
//    String absolutePath = "C:\\geekbang-lessons\\training-camp\\xiaomage-java\\stage-0\\user-platform\\user-web\\src\\main\\webapp";
//    absolutePath = ".";
//    StandardContext ctx = (StandardContext) tomcat.addWebapp("/app", new File(".").getAbsolutePath()+"\\"+"user-web");
//    System.out.println("configuring app with basedir: " + absolutePath);
//
//    // Declare an alternative location for your "WEB-INF/classes" dir
//    // Servlet 3.0 annotation will work
//    File classes = new File(this.getClass().getResource("/").getPath());
//    String base = classes.getAbsolutePath();
//    WebResourceRoot resources = new StandardRoot(ctx);
//    resources.addPreResources(new DirResourceSet(resources, "/WEB-INF/classes",
//        base, "/"));
//    ctx.setResources(resources);
//
//    tomcat.start();
//    tomcat.getServer().await();
//
//  }
//
//}
