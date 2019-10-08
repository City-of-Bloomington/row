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

public class CutSearchAction extends TopAction{

		static final long serialVersionUID = 249L;	

		static Logger logger = LogManager.getLogger(CutSearchAction.class);
		//
		List<Excavation> cuts = null;
		List<Type> utility_types = null;
		List<Address> addresses = null;
		ExcavationList cutList = null;
		String cutsTitle = "Most recent excavations";
		ApiKey key = null;
		public String execute(){
				String ret = SUCCESS;
				String back = doPrepare();
				if(action.startsWith("Show")){
						logger.debug(" cut action save ");							
						getCutList();
						cutList.setNoLimit();
						cutList.ensureAddress();
						back = cutList.find();
						if(!back.equals("")){
								addActionError(back);
								logger.error(" cut action "+back);
						}
						else{
								List<Excavation> list = cutList.getExcavations();
								List<Address> addrList = cutList.getAddresses(); 
								if(list.size() > 80){
										cuts = list.subList(0,80);
										addActionMessage("Matched "+list.size()+" showing the first 80 because of google map limits ");
										if(addrList != null && addrList.size() > 0)
												addresses = addrList.subList(0,80);					
								}
								else{
										cuts = list;
										addresses = addrList;
								}
								if(cutList.coordsOnly()){
										ret = "map2"; // resizeable map
								}
								else{
										ret = "map";
								}
						}
				}
				else if(action.startsWith("Export")){
						logger.debug(" cut action save ");							
						getCutList();
						cutList.setNoLimit();
						cutList.ensureAddress();
						back = cutList.find();
						if(!back.equals("")){
								addActionError(back);
								logger.error(" cut action "+back);
						}
						else{
								List<Excavation> list = cutList.getExcavations();
								List<Address> addrList = cutList.getAddresses(); 
								addActionMessage("Matched "+list.size());
								cuts = list;
								addresses = addrList;
								ret = "csv";
						}
				}
				else if(!action.equals("")){
						logger.debug(" cut action ");
						getCutList();
						cutList.setNoLimit();
						back = cutList.find();
						if(!back.equals("")){
								addActionError(back);
								logger.error("cut action  "+back);
						}
						else{
								cuts = cutList.getExcavations();
								if(cuts == null || cuts.size() == 0){
										cutsTitle = "No match found ";
										addActionMessage("No match found");
								}
								else if(cuts.size() == 1){
										Excavation one = cuts.get(0);
										id = one.getId();
										try{
												HttpServletResponse res = ServletActionContext.getResponse();
												String str = url+"excavation.action?id="+id;
												res.sendRedirect(str);
												return super.execute();
										}catch(Exception ex){
												System.err.println(ex);
										}						
								}
								else{
										ret = "cutResult";
										cutsTitle = "Found "+cuts.size()+" records";
										addActionMessage("Found "+cuts.size()+" reocrds");
								}
						}
				}
				return ret;
		}
		public boolean hasCuts(){
				return cuts != null && cuts.size() > 0;
		}
		public ExcavationList getCutList(){ 
				if(cutList == null){
						cutList = new ExcavationList();
				}		
				return cutList;
		}
		public List<Excavation> getCuts(){
				return cuts;
		}
		public boolean hasAddresses(){
				return addresses != null && addresses.size() > 0;
		}
		public List<Address> getAddresses(){
				return addresses;
		}
		//
		// this is the center point for the list of addresses
		// to draw the map around it
		//
		public Address getAddress(){
				logger.debug("cut addresses  ");
				double lat=0, lng=0;
				if(addresses != null && addresses.size() > 0){
						for(Address addr:addresses){
								lat += addr.getLoc_lat_dbl();
								lng += addr.getLoc_long_dbl();
						}
						lat = lat /(addresses.size());
						lng = lng /(addresses.size());
						Address addr = new Address(" ", " ",""+lat,""+lng);
						return addr;
				}
				return new Address();
		}
		public String getCutsTitle(){
				return cutsTitle;
		}

		public List<Type> getUtility_types(){
				logger.debug("get util types  ");
				TypeList bl = new TypeList("utility_types");
				String back = bl.find();
				if(back.equals("") && bl.getTypes() != null){
						utility_types = bl.getTypes();
				}
				else{
						logger.error("util types  "+back);
				}
				return utility_types;

		}
		public ApiKey getKey(){
				logger.debug("cut get key  ");
				ApiKeyList akl = new ApiKeyList();
				String back = akl.find();
				if(back.equals("")){
						List<ApiKey> keys = akl.getKeys();
						if(keys != null && keys.size() > 0){
								key = keys.get(0);
						}
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

}





































