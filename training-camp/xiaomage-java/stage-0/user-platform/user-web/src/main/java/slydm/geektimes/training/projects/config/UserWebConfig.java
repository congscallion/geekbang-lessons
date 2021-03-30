package slydm.geektimes.training.projects.config;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.Properties;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.sql.DataSource;
import org.apache.derby.jdbc.EmbeddedDataSource;
import slydm.geektimes.training.beans.factory.BeanFactoryAware;
import slydm.geektimes.training.context.annotation.Bean;
import slydm.geektimes.training.context.annotation.ComponentScan;
import slydm.geektimes.training.context.annotation.Configuration;
import slydm.geektimes.training.exception.BeansException;
import slydm.geektimes.training.ioc.BeanFactory;

/**
 * @author wangcymy@gmail.com(wangcong) 2021/3/29 18:50
 */
@Configuration
@ComponentScan(basePackage = "slydm.geektimes.training")
public class UserWebConfig implements BeanFactoryAware {

  private BeanFactory beanFactory;


  @Bean
  public DataSource dataSource() {
    EmbeddedDataSource ds = new EmbeddedDataSource();
    ds.setDatabaseName("/db/user-platform");
    return ds;
  }

  @Bean
  public EntityManagerFactory entityManagerFactory() {

    Map map = loadProperties("META-INF/jpa-datasource.properties");
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("emf", map);
    return emf;
  }

  private Map loadProperties(String propertiesLocation) {
    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    URL propertiesFileURL = classLoader.getResource(propertiesLocation);
    Properties properties = new Properties();
    try {
      properties.load(propertiesFileURL.openStream());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    for (String propertyName : properties.stringPropertyNames()) {
      String propertyValue = properties.getProperty(propertyName);
      if (propertyValue.startsWith("@")) {
        String componentName = propertyValue.substring(1);
        Object bean = beanFactory.getBean(componentName);
        properties.put(propertyName, bean);
      }
    }

    return properties;
  }

  @Override
  public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
    this.beanFactory = beanFactory;
  }
}
