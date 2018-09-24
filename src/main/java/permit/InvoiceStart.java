/**
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
package permit;
import java.sql.*;
import java.text.*;
import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class InvoiceStart implements java.io.Serializable{

    String company_id="",
				company_contact_id="", contact_id="",
				user_id="";
		boolean needMore = true;
		static final long serialVersionUID = 225L;	
		static Logger logger = LogManager.getLogger(InvoiceStart.class);
		CompanyContact companyContact = null;
		Company company = null;
		Contact contact = null;
		List<CompanyContact> companyContacts = null;

    public InvoiceStart(){

    }
		//
		// setters
		//
		public void setCompany_id(String val){
				if(val != null)
						company_id = val;
    }
		public void setContact_id(String val){
				if(val != null)
						contact_id = val;
    }	
		public void setCompany_contact_id(String val){
				if(val != null)
						company_contact_id = val;
    }
		public void setSel_company_contact(String[] vals){
				if(vals != null){
						if(vals.length > 0){
								company_contact_id = vals[0];
						}
				}
    }	
		public void setUser_id(String val){
				if(val != null)
						user_id = val;
    }	
    //
    // getters
    //
		public String getCompany_id(){
				return company_id;
    }
		public String getContact_id(){
				return contact_id;
    }	
    public String getCompany_contact_id(){
				return company_contact_id;
    }
		public boolean getNeedMore(){
				return needMore;
		}
		//
		public CompanyContact getCompanyContact(){
				if(companyContact == null && !company_contact_id.equals("")){
						CompanyContact one = new CompanyContact(company_contact_id);
						String back = one.doSelect();
						if(back.equals("")){
								companyContact = one;
						}
				}
				return companyContact;
		}
		public List<CompanyContact> getCompanyContacts(){
				return companyContacts;
		}
		public void findCompanyContacts(){
				if(companyContacts == null){
						if(!company_id.equals("")){
								CompanyContactList ccl = new CompanyContactList(company_id);
								String back = ccl.find();
								List<CompanyContact> list = ccl.getCompany_contacts();
								if(back.equals("") && list.size() > 0){
										companyContacts = list;
								}
						}
						else if(!contact_id.equals("")){
								CompanyContactList ccl = new CompanyContactList(null, contact_id);
								String back = ccl.find();
								List<CompanyContact> list = ccl.getCompany_contacts();
								if(back.equals("")){
										if(list.size() > 0){
												companyContacts = list;
												if(list.size() == 1){
														companyContact = list.get(0);
														company_contact_id = companyContact.getId();
												}
												else{
														// we need to pick from a list
												}
										}
										else{
												// new contact never affiliated before, so we
												// create a new record
												companyContact = new CompanyContact();
												companyContact.setContact_id(contact_id);
												back = companyContact.doSave();
												company_contact_id = companyContact.getId();
										}
								}				
						}
				}
		}
		//
		public Contact getContact(){
				getCompanyContact();
				if(companyContact != null){
						contact = companyContact.getContact();
				}
				else if(!contact_id.equals("")){
						Contact one = new Contact(contact_id);
						String back = one.doSelect();
						if(back.equals("")){
								contact = one;
						}
				}
				return contact;
		}
		public Company getCompany(){
				getCompanyContact();
				if(companyContact != null){
						company =  companyContact.getCompany();
				}
				else if(!company_id.equals("")){
						Company one = new Company(company_id);
						String back = one.doSelect();
						if(back.equals("")){
								company = one;
						}
				}
				return company;
		}
		public boolean hasCompany(){
				getCompany();
				return company != null;
		}
	
		public boolean hasContact(){
				getContact();
				return contact != null;
		}
		public boolean hasCompanyContacts(){
				findCompanyContacts();
				return companyContacts != null;
		}	
		String doNext(){
				String msg = "";
				if(!company_contact_id.equals("")){
						needMore = false;
				}
				else if(!company_id.equals("")){
						findCompanyContacts();
				}
				else if(!contact_id.equals("")){
						//
						// contact only, no need for company
						//
						findCompanyContacts();
						if(!company_contact_id.equals("")){
								needMore = false;
						}			
				}
				return msg;
		}

	
}
