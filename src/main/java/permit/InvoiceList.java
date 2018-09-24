/**
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
package permit;
import java.util.ArrayList;
import java.util.List;
import java.text.*;
import java.sql.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class InvoiceList{

		static Logger logger = LogManager.getLogger(InvoiceList.class);
		static final long serialVersionUID = 301L;
		static SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		List<Invoice> invoices = null;
    String contact_id="", id="", invoice_num="", company_id="", status="",date_from="",date_to="", which_date="i.date", sort_by="i.id DESC", limit="20";
		boolean unpaidStatus = false;
    public InvoiceList(){
    }
    public InvoiceList(String val){
				setContact_id(val);
    }
    public InvoiceList(String val, String val2){
				setContact_id(val);
				setCompany_id(val2);
    }
		public void setId(String val){
				if(val != null)
						id = val;
		}
		public void setInvoice_num(String val){
				if(val != null)
						invoice_num = val;
		}		
		public void setContact_id(String val){
				if(val != null)
						contact_id = val;
		}
		public void setCompany_id(String val){
				if(val != null)
						company_id = val;
		}
		public void setStatus(String val){
				if(val != null && !val.equals("-1"))
						status = val;
		}
		public void setUnpaidStatus(){
				unpaidStatus = true;
		}		
		public void setDate_from(String val){
				if(val != null)
						date_from = val;
		}
	
		public void setDate_to(String val){
				if(val != null)
						date_to = val;
		}
		public void setWhich_date(String val){
				if(val != null)
						which_date = val;
		}
		public void setSort_by(String val){
				if(val != null)
						sort_by = val;
		}
		//
		public String getId(){
				return id ;
		}	
		public String getContact_id(){
				return contact_id ;
		}
		public String getCompany_id(){
				return company_id ;
		}
	
		public String getStatus(){
				if(status.equals("")){
						return "-1";
				}
				return status ;
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
		public String getSort_by(){
				return sort_by ;
		}
		public String getInvoice_num(){
				return invoice_num ;
		}		
		public void setNoLimit(){
				limit = "";
		}
		public List<Invoice> getInvoices(){
				return invoices;
		}
    /**
		 * find all invoices that match certain criterea from the database 
		 * @return errors if any
		 */
		public String find(){
		
				String msg="", qw="";
				String qq = "select i.* from invoices i left join company_contacts cc on i.company_contact_id = cc.id ";
				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				if(!id.equals("")){
						if(!qw.equals("")) qw += " and ";
						qw += " i.id = ? ";
				}
				if(!invoice_num.equals("")){
						if(!qw.equals("")) qw += " and ";
						qw += " i.invoice_num = ? ";
				}				
				if(!contact_id.equals("")){
						if(!qw.equals("")) qw += " and ";
						qw += " cc.contact_id = ? ";
				}
				if(!company_id.equals("")){
						if(!qw.equals("")) qw += " and ";
						qw += " cc.company_id = ? ";
				}
				if(!status.equals("")){
						if(!qw.equals("")) qw += " and ";
						qw += " i.status = ? ";
				}
				else if(unpaidStatus){
						if(!qw.equals("")) qw += " and ";						
						qw += " (i.status = 'Billed' or i.status = 'Pending')";
				}
				if(!date_from.equals("")){
						if(!qw.equals("")) qw += " and ";
						qw += which_date+" >= ? ";
				}
				if(!date_to.equals("")){
						if(!qw.equals("")) qw += " and ";
						qw += which_date+" <= ? ";
				}		
				if(!qw.equals("")){
						qq += " where "+qw;
				}
				if(!sort_by.equals("")){
						qq += " order by "+sort_by;
				}
				if(!limit.equals("")){
						qq += " limit "+limit;
				}
				con = Helper.getConnection();
				if(con == null){
						msg = "Could not connect ";
						return msg;
				}		
				try{
						logger.debug(qq);
						pstmt = con.prepareStatement(qq);
						int jj=1;
						if(!id.equals("")){
								pstmt.setString(jj++, id);
						}
						if(!invoice_num.equals("")){
								pstmt.setString(jj++, invoice_num);
						}						
						if(!contact_id.equals("")){
								pstmt.setString(jj++, contact_id);
						}
						if(!company_id.equals("")){
								pstmt.setString(jj++, company_id);
						}
						if(!status.equals("")){
								pstmt.setString(jj++, status);
						}			
						if(!date_from.equals("")){
								pstmt.setDate(jj++, new java.sql.Date(dateFormat.parse(date_from).getTime()));		
						}
						if(!date_to.equals("")){
								pstmt.setDate(jj++, new java.sql.Date(dateFormat.parse(date_to).getTime()));		
						}					
						invoices = new ArrayList<Invoice>();
				
						rs = pstmt.executeQuery();
						while(rs.next()){
								Invoice one =
										new Invoice(rs.getString(1),
																rs.getString(2),
																rs.getString(3),
																rs.getString(4),
																rs.getString(5),
																rs.getString(6),
																rs.getString(7),
																rs.getString(8));
								invoices.add(one);
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
