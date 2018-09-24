<%@  include file="header.jsp" %>
<!-- 
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->

<h3>Search Bonds</h3>
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
<s:form action="bondSearch" method="post" id="form_id" >
  <s:hidden name="action2" id="action_id" value=""/>  
  <table border="1" width="100%" cellpadding="0" cellspacing="0">
		<tr>
			<td>
				<table width="100%" cellpadding="0" cellspacing="0">
					<tr>
						<th>Bond ID </th>
						<td><s:textfield name="bondList.id" size="10" maxlength="10" value="%{bondList.id}" /></td>
					</tr>
					<tr>
						<th>Bond Number </th>
						<td><s:textfield name="bondList.bond_num" size="12" maxlength="12" value="%{bondList.bond_num}" /></td>
					</tr>
					<tr>
						<th>Permit Number </th>
						<td><s:textfield name="bondList.permit_num" size="12" maxlength="12" value="%{bondList.permit_num}" /></td>
					</tr>		  
					<tr>
						<th>Company Name </th>
						<td colspan="2"><s:textfield name="company_name" size="30" maxlength="30" value="%{company_name}" id="company_name2" /> Company ID <s:textfield name="bondList.company_id" value="%{bondList.company_id}" id="company_id" size="10" />
						</td>
					</tr>
					<tr>
						<th>Contact Name </th>
						<td colspan="2"><s:textfield name="contact_name" size="30" maxlength="30" value="%{contact_name}" id="contact_name" /> Contact ID <s:textfield name="bondList.contact_id" value="%{bondList.contact_id}" id="contact_id" size="10" />
						</td>
					</tr>
					<tr>
						<th>Bond Company </th>
						<td colspan="2"><s:select name="bondList.bond_company_id" list="bond_companies" value="%{bondList.bond_company_id}" listKey="id" listValue="name" headerKey="-1" headerValue="All" />
						</td>
					</tr>
					<tr>
						<th>Bond Type </th>
						<td colspan="2"><s:select name="bondList.type" list="#{'Excavation':'Excavation','Development':'Development','Grading':'Grading'}" value="%{bondList.type}" headerKey="-1" headerValue="All" />
						</td>
					</tr>			  
					<tr>
						<th>&nbsp;</th><td>From</td><td>To</td>
					</tr>
					<tr>
						<th>Expire Date</th>		   
						<td><s:textfield name="bondList.date_from" value="%{bondList.date_from}" maxlength="10" size="10" cssClass="date" /> - </td> 
						<td><s:textfield name="bondList.date_to" value="%{bondList.date_to}" maxlength="10" size="10" cssClass="date" /></td>
					</tr>
					<tr>
						<th>Bond Amount ($)</th>		   
						<td><s:textfield name="bondList.amount_from" value="%{bondList.amount_from}" maxlength="10" size="10" /> - </td>
						<td><s:textfield name="bondList.amount_to" value="%{bondList.amount_to}" maxlength="10" size="10" /></td>
					</tr>		 
					<tr>
						<th>Sort by</th>
						<td colspan="2" align="left"><s:radio name="bondList.sort_by" value="bondList.sort_by" list="#{'b.id DESC':'ID','c.name':'Bond Company','b.expire_date':'Expire Date','t.name':'Type'}" /></td>
					</tr>		 
				</table>
			</td>
		</tr>
		<tr>
			<td align="right"><s:submit name="action" value="Submit"/></td>
		</tr>
		<tr>
			<td align="left">
				To add a new bond click <a href="<s:property value='#application.url' />bondStart.action">here</a>
			</td>
		</tr>		
  </table>
</s:form>
<s:if test="bonds != null">
  <s:set var="bondsTitle" value="bondsTitle" />	
  <s:set var="bonds" value="bonds" />
  <%@  include file="bonds.jsp" %>	  
</s:if>
<%@  include file="footer.jsp" %>
























































