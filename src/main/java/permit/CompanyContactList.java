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

public class CompanyContactList{

		static Logger logger = LogManager.getLogger(CompanyContactList.class);
		static final long serialVersionUID = 203L;
		List<CompanyContact> company_contacts = null;
    String company_id="", contact_id="";
    public CompanyContactList(){
    }
    public CompanyContactList(String val){
				setCompany_id(val);
    }
    public CompanyContactList(String val, String val2){
				setCompany_id(val);
				setContact_id(val2);
    }	
		public void setCompany_id(String val){
				if(val != null)
						company_id = val;
		}
		public void setContact_id(String val){
				if(val != null)
						contact_id = val;
		}	
		public List<CompanyContact> getCompany_contacts(){
				return company_contacts;
		}
		public String find(){
		
				String msg="", qw="";
				String qq = "select cc.id, cc.company_id, cc.contact_id, "+
						" cp.name,cp.address,cp.city,cp.state,cp.zip,cp.phone,cp.website,cp.notes, "+
						" c.fname,c.lname,c.title,c.type_id,c.address,c.city,c.state,c.zip,"+
						" c.work_phone,c.cell_phone,c.fax,c.email,c.notes "+
						" from company_contacts cc "+
						" left join companies cp on cp.id = cc.company_id "+
						" left join contacts c on c.id = cc.contact_id ";
		
				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				if(!company_id.equals("")){
						if(!qw.equals("")) qw += " and ";
						qw += " cc.company_id = ? ";
				}
				if(!contact_id.equals("")){
						if(!qw.equals("")) qw += " and ";
						qw += " cc.contact_id = ? ";
				}		
				if(!qw.equals("")){
						qq += " where "+qw;
				}
				qq += " order by c.id desc ";
				con = Helper.getConnection();
				if(con == null){
						msg = "Could not connect ";
						return msg;
				}		
				try{
						logger.debug(qq);
						pstmt = con.prepareStatement(qq);
						int jj=1;
						if(!company_id.equals("")){
								pstmt.setString(jj++, company_id);
						}
						if(!contact_id.equals("")){
								pstmt.setString(jj++, contact_id);
						}			
						company_contacts = new ArrayList<CompanyContact>();
				
						rs = pstmt.executeQuery();
						while(rs.next()){
								CompanyContact one =
										new CompanyContact(rs.getString(1),
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
								company_contacts.add(one);
						}
				}
				catch(Exception ex){
						msg += " "+ex;
						logger.error(ex+" : "+qq);
				}
				finally{
						Helper.databaseDisconnect(con, pstmt, rs);
				}
				return msg;
		}
	
}
