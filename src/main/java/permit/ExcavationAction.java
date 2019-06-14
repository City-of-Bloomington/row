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

public class ExcavationAction extends TopAction{

		static final long serialVersionUID = 237L;	
		String permit_id="";
		static Logger logger = LogManager.getLogger(ExcavationAction.class);
		//
		Excavation excavation = null; 
		List<User> inspectors = null;
		List<Type> utility_types = null;
		public String execute(){
				String ret = INPUT;
				String back = doPrepare();
				///
				if(!action.equals("") && user == null){
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
						excavation.setUser_id(user.getId());
						back = excavation.handleAddress(checkAddrUrl);
						back = excavation.doSave();
						if(!back.equals("")){
								addActionError(back);
						}
						else{
								id = excavation.getId();
								//
								addActionMessage("Saved Successfully");
						}
				}
				else if(action.equals("Update")){
						ret = SUCCESS;			
						excavation.setUser_id(user.getId());
						back = excavation.handleAddress(checkAddrUrl);
						back = excavation.doUpdate();
						if(!back.equals("")){
								addActionError(back);
						}
						else{
								addActionMessage("Updated Successfully");
						}
				}
				else if(action.equals("Delete") && user.isAdmin()){
						ret = SUCCESS;			
						back = excavation.doDelete();
						if(!back.equals("")){
								addActionError(back);
						}
						else{
								addActionMessage("Deleted Successfully");
								excavation = new Excavation();
								id="";
						}
				}				
				else if(!id.equals("")){
						ret = populate();
				}
				//
				return ret;
		}
		/**
		 * this method is used to get user and debug param
		 */

		public Excavation getExcavation(){ 
				if(excavation == null){
						excavation = new Excavation();
						if(!permit_id.equals("")){
								excavation.setPermit_id(permit_id);
						}
				}		
				return excavation;
		}
		public List<Type> getUtility_types(){
				if(utility_types == null){
						TypeList tl = new TypeList("utility_types");
						String back = tl.find();
						if(back.equals("")){
								utility_types = tl.getTypes();
						}
				}
				return utility_types;
		}

		public void setExcavation(Excavation val){
				if(val != null)
						excavation = val;
		}

		public String getId(){
				if(id.equals("") && excavation != null){
						id = excavation.getId();
				}
				return id;
		}

		public void setPermit_lookup(String val){
				// for auto_complete
		}

		public void setPermit_id(String val){
				if(val != null)
						permit_id = val;
		}	

		public String populate(){
				String ret = SUCCESS;
				if(!id.equals("")){
						excavation = new Excavation(id);
						String back = excavation.doSelect();
						if(!back.equals("")){
								addActionError(back);
						}
				}
				return ret;
		}

}





































