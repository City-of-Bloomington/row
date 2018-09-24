package permit;
/**
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
import java.util.*;
import java.io.*;
import java.text.*;
import com.opensymphony.xwork2.ModelDriven;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;  
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class AffiliationAction extends TopAction{

		static final long serialVersionUID = 201L;	
		String contact_id = "", company_id="";
		static Logger logger = LogManager.getLogger(AffiliationAction.class);

		CompanyContact companyContact = null;

		public String execute(){
				String ret = INPUT;
				String back = doPrepare();
				if(!back.equals("")){
						try{
								HttpServletResponse res = ServletActionContext.getResponse();
								String str = url+"Login";
								res.sendRedirect(str);
								return super.execute();
						}catch(Exception ex){
								System.err.println(ex);
						}
				}
				if(action.equals("Save")){
						ret = SUCCESS;
						companyContact.setUser_id(user.getId());
						back = companyContact.doSave();
						if(!back.equals("")){
								addActionError(back);
						}
						else{
								id = companyContact.getId();
								//
								// check if we want to add a contact
								//
								addActionMessage("Saved Successfully");
						}
				}
				if(action.startsWith("Remove")){
						ret = SUCCESS;
						companyContact.setUser_id(user.getId());
						company_id = companyContact.getCompany_id();
						contact_id = companyContact.getContact_id();
						back = companyContact.doDelete();
						if(!back.equals("")){
								addActionError(back);
						}
						else{
								addActionMessage("Affiliation Removed Successfully");
						}
				}		
				else if(!id.equals("")){
						ret = populate();
				}
				//
				// we are adding this contact to an existing company
				//
				return ret;
		}

		public CompanyContact getCompanyContact(){ 
				if(companyContact == null){
						companyContact = new CompanyContact();
						if(!contact_id.equals("")){
								companyContact.setContact_id(contact_id);	
						}
						if(!company_id.equals("")){
								companyContact.setCompany_id(company_id);	
						}			
				}		
				return companyContact;
		}
		//
		// always new owner
		//

		public void setCompanyContact(CompanyContact val){
				if(val != null)
						companyContact = val;
		}

		public String getId(){
				if(id.equals("") && companyContact != null){
						id = companyContact.getId();
				}
				return id;
		}
		public String getCompany_id(){
				return company_id;
		}
		public String getContact_id(){
				return contact_id;
		}	

		public String getContactsTitle(){
				return "Most recent contacts";
		}
		public String getCompaniesTitle(){
				return "Contact Affiliations";
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
		// we need these for auto_complete
		//
		public void setContact_name(String val){
				// nothing
		}
		public void setCompany_name(String val){
				// nothing
		}	
		public String populate(){
				String ret = SUCCESS;
				if(!id.equals("")){
						companyContact = new CompanyContact(id);
						String back = companyContact.doSelect();
						if(!back.equals("")){
								addActionError(back);
						}
				}
				return ret;
		}

}





































