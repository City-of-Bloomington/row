/**
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */

package permit;

import java.util.*;
import java.io.*;
import java.text.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.struts2.ServletActionContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger; 


public class ReportAction extends TopAction{

		static final long serialVersionUID = 180L;	
   
		String report_type="";
		static Logger logger = LogManager.getLogger(ReportAction.class);
		Report report = null;
		List<String> years = null;
		List<Type> report_types = null;
		//
		public String execute(){
				String back = doPrepare();
				String ret = INPUT;            // default
				if(action.equals("Submit")){
						ret = SUCCESS;
						logger.debug(" report action ");
						back = report.find();
						if(!back.equals("")){
								addActionError(back);
								ret = INPUT;
								logger.debug(" report action "+back);
						}
				}
				return ret;
		}			 
		public Report getReport(){
				if(report == null){
						report = new Report();
				}
				return report;
		}
		public void setReport(Report val){
				if(val != null)
						report = val;
		}
		public List<Type> getReport_types(){
				if(report_types == null){
						logger.debug(" report action types ");
						report_types = new ArrayList<Type>(4);
						report_types.add(new Type("active_permits","Active Permits"));
						report_types.add(new Type("cbu","CBU Permits"));
						report_types.add(new Type("vectren","Vectren Permits"));
						report_types.add(new Type("patch","Temporary Patch"));
						report_types.add(new Type("bond","Active Bonds"));
						report_types.add(new Type("bond0","Zero Bonds"));
						report_types.add(new Type("bondAbout","About To Expire Bonds"));
						report_types.add(new Type("insurAbout","About To Expire Insurances"));
						report_types.add(new Type("inspections","Active Inspections for Active Permits"));
				}
				return report_types;
		}

}





































