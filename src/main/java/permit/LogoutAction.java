/**
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
package permit;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogoutAction extends TopAction{
		
    private static final long serialVersionUID = 60L;
    @Override
    public String execute(){
				doPrepare();
				try{
						HttpServletRequest request=ServletActionContext.getRequest();  
						HttpSession session=request.getSession();
						if(session != null)
								session.invalidate();
						try{
								HttpServletResponse res = ServletActionContext.getResponse();
								String str = url+"Logout";
								res.sendRedirect(str);
								return super.execute();
						}catch(Exception ex){
								System.err.println(ex);
						}			
				}catch(Exception ex){
						System.out.println(ex);
				}
				return SUCCESS;
    }     

		@Override  	
		public void setServletContext(ServletContext ctx) {  
        this.ctx = ctx;  
    }  		
}



