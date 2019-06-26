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

public class InspectionAction extends TopAction{

		static final long serialVersionUID = 247L;	
		String permit_id="";
		static Logger logger = LogManager.getLogger(InspectionAction.class);
		//
		Inspection inspection = null; 
		List<User> inspectors = null;
		List<Type> utility_types = null;

		public String execute(){
				String ret = INPUT;
				String back = doPrepare();
				if(action.equals("Save")){
						ret = SUCCESS;
						logger.debug(" insp action save ");
						inspection.setUser_id(user.getId());
						back = inspection.doSave();
						if(!back.equals("")){
								addActionError(back);
								logger.debug(" insp action save "+back);								
						}
						else{
								id = inspection.getId();
								//
								addActionMessage("Saved Successfully");
						}
				}
				else if(action.equals("Update")){
						ret = SUCCESS;
						logger.debug(" insp action update ");
						inspection.setUser_id(user.getId());
						back = inspection.doUpdate();
						if(!back.equals("")){
								addActionError(back);
								logger.debug(" insp action "+back);
						}
						else{
								addActionMessage("Updated Successfully");
						}
				}
				else if(!id.equals("")){
						ret = populate();
				}
				//
				return ret;
		}

		public Inspection getInspection(){ 
				if(inspection == null){
						inspection = new Inspection();
						if(!permit_id.equals("")){
								inspection.setPermit_id(permit_id);
						}
				}		
				return inspection;
		}
		//

		public void setInspection(Inspection val){
				if(val != null)
						inspection = val;
		}
		@Override  
		public String getId(){
				if(id.equals("") && inspection != null){
						id = inspection.getId();
				}
				return id;
		}

		public void setPermit_lookup(String val){
				// for auto_complete
		}

		public String getInspectionsTitle(){
				return "Most recent inspections";
		}

		public void setPermit_id(String val){
				if(val != null)
						permit_id = val;
		}	
		public List<User> getInspectors(){
				logger.debug(" insp action inspectors ");
				InspectorList bl = new InspectorList();
				String back = bl.find();
				if(back.equals("") && bl.getInspectors() != null){
						inspectors = bl.getInspectors();
				}
				return inspectors;

		}
		public String populate(){
				String ret = SUCCESS;
				if(!id.equals("")){
						inspection = new Inspection(id);
						String back = inspection.doSelect();
						if(!back.equals("")){
								addActionError(back);
								logger.debug(" insp action pop "+back);
						}
				}
				return ret;
		}


}





































