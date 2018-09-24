<%@  include file="header.jsp" %>
<!-- 
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->

<s:form action="insurance" method="post">    
	<s:if test="insurance.id == ''">
		<h3>New Insurance</h3>
	</s:if>
  <s:else>
		<s:hidden name="insurance.id" value="%{insurance.id}" />
		<h3>Edit Insurance <s:property value="insurance.id" /></h3>
  </s:else>
  <s:if test="insurance.company_contact_id != ''">
		<s:hidden name="insurance.company_contact_id" value="%{insurance.company_contact_id}" />
  </s:if>
  <s:if test="insurance.permit_id != ''">
		<s:hidden name="insurance.permit_id" value="%{insurance.permit_id}" />
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
	<p>Note: if the insurance company is not listed in the insurance company pick up list
		you need to add it to the list by click on 'Admin' link in top and pick 'Edit Categories' then pick bond companies.
	</p>	
  <table border="1" width="100%" cellpadding="0" cellspacing="0">
	<caption>Insurance Info</caption>
	<tr>
	  <td>
		<table width="100%">
		  <s:if test="insurance.hasCompany()" >
				<tr>
					<th>Company</th> 
					<td><a href="<s:property value='#application.url' />company.action?id=<s:property value='%{insurance.company.id}' />"><s:property value="%{insurance.company.name}"/></a>
					</td>
				</tr>						  
				<s:if test="insurance.hasContact()" >
					<tr>
						<th>Contact</th>
						<td><a href="<s:property value='#application.url' />contact.action?id=<s:property value='%{insurance.contact.id}' />"><s:property value="%{insurance.contact.fullName}"/></a>
						</td>
					</tr>						
				</s:if>	
		  </s:if>
		  <s:elseif test="insurance.hasContact()" >
				<tr>
					<th>Contact</th> 
					<td><s:property value="%{insurance.contact.fullName}"/></td>
				</tr>
		  </s:elseif>
		  <tr>
				<th>Insurance Company</th> 
				<td><s:select name="insurance.insurance_company_id" value="%{insurance.insurance_company_id}" list="insurance_companies" listKey="id" listValue="name" headerKey="-1" headerValue="" /></td>
		  </tr>
		  <tr>
				<th>Policy Number </th> 
				<td><s:textfield name="insurance.policy_num" size="12" value="%{insurance.policy_num}" maxlength="20" required="true" /> *</td>
		  </tr>		  
		  <tr>
				<th>Expire Date </th> 
				<td><s:textfield name="insurance.expire_date" size="10" value="%{insurance.expire_date}" maxlength="10" required="true" cssClass="date" /> *</td>
		  </tr>
		  <tr>
				<th>Amount </th> 
				<td>$<s:textfield name="insurance.amount" size="12" value="%{insurance.amount}" maxlength="12" required="true" /> *</td>
		  </tr>
		  <tr>
				<th>Type </th> 
				<td><s:select name="insurance.type" value="%{insurance.type}" list="#{'Personal injury':'Personal injury','Property damage':'Property damage'}" /> *</td>
		  </tr>			
		  <tr>
				<th valign="top">Notes </th> 
				<td align="left"><s:textarea name="insurance.notes" rows="10" cols="80" value="%{insurance.notes}" /></td>
		  </tr> 
		</table> 
	  </td>
	</tr>
	<tr>
	  <s:if test="insurance.id == ''">
			<td align="right"><s:submit name="action" value="Save" /></td> 
	  </s:if>
	  <s:else>
			<td><table width="100%">
				<tr>
					<td align="left"><s:submit name="action" value="Update" /></td>
				</tr>
			</table></td>
	  </s:else>
	</tr>
  </table>
  </s:form>
  <s:if test="insurance.id == ''">
		<s:if test="insurances != null ">
			<s:set var="insurancesTitle" value="insurancesTitle" />	
			<s:set var="insurances" value="insurances" />
			<%@  include file="insurances.jsp" %>	  
		</s:if>
  </s:if>
  <s:else>
		<s:if test="insurance.hasPermits()">
			<s:set var="permitsTitle" value="'Related Permits'" />
			<s:set var="permits" value="insurance.permits" />
			<%@  include file="permits.jsp" %>	  	  
		</s:if>
  </s:else>
<%@  include file="footer.jsp" %>























































