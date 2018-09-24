package permit;
/**
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */

import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AddressList{

    boolean debug = false;
		static final long serialVersionUID = 214L;		
		static Logger logger = LogManager.getLogger(AddressList.class);
		List<Address> addresses = null;
    String errors = "";
    public AddressList(){
    }
		public List<Address> getAddresses(){
				return addresses;
		}
    /**
		 * find all addresses from the database
		 * @return errors if any
		 */
		public String find(){
		
				String msg="";
				String qq = "select id,address,loc_lat,loc_long from "+
						"addresses order by address ";
				Connection con = null;
				Statement stmt = null;
				ResultSet rs = null;
				if(debug)
						logger.debug(qq);
				try{
						con = Helper.getConnection();
						if(con == null){
								msg = "Could not connect ";
						}
						else{
								stmt = con.createStatement();
						}
						addresses = new ArrayList<Address>();
						rs = stmt.executeQuery(qq);
						while(rs.next()){
								Address one = new Address(rs.getString(1),
																					rs.getString(2),
																					rs.getString(3),
																					rs.getString(4));
								addresses.add(one);
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
