package slydm.geektimes.training.projects.user.orm.jpa;

import javax.persistence.EntityManager;

/**
 * 线程与 {@link ThreadLocal} 绑定
 *
 * @author wangcymy@gmail.com(wangcong) 2021/3/17 23:27
 */
public class EntityManagerHolder {

  private static ThreadLocal<EntityManager> threadBindEntityManagers = new ThreadLocal();

  public static void put(EntityManager entityManager) {
    threadBindEntityManagers.set(entityManager);
  }

  public static EntityManager get() {
    return threadBindEntityManagers.get();
  }
  
  public static void clear() {
    threadBindEntityManagers.remove();
  }

}
