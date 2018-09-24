<%@  include file="header.jsp" %>
<!-- 
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->

<h3>Search Permits</h3>
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
<s:form action="permitSearch" method="post" id="form_id" >
  <s:hidden name="action2" id="action_id" value=""/>  
  <table border="1" width="100%" cellpadding="0" cellspacing="0">
		<tr>
			<td>
				<table width="100%" cellpadding="0" cellspacing="0">
					<tr>
						<td align="right">Permit ID </td>
						<td><s:textfield name="permitList.id" size="10" maxlength="10" value="%{permitList.id}" /></td>
					</tr>
					<tr>
						<td align="right">Permit Number </td>
						<td><s:textfield name="permitList.permit_num" size="12" maxlength="12" value="%{permitList.permit_num}" /></td>
					</tr>		  
					<tr>
						<td align="right">Company Name </td>
						<td><s:textfield name="company_name" size="30" maxlength="30" value="%{company_name}" id="company_name2" /> Company ID <s:textfield name="permitList.company_id" value="%{permitList.company_id}" id="company_id" />
						</td>
					</tr>
					<tr>
						<td align="right">Contact Name </td>
						<td><s:textfield name="contact_name" size="30" maxlength="30" value="%{contact_name}" id="contact_name" /> Contact ID <s:textfield name="permitList.contact_id" value="%{permitList.contact_id}" id="contact_id" />
						</td>
					</tr>		  
					<tr>
						<td align="right">Status </td>
						<td><s:select name="permitList.status" value="%{permitList.status}" list="{'Not Started','Active','On Hold','Work Complete','Closed','Violation'}"  headerKey="-1" headerValue="All"  /></td>
					</tr>
					<tr>
						<td align="right">Reviewer </td>
						<td><s:select name="permitList.reviewer_id" value="%{permitList.reviewer_id}" list="inspectors" listKey="id" listValue="fullName" headerKey="-1" headerValue="All"  /></td>
					</tr>		 
					<tr>
						<td align="right">Date: from </td>		   
						<td><s:textfield name="permitList.date_from" value="%{permitList.date_from}" maxlength="10" size="10" cssClass="date" /></td>
					</tr>
					<tr>
						<td align="right">Date: to </td>		   
						<td><s:textfield name="permitList.date_to" value="%{permitList.date_to}" maxlength="10" size="10" cssClass="date" /></td>
					</tr>		 
					<tr>
						<td align="right">Date Options:</td>		 		 
						<td><s:radio name="permitList.which_date" value="%{permitList.which_date}" list="#{'i.date':'Permit Date','i.start_date':'Start Date'}" /></td>
					</tr>
					<tr>
						<td align="right">Sort by</td>
						<td align="left"><s:radio name="permitList.sort_by" value="permitList.sort_by" list="#{'i.id DESC':'ID','i.date':'Date','i.status':'Status'}" /></td>
					</tr>		 
				</table>
			</td>
		</tr>
		<tr>
			<td align="right"><s:submit name="action" value="Submit"/></td>
		</tr>
		<s:if test="#session.user != null">
			<tr>
				<td align="left">
					To add a new permit click <a href="<s:property value='#application.url' />permitStart.action">here</a>
				</td>
			</tr>
		</s:if>
  </table>
</s:form>
<s:if test="permits != null">
  <s:set var="permitsTitle" value="permitsTitle" />	
  <s:set var="permits" value="permits" />
  <%@  include file="permits.jsp" %>	  
</s:if>
<%@  include file="footer.jsp" %>
























































