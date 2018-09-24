/**
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
package permit;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import javax.naming.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CompanyList{

    boolean debug = false;
		static final long serialVersionUID = 4L;		
		static Logger logger = LogManager.getLogger(CompanyList.class);
		String limit = "20", id="", name="", city="", address="", state="", zip="", phone="", sort_by = "";
		List<Company> companies = null;
    public CompanyList(){
    }
		public List<Company> getCompanies(){
				return companies;
		}
		public void setId(String val){
				if(val != null)
						id = val;
		}	
		public void setName(String val){
				if(val != null)
						name = val;
		}
		public void setAddress(String val){
				if(val != null)
						address = val;
		}
		public void setCity(String val){
				if(val != null)
						city = val;
		}
		public void setState(String val){
				if(val != null)
						state = val;
		}	
		public void setZip(String val){
				if(val != null)
						zip = val;
		}
		public void setPhone(String val){
				if(val != null)
						phone = val;
		}
		public void setSort_by(String val){
				if(val != null)
						sort_by = val;
		}
		public void setNo_limit(){
				limit = "";
		}
		public String getId(){
				return id;
		}	
		public String getName(){
				return name;
		}
		public String getAddress(){
				return address;
		}
		public String getCity(){
				return city;
		}
		public String getState(){
				return state;
		}	
		public String getZip(){
				return zip;
		}
		public String getPhone(){
				return phone;
		}
		public String getSort_by(){
				return sort_by;
		}	
    /**
		 * find all companies from the database
		 * @return errors if any
		 */
		public String find(){
		
				String msg="";
				String qq = "select c.id,c.name,c.address,c.city,c.state,c.zip,"+
						"c.phone,c.website,c.notes from companies c ";
				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				String qw = "";
				if(!id.equals("")){
						qw += "c.id = ? ";
				}
				if(!name.equals("")){
						if(!qw.equals("")) qw += " and ";
						qw += "c.name like ? ";
				}
				if(!address.equals("")){
						if(!qw.equals("")) qw += " and ";			
						qw += "c.address like ? ";
				}
				if(!city.equals("")){
						if(!qw.equals("")) qw += " and ";
						qw += "c.city like ? ";
				}
				if(!state.equals("")){
						if(!qw.equals("")) qw += " and ";
						qw += "c.state = ? ";
				}
				if(!zip.equals("")){
						if(!qw.equals("")) qw += " and ";
						qw += "c.zip like ? ";
				}
				if(!phone.equals("")){
						if(!qw.equals("")) qw += " and ";
						qw += "c.phone = ? ";
				}
				if(!qw.equals("")){
						qq += " where "+qw;
				}
				if(sort_by.equals("")){
						qq += " order by c.id DESC ";
				}
				else{
						qq += " order by "+sort_by;
				}
				if(!limit.equals("")){
						qq += " limit "+limit;
				}
				if(debug)
						logger.debug(qq);
				try{
						con = Helper.getConnection();
						if(con == null){
								msg = "Could not connect ";
								return msg;
						}
						pstmt = con.prepareStatement(qq);
						int jj=1;
						if(!id.equals("")){
								pstmt.setString(jj++, id);
						}
						if(!name.equals("")){
								pstmt.setString(jj++, "%"+name+"%");
						}
						if(!address.equals("")){
								pstmt.setString(jj++, "%"+address+"%");
						}
						if(!city.equals("")){
								pstmt.setString(jj++, city);
						}
						if(!state.equals("")){
								pstmt.setString(jj++, state);
						}
						if(!zip.equals("")){
								pstmt.setString(jj++, zip);
						}
						if(!phone.equals("")){
								pstmt.setString(jj++, phone);
						}			
						companies = new ArrayList<Company>();
						rs = pstmt.executeQuery();
						while(rs.next()){
								Company one = new Company(rs.getString(1),
																					rs.getString(2),
																					rs.getString(3),
																					rs.getString(4),
																					rs.getString(5),
																					rs.getString(6),
																					rs.getString(7),
																					rs.getString(8),
																					rs.getString(9));
								companies.add(one);
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
