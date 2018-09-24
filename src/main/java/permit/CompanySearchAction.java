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
import org.apache.struts2.util.ServletContextAware;  
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CompanySearchAction extends TopAction{

		static final long serialVersionUID = 227L;	
		static Logger logger = LogManager.getLogger(CompanySearchAction.class);
		//
		List<Company> companies = null;
		CompanyList companyList = null;

		String companiesTitle = "Most Recent Companies";
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
						companyList.setNo_limit();
						back = companyList.find();
						if(!back.equals("")){
								addActionError(back);
						}
						else{
								companies = companyList.getCompanies();
								if(companies == null || companies.size() == 0){
										companiesTitle = "No match found ";
								}
								else if(companies.size() == 1){
										Company one = companies.get(0);
										id = one.getId();
										try{
												HttpServletResponse res = ServletActionContext.getResponse();
												String str = url+"company.action?id="+id;
												res.sendRedirect(str);
												return super.execute();
										}catch(Exception ex){
												System.err.println(ex);
										}						
								}
								else{
										companiesTitle = "Found "+companies.size()+" records";
								}
						}
				}
				else{
						CompanyList bl = new CompanyList();
						back = bl.find();
						if(back.equals("") && bl.getCompanies() != null){
								companies = bl.getCompanies();
						}
						else{
								addActionError(back);
						}
				}
				return ret;
		}
		public CompanyList getCompanyList(){ 
				if(companyList == null){
						companyList = new CompanyList();
				}		
				return companyList;
		}
		public List<Company> getCompanies(){
				return companies;
		}
		public String getCompaniesTitle(){
				return companiesTitle;
		}

		public String populate(){
				String ret = SUCCESS;
				return ret;
		}

}





































