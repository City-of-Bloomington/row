/**
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
package permit;
import java.sql.*;
import javax.naming.*;
import javax.naming.directory.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger; 

public class Type{

    String id="", name="", user_id="";
		static Logger logger = LogManager.getLogger(Type.class);
		String table_name = "bond_companies"; // contact_types, cut_types,utility_types
		static final long serialVersionUID = 261L;	

    String errors = "";
    public Type(String val, String val2, String val3){
				setId(val);
				setName(val2);
				setTable_name(val3);
    }	
    public Type(String val, String val2){
				setId(val);
				setName(val2);
    }
	
    public Type(String val){

				setId(val);
    }	
    public Type(){
    }
		//
		// we need for list
		//
    // getters
    //
		public String getId(){
				if(id.equals("")){
						return "-1";
				}
				return id;
    }
		public String getName(){
				return Helper.initCap(name);
    }
		public String getNameSingle(){
				String ret = Helper.initCap(name);
				if(ret.endsWith("ies")){ // companies
						ret = ret.substring(0,ret.indexOf("ies"))+"y";
				}
				else if(ret.endsWith("ses")){ // statuses
						int len = ret.length();
						ret = ret.substring(0,len - 2);
				}		
				else if(ret.endsWith("s")){
						int len = ret.length();
						ret = ret.substring(0,len - 1);
				}
				return ret;
    }	
		public String getTabke_name(){
				return table_name;
    }	
		public String getErrors(){
				return errors;
    }
		boolean hasErrors(){
				return !errors.equals("");
		}

		public void setId(String val){
				if(val != null && !val.equals("-1"))
						id = val;
		}
		public void setName(String val){
				if(val != null)
						name = val;
		}
	
		public void setTable_name(String val){
				if(val != null){
						table_name = val;
				}
		}
		public void setUser_id(String val){
				if(val != null)
						user_id = val;
		}	
		public String toString(){
				return getName();
		}
		public String doSave(){
		
				String msg = "";
				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				if(name.equals("")){
						msg = " name not set ";
						return msg;
				}
				if(table_name.equals("")){
						msg = " table name not set";
						return msg;
				}
				String qq = "insert into "+table_name+" values (0,?)";
				con = Helper.getConnection();
				if(con == null){
						msg = "Could not connect to Database ";
						logger.error(msg);
						return msg;
				}
				try {
						logger.debug(qq);			
						pstmt = con.prepareStatement(qq);
						pstmt.setString(1, name);
						pstmt.executeUpdate();
						qq = "select LAST_INSERT_ID() ";
						logger.debug(qq);
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
		public String doUpdate(){
		
				String msg = "";
				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				if(name.equals("")){
						msg = " type name not set ";
				}
				String qq = "update "+table_name+" set name=? where id=? ";
				con = Helper.getConnection();
				if(con == null){
						msg = "Could not connect to Database ";
						logger.error(msg);
						return msg;
				}
				try {
						logger.debug(qq);			
						pstmt = con.prepareStatement(qq);
						pstmt.setString(1, name);
						pstmt.setString(2, id);			
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
		public String doSelect(){
		
				String msg = "";
				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				String qq = "";
				con = Helper.getConnection();
				if(con == null){
						msg = "Could not connect to Database ";
						logger.error(msg);
						return msg;
				}
				qq = "select name from "+table_name+" where id=? "; 
				logger.debug(qq);
				try {
						pstmt = con.prepareStatement(qq);
						pstmt.setString(1, id);
						rs = pstmt.executeQuery();
						//
						if(rs.next()){
								setName(rs.getString(1));
						}
						else{
								msg = "No record found for id "+id;
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
	
}
