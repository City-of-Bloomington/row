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

public class PickPoint extends HttpServlet{

    String url="";
    boolean debug = false, production = false;
		static final long serialVersionUID = 1194L;	
		static Logger logger = LogManager.getLogger(PickPoint.class);
    
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
    
				String message="", action="", point_type="start";
				res.setContentType("text/html");
				PrintWriter out = res.getWriter();
				String name, value;
				HttpSession session = null;
				if(url.equals("")){
						url    = getServletContext().getInitParameter("url");
				}
				Enumeration<String> values = req.getParameterNames();
				String [] vals = null;
				String center_lat = "39.16992723";
				String center_long = "-86.53680559";
				while (values.hasMoreElements()){
						name = values.nextElement().trim();
						vals = req.getParameterValues(name);
						value = vals[vals.length-1].trim();	
						if (name.equals("point_type")) { // this is what jquery sends
								point_type = value;
						}
						else if (name.equals("center_lat")){
								if(!value.equals(""))
										center_lat = value;  
						}
						else if (name.equals("center_long")){
								if(!value.equals(""))
										center_long = value;  
						}			
						else if (name.equals("action")){ 
								action = value;  
						}
						else{

						}
				}
				out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
				out.println("<html><head><title>Pick Point</title></head><body>");
				out.println(" <div id=\"map\" style=\"width: 400px; height: 300px\"></div>\n");
				out.println("<br />");
				out.println("Note:");
				out.println("<ul><li>you can pan or zoom in/out to get to the point you want</li>");
				out.println(" <li>The start point should be the lower left conrner of you map</li>");
				out.println(" <li>The end point should be the top right corner of the map</li>");
				out.println("</ul>");
				out.println(" ");
				out.println("<a href=\"javascript:window.close();\">Close</a>");
				//
				out.println("<script type=\"text/javascript\" src=\"//maps.google.com/maps/api/js?sensor=false\"></script>");	
				out.println("<script>");
				out.println(" var marker = null; ");
				out.println(" var myStyles =[{ ");
				out.println(" featureType: \"poi\",");
				out.println(" elementType: \"labels\",");
				out.println(" stylers: [ ");
				out.println(" { visibility: \"off\" } ");
				out.println(" ]                       ");
				out.println(" }                       ");
				out.println(" ];                      ");	  
				out.println(" function initialize() { ");
				out.println(" var mapOptions = {      ");
				out.println(" zoom: 17,               ");
				out.println(" styles: myStyles,       ");// 401 n morton st
				out.println(" center: new google.maps.LatLng("+center_lat+","+center_long+") ");
				out.println(" };                                ");
				out.println(" var map = new google.maps.Map(document.getElementById('map'), mapOptions); ");
				out.println(" google.maps.event.addListener(map, 'click', function(e) { ");
				out.println(" placeMarker(e.latLng, map); ");
				out.println(" });                         ");
				out.println(" placeMarker(mapOptions.center, map); ");
				out.println(" } ");
  
				out.println(" function placeMarker(position, map) { ");
				out.println(" if(marker) marker.setMap(null); ");
				out.println(" marker = new google.maps.Marker({ ");
				out.println(" position: position, ");
				out.println(" map: map ");
				out.println(" }); ");
				out.println(" map.panTo(position); ");
				if(point_type.equals("start")){
						out.println(" opener.document.getElementById('start_lat').value=position.lat(); ");
						out.println(" opener.document.getElementById('start_long').value=position.lng();	");
				}
				else{
						out.println(" opener.document.getElementById('end_lat').value=position.lat(); ");
						out.println(" opener.document.getElementById('end_long').value=position.lng();	");
				}
				out.println(" } ");
				out.println(" google.maps.event.addDomListener(window, 'load', initialize); ");
				out.println(" </script>  ");
				out.println("</body>");
				out.println("</html>");		
				out.close();
    }

}






















































