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

public class Inspector extends User{

	
		static final long serialVersionUID = 282L;	
    String errors = "";
		static Logger logger = LogManager.getLogger(Inspector.class);
    public Inspector(){
    }	
    public Inspector(String val){
				super(val);
    }
    public Inspector(String val, String val2){
				super(val, val2);
    }	
	
    public Inspector(
										 String val,
										 String val2,
										 String val3,
										 String val4,
										 String val5
										 ){
				super(val, val2, val3, val4, val5);
    }	
    //
    public String toString(){
				if(fullName.equals("")) return empid;
				else return fullName;
    }
		public String doSelect(){
		
				String msg="", qq="";
				qq = "select u.id,u.empid,u.fullname,u.role,s.active from users u,inspectors s where s.user_id=u.id and ";
				if(!empid.equals(""))
						qq += " u.empid = ?";
				else
						qq += " u.id=?";
				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				con = Helper.getConnection();
				if(con == null){
						msg = "Could not connect ";
						return msg;
				}		
				logger.debug(qq);
				try{
						pstmt = con.prepareStatement(qq);
						if(!empid.equals(""))
								pstmt.setString(1, empid);
						else
								pstmt.setString(1, id);
						rs = pstmt.executeQuery();
						if(rs.next()){
								setId(rs.getString(1));
								setEmpid(rs.getString(2));
								setFullName(rs.getString(3));
								setRole(rs.getString(4));
								setActive(rs.getString(5));
								userExists = true;
						}
						else{
								msg = "No match found";
						}
				}
				catch(Exception ex){
						msg += " "+ex;
						logger.error(ex+":"+qq);
				}
				finally{
						Helper.databaseDisconnect(con, pstmt, rs);
				}
				return msg;
		}
	
}
