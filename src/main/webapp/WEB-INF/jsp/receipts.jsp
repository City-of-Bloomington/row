<!-- 
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->

<s:if test="hasActionErrors()">
  <div class="errors">
    <s:actionerror/>
  </div>
</s:if>
<h3><s:property value="#receiptsTitle" /></h3>
<table border="1">
  <tr>
		<td>ID</td>
		<td>Company</td>
		<td>Contact </td>
		<td>Invoice</td>
		<td>Invoice Amount</td>
		<td>Amount Paid</td>
		<td>Payment Date</td>
		<td>Payment Method</td>
		<td>Void?</td>
  </tr>
  <s:iterator var="one" value="#receipts" status="conStatus">
		<tr <s:if test="conStatus.even">style="background:lightgray"</s:if>>
			<td>
				<a href="<s:property value='#application.url' />receipt.action?id=<s:property value='id' />"> <s:property value="id" /></a>
			</td>				  
			<td>&nbsp;
				<s:if test="invoice.hasCompany()">
					<s:property value="invoice.company.name" />
				</s:if>
			</td>
			<td>&nbsp;
				<s:if test="invoice.hasContact()">
					<s:property value="invoice.contact.fullName" />
				</s:if>
			</td>
			<td>
				<a href="<s:property value='#application.url' />invoice.action?id=<s:property value='invoice_id' />"> <s:property value="invoice_id" /></a>
			</td>
			<td>
				$<s:property value="invoice.total" />
			</td>
			<td>
				$<s:property value="amount_paid" />
			</td>	
			<td>
				<s:property value="date" />
			</td>
			<td>
				<s:property value="payment_type" />
			</td>		
			<td>
				<s:if test="#one.voided">Yes</s:if>
			</td>		
		</tr>
	</s:iterator>
</table>






















































