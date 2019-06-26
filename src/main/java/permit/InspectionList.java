/**
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
package permit;
import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;
import java.sql.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class InspectionList{

    String permit_num="",inspector_id="",status="",date_from="",date_to="";
		String which_date = "i.date", limit="20", sort_by="id DESC", id="",
				company_id="", contact_id="";
		static final long serialVersionUID = 215L;		
		static Logger logger = LogManager.getLogger(InspectionList.class);
		static SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");	
		List<Inspection> inspections = null;
    String errors = "";
		boolean activeStatus = false; // all
    public InspectionList(){
    }
		public void setId(String val){
				if(val != null)
						id = val;
    }	
		public void setPermit_num(String val){
				if(val != null)
						permit_num = val;
    }
		public void setInspector_id(String val){
				if(val != null && !val.equals("-1"))
						inspector_id = val;
    }
		public void setDate_from(String val){
				if(val != null)
						date_from = val;
    }
		public void setDate_to(String val){
				if(val != null)
						date_to = val;
    }	
		public void setStatus(String val){
				if(val != null && !val.equals("-1"))
						status = val;
    }
		public void setActiveStatusOnly(){
				activeStatus = true;
		}
		public void setNoLimit(){
				limit="";
		}
		public void setCompany_id(String val){
				if(val != null)
						company_id = val;
    }
		public void setContact_id(String val){
				if(val != null)
						contact_id = val;
    }		
		public void setSortBy(String val){
				if(val != null)
						sort_by = val;
		}
		public String getId(){
				return id;
		}
		public String getDate_from(){
				return date_from;
    }
		public String getDate_to(){
				return date_to;
    }	
		public String getStatus(){
				return status;
    }
		public String getPermit_num(){
				return permit_num;
    }
		public String getInspector_id(){
				return inspector_id;
    }
		public String getCompany_id(){
				return company_id;
    }
		public String getContact_id(){
				return contact_id;
    }	
		public String getStort_by(){
				return sort_by;
		}
		public List<Inspection> getInspections(){
				return inspections;
		}
    /**
		 * find all the inspections from the database
		 * @return errors if any
		 */
		public String find(){
		
				String msg="", qw="";
				String qq = "select i.id,i.inspector_id,date_format(i.date,'%m/%d/%Y'),"+
						" i.permit_num,i.notes,i.status,i.has_picture,"+
						" date_format(i.followup_date,'%m/%d/%Y'),u.fullname "+
						" from inspections i ";
				qq += " left join users u on u.id=i.inspector_id ";
				qq += " left join excavpermits p on p.permit_num = i.permit_num "+
						" left join company_contacts cc on cc.id=p.company_contact_id ";		
				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				if(!id.equals("")){
						if(!qw.equals("")) qw += " and ";
						qw += "i.id = ? ";
				}		
				if(!inspector_id.equals("")){
						if(!qw.equals("")) qw += " and ";
						qw += "i.inspector_id = ? ";
				}
				if(!permit_num.equals("")){
						if(!qw.equals("")) qw += " and ";
						qw += "i.permit_num = ? ";
				}
				if(activeStatus){
						if(!qw.equals("")) qw += " and ";
						qw += " i.status <> 'Completed' and (p.status in ('Active','Violation')) ";
				}
				else if(!status.equals("")){
						if(!qw.equals("")) qw += " and ";
						qw += "i.status = ? ";
				}
				if(!date_from.equals("")){
						if(!qw.equals("")) qw += " and ";
						qw += which_date+" >= ? ";
				}
				if(!date_to.equals("")){
						if(!qw.equals("")) qw += " and ";
						qw += which_date+" <= ? ";
				}
				if(!company_id.equals("")){
						if(!qw.equals("")) qw += " and ";
						qw += " cc.company_id = ? ";
				}
				if(!contact_id.equals("")){
						if(!qw.equals("")) qw += " and ";
						qw += " cc.contact_id = ? ";
				}				
				if(!qw.equals("")){
						qq += " where "+qw;
				}
				if(!sort_by.equals(""))
						qq += " order by "+sort_by;
				if(!limit.equals("")){
						qq += " limit "+limit;
				}
				logger.debug(qq);
				con = Helper.getConnection();
				if(con == null){
						msg = "Could not connect ";
						return msg;
				}				
				try{
						pstmt = con.prepareStatement(qq);
						int jj = 1;
						if(!id.equals("")){
								pstmt.setString(jj++, id);
						}				
						if(!inspector_id.equals("")){
								pstmt.setString(jj++, inspector_id);
						}		
						if(!permit_num.equals("")){
								pstmt.setString(jj++, permit_num);
						}
						if(!activeStatus && !status.equals("")){
								pstmt.setString(jj++, status);
						}
						if(!date_from.equals("")){
								pstmt.setDate(jj++, new java.sql.Date(dateFormat.parse(date_from).getTime()));		
						}
						if(!date_to.equals("")){
								pstmt.setDate(jj++, new java.sql.Date(dateFormat.parse(date_to).getTime()));		
						}
						if(!company_id.equals("")){
								pstmt.setString(jj++, company_id);
						}
						if(!contact_id.equals("")){
								pstmt.setString(jj++, contact_id);
						}			
						inspections = new ArrayList<Inspection>();
						rs = pstmt.executeQuery();
						while(rs.next()){
								Inspection one = new Inspection(rs.getString(1),
																								rs.getString(2),
																								rs.getString(3),
																								rs.getString(4),
																								rs.getString(5),
																								rs.getString(6),
																								rs.getString(7),
																								rs.getString(8),
																								rs.getString(9));
								inspections.add(one);
						}
				}
				catch(Exception ex){
						msg += " "+ex;
						logger.error(ex+" : "+qq);
				}
				finally{
						Helper.databaseDisconnect(con, pstmt, rs);
				}
				return msg;
		}
	
}
