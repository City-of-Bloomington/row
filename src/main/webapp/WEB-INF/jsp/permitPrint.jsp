<?xml version="1.0" encoding="UTF-8" ?>
<!-- 
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
	<head>
		<s:head />
		<title>Excavation Permits</title>
	</head>
	<body>
		<table width="100%">
			<tr>
				<td align="center" width="30%">
					<img src="<s:property value='#application_url' />js/images/city_logo3.jpg" width="30%" />
				</td>
				<td align="center">
					<h3>City of Bloomington</h3>
					<h3>Planning and Transportation Department</h3>
					<h3>bloomington.in.gov</h3>
				</td>
				<td align="left">
					401 N Morton St Suite 130<br />
					PO Box 100<br />
					Bloomington, IN 47404<br />
					<br />
					Phone: (812) 349-3423 <br />
					Fac: (812) 349-3520 <br />
					Email: planning@bloomington.in.gov
				</td>
			</tr>
		</table>
		<table width="100%" border="1">
			<caption>Right Of Way Excavation Permit</caption>
			<tr>
				<td>
					<table width="100%">
						<tr>
							<td>Company</td>
							<td><s:property value="%{permit.company}" /> </td>
							<td>Status</td>
							<td><s:property value="%{permit.status}" /> </td>
							<td>Permit</td>
							<td><s:property value="%{permit.permit_num}" /> </td>
						</tr>
						<tr>
							<td>Responsible</td>
							<td><s:property value="%{permit.contact}" /> </td>
							<td>Inspector</td>
							<td><s:property value="%{permit.reviewer}" /> </td>
							<td>Date Issued</td>
							<td><s:property value="%{permit.date}" /> </td>
						</tr>	
						<tr>
							<td>Project</td>
							<td><s:property value="%{permit.project}" /></td>
							<td>Permit Fee</td>
							<td>$<s:property value="%{permit.fee}" /> </td>
							<td>Start Date</td>
							<td><s:property value="%{permit.start_date}" /> </td>
						</tr>
						<tr>
							<td>Bond Amount</td>
							<td><s:property value="%{permit.bond.amount}" /> </td>
							<td>Expiration Date</td>
							<td><s:property value="%{permit.bond.expire_date}" /> </td>
							<td>Invoice</td>
							<td><s:property value="%{permit.invoice.status}" /></td>
						</tr>  
					</table>
				</td>
			</tr>
		</table>

		<br />
		Excavations
<table width="100%" border="1">
	<tr>
		<td>Address</td>
		<td>Cut Type</td>
		<td>Utility</td>
		<td>Description</td>
		<td>Width</td>
		<td>Length</td>
	</tr>
	<s:iterator var="one" value="%{permit.excavations}" status="cutStatus">
		<tr>
			<td><s:property value="address" /> </td>
			<td><s:property value="cut_type" /> </td>
			<td><s:property value="utility_type" /> </td>
			<td><s:property value="cut_description" /> </td>
			<td><s:property value="width" /> </td>
			<td><s:property value="length" /> </td>
		</tr>
	</s:iterator>
</table>
<table>
  <tr>
		<td align="Left">Special Provisions</td>
  </tr>
  <tr>
		<td><s:property value="%{permit.notes}" /> </td>
  </tr>
  <tr>
		<td> </td>
  </tr>
  <tr>
		<td> </td>
  </tr>
</table>
<h4>Standards Conditions of Approval:</h4>
<ul>
  <li>
		<b>Permittee is required to call the Planning and Transportation Department at 812-349-3423 for inspection of any work at the City of Bloomington Right of way prior to placing any concrete, or at any point specified in the Specific Conditions of Approval.</b>
  </li>
  <li>
		This approval only covers concerns within the jurisdictions of the City of Bloomington Planning and Transportaion Department, other entities or agencies may also need to grant approval for work done in the course of this project.
  </li>
  <li>
		Projects shall conform to all current A.D.A.,(Americans with Disabilities Act) requirements. 
  </li>
  <li>
		All utility work shall conform to specifications to be obtained from the owner affected utility, and work on said utility shall be performed only with expressed permission of its owner. It shall be the responsibility of the permittee to obtain any necessary inspections or approvals from the owner of the utilites involved.
  </li>
  <li>
		Permittee shall be responsible supplying and placing all required signs and barricades. All signs and barricades, and their placement, shall conform to current M.U.T.C.D. and I.N.D.O.T. standards. All required traffic control measures shall be in place before work begins. 
  </li>
  <li>
		Erosion control measures complying with Bloomington Municipal Code 20.06.05.03 are required to be in place during the period of any earth distubing activities, and remain in place until the site is stablized.
  </li>
  <li>
	All bonding must remain current until a written release of such bonding is given by a representitive of the City of Bloomington Planning and Transportation Department.
  </li>
  <li>
	Any work in a street shall coform to the City of Bloomington Street Cut Requirements. Copies of these requirements are available from Engineering Department. All damaged Public Improvements must be repaired to prior or improved conditions.  
  </li>
  <li>
	Any brick or inlaid limestone sidewalks, or brick surfaced streets, shall remain undisturbed, unless specific permission is given by a representitive of the Planning and Transportation Department. If they are disturbed the surface material shall be taken up, saved, and re-installed to City of Bloomington specifications. Backfill methods and materials must also meet these specifications. 
  </li>
  <li>
	Any damage to any underground facility or utility must be repored immediately to the City of Bloomington Planning and Transportation Department and the owner of the facility or utility (if known). If not, the permittee will be required to re-escavate the damaged facility or utility, at their expense, to demonstrate that repairs have been made to the satisfaction of the owner of the damaged facility or utility,
  </li>
  <li>
	Any above ground appurtenances (line markers, switch boxes, meters, etc,), or structures, to be placed in the City Right of Way must be approved the City of Bloomington Planning and Transportation Department prior to installation. 
  </li>
  <li>
	All existing regulatory signs (STOP, YIELD, NO PARKING, etc,), or structures to be placed in the City of Bloomington Traffic Division. Any regulatory signs reoved, or installed, by the permittee are subject to removal or replacement the Traffic Division with Permittee being billed for time and materials.
  </li>
</ul>
<table with="100%">
  <tr>
		<td align="right"><s:property value="%{permit.reviewer}" /></td>
  </tr>
</table>
</body>
</html>























































