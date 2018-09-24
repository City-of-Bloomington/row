<%@  include file="header.jsp" %>
<!-- 
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<s:form action="%{actionName}" method="post">    
  <h3><s:property value="pageTitle" /></h3>
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
  <s:if test="start.hasCompany()" >
		<s:hidden name="start.company_id" value="%{start.company_id}" />
  </s:if>
  <s:if test="start.hasContact()" >
		<s:hidden name="start.contact_id" value="%{start.contact_id}" />
  </s:if>  
  <s:property value="subjectLine" />
  <ul>
		<li>If this is for a company, enter the company name to select it from a list</li>
		<ul>
			<li>Pick the contact from the list</li>
		</ul>
		<li>If this is for a contact person only (such as property owner, no company involved), enter the contact name to select from the list</li>
  </ul>
	
  <table border="1" width="100%" cellpadding="0" cellspacing="0">
		<tr>
			<td>
				<table width="100%">
					<s:if test="start.hasCompany()" >
						<tr>
							<th>Company</th> 
							<td><a href="<s:property value='#application.url' />company.action?id=<s:property value='%{start.company.id}' />"><s:property value="%{start.company.name}"/></a> <s:if test="start.company.isFlagged()"><img src="<s:property value='#{application.url}' />js/images/red_flag.jpg" alt="red flagged company" width="25px"/> (<font color="red"><s:property value="start.company.flaggedInfo" /></font>) </s:if>
							</td>
						</tr>
						<s:if test="start.companyContacts != null">
							<tr><td colspan="2">Pick the contact to use in this invoice, then click on "Next"</td></tr>
							<tr><th width="15%">&nbsp;</th><td>Contact</td></tr>
							<s:iterator var="one" value="%{start.companyContacts}" status="itStatus" >
								<tr>
									<th>
										<input type="radio" name="start.sel_company_contact" value="<s:property value='id' />" <s:if test="#itStatus.count == 1"> checked="checked"</s:if> />
									</th>
									<td>
										<s:property value="contact.fullName" />
									</td>
								</tr>
							</s:iterator>
						</s:if>
					</s:if>
					<s:elseif test="start.hasContact()" >
						<tr>
							<th>Contact</th> 
							<td>
								<a href="<s:property value='#application.url' />contact.action?id=<s:property value='%{start.contact.id}' />"><s:property value="%{start.contact.fullName}"/></a>			  
							</td>
						</tr>
					</s:elseif>
					<s:else>
						<tr>
							<th>Company</th>
							<td align="left">
								<s:textfield name="company_name" value="" size="30" maxlength="70" id="company_name" /> ID 
								<s:textfield name="start.company_id" value="" size="10" maxlength="10" id="company_id" />			
							</td>
						</tr>
						<tr>
							<th>or</th> 
							<td>&nbsp;</td>
						</tr>
						<tr>
							<th>Contact/Owner</th> 
							<td align="left">
								<s:textfield name="contact_name" size="30" value="" maxlength="50" id="contact_name" /> ID
								<s:textfield name="start.contact_id" value="" size="10" maxlength="10" id="contact_id" />				  
							</td>
						</tr>
					</s:else>
				</table> 
			</td>
		</tr>
		<tr>
			<s:if test="start.company_contact_id == ''">
				<td align="right"><s:submit name="action" value="Next" /></td> 
			</s:if>
		</tr>
  </table>
</s:form>
<%@  include file="footer.jsp" %>























































