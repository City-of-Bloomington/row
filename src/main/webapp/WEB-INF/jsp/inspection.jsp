<%@  include file="header.jsp" %>
<!-- 
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<s:form action="inspection" method="post">    
  <s:if test="inspection.id == ''">
		<h3>New Inspection</h3>
		To add a new inspection, an existing permit must be available. Enter the permit number in the field specified to get the permit info. <br />
		You can add a new inspection from permit page as well.
		<s:hidden name="inspection.permit_num" value="%{inspection.permit_num}" id="permit_num" />
		<s:hidden name="permit_id" value="" id="permit_id" />		
  </s:if>
  <s:else>
		<h3>Edit Inspection <s:property value="inspection.id" /></h3>
		<s:hidden name="inspection.id" value="%{inspection.id}" />
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
		<caption>Inspection Info</caption>
		<tr>
			<td>
				<table width="100%">
					<tr>
						<th width="25%">Permit Number </th>
						<td>
							<s:if test="inspection.permit_num == ''">
								<s:textfield name="permit_lookup" size="20" value="" maxlength="30" id="permit_lookup"  />
							</s:if>
							<s:else>
								<s:property value='inspection.permit_num' /> <a href="<s:property value='#application.url' />permit.action?id=<s:property value='inspection.permit.id' />"> Related Permit</a>
							</s:else>
						</td>
					</tr>
					<tr>
						<th width="25%">Date </th> 
						<td><s:textfield name="inspection.date" size="10" value="%{inspection.date}" maxlength="10" required="true" cssClass="date" /> *</td>
					</tr>
					<tr>
						<th>Status</th> 
						<td><s:select name="inspection.status" value="%{inspection.status}" list="{'Not Started','In Progress','Need Touchup','Need Followup','Completed'}"  headerKey="-1" headerValue="" /></td>
					</tr>
					<tr>
						<th>Inspector</th> 
						<td><s:select name="inspection.inspector_id" value="%{inspection.inspector_id}" list="inspectors" listKey="id" listValue="fullName" headerKey="-1" headerValue="" required="true" /> *</td>
					</tr>
					<tr>
						<th>Followup Date </th> 
						<td><s:textfield name="inspection.followup_date" size="10" value="%{inspection.followup_date}" maxlength="10" cssClass="date" /></td>
					</tr>
					<tr>
						<th valign="top">Notes </th> 
						<td><s:textarea name="inspection.notes" rows="5" value="%{inspection.notes}" cols="100" /></td>
					</tr>
				</table> 
			</td>
		</tr>
		<tr>
			<s:if test="inspection.id == ''">
				<td align="right"><s:submit name="action" value="Save" /></td> 
			</s:if>
			<s:else>
				<td><table width="100%">
					<tr>
						<td align="left"><s:submit name="action" value="Update" /></td>
						<td align="right"><s:submit name="action" value="Delete" onclick="validateDelete()" /></td>
					</tr>
				</table></td>
			</s:else>
		</tr>
  </table>
</s:form>

<%@  include file="footer.jsp" %>























































