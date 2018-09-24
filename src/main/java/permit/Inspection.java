/**
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
package permit;
import java.util.List;
import java.sql.*;
import java.text.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Inspection implements java.io.Serializable{

    String id="", inspector_id="", date="", followup_date="",
				permit_num="",notes="",status="", has_picture="";
		String permit_id="", user_id="";
		static SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");	
		static final long serialVersionUID = 242L;	
		static Logger logger = LogManager.getLogger(Inspection.class);
		Address address = null; // needed for reports
		User inspector = null;
		Permit permit = null;
    String errors = "";
    public Inspection(){

    }
    public Inspection(String val){
				setId(val);
    }
    public Inspection(String val,
											String val2,
											String val3,
											String val4,
											String val5,
											String val6,
											String val7,
											String val8,
											String val9
											){
				setValues(val, val2, val3, val4, val5, val6, val7, val8, val9);
    }
		void setValues(
									 String val,
									 String val2,
									 String val3,
									 String val4,
									 String val5,
									 String val6,
									 String val7,
									 String val8,
									 String val9
									 ){
				setId(val);
				setInspector_id(val2);
				setDate(val3);
				setPermit_num(val4);
				setNotes(val5);
				setStatus(val6);
				setHas_picture(val7);		
				setFollowup_date(val8);
				if(!inspector_id.equals("") && val9 != null){
						inspector = new User(inspector_id, null, val9);
				}
		}
		//
		// setters
		//
		public void setId(String val){
				if(val != null)
						id = val;
    }
		public void setInspector_id(String val){
				if(val != null && !val.equals("-1"))
						inspector_id = val;
    }
		public void setDate(String val){
				if(val != null)
						date = val;
    }
		public void setPermit_num(String val){
				if(val != null)
						permit_num = val;
    }
		public void setNotes(String val){
				if(val != null)
						notes = val;
    }
		public void setStatus(String val){
				if(val != null && !val.equals("-1")){
						status = val;
				}
    }
		public void setFollowup_date(String val){
				if(val != null)
						followup_date = val;
    }
		public void setHas_picture(String val){
				if(val != null && (val.equals("y") || val.equals("true")))
						has_picture = "y";
    }
		public void setPermit_id(String val){
				if(val != null)
						permit_id = val;
    }
		public void setUser_id(String val){
				if(val != null)
						user_id = val;
    }	
    //
    // getters
    //
		public String getId(){
				return id;
    }	
		public String getInspector_id(){
				return inspector_id;
    }	
    public String getDate(){
				return date;
    }

		public String getPermit_num(){
				if(permit_num.equals("") && !permit_id.equals("")){
						getPermit();
						if(permit != null){
								permit_num = permit.getPermit_num();
						}
				}
				return permit_num;
		}	
		public String getNotes(){
				return notes;
    }	
		public String getStatus(){
				return status;
    }
		public String getFollowup_date(){
				return followup_date;
    }
		public boolean getHas_picture(){
				return !has_picture.equals("");
		}
		public String getHas_picture2(){ // for lists
				return has_picture;
		}
		public String toString(){
				return notes;
		}
		public Permit getPermit(){
				if(permit == null){
						if(!permit_id.equals("")){
								Permit one = new Permit(permit_id);
								String back = one.doSelect();
								if(back.equals("")){
										permit = one;
								}
						}
						else if(!permit_num.equals("")){
								PermitList pl = new PermitList();
								pl.setPermit_num(permit_num);
								String back = pl.find();
								if(back.equals("")){
										List<Permit> ones = pl.getPermits();
										if(ones != null && ones.size() > 0){
												permit = ones.get(0);
										}
								}
						}
				}
				return permit;
		}
		public User getInspector(){
				if(inspector == null && !inspector_id.equals("")){
						User one = new User(inspector_id);
						String back = one.doSelect();
						if(back.equals("")){
								inspector = one;
						}
				}
				return inspector;

		}
		public boolean hasAddress(){
				Excavation excavation = null;
				getPermit();
				if(permit != null){
						if(permit.hasExcavations()){
								excavation = permit.getLastExcavation();
						}
				}
				if(excavation  != null){
						address = excavation.getAddress();
				}
				return address != null;
		}
		public Address getAddress(){
				return address;
		}
		public String doSelect(){
		
				String msg="";
				String qq = "select i.id,i.inspector_id,date_format(i.date,'%m/%d/%Y'),i.permit_num,i.notes,i.status,i.has_picture,date_format(i.followup_date,'%m/%d/%Y'),u.fullname from inspections i ";
				qq += " left join users u on u.id=i.inspector_id ";
				qq += " where i.id=?";
				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				logger.debug(qq);
				try{
						con = Helper.getConnection();
						if(con == null){
								msg = "Could not connect ";
						}
						else{
								pstmt = con.prepareStatement(qq);
								pstmt.setString(1,id);
								rs = pstmt.executeQuery();
								if(rs.next()){
										setValues(rs.getString(1),
															rs.getString(2),
															rs.getString(3),
															rs.getString(4),
															rs.getString(5),
															rs.getString(6),
															rs.getString(7),
															rs.getString(8),
															rs.getString(9)
															);
								}
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
		public String doSave(){
		
				String msg = "";
				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				if(permit_num.equals("")){
						getPermit();
				}
				if(permit_num.equals("")){
						msg = "Permit number is required";
						return msg;
				}
				String qq = "insert into inspections values (0,"+
						"?,?,?,?,?,?,?)"; // has_picture 
				con = Helper.getConnection();
				if(con == null){
						msg = "Could not connect to Database ";
						logger.error(msg);
						return msg;
				}
				try {
						logger.debug(qq);			
						pstmt = con.prepareStatement(qq);
						msg += setFields(pstmt);
						pstmt.executeUpdate();
						qq = "select LAST_INSERT_ID() ";

						logger.debug(qq);

						pstmt = con.prepareStatement(qq);			
						rs = pstmt.executeQuery();
						if(rs.next()){
								id = rs.getString(1);
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
		public String setFields(PreparedStatement pstmt){

				String msg = "";
				String today = Helper.getToday();
				int jj = 1;
				try{
						if(inspector_id.equals(""))
								pstmt.setNull(jj++, Types.VARCHAR);
						else
								pstmt.setString(jj++, inspector_id);				
						if(date.equals(""))
								pstmt.setNull(jj++, Types.DATE);
						else
								pstmt.setDate(jj++, new java.sql.Date(dateFormat.parse(date).getTime()));				
						if(permit_num.equals(""))
								pstmt.setNull(jj++, Types.VARCHAR);
						else						
								pstmt.setString(jj++, permit_num);
						if(notes.equals(""))
								pstmt.setNull(jj++, Types.VARCHAR);
						else
								pstmt.setString(jj++, notes);
						if(status.equals(""))
								pstmt.setNull(jj++, Types.VARCHAR);
						else
								pstmt.setString(jj++, status);
						if(has_picture.equals(""))
								pstmt.setNull(jj++, Types.CHAR);
						else
								pstmt.setString(jj++, has_picture);			
						if(followup_date.equals(""))
								pstmt.setNull(jj++, Types.DATE);
						else
								pstmt.setDate(jj++, new java.sql.Date(dateFormat.parse(followup_date).getTime()));
				}
				catch(Exception ex){
						msg += ex;
						logger.error(msg);
				}
				return msg;
		}
		public String doUpdate(){
		
				String msg = "";
				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				//
		
				String qq = "";
				con = Helper.getConnection();
				if(con == null){
						msg = "Could not connect to Database ";
						logger.error(msg);
						return msg;
				}		
				try {
						//
						qq = "update inspections set ";
						qq += "inspector_id = ?,"; 
						qq += "date =?,"; 
						qq += "permit_num =?, ";
						qq += "notes = ?,";
						qq += "status = ?,";
						qq += "has_picture =?,";
						qq += "followup_date = ? ";
						qq += "where id=? ";
						logger.debug(qq);
						pstmt = con.prepareStatement(qq);
						setFields(pstmt);
						pstmt.setString(8, id);
						pstmt.executeUpdate();
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
		public String doDelete(){
		
				String msg = "";
				Connection con = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;		
				if(id.equals("")){
						msg = "company id not set";
						return msg;
				}
				//
				String qq = "delete from inspections where id=?";
				con = Helper.getConnection();
				if(con == null){
						msg = "Could not connect to Database ";
						logger.error(msg);
						return msg;
				}
				try {
						logger.debug(qq);
						pstmt = con.prepareStatement(qq);
						pstmt.setString(1, id);
						pstmt.executeUpdate();
				}
				catch (Exception ex){
						msg += ex+":"+qq;
						logger.error(msg);
				}
				finally{
						Helper.databaseDisconnect(con, pstmt, rs);
				}
				return msg;
		}			
	
}
