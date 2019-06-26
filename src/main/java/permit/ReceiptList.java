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

public class ReceiptList{

		static Logger logger = LogManager.getLogger(ReceiptList.class);
		static final long serialVersionUID = 303L;
		static SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		List<Receipt> receipts = null;
    String invoice_id="", id="", date_from="",date_to="",
				payment_type="", company_id = "", contact_id="",
				which_date="r.date", sort_by="r.id DESC", limit="20";
		boolean voided = false;
    public ReceiptList(){
    }
    public ReceiptList(String val){
				setInvoice_id(val);
    }
		public void setId(String val){
				if(val != null)
						id = val;
		}
		public void setInvoice_id(String val){
				if(val != null)
						invoice_id = val;
		}	
		public void setContact_id(String val){
				if(val != null)
						contact_id = val;
		}
		public void setCompany_id(String val){
				if(val != null)
						company_id = val;
		}
		public void setPayment_type(String val){
				if(val != null && !val.equals("-1"))
						payment_type = val;
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
		public void setVoided(boolean val){
				voided = val;
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
	
		public String getPayment_type(){
				if(payment_type.equals("")){
						return "-1";
				}
				return payment_type ;
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
		public boolean getVoided(){
				return voided ;
		}	
		public String getSort_by(){
				return sort_by ;
		}
	
		public void setNoLimit(){
				limit = "";
		}
		public List<Receipt> getReceipts(){
				return receipts;
		}
    /**
		 * find all actions from the database
		 * @return errors if any
		 */
		public String find(){
		
				String msg="", qw="";
				String qq = "select r.id,r.invoice_id,date_format(r.date,'%m/%d/%Y'),r.payment_type,r.check_num,r.paid_by,r.user_id,r.voided,r.amount_paid from receipts r left join invoices i on i.id=r.invoice_id "+
						" left join company_contacts cc on i.company_contact_id = cc.id ";
				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				if(!id.equals("")){
						if(!qw.equals("")) qw += " and ";
						qw += " r.id = ? ";
				}
				if(!invoice_id.equals("")){
						if(!qw.equals("")) qw += " and ";
						qw += " r.invoice_id = ? ";
				}		
				if(!contact_id.equals("")){
						if(!qw.equals("")) qw += " and ";
						qw += " cc.contact_id = ? ";
				}
				if(!company_id.equals("")){
						if(!qw.equals("")) qw += " and ";
						qw += " cc.company_id = ? ";
				}
				if(!payment_type.equals("")){
						if(!qw.equals("")) qw += " and ";
						qw += " r.payment_type = ? ";
				}		
				if(!date_from.equals("")){
						if(!qw.equals("")) qw += " and ";
						qw += which_date+" >= ? ";
				}
				if(!date_to.equals("")){
						if(!qw.equals("")) qw += " and ";
						qw += which_date+" <= ? ";
				}
				if(voided){
						if(!qw.equals("")) qw += " and ";
						qw += " r.voided is not null ";
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
				logger.debug(qq);				
				try{
						pstmt = con.prepareStatement(qq);
						int jj=1;
						if(!id.equals("")){
								pstmt.setString(jj++, id);
						}
						if(!invoice_id.equals("")){
								pstmt.setString(jj++, invoice_id);
						}			
						if(!contact_id.equals("")){
								pstmt.setString(jj++, contact_id);
						}
						if(!company_id.equals("")){
								pstmt.setString(jj++, company_id);
						}
						if(!payment_type.equals("")){
								pstmt.setString(jj++, payment_type);
						}			
						if(!date_from.equals("")){
								pstmt.setDate(jj++, new java.sql.Date(dateFormat.parse(date_from).getTime()));		
						}
						if(!date_to.equals("")){
								pstmt.setDate(jj++, new java.sql.Date(dateFormat.parse(date_to).getTime()));		
						}					
						receipts = new ArrayList<Receipt>();
				
						rs = pstmt.executeQuery();
						while(rs.next()){
								Receipt one =
										new Receipt(rs.getString(1),
																rs.getString(2),
																rs.getString(3),
																rs.getString(4),
																rs.getString(5),
																rs.getString(6),
																rs.getString(7),
																rs.getString(8),
																rs.getString(9));
								if(!receipts.contains(one)){
										receipts.add(one);
								}
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
