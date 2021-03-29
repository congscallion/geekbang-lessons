package slydm.geektimes.training.projects.user.orm.jpa.db.conf;

import javax.sql.DataSource;
import org.apache.derby.jdbc.EmbeddedDataSource;
import org.apache.derby.jdbc.EmbeddedDriver;
import slydm.geektimes.training.beans.factory.BeanFactoryAware;
import slydm.geektimes.training.context.annotation.Component;
import slydm.geektimes.training.exception.BeansException;
import slydm.geektimes.training.ioc.BeanFactory;

/**
 * @author wangcymy@gmail.com(wangcong) 2021/3/29 20:13
 */
@Component
public class DataSourceConfig implements BeanFactoryAware {

  private BeanFactory beanFactory;

  private DataSource ds;

  public DataSourceConfig(){

    EmbeddedDataSource ds = new EmbeddedDataSource();
    ds.setDescription("Derby database for User Platform");
    ds.setUser("");
    ds.setPassword("");

  }



  @Override
  public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
    this.beanFactory = beanFactory;
  }
}
