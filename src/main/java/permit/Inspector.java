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
		String phone="",fax_num="",activeInspect="";
		String first_name="",last_name="";// not needed
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
										 String val5,
										 
										 String val6,
										 String val7,
										 String val8
										 ){
				super(val, val2, val3, val4, val5);
				setPhone(val6);
				setFax_num(val7);
				setActiveInspect(val8);
    }
		public String getPhone(){
				return phone;
		}
		public String getFax_num(){
				return fax_num;
		}
		public String getActiveInspect(){
				return activeInspect;
		}
		public void setPhone(String val){
				if(val != null)
						phone = val;
    }
		public void setFax_num(String val){
				if(val != null)
						fax_num = val;
    }		
    public void setActiveInspect(String val){
				if(val != null && !val.isEmpty())
						activeInspect = val;
    }
    public void setActiveInspectStatus(boolean val){
				if(val)
						activeInspect = "y";
    }
		public void setUser(User user){
				setId(user.getId());
				setEmpid(user.getEmpid());
				setFullName(user.getFullName());
				setRole(user.getRole());
				setActive(user.getActive());
		}
		public boolean getActiveInspectStatus(){
				return !activeInspect.equals("");
		}		
    //
    public String toString(){
				if(fullName.isEmpty()) return empid;
				else return fullName;
    }
		public boolean isInspector(){
				int cnt = 0;
				if(!id.isEmpty()){
						String msg="", qq="";
						qq = "select count(*) from inspectors s where s.user_id=? ";
						Connection con = null;
						PreparedStatement pstmt = null;
						ResultSet rs = null;
						con = Helper.getConnection();
						if(con != null){
								logger.debug(qq);
								try{
										pstmt = con.prepareStatement(qq);
										pstmt.setString(1, id);						
										rs = pstmt.executeQuery();
										if(rs.next()){
												cnt = rs.getInt(1);
										}
								}
								catch(Exception ex){
										msg += " "+ex;
								logger.error(ex+":"+qq);
								}
								finally{
										Helper.databaseDisconnect(con, pstmt, rs);
								}				
						}
				}
				return cnt > 0;
		}
		public String doSelect(){
		
				String msg="", qq="";
				qq = "select u.id,u.empid,u.fullname,u.role,u.active,s.phone,s.fax_num,s.active from users u,inspectors s where s.user_id=u.id and ";
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
								
								setPhone(rs.getString(6));
								setFax_num(rs.getString(7));
								setActiveInspect(rs.getString(8));
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
		public String doSave(){
		
				String msg = "";
				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				if(id.isEmpty()){
						msg = " user info not set";
						return msg;
				}
				String qq = "insert into inspectors values (?,null,null,?,?,'y')";
				con = Helper.getConnection();
				if(con == null){
						msg = "Could not connect to Database ";
						logger.error(msg);
						return msg;
				}
				try {
						logger.debug(qq);			
						pstmt = con.prepareStatement(qq);
						pstmt.setString(1, id);
						if(phone.isEmpty())
								pstmt.setNull(2, Types.VARCHAR);
						else
								pstmt.setString(2, phone);
						if(fax_num.isEmpty())
								pstmt.setNull(3, Types.VARCHAR);
						else
								pstmt.setString(3, fax_num);						
						pstmt.executeUpdate();
						
				}
				catch (Exception ex){
						msg += ex+":"+qq;
						logger.error(ex+":"+qq);
				}
				finally{
						Helper.databaseDisconnect(con, rs, pstmt);
				}
				return msg;
    }
		public String doUpdate(){
		
				String msg = "";
				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				if(id.isEmpty()){
						msg = " user info not set";
						return msg;
				}
				String qq = "update inspectors set phone=?,fax_num=?,active=? where user_id=?";
				con = Helper.getConnection();
				if(con == null){
						msg = "Could not connect to Database ";
						logger.error(msg);
						return msg;
				}
				try {
						logger.debug(qq);			
						pstmt = con.prepareStatement(qq);

						if(phone.isEmpty())
								pstmt.setNull(1, Types.VARCHAR);
						else
								pstmt.setString(1, phone);
						if(fax_num.isEmpty())
								pstmt.setNull(2, Types.VARCHAR);
						else
								pstmt.setString(2, fax_num);
						if(activeInspect.isEmpty())
								pstmt.setNull(3, Types.VARCHAR);
						else
								pstmt.setString(3, "y");						
						pstmt.setString(4, id);
						pstmt.executeUpdate();
						
				}
				catch (Exception ex){
						msg += ex+":"+qq;
						logger.error(ex+":"+qq);
				}
				finally{
						Helper.databaseDisconnect(con, rs, pstmt);
				}
				return msg;
    }				
	
}
