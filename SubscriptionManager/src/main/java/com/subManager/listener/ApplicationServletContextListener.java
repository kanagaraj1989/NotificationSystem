package com.subManager.listener;

import java.io.FileInputStream;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.subManager.constant.AppConstant;
import com.subManager.servlet.BaseServlet;



/**
 * Application Lifecycle Listener implementation class ApplicationServletContextListener
 *
 */
@WebListener
public class ApplicationServletContextListener implements ServletContextListener {

	private static Logger log = Logger.getLogger(BaseServlet.class); 
	
    /**
     * Default constructor. 
     */
    public ApplicationServletContextListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent event) {
    	   ServletContext ctx = event.getServletContext();
           
           String prefix = ctx.getRealPath("/");
   		String file = prefix + "WEB-INF" + System.getProperty("file.separator")
   				+ "classes" + System.getProperty("file.separator")
   				+ "log4j.properties";
   		
   		if (file != null) 
   		{
   			PropertyConfigurator.configure(file);
   			System.out.println("Log4J Logging started for application: " + file);

   		} 
   		
   		log.debug("Application initialized with logger");

   		String configFile = prefix + "WEB-INF" + System.getProperty("file.separator")
   				+ "classes" + System.getProperty("file.separator")
   				+ "config.properties";
   		
   			
   		if (configFile != null) 
   		{
   			PropertyConfigurator.configure(configFile);
   			System.out.println("Log4J Logging started for application: " + configFile);
   			Properties prop = new Properties();
   			try 
   			{
   				prop.load(new FileInputStream(configFile));
   				
   				//AppConstant.URL = prop.getProperty("serviceUrl");
   				AppConstant.subMngDbNmSpace = prop.getProperty("jndiSubScriptionConfNmspace");
   				log.debug("JNDI Namespace="+AppConstant.subMngDbNmSpace);
   			}catch(Exception ex)
   			{
   				log.error("Exception occured"+ex.getStackTrace());
   			}
   		}
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0) {
        // TODO Auto-generated method stub
    }
	
}
