<%@  include file="header.jsp" %>
<!-- 
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->

<s:form action="invoice" method="post">    
  <h3>New Invoice</h3>
  <s:if test="invoice.company_contact_id != ''">
		<s:hidden name="invoice.company_contact_id" value="%{invoice.company_contact_id}" />
  </s:if>
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
  <p>*indicate a required field</p>
  <p>Pick the start date range for the permits that will be included in this invoice</p>
  <table border="1" width="100%" cellpadding="0" cellspacing="0">
		<caption>Invoice Info</caption>
		<tr>
			<td>
				<table width="100%">
					<s:if test="invoice.hasCompany()" >
						<tr>
							<th>Company</th> 
							<td><a href="<s:property value='#application.url' />company.action?id=<s:property value='%{invoice.company.id}' />"><s:property value="%{invoice.company.name}"/></a>
							</td>
						</tr>						  
						<s:if test="invoice.hasContact()" >
							<tr>
								<th>Company Contact</th>
								<td><a href="<s:property value='#application.url' />contact.action?id=<s:property value='%{invoice.contact.id}' />"><s:property value="%{invoice.contact.fullName}"/></a>
								</td>
							</tr>						
						</s:if>	
					</s:if>
					<s:elseif test="invoice.hasContact()" >
						<tr>
							<th>Contact</th> 
							<td><s:property value="%{invoice.contact.fullName}"/></td>
						</tr>
					</s:elseif>
					<tr>
						<th width="40%">Permit Start Date Range, from</th> 
						<td><s:textfield name="invoice.start_date" size="10" value="%{invoice.start_date}" maxlength="10" required="true" cssClass="date" /> *</td>
					</tr>
					<tr>
						<th>Permit Start Date Range, to </th> 
						<td><s:textfield name="invoice.end_date" size="10" value="%{invoice.end_date}" maxlength="10" required="true" cssClass="date" /> *</td>
					</tr>
				</table> 
			</td>
		</tr>
		<tr>
			<td align="right"><s:submit name="action" value="Next" /></td> 
		</tr>
  </table>
</s:form>
<%@  include file="footer.jsp" %>























































