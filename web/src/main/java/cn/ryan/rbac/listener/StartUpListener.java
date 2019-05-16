package cn.ryan.rbac.listener;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 项目启动监听器
 *
 * @author ryan
 * @create 2019-04-18 17:21
 **/
@Slf4j
public class StartUpListener implements ServletContextListener {
    public static final String APP_PATH = "APP_PATH";
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        String contextPath = servletContext.getContextPath();
        servletContext.setAttribute(APP_PATH,contextPath);
        log.info(contextPath);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
