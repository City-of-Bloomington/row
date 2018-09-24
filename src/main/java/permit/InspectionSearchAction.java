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


public class InspectionSearchAction extends TopAction{

		static final long serialVersionUID = 259L;	
		static Logger logger = LogManager.getLogger(InspectionSearchAction.class);
		//
		List<Inspection> inspections = null;
		List<Type> utility_types = null;
		List<User> inspectors = null;
		InspectionList inspectionList = null;

		String inspectionsTitle = "Most recent inspections";
		public String execute(){
				String ret = SUCCESS;
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
				if(!action.equals("")){
						inspectionList.setNoLimit();
						back = inspectionList.find();
						if(!back.equals("")){
								addActionError(back);
						}
						else{
								inspections = inspectionList.getInspections();
								if(inspections == null || inspections.size() == 0){
										inspectionsTitle = "No match found ";
								}
								else if(inspections.size() == 1){
										Inspection one = inspections.get(0);
										id = one.getId();
										try{
												HttpServletResponse res = ServletActionContext.getResponse();
												String str = url+"inspection.action?id="+id;
												res.sendRedirect(str);
												return super.execute();
										}catch(Exception ex){
												System.err.println(ex);
										}						
								}
								else{
										inspectionsTitle = "Found "+inspections.size()+" records";
								}
						}
				}
				else{
						InspectionList bl = new InspectionList();
						back = bl.find();
						if(back.equals("") && bl.getInspections() != null){
								inspections = bl.getInspections();
						}
						else{
								addActionError(back);				
						}
				}
				return ret;
		}

		public InspectionList getInspectionList(){ 
				if(inspectionList == null){
						inspectionList = new InspectionList();
				}		
				return inspectionList;
		}
		public List<Inspection> getInspections(){
				return inspections;
		}
		public String getInspectionsTitle(){
				return inspectionsTitle;
		}
		public List<User> getInspectors(){
				InspectorList bl = new InspectorList();
				String back = bl.find();
				if(back.equals("") && bl.getInspectors() != null){
						inspectors = bl.getInspectors();
				}
				return inspectors;

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





































