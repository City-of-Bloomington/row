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
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;  
import org.apache.struts2.dispatcher.SessionMap;  
import org.apache.struts2.interceptor.SessionAware;  
import org.apache.struts2.util.ServletContextAware;  
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CompanyAction extends TopAction{

	static final long serialVersionUID = 20L;	
	String contact_id="";
	static Logger logger = LogManager.getLogger(CompanyAction.class);
	//
	Company company = null; 
	List<Company> companies = null;
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
			company.setUser_id(user.getId());
			back = company.doSave();
			if(!back.equals("")){
				addActionError(back);
			}
			else{
				//
				id = company.getId();
				addActionMessage("Saved Successfully");
			}
		}
		else if(action.equals("Update")){
			ret = SUCCESS;			
			company.setUser_id(user.getId());
			back = company.doUpdate();
			if(!back.equals("")){
				addActionError(back);
			}
			else{
				addActionMessage("Updated Successfully");
			}
		}
		else if(action.startsWith("Remove Aff")){
			ret = SUCCESS;			
			company.setUser_id(user.getId());
			back = company.deleteAffiliates();
			if(!back.equals("")){
				addActionError(back);
			}
			else{
				addActionMessage("Removed Successfully");
			}
		}				
		else if(!id.equals("")){
			ret = populate();
		}
		if(!contact_id.equals("")){
			if(id.equals("")){
				getId();
			}
			if(!id.equals("")){
				CompanyContact cc = new CompanyContact(null, id, contact_id);
				back = cc.doSave();
				if(!back.equals("")){
					addActionError(back);
				}
			}
		}		
      return ret;
   }

	public Company getCompany(){ 
		if(company == null){
			company = new Company();
		}		
		return company;
	}

	public void setCompany(Company val){
		if(val != null)
			company = val;
	}

	public String getId(){
		if(id.equals("") && company != null){
			id = company.getId();
		}
		return id;
	}

	public void setAction2(String val){ // for auto
		if(val != null && !val.equals(""))
			action = val;
	}	
	public String getCompaniesTitle(){
		return "Most recent companies";
	}
	public String getInvoicesTitle(){
		return "Company recent invoice(s) ";
	}
	public String getPermitsTitle(){
		return "Company recent permit(s) ";
	}
	public String getBondsTitle(){
		return "Company recent bond(s) ";
	}
	public String getInsurancesTitle(){
		return "Company recent insurance(s) ";
	}		
	public void setContact_id(String val){
		if(val != null && !val.equals(""))
			contact_id = val;
	}
	public String getContact_id(){
		return contact_id;
	}	
	public String getContactsTitle(){
		return "Contacts affiliated with this company";
	}		
	public String populate(){
		String ret = SUCCESS;
		if(!id.equals("")){
			company = new Company(id);
			String back = company.doSelect();
			if(!back.equals("")){
				addActionError(back);
			}
		}
		return ret;
	}

}





































