/**
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
package permit;
import java.util.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Logout extends HttpServlet{

    String url="", url3="", cas_url="";
		static final long serialVersionUID = 181L;	
    boolean debug = false;

    public void doGet(HttpServletRequest req, 
											HttpServletResponse res) 
				throws ServletException, IOException{

				res.setContentType("text/html");
				PrintWriter out = res.getWriter();
				String name= "";
				String value = "";
				if(url.equals("")){
						url   = getServletContext().getInitParameter("url");
						cas_url = getServletContext().getInitParameter("cas_url");	
				}
				HttpSession session = req.getSession(false);
				if(session != null){
						session.invalidate();
				}
				res.sendRedirect(cas_url+"?url="+url);
				return;
    }

}






















































