/**
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
package permit;
import java.sql.*;
import java.text.*;
import java.util.List;
import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger; 

public class Receipt implements java.io.Serializable{

    String id="", date="", invoice_id="", payment_type="", check_num="",
				paid_by = "", voided="", amount_paid="",
				user_id="";
		String permit_id=""; // needed when we come from a permit
		static final int page_size = 30, first_page = 20;
		static final long serialVersionUID = 225L;	
		static Logger logger = LogManager.getLogger(Receipt.class);
		static SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Invoice invoice = null;
		User user = null;
		List<Page> pages = null;
    public Receipt(){

    }
    public Receipt(String val){
				setId(val);
    }
    public Receipt(String val,
									 String val2,
									 String val3,
									 String val4,
									 String val5,
									 String val6,
									 String val7,
									 String val8,
									 String val9
									 ){
				setValues(val, val2, val3, val4, val5, val6, val7, val8, val9);
    }
		void setValues(
									 String val,
									 String val2,
									 String val3,
									 String val4,
									 String val5,
									 String val6,
									 String val7,
									 String val8,
									 String val9
									 ){
				setId(val);
				setInvoice_id(val2);
				setDate(val3);
				setPayment_type(val4);
				setCheck_num(val5);
				setPaid_by(val6);
				setUser_id(val7);
				setVoided(val8);
				setAmount_paid(val9);
		}
		//
		// setters
		//
		public void setId(String val){
				if(val != null)
						id = val;
    }
		public void setPayment_type(String val){
				if(val != null && !val.equals("-1"))
						payment_type = val;
    }
		public void setDate(String val){
				if(val != null)
						date = val;
    }
		public void setInvoice_id(String val){
				if(val != null)
						invoice_id = val;
    }
		public void setCheck_num(String val){
				if(val != null)
						check_num = val;
    }
		public void setPaid_by(String val){
				if(val != null)
						paid_by = val;
    }
		public void setVoided(String val){
				if(val != null)
						voided = val;
    }
		public void setUser_id(String val){
				if(val != null)
						user_id = val;
    }
		public void setAmount_paid(String val){
				if(val != null)
						amount_paid = val;
    }
	
    //
    // getters
    //
		public String getId(){
				return id;
    }	
    public String getPayment_type(){
				if(payment_type.equals("") && id.equals("")){
						payment_type = "Check";
				}
				return payment_type;
    }
		public String getDate(){
				if(id.equals("") && date.equals("")){
						date = Helper.getToday();
				}
				return date;
    }	
    public String getInvoice_id(){
				return invoice_id;
    }
    public String getInvoice_num(){
				getInvoice();
				if(invoice != null){
						return invoice.getInvoice_num();
				}
				return invoice_id;
    }		
		public String getCheck_num(){
				return check_num;
    }	
    public String getPaid_by(){
				return paid_by;
    }
    public String getAmount_paid(){
				return amount_paid;
    }	

		public String toString(){
				return id;
		}
		//
		public Invoice getInvoice(){
				if(invoice == null && !invoice_id.equals("")){
						Invoice one = new Invoice(invoice_id);
						String back = one.doSelect();
						if(back.equals("")){
								invoice = one;
						}
				}
				return invoice;
		}
		public User getUser(){
				if(user == null && !user_id.equals("")){
						User one = new User(user_id);
						String back = one.doSelect();
						if(back.equals("")){
								user = one;
						}
				}
				return user;
		}
		public List<Page> getPages(){
				if(pages == null){
						// createPages();
				}
				return pages;
		}
		public boolean equals(Object gg){
				boolean match = false;
				if (gg != null && gg instanceof Receipt){
						match = id.equals(((Receipt)gg).id);
				}
				return match;
		}
		public int hashCode(){
				int code = 0;
				try{
						code = Integer.parseInt(id);
				}catch(Exception ex){};
				return code;
		}		
    //
		public String doSelect(){
		
				String msg="";
		
				String qq = "select id,invoice_id,date_format(date,'%m/%d/%Y'),payment_type,check_num,paid_by,user_id,voided,amount_paid from receipts where id=?";
				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				logger.debug(qq);
				try{
						con = Helper.getConnection();
						if(con == null){
								msg = "Could not connect ";
						}
						else{
								pstmt = con.prepareStatement(qq);
								pstmt.setString(1,id);
								rs = pstmt.executeQuery();
								if(rs.next()){
										setValues(rs.getString(1),
															rs.getString(2),
															rs.getString(3),
															rs.getString(4),
															rs.getString(5),
															rs.getString(6),
															rs.getString(7),
															rs.getString(8),
															rs.getString(9));
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
		public String doSave(){
		
				String msg = "";
				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				String qq = "insert into receipts values (0,"+
						"?,?,?,?,?,?,?,?)";
				con = Helper.getConnection();
				if(con == null){
						msg = "Could not connect to Database ";
						logger.error(msg);
						return msg;
				}
				try {
						logger.debug(qq);			
						pstmt = con.prepareStatement(qq);
						msg += setFields(pstmt);
						pstmt.executeUpdate();
						qq = "select LAST_INSERT_ID() ";
						logger.debug(qq);
						pstmt = con.prepareStatement(qq);			
						rs = pstmt.executeQuery();
						if(rs.next()){
								id = rs.getString(1);
						}
						getInvoice();
						if(invoice != null){
								invoice.setStatus("Paid");
								msg += invoice.doUpdate();
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
		public String setFields(PreparedStatement pstmt){

				String msg = "";
				String today = Helper.getToday();
				int jj = 1;
				try{
						pstmt.setString(jj++, invoice_id);
						if(date.equals(""))
								pstmt.setNull(jj++, Types.VARCHAR);
						else
								pstmt.setDate(jj++, new java.sql.Date(dateFormat.parse(date).getTime()));					
						if(payment_type.equals(""))
								pstmt.setNull(jj++, Types.VARCHAR);
						else
								pstmt.setString(jj++, payment_type);

						if(check_num.equals(""))	
								pstmt.setNull(jj++, Types.VARCHAR);
						else				
								pstmt.setString(jj++, check_num);
						if(paid_by.equals(""))
								pstmt.setNull(jj++, Types.VARCHAR);
						else
								pstmt.setString(jj++, paid_by);
						if(user_id.equals(""))
								pstmt.setNull(jj++, Types.INTEGER);
						else
								pstmt.setString(jj++, user_id);			
						if(voided.equals(""))
								pstmt.setNull(jj++, Types.CHAR);
						else
								pstmt.setString(jj++, "y");
						if(amount_paid.equals(""))
								pstmt.setNull(jj++, Types.VARCHAR);
						else
								pstmt.setString(jj++, amount_paid);				
				}
				catch(Exception ex){
						msg += ex;
						logger.error(msg);
				}
				return msg;
		}
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
				try {
						//
						qq = "update receipts set ";
						qq += "invoice_id = ?, ";
						qq += "date = ?,"; 
						qq += "payment_type =?,"; 
						qq += "check_num =?,";
						qq += "paid_by = ?,";
						qq += "user_id = ?, ";
						qq += "voided = ?, ";
						qq += "amount_paid=? ";
						qq += "where id=? ";
						logger.debug(qq);
						pstmt = con.prepareStatement(qq);
						setFields(pstmt);
						pstmt.setString(9, id);
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
		public String doDelete(){
		
				String msg = "";
				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;		
				if(id.equals("")){
						msg = "company id not set";
						return msg;
				}
				//
				String qq = "delete from receipts where id=?";
				con = Helper.getConnection();
				if(con == null){
						msg = "Could not connect to Database ";
						logger.error(msg);
						return msg;
				}
				try {
						logger.debug(qq);
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
