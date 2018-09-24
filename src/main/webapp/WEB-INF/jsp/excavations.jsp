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
<h3><s:property value="#excavationsTitle" /></h3>
<table border="1">
  <tr>
		<td>ID</td>
		<td>Company</td>
		<td>Contact</td>
		<td>Description</td>
		<td>Cut Type</td>
		<td>Permit Number</td>
		<td>Start Date</td>
		<td>Address</td>
		<td>Depth</td>
		<td>Width</td>
		<td>Length</td>
		<td>Status</td>
		<td>Utility</td>
  </tr>
  <s:iterator var="one" value="#excavations" status="conStatus">
		<tr <s:if test="conStatus.even">style="background:lightgray"</s:if>>
			<td>
				<s:if test="#session != null && #session.user != null">
					<a href="<s:property value='#application.url' />excavation.action?id=<s:property value='id' />">Edit <s:property value="id" /></a>
				</s:if>
				<s:else>
					<a href="<s:property value='#application.url' />excavationView.action?id=<s:property value='id' />">View <s:property value="id" /></a>
				</s:else>
			</td>
			<td>&nbsp;
				<s:property value="permit.company" />
			</td>
			<td>&nbsp;
				<s:property value="permit.contact" />
			</td>	
			<td>&nbsp;
				<s:property value="cut_description" />
			</td>
			<td>
				<s:property value="cut_type" />
			</td>
			<td>
				<s:property value="permit_num" />
			</td>
			<td>
				<s:property value="permit.start_date" />
			</td>		
			<td>
				<s:property value="address" />
			</td>	
			<td>
				<s:property value="depth" />
			</td>
			<td>
				<s:property value="width" />
			</td>
			<td>
				<s:property value="length" />
			</td>	
			<td>
				<s:property value="status" />
			</td>
			<td>
				<s:property value="utility_type" />
			</td>		
		</tr>
	</s:iterator>
</table>






















































