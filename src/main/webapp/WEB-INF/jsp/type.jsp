<%@  include file="header.jsp" %>
<!-- 
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<s:form action="type" method="post" id="form_id">    
  <s:if test="type.id == '' || type.id == '-1'">
		<s:if test="table.nameSingle == ''">
			<h3>Pick Category to Add or Edit</h3>			
		</s:if>
		<s:else>
			<h3>New <s:property value="%{table.nameSingle}" /></h3>
		</s:else>
	</s:if>
	<s:else>
		<h3>Edit <s:property value="%{table.nameSingle}" /> <s:property value="type.id" /></h3>
		<s:hidden name="type.id" value="%{type.id}" />
	</s:else>
  <s:hidden name="action2" value="" id="action_id" />  
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
  <ul>
		<li>First select one of the type category from the list</li>
		<li>To edit an item from the  specified type</li>
		<ul>
			<li>pick that item from the list below.</li>
			<li>Change the text as desired</li>
			<li>Click on 'Update'</li>
		</ul>
		<li>To add a new item to the specified type</li>
		<ul>
			<li>If you are in 'Edit' mode click on 'Add New Type'</li>
			<li>Enter a new text in the field 'Name/Description'</li>
			<li>Click on 'Save'</li>
			<li>The newely added item should show in the lower list</li>
		</ul>
  </ul>
  <table border="1" width="100%" cellpadding="0" cellspacing="0">
		<caption><s:property value="%{table.name}" /></caption>
		<tr>
			<td>
				<table width="100%">
					<tr>
						<th>Type/Category </th> 
						<td><s:select name="table_name" value="%{table_name}" list="tables" listKey="id" listValue="name" onchange="updateInput()" id="select_id" headerKey="-1" headerValue="Select Type" /></td>
					</tr>
					<tr>
						<th>Name/Description *</th> 
						<td><s:textfield name="type.name" size="50" value="%{type.name}" maxlength="70" required="true" /></td>
					</tr>
				</table> 
			</td>
		</tr>
		<tr>
			<s:if test="type.id == ''">
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
								<s:submit name="action" value="Add New Type" />
							</td>
						</tr>
					</table>
				</td>	
			</s:else>
		</tr>
  </table>
</s:form>
  
<s:if test="types != null ">
	<s:set var="typesTitle" value="typesTitle" />	
	<s:set var="types" value="types" />
	<%@  include file="types.jsp" %>	  
</s:if>  
<%@  include file="footer.jsp" %>























































