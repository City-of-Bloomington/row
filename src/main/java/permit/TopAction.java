/**
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
package permit;
import java.util.*;
import java.io.*;
import java.text.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.opensymphony.xwork2.ModelDriven;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;  
import org.apache.struts2.dispatcher.SessionMap;  
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.interceptor.ParameterAware;
import org.apache.struts2.util.ServletContextAware;  
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger; 


public abstract class TopAction extends ActionSupport implements SessionAware, ServletContextAware{

		static final long serialVersionUID = 80L;	
   
		String action="", url="", checkAddrUrl="", id="";
		static Logger logger = LogManager.getLogger(TopAction.class);
		static boolean activeMail = false;
		User user = null;
	  ServletContext ctx;
		Map<String, Object> sessionMap;

		public void setAction(String val){
				if(val != null)
						action = val;
		}
		public void setAction2(String val){
				if(val != null && !val.equals(""))
						action = val;
		}		
		public String getAction(){
				return action;
		}
		public void setId(String val){
				if(val != null)
						id = val;
		}
		public String getId(){
				return id;
		}
		public User getUser(){
				return user;
		}		
		String doPrepare(){
				String back = "";
				try{
						user = (User)sessionMap.get("user");
						if(url.equals("")){
								String val = ctx.getInitParameter("url");
								if(val != null)
										url = val;
								val = ctx.getInitParameter("checkAddrUrl");
								if(val != null)
										checkAddrUrl = val;
								val = ctx.getInitParameter("activeMail");
								if(val != null && val.equals("true"))
										activeMail = true;								
						}
						setUrls();
						if(user == null){
								HttpServletResponse res = ServletActionContext.getResponse();
								String str = "/row/Login";
								res.sendRedirect(str);
								return super.execute();
						}								
				}catch(Exception ex){
						System.out.println(ex);
				}		
				return back;
		}		
		@Override  
		public void setSession(Map<String, Object> map) {  
				sessionMap=map;  
		}
		@Override  	
		public void setServletContext(ServletContext ctx) {  
        this.ctx = ctx;  
    }
		private void setUrls(){
				HttpServletRequest request = ServletActionContext.getRequest();				
				String host_forward = request.getHeader("X-Forwarded-Host");
				String host = request.getHeader("host");				
				if(host_forward != null){
						// System.err.println(" forward "+host_forward);
						url = host_forward+"/row/";
				}
				else if(host != null){
						// System.err.println("host "+host);												
						if(host.indexOf("row") > -1){
								url = host;
						}
						else{
								url = host+"/row/";
						}
				}
		}
		
}





































