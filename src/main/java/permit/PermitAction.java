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


public class PermitAction extends TopAction{

		static final long serialVersionUID = 207L;	
		String company_contact_id="";
		static Logger logger = LogManager.getLogger(PermitAction.class);
		//
		Permit permit = null; 
		List<User> inspectors = null;
		List<Type> utility_types = null;
		List<Bond> bonds = null; // for new permit we need list of bonds
		//
		public String execute(){
				String ret = INPUT;
				String back = doPrepare();
				//
				if(action.equals("Save")){
						ret = SUCCESS;
						logger.debug("permit action save ");
						permit.setUser_id(user.getId());
						back = permit.doSave();
						if(!back.equals("")){
								addActionError(back);
								logger.debug("permit action save "+back);								
						}
						else{
								id = permit.getId();
								//
								addActionMessage("Saved Successfully");
						}
				}
				else if(action.equals("Update")){
						ret = SUCCESS;
						logger.debug("permit action update ");
						permit.setUser_id(user.getId());
						back = permit.doUpdate();
						if(!back.equals("")){
								addActionError(back);
								logger.debug("permit action update "+back);
						}
						else{
								
								addActionMessage("Updated Successfully");
						}
				}
				else if(action.equals("Print")){
						ret = "print";
						populate();
				}		
				else if(!company_contact_id.equals("")){
						getPermit();
				}
				else if(!id.equals("")){
						ret = populate();
				}
				//
				return ret;
		}

		public Permit getPermit(){ 
				if(permit == null){
						logger.debug("permit action get permit ");
						permit = new Permit();
						if(!company_contact_id.equals("")){
								permit.setCompany_contact_id(company_contact_id);
						}
				}		
				return permit;
		}
		//
		public List<Bond> getBonds(){
				logger.debug("permit action get bonds ");
				if(!company_contact_id.equals("")){
						BondList bl = new BondList();			
						Company company = permit.getCompany();
						Contact contact = permit.getContact();
						if(company != null){ // prefered 
								bl.setCompany_id(company.getId());
						}
						else if(contact != null){ // only contact
								bl.setContact_id(contact.getId());
						}
						else
								bl.setCompany_contact_id(company_contact_id);
						bl.setActiveOnly();
						String back = bl.find();
						if(back.equals("")){
								List<Bond> ones = bl.getBonds();
								if(ones != null && ones.size() > 0)
										bonds = ones;
						}
						else{
								logger.debug("permit action get bonds "+back);
						}
				}
				return bonds;
		}

		public List<User> getInspectors(){
				logger.debug("permit action get inspectors ");
				InspectorList bl = new InspectorList();
				String back = bl.find();
				if(back.equals("")){
						if(bl.getInspectors() != null){
								inspectors = bl.getInspectors();
						}
				}
				else{
						logger.debug("permit action get inspectors "+back);
				}
				return inspectors;
		}

		public void setPermit(Permit val){
				if(val != null)
						permit = val;
		}
		@Override  
		public String getId(){
				if(id.equals("") && permit != null){
						id = permit.getId();
				}
				return id;
		}
		public String getCompany_contact_id(){
				return company_contact_id;
		}
		public void setCompany_contact_id(String val){
				company_contact_id = val;
		}	
	
		public String getExcavationsTitle(){
				return "Related Excavations";
		}
		public String getBondTitle(){
				return "Related bond";
		}	
		public String getInspectionsTitle(){
				return "Related Inspections";
		}	

		public String populate(){
				String ret = SUCCESS;
				if(!id.equals("")){
						permit = new Permit(id);
						String back = permit.doSelect();
						if(!back.equals("")){
								addActionError(back);
								logger.debug("permit action pop "+back);
						}
				}
				return ret;
		}


}





































