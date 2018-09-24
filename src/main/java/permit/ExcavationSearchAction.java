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

public class ExcavationSearchAction extends TopAction{

		static final long serialVersionUID = 249L;	

		static Logger logger = LogManager.getLogger(ExcavationSearchAction.class);
		//
		List<Excavation> excavations = null;
		List<Type> utility_types = null;
		List<Address> addresses = null;
		ExcavationList excavList = null;
		String excavationsTitle = "Most recent excavations";
		ApiKey key = null;
		public String execute(){
				String ret = SUCCESS;
				String back = doPrepare();
				if(action.startsWith("Show")){
						getExcavList();
						excavList.setNoLimit();
						excavList.ensureAddress();
						back = excavList.find();
						if(!back.equals("")){
								addActionError(back);
						}
						else{
								List<Excavation> list = excavList.getExcavations();
								List<Address> addrList = excavList.getAddresses(); 
								if(list.size() > 80){
										excavations = list.subList(0,80);
										addActionMessage("Matched "+list.size()+" showing the first 80 because of google map limits ");
										addresses = addrList.subList(0,80);					
								}
								else{
										excavations = list;
										addresses = addrList;
								}
								if(excavList.coordsOnly()){
										ret = "map2"; // resizeable map
								}
								else{
										ret = "map";
								}
						}
				}
				else if(!action.equals("")){
						getExcavList();
						excavList.setNoLimit();
						back = excavList.find();
						if(!back.equals("")){
								addActionError(back);
						}
						else{
								
								// ret = "result";
								excavations = excavList.getExcavations();
								if(excavations == null || excavations.size() == 0){
										excavationsTitle = "No match found ";
										addActionMessage("No match found");
								}
								else if(excavations.size() == 1){
										Excavation one = excavations.get(0);
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
										ret = "excavResult";
										excavationsTitle = "Found "+excavations.size()+" records";
										addActionMessage("Found "+excavations.size()+" reocrds");
								}
						}
				}
				/*
				else{
						ExcavationList bl = new ExcavationList();
						back = bl.find();
						if(back.equals("")){
								List<Excavation> ones = bl.getExcavations();
								if(ones != null && ones.size() > 0){
										excavations = ones;
										addresses = bl.getAddresses();
								}
						}
						else{
								addActionError(back);
						}
				}
				*/
				return ret;
		}
		public boolean hasExcavations(){
				return excavations != null && excavations.size() > 0;
		}
		public ExcavationList getExcavList(){ 
				if(excavList == null){
						excavList = new ExcavationList();
				}		
				return excavList;
		}
		public List<Excavation> getExcavations(){
				return excavations;
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
		public String getExcavationsTitle(){
				return excavationsTitle;
		}

		public List<Type> getUtility_types(){
				TypeList bl = new TypeList("utility_types");
				String back = bl.find();
				if(back.equals("") && bl.getTypes() != null){
						utility_types = bl.getTypes();
				}
				return utility_types;

		}
		public ApiKey getKey(){
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

		public String populate(){
				String ret = SUCCESS;
				return ret;
		}

}





































