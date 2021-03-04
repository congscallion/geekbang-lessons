package slydm.geektimes.training.web.mvc.engine;

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.DispatcherType;
import javax.servlet.descriptor.JspConfigDescriptor;
import javax.servlet.descriptor.JspPropertyGroupDescriptor;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.descriptor.web.FilterDef;
import org.apache.tomcat.util.descriptor.web.FilterMap;
import org.apache.tomcat.util.descriptor.web.JspConfigDescriptorImpl;
import org.apache.tomcat.util.descriptor.web.JspPropertyGroup;
import org.apache.tomcat.util.descriptor.web.JspPropertyGroupDescriptorImpl;
import org.apache.tomcat.util.descriptor.web.ServletDef;
import slydm.geektimes.training.web.mvc.MyDispatcherServlet;
import slydm.geektimes.training.web.mvc.filter.DefaultCharsetEncodingFilter;

/**
 * My Web Mvc 启动器
 *
 * @author wangcymy@gmail.com(wangcong) 3/4/21 11:47 PM
 */
public class ApplicationContext {

  private Logger logger = Logger.getLogger(ApplicationContext.class.getName());

  public void start() {

    Tomcat tomcat = new Tomcat();
    tomcat.setPort(8080);
    tomcat.setHostname("localhost");
    tomcat.getHost().setAppBase(".");
    File docBase = new File(System.getProperty("java.io.tmpdir"));
    Context context = tomcat.addContext("", docBase.getAbsolutePath());

    Class servletClass = MyDispatcherServlet.class;
    ServletDef servletDef = new ServletDef();
    servletDef.setServletClass(servletClass.getName());
    servletDef.setServletName(servletClass.getSimpleName());
    servletDef.setLoadOnStartup("1");
    Tomcat.addServlet(
        context, servletClass.getSimpleName(), servletClass.getName())
        .setLoadOnStartup(1);
    context.addServletMappingDecoded("/*", servletClass.getSimpleName());

    Class filterClass = DefaultCharsetEncodingFilter.class;
    FilterDef myFilterDef = new FilterDef();
    myFilterDef.setFilterClass(filterClass.getName());
    myFilterDef.setFilterName(filterClass.getSimpleName());
    myFilterDef.addInitParameter("encoding", "UTF-8");
    context.addFilterDef(myFilterDef);

    FilterMap myFilterMap = new FilterMap();
    myFilterMap.setFilterName(filterClass.getSimpleName());
    myFilterMap.addURLPattern("/*");
    myFilterMap.setDispatcher(DispatcherType.REQUEST.name());
    myFilterMap.setDispatcher(DispatcherType.FORWARD.name());
    myFilterMap.setDispatcher(DispatcherType.INCLUDE.name());
    myFilterMap.setDispatcher(DispatcherType.ERROR.name());
    context.addFilterMap(myFilterMap);

    Collection<JspPropertyGroupDescriptor> jspPropertyGroups = new LinkedHashSet<>();

    /*<jsp-property-group>
        <url-pattern>*.jsp</url-pattern>
        <page-encoding>UTF-8</page-encoding>
        <include-prelude>/WEB-INF/jsp/prelude/header.jspf</include-prelude>
        <include-coda>/WEB-INF/jsp/coda/footer.jspf</include-coda>
        <trim-directive-whitespaces>true</trim-directive-whitespaces>
    </jsp-property-group>*/
    JspPropertyGroup jspPropertyGroup = new JspPropertyGroup();
    jspPropertyGroup.addUrlPattern("*.jsp");
    jspPropertyGroup.setPageEncoding("UTF-8");
    jspPropertyGroup.addIncludePrelude("/WEB-INF/jsp/prelude/header.jspf");
    jspPropertyGroup.addIncludeCoda("/WEB-INF/jsp/coda/footer.jspf");
    jspPropertyGroup.setTrimWhitespace("true");
    JspPropertyGroupDescriptor jspPropertyGroupDescriptor = new JspPropertyGroupDescriptorImpl(jspPropertyGroup);
    jspPropertyGroups.add(jspPropertyGroupDescriptor);


    /*<jsp-property-group>
        <url-pattern>*.jspf</url-pattern>
        <page-encoding>UTF-8</page-encoding>
        <include-prelude>/WEB-INF/jsp/prelude/include-taglibs.jspf</include-prelude>
        <include-prelude>/WEB-INF/jsp/prelude/variables.jspf</include-prelude>
        <trim-directive-whitespaces>true</trim-directive-whitespaces>
    </jsp-property-group>*/
    JspPropertyGroup jspPropertyGroup2 = new JspPropertyGroup();
    jspPropertyGroup2.addUrlPattern("*.jspf");
    jspPropertyGroup2.setPageEncoding("UTF-8");
    jspPropertyGroup2.addIncludePrelude("/WEB-INF/jsp/prelude/include-taglibs.jspf");
    jspPropertyGroup2.addIncludeCoda("/WEB-INF/jsp/prelude/variables.jspf");
    jspPropertyGroup2.setTrimWhitespace("true");
    JspPropertyGroupDescriptor jspPropertyGroupDescriptor2 = new JspPropertyGroupDescriptorImpl(jspPropertyGroup2);
    jspPropertyGroups.add(jspPropertyGroupDescriptor2);
    JspConfigDescriptor jspConfigDescriptor = new JspConfigDescriptorImpl(jspPropertyGroups, Collections.EMPTY_LIST);
    context.setJspConfigDescriptor(jspConfigDescriptor);

    /*<welcome-file-list>
        <welcome-file>/</welcome-file>
        <welcome-file>/index</welcome-file>
        <welcome-file>/index.jsp</welcome-file>
    </welcome-file-list>*/
    context.addWelcomeFile("/");
    context.addWelcomeFile("/index");
    context.addWelcomeFile("/index.jsp");

    // 启动 tomcat
    try {
      tomcat.start();
      tomcat.getServer().await();
    } catch (LifecycleException e) {
      logger.log(Level.ALL, "tomcat 容器启动失败", e);
    }
  }


}
