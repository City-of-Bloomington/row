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

public class PermitStartAction extends TopAction{

		static final long serialVersionUID = 207L;	
		String company_id="", contact_id="", company_contact_id="";
		static Logger logger = LogManager.getLogger(PermitStartAction.class);
		//
		CompanyContactStart start = null;
		List<Permit> permits = null;
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
				if(action.equals("Next")){
						ret = SUCCESS;
						start.setUser_id(user.getId());
						back = start.doNext();
						if(!back.equals("")){
								addActionError(back);
						}
						else{
								if(!start.getNeedMore()){
										company_contact_id = start.getCompany_contact_id();
										try{
												HttpServletResponse res = ServletActionContext.getResponse();
												String str = url+"permit.action?company_contact_id="+company_contact_id;
												res.sendRedirect(str);
												return super.execute();
										}catch(Exception ex){
												System.err.println(ex);
										}
								}
						}
				}
				//
				return ret;
		}

		public CompanyContactStart getStart(){ 
				if(start == null){
						start = new CompanyContactStart();
						if(!company_id.equals("")){
								start.setCompany_id(company_id);
						}
						if(!contact_id.equals("")){
								start.setContact_id(contact_id);
						}			
				}		
				return start;
		}
		//


		public void setCompanyContactStart(CompanyContactStart val){
				if(val != null)
						start = val;
		}

		public String getContact_id(){
				return contact_id;
		}
		public String getCompany_id(){
				return company_id;
		}
		public void setCompany_contact_id(String val){
				company_contact_id = val;
		}	
		public String getPermitsTitle(){
				return "Most recent permits";
		}
		public String getPageTitle(){
				return "New Permit";
		}
		public String getActionName(){
				return "permitStart";
		}
		public String getSubjectLine(){
				return "To create a new permit:";
		}
		public List<Permit> getPermits(){
				PermitList bl = new PermitList();
				String back = bl.find();
				if(back.equals("") && bl.getPermits() != null){
						permits = bl.getPermits();
				}
				return permits;
		}	
		//
		// we need these for auto_complete
		//
		public void setCompany_name(String val){
				// nothing
		}
		public void setContact_name(String val){
				// nothing
		}
}





































