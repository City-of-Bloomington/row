<%@  include file="header.jsp" %>
<!-- 
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<h3>Search Invoices</h3>
<s:if test="hasActionErrors()">
  <div class="errors">
    <s:actionerror/>
  </div>
</s:if>
<s:elseif test="hasActionMessages()">
  <div class="welcome">
    <s:actionmessage/>
  </div>
</s:elseif>
<s:form action="invoiceSearch" method="post" id="form_id" >
  <s:hidden name="action2" id="action_id" value=""/>  
  <table border="1" width="100%" cellpadding="0" cellspacing="0">
		<tr>
			<td>
				<table width="100%" cellpadding="0" cellspacing="0">
					<tr>
						<td align="right">Invoice No. </td>
						<td><s:textfield name="invoiceList.invoice_num" size="10" maxlength="10" value="%{invoiceList.invoice_num}" /></td>
					</tr>
					<tr>
						<td align="right">Invoice ID </td>
						<td><s:textfield name="invoiceList.id" size="10" maxlength="10" value="%{invoiceList.id}" /></td>
					</tr>
					<tr>
						<td align="right">Company Name </td>
						<td><s:textfield name="company_name" size="30" maxlength="30" value="%{company_name}" id="company_name2" /> Company ID <s:textfield name="invoiceList.company_id" value="%{invoiceList.company_id}" id="company_id" />
						</td>
					</tr>
					<tr>
						<td align="right">Contact Name </td>
						<td><s:textfield name="contact_name" size="30" maxlength="30" value="%{contact_name}" id="contact_name" /> Contact ID <s:textfield name="invoiceList.contact_id" value="%{invoiceList.contact_id}" id="contact_id" />
						</td>
					</tr>		  
					<tr>
						<td align="right">Status </td>
						<td><s:select name="invoiceList.status" value="%{invoiceList.status}" list="{'Billed','Pending','Paid'}"  headerKey="-1" headerValue="All"  /></td>
					</tr>
					<tr>
						<td align="right">Date: from </td>		   
						<td><s:textfield name="invoiceList.date_from" value="%{invoiceList.date_from}" maxlength="10" size="10" cssClass="date" /></td>
					</tr>
					<tr>
						<td align="right">Date: to </td>		   
						<td><s:textfield name="invoiceList.date_to" value="%{invoiceList.date_to}" maxlength="10" size="10" cssClass="date" /></td>
					</tr>		 
					<tr>
						<td align="right">Date Options:</td>		 		 
						<td><s:radio name="invoiceList.which_date" value="%{invoiceList.which_date}" list="#{'i.date':'Invoice Date','i.start_date':'Start Date','i.end_date':'End Date'}" /></td>
					</tr>
					<tr>
						<td align="right">Sort by</td>
						<td align="left"><s:radio name="invoiceList.sort_by" value="invoiceList.sort_by" list="#{'i.id DESC':'ID','i.date':'Date','i.status':'Status'}" /></td>
					</tr>		 
				</table>
			</td>
		</tr>
		<tr>
			<td align="right"><s:submit name="action" value="Submit"/></td>
		</tr>
		<tr>
			<td align="left">
				To add a new invoice click <a href="<s:property value='#application.url' />invoiceStart.action">here</a>
			</td>
		</tr>
  </table>
</s:form>
<s:if test="invoices != null">
  <s:set var="invoicesTitle" value="invoicesTitle" />	
  <s:set var="invoices" value="invoices" />
  <%@  include file="invoices.jsp" %>	  
</s:if>
<%@  include file="footer.jsp" %>
























































