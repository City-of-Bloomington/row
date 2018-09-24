/**
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
package permit;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class InspectorList{

		static Logger logger = LogManager.getLogger(InspectorList.class);
		static final long serialVersionUID = 151L;
		boolean activeOnly = false;
		List<User> inspectors = null;
    String errors = "";
    public InspectorList(){
    }
		public String getErrors(){
				return errors;
		}
		public boolean hasErrors(){
				return !errors.equals("");
		}
		public void setActiveOnly(){
				activeOnly = true;
		}
		public List<User> getInspectors(){
				return inspectors;
		}
    /**
		 * find all inspectors from the database
		 * @return errors if any
		 */
		public String find(){
		
				String msg="";
				String qq = "select u.id,u.empid,u.fullname,u.role,s.active from users u,inspectors s where s.user_id=u.id ";				
				// String qq = "select * from users u,inspectors s where u.id=s.user_id order by u.fullname";
				Connection con = null;
				Statement stmt = null;
				ResultSet rs = null;
				logger.debug(qq);
				con = Helper.getConnection();
				if(con == null){
						msg = "Could not connect ";
						return msg;
				}		
				try{
						if(activeOnly){
								qq += " and s.active is not null ";
						}
						qq += " order by u.fullname ";
						stmt = con.createStatement();
						rs = stmt.executeQuery(qq);
						inspectors = new ArrayList<User>();
						while(rs.next()){
								String str = rs.getString(1);
								String str2 = rs.getString(2); 
								String str3 = rs.getString(3);
								String str4 = rs.getString(4);
								String str5 = rs.getString(5);
								User one = new User(str, str2, str3, str4, str5);
								inspectors.add(one);
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
