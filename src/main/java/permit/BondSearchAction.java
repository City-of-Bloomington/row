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

public class BondSearchAction extends TopAction{

		static final long serialVersionUID = 259L;	
		static Logger logger = LogManager.getLogger(BondSearchAction.class);
		//
		List<Bond> bonds = null;
		BondList bondList = null;
		List<User> inspectors = null;
		List<Type> types = null;
		List<Type> bond_companies = null;
		String bondsTitle = "Most Recent Bonds";
		public String execute(){
				String ret = SUCCESS;
				String back = doPrepare();
				if(!action.equals("")){
						bondList.setNoLimit();
						back = bondList.find();
						if(!back.equals("")){
								addActionError(back);
						}
						else{
								// ret = "result";
								bonds = bondList.getBonds();
								if(bonds == null || bonds.size() == 0){
										bondsTitle = "No match found ";
								}
								else if(bonds.size() == 1){
										Bond one = bonds.get(0);
										id = one.getId();
										try{
												HttpServletResponse res = ServletActionContext.getResponse();
												String str = url+"bond.action?id="+id;
												res.sendRedirect(str);
												return super.execute();
										}catch(Exception ex){
												System.err.println(ex);
										}						
								}
								else{
										bondsTitle = "Found "+bonds.size()+" records";
								}
						}
				}
				else{
						BondList bl = new BondList();
						back = bl.find();
						if(back.equals("") && bl.getBonds() != null){
								bonds = bl.getBonds();
						}
						else{
								addActionError(back);
						}
				}
				return ret;
		}

		public BondList getBondList(){ 
				if(bondList == null){
						bondList = new BondList();
				}		
				return bondList;
		}
		public List<Bond> getBonds(){
				return bonds;
		}
		public String getBondsTitle(){
				return bondsTitle;
		}

		public List<Type> getTypes(){
				TypeList bl = new TypeList("bond_types");
				String back = bl.find();
				if(back.equals("") && bl.getTypes() != null){
						types = bl.getTypes();
				}
				return types;
		}
		public List<Type> getBond_companies(){
				TypeList bl = new TypeList("bond_companies");
				String back = bl.find();
				if(back.equals("") && bl.getTypes() != null){
						bond_companies = bl.getTypes();
				}
				return bond_companies;
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





































