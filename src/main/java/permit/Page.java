/**
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
package permit;

import java.util.List;
import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Page{

    String page_num="Page 1 of 1";
		int neededLines = 0;
		static Logger logger = LogManager.getLogger(Page.class);
		static final long serialVersionUID = 271L;	
		List<Permit> permits = null;
    public Page(){
    }	
    public Page(String val){
				setPage_num(val);
    }	

		public String getPage_num(){
				return "";
				// return page_num;
    }
		public Integer getNeededLines(){
				return new Integer(neededLines);
    }	

		public void setPage_num(String val){
				if(val != null)
						page_num = val;
		}
		public void setNeedLines(int val){
				neededLines = val;
		}
		public void add(Permit val){
				if(val != null){
						if(permits == null){
								permits = new ArrayList<Permit>();
						}
						permits.add(val);
				}
		}
		public List<Permit> getPermits(){
				return permits;
		}
		public String toString(){
				return page_num;
		}
	
}
