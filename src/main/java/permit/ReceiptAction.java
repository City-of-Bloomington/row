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

public class ReceiptAction extends TopAction{

		static final long serialVersionUID = 205L;	

		String invoice_id="";
		static Logger logger = LogManager.getLogger(ReceiptAction.class);
		//
		Receipt receipt = null; 
		public String execute(){
				String ret = INPUT;
				String back = doPrepare();
				if(action.equals("Save")){
						ret = SUCCESS;
						logger.debug(" receipt action save ");
						receipt.setUser_id(user.getId());
						back = receipt.doSave();
						if(!back.equals("")){
								addActionError(back);
								logger.debug(" receipt action save "+back);
						}
						else{
								id = receipt.getId();
								//
								addActionMessage("Saved Successfully");
						}
				}
				else if(action.equals("Update")){
						ret = SUCCESS;
						logger.debug(" receipt action update ");
						receipt.setUser_id(user.getId());
						back = receipt.doUpdate();
						if(!back.equals("")){
								addActionError(back);
						}
						else{
								logger.debug(" receipt action update "+back);
								addActionMessage("Updated Successfully");
						}
				}
				else if(action.startsWith("Print")){
						populate();
						try{
								HttpServletResponse res = ServletActionContext.getResponse();
								String str = url+"ReceiptPdf?action=Print&id="+receipt.getId();
								res.sendRedirect(str);
								return super.execute();
						}catch(Exception ex){
								logger.error(ex);
						}
				}			
				else if(!id.equals("")){
						ret = populate();
				}
				//
				return ret;
		}

		public Receipt getReceipt(){ 
				if(receipt == null){
						receipt = new Receipt();
						if(!invoice_id.equals("")){
								receipt.setInvoice_id(invoice_id);
						}
				}		
				return receipt;
		}
		//

		public void setReceipt(Receipt val){
				if(val != null)
						receipt = val;
		}

		public String getId(){
				if(id.equals("") && receipt != null){
						id = receipt.getId();
				}
				return id;
		}


		public String getReceiptsTitle(){
				return "Most recent receipts";
		}


		public void setInvoice_id(String val){
				if(val != null)
						invoice_id = val;
		}	

		public String populate(){
				String ret = SUCCESS;
				if(!id.equals("")){
						receipt = new Receipt(id);
						String back = receipt.doSelect();
						if(!back.equals("")){
								addActionError(back);
								logger.debug(" receipt action pop "+back);
						}
				}
				return ret;
		}


}





































