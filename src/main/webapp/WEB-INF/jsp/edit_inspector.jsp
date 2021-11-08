<%@  include file="header.jsp" %>
<!-- 
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<s:form action="inspector" method="post" id="form_id">    
	<h3>Edit Inspector: <s:property value="inspector.fullName" /></h3>
	<s:hidden name="inspector.id" value="%{inspector.id}" />
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
		<tr>
			<td>
				<table width="100%">
					<tr>
						<th>Username </th> 
						<td><s:property value="inspector.empid"/></td>
					</tr>
					<tr>
						<th>Full Name </th> 
						<td><s:property value="inspector.fullName"/></td>
					</tr>
					<tr>
						<th>Phone</th> 
						<td><s:textfield name="inspector.phone" size="20" value="%{inspector.phone}" maxlength="20" /></td>
					</tr>
					<tr>
						<th>Fax #</th> 
						<td><s:textfield name="inspector.fax_num" size="20" value="%{inspector.fax_num}" maxlength="20" /></td>
					</tr>						
					<tr>
						<th>Active </th> 
						<td><s:checkbox name="inspector.activeInspectStatus" value="%{inspector.activeInspectStatus}" />Yes (Uncheck to disable) </td>
					</tr>					
				</table> 
			</td>
		</tr>
		<tr>
			<td align="center">
				<s:submit name="action" value="Update" />
			</td>
		</tr>
  </table>
</s:form>

<%@  include file="footer.jsp" %>























































