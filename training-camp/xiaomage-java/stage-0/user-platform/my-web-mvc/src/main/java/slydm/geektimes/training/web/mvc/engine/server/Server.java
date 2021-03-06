package slydm.geektimes.training.web.mvc.engine.server;

/**
 * 代表一个具体的Web容器或者应用服务器， 即可是Tomcat,也可以是Jetty或者其它,Undertow
 *
 * @author wangcymy@gmail.com(wangcong) 2021/3/5 14:32
 */
public interface Server {

  /**
   * 启动 web容器或者应用服务器
   *
   * @param args 启动参数
   */
  void run(String[] args);

}
