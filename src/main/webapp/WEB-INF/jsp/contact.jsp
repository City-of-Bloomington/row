<%@  include file="header.jsp" %>
<!-- 
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->

<s:form action="contact" method="post" id="form_id" >    
  <s:if test="contact.id == ''">
		<s:hidden name="id" value="" id="contact_id" />
		<s:hidden name="action2" value="" id="action_id" />	
		<h3>New Contact</h3>
		<ul>
			<li>If you do not know if the contact is in the system, start typing his/her first name or last name and you may pick from the available list.</li>
			<li>
				To add a new contact, fill the fields below and then click on 'Save'
			</li>
			<li>
				After saving you can add affiliation by clicking on 'Add Affiliation'
			</li>
		</ul>
  </s:if>
  <s:else>
		<s:hidden name="contact.id" value="%{contact.id}" />
		<h3>Edit Contact <s:property value="contact.id" /></h3>
  </s:else>
  <s:if test="company_id != ''">
		<s:hidden name="company_id" value="%{company_id}" />
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
  <p>*indicate a required field<br />
  </p>

  <table border="1" width="100%" cellpadding="0" cellspacing="0">
		<caption>Contact Info</caption>	
		<tr>
			<td>
				<table width="100%">
					<s:if test="contact.hasAffiliations() ">
						<tr>
							<td colspan="3">
								<table width="100%" border="1">
									<caption>Contact Affiliation</caption>
									<tr><td>Company ID</td><td>Company</td><td>Address</td><td>City, State Zip</td><td>Action</td></tr>
									<s:iterator value="%{contact.company_contacts}">
										<tr>
											<td>
												<a href="<s:property value='#application.url' />company.action?id=<s:property value='company.id' />"> <s:property value="company.id" /></a>
											</td>				  
											<td>
												<s:property value="company.name" />
											</td>
											<td>
												<s:property value="company.address" />
											</td>
											<td>
												<s:property value="company.cityStateZip" />
											</td>
											<td>
												<button onclick="document.location='<s:property value='#application.url' />permit.action?company_contact_id=<s:property value='id' />';return false;">Add New Permit</button>
											</td>
										</tr>
									</s:iterator>
								</table>
							</td>
						</tr>				
					</s:if>
					<tr><th width="15%">&nbsp;</th><td>First</td><td>Last</td></tr>
					<tr>
						<th>Name</th>
						<td>
							<s:textfield name="contact.fname" size="40" value="%{contact.fname}" maxlength="70" class="contact_name2" />
						</td>
						<td>
							<s:textfield name="contact.lname" size="40" value="%{contact.lname}" maxlength="70" required="true" class="contact_name2" /> *
						</td>
					</tr>
					<tr>
						<th>&nbsp;</th><td>Title</td><td>Contact Category</td>
					</tr>
					<tr>
						<th>Work</th>
						<td><s:textfield name="contact.title" size="40" value="%{contact.title}" maxlength="50" /></td>
						<td><s:select name="contact.type_id" list="types" listKey="id" listValue="name" headerKey="-1" headerValue="" /></td>
					</tr>		  
					<tr>
						<th></th><td align="left">Address</td><td align="left">City</td>
					</tr>
					<tr>
						<th></th>
						<td align="left"><s:textfield name="contact.address" size="40" value="%{contact.address}" maxlength="70" required="true" /> *</td>
						<td align="left"><s:textfield name="contact.city" size="30" value="%{contact.city}" maxlength="70" required="true" /> *</td>
					</tr>
					<tr>
						<th>&nbsp;</th><td>State</td><td>Postal Code</td>
					</tr>
					<tr>
						<td>&nbsp;</td>
						<td align="left"><s:textfield value="%{contact.state}" name="contact.state" size="2" /></td>
						<td align="left"><s:textfield name="contact.zip" size="10" value="%{contact.zip}" maxlength="10" required="true" /> *</td>
					</tr>
					<tr>
						<th>&nbsp;</th><td>Work</td><td>Cell</td>
					</tr>
					<tr>
						<th>Phones</th>
						<td align="left"><s:textfield name="contact.work_phone" size="20" maxlength="30" value="%{contact.work_phone}" /></td>
						<td align="left"><s:textfield name="contact.cell_phone" size="20" maxlength="30" value="%{contact.cell_phone}" /></td>
					</tr>
					<tr>
						<th>&nbsp;</th><td>Fax</td><td>Email</td>
					</tr>
					<tr>
						<th>&nbsp;</th>
						<td align="left"><s:textfield name="contact.fax" size="20" maxlength="30" value="%{contact.fax}" /></td>
						<td align="left"><s:textfield name="contact.email" size="40" maxlength="70" value="%{contact.email}" /></td>
					</tr>		  
					<tr>
						<th valign="top">Notes </th> 
						<td align="left" colspan="2"><s:textarea name="contact.notes" rows="5" cols="80" value="%{contact.notes}" /></td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<s:if test="contact.id == ''">
				<td align="right"><s:submit name="action" value="Save" /></td> 
			</s:if>
			<s:else>
				<td><table width="100%">
					<tr>
						<td align="left"><s:submit name="action" value="Update" /></td>
						<td>			
							<s:if test="%{contact.isPropertyOwner()}" >
								<button onclick="document.location='<s:property value='#application.url' />permit.action?company_contact_id=<s:property value='%{contact.company_contact.id}' />';return false;">Add New Permit</button>
							</s:if>
							<s:else>
								<button onclick="document.location='<s:property value='#application.url' />affiliation.action?contact_id=<s:property value='contact.id' />';return false;">Add Affiliation</button>				
							</s:else>
						</td>
						<td align="right"><s:submit name="action" value="Delete" onclick="validateDelete()" /></td>
					</tr> 						
				</table>
				</td>			  
			</s:else>
		</tr>
		<s:if test="contact.hasPermits()" >
			<tr>
				<td>
					<s:set var="permitsTitle" value="permitsTitle" />	
					<s:set var="permits" value="%{contact.permits}" />
					<%@  include file="permitsShort.jsp" %>	  
				</td>
			</tr>
		</s:if>
		<s:if test="contact.hasInvoices()" >
			<tr>
				<td>
					<s:set var="invoicesTitle" value="invoicesTitle" />	
					<s:set var="invoices" value="%{contact.invoices}" />
					<%@  include file="invoicesShort.jsp" %>	  
				</td>
			</tr>
		</s:if>
		<s:if test="contact.hasBonds()" >
			<tr>
				<td>
					<s:set var="bondsTitle" value="bondsTitle" />	
					<s:set var="bonds" value="%{contact.bonds}" />
					<%@  include file="bondsShort.jsp" %>	  
				</td>
			</tr>
		</s:if>		  
  </table> 
</s:form>
<s:if test="contact.id == ''">
	<s:if test="contacts != null ">
	  <s:set var="contactsTitle" value="contactsTitle" />	
	  <s:set var="contacts" value="contacts" />
	  <%@  include file="contacts.jsp" %>	  
	</s:if>
</s:if>
<%@  include file="footer.jsp" %>























































