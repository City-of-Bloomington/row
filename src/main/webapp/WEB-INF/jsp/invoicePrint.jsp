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
  <style>
  td.bolder {
		font-weight: bold;
		font-size: 75%;
  }
  td.softer {
		font-weight: normal;
		font-size: 75%;
  }  
  </style>
  <title>Invoice</title>
</head>
<body>
	<table width="100%">
		<tr>
			<td align="center" width="30%">
				<img src="<s:property value='#application_url' />js/images/city_logo3.jpg" width="30%" />
			</td>
			<td align="center">
				<h3>City of Bloomington</h3>
				<h4>Planning and Transportation Department</h4>
				<h4>bloomington.in.gov</h4>
				<h3>INVOICE</h3>
			</td>
			<td align="left" class="softer">
				401 N Morton St Suite 130<br />
				PO Box 100<br />
				Bloomington, IN 47402<br />
				<br />
				Phone: (812) 349-3423 <br />
				Fac: (812) 349-3520 <br />
				Email: planning@bloomington.in.gov
			</td>
		</tr>
	</table>
	<table width="100%">
		<tr>
			<td align="left" width="35%" class="softer">
				<b>Company</b><br />
				<s:if test="invoice.hasCompany()"><s:property value="%{invoice.company}" /></s:if><br />
				<s:if test="invoice.hasContact()">
					<s:property value="%{invoice.contact.fullName}" /><br />
					<s:property value="%{invoice.contact.address}" /><br />
					<s:property value="%{invoice.contact.cityStateZip}" /><br />
				</s:if>
			</td>
			<td align="center" width="30%">&nbsp;
			</td>
			<td>
				<table width="100%">
					<tr>
						<td align="left" class="bolder"><b>Invoice Number </b></td>
						<td align="right" class="softer"><s:property value="%{invoice.invoice_num}" /></td>
					</tr>
					<tr>
						<td align="left" class="bolder"><b>Status </b></td>
						<td align="right" class="softer"><b><s:property value="%{invoice.status}" /></b></td>
					</tr>
					<tr>
						<td align="left" class="bolder"><b>Invoice Date </b></td>
						<td align="right" class="softer"><s:property value="%{invoice.date}" /></td>
					</tr>
					<tr>
						<td align="left" class="softer"><b>From </b><s:property value="%{invoice.start_date}" />  </td>
						<td align="left" class="softer"><b>To </b><s:property value="%{invoice.end_date}" /></td>
					</tr>		
				</table>
			</td>
		</tr>
	</table>
	<s:iterator var="one" value="%{invoice.pages}" status="pageStatus">
		<table width="100%" border="1">
			<tr>
				<td align="center">
					<table width="75%">
						<tr>
							<td align="left" class="softer"><b>Invoice Number </b> <s:property value="%{invoice.invoice_num}" /> </td>
							<td align="right" class="softer"><s:property value="page_num" /></td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td>	
					<table width="100%">
						<tr>
							<td width="25%" class="bolder">Excavation Permit Number</td>
							<td width="40%" class="bolder">Project</td>		  
							<td width="25%" class="bolder">Date Issued</td>
							<td class="bolder">Permit Fee</td>
						</tr>		  
						<s:iterator value="permits" status="permitStatus">
							<tr>
								<td valign="top" class="softer"><s:property value="permit_num" /></td>
								<td class="softer"><s:property value="project" /><br />
									<s:iterator var="two" value="excavations">
										<s:property value="address" /> (<s:property value="cut_type" />) <br />
									</s:iterator>
								</td>
								<td valign="top" class="softer">
									<s:property value="date" /> 
								</td>
								<td valign="top" align="right" class="softer">
									$<s:property value="fee" /> 
								</td>			
							</tr>
						</s:iterator>
						<s:iterator var="counter" begin="1" end="neededLines">
							<tr>
								<td>&nbsp;</td>
							</tr>
						</s:iterator>
						<s:if test="#pageStatus.last">
							<tr>
								<td align="right" colspan="4" class="bolder">Total Invoice Amount <br />
									<s:property value="%{invoice.total}" />
							</tr>
						</s:if>
					</table>
								</td>
			</tr>
			<tr>
				<td>
					<table width="100%">
						<tr>
							<td colspan="2" align="center" class="softer">Payment due upon receipt. Please make check payable to 'City of Bloomington'. Thank You.
							</td>
						</tr>
						<tr>
							<td align="left" class="softer">
								<s:property value="%{invoice.today}" />
							</td>
							<td align="right" class="softer"><s:property value="page_num" />
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<s:if test="#pageStatus.last != true">
			<p style="page-break-after:always"></p>
		</s:if>
	</s:iterator>
</body>
</html>























































