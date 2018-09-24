<%@  include file="header.jsp" %>
<!-- 
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->

<h3>Search Insurances</h3>
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
<s:form action="insuranceSearch" method="post" id="form_id" >
  <s:hidden name="action2" id="action_id" value=""/>  
  <table border="1" width="100%" cellpadding="0" cellspacing="0">
		<tr>
			<td>
				<table width="100%" cellpadding="0" cellspacing="0">
					<tr>
						<th>Insurance ID </th>
						<td><s:textfield name="insuranceList.id" size="10" maxlength="10" value="%{insuranceList.id}" /></td>
					</tr>
					<tr>
						<th>Policy Number </th>
						<td><s:textfield name="insuranceList.policy_num" size="12" maxlength="12" value="%{insuranceList.policy_num}" /></td>
					</tr>
					<tr>
						<th>Permit Number </th>
						<td><s:textfield name="insuranceList.permit_num" size="12" maxlength="12" value="%{insuranceList.permit_num}" /></td>
					</tr>
					<tr>
						<th>Permit ID </th>
						<td><s:textfield name="insuranceList.permit_id" size="12" maxlength="12" value="%{insuranceList.permit_id}" /></td>
					</tr>					
					<tr>
						<th>Company Name </th>
						<td colspan="2"><s:textfield name="company_name" size="30" maxlength="30" value="%{company_name}" id="company_name2" /> Company ID <s:textfield name="insuranceList.company_id" value="%{insuranceList.company_id}" id="company_id" size="10" />
						</td>
					</tr>
					<tr>
						<th>Contact Name </th>
						<td colspan="2"><s:textfield name="contact_name" size="30" maxlength="30" value="%{contact_name}" id="contact_name" /> Contact ID <s:textfield name="insuranceList.contact_id" value="%{insuranceList.contact_id}" id="contact_id" size="10" />
						</td>
					</tr>
					<tr>
						<th>Insurance Company </th>
						<td colspan="2"><s:select name="insuranceList.insurance_company_id" list="insuranceCompanies" value="%{insuranceList.insurance_company_id}" listKey="id" listValue="name" headerKey="-1" headerValue="All" />
						</td>
					</tr>
					<tr>
						<th>Insurance Type </th>
						<td colspan="2"><s:select name="insuranceList.type" list="#{'Personal injury':'Personal injury','Property damage':'Property damage'}" value="%{insuranceList.type}" headerKey="-1" headerValue="All" />
						</td>
					</tr>					
					<tr>
						<th>&nbsp;</th><td>From</td><td>To</td>
					</tr>
					<tr>
						<th>Expire Date</th>		   
						<td><s:textfield name="insuranceList.date_from" value="%{insuranceList.date_from}" maxlength="10" size="10" cssClass="date" /> - </td> 
						<td><s:textfield name="insuranceList.date_to" value="%{insuranceList.date_to}" maxlength="10" size="10" cssClass="date" /></td>
					</tr>
					<tr>
						<th>Insurance Amount ($)</th>		   
						<td><s:textfield name="insuranceList.amount_from" value="%{insuranceList.amount_from}" maxlength="10" size="10" /> - </td>
						<td><s:textfield name="insuranceList.amount_to" value="%{insuranceList.amount_to}" maxlength="10" size="10" /></td>
					</tr>		 
					<tr>
						<th>Sort by</th>
						<td colspan="2" align="left"><s:radio name="insuranceList.sort_by" value="insuranceList.sort_by" list="#{'b.id DESC':'ID','c.name':'Insurance Company','b.expire_date':'Expire Date'}" /></td>
					</tr>		 
				</table>
			</td>
		</tr>
		<tr>
			<td align="right"><s:submit name="action" value="Submit"/></td>
		</tr>
		<tr>
			<td align="left">
				To add a new Insurance click <a href="<s:property value='#application.url' />insuranceStart.action">here</a>
			</td>
		</tr>		
  </table>
</s:form>
<s:if test="insurances != null">
  <s:set var="insurancesTitle" value="insurancesTitle" />	
  <s:set var="insurances" value="insurances" />
  <%@  include file="insurances.jsp" %>	  
</s:if>
<%@  include file="footer.jsp" %>
























































