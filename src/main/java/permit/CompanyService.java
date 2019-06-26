/**
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
package permit;

import java.util.*;
import java.sql.*;
import java.io.*;
import javax.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.naming.*;
import javax.naming.directory.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * provides the list of companies for javascript jquery auto complete function
 * in json format
 */
public class CompanyService extends HttpServlet{

    String url="";
    boolean debug = false, production = false;
		static final long serialVersionUID = 182L;	
		static Logger logger = LogManager.getLogger(CompanyService.class);

    
    public void doGet(HttpServletRequest req, 
											HttpServletResponse res) 
				throws ServletException, IOException {
				doPost(req,res);
    }

    public void doPost(HttpServletRequest req, 
											 HttpServletResponse res) 
				throws ServletException, IOException {
    
				String id = "";
				String message="", action="";
				res.setContentType("application/json");
				PrintWriter out = res.getWriter();
				String name, value;
				String fullName="", term ="", type="", name_str="";
				boolean success = true;
				HttpSession session = null;
				if(url.equals("")){
						url    = getServletContext().getInitParameter("url");
				}
				Enumeration<String> values = req.getParameterNames();
				String [] vals = null;
				while (values.hasMoreElements()){
						name = values.nextElement().trim();
						vals = req.getParameterValues(name);
						value = vals[vals.length-1].trim();	
						if (name.equals("name")) { // this is what jquery sends
								name_str = value;
						}
						else if (name.equals("term")) { // this is what jquery sends
								name_str = value;
						}			
						else if (name.equals("action")){ 
								action = value;  
						}
						else{
								// System.err.println(name+" "+value);
						}
				}
				logger.debug(" comp service ");
				CompanyList cl = null;
				List<Company> list = null;
				if(!name_str.equals("")){
						cl = new CompanyList();
						cl.setName(name_str);
						cl.setSort_by("c.name");
						cl.setNo_limit();
						String back = cl.find();
						if(back.equals("")){
								list = cl.getCompanies();
						}
						else{
								logger.debug(" comp service "+back);
						}
				}
				if(list != null && list.size() > 0){
						logger.debug(" comp service write json ");
						String json = writeJson(list);
						out.println(json);
				}
				out.flush();
				out.close();
    }
		/**
		 * *************************
		 *
		 * json format as an array
	   ***************************
		 */
		String writeJson(List<Company> list){
				String json="";
				for(Company one:list){
						if(!json.equals("")) json += ",";
						json += "{\"id\":\""+one.getId()+"\",\"value\":\""+one.getName()+" "+one.getAddress()+"\"}";
				}
				json = "["+json+"]";
				return json;
		}

}






















































