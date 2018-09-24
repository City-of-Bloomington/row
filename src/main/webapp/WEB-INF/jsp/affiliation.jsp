<%@  include file="header.jsp" %>
<!-- 
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<s:form action="affiliation" method="post">    
  <s:if test="companyContact.id == ''">
		<h3>Add Contact to Company</h3>
  </s:if>
  <s:else>
		<s:hidden name="companyContact.id" value="%{companyContact.id}" />
		<h3>Edit Affiliation <s:property value="contact.id" /></h3>
  </s:else>
  <s:if test="companyContact.company_id != ''" >
		<s:hidden name="companyContact.company_id" value="%{companyContact.company_id}" id="company_id" />
		<ul>
			<li>To add an existing contact to this compay, start typing the contact name to pick from the list. Then click on 'Save'</li>
			<li>If the contact is not in the list click on 'Add New Contact'</li>
		</ul>
  </s:if>
  <s:if test="companyContact.contact_id != ''" >  
		<s:hidden name="companyContact.contact_id" value="%{companyContact.contact_id}" id="contact_id" />
		<ul>
			<li>To add the contact to an existing compay, start typing the company name to pick from the list. Then click on 'Save'</li>
			<li>If the company is not in the list click on 'Add New Company'</li>
		</ul>	
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
	
  <table border="1" width="100%" cellpadding="0" cellspacing="0">
		<caption>Affiliation Info</caption>	
		<tr>
			<td>
				<table width="100%">
					<s:if test="companyContact.company_id == ''">
						<tr>
							<th>Company Name</th>
							<td>
								<s:textfield name="company_name" size="30" value="%{company_name}" maxlength="70" id="company_name2" /> ID
								<s:textfield name="companyContact.company_id" size="10" value="" maxlength="10" id="company_id" />
							</td>
						</tr>
					</s:if>
					<s:else>
						<tr>
							<th>Company ID </th>
							<td><a href="<s:property value="#applicaiton.url" />company.action?id=<s:property value='companyContact.company_id' />"><s:property value="companyContact.company_id" /></td>
						</tr>			
						<tr>
							<th>Company Name</th>
							<td><s:property value="companyContact.company.name" /></td>
						</tr>
						<tr>
							<th>Company Address</th>
							<td><s:property value="companyContact.company.address" /></td>
						</tr>
					</s:else>
					<s:if test="companyContact.contact_id == ''">
						<tr><td colspan="2">Note: start typing contact name to pick from a list</td></tr>
						<tr>
							<th>Contact Name</th>
							<td>
								<s:textfield name="contact_name" size="30" value="%{contact_name}" maxlength="70" id="contact_name" /> ID
								<s:textfield name="companyContact.contact_id" size="10" value="%{companyContact.contact_id}" maxlength="10" id="contact_id" />
							</td>
						</tr>
					</s:if>
					<s:else>
						<tr>
							<th>Contact ID </th>
							<td><a href="<s:property value="#applicaiton.url" />contact.action?id=<s:property value='companyContact.contact_id' />"><s:property value="companyContact.contact_id" /></td>
						</tr>						
						<tr>
							<th>Contact </th>
							<td><s:property value="companyContact.contact.fullName" /></td>
						</tr>
						<tr>
							<th>Contact Address</th>
							<td><s:property value="companyContact.contact.address" /></td>
						</tr>
					</s:else>		  
				</table> 
			</td>
		</tr>
		<tr>
			<td>
				<table width="100%">
					<tr>
						<s:if test="companyContact.id == ''">
							<td align="center"><s:submit name="action" value="Save" /></td>
							<s:if test="companyContact.company_id == ''" >
								<td align="right">
									<button onclick="document.location='<s:property value='#application.url' />company.action?contact_id=<s:property value='companyContact.contact_id' />';return false;">Add New Company</button>
								</td>
							</s:if>
							<s:if test="companyContact.contact_id == ''" >
								<td align="right">
									<button onclick="document.location='<s:property value='#application.url' />contact.action?company_id=<s:property value='companyContact.company_id' />';return false;">Add New Contact</button>
								</td>
							</s:if>			  
						</s:if>
						<s:else>
							<td align="right"><s:submit name="action" value="Remove Contact From the Company" onclick="validateDelete()" /></td>
						</s:else>
					</tr>
				</table>
			</td>
		</tr>
  </table>
</s:form>

<%@  include file="footer.jsp" %>























































