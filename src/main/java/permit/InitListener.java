/**
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
package permit;

import ognl.OgnlRuntime;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionListener;
import javax.servlet.http.HttpSessionEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
// not used
// it did not help
//
public class InitListener implements ServletContextListener, HttpSessionListener{

    public InitListener()  {
				System.out.println("Listener initialized");		
    }

    public void contextInitialized(ServletContextEvent sce)  {
				System.out.println("context started");				
        OgnlRuntime.setSecurityManager(null);
    }
		@Override
		public void contextDestroyed(ServletContextEvent arg0) {
				System.out.println("Listener destroyed");
		}

		@Override
		public void sessionDestroyed(HttpSessionEvent event){

		}
		@Override
		public void sessionCreated(HttpSessionEvent event){

		}

}

