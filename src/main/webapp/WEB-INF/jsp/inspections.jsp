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
<h3><s:property value="#inspectionsTitle" /></h3>
<table border="1">
  <tr>
		<td>ID</td>
		<td>Date</td>
		<td>Permit Number</td>
		<td>Address</td>
		<td>Inspector</td>
		<td>Status</td>
		<td>Followup Date</td>
		<td>Notes</td>
  </tr>
  <s:iterator var="one" value="#inspections" status="conStatus">
		<tr <s:if test="conStatus.even">style="background:lightgray"</s:if>>
			<td>
				<a href="<s:property value='#application.url' />inspection.action?id=<s:property value='id' />"> <s:property value="id" /></a>
			</td>				  
			<td>
				<s:property value="date" />
			</td>
			<td>
				<s:property value="permit_num" />
			</td>
			<td>
				<s:if test="hasAddress()"><s:property value="address" /></s:if><s:else>&nbsp;</s:else>
			</td>
			<td>
				<s:property value="inspector" />
			</td>	
			<td>
				<s:property value="status" />
			</td>
			<td>
				<s:property value="followup_date" />
			</td>
			<td>
				<s:property value="notes" />
			</td>
		</tr>
	</s:iterator>
</table>






















































