<%@  include file="header.jsp" %>
<!-- 
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->

<s:form action="invoice" method="post">    
  <s:if test="invoice.id == ''">
		<h3>New Invoice</h3>
  </s:if>
  <s:else>
		<s:hidden name="invoice.id" value="%{invoice.id}" />
		<h3>Edit Invoice <s:property value="invoice.id" /></h3>
  </s:else>
  <s:if test="invoice.company_contact_id != ''">
		<s:hidden name="invoice.company_contact_id" value="%{invoice.company_contact_id}" />
  </s:if>
  <s:if test="invoice.permit_id != ''">
		<s:hidden name="invoice.permit_id" value="%{invoice.permit_id}" />
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

  <table border="1" width="100%" cellpadding="0" cellspacing="0">
	<caption>Invoice Info</caption>
	<tr>
	  <td>
			<table width="100%">
				<tr>
					<th>Invoice No.</th> 
					<td><s:textfield name="invoice.invoice_num" size="10" value="%{invoice.invoice_num}" maxlength="10" required="true" /> *</td>
				</tr>
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
					<th>Status</th> 
					<td><s:select name="invoice.status" value="%{invoice.status}" list="{'Billed','Pending','Paid'}" /></td>
				</tr>
				<tr>
					<th>Invoice Date </th> 
					<td><s:textfield name="invoice.date" size="10" value="%{invoice.date}" maxlength="10" required="true" cssClass="date" /> *</td>
				</tr>
				<tr>
					<th>Start Date </th> 
					<td><s:textfield name="invoice.start_date" size="10" value="%{invoice.start_date}" maxlength="10" required="true" cssClass="date" /> *</td>
				</tr>
				<tr>
					<th>End Date </th> 
					<td><s:textfield name="invoice.end_date" size="10" value="%{invoice.end_date}" maxlength="10" required="true" cssClass="date" /> *</td>
				</tr>
				<tr>
					<th>Invoice Total </th> 
					<td><s:property value="%{invoice.total}" /></td>
				</tr>		  
				<tr>
					<th valign="top">Notes </th> 
					<td align="left"><s:textarea name="invoice.notes" rows="10" cols="80" value="%{invoice.notes}" /></td>
				</tr> 
			</table> 
	  </td>
	</tr>
	<s:if test="invoice.canAddPermits()">
		<tr>
			<td>
				<table width="100%" border="1">
					<s:if test="invoice.id == ''">
						<caption>Select the permits to include in this invoice</caption>
					</s:if>
					<s:else>
						<caption>Select new permits to be added to this invoice</caption>
					</s:else>
					<tr>
						<th>Check to Select</th>
						<th>Permit Number</th>
						<td>Status</td>
						<td>Type</td>
						<td>Project</td>
						<td>Reviewer</td>
						<td>Permit Date</td>
						<td>Start Date</td>
						<td>Fee</td>
					</tr>
					<s:if test="invoice.id == ''">
						<s:iterator var="one" value="invoice.permits">
							<tr>
								<td>
									<s:checkbox name="invoice.add_permits" fieldValue="%{id}" checked="checked" />
								</td>
								<td>
									<s:property value="permit_num" />
								</td>
								<td>
									<s:property value="status" />
								</td>	
								<td>
									<s:property value="permit_type" />
								</td>
								<td>
									<s:property value="project" />
								</td>
								<td>
									<s:property value="reviewer" />
								</td>	
								<td>
									<s:property value="date" />
								</td>
								<td>
									<s:property value="start_date" />
								</td>		
								<td align="right">$<s:property value="fee" /></td>
							</tr>
						</s:iterator>
					</s:if>
					<s:else>
						<s:iterator var="one" value="invoice.newPermits">
							<tr>
								<td>
									<s:checkbox name="invoice.add_permits" fieldValue="%{id}" />
								</td>
								<td>
									<s:property value="permit_num" />
								</td>
								<td>
									<s:property value="status" />
								</td>	
								<td>
									<s:property value="permit_type" />
								</td>
								<td>
									<s:property value="project" />
								</td>
								<td>
									<s:property value="reviewer" />
								</td>	
								<td>
									<s:property value="date" />
								</td>
								<td>
									<s:property value="start_date" />
								</td>		
								<td align="right">$<s:property value="fee" /></td>
							</tr>
						</s:iterator>							
					</s:else>
				</table>
			</td>
		</tr>
	</s:if>	
	<tr>
	  <s:if test="invoice.id == ''">
			<td align="right"><s:submit name="action" value="Save" /></td> 
	  </s:if>
	  <s:else>
			<td>
				<table width="100%">
					<tr>
						<td align="left"><s:submit name="action" value="Update" /></td>
						<td align="center">
							<button onclick="document.location='<s:property value='#application.url' />InvoicePdf?action=Print&id=<s:property value='invoice.id' />';return false;">Printable </button>	
						</td>
						<td align="right">			
							<s:if test="invoice.hasReceipt()">
								<button onclick="document.location='<s:property value='#application.url' />receipt.action?id=<s:property value='invoice.receipt.id' />';return false;">Related Receipt</button>	
							</s:if>
							<s:elseif test="invoice.status != 'Paid'">
								<button onclick="document.location='<s:property value='#application.url' />receipt.action?invoice_id=<s:property value='invoice.id' />';return false;">New Receipt</button>
							</s:elseif>
						</td>
					</tr>
				</table></td>
	  </s:else>
	</tr>
	<s:if test="invoice.id != ''">
	  <s:if test="invoice.hasPermits()">
			<tr>
				<td colspan="2">
					<s:set var="permitsTitle" value="permitsTitle" />	
					<s:set var="permits" value="%{invoice.permits}" />
					<%@  include file="permitsShort.jsp" %>	  
				</td>
			</tr>
	  </s:if>
	</s:if>
  </table>
</s:form>
<s:if test="invoice.id == ''">
	<s:if test="invoices != null ">
	  <s:set var="invoicesTitle" value="invoicesTitle" />	
	  <s:set var="invoices" value="invoices" />
	  <%@  include file="invoices.jsp" %>	  
	</s:if>
</s:if>
<%@  include file="footer.jsp" %>























































