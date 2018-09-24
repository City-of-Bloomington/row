<%@  include file="header.jsp" %>
<!-- 
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<s:form action="receipt" method="post">    
  <s:if test="receipt.id == ''">
		<h3>New Receipt</h3>
  </s:if>
  <s:else>
		<s:hidden name="receipt.id" value="%{receipt.id}" />
		<s:hidden name="receipt.user_id" value="%{receipt.user_id}" />	
		<h3>Edit Receipt <s:property value="receipt.id" /></h3>
  </s:else>
  <s:hidden name="receipt.invoice_id" value="%{receipt.invoice_id}" />
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
	<caption>Receipt Info</caption>
	<tr>
	  <td>
			<table width="100%">
				<s:if test="receipt.invoice.hasCompany()" >
					<tr>
						<th>Company</th> 
						<td><a href="<s:property value='#application.url' />company.action?id=<s:property value='%{receipt.invoice.company.id}' />"><s:property value="%{receipt.invoice.company.name}"/></a>
						</td>
					</tr>						  
					<s:if test="receipt.invoice.hasContact()" >
						<tr>
							<th>Company Contact</th>
							<td><a href="<s:property value='#application.url' />contact.action?id=<s:property value='%{receipt.invoice.contact.id}' />"><s:property value="%{receipt.invoice.contact.fullName}"/></a>
							</td>
						</tr>						
					</s:if>	
				</s:if>
				<s:elseif test="receipt.invoice.hasContact()" >
					<tr>
						<th>Contact</th> 
						<td><s:property value="%{receipt.invoice.contact.fullName}"/></td>
					</tr>
				</s:elseif>
				<tr>
					<th>Invoice No.</th> 
					<td><a href="<s:property value='#application.url' />invoice.action?id=<s:property value='%{receipt.invoice_id}' />"><s:property value="%{receipt.invoice_num}"/></a></td>
				</tr>		  
				<tr>
					<th>Invoice Total</th> 
					<td><s:property value="%{receipt.invoice.total}" /></td>
				</tr>
				<tr>
					<th>Amount Paid </th> 
					<td><s:textfield name="receipt.amount_paid" size="12" value="%{receipt.amount_paid}" maxlength="12" required="true" /> *</td>
				</tr>		  
				<tr>
					<th>Payment Method</th> 
					<td><s:radio name="receipt.payment_type" value="%{receipt.payment_type}" list="{'Check','Cash','Money Order','Credit Card'}" /></td>
				</tr>
				<tr>
					<th>Check Number </th> 
					<td><s:textfield name="receipt.check_num" size="30" value="%{receipt.check_num}" maxlength="30" /> </td>
				</tr>
				
				<tr>
					<th>Payment Date </th> 
					<td><s:textfield name="receipt.date" size="10" value="%{receipt.date}" maxlength="10" required="true" cssClass="date" /> *</td>
				</tr>
				<tr>
					<th>Paid By </th> 
					<td><s:textfield name="receipt.paid_by" size="30" value="%{receipt.paid_by}" maxlength="30" /> </td>
				</tr>
				<s:if test="receipt.id != ''">
					<tr>
						<th>Received By </th> 
						<td><s:property value="%{receipt.user}" /> </td>
					</tr>
					<tr>
						<th>Void?</th>
						<td><s:checkbox name="receipt.voided" value="%{receipt.voided}" />
							To void this receipt check this option
						</td>
					</tr>
				</s:if>
			</table> 
		</td>
	</tr>
	<tr>
		<s:if test="receipt.id == ''">
			<td align="right"><s:submit name="action" value="Save" /></td> 
	  </s:if>
	  <s:else>
			<td><table width="100%">
				<tr>
					<td align="left"><s:submit name="action" value="Update" /></td>
					<td align="right">
						<button onclick="document.location='<s:property value='#application.url' />ReceiptPdf?action=Print&id=<s:property value='receipt.id' />';return false;">Printable </button>	
					</td>
				</tr>
			</table></td>
	  </s:else>
	</tr>
	<s:if test="receipt.invoice.hasPermits()">
	  <tr>
			<td colspan="2">
				<s:set var="permitsTitle" value="'Related Permit(s)'" />	
				<s:set var="permits" value="%{receipt.invoice.permits}" />
				<%@  include file="permitsShort.jsp" %>	  
			</td>
	  </tr>
	</s:if>
  </table>
</s:form>
<s:if test="receipt.id == ''">
	<s:if test="receipts != null ">
	  <s:set var="receiptsTitle" value="receiptsTitle" />	
	  <s:set var="receipts" value="receipts" />
	  <%@  include file="receipts.jsp" %>	  
	</s:if>
</s:if>
<%@  include file="footer.jsp" %>























































