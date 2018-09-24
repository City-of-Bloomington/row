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
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class InsuranceSearchAction extends TopAction{

		static final long serialVersionUID = 269L;	
		static Logger logger = LogManager.getLogger(InsuranceSearchAction.class);
		//
		List<Insurance> insurances = null;
		InsuranceList insuranceList = null;
		List<User> inspectors = null;
		List<Type> types = null;
		List<Type> insuranceCompanies = null;
		String insurancesTitle = "Most Recent Insurances";
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
						insuranceList.setNoLimit();
						back = insuranceList.find();
						if(!back.equals("")){
								addActionError(back);
						}
						else{
								// ret = "result";
								insurances = insuranceList.getInsurances();
								if(insurances == null || insurances.size() == 0){
										insurancesTitle = "No match found ";
								}
								else if(insurances.size() == 1){
										Insurance one = insurances.get(0);
										id = one.getId();
										try{
												HttpServletResponse res = ServletActionContext.getResponse();
												String str = url+"insurance.action?id="+id;
												res.sendRedirect(str);
												return super.execute();
										}catch(Exception ex){
												System.err.println(ex);
										}						
								}
								else{
										insurancesTitle = "Found "+insurances.size()+" records";
								}
						}
				}
				else{
						InsuranceList bl = new InsuranceList();
						back = bl.find();
						if(back.equals("") && bl.getInsurances() != null){
								insurances = bl.getInsurances();
						}
						else{
								addActionError(back);
						}
				}
				return ret;
		}

		public InsuranceList getInsuranceList(){ 
				if(insuranceList == null){
						insuranceList = new InsuranceList();
				}		
				return insuranceList;
		}
		public List<Insurance> getInsurances(){
				return insurances;
		}
		public String getInsurancesTitle(){
				return insurancesTitle;
		}
		/*
		public List<Type> getTypes(){
				TypeList bl = new TypeList("insurance_types");
				String back = bl.find();
				if(back.equals("") && bl.getTypes() != null){
						types = bl.getTypes();
				}
				return types;
		}
		*/
		//
		// bond and insurance companies are the same
		//
		public List<Type> getInsuranceCompanies(){
				TypeList bl = new TypeList("bond_companies");
				String back = bl.find();
				if(back.equals("") && bl.getTypes() != null){
						insuranceCompanies = bl.getTypes();
				}
				return insuranceCompanies;
		}	

		public void setAction2(String val){
				if(val != null && !val.equals(""))		
						action = val;
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





































