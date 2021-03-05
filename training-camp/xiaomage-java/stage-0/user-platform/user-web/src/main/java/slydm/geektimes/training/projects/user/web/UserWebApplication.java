package slydm.geektimes.training.projects.user.web;

import java.io.IOException;

/**
 * @author wangcymy@gmail.com(wangcong) 3/5/21 12:10 AM
 */
public class UserWebApplication {

  public static void main(String[] args) throws IOException {

    Server server = new TomcatServer();
    server.run(args);
  }


}
