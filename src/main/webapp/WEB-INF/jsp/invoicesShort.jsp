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
<s:elseif test="hasActionMessages()">
  <div class="welcome">
    <s:actionmessage/>
  </div>
</s:elseif>
<h3><s:property value="#invoicesTitle" /></h3>
<table border="1" width="100%">
  <tr>
		<td>ID</td>
		<td>Status</td>
		<td>Inoice Date</td>
		<td>Start Date</td>
		<td>End Date</td>
		<td>Receipt</td>
  </tr>
  <s:iterator var="one" value="#invoices" status="conStatus">
		<tr <s:if test="conStatus.even">style="background:lightgray"</s:if>>
			<td>
				<a href="<s:property value='#application.url' />invoice.action?id=<s:property value='id' />"> <s:property value="id" /></a>
			</td>				  
			<td>
				<s:property value="status" />
			</td>	
			<td>
				<s:property value="date" />
			</td>
			<td>
				<s:property value="start_date" />
			</td>		
			<td>
				<s:property value="end_date" />
			</td>		
			<td>&nbsp;
				<s:if test="hasReceipt()">
					<a href="<s:property value='#application.url' />receipt.action?id=<s:property value='receipt.id' />"> <s:property value="receipt.id" /></a>
				</s:if>
			</td>	  
		</tr>
	</s:iterator>
</table>






















































