/**
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
package permit;

import java.sql.*;
import java.util.*;
import javax.naming.directory.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class PermitList {

    String id="", which_date="date", date_from="", date_to="", reviewer_id="",
				status="",company_contact_id="", bond_id="", invoice_id="", permit_type="", company_id="", contact_id="", permit_num="", insurance_id="";
		String limit = "limit 20", sort_by="p.id DESC ";
		String searchStr = "";
		boolean noInvoice = false;
		static final long serialVersionUID = 111L;	
		static Logger logger = LogManager.getLogger(PermitList.class);
		List<Permit> permits = null;
    public PermitList(){
    }
    public PermitList(String val){
				setCompany_contact_id(val);
    }	
    public void setId(String val){
				if(val != null){
						id = val;
				}
    }
    public void setPermit_num(String val){
				if(val != null){
						permit_num = val;
				}
    }	
		public void setCompany_contact_id(String val){ 
				if(val != null){
						company_contact_id = val;
				}
    }
		public void setCompany_id(String val){ 
				if(val != null){
						company_id = val;
				}
    }
		public void setContact_id(String val){ 
				if(val != null){
						contact_id = val;
				}
    }	
    public void setBond_id(String val){
				if(val != null)
						bond_id = val;
    }
    public void setInsurance_id(String val){
				if(val != null)
						insurance_id = val;
    }		
    public void setInvoice_id(String val){
				if(val != null)
						invoice_id = val;
    }	
    public void setStatus(String val){
				if(val != null && !val.equals("-1")){
						status = val;
				}
    }
		public void setPermit_type(String val){
				if(val != null && !val.equals("-1")){
						permit_type = val;
				}
    }	
		public void setReviewer_id(String val){
				if(val != null && !val.equals("-1")){
						reviewer_id = val;
				}
    }
		public void setDate_from(String val){
				if(val != null){		
						date_from = val;
				}
    }
		public void setDate_to(String val){
				if(val != null){		
						date_to = val;
				}
    }
		public void setWhich_date(String val){
				if(val != null){		
						which_date = val;
				}
    }
		public void setNoLimit(){
				limit = "";
		}
		public void setNoInvoice(){
				noInvoice = true;
		}
		public void setSortBy(String val){
				if(val != null)
						sort_by =val;
		}
    //
    // getters
    //
    public String getId(){
				return id;
    }
    public String getPermit_num(){
				return permit_num;
    }	
    public String getCompany_id(){
				return company_id;
    }
    public String getContact_id(){
				return contact_id;
    }	
    public String getCompany_Contact_id(){
				return company_contact_id;
    }	
		public String getDate_from(){
				return date_from ;
    }
		public String getDate_to(){
				return date_to ;
    }
		public String getWhich_date(){
				return which_date ;
    }
		public String getStatus(){
				if(status.equals("")){
						return "-1";
				}
				return status;
    }	
		public String getReviewer_id(){
				if(reviewer_id.equals("")){
						return "-1";
				}
				return	reviewer_id;
		}
		public String getBond_id(){
				return bond_id;
		}
		public String getInsurance_id(){
				return insurance_id;
		}		
		public String getInvoice_id(){

				return	invoice_id;
		}
		public String getPermit_type(){
				if(permit_type.equals("")){
						return "-1";
				}
				return	permit_type;
		}
		public String getSort_by(){
				return sort_by;
    }
		public List<Permit> getPermits(){
				return permits;
		}
		
		String find(){
				String msg = "";
				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				boolean ownTbl = false;
				//
				String qq = "", qw="";
				qq = "select p.id, " +           // 1
						"p.permit_num, " +
						"p.status, " +
						"p.project, " +
						"p.reviewer_id, " +
						"date_format(p.start_date,'%m/%d/%Y'), " + 
						"date_format(p.date,'%m/%d/%Y'), " + 
						"p.fee, " + 
						"p.bond_id, " +
						"p.company_contact_id, "+
						"p.notes, " + 
						"p.invoice_id, " + 
						"p.permit_type,  " +
						"u.empid,u.fullname,u.role,u.active,p.insurance_id "+
						"";			
				String qf = "from excavpermits p "+
						" left join company_contacts cc on p.company_contact_id = cc.id "+
						" left join users u on u.id=p.reviewer_id ";			
				if(noInvoice){
						if(!qw.equals("")) qw += " and ";
						qw += " p.invoice_id is null ";
				}
				if(!which_date.equals("")){
						if(!date_from.equals("")){
								if(!qw.equals("")) qw += " and ";
								qw += which_date+">= str_to_date('"+date_from+"','%m/%d/%Y')";
						}
						if(!date_to.equals("")){
								if(!qw.equals("")) qw += " and ";
								qw += which_date+"<= str_to_date('"+date_to+"','%m/%d/%Y')";
						}
				}

				if(!id.equals("")){
						if(!qw.equals("")) qw += " and ";
						qw += " p.id = ? ";
				}
				if(!permit_num.equals("")){
						if(!qw.equals("")) qw += " and ";
						qw += " p.permit_num = ? ";
				}		
				if(!company_id.equals("")){
						if(!qw.equals("")) qw += " and ";
						qw += " cc.company_id = ? ";
				}
				if(!contact_id.equals("")){
						if(!qw.equals("")) qw += " and ";
						qw += " cc.contact_id = ? ";
				}		
				if(!status.equals("")){
						if(!qw.equals("")) qw += " and ";
						qw += " p.status = ? ";
				}
				if(!permit_type.equals("")){
						if(!qw.equals("")) qw += " and ";
						qw += " p.permit_type = ? ";
				}		
				if(!company_contact_id.equals("")){
						if(!qw.equals("")) qw += " and ";
						qw += " p.company_contact_id = ? ";
				}		
				if(!bond_id.equals("")){
						if(!qw.equals("")) qw += " and ";
						qw += " p.bond_id = ? ";
				}
				if(!reviewer_id.equals("")){
						if(!qw.equals("")) qw += " and ";
						qw += " p.reviewer_id = ? ";
				}
				if(!invoice_id.equals("")){
						if(!qw.equals("")) qw += " and ";
						qw += " p.invoice_id = ? ";
				}
				if(!insurance_id.equals("")){
						if(!qw.equals("")) qw += " and ";
						qw += " p.insurance_id = ? ";
				}				
				qq += qf;
				if(!qw.equals("")){
						qq += " where "+qw;
				}
				if(!sort_by.equals("")){
						qq += " order by "+sort_by;
				}
				qq += " "+limit;
				try {
						con = Helper.getConnection();
						if(con == null){
								msg = "Could not connect to Database ";
								logger.error(msg);
								return msg;
						}
						logger.debug(qq);
						pstmt = con.prepareStatement(qq);
						int jj=1;
						if(!id.equals("")){
								pstmt.setString(jj++, id);
						}
						if(!permit_num.equals("")){
								pstmt.setString(jj++, permit_num);
						}			
						if(!company_id.equals("")){
								pstmt.setString(jj++, company_id);
						}
						if(!contact_id.equals("")){
								pstmt.setString(jj++, contact_id);
						}			
						if(!status.equals("")){
								pstmt.setString(jj++, status);
						}
						if(!permit_type.equals("")){
								pstmt.setString(jj++, permit_type);
						}			
						if(!company_contact_id.equals("")){
								pstmt.setString(jj++, company_contact_id);
						}			
						if(!bond_id.equals("")){
								pstmt.setString(jj++, bond_id);
						}			
						if(!reviewer_id.equals("")){
								pstmt.setString(jj++, reviewer_id);
						}
						if(!invoice_id.equals("")){
								pstmt.setString(jj++, invoice_id);
						}
						if(!insurance_id.equals("")){
								pstmt.setString(jj++, insurance_id);
						}						
						rs = pstmt.executeQuery();
						permits = new ArrayList<Permit>();
						while(rs.next()){
								Permit one = new Permit(
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
																				rs.getString(14),
																				rs.getString(15),
																				rs.getString(16),
																				rs.getString(17),
																				rs.getString(18)
																				);
								permits.add(one);
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
		 * this is needed when not a lot of data is needed, such as
		 * auto complete service
		 */
		String findAbbrevList(){
				String msg = "";
				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				String qq = "", qw="";
				qq = "select p.id, " +           // 1
						"p.permit_num, " +
						"p.project " +
						"";			
				String qf = "from excavpermits p left join company_contacts cc on p.company_contact_id = cc.id ";
				if(noInvoice){
						if(!qw.equals("")) qw += " and ";
						qw += " p.invoice_id is null ";
				}
				if(!which_date.equals("")){
						if(!date_from.equals("")){
								if(!qw.equals("")) qw += " and ";
								qw += which_date+">= str_to_date('"+date_from+"','%m/%d/%Y')";
						}
						if(!date_to.equals("")){
								if(!qw.equals("")) qw += " and ";
								qw += which_date+"<= str_to_date('"+date_to+"','%m/%d/%Y')";
						}
				}

				if(!id.equals("")){
						if(!qw.equals("")) qw += " and ";
						qw += " p.id = ? ";
				}
				if(!permit_num.equals("")){
						if(!qw.equals("")) qw += " and ";
						qw += " p.permit_num like ? ";
				}		
				if(!company_id.equals("")){
						if(!qw.equals("")) qw += " and ";
						qw += " cc.company_id = ? ";
				}
				if(!contact_id.equals("")){
						if(!qw.equals("")) qw += " and ";
						qw += " cc.contact_id = ? ";
				}		
				if(!status.equals("")){
						if(!qw.equals("")) qw += " and ";
						qw += " p.status = ? ";
				}
				if(!permit_type.equals("")){
						if(!qw.equals("")) qw += " and ";
						qw += " p.permit_type = ? ";
				}		
				if(!company_contact_id.equals("")){
						if(!qw.equals("")) qw += " and ";
						qw += " p.company_contact_id = ? ";
				}		
				if(!bond_id.equals("")){
						if(!qw.equals("")) qw += " and ";
						qw += " p.bond_id = ? ";
				}
				if(!reviewer_id.equals("")){
						if(!qw.equals("")) qw += " and ";
						qw += " p.reviewer_id = ? ";
				}
				if(!invoice_id.equals("")){
						if(!qw.equals("")) qw += " and ";
						qw += " p.invoice_id = ? ";
				}		
				qq += qf;
				if(!qw.equals("")){
						qq += " where "+qw;
				}
				if(!sort_by.equals("")){
						qq += " order by "+sort_by;
				}
				qq += " "+limit;
				try {
						con = Helper.getConnection();
						if(con == null){
								msg = "Could not connect to Database ";
								logger.error(msg);
								return msg;
						}
						logger.debug(qq);
						pstmt = con.prepareStatement(qq);
						int jj=1;
						if(!id.equals("")){
								pstmt.setString(jj++, id);
						}
						if(!permit_num.equals("")){
								pstmt.setString(jj++, permit_num+"%");
						}			
						if(!company_id.equals("")){
								pstmt.setString(jj++, company_id);
						}
						if(!contact_id.equals("")){
								pstmt.setString(jj++, contact_id);
						}			
						if(!status.equals("")){
								pstmt.setString(jj++, status);
						}
						if(!permit_type.equals("")){
								pstmt.setString(jj++, permit_type);
						}			
						if(!company_contact_id.equals("")){
								pstmt.setString(jj++, company_contact_id);
						}			
						if(!bond_id.equals("")){
								pstmt.setString(jj++, bond_id);
						}			
						if(!reviewer_id.equals("")){
								pstmt.setString(jj++, reviewer_id);
						}
						if(!invoice_id.equals("")){
								pstmt.setString(jj++, invoice_id);
						}					
						rs = pstmt.executeQuery();
						permits = new ArrayList<Permit>();
						while(rs.next()){
								Permit one = new Permit(
																				rs.getString(1),
																				rs.getString(2),
																				rs.getString(3)
																				);
								permits.add(one);
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
	
}
