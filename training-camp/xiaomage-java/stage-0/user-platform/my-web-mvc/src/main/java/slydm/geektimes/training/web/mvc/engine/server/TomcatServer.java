package slydm.geektimes.training.web.mvc.engine.server;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.Wrapper;
import org.apache.catalina.servlets.DefaultServlet;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;
import slydm.geektimes.training.web.mvc.servlet.MyDispatcherServlet;

/**
 * 创建 Tomcat容器实例 的服务
 *
 * @author wangcymy@gmail.com(wangcong) 2021/3/5 14:35
 */
public class TomcatServer implements Server {

    private static final Logger logger = Logger.getLogger(TomcatServer.class.getName());

    private static final String DEFAULT_HOST = "localhost";
    private static final int DEFAULT_PORT = 8080;
    private static final String DEFAULT_CONTEXT_PATH = "/";
    private static final String DOC_BASE = ".";
    private static final String WEB_APP_MOUNT = "/WEB-INF/classes";
    private static final String ADDITION_WEB_INF_CLASSES = "target/classes";
    private static final String INTERNAL_PATH = "/";
    private static final String WEB_APP_LOCATION = "src/main/webapp/";


    @Override
    public void run(String[] args) {
        int port = port(args);
        Tomcat tomcat = tomcat(port);

        try {
            tomcat.start();
        } catch (LifecycleException exception) {
            logger.log(Level.SEVERE, "start tomcat error", exception);
            System.exit(1);
        }

        logger.info("Application started with URL " + DEFAULT_HOST + ":" + port + DEFAULT_CONTEXT_PATH + ".");
        tomcat.getServer().await();
    }

    /**
     * 创建 Tomcat 容器实例
     *
     * @param port 端口
     * @return 容器实例
     */
    private Tomcat tomcat(int port) {
        Tomcat tomcat = new Tomcat();
        tomcat.setHostname(DEFAULT_HOST);
        tomcat.getHost().setAppBase(DOC_BASE);
        tomcat.setPort(port);
        tomcat.getConnector();
        context(tomcat);
        return tomcat;
    }

    /**
     * 配置 Tomcat
     *
     * @param tomcat tomcat 容器实例
     */
    private Context context(Tomcat tomcat) {

        Context context = tomcat.addWebapp(DEFAULT_CONTEXT_PATH, new File(WEB_APP_LOCATION).getAbsolutePath());
        logger.info("configuring app with basedir: " + new File("./" + WEB_APP_LOCATION).getAbsolutePath());
        File classes = new File(ADDITION_WEB_INF_CLASSES);
        String base = classes.getAbsolutePath();
        WebResourceRoot resources = new StandardRoot(context);
        resources.addPreResources(new DirResourceSet(resources, WEB_APP_MOUNT, base, INTERNAL_PATH));
        context.setResources(resources);

        configDefault(context);

        return context;
    }


    /**
     * 配置默认 Servlet Filter Listener
     *
     * @param context
     */
    private void configDefault(Context context) {

//        Class defaultServletClass = DefaultServlet.class;
//        Wrapper defaultServlet = context.createWrapper();
//        defaultServlet.setName("default");
//        defaultServlet.setServletClass(defaultServletClass.getName());
//        defaultServlet.addInitParameter("debug", "0");
//        defaultServlet.addInitParameter("listings", "false");
//        defaultServlet.setLoadOnStartup(1);
//        context.addChild(defaultServlet);
//        context.addServletMappingDecoded("*.css", "default");
//        context.addServletMappingDecoded("*.js", "default");

//        Class myDispatcherServletClass = MyDispatcherServlet.class;
//        Wrapper dispatcherServlet = context.createWrapper();
//        dispatcherServlet.setName(myDispatcherServletClass.getSimpleName());
//        dispatcherServlet.setServletClass(myDispatcherServletClass.getName());
//        dispatcherServlet.setLoadOnStartup(1);
//        context.addChild(dispatcherServlet);
//        context.addServletMappingDecoded("/", myDispatcherServletClass.getSimpleName());

//        Class encodingFilterClass = DefaultCharsetEncodingFilter.class;
//        FilterDef myFilterDef = new FilterDef();
//        myFilterDef.setFilterClass(encodingFilterClass.getName());
//        myFilterDef.setFilterName(encodingFilterClass.getSimpleName());
//        myFilterDef.addInitParameter("encoding", "UTF-8");
//        context.addFilterDef(myFilterDef);
//
//        FilterMap myFilterMap = new FilterMap();
//        myFilterMap.setFilterName(encodingFilterClass.getSimpleName());
//        myFilterMap.addURLPattern("/*");
//        myFilterMap.setDispatcher(DispatcherType.REQUEST.name());
//        myFilterMap.setDispatcher(DispatcherType.FORWARD.name());
//        myFilterMap.setDispatcher(DispatcherType.INCLUDE.name());
//        myFilterMap.setDispatcher(DispatcherType.ERROR.name());
//        context.addFilterMap(myFilterMap);

        /*<welcome-file-list>
            <welcome-file>/</welcome-file>
            <welcome-file>/index</welcome-file>
            <welcome-file>/index.jsp</welcome-file>
        </welcome-file-list>*/
        context.addWelcomeFile("/");
        context.addWelcomeFile("/index");
        context.addWelcomeFile("/index.jsp");

    }

    /**
     * 解析端口
     *
     * @param args 参数列表
     * @return 端口
     */
    private int port(String[] args) {
        if (args.length > 0) {
            String port = args[0];
            try {
                return Integer.valueOf(port);
            } catch (NumberFormatException exception) {
                logger.log(Level.SEVERE, "Invalid port number argument " + port, exception);
            }
        }
        return DEFAULT_PORT;
    }

}
