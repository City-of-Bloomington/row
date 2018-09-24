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


public class ContactSearchAction extends TopAction{

		static final long serialVersionUID = 347L;	
		static Logger logger = LogManager.getLogger(ContactSearchAction.class);
		//
		List<Contact> contacts = null;
		ContactList contactList = null;

		List<Type> types = null;		

		String contactsTitle = "Most Recent Contacts";
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
						contactList.setNo_limit();
						back = contactList.find();
						if(!back.equals("")){
								addActionError(back);
						}
						else{
								// ret = "result";
								contacts = contactList.getContacts();
								if(contacts == null || contacts.size() == 0){
										contactsTitle = "No match found ";
								}
								else if(contacts.size() == 1){
										Contact one = contacts.get(0);
										id = one.getId();
										try{
												HttpServletResponse res = ServletActionContext.getResponse();
												String str = url+"contact.action?id="+id;
												res.sendRedirect(str);
												return super.execute();
										}catch(Exception ex){
												System.err.println(ex);
										}						
								}
								else{
										contactsTitle = "Found "+contacts.size()+" records";
								}
						}
				}
				else{
						ContactList bl = new ContactList();
						back = bl.find();
						if(back.equals("") && bl.getContacts() != null){
								contacts = bl.getContacts();
						}
						else{
								addActionError(back);
						}
				}
				return ret;
		}

		public ContactList getContactList(){ 
				if(contactList == null){
						contactList = new ContactList();
				}		
				return contactList;
		}
		public List<Contact> getContacts(){
				return contacts;
		}
		public String getContactsTitle(){
				return contactsTitle;
		}

		public List<Type> getTypes(){
				TypeList bl = new TypeList("contact_types");
				String back = bl.find();
				if(back.equals("") && bl.getTypes() != null){
						types = bl.getTypes();
				}
				return types;
		}
		public String populate(){
				String ret = SUCCESS;
				return ret;
		}

}





































