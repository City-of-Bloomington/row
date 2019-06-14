/**
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
package permit;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import javax.naming.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger; 

public class UserList{

		static Logger logger = LogManager.getLogger(UserList.class);
		static final long serialVersionUID = 271L;	
		String table_name="users";
		List<User> users = null;
    String errors = "";
    public UserList(){
    }
		String getErrors(){
				return errors;
		}
		public List<User> getUsers(){
				return users;
		}
	
		public String find(){
		
				String msg="";
		
				String qq = "select id,empid, fullname, role, active from users order by fullname ";
				Connection con = null;
				Statement stmt = null;
				ResultSet rs = null;
				logger.debug(qq);
				try{
						con = Helper.getConnection();
						if(con == null){
								msg = "Could not connect ";
						}
						else{
								stmt = con.createStatement();
						}
						users = new ArrayList<>();
						rs = stmt.executeQuery(qq);
						while(rs.next()){
								User one = new User(rs.getString(1),
																		rs.getString(2),
																		rs.getString(3),
																		rs.getString(4),
																		rs.getString(5));
								if(!users.contains(one))
										users.add(one);
						}
				}
				catch(Exception ex){
						msg += " "+ex;
						logger.error(ex+" : "+qq);
				}
				finally{
						Helper.databaseDisconnect(con, stmt, rs);
				}
				return msg;
		}
	
}
