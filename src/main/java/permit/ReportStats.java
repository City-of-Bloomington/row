/**
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 */
package permit;

import java.util.*;
import java.sql.*;
import java.io.*;
import java.text.*;
import javax.sql.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger; 

public class ReportStats{
	
		static Logger logger = LogManager.getLogger(ReportStats.class);
		static final long serialVersionUID = 174L;
		static SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		NumberFormat currFormat = NumberFormat.getCurrencyInstance();	
		String year = "",date_from="",date_to="";
		String title = "", which_date="",by="", day="", prev_year="", next_year="";
		boolean debug = false;
		boolean
				permits=true,
				excavations=false,
				invoices=false,
				bonds=false;
		List<List<ReportRow>> all = new ArrayList<List<ReportRow>>();
		Hashtable<String, ReportRow> all2 = new Hashtable<String, ReportRow>(4);
		DecimalFormat decFormat = new DecimalFormat("###,###.##");
		List<ReportRow> rows = null; 
		ReportRow columnTitle = null;
		//
		int totalIndex = 2; // DB index for row with 2 items
		public ReportStats(){

		}

		public void setYear(String val){
				if(val != null && !val.equals("-1"))
						year = val;
		}
		public void setPrev_year(String val){
				if(val != null && !val.equals("-1"))
						prev_year = val;
		}
		public void setNext_year(String val){
				if(val != null && !val.equals("-1"))
						next_year = val;
		}	

		public void setDate_from(String val){
				if(val != null)
						date_from = val;
		}	
		public void setDate_to(String val){
				if(val != null)
						date_to = val;
		}
		public void setBy(String val){
				if(val != null)
						by = val;
		}
		public void setPermits(Boolean val){
				permits = val;
		}
		public void setExcavations(Boolean val){
				excavations = val;
		}
		public void setInvoices(Boolean val){
				invoices = val;
		}
		public void setBonds(Boolean val){
				bonds = val;
		}	
		//
		// getters
		//
		public String getYear(){
				return year;
		}
		public String getPrev_year(){
				return prev_year;
		}
		public String getNext_year(){
				return next_year;
		}	

		public String getDate_from(){
				return date_from ;
		}	
		public String getDate_to(){
				return date_to ;
		}
		public String getBy(){
				return by ;
		}

		public boolean getPermits(){
				return permits;
		}	
		public boolean getExcavations(){
				return excavations;
		}
		public boolean getInvoices(){
				return invoices;
		}
		public boolean getBonds(){
				return bonds;
		}	
		public String getTitle(){
				return title;
		}	
		public List<ReportRow> getRows(){
				return rows;
		}

		public List<List<ReportRow>> getAll(){
				return all;
		}


		public ReportRow getColumnTitle(){
				return columnTitle;
		}
		public String find(){
				String msg = "";
				if(permits){
						msg += permitStats();
				}
				if(excavations){
						msg +=  excavationStats();
				}
				if(invoices){
						msg += invoiceStats();
				}
				if(bonds){
						msg += bondStats();
				}		
				return msg;
		}
		void setTitle(){
				if(!year.equals("")){
						title +=" "+year;
				}
				else {
						if(!date_from.equals("")){
								title += " "+date_from;
						}
						if(!date_to.equals("")){
								if(!date_from.equals(date_to)){
										title += " - "+date_to;
								}
						}
				}
		}
		/**
		 * permit stats
		 */
		public String permitStats(){
		
				Connection con = null;
				PreparedStatement pstmt = null;
				PreparedStatement pstmt2 = null;
				ResultSet rs = null;

				String msg = "";
				String which_date = "";
				String qq = "", qq2="", qw="", qg="";
				which_date = " p.date ";
				title = "Permit Stats ";
				setTitle();
				rows = new ArrayList<ReportRow>();		
				ReportRow one = new ReportRow(debug, 2);
				one.setRow("Title", title);
				rows.add(one);
				one = new ReportRow(debug, 3);
				one.setRow("Status","Count","Fees");
				rows.add(one);
				//
				// Permits
				//
				qq = " select p.status status, count(*) amount, sum(fee) from excavpermits p ";
				qg = " group by status having amount > 0 ";
				//
				// we do not need
				//
				qq2 = " select count(*) from excavpermits p ";
		
				if(!year.equals("")){
						if(!qw.equals(""))
								qw += " and ";
						qw += " year("+which_date+") = ? ";
				}
				else {
						if(!date_from.equals("")){
								if(!qw.equals(""))
										qw += " and ";
								qw += which_date+" >= ? ";
						}
						if(!date_to.equals("")){
								if(!qw.equals(""))
										qw += " and ";
								qw += which_date+" <= ? ";
						}
				}
				if(!qw.equals("")){
						qq += " where "+qw;
						qq2 += " where "+qw;
				}
				qq += qg;
				logger.debug(qq);
				con = Helper.getConnection();
				if(con == null){
						msg = "Could not connect ";
						return msg;
				}
				try{
						pstmt = con.prepareStatement(qq);
						int jj=1;
						if(!year.equals("")){
								pstmt.setString(jj, year);
								jj++;
						}
						else {
								if(!date_from.equals("")){
										pstmt.setDate(jj, new java.sql.Date(dateFormat.parse(date_from).getTime()));
										jj++;
								}
								if(!date_to.equals("")){
										pstmt.setDate(jj, new java.sql.Date(dateFormat.parse(date_to).getTime()));
										jj++;
								}
						}
						rs = pstmt.executeQuery();
						double total = 0.;
						int count=0;
						while(rs.next()){
								String str = rs.getString(1);
								if(str == null) str = "Unknown";
								one = new ReportRow(debug, 3);
								one.setRow(str,
													 rs.getString(2),
													 currFormat.format(rs.getDouble(3)));
								count += rs.getInt(2);
								total += rs.getDouble(3);
								rows.add(one);
						}
						one = new ReportRow(debug, 3);			
						one.setRow("Total",""+count, currFormat.format(total));
						rows.add(one);
						all.add(rows);
			
				}catch(Exception e){
						msg += e+":"+qq;
						logger.error(msg);
				}
				finally{
						Helper.databaseDisconnect(con, rs, pstmt, pstmt2);
				}		
				return msg;
		}
		/**
		 * excavation stats
		 */
		public String excavationStats(){
		
				Connection con = null;
				PreparedStatement pstmt = null;
				PreparedStatement pstmt2 = null;
				PreparedStatement pstmt3 = null;		
				ResultSet rs = null;

				String msg = "";
				String which_date = "";
				String qq = "", qq2="", qq3="", qw="", qg="";
				which_date="p.date "; // we are using permit date since excavation has no date
				title = "Excavations by Cut Types ";
				setTitle();
				rows = new ArrayList<ReportRow>();		
				ReportRow one = new ReportRow(debug, 2);
				one.setRow("Title", title);
				rows.add(one);
				one = new ReportRow(debug, 4);
				one.setRow("Cut Type","Count","Length","Area");
				rows.add(one);
				//
				// excav
				//
				qq = " select e.cut_type type, count(*) amount, sum(e.length),sum(e.length*e.width) from excavcuts e join excavpermits p on p.permit_num=e.permit_num ";
				qg = " group by type having amount > 0 ";
				//
				//
				qq2 = " select e.status type, count(*) amount, sum(e.length),sum(e.length*e.width) from excavcuts e join excavpermits p on p.permit_num=e.permit_num ";
				qq3 = " select u.name type, count(*) amount, sum(e.length), sum(e.length*e.width) from excavcuts e left join utility_types u on u.id=e.utility_type_id  join excavpermits p on p.permit_num=e.permit_num ";		
				if(!year.equals("")){
						if(!qw.equals(""))
								qw += " and ";
						qw += " year("+which_date+") = ? ";
				}
				else {
						if(!date_from.equals("")){
								if(!qw.equals(""))
										qw += " and ";
								qw += which_date+" >= ? ";
						}
						if(!date_to.equals("")){
								if(!qw.equals(""))
										qw += " and ";
								qw += which_date+" <= ? ";
						}
				}
				if(!qw.equals("")){
						qq += " where "+qw;
						qq2 += " where "+qw;
						qq3 += " where "+qw;			
				}
				qq += qg;
				qq2 += qg;
				qq3 += qg;
				logger.debug(qq);
				con = Helper.getConnection();
				if(con == null){
						msg = "Could not connect ";
						return msg;
				}			
				try{
						pstmt = con.prepareStatement(qq);
						pstmt2 = con.prepareStatement(qq2);
						pstmt3 = con.prepareStatement(qq3);			
						int jj=1;
						if(!year.equals("")){
								pstmt.setString(jj, year);
								pstmt2.setString(jj, year);
								pstmt3.setString(jj, year);				
								jj++;
						}
						else {
								if(!date_from.equals("")){
										pstmt.setDate(jj, new java.sql.Date(dateFormat.parse(date_from).getTime()));
										pstmt2.setDate(jj, new java.sql.Date(dateFormat.parse(date_from).getTime()));
										pstmt3.setDate(jj, new java.sql.Date(dateFormat.parse(date_from).getTime()));					
										jj++;
								}
								if(!date_to.equals("")){
										pstmt.setDate(jj, new java.sql.Date(dateFormat.parse(date_to).getTime()));
										pstmt2.setDate(jj, new java.sql.Date(dateFormat.parse(date_to).getTime()));
										pstmt3.setDate(jj, new java.sql.Date(dateFormat.parse(date_to).getTime()));					
										jj++;
								}
						}
						rs = pstmt.executeQuery();
						int total = 0, count=0, total2=0;
						while(rs.next()){
								String str = rs.getString(1);
								if(str == null) str = "Unknown";
								one = new ReportRow(debug, 4);
								one.setRow(str,
													 rs.getString(2),
													 rs.getString(3),
													 rs.getString(4));
								count += rs.getInt(2);
								total += rs.getInt(3);
								total2 += rs.getInt(4);
								rows.add(one);
						}
						one = new ReportRow(debug, 4);
						one.setRow("Total",""+count,""+total,""+total2);
						rows.add(one);
						all.add(rows);
						//
						title = "Excavations by Statuses ";
						setTitle();
						rows = new ArrayList<ReportRow>();		
						one = new ReportRow(debug, 3);
						one.setRow("Title", title);
						rows.add(one);			
						one = new ReportRow(debug, 4);
						one.setRow("Status","Count","Length","Area");
						rows.add(one);			
						//
						rs = pstmt2.executeQuery();
						total = 0; count=0; total2=0;
						while(rs.next()){
								String str = rs.getString(1);
								if(str == null) str = "Unknown";
								one = new ReportRow(debug, 4);
								one.setRow(str,
													 rs.getString(2),
													 rs.getString(3),
													 rs.getString(4));
								count += rs.getInt(2);
								total += rs.getInt(3);
								total2 += rs.getInt(4);
								rows.add(one);
						}
						one = new ReportRow(debug, 4);
						one.setRow("Total",""+count,""+total,""+total2);
						rows.add(one);
						all.add(rows);
						// 
						title = "Excavations by Utility Types ";
						setTitle();
						rows = new ArrayList<ReportRow>();		
						one = new ReportRow(debug, 2);
						one.setRow("Title", title);
						rows.add(one);			
						one = new ReportRow(debug, 4);
						one.setRow("Utitlity Type","Count","Length","Area");
						rows.add(one);
						//
						rs = pstmt3.executeQuery();
						total = 0; count=0; total2=0;
						while(rs.next()){
								String str = rs.getString(1);
								if(str == null) str = "Unknown";
								one = new ReportRow(debug, 4);
								one.setRow(str,
													 rs.getString(2),
													 rs.getString(3),
													 rs.getString(4));
								count += rs.getInt(2);
								total += rs.getInt(3);
								total2 += rs.getInt(4);
								rows.add(one);
						}
						one = new ReportRow(debug, 4);			
						one.setRow("Total",""+count,""+total,""+total2);
						rows.add(one);
						all.add(rows);			
			
				}catch(Exception e){
						msg += e+":"+qq;
						logger.error(msg);
				}
				finally{
						Helper.databaseDisconnect(con, rs, pstmt, pstmt2, pstmt3);
				}		
				return msg;
		}	
		/**
		 * invoices stats
		 */
		public String invoiceStats(){
		
				Connection con = null;
				PreparedStatement pstmt = null;
				PreparedStatement pstmt2 = null;
				ResultSet rs = null;
				//
				String msg = "";
				String which_date = "";
				String qq = "", qq2="", qw="", qg="";
				which_date = " i.date ";
				title = "Invoices Stats ";
				setTitle();
				rows = new ArrayList<ReportRow>();		
				ReportRow one = new ReportRow(debug, 2);
				one.setRow("Title", title);
				rows.add(one);
				one = new ReportRow(debug, 3);
				one.setRow("Status","Count","Fees");
				rows.add(one);
				//
				// Permits
				//
				qq = " select i.status status, count(*) amount, sum(p.fee) from invoices i join excavpermits p on p.invoice_id=i.id ";
				//
				qg = " group by status having amount > 0 ";
				//
				// we do not need
				//
				if(!year.equals("")){
						if(!qw.equals(""))
								qw += " and ";
						qw += " year("+which_date+") = ? ";
				}
				else {
						if(!date_from.equals("")){
								if(!qw.equals(""))
										qw += " and ";
								qw += which_date+" >= ? ";
						}
						if(!date_to.equals("")){
								if(!qw.equals(""))
										qw += " and ";
								qw += which_date+" <= ? ";
						}
				}
				if(!qw.equals("")){
						qq += " where "+qw;
						qq2 += " where "+qw;
				}
				qq += qg;
				logger.debug(qq);
				con = Helper.getConnection();
				if(con == null){
						msg = "Could not connect ";
						return msg;
				}
				try{
						pstmt = con.prepareStatement(qq);
						int jj=1;
						if(!year.equals("")){
								pstmt.setString(jj, year);
								jj++;
						}
						else {
								if(!date_from.equals("")){
										pstmt.setDate(jj, new java.sql.Date(dateFormat.parse(date_from).getTime()));
										jj++;
								}
								if(!date_to.equals("")){
										pstmt.setDate(jj, new java.sql.Date(dateFormat.parse(date_to).getTime()));
										jj++;
								}
						}
						rs = pstmt.executeQuery();
						double total = 0.;
						int count=0;
						while(rs.next()){
								String str = rs.getString(1);
								if(str == null) str = "Unknown";
								one = new ReportRow(debug, 3);
								one.setRow(str,
													 rs.getString(2),
													 "$"+rs.getString(3));
								count += rs.getInt(2);
								total += rs.getDouble(3);
								rows.add(one);
						}
						one = new ReportRow(debug, 3);			
						one.setRow("Total",""+count, currFormat.format(total));
						rows.add(one);
						all.add(rows);
			
				}catch(Exception e){
						msg += e+":"+qq;
						logger.error(msg);
				}
				finally{
						Helper.databaseDisconnect(con, rs, pstmt, pstmt2);
				}		
				return msg;
		}
		/**
		 * bonds stats
		 */
		public String bondStats(){
		
				Connection con = null;
				PreparedStatement pstmt = null;
				PreparedStatement pstmt2 = null;
				ResultSet rs = null;

				String msg = "";
				String which_date = "";
				String qq = "", qq2="", qw="", qw2="", qg="";
				which_date = " b.expire_date ";
				title = "Bonds Expire Status (as of today)";
				String today = Helper.getToday();
				rows = new ArrayList<ReportRow>();		
				ReportRow one = new ReportRow(debug, 2);
				one.setRow("Title", title);
				rows.add(one);
				one = new ReportRow(debug, 2);
				one.setRow("Status ","Count");
				rows.add(one);
				int yy = Helper.getCurrentYear();
				//
				// bonds
				//
				qq = " select 'Expired', count(*) from bonds b ";
				qq2 = " select 'Active', count(*) from bonds b ";
				//
				// we do not need
				//
				qw =  " where "+which_date+" < ? ";
				qw2 = " where "+which_date+" >= ? ";
				//
				qq += qw;
				qq2 += qw2;
				//
				qq += " union "+qq2;
				//
				logger.debug(qq);
				con = Helper.getConnection();
				if(con == null){
						msg = "Could not connect ";
						return msg;
				}
				try{
						pstmt = con.prepareStatement(qq);
						int jj=1;
						pstmt.setDate(jj++, new java.sql.Date(dateFormat.parse(today).getTime()));
						pstmt.setDate(jj++, new java.sql.Date(dateFormat.parse(today).getTime()));					
						rs = pstmt.executeQuery();
						int count=0;
						while(rs.next()){
								one = new ReportRow(debug, 2);
								one.setRow(rs.getString(1), rs.getString(2));
								count += rs.getInt(2);
								rows.add(one);
						}
						one = new ReportRow(debug, 2);			
						one.setRow("Total",""+count);
						rows.add(one);
						all.add(rows);
						qq = " select 'Expired before "+yy+"', count(*) from bonds b where year("+which_date+") < ? ";
						qq += " union select 'Expired "+yy+"', count(*) from bonds b where year("+which_date+") = ? and "+which_date+" < ? ";
						qq += " union select 'Will Expire "+yy+"', count(*) from bonds b where year("+which_date+") = ? and "+which_date+" >= ? ";			
						qq += " union select 'Will Expire After "+yy+"', count(*) from bonds b where year("+which_date+") > ? ";			
						con = Helper.getConnection();
						pstmt = con.prepareStatement(qq);
						jj=1;
						pstmt.setInt(jj++, yy);
						pstmt.setInt(jj++, yy);			
						pstmt.setDate(jj++, new java.sql.Date(dateFormat.parse(today).getTime()));
						pstmt.setInt(jj++, yy);		
						pstmt.setDate(jj++, new java.sql.Date(dateFormat.parse(today).getTime()));
						pstmt.setInt(jj++, yy);		
						rows = new ArrayList<ReportRow>();		
						one = new ReportRow(debug, 2);
						one.setRow("Title", title);
						rows.add(one);
						one = new ReportRow(debug, 2);
						one.setRow("Status ","Count");
						rows.add(one);
						rs = pstmt.executeQuery();
						count=0;
						while(rs.next()){
								one = new ReportRow(debug, 2);
								one.setRow(rs.getString(1), rs.getString(2));
								count += rs.getInt(2);
								rows.add(one);
						}
						one = new ReportRow(debug, 2);			
						one.setRow("Total",""+count);
						rows.add(one);
						all.add(rows);
			
				}catch(Exception e){
						msg += e+":"+qq;
						logger.error(msg);
				}
				finally{
						Helper.databaseDisconnect(con, rs, pstmt, pstmt2);
				}		
				return msg;
		}
		
}






















































