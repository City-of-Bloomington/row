/**
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
package permit;
import java.util.*; 
import javax.servlet.ServletContext;
import com.opensymphony.xwork2.ModelDriven;
import org.apache.struts2.ServletActionContext;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger; 


public class WelcomeAction extends TopAction{
    private static final long serialVersionUID = 193L;
		static Logger logger = LogManager.getLogger(WelcomeAction.class);

    @Override
		public String execute(){
				String ret = SUCCESS;
				doPrepare();
				if(user == null){
						try{
								HttpServletResponse res = ServletActionContext.getResponse();
								String str = url+"Login";
								res.sendRedirect(str);
								return super.execute();
						}catch(Exception ex){
								System.err.println(ex);
						}
				}
				return ret;
		}

}


