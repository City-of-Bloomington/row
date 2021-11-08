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
		List<Inspector> inspectors = null;
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
		public List<Inspector> getInspectors(){
				return inspectors;
		}
    /**
		 * find all inspectors from the database
		 * @return errors if any
		 */
		public String find(){
		
				String msg="";
				Connection con = null;
				Statement stmt = null;
				ResultSet rs = null;
				
				String qq = "select u.id,u.empid,u.fullname,u.role,u.active,s.phone,s.fax_num,s.active from users u,inspectors s where s.user_id=u.id ";				

				if(activeOnly){
						qq += " and s.active is not null and u.active is not null ";
				}
				qq += " order by u.fullname ";
				
				logger.debug(qq);
				con = Helper.getConnection();
				if(con == null){
						msg = "Could not connect ";
						return msg;
				}		
				try{
						stmt = con.createStatement();
						rs = stmt.executeQuery(qq);
						inspectors = new ArrayList<>();
						while(rs.next()){
								Inspector one =
										new Inspector(
																	rs.getString(1),
																	rs.getString(2), 
																	rs.getString(3),
																	rs.getString(4),
																	rs.getString(5),
																	rs.getString(6),
																	rs.getString(7),
																	rs.getString(8));
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
