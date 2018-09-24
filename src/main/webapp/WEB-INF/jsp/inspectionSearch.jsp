<%@  include file="header.jsp" %>
<!-- 
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->

<h3>Search Inspections</h3>
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
<s:form action="inspectionSearch" method="post" id="form_id" >
  <s:hidden name="action2" id="action_id" value=""/>  
  <table border="1" width="100%" cellpadding="0" cellspacing="0">
		<tr>
			<td>
				<table width="100%" cellpadding="0" cellspacing="0">
					<tr>
						<td align="right">Inspection ID </td>
						<td><s:textfield name="inspectionList.id" size="10" maxlength="10" value="%{inspectionList.id}" /></td>
					</tr>
					<tr>
						<td align="right">Permit Number </td>
						<td><s:textfield name="inspectionList.permit_num" size="12" maxlength="12" value="%{inspectionList.permit_num}" /></td>
					</tr>		  
					<tr>
						<td align="right">Company Name </td>
						<td><s:textfield name="company_name" size="30" maxlength="30" value="%{company_name}" id="company_name2" /> Company ID <s:textfield name="inspectionList.company_id" value="%{inspectionList.company_id}" id="company_id" />
						</td>
					</tr>
					<tr>
						<td align="right">Contact Name </td>
						<td><s:textfield name="contact_name" size="30" maxlength="30" value="%{contact_name}" id="contact_name" /> Contact ID <s:textfield name="inspectionList.contact_id" value="%{inspectionList.contact_id}" id="contact_id" />
						</td>
					</tr>		  
					<tr>
						<td align="right">Status </td>
						<td><s:select name="inspectionList.status" value="%{inspectionList.status}" list="{'Not Started','In Progress','Need Touchup','Need Followup','Completed'}"  headerKey="-1" headerValue="All"  /></td>
					</tr>
					<tr>
						<td align="right">Inspector </td>
						<td><s:select name="inspectionList.inspector_id" value="%{inspectionList.inspector_id}" list="inspectors" listKey="id" listValue="fullName" headerKey="-1" headerValue="All"  /></td>
					</tr>		 
					<tr>
						<td align="right">Date: from </td>		   
						<td><s:textfield name="inspectionList.date_from" value="%{inspectionList.date_from}" maxlength="10" size="10" cssClass="date" /></td>
					</tr>
					<tr>
						<td align="right">Date: to </td>		   
						<td><s:textfield name="inspectionList.date_to" value="%{inspectionList.date_to}" maxlength="10" size="10" cssClass="date" /></td>
					</tr>		 
					<tr>
						<td align="right">Sort by</td>
						<td align="left"><s:radio name="inspectionList.sort_by" value="inspectionList.sort_by" list="#{'i.id DESC':'ID','i.date':'Date','i.status':'Status'}" /></td>
					</tr>		 
				</table>
			</td>
		</tr>
		<tr>
			<td align="right"><s:submit name="action" value="Submit"/></td>
		</tr>
		<tr>
			<td align="left">
				To add a new inspecion click <a href="<s:property value='#application.url' />inspection.action">here</a>
			</td>
		</tr>
  </table>
</s:form>
<s:if test="inspections != null">
  <s:set var="inspectionsTitle" value="inspectionsTitle" />	
  <s:set var="inspections" value="inspections" />
  <%@  include file="inspections.jsp" %>	  
</s:if>
<%@  include file="footer.jsp" %>
























































