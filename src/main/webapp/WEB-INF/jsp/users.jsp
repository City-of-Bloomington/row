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
		<td>Username</td>
		<td>Full Name </td>
		<td>Role</td>
		<td>Active</td>
  </tr>
  <s:iterator var="one" value="#users" status="rowStatus">
		<tr <s:if test="rowStatus.even">style="background:lightgray"</s:if>>
			<td>
				<a href="<s:property value='#application.url' />user.action?id=<s:property value='id' />"> <s:property value="id" /></a>
			</td>				  
			<td>
					<s:property value="empid" />
			</td>
			<td>
					<s:property value="fullName" />
			</td>
			<td>
				<s:property value="role" />
			</td>
			<td>
				<s:property value="isActive()" />
			</td>		
		</tr>
	</s:iterator>
</table>






















































