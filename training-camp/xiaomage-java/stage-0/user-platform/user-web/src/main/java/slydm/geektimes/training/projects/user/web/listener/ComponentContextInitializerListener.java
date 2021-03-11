package slydm.geektimes.training.projects.user.web.listener;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import slydm.geektimes.training.projects.context.ComponentContext;
import slydm.geektimes.training.projects.user.domin.User;


/**
 * @author wangcymy@gmail.com(wangcong) 2021/3/10 21:52
 */
public class ComponentContextInitializerListener implements ServletContextListener {

  private ServletContext servletContext;

  @Override
  public void contextInitialized(ServletContextEvent servletContextEvent) {

    servletContext = servletContextEvent.getServletContext();
    ComponentContext context = new ComponentContext();
    context.init(servletContext);

    testEntityManager(context.getComponent("bean/EntityManager"));


  }

  private void testEntityManager(EntityManager entityManager) {
    User user = new User();
    user.setName("小马哥");
    user.setPassword("******");
    user.setEmail("mercyblitz@gmail.com");
    user.setPhoneNumber("123456789");
    EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();
    entityManager.persist(user);
    transaction.commit();

    System.out.println(entityManager.find(User.class, user.getId()));
  }


  @Override
  public void contextDestroyed(ServletContextEvent servletContextEvent) {

  }
}
