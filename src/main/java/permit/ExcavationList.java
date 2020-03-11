/**
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
package permit;

import java.sql.*;
import java.util.*;
import javax.naming.directory.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ExcavationList {

    String id="", which_date="date", date_from="", date_to="",
				utility_type_id="",
				status="",cut_type="", permit_num="", address_id="";
		String address="", company_id="", contact_id="";
		String limit = "limit 20", sort_by="c.id DESC";
		String lat_from = "", lat_to="", long_from = "", long_to= "";
		String company_name = "";
		String searchStr = "";
		boolean activeOnly = false, ensureAddress=false;
		boolean coordsOnly = true;
		static final long serialVersionUID = 112L;	
		static Logger logger = LogManager.getLogger(ExcavationList.class);
		
		List<Excavation> excavations = null;
		List<Address> addresses = null;
    public ExcavationList(){
    }
    public ExcavationList(String val){
				setPermit_num(val);
    }
    public ExcavationList(String val, String val2){
				setPermit_num(val);
				setAddress_id(val2);
    }	
    public void setId(String val){
				if(val != null && !val.equals("")){
						id = val;
						coordsOnly = false;
				}
    }
		public void setPermit_num(String val){ // sendout batch id
				if(val != null && !val.equals("")){
						permit_num = val;
						coordsOnly = false;
				}
    }
    public void setAddress_id(String val){
				
				if(val != null && !val.equals("")){			
						address_id = val;
						coordsOnly = false;
				}
    }
    public void setAddress(String val){
				if(val != null && !val.equals("")){
						address = val;
						coordsOnly = false;
						
				}
    }	
    public void setStatus(String val){
				if(val != null && !val.equals("-1")){
						status = val;
						coordsOnly = false;
						
				}
    }
    public void setCut_type(String val){
				if(val != null && !val.equals("-1")){
						cut_type = val;
						coordsOnly = false;
				}
    }
    public void setUtility_type_id(String val){
				if(val != null && !val.equals("-1")){
						utility_type_id = val;
						coordsOnly = false;
				}
    }	
		public void setDate_from(String val){
				if(val != null && !val.equals("")){						
						date_from = val;
						coordsOnly = false;
				}
		}
		public void setDate_to(String val){
				if(val != null && !val.equals("")){					
						date_to = val;
						coordsOnly = false;
				}
    }
		public void setLat_from(String val){
				if(val != null){		
						lat_from = val;
				}
    }
		public void setLat_to(String val){
				if(val != null){		
						lat_to = val;
				}
    }
		public void setLong_from(String val){
				if(val != null){		
						long_from = val;
				}
    }
		public void setLong_to(String val){
				if(val != null){		
						long_to = val;
				}
    }	
		public void setWhich_date(String val){
				if(val != null){		
						which_date = val;
				}
    }
		public void setSort_by(String val){
				if(val != null){		
						sort_by = val;
				}
    }
		public void setCompany_id(String val){
				if(val != null && !val.equals("")){					
						company_id = val;
						coordsOnly = false;
				}
    }
		public void setCompany_name(String val){
				if(val != null && !val.equals("")){		
						company_name = val;
						coordsOnly = false;
				}
    }	
		public void setContact_id(String val){
				if(val != null && !val.equals("")){					
						contact_id = val;
						coordsOnly = false;
				}
    }
	
		public void setNoLimit(){
				limit = "";
		}
		public void ensureAddress(){
				ensureAddress = true;
		}
		public void setActiveOnly(){
				activeOnly = true;
		}
    //
    // getters
    //
		public String getDate_from(){
				return date_from ;
    }
		public String getDate_to(){
				return date_to ;
    }
		public String getWhich_date(){
				return which_date ;
    }
		public String getLat_from(){
				return lat_from ;
    }
		public String getLat_to(){
				return lat_to ;
    }
		public String getLong_from(){
				return long_from ;
    }
		public String getLong_to(){
				return long_to ;
    }	
		public String getStatus(){
				if(status.equals("")){
						return "-1";
				}
				return	status;
		}
		public String getCut_type(){
				if(cut_type.equals("")){
						return "-1";
				}
				return	cut_type;
		}
		public String getUtility_type_id(){
				if(utility_type_id.equals("")){
						return "-1";
				}
				return	utility_type_id;
		}	
		public String getPermit_num(){
				return permit_num;
		}
		
		public String getSort_by(){
				return sort_by;
    }
		public String getCompany_id(){
				return company_id;
    }
		public String getContact_id(){
				return contact_id;
    }
		public String getAddress(){
				return address;
    }
		public boolean hasCoords(){
				return !(lat_to.equals("") || lat_from.equals("") || long_to.equals("") || long_from.equals(""));
		}
		public boolean coordsOnly(){
				return coordsOnly && hasCoords(); // for resizeable map
		}
		public List<Excavation> getExcavations(){
				return excavations;
		}
		public List<Address> getAddresses(){
				logger.debug(" excav list addresses ");
				if(addresses == null && excavations != null){
						addresses = new ArrayList<Address>();
						for(Excavation one:excavations){
								Address addr = one.getAddress();
								if(addr != null){
										addresses.add(addr);
								}
						}
				}
				return addresses;
		}
		String find(){
				String msg = "";
				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				boolean ownTbl = false;
				//
				String qq = "", qw="", qf="";
				qq = "select c.id, " +                         
						"c.cut_description, " +
						"c.cut_type, " +
						"c.permit_num, " +
						"c.address_id, " +
						"c.depth,"+     				
						"c.width, " +
						"c.length, " + 
						"c.status, " + 				
						"c.utility_type_id, " +
						"u.name, "+
						"a.address,a.loc_lat,a.loc_long ";
				qf = " from excavcuts c "+
						" left join utility_types u on c.utility_type_id=u.id "+
						" left join addresses a on c.address_id=a.id "+
						" left join excavpermits p on p.permit_num = c.permit_num "+
						" left join company_contacts cc on cc.id=p.company_contact_id "+
						" left join companies cm on cm.id=cc.company_id ";
				if(!id.equals("")){
						if(!qw.equals("")) qw += " and ";
						qw += " c.id = ? ";
				}
				if(!cut_type.equals("")){
						if(!qw.equals("")) qw += " and ";
						qw += " c.cut_type = ? ";
				}
				
				if(!permit_num.equals("")){
						if(!qw.equals("")) qw += " and ";
						qw += " c.permit_num = ? ";
				}		
				if(!address_id.equals("")){
						if(!qw.equals("")) qw += " and ";
						qw += " c.address_id = ? ";
				}		
				if(!status.equals("")){
						if(!qw.equals("")) qw += " and ";
						qw += " c.status = ? ";
				}
				if(!utility_type_id.equals("")){
						if(!qw.equals("")) qw += " and ";
						qw += " c.utility_type_id = ? ";
				}
				if(!address.equals("")){
						if(!qw.equals("")) qw += " and ";
						qw += " a.address like ? ";
				}
				if(!company_id.equals("")){
						if(!qw.equals("")) qw += " and ";
						qw += " cc.company_id = ? ";
				}
				if(!contact_id.equals("")){
						if(!qw.equals("")) qw += " and ";
						qw += " cc.contact_id = ? ";
				}
				if(!lat_from.equals("")){
						if(!qw.equals("")) qw += " and ";			
						qw += " a.loc_lat >= ? ";
				}
				if(!lat_to.equals("")){
						if(!qw.equals("")) qw += " and ";
						qw += " a.loc_lat <= ? ";
				}
				if(!long_from.equals("")){
						if(!qw.equals("")) qw += " and ";
						qw += " a.loc_long >= ? ";
				}
				if(!long_to.equals("")){
						if(!qw.equals("")) qw += " and ";
						qw += " a.loc_long <= ? ";
				}
				if(activeOnly){ // active only permits
						if(!qw.equals("")) qw += " and ";
						qw += " (p.status != 'Work Complete' and p.status != 'Closed')";
				}
				if(!company_name.equals("")){
						if(!qw.equals("")) qw += " and ";
						qw += " cm.name like ? ";
				}
				if(ensureAddress){
						if(!qw.equals("")) qw += " and ";
						qw += " ((not a.loc_long is null) and a.loc_long < 0 and a.loc_lat >0)";
				}
				if(!date_from.equals("")){
						if(!qw.equals("")) qw += " and ";			
						qw += " p.start_date >= str_to_date('"+date_from+"','%m/%d/%Y')";
				}
				if(!date_to.equals("")){
						if(!qw.equals("")) qw += " and ";			
						qw += " p.start_date <= str_to_date('"+date_to+"','%m/%d/%Y')";
				}		
				qq += qf;
				if(!qw.equals("")){
						qq += " where "+qw;
				}
				if(!sort_by.equals("")){
						qq += " order by "+sort_by;
				}
				if(!limit.equals(""))
						qq += " "+limit;
				con = Helper.getConnection();
				if(con == null){
						msg = "Could not connect to Database ";
						logger.error(msg);
						return msg;
				}
				logger.debug(qq);
				try {
						pstmt = con.prepareStatement(qq);
						int jj=1;
						if(!id.equals("")){
								pstmt.setString(jj++, id);
						}
						if(!cut_type.equals("")){
								pstmt.setString(jj++, cut_type);
						}
						if(!permit_num.equals("")){
								pstmt.setString(jj++, permit_num);
						}			
						if(!address_id.equals("")){
								pstmt.setString(jj++, address_id);
						}			
						if(!status.equals("")){
								pstmt.setString(jj++, status);
						}			
						if(!utility_type_id.equals("")){
								pstmt.setString(jj++, utility_type_id);
						}
						if(!address.equals("")){
								pstmt.setString(jj++, "%"+address+"%");
						}
						if(!company_id.equals("")){
								pstmt.setString(jj++, company_id);
						}
						if(!contact_id.equals("")){
								pstmt.setString(jj++, contact_id);
						}
						if(!lat_from.equals("")){
								pstmt.setString(jj++, lat_from);
						}
						if(!lat_to.equals("")){
								pstmt.setString(jj++, lat_to);
						}
						if(!long_from.equals("")){
								pstmt.setString(jj++, long_from);
						}
						if(!long_to.equals("")){
								pstmt.setString(jj++, long_to);			
						}
						if(!company_name.equals("")){
								pstmt.setString(jj++, company_name);			
						}			
						rs = pstmt.executeQuery();
						excavations = new ArrayList<Excavation>();
						while(rs.next()){
								Excavation one = new Excavation(
																								rs.getString(1),
																								rs.getString(2),
																								rs.getString(3),
																								rs.getString(4),
																								rs.getString(5),
																								rs.getString(6),
																								rs.getString(7),
																								rs.getString(8),
																								rs.getString(9),
																								rs.getString(10),
																								rs.getString(11),
																								rs.getString(12),
																								rs.getString(13),
																								rs.getString(14)
																								);
								if(!excavations.contains(one))
										excavations.add(one);
						}
				}
				catch (Exception ex){
						msg += ex+":"+qq;
						logger.error(ex+":"+qq);
				}
				finally{
						Helper.databaseDisconnect(con, pstmt, rs);
				}
				return msg;		
		}
		
}
