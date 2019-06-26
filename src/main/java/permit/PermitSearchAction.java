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

public class PermitSearchAction extends TopAction{

		static final long serialVersionUID = 239L;	
		static Logger logger = LogManager.getLogger(PermitSearchAction.class);
		//
		List<Permit> permits = null;
		PermitList permitList = null;
		List<User> inspectors = null;
		String company_name="", contact_name="";
		String permitsTitle = "Most Recent Permits";
		
		public String execute(){
				String ret = SUCCESS;
				String back = doPrepare();
				//
				if(!action.equals("")){
						logger.debug(" permit search action ");
						permitList.setNoLimit();
						back = permitList.find();
						if(!back.equals("")){
								addActionError(back);
								logger.error(" permit search action "+back);
						}
						else{
								// ret = "result";
								logger.debug(" permit search action after back ");
								permits = permitList.getPermits();
								if(permits == null || permits.size() == 0){
										permitsTitle = "No match found ";
								}
								else if(permits.size() == 1){
										Permit one = permits.get(0);
										id = one.getId();
										try{
												HttpServletResponse res = ServletActionContext.getResponse();
												String str = url+"permit.action?id="+id;
												res.sendRedirect(str);
												return super.execute();
										}catch(Exception ex){
												System.err.println(ex);
										}						
								}
								else{
										permitsTitle = "Found "+permits.size()+" records";
								}
						}
				}
				else{
						// most recent
						PermitList bl = new PermitList();
						back = bl.find();
						if(back.equals("") && bl.getPermits() != null){
								permits = bl.getPermits();
						}
						else{
								addActionError(back);
						}
				}
				return ret;
		}

		public PermitList getPermitList(){ 
				if(permitList == null){
						permitList = new PermitList();
				}		
				return permitList;
		}
		public List<Permit> getPermits(){
				return permits;
		}
		public String getPermitsTitle(){
				return permitsTitle;
		}

		public List<User> getInspectors(){
				logger.debug(" get inspector list ");
				InspectorList bl = new InspectorList();
				String back = bl.find();
				if(back.equals("") && bl.getInspectors() != null){
						inspectors = bl.getInspectors();
				}
				return inspectors;

		}	

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





































