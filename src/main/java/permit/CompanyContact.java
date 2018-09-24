/**
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
package permit;
import java.sql.*;
import javax.naming.*;
import javax.naming.directory.*;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CompanyContact{

    String id="", company_id="", contact_id="", user_id="";

		static final long serialVersionUID = 193L;	
		static Logger logger = LogManager.getLogger(CompanyContact.class);
		Company company = null;
		Contact contact = null;
		//
    public CompanyContact(){

    }
    public CompanyContact(String val){
				setId(val);
    }
    public CompanyContact(
													String val,
													String val2,
													String val3){
				setId(val);
				setCompany_id(val2);
				setContact_id(val3);
		}
    public CompanyContact(
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
													String val14,
													String val15,
													String val16,
													String val17,
													String val18,
													String val19,
													String val20,
													String val21,
													String val22,
													String val23,
													String val24				 
													){				 
				setFields(val, val2, val3, val4, val5, val6, val7, val8, val9, val10,
									val11, val12, val13, val14, val15, val16, val17, val18,
									val19, val20, val21, val22, val23, val24
									);
    }
		void setFields(String val,
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
									 String val14,
									 String val15,
									 String val16,
									 String val17,
									 String val18,
									 String val19,
									 String val20,
									 String val21,
									 String val22,
									 String val23,
									 String val24				   
									 ){
				setId(val);
				setCompany_id(val2);
				setContact_id(val3);
				if(!company_id.equals("")){
						company = new Company(company_id, val4, val5, val6, val7, val8, val9,val10, val11);
				}
				if(!contact_id.equals("")){
						contact = new Contact(contact_id, val12, val13, val14, val15, val16, val17,val18, val19, val20, val21, val22, val23, val24);
				}		
    }
	
		//
		public void setId(String val){
				if(val != null)		
						id = val;
    }
		public void setUser_id(String val){
				if(val != null)		
						user_id = val;
    }	
    public void setCompany_id(String val){
				if(val != null)
						company_id = val;
    }
    public void setContact_id(String val){
				if(val != null)
						contact_id = val;
    }
    //
    // getters
    //
		public String getId(){
				return id;
    }
		public String getCompany_id(){
				return company_id;
    }
		public String getContact_id(){	
				return contact_id;
		}
	
		public Company getCompany(){
				if(company == null && !company_id.equals("")){
						Company one = new Company(company_id);
						String back = one.doSelect();
						if(back.equals("")){
								company = one;
						}
				}
				return company;
		}
		public Contact getContact(){
				if(contact == null && !contact_id.equals("")){
						Contact one = new Contact(contact_id);
						String back = one.doSelect();
						if(back.equals("")){
								contact = one;
						}
				}
				return contact;
		}

		String doSave(){
				//
				String msg = "";
				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				//
				String qq = "";
				if(company_id.equals("") && contact_id.equals("")){
						msg = "company or contact not set ";
						logger.error(msg);
						return msg;
				}
				qq = "insert into company_contacts values(0,?,?)";
				try {
						con = Helper.getConnection();
						if(con == null){
								msg = "Could not connect to Database ";
								logger.error(msg);
								return msg;
						}
						logger.debug(qq);			
						pstmt = con.prepareStatement(qq);
						msg = setFields(pstmt);
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
	
		String doUpdate(){
		
				String msg = "";
				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				//
				String qq = "";
				if(company_id.equals("") && contact_id.equals("")){
						msg = "company or contac  not set ";
						logger.error(msg);
						return msg;
				}
				if(id.equals("")){
						return doSave();
				}
				qq = "update company_contacts set company_id=?,contact_id=? where id=?";

				try {
						con = Helper.getConnection();
						if(con == null){
								msg = "Could not connect to Database ";
								logger.error(msg);
								return msg;
						}

						logger.debug(qq);			
						pstmt = con.prepareStatement(qq);
						msg = setFields(pstmt);
						pstmt.setString(3, id);
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
		String setFields(PreparedStatement pstmt){

				String msg = "";
				int jj=1;
				try{
						if(company_id.equals(""))
								pstmt.setNull(jj++, Types.VARCHAR);
						else
								pstmt.setString(jj++,company_id);
						if(contact_id.equals(""))
								pstmt.setNull(jj++, Types.VARCHAR);
						else
								pstmt.setString(jj++,contact_id);
				}
				catch(Exception ex){
						msg += ex;
						logger.error(msg);
				}
				return msg;
		}
		String doSelect(){
				String msg = "";
				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				//
				String qq = "";
				if(id.equals("")){
						msg = "  id not set ";
						logger.error(msg);
						return msg;
				}
				qq = "select cc.id, cc.company_id, cc.contact_id, "+
						" cp.name,cp.address,cp.city,cp.state,cp.zip,cp.phone,cp.website,cp.notes, "+
						" c.fname,c.lname,c.title,c.type_id,c.address,c.city,c.state,c.zip,"+
						" c.work_phone,c.cell_phone,c.fax,c.email,c.notes "+
						" from company_contacts cc "+
						" left join companies cp on cp.id = cc.company_id "+
						" left join contacts c on c.id = cc.contact_id "+
						" where cc.id=?";
				try {
						con = Helper.getConnection();
						if(con == null){
								msg = "Could not connect to Database ";
								logger.error(msg);
								return msg;
						}

						logger.debug(qq);			
						pstmt = con.prepareStatement(qq);
						pstmt.setString(1,id);
						rs = pstmt.executeQuery();
						if(rs.next()){
								setFields(rs.getString(1),
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
													rs.getString(14),
													rs.getString(15),
													rs.getString(16),
													rs.getString(17),
													rs.getString(18),
													rs.getString(19),
													rs.getString(20),
													rs.getString(21),
													rs.getString(22),
													rs.getString(23),
													rs.getString(24)						  
													);
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
		String doDelete(){
		
				String msg = "";
				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				//
				String qq = "";
				if(id.equals("")){
						msg = "Owner id not set ";
						logger.error(msg);
						return msg;
				}
				qq = "delete from company_contacts where id=?";
				try {
						con = Helper.getConnection();
						if(con == null){
								msg = "Could not connect to Database ";
								logger.error(msg);
								return msg;
						}
						logger.debug(qq);
						pstmt = con.prepareStatement(qq);
						pstmt.setString(1, id);
						pstmt.executeUpdate();
						id="";
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
