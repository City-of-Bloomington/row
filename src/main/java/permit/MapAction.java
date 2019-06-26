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



public class MapAction extends TopAction{

		static final long serialVersionUID = 233L;	
		String excavation_id="";
		static Logger logger = LogManager.getLogger(MapAction.class);
		Address address = null;
		ApiKey key = null;
		public String execute(){
				String ret = INPUT;
				String back = doPrepare();
				if(action.equals("Update")){
						ret = SUCCESS;			
						address.setUser_id(user.getId());
						back = address.doUpdate();
						if(!back.equals("")){
								addActionError(back);
								logger.debug(back);
						}
						else{
								excavation_id = address.getExcavation_id();
								try{
										HttpServletResponse res = ServletActionContext.getResponse();
										String str = url+"excavation.action?id="+excavation_id;
										res.sendRedirect(str);
										return super.execute();
								}catch(Exception ex){
										logger.error(ex);
								}				
						}
				}
				else if(!excavation_id.equals("")){
						getAddress();
				}		
				else if(!id.equals("")){
						ret = populate();
				}
				return ret;
		}
		public Address getAddress(){ 
				if(address == null){
						address = new Address();
						if(!excavation_id.equals("")){
								address.setExcavation_id(excavation_id);
								address.doSelect();
						}
				}		
				return address;
		}
		//

		public void setAddress(Address val){
				if(val != null)
						address = val;
		}
		@Override  
		public String getId(){
				if(id.equals("") && address != null){
						id = address.getId();
				}
				return id;
		}

		public void setExcavation_id(String val){
				if(val != null)
						excavation_id = val;
		}
		public String populate(){
				String ret = SUCCESS;
				if(!id.equals("")){
						address = new Address(id);
						String back = address.doSelect();
						if(!back.equals("")){
								addActionError(back);
								logger.debug(back);
						}
				}
				return ret;
		}
		public ApiKey getKey(){
				ApiKeyList akl = new ApiKeyList();
				akl.setActiveOnly();
				String back = akl.find();
				if(back.equals("")){
						List<ApiKey> keys = akl.getKeys();
						if(keys != null && keys.size() > 0){
								key = keys.get(0);
						}
				}
				else{
						logger.debug(back);
				}
				return key;
		}
		public String getKeyValue(){
				String ret = "";
				getKey();
				if(key != null){
						ret = key.getValue();
				}
				return ret;
		}

}





































