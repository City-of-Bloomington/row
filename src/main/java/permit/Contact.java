/**
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
package permit;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Contact{

    String id="", fname="", lname="", title="", address="";
		String type_id="",work_phone="", cell_phone="", fax="",
				city="Bloomington", state = "IN", zip="4740",
				email = "", notes="";
		static final long serialVersionUID = 191L;	
		static Logger logger = LogManager.getLogger(Contact.class);
    String user_id = "";
		List<CompanyContact> company_contacts = null;
		List<Company> companies = null;
		Type type = null;
		List<Permit> permits = null;
		List<Invoice> invoices = null;
		List<Bond> bonds = null;
		CompanyContact company_contact = null;
		//
    public Contact(){

    }
    public Contact(String val){
				setId(val);
    }	
    public Contact(
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
									 String val15){				 
				setFields(val, val2, val3, val4, val5,
									val6, val7, val8, val9, val10,
									val11, val12, val13, val14, val15);
		
    }
    public Contact(
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
									 String val14){				 
				setFields(val, val2, val3, val4, val5,
									val6, val7, val8, val9, val10,
									val11, val12, val13, val14);
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
									 String val14){
				setId(val);
				setFname(val2);
				setLname(val3);
				setTitle(val4);
				setType_id(val5);
				setAddress(val6);
				setCity(val7);
				setState(val8);
				setZip(val9);
				setWork_phone(val10);
				setCell_phone(val11);
				setFax(val12);
				setEmail(val13);
				setNotes(val14);
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
									 String val15){
				setFields(val, val2, val3, val4, val5,
									val6, val7, val8, val9, val10,
									val11, val12, val13, val14);
				if(val15 != null && !type_id.equals("")){
						type = new Type(type_id, val15);
				}
    }
	
		//
		public void setId(String val){
				if(val != null)		
						id = val;
    }
    public void setFname(String val){
				if(val != null)
						fname = val.toUpperCase();
    }
    public void setLname(String val){
				if(val != null)
						lname = val.toUpperCase();
    }
		public void setTitle(String val){
				if(val != null)		
						title = val;
    }	

		public void setType_id(String val){
				if(val != null && !val.equals("-1"))
						type_id = val;
    }
		public void setAddress(String val){
				if(val != null)		
						address = val;
    }
		public void setWork_phone(String val){
				if(val != null)		
						work_phone = val;
    }
		public void setCell_phone(String val){
				if(val != null)		
						cell_phone = val;
    }
		public void setFax(String val){
				if(val != null)		
						fax = val;
    }
		public void setEmail(String val){
				if(val != null)		
						email = val;
    }
		public void setCity(String val){
				if(val != null)		
						city = val.toUpperCase();
    }
		public void setState(String val){
				if(val != null)		
						state = val;
    }
		public void setZip(String val){
				if(val != null)		
						zip = val;
    }
		public void setUser_id(String val){
				if(val != null)		
						user_id = val;
    }
		public void setNotes(String val){
				if(val != null)		
						notes = val;
    }	
    //
    // getters
    //
    public String getFname(){
				return fname;
    }
    public String getLname(){
				return lname;
    }
		public String getFullName(){
				String str = lname.trim();
				if(!fname.trim().equals("")){
						if(!str.equals("")) str += ", ";
						str += fname.trim();
				}
				return Helper.initCap(str);
    }
		public String getId(){
				return id;
    }
		public String getType_id(){
				if(type_id.equals("")){
						return "-1";
				}
				return type_id;
    }
		public Type getType(){
				if(type == null && !type_id.equals("")){
						Type one = new Type(type_id, null, "contact_types");
						String back = one.doSelect();
						if(back.equals("")){
								type = one;
						}
				}
				return type;
    }	
		public String getWork_phone(){	
				return work_phone;
		}
		public String getCell_phone(){	
				return cell_phone;
		}
		public String getFax(){	
				return fax;
		}
		public String getEmail(){	
				return email;
		}
		public String getAddress(){	
				return address;
		}
		public String getCity(){
				return city ;
		}
		public String getTitle(){
				return title ;
		}	
		public String getState(){
				return state ;
		}
		public String getZip(){	
				return zip ;
		}
		public String getNotes(){	
				return notes;
		}	
		public String getPhones(){
				String ret = "";
				if(!work_phone.equals("")){
						ret += "w:"+work_phone;
				}
				if(!cell_phone.equals("")){
						if(!ret.equals("")) ret += ", ";
						ret += "c:"+cell_phone;
				}
				if(!fax.equals("")){
						if(!ret.equals("")) ret += ", ";
						ret += "f:"+fax;
				}		
				return ret ;
		}	

		public String getCityStateZip(){
				String str = Helper.initCap(city);
				if(!state.equals("")){
						if(!str.equals("")) str += ", ";
						str += state;
				}
				if(!zip.equals("")){
						if(!str.equals("")) str += " ";
						str += zip;
				}
				return str;
    }
		public List<CompanyContact> getCompany_contacts(){
				if(!id.equals("") && company_contacts == null){
						CompanyContactList ccl = new CompanyContactList(null, id);
						String back = ccl.find();
						if(back.equals("")){
								List<CompanyContact> list = ccl.getCompany_contacts();
								if(list != null && list.size() > 0){
										company_contacts = list;
								}
						}
				}
				return company_contacts;
		}
		public CompanyContact getCompany_contact(){
				if(company_contact == null && hasCompany_contacts()){
						company_contact = company_contacts.get(0);
				}
				return company_contact;
		}
		public List<Company> getCompanies(){
				if(!id.equals("") && companies == null){
						getCompany_contacts();
						if(company_contacts != null){
								companies = new ArrayList<Company>();
								for(int j=0;j<company_contacts.size();j++){
										Company one = company_contacts.get(j).getCompany();
										if(one != null){
												companies.add(one);
										}
								}
						}
				}
				return companies;
		}
		public boolean hasAffiliations(){
				getCompanies();
				return (companies != null && companies.size() > 0);
		}
		//
		// we need this because property owners do not need affiliation
		//
		public boolean isPropertyOwner(){
				return type_id.equals("3");
		}
		public List<Invoice> getInvoices(){
				logger.debug(" contact invoices ");
				if(!id.equals("")){
						InvoiceList ccl = new InvoiceList(id);
						ccl.setNoLimit();
						String back = ccl.find();
						if(back.equals("")){
								List<Invoice> list = ccl.getInvoices();
								if(list != null && list.size() > 0){
										invoices = list;
								}
						}
						else{
								logger.debug(" contact invoices "+back);
						}
				}
				return invoices;
		}
		public List<Permit> getPermits(){
				logger.debug(" contact permits ");
				if(!id.equals("")){
						PermitList ccl = new PermitList();
						ccl.setContact_id(id);
						ccl.setNoLimit();
						String back = ccl.find();
						if(back.equals("")){
								List<Permit> list = ccl.getPermits();
								if(list != null && list.size() > 0){
										permits = list;
								}
						}
						else{
								logger.error(" contact permits "+back);
						}
				}
				return permits;
		}
		public List<Bond> getBonds(){
				logger.debug(" contact bonds ");
				if(!id.equals("")){
						BondList ccl = new BondList();
						ccl.setContact_id(id);
						ccl.setNoLimit();
						String back = ccl.find();
						if(back.equals("")){
								List<Bond> list = ccl.getBonds();
								if(list != null && list.size() > 0){
										bonds = list;
								}
						}
						else{
								logger.debug(" contact bonds "+back);
						}
				}
				return bonds;
		}		
		public boolean hasPermits(){
				getPermits();
				return permits != null && permits.size() > 0;
		}
		public boolean hasInvoices(){
				getInvoices();
				return (invoices != null && invoices.size() > 0);
		}
		public boolean hasBonds(){
				getBonds();
				return bonds != null && bonds.size() > 0;
		}
		public boolean hasCompany_contacts(){
				getCompany_contacts();
				return company_contacts != null && company_contacts.size() > 0;
		}
    //
	
		public String toString(){
				return getFullName();
		}
		/**
		 * check if this object has data
		 * last name or first name must be entered and street name or pobox too
		 * @return boolean
		 */
		public boolean hasData(){
				return (!lname.equals("") || !fname.equals("")) &&
						(!address.equals(""));
		}
		public boolean equals(Object gg){
				boolean match = false;
				if (gg != null && gg instanceof Contact){
						match = id.equals(((Contact)gg).id);
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
		String doSave(){
				String msg = "";
				Connection con = null;
				PreparedStatement pstmt = null, pstmt2=null;
				ResultSet rs = null;
				//
				String qq = "";
				if(fname.equals("") && lname.equals("")){
						msg = "Contact name not set ";
						logger.error(msg);
						return msg;
				}
				qq = "insert into contacts values(0,?,?,?,?,"+
						"?,?,?,?,?,"+
						"?,?,?,?)";

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
				if(isPropertyOwner()){
						CompanyContact cct = new CompanyContact(null, null, id);
						msg = cct.doSave();
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
				if(fname.equals("") && lname.equals("")){
						msg = "Owner name not set ";
						logger.error(msg);
						return msg;
				}
				if(id.equals("")){
						return doSave();
				}
				qq = "update contacts set fname=?,lname=?,title=?,type_id=?,address=?,city=?,state=?,zip=?,work_phone=?,cell_phone=?,fax=?,email=?,notes=? where id=? ";
				con = Helper.getConnection();
				if(con == null){
						msg = "Could not connect to Database ";
						logger.error(msg);
						return msg;
				}
				try {
						logger.debug(qq);			
						pstmt = con.prepareStatement(qq);
						msg = setFields(pstmt);
						pstmt.setString(14, id);
						pstmt.executeUpdate();
				}
				catch (Exception ex){
						msg += ex+":"+qq;
						logger.error(ex+":"+qq);
				}
				finally{
						Helper.databaseDisconnect(con, pstmt, rs);
				}
				//
				// after update he may becomes property owner
				//
				if(!hasCompany_contacts() && isPropertyOwner()){
						CompanyContact cct = new CompanyContact(null, null, id);
						msg = cct.doSave();
				}		
				return msg;
		
		}
		String setFields(PreparedStatement pstmt){

				String msg = "";
				int jj=1;
				try{
						if(fname.equals(""))
								pstmt.setNull(jj++, Types.VARCHAR);
						else
								pstmt.setString(jj++,fname);
						if(lname.equals(""))
								pstmt.setNull(jj++, Types.VARCHAR);
						else
								pstmt.setString(jj++,lname);
						if(title.equals("")) // 3
								pstmt.setNull(jj++, Types.VARCHAR);
						else
								pstmt.setString(jj++,title);
						if(type_id.equals(""))
								pstmt.setNull(jj++, Types.VARCHAR);
						else
								pstmt.setString(jj++,type_id);
						if(address.equals(""))
								pstmt.setNull(jj++, Types.VARCHAR);
						else
								pstmt.setString(jj++,address);
						if(city.equals(""))
								pstmt.setNull(jj++, Types.VARCHAR);
						else
								pstmt.setString(jj++,city);
						if(state.equals(""))
								pstmt.setNull(jj++, Types.VARCHAR);
						else
								pstmt.setString(jj++,state);
						if(zip.equals(""))
								pstmt.setNull(jj++, Types.VARCHAR);
						else
								pstmt.setString(jj++,zip);
						if(work_phone.equals(""))
								pstmt.setNull(jj++, Types.VARCHAR);
						else
								pstmt.setString(jj++,work_phone);
						if(cell_phone.equals(""))
								pstmt.setNull(jj++, Types.VARCHAR);
						else
								pstmt.setString(jj++,cell_phone);
						if(fax.equals(""))
								pstmt.setNull(jj++, Types.VARCHAR);
						else
								pstmt.setString(jj++,fax);
			
						if(email.equals(""))    
								pstmt.setNull(jj++, Types.VARCHAR);
						else
								pstmt.setString(jj++,email);
						if(notes.equals(""))
								pstmt.setNull(jj++, Types.VARCHAR);
						else
								pstmt.setString(jj++, notes);			
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
						msg = " owner id not set ";
						logger.error(msg);
						return msg;
				}
				qq = "select c.id,c.fname,c.lname,c.title,c.type_id,c.address,"+
						" c.city,c.state,c.zip,c.work_phone,c.cell_phone,c.fax,c.email,"+
						" c.notes,t.name "+
						" from contacts c "+
						" left join contact_types t on c.type_id=t.id where c.id=?";
				con = Helper.getConnection();
				if(con == null){
						msg = "Could not connect to Database ";
						logger.error(msg);
						return msg;
				}
				logger.debug(qq);			
				
				try {
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
													rs.getString(15));
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
				qq = "delete from contacts where id=?";
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
						id="";
						lname="";fname="";
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
