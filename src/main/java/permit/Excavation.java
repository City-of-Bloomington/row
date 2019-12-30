/**
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
package permit;

import java.util.*;
import java.sql.*;
import java.io.*;
import java.text.*;
import javax.naming.*;
import javax.sql.*;
import javax.naming.directory.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Excavation implements java.io.Serializable{

		static Logger logger = LogManager.getLogger(Excavation.class);
		static final long serialVersionUID = 201L;		
		static SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");	
		String permit_num = "", id="", cut_description="";
		String status = "", cut_type ="";

		String depth = "", width="", length="";
		String utility_type_id = "", permit_id="", user_id="";
		// adding new address we need these
		String address_id = "", address_str="", loc_lat="", loc_long="";
		Permit permit = null;
		Type utility_type = null;
		Address address = null;
		//
		String message="";
		//

		public Excavation(){
		}	
		public Excavation(String value){
				id = value;
		}
		public Excavation(
											String val,
											String val2,
											String val3,
											String val4,
											String val5,
											String val6,
											String val7,
											String val8,
											String val9,
											String val10,
											String val11,
											String val12,
											String val13,
											String val14			  
											){
				setValues(val, val2, val3, val4, val5,
									val6, val7, val8, val9, val10,
									val11, val12, val13, val14);
		}

		void setValues(String val,
									 String val2,
									 String val3,
									 String val4,
									 String val5,
									 String val6,
									 String val7,
									 String val8,
									 String val9,
									 String val10,
									 String val11,
									 String val12,
									 String val13,
									 String val14				   
									 ){
				setId(val);
				setCut_description(val2);
				setCut_type(val3);
				setPermit_num(val4);
				setAddress_id(val5);
				setDepth(val6);
				setWidth(val7);
			
				setLength(val8);
				setStatus(val9);
				setUtility_type_id(val10);
				if(val11 != null && !utility_type_id.equals("")){
						utility_type = new Type(utility_type_id, val11);
				}
				if(!address_id.equals("")){
						address = new Address(address_id, val12, val13, val14);
				}
		}
    //
		// setters
		//
		public void setId(String value){
				if(value != null)
						id = value;
		}
		public void setPermit_num(String value){
				if(value != null)
						permit_num = value;
		}
		public void setPermit_id(String value){
				if(value != null)
						permit_id = value;
		}	
		public void setStatus(String value){
				if(value != null)
						status = value;
		}
		public void setCut_description(String value){
				if(value != null)
						cut_description = value;
		}
		public void setAddress_id(String value){
				if(value != null)
						address_id = value;
		}
		public void setAddress(String value){
				if(value != null)
						address_str = value;
		}
		public void setLoc_lat(String value){
				if(value != null)
						loc_lat = value;
		}
		public void setLoc_long(String value){
				if(value != null)
						loc_long = value;
		}	
		public void setDepth(String value){
				if(value != null)
						depth = value;
		}
	
		public void setWidth(String value){
				if(value != null)
						width = value;
		}
		public void setLength(String value){
				if(value != null)
						length = value;
		}
		public void setCut_type(String value){
				if(value != null && !value.equals("-1"))		
						cut_type = value;
		}	
		public void setUtility_type_id(String value){
				if(value != null && !value.equals("-1"))		
						utility_type_id = value;
		}
		public void setUser_id(String value){
				if(value != null)
						user_id = value;
		}
		//
		// getters
		//
		public String getId(){
		
				return id;
		}
		public String getPermit_num(){
				logger.debug("excav permit num ");				
				if(permit_num.equals("") && !permit_id.equals("")){
						getPermit();
						if(permit != null){
								permit_num = permit.getPermit_num();
						}
				}
				return permit_num;
		}
		public String getCut_description(){
				return cut_description;
		}
		public String getCut_type(){
				return cut_type;
		}
		public String getStatus(){
				return status;
		}	
		public String getAddress_id(){
				return address_id;
		}	
		public String getDepth(){
				return depth;
		}	
		public String getWidth(){
				return width;
		}
		public String getLength(){
				return length;
		}		
		public String getUtility_type_id(){
				return utility_type_id ;
		}
		public boolean hasValidAddress(){
				getAddress();
				if(address != null){
						return address.isValidAddress();
				}
				return false;
		}
		public String handleAddress(String url){
				String back = "";
				if(address == null){
						getAddress();
				}
				if(!address_id.equals("")){// if available in case of update
						back = address.doUpdate();
				}
				else{
						address.getMasterAddrInfo(url);
						back = address.doSave();
						address_id = address.getId();
				}
				return back;
		}
		public Permit getPermit(){
				if(permit == null){
						logger.debug("excav get permit ");
						if(!permit_id.equals("")){
								Permit one = new Permit(permit_id);
								String back = one.doSelect();
								if(back.equals("")){
										permit = one;
								}
						}
						else if(!permit_num.equals("")){
								PermitList pl = new PermitList();
								pl.setPermit_num(permit_num);
								String back = pl.find();
								if(back.equals("")){
										List<Permit> ones = pl.getPermits();
										if(ones != null && ones.size() > 0){
												permit = ones.get(0);
										}
								}
								else{
										logger.error("excav permit "+back);
								}
						}
				}
				return permit;
		}
		public boolean hasPermit(){
				if(permit == null){
						getPermit();
				}
				return permit != null;
		}
		public Address getAddress(){
				if(address == null){
						logger.debug("excav get address ");
						if(!address_id.equals("")){
								Address one = new Address(address_id);
								String back = one.doSelect();
								if(back.equals("")){
										address = one;
								}
								else{
										logger.error("excav address "+back);
								}
						}
						else{
								address = new Address(); // empty address
						}
				}
				return address;
		}
		public Type getUtility_type(){
				logger.debug("excav util types ");
				if(utility_type == null && !utility_type_id.equals("")){
						Type one = new Type(utility_type_id, null, "utility_types");
						String back = one.doSelect();
						if(back.equals("")){
								utility_type = one;
						}
				}
				return utility_type;
		}	
		//
		private String findOrAddAddress(){
				String msg = "";
				if(address_str.equals("")) return msg;

				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				String qq = " select id from addresses where address like ? ";
				String qq2 = " insert into addresses values(0,?,?,?) ";		
				con = Helper.getConnection();
				if(con == null){
						msg = "Could not connect to Database ";
						logger.error(msg);
						return msg;
				}
				logger.debug(qq);							
				try {
						pstmt = con.prepareStatement(qq);
						pstmt.setString(1, address_str);
						rs = pstmt.executeQuery();
						if(rs.next()){
								address_id = rs.getString(1);
						}
						if(address_id.equals("")){
								qq = qq2;
								logger.debug(qq);			
								pstmt = con.prepareStatement(qq);
								pstmt.setString(1, address_str);
								pstmt.setString(2, loc_lat);
								pstmt.setString(3, loc_long);
								pstmt.executeUpdate();
								qq = "select LAST_INSERT_ID() ";
								logger.debug(qq);
								pstmt = con.prepareStatement(qq);			
								rs = pstmt.executeQuery();
								if(rs.next()){
										address_id = rs.getString(1);
								}					
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
		/**
		 * save data to database
		 * @return string if an error occurs
		 */
		public String doSave(){
		
				String msg = "";
				Connection con = null;
				PreparedStatement pstmt = null, pstmt2=null;
				ResultSet rs = null;
				if(cut_description.equals("")){
						msg = "Cut description is required";
						return msg;
				}
				//
				// add the address first
				//
				if(address_str.equals("") && address_id.equals("")){
						msg = "Address is required";
						return msg;
				}
				if(address_id.equals("")){
						msg += findOrAddAddress();
				}
				String qq = "insert into excavcuts values (0,"+
						"?,?,?,?, ?,?,?,?,?)";
				con = Helper.getConnection();
				if(con == null){
						msg = "Could not connect to Database ";
						logger.error(msg);
						return msg;
				}
				logger.debug(qq);							
				try {
						pstmt = con.prepareStatement(qq);
						msg += setFields(pstmt);
						pstmt.executeUpdate();
						//
						qq = "select LAST_INSERT_ID() ";
						logger.debug(qq);
						pstmt2 = con.prepareStatement(qq);			
						rs = pstmt2.executeQuery();
						if(rs.next()){
								id = rs.getString(1);
						}			
				}
				catch (Exception ex){
						msg += ex+":"+qq;
						logger.error(ex+":"+qq);
				}
				finally{
						Helper.databaseDisconnect(con, rs, pstmt, pstmt2);
				}
				return msg;
    }
	
		//
		public String setFields(PreparedStatement pstmt){

				String msg = "";
				String today = Helper.getToday();
				int jj = 1;
				try{
						if(cut_description.equals(""))
								pstmt.setNull(jj++, Types.VARCHAR);
						else
								pstmt.setString(jj++, cut_description);
						if(cut_type.equals(""))
								pstmt.setNull(jj++, Types.INTEGER);
						else
								pstmt.setString(jj++, cut_type);
						if(permit_num.equals(""))
								pstmt.setNull(jj++, Types.INTEGER);
						else
								pstmt.setString(jj++, permit_num);
						if(address_id.equals(""))
								pstmt.setNull(jj++, Types.INTEGER);
						else
								pstmt.setString(jj++, address_id);			
						if(depth.equals(""))	
								pstmt.setNull(jj++, Types.INTEGER);
						else				
								pstmt.setString(jj++, depth);
						if(width.equals(""))	
								pstmt.setNull(jj++, Types.INTEGER);
						else				
								pstmt.setString(jj++, width);
						if(length.equals(""))	
								pstmt.setNull(jj++, Types.INTEGER);
						else				
								pstmt.setString(jj++, length);
						if(status.equals(""))
								pstmt.setNull(jj++, Types.INTEGER);
						else						
								pstmt.setString(jj++, status);
						if(utility_type_id.equals(""))
								pstmt.setNull(jj++, Types.INTEGER);
						else						
								pstmt.setString(jj++, utility_type_id);			
				}
				catch(Exception ex){
						msg += ex;
						logger.error(msg);
				}
				return msg;
		}
	
		/**
		 * update the record in the database
		 */
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
				qq = "update excavcuts set ";
				
				qq += "cut_description = ?, ";
				qq += "cut_type = ?,"; 
				qq += "permit_num =?,"; 
				qq += "address_id =?,";
				qq += "depth = ?,";
				qq += "width = ?,";		
				qq += "length = ?,"; 
				qq += "status = ?,"; 
				qq += "utility_type_id = ? ";
				qq += " where id = ? ";
				logger.debug(qq);
				try {
						//
						pstmt = con.prepareStatement(qq);
						setFields(pstmt);
						pstmt.setString(10, id);
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
		/**
		 * retreive the record from the database
		 * @return string if an error occurs
		 */
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
				qq = "select c.id, " +                         // 1
						"c.cut_description, " +
						"c.cut_type, " +
						"c.permit_num, " +
						"c.address_id, " +
						"c.depth,"+     				
						"c.width, " +
						"c.length, " + 
						"c.status, " + //				
						"c.utility_type_id, " +
						"u.name, "+
						" a.address, "+
						" a.loc_lat, "+
						" a.loc_long "+
						" from excavcuts c "+
						" left join utility_types u on c.utility_type_id=u.id "+
						" left join addresses a on a.id=c.address_id "+
						" where c.id=?";
				logger.debug(qq);
				try {
						pstmt = con.prepareStatement(qq);
						pstmt.setString(1, id);
						rs = pstmt.executeQuery();
						//
						if(rs.next()){
								setValues(
													rs.getString(1),
													rs.getString(2),
													rs.getString(3),
													rs.getString(4),
													rs.getString(5),
													rs.getString(6),
													rs.getString(7),
													rs.getString(8),
													rs.getString(9),
													rs.getString(10),
													rs.getString(11),
													rs.getString(12),
													rs.getString(13),
													rs.getString(14));
						}
						else{
								msg = "No record found for "+id;
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
		//
		public String doDelete(){
		
				String msg = "";
				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;		
				if(id.equals("")){
						msg = "Need to set ticket id ";
						return msg;
				}
				//
				String qq = "delete from excavcuts where id=?";
				con = Helper.getConnection();
				if(con == null){
						msg = "Could not connect to Database ";
						logger.error(msg);
						return msg;
				}
				logger.debug(qq);				
				try {
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
	
}






















































