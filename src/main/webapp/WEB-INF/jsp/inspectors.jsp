<%@  include file="header.jsp" %>
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
<h3>Inspectors </h3>
<p>To add a new inspector, the inspector user info must be entered into users table first and available in the list below </p>
<s:if test="haveUsers()">
	<h4>Potential Inspectors</h4>
	<table border="1">
  <tr>
		<td>Full Name</td>
		<td>Username</td>
		<td>Active</td>
  </tr>
  <s:iterator var="one" value="users" status="rowStatus">
		<tr>
			<td>
				Add as <a href="<s:property value='#application.url' />inspector.action?action=new&id=<s:property value='id' />"> Inspector <s:property value="fullName" /></a>
			</td>				  
			<td>
					<s:property value="empid" />
			</td>
			<td>
				<s:property value="isActive()" />
			</td>		
		</tr>
	</s:iterator>
	</table>
</s:if>
<h2>All Inspectors (including inactive ones)</h2>
<table border="1">
  <tr>
		<td>ID</td>
		<td>Username</td>
		<td>Full Name </td>
		<td>Phone</td>
		<td>Fax #</td>
		<td>Active</td>
  </tr>
  <s:iterator var="one" value="inspectors" status="rowStatus">
		<tr <s:if test="rowStatus.even">style="background:lightgray"</s:if>>
			<td>
				<a href="<s:property value='#application.url' />inspector.action?id=<s:property value='id' />"> Update <s:property value="id" /></a>
			</td>				  
			<td>
					<s:property value="empid" />
			</td>
			<td>
					<s:property value="fullName" />
			</td>
			<td>
				<s:property value="phone" />
			</td>
			<td>
				<s:property value="fax_num" />
			</td>			
			<td>
				<s:property value="activeInspectStatus" />
			</td>		
		</tr>
	</s:iterator>
</table>

<%@  include file="footer.jsp" %>




















































