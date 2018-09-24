<%@  include file="header.jsp" %>
<!-- 
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->

<h3>Search Companies</h3>
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
<s:form action="companySearch" method="post" id="form_id" >
  <s:hidden name="action2" id="action_id" value=""/>  
  <table border="1" width="100%" cellpadding="0" cellspacing="0">
		<tr>
			<td>
				<table width="100%" cellpadding="0" cellspacing="0">
					<tr>
						<td align="right">ID </td>
						<td><s:textfield name="companyList.id" size="10" maxlength="10" value="%{companyList.id}" id="company_id" /></td>
					</tr>
					<tr>
						<td align="right">Name </td>
						<td><s:textfield name="companyList.name" size="30" maxlength="30" value="%{companyList.name}" id="company_name" />(Enter few name letters to select from list)</td>
					</tr>
					<tr>
						<td align="right">Address </td>
						<td><s:textfield name="companyList.address" value="%{companyList.address}" maxlength="70" size="50" /></td>
					</tr>
					<tr>
						<td align="right">City </td>		   
						<td><s:textfield name="companyList.city" value="%{companyList.city}" maxlength="30" size="30" /></td>
					</tr>
					<tr>
						<td align="right">State </td>		 		 
						<td><s:textfield name="companyList.state" value="%{companyList.state}" maxlength="2" size="2" /></td>
					</tr>
					<tr>
						<td align="right">Zip </td>			 
						<td><s:textfield name="companyList.zip" value="%{companyList.zip}" maxlength="5" size="5" /></td>
					</tr>
					<tr>
						<td align="right">Phone </td>			 
						<td><s:textfield name="companyList.phone" value="%{companyList.phone}" maxlength="20" size="20" /></td>
					</tr>
					<tr>
						<td align="right">Sort by</td>
						<td align="left"><s:select name="companyList.sort_by" value="companyList.sort_by" list="#{'c.id':'ID','c.name':'Name','c.address':'Address'}" headerKey="c.id" headerValue="ID" /></td>
					</tr>		 
				</table>
			</td>
		</tr>
		<tr>
			<td align="right"><s:submit name="action" value="Submit"/></td>
		</tr>
		<tr>
			<td align="left">
				To add a new company <a href="<s:property value='#application.url' />company.action">here</a>
			</td>
		</tr>
  </table>
</s:form>
<s:if test="companies != null">
  <s:set var="companiesTitle" value="companiesTitle" />	
  <s:set var="companies" value="companies" />
  <%@  include file="companies.jsp" %>	  
</s:if>
<%@  include file="footer.jsp" %>
























































