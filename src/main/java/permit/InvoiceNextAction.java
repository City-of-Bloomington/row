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


public class InvoiceNextAction extends TopAction{

		static final long serialVersionUID = 203L;	
		String company_contact_id="";
		String permit_id="";
		static Logger logger = LogManager.getLogger(InvoiceAction.class);
		//
		Invoice invoice = null; 
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
						ret = "next";
						back = invoice.doNext();
						if(!back.equals("")){
								ret = INPUT;
								addActionError(back);
						}
				}
				else{
						getInvoice();
				}		
				return ret;
		}

		public Invoice getInvoice(){ 
				if(invoice == null){
						invoice = new Invoice();
						if(!company_contact_id.equals("")){
								invoice.setCompany_contact_id(company_contact_id);
						}
						if(!permit_id.equals("")){
								invoice.setPermit_id(permit_id);
						}
				}		
				return invoice;
		}
		public void setInvoice(Invoice val){
				if(val != null)
						invoice = val;
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
				return "Ralated Permits";
		}

		public void setPermit_id(String val){
				if(val != null)
						permit_id = val;
		}

}





































