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

public class TypeAction extends TopAction{

		static final long serialVersionUID = 423L;	
		String table_name="bond_companies";
		static Logger logger = LogManager.getLogger(TypeAction.class);
		//
		Type type = null; 
		List<Type> types = null;
		List<Type> tables = null;
		Type table = null;
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
				if(action.equals("Save")){
						ret = SUCCESS;
						type.setUser_id(user.getId());
						if(!table_name.equals("")){
								type.setTable_name(table_name);
						}
						back = type.doSave();
						if(!back.equals("")){
								addActionError(back);
						}
						else{
								id = type.getId();
								//
								addActionMessage("Saved Successfully");
						}
				}
				else if(action.equals("Update")){
						ret = SUCCESS;
						if(!table_name.equals("")){
								type.setTable_name(table_name);
						}			
						type.setUser_id(user.getId());
						back = type.doUpdate();
						if(!back.equals("")){
								addActionError(back);
						}
						else{
								addActionMessage("Updated Successfully");
						}
				}
				else if(action.equals("Submit")){
						ret = SUCCESS;
						id = "";
						type = new Type();
						if(!table_name.equals("")){
								type.setTable_name(table_name);
						}			
				}
				else if(action.startsWith("Add")){
						ret = SUCCESS;
						if(!table_name.equals("")){
								type.setTable_name(table_name);
						}
						back = type.doSave();
						if(!back.equals("")){
								addActionError(back);
						}
						else{
								id = type.getId();
								addActionMessage("Added Successfully");
						}						
				}
				else if(!id.equals("")){
						System.err.println(" calling populate");
						ret = populate();
				}
				//
				return ret;
		}
		public Type getType(){ 
				if(type == null){
						type = new Type();
						if(!id.equals("")){
								type.setId(id);
						}
						if(!table_name.equals("")){
								type.setTable_name(table_name);
						}
				}		
				return type;
		}
		//
		// always new owner
		//
		public List<Type> getTypes(){
				TypeList bl = new TypeList();
				if(!table_name.equals("")){
						bl.setTable_name(table_name);
				}
				String back = bl.find();
				if(back.equals("") && bl.getTypes() != null){
						types = bl.getTypes();
				}
				return types;
		}
		public List<Type> getTables(){
				TypeList bl = new TypeList("category_tables");
				String back = bl.find();
				if(back.equals("") && bl.getTypes() != null){
						tables = bl.getTypes();
				}
				return tables;
		}
		public Type getTable(){
				if(!table_name.equals("")){
						table = new Type(table_name,null,"category_tables");
						table.doSelect();
				}
				else{
						table = new Type(); 
				}
				return table;
		}

		public void setType(Type val){
				if(val != null)
						type = val;
		}
		@Override  
		public String getId(){
				if(id.equals("") && type != null){
						id = type.getId();
				}
				return id;
		}

		public String getTypesTitle(){
				return "All "+table.getName();
		}
		public void setTable_name(String val){
				if(val != null)
						table_name = val;
		}
		public String getTable_name(){
				return table_name;
		}
		public String populate(){
				String ret = SUCCESS;
				if(!id.equals("")){
						type = new Type(id, null, table_name);
				}		
				String back = type.doSelect();
				if(!back.equals("")){
						addActionError(back);
				}
				return ret;
		}


}





































