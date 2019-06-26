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

public class UserAction extends TopAction{

		static final long serialVersionUID = 205L;	

		static Logger logger = LogManager.getLogger(ReceiptAction.class);
		//
		User rowUser = null;
		List<User> users = null;
		public String execute(){
				String ret = INPUT;
				String back = doPrepare();
				if(action.equals("Save")){
						ret = SUCCESS;
						logger.debug(" user action save ");
						back = rowUser.doSave();
						if(!back.equals("")){
								addActionError(back);
								logger.debug(" user action save "+back);
						}
						else{
								id = rowUser.getId();
								//
								addActionMessage("Saved Successfully");
						}
				}
				else if(action.equals("Update")){
						ret = SUCCESS;
						logger.debug(" user action update ");
						back = rowUser.doUpdate();
						if(!back.equals("")){
								addActionError(back);
								logger.debug(" user action update "+back);
						}
						else{
								addActionMessage("Updated Successfully");
						}
				}
				else if(action.startsWith("New")){
						ret = SUCCESS;
						id = "";
						getRowUser();
				}
				else if(!id.equals("")){
						ret = populate();
				}
				//
				return ret;
		}

		public User getRowUser(){ 
				if(rowUser == null){
						rowUser = new User();
				}		
				return rowUser;
		}
		//

		public void setRowUser(User val){
				if(val != null)
						rowUser = val;
		}

		public String getId(){
				if(id.equals("") && user != null){
						id = rowUser.getId();
				}
				return id;
		}


		public String getUsersTitle(){
				return "Current users";
		}
		public List<User> getUsers(){
				if(users == null){
						UserList ul = new UserList();
						String back = ul.find();
						if(back.equals("")){
								users = ul.getUsers();
						}
						else{
								logger.debug(" user action users "+back);
								addActionError(back);
						}
				}
				return users;
		}
		public String populate(){
				String ret = SUCCESS;
				if(!id.equals("")){
						rowUser = new User(id);
						String back = rowUser.doSelect();
						if(!back.equals("")){
								addActionError(back);
								logger.debug(" user action pop "+back);
						}
				}
				return ret;
		}


}





































