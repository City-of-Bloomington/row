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

public class InvoiceSearchAction extends TopAction{

		static final long serialVersionUID = 229L;	

		static Logger logger = LogManager.getLogger(InvoiceSearchAction.class);
		//
		List<Invoice> invoices = null;
		InvoiceList invoiceList = null;

		String invoicesTitle = "Most Recent Invoices";
		public String execute(){
				String ret = SUCCESS;
				String back = doPrepare();
				if(!action.equals("")){
						logger.debug(" invoice search action ");
						invoiceList.setNoLimit();
						back = invoiceList.find();
						if(!back.equals("")){
								addActionError(back);
								logger.debug(" invoice search action "+back);
						}
						else{
								invoices = invoiceList.getInvoices();
								if(invoices == null || invoices.size() == 0){
										invoicesTitle = "No match found ";
								}
								else if(invoices.size() == 1){
										Invoice one = invoices.get(0);
										id = one.getId();
										try{
												HttpServletResponse res = ServletActionContext.getResponse();
												String str = url+"invoice.action?id="+id;
												res.sendRedirect(str);
												return super.execute();
										}catch(Exception ex){
												logger.error(ex);
										}						
								}
								else{
										invoicesTitle = "Found "+invoices.size()+" records";
								}
						}
				}
				else{
						InvoiceList bl = new InvoiceList();
						back = bl.find();
						if(back.equals("")){
								if(bl.getInvoices() != null){
										invoices = bl.getInvoices();
								}
						}
						else{
								logger.error(back);								
								addActionError(back);
						}
				}
				return ret;
		}

		public InvoiceList getInvoiceList(){ 
				if(invoiceList == null){
						invoiceList = new InvoiceList();
				}		
				return invoiceList;
		}
		public List<Invoice> getInvoices(){
				return invoices;
		}
		public String getInvoicesTitle(){
				return invoicesTitle;
		}

		String company_name="", contact_name="";
		public void setCompany_name(String val){
				if(val != null)
						company_name = val;
		}
		public void setContact_name(String val){
				if(val != null)
						contact_name = val;
		}
		public String getCompany_name(){
				return company_name;
		}		
		public String getContact_name(){
				return contact_name;
		}	
		public String populate(){
				String ret = SUCCESS;
				return ret;
		}

}





































