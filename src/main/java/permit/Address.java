package permit;
/**
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
 */

import java.sql.*;
import javax.naming.*;
import javax.naming.directory.*;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.ResponseHandler;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.*;
import org.json.XML;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Address implements java.io.Serializable{

    String id="", address="", loc_lat="", loc_long="";
		String street_address = ""; // from master address
    boolean debug = false;
		static final long serialVersionUID = 212L;	
		static Logger logger = LogManager.getLogger(Address.class);
		String excavation_id="", user_id="";
		Excavation excavation = null;
    String errors = "";
    public Address(){

    }
    public Address(String val){
				setId(val);
    }
    public Address(String val,
									 String val2,
									 String val3,
									 String val4
									 ){
				setValues(val, val2, val3, val4);
    }
		void setValues(
									 String val,
									 String val2,
									 String val3,
									 String val4
									 ){
				setId(val);
				setAddress(val2);
				setLoc_lat(val3);
				setLoc_long(val4);
		}
		//
		// setters
		//
		public void setId(String val){
				if(val != null)
						id = val;
    }
		public void setAddress(String val){
				if(val != null)
						address = val;
    }
		public void setLoc_lat(String val){
				if(val != null)
						loc_lat = val;
    }
		public void setLoc_long(String val){
				if(val != null)
						loc_long = val;
    }
		public void setExcavation_id(String val){
				if(val != null)
						excavation_id = val;
    }
		public void setUser_id(String val){
				if(val != null)
						user_id = val;
    }	
    //
    // getters
    //
		public String getId(){
				return id;
    }	
		public String getAddress(){
				return address;
    }	
    public String getLoc_lat(){
				return loc_lat;
    }
		public String getLoc_long(){
				return loc_long;
    }
    public double getLoc_lat_dbl(){
				double ret = 0.;
				try{
						ret = Double.parseDouble(loc_lat);
				}
				catch(Exception ex){

				}
				return ret;
    }
		public double getLoc_long_dbl(){
				double ret = 0.;
				try{
						ret = Double.parseDouble(loc_long);
				}
				catch(Exception ex){

				}
				return ret;		
    }	
		public String getExcavation_id(){
				return excavation_id;
    }	
		public String toString(){
				return address;
		}
		public Excavation getExcavation(){
				if(excavation == null && !excavation_id.equals("")){
						Excavation one = new Excavation(excavation_id);
						String back = one.doSelect();
						if(back.equals("")){
								excavation = one;
						}
				}
				return excavation;
		}
		public String getAddressFromExcavation(){
				String back="";
				getExcavation();
				if(excavation != null){
						Address one = excavation.getAddress();
						if(one != null){
								id = one.getId();			
								address = one.getAddress();
								loc_lat = one.getLoc_lat();
								loc_long = one.getLoc_long();
						}
						else{
								back = "No address found";
						}
				}
				else{
						back = "No excavation found";
				}
				return back;
		}
		public boolean isValidAddress(){
				return !(address.equals("") || loc_lat.equals("") || loc_long.equals(""));
		}
		public String doSelect(){
		
				String msg="";
				String qq = "select id,address,loc_lat,loc_long from "+
						"addresses where id=?";
				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				if(id.equals("") && !excavation_id.equals("")){
						return getAddressFromExcavation();
				}
				if(debug)
						logger.debug(qq);
				try{
						con = Helper.getConnection();
						if(con == null){
								msg = "Could not connect ";
						}
						else{
								pstmt = con.prepareStatement(qq);
								pstmt.setString(1,id);
								rs = pstmt.executeQuery();
								if(rs.next()){
										setValues(rs.getString(1),
															rs.getString(2),
															rs.getString(3),
															rs.getString(4));
								}
						}
				}
				catch(Exception ex){
						msg += " "+ex;
						logger.error(ex+" : "+qq);
				}
				finally{
						Helper.databaseDisconnect(con, pstmt, rs);
				}
				return msg;
		}
		public String doSave(){
		
				String msg = "";
				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				if(address.equals("")){
						msg = "address is required";
						return msg;
				}
				if(!street_address.equals("")){
						address = street_address;
				}
				String qq = "insert into addresses values (0,?,?,?)";
				con = Helper.getConnection();
				if(con == null){
						msg = "Could not connect to Database ";
						logger.error(msg);
						return msg;
				}
				try {
						if(debug)
								logger.debug(qq);			
						pstmt = con.prepareStatement(qq);
						msg += setFields(pstmt);
						pstmt.executeUpdate();
						qq = "select LAST_INSERT_ID() ";
						if(debug){
								logger.debug(qq);
						}
						pstmt = con.prepareStatement(qq);			
						rs = pstmt.executeQuery();
						if(rs.next()){
								id = rs.getString(1);
						}			
				}
				catch (Exception ex){
						msg += ex+":"+qq;
						logger.error(ex+":"+qq);
				}
				finally{
						Helper.databaseDisconnect(con, pstmt, rs);
				}
				return msg;
    }	
		public String setFields(PreparedStatement pstmt){

				String msg = "";
				String today = Helper.getToday();
				int jj = 1;
				try{
						if(address.equals(""))
								pstmt.setNull(jj++, Types.VARCHAR);
						else
								pstmt.setString(jj++, address);				
						if(loc_lat.equals(""))	
								pstmt.setNull(jj++, Types.VARCHAR);
						else				
								pstmt.setString(jj++, loc_lat);
						if(loc_long.equals(""))
								pstmt.setNull(jj++, Types.VARCHAR);
						else						
								pstmt.setString(jj++, loc_long);
				}
				catch(Exception ex){
						msg += ex;
						logger.error(msg);
				}
				return msg;
		}
		public String doUpdate(){
		
				String msg = "";
				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				//
		
				String qq = "";
				con = Helper.getConnection();
				if(con == null){
						msg = "Could not connect to Database ";
						logger.error(msg);
						return msg;
				}		
				try {
						//
						qq = "update addresses set ";
						qq += "address = ?,"; 
						qq += "loc_lat =?,"; 
						qq += "loc_long =? ";
						qq += "where id=? ";
						logger.debug(qq);
						pstmt = con.prepareStatement(qq);
						setFields(pstmt);
						pstmt.setString(4, id);
						pstmt.executeUpdate();
				}
				catch (Exception ex){
						msg += ex+":"+qq;
						logger.error(ex+":"+qq);
				}
				finally{
						Helper.databaseDisconnect(con, pstmt, rs);
				}
				return msg;
		}
		public String doDelete(){
		
				String msg = "";
				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;		
				if(id.equals("")){
						msg = "company id not set";
						return msg;
				}
				//
				String qq = "delete from addresses where id=?";
				con = Helper.getConnection();
				if(con == null){
						msg = "Could not connect to Database ";
						logger.error(msg);
						return msg;
				}
				try {
						if(debug)
								logger.debug(qq);
						pstmt = con.prepareStatement(qq);
						pstmt.setString(1, id);
						pstmt.executeUpdate();
				}
				catch (Exception ex){
						msg += ex+":"+qq;
						logger.error(msg);
				}
				finally{
						Helper.databaseDisconnect(con, pstmt, rs);
				}
				return msg;
		}
		//
		//
		void getMasterAddrInfo(String url2){
				//
				String back = "";		
				String url = url2+"addresses/verify.php?format=xml&address=";
				DefaultHttpClient httpclient = new DefaultHttpClient();		
				String addrStr = "";
				String streetAddress="";
				double addr_lat=0, addr_long=0;
				try{
						addrStr = java.net.URLEncoder.encode(address, "UTF-8");
						addrStr += "+Bloomington";			
						url += addrStr;			
						if(debug){
								logger.debug(url);
						}
						HttpGet httpget = new HttpGet(url);
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            String responseBody = httpclient.execute(httpget, responseHandler);
            logger.debug("----------------------------------------");
            logger.debug(responseBody);
            logger.debug("----------------------------------------");
			
						JSONObject jObj = org.json.XML.toJSONObject(responseBody);
						// System.err.println(jObj.toString());
						if(jObj.has("address")){
								JSONObject jObj2 = jObj.getJSONObject("address");
								if(jObj2.has("streetAddress")){
										street_address = jObj2.getString("streetAddress");
										// System.err.println(street_address);					
								}
								if(jObj2.has("latitude")){
										addr_lat = jObj2.getDouble("latitude");
								}
								if(jObj2.has("longitude")){
										addr_long = jObj2.getDouble("longitude");
								}
								if(addr_lat != 0){
										loc_lat = ""+addr_lat;
										loc_long = ""+addr_long;
								}
								// System.err.println(streetAddress+" "+addr_lat+" "+addr_long);
						}
				}catch(Exception ex){
						logger.error(" "+ex);
				}
		}	
}
