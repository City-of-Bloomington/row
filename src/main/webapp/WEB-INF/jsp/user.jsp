<%@  include file="header.jsp" %>
<!-- 
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<s:form action="user" method="post" id="form_id">    
  <s:if test="rowUser.id == ''">
		<h3>New User</h3>
	</s:if>
	<s:else>
		<h3>Edit User: <s:property value="rowUser" /></h3>
		<s:hidden name="rowUser.id" value="%{rowUser.id}" />
	</s:else>
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
						<th>Username *</th> 
						<td><s:textfield name="rowUser.empid" size="50" value="%{rowUser.empid}" maxlength="70" required="true" /></td>
					</tr>
					<tr>
						<th>Full Name *</th> 
						<td><s:textfield name="rowUser.fullName" size="50" value="%{rowUser.fullName}" maxlength="70" required="true" /></td>
					</tr>
					<tr>
						<th>Role </th> 
						<td><s:radio name="rowUser.role" value="%{rowUser.role}" list="#{'Edit':'Edit','Edit:Delete':'Edit and Delete','Edit:Delete:Admin':'Admin (all)'}" /> </td>
					</tr>
					<tr>
						<th>Active </th> 
						<td><s:checkbox name="rowUser.activeStatus" value="%{rowUser.activeStatus}" />Yes (Uncheck to disable) </td>
					</tr>					
				</table> 
			</td>
		</tr>
		<tr>
			<s:if test="rowUser.id == ''">
				<td align="right">	  		  
					<s:submit name="action" value="Save" />
				</td>		  
			</s:if>
			<s:else>
				<td>
					<table width="100%">
						<tr>
							<td align="center">
								<s:submit name="action" value="Update" />
							</td>
							<td align="right">
								<a href="<s:property value='#application.url'/>user.action">New User</a>								
							</td>
						</tr>
					</table>
				</td>	
			</s:else>
		</tr>
  </table>
</s:form>
  
<s:if test="users != null ">
	<s:set var="usersTitle" value="usersTitle" />	
	<s:set var="users" value="users" />
	<%@  include file="users.jsp" %>	  
</s:if>  
<%@  include file="footer.jsp" %>























































