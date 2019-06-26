package permit;
/**
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
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


public class RescheduleAction extends TopAction{

		static final long serialVersionUID = 313L;	
		static Logger logger = LogManager.getLogger(RescheduleAction.class);
		Reschedule reschedule = null;
		List<Bond> bonds = null;
		List<Insurance> insurances = null;
		//
		public String execute(){
				String ret = INPUT;
				String back = doPrepare();
				if(action.equals("Process")){
						ret = SUCCESS;
						logger.debug(" reschedule action ");
						reschedule.setUrl(url);
						if(activeMail)
								reschedule.setActiveMail();
						back = reschedule.doProcess();
						if(!back.equals("")){
								addActionError(back);
								logger.debug(" reschedule action "+back);
						}
						else{
								addActionMessage("Saved Successfully");
								bonds = reschedule.getBonds();
								insurances = reschedule.getInsurances();
						}
				}
				return ret;
		}
		
		public Reschedule getReschedule(){ 
				if(reschedule == null){
						reschedule = new Reschedule();
				}		
				return reschedule;
		}
		//
		public void setReschedule(Reschedule val){
				if(val != null)
						reschedule = val;
		}
		public String populate(){
				String ret = SUCCESS;
				return ret;
		}
		public List<Bond> getBonds(){
				return bonds;
		}
		public List<Insurance> getInsurances(){
				return insurances;
		}
		public boolean hasBonds(){
				return bonds != null && bonds.size() > 0;
		}
		public boolean hasInsurances(){
				return insurances != null && insurances.size() > 0;
		}		

}





































