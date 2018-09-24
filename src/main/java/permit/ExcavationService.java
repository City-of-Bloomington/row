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
 * provides excavation info in json format as a service for javascript 
 * drawing maps
 */
public class ExcavationService extends HttpServlet{

    String url="";
    boolean debug = false, production = false;
		static final long serialVersionUID = 193L;	
		static Logger logger = LogManager.getLogger(ExcavationService.class);

    
    public void doGet(HttpServletRequest req, 
											HttpServletResponse res) 
				throws ServletException, IOException {
				doPost(req,res);
    }
    /**
     * @link #doGetost
     */

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
				ExcavationList el = new ExcavationList();
				while (values.hasMoreElements()){
						name = values.nextElement().trim();
						vals = req.getParameterValues(name);
						value = vals[vals.length-1].trim();	
						if (name.equals("lat_from")) { 
								el.setLat_from(value);
						}
						else if (name.equals("lat_to")) { 
								el.setLat_to(value);
						}
						else if (name.equals("long_from")) {
								el.setLong_from(value);
						}
						else if (name.equals("long_to")) { 
								el.setLong_to(value);
						}			
						else if (name.equals("action")){ 
								action = value;  
						}
						else{
								// System.err.println(name+" "+value);
						}
				}
		
				List<Excavation> list = null;
				if(el.hasCoords()){
						el.setNoLimit();
						String back = el.find();
						if(back.equals("")){
								list = el.getExcavations();
						}
				}
				if(list != null && list.size() > 0){
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
	   * **************************
		 */
		String writeJson(List<Excavation> list){
				String json="";
				int jj = 0;
				for(Excavation one:list){
						jj++;
						if(jj > 100) break;
						Address addr = one.getAddress();
						if(addr == null) continue;
						if(!json.equals("")) json += ",";
						json += "{\"id\":\""+one.getId()+"\",\"address\":\""+addr.getAddress()+"\",\"permit_num\":\""+one.getPermit_num()+"\",\"utility_type_id\":\""+one.getUtility_type_id()+"\",\"loc_lat\":\""+addr.getLoc_lat()+"\",\"loc_long\":\""+addr.getLoc_long()+"\",\"status\":\""+one.status+"\"}";
				}
				json = "["+json+"]";
				return json;
		}

}






















































