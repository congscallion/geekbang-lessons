package slydm.geektimes.training.projects.user.web;

import slydm.geektimes.training.web.mvc.engine.server.Server;
import slydm.geektimes.training.web.mvc.engine.server.TomcatServer;

/**
 * @author wangcymy@gmail.com(wangcong) 3/5/21 12:10 AM
 */
public class UserWebApplication {

  public static void main(String[] args) {

    Server server = new TomcatServer();
    server.run(args);
  }


}
