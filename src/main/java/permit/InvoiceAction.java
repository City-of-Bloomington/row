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

public class InvoiceAction extends TopAction{

		static final long serialVersionUID = 203L;	
		String company_contact_id="";
		String permit_id="";
		static Logger logger = LogManager.getLogger(InvoiceAction.class);
		//
		Invoice invoice = null; 
		public String execute(){
				String ret = INPUT;
				String back = doPrepare();
				if(action.equals("Save")){
						logger.error("invoice action save");
						ret = SUCCESS;
						invoice.setUser_id(user.getId());
						back = invoice.doSave();
						if(!back.equals("")){
								addActionError(back);
								logger.error("invoice action "+back);
						}
						else{
								id = invoice.getId();
								//
								addActionMessage("Saved Successfully");
						}
				}
				else if(action.equals("Update")){
						ret = SUCCESS;
						logger.error("invoice action update");
						invoice.setUser_id(user.getId());
						back = invoice.doUpdate();
						if(!back.equals("")){
								addActionError(back);
								logger.error("invoice action "+back);
						}
						else{
								addActionMessage("Updated Successfully");
						}
				}
				else if(action.startsWith("Print")){
						ret = "print";
						populate();
						invoice.createPages();
				}			
				else if(!company_contact_id.equals("")){
						getInvoice();
						invoice.setCompany_contact_id(company_contact_id);
				}
				else if(!permit_id.equals("")){
						getInvoice();
						invoice.setPermit_id(permit_id);
				}		
				else if(!id.equals("")){
						ret = populate();
				}
				//
				return ret;
		}

		public Invoice getInvoice(){ 
				if(invoice == null){
						invoice = new Invoice();
						if(!company_contact_id.equals("")){
								invoice.setCompany_contact_id(company_contact_id);
						}
				}		
				return invoice;
		}
		//

		public void setInvoice(Invoice val){
				if(val != null)
						invoice = val;
		}
		@Override  
		public String getId(){
				if(id.equals("") && invoice != null){
						id = invoice.getId();
				}
				return id;
		}
		public String getCompany_contact_id(){
				return company_contact_id;
		}
		public void setCompany_contact_id(String val){
				company_contact_id = val;
		}	

		public String getInvoicesTitle(){
				return "Most recent invoices";
		}
		public String getPermitsTitle(){
				return "Related Permits";
		}

		public void setPermit_id(String val){
				if(val != null)
						permit_id = val;
		}
		public String populate(){
				String ret = SUCCESS;
				if(!id.equals("")){
						invoice = new Invoice(id);
						String back = invoice.doSelect();
						if(!back.equals("")){
								addActionError(back);
								logger.error("invoice action pop "+back);
						}
				}
				return ret;
		}


}





































