<%@  include file="header.jsp" %>
<!-- 
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<s:form action="company" method="post" id="form_id" >    
  <s:if test="company.id == ''">
		<h3>New Company</h3>
		<s:hidden name="id" value="%{company.id}" id="company_id" />
		<s:hidden name="action2" value="" id="action_id" />
  </s:if>
  <s:else>
		<s:hidden name="company.id" value="%{company.id}" />
		<h3>Edit Company <s:property value="company.id" /></h3>
  </s:else>
  <s:if test="contact_id != ''">
		<s:hidden name="contact_id" value="%{contact_id}" />
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
  <p>
		* indicate a required field<br />
    <s:if test="company.id != ''">
			** check to select
		</s:if>
  </p>
  <s:if test="company.id != ''">
		Note: To add a new affiliaion to this company:
		<ul>
			<li>If the contact is not in the sytem yet, you need to add him/her first by clicking on 'Contacts' in the top menu</li>
			<li>If the contact is already in the system, click on "Add New Affiliation" to select the contact and then adding him/her.</li>
		</ul>
  </s:if>  
  <table border="1" width="100%" cellpadding="0" cellspacing="0">
		<caption>Company Info</caption>	
		<tr>
			<td>
				<table width="100%">
					<s:if test="company.hasContacts()">
						<tr><td colspan="2" align="center">
							<table border="1">
								<caption>Company Contacts</caption>
								<tr>
									<td>**</td>
									<td>Contact ID</td>
									<td>Full Name</td>
									<td>Title </td>
									<td>Category</td>
									<td>Phones</td>
									<td>Add </td>
									<td>Add </td>
									<td>Add </td>
								</tr>				
								<s:iterator var="one" value="company.company_contacts">
									<s:if test="contact != null">
										<tr>
											<td>
												<input type="radio" name="company.sel_company_contact" value="<s:property value='id'/>" />
											</td>							  
											<td>
												<a href="<s:property value='#application.url' />contact.action?id=<s:property value='contact.id' />"> <s:property value="contact.id" /></a>
											</td>				  
											<td>
												<s:property value="contact.fullName" />
											</td>
											<td>
												<s:property value="contact.title" />
											</td>
											<td>
												<s:property value="contact.type" />
											</td>	
											<td>
												<s:property value="contact.phones" />
											</td>		
											<td>
												<button onclick="document.location='<s:property value='#application.url' />permit.action?company_contact_id=<s:property value='id' />';return false;">New Permit</button>
											</td>
											<td>
												<button onclick="document.location='<s:property value='#application.url' />bond.action?company_contact_id=<s:property value='id' />';return false;">New Bond</button>
											</td>
											<td>
												<button onclick="document.location='<s:property value='#application.url' />insurance.action?company_contact_id=<s:property value='id' />';return false;">New Insurance</button>
											</td>
										</tr>
									</s:if>
								</s:iterator>
							</table>
						</td></tr>
					</s:if>
					<tr>
						<th>Name</th> 
						<td><s:textfield name="company.name" size="60" value="%{company.name}" maxlength="70" required="true" id="company_name" /> *</td>
					</tr> 		
					<tr>
						<th>Address</th> 
						<td align="left"><s:textfield name="company.address" size="60" value="%{company.address}" maxlength="70" required="true" /> *</td>
					</tr> 
					<tr>
						<th>City</th> 
						<td align="left"><s:textfield name="company.city" size="60" value="%{company.city}" maxlength="70" required="true" /> *</td>
					</tr> 
					<tr>
						<th>State</th>
						<td align="left"><s:textfield value="%{company.state}" name="company.state" size="2" /> *</td>
					</tr>
					<tr>
						<th>Postal Code</th> 
						<td align="left"><s:textfield name="company.zip" size="10" value="%{company.zip}" maxlength="10" required="true" /> *</td>
					</tr>
					<tr>
						<th>Phone</th> 
						<td align="left"><s:textfield name="company.phone" size="20" maxlength="30" value="%{company.phone}" /></td>
					</tr>
					<tr>
						<th>Website</th> 
						<td align="left"><s:textfield name="company.website" size="70" maxlength="70" value="%{company.website}" /></td>
					</tr>		  
					<tr>
						<th valign="top">Notes </th> 
						<td align="left"><s:textarea name="company.notes" rows="10" cols="80" value="%{company.notes}" /></td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<s:if test="company.id == ''">
				<td align="right"><s:submit name="action" value="Save" /></td> 
			</s:if>
			<s:else>
				<td>
					<table width="100%">
						<tr>
							<td align="left"><s:submit name="action" value="Update" /></td>
							<td> <button onclick="document.location='<s:property value='#application.url' />affiliation.action?company_id=<s:property value='company.id' />';return false;">Add New Affiliation</button></td>
							<s:if test="company.hasAffiliations()">
								<td align="right"><s:submit name="action" value="Remove Affiliate(s)" /></td>
							</s:if>
							<td align="right"><s:submit name="action" value="Delete" onclick="validateDelete()" /></td>
						</tr>
					</table>
				</td>
			</s:else>
		</tr>
		<s:if test="company.hasBonds()" >
			<tr>
				<td>
					<s:set var="bondsTitle" value="bondsTitle" />	
					<s:set var="bonds" value="%{company.bonds}" />
					<%@  include file="bondsShort.jsp" %>	  
				</td>
			</tr>
		</s:if>
		<s:if test="company.hasInsurances()" >
			<tr>
				<td>
					<s:set var="insurancesTitle" value="insurancesTitle" />	
					<s:set var="insurances" value="%{company.insurances}" />
					<%@  include file="insurancesShort.jsp" %>	  
				</td>
			</tr>
		</s:if>		
		<s:if test="company.hasPermits()" >
			<tr>
				<td>
					<s:set var="permitsTitle" value="permitsTitle" />	
					<s:set var="permits" value="%{company.permits}" />
					<%@  include file="permitsShort.jsp" %>	  
				</td>
			</tr>
		</s:if>
		<s:if test="company.hasInvoices()" >
			<tr>
				<td>
					<s:set var="invoicesTitle" value="invoicesTitle" />	
					<s:set var="invoices" value="%{company.invoices}" />
					<%@  include file="invoicesShort.jsp" %>	  
				</td>
			</tr>
		</s:if>
  </table>
</s:form>

<%@  include file="footer.jsp" %>























































