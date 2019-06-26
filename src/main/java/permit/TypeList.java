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

public class TypeList{

		static Logger logger = LogManager.getLogger(TypeList.class);
		static final long serialVersionUID = 271L;	
		String table_name="bond_types";
		List<Type> types = null;
    String errors = "";
    public TypeList(){
    }
    public TypeList(String table){
				setTable_name(table);
    }
    void setTable_name(String val){
				if(val != null){
						table_name = val;
				}
		}
		public String getTable_name(){
				return table_name;
		}
		String getErrors(){
				return errors;
		}
		public List<Type> getTypes(){
				return types;
		}
	
		public String find(){
		
				String msg="";
				String qq = "select id, name from "+table_name+" order by name ";
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
						stmt = con.createStatement();
						types = new ArrayList<Type>();
						rs = stmt.executeQuery(qq);
						while(rs.next()){
								String str = rs.getString(1);
								String str2 = rs.getString(2);
								if(str != null && str2 != null){
										Type cl = new Type(str, str2);
										types.add(cl);
								}
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
