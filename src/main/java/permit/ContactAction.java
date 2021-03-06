/**
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
package permit;

import java.util.*;
import java.io.*;
import java.text.*;
import com.opensymphony.xwork2.ModelDriven;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;  
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ContactAction extends TopAction{

		static final long serialVersionUID = 201L;	
		String company_id="";
		static Logger logger = LogManager.getLogger(ContactAction.class);
		//
		Contact contact = null; 
		List<Contact> contacts = null;
		List<Type> types = null;	

		public String execute(){
				String ret = INPUT;
				String back = doPrepare();
				if(action.equals("Save")){
						logger.debug(" contact action save ");

						ret = SUCCESS;
						contact.setUser_id(user.getId());
						back = contact.doSave();
						if(!back.equals("")){
								addActionError(back);
								logger.error(" contact action save "+back);
						}
						else{
								id = contact.getId();
								//
								// check if we want to add a contact
								//
								addActionMessage("Saved Successfully");
						}
				}
				else if(action.equals("Update")){
						ret = SUCCESS;
						logger.debug(" contact action update ");
						contact.setUser_id(user.getId());
						back = contact.doUpdate();
						if(!back.equals("")){
								addActionError(back);
								logger.error(" contact action update "+back);
						}
						else{
								addActionMessage("Updated Successfully");
						}
				}		
				else if(!id.equals("")){
						ret = populate();
				}
				//
				// we are adding this contact to an existing company
				//
				if(!company_id.equals("")){
						if(id.equals("")){
								getId();
						}
						CompanyContact cc = new CompanyContact(null, company_id, id);
						back = cc.doSave();
						if(!back.equals("")){
								addActionError(back);
								logger.debug(" contact action "+back);
						}
				}
				return ret;
		}

		public Contact getContact(){ 
				if(contact == null){
						contact = new Contact();
				}		
				return contact;
		}
		//
		public List<Contact> getContacts(){
				logger.debug(" contacts ");
				ContactList bl = new ContactList();
				String back = bl.find();
				if(back.equals("") && bl.getContacts() != null){
						contacts = bl.getContacts();
				}
				else{
						logger.error(" contacts "+back);
				}
				return contacts;
		}
		public List<Type> getTypes(){
				logger.debug(" contact types");
				TypeList bl = new TypeList("contact_types");
				String back = bl.find();
				if(back.equals("") && bl.getTypes() != null){
						types = bl.getTypes();
				}
				return types;
		}

		public void setContact(Contact val){
				if(val != null)
						contact = val;
		}

		public String getId(){
				if(id.equals("") && contact != null){
						id = contact.getId();
				}
				return id;
		}
		public String getCompany_id(){
				return company_id;
		}	

		public String getContactsTitle(){
				return "Most recent contacts";
		}
		public String getCompaniesTitle(){
				return "Contact Affiliations";
		}
		public String getPermitsTitle(){
				return "Contact recent permit(s)";
		}
		public String getInvoicesTitle(){
				return "Contact recent invoice(s)";
		}
		public String getBondsTitle(){
				return "Contact recent bond(s)";
		}	

		public void setCompany_id(String val){
				if(val != null)
						company_id = val;
		}	
		public String populate(){
				String ret = SUCCESS;
				if(!id.equals("")){
						contact = new Contact(id);
						String back = contact.doSelect();
						if(!back.equals("")){
								addActionError(back);
								logger.error(" contact pop "+back);
						}
				}
				return ret;
		}

}





































