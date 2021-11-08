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

public class InspectorAction extends TopAction{

		static final long serialVersionUID = 205L;	

		static Logger logger = LogManager.getLogger(InspectorAction.class);
		//
		Inspector inspector = null;
		User rowUser = null;
		List<User> users = null;
		List<Inspector> inspectors = null;
		public String execute(){
				String ret = INPUT;
				String back = doPrepare();
				if(action.equals("Save")){
						ret = SUCCESS;
						logger.debug(" inspector action save ");
						back = inspector.doSave();
						if(!back.equals("")){
								addActionError(back);
								logger.debug(" inspector action save "+back);
						}
						else{
								addActionMessage("Saved Successfully");
								ret = INPUT;
						}
				}
				else if(action.equals("Update")){
						logger.debug(" inspector action update ");
						back = inspector.doUpdate();
						if(!back.equals("")){
								addActionError(back);
								logger.debug(" inspector action update "+back);
						}
						else{
								addActionMessage("Updated Successfully");
								ret = INPUT;
						}
				}
				
				else if(action.startsWith("new")){
						ret = SUCCESS;
						getInspector();
						ret="new";
				}
				else if(!id.equals("")){
						getInspector();
						ret="edit";     
				}
				//
				return ret;
		}

		public Inspector getInspector(){ 
				if(inspector == null){
						inspector = new Inspector();
						if(!id.isEmpty()){
								inspector.setId(id);
								if(!inspector.isInspector()){
										User rowUser = new User(id);
										rowUser.doSelect();
										inspector.setUser(rowUser);
								}
								else{
										inspector.doSelect();
								}
						}
				}		
				return inspector;
		}
		//
		public User getRowUser(){ 
				if(rowUser == null){
						rowUser = new User();
				}		
				return rowUser;
		}
		//

		public void setRowUser(User val){
				if(val != null){
						rowUser = val;
						id = rowUser.getId();
				}
		}

		public void setInspector(Inspector val){
				if(val != null)
						inspector = val;
		}

		public String getId(){
				if(id.isEmpty()){
						if(inspector != null){
								id = inspector.getId();
						}
						else if(rowUser != null){
								id = rowUser.getId();
						}
				}
				return id;
		}


		public String getInspectorsTitle(){
				return "Current inspectors";
		}
		//
		// list of users that are not inspectors
		//
		public boolean haveUsers(){
				getUsers();
				return users != null && users.size() > 0;
		}
		public List<User> getUsers(){
				if(users == null){
						UserList ul = new UserList();
						String back = ul.findNonInspectorUsers();
						if(back.equals("")){
								users = ul.getUsers();
						}
						else{
								logger.debug(" inspector action users "+back);
								addActionError(back);
						}
				}
				return users;
		}		
		public List<Inspector> getInspectors(){
				if(inspectors == null){
						InspectorList ul = new InspectorList();
						String back = ul.find();
						if(back.equals("")){
								inspectors = ul.getInspectors();
						}
						else{
								logger.debug(" inspectors action "+back);
								addActionError(back);
						}
				}
				return inspectors;
		}
		public String populate(){
				String ret = SUCCESS;
				if(!id.equals("")){
						inspector = new Inspector(id);
						String back = inspector.doSelect();
						if(!back.equals("")){
								addActionError(back);
								logger.debug(" inspector action "+back);
						}
				}
				return ret;
		}


}





































