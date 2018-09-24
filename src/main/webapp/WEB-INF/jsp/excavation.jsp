<%@  include file="header.jsp" %>
<!-- 
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<s:form action="excavation" method="post">    
  <s:if test="excavation.id == ''">
		<h3>New Excavation</h3>
		To add a new excavation,
		<ul>
			<li>an existing permit must be available. Enter the permit number in the field specified to get the permit info. </li>
			<li>Enter the location address, such as '401 N Morton St'</li>
			<li>You do not need to enter the latitude or longitude now, these will be taken from our Master address system.</li>
			<li>You can change the lat/long later after you click on 'Save'</li>
			<li>You can add a new excavation from permit page as well.</li>
		</ul>
		<s:hidden name="excavation.permit_num" value="%{excavation.permit_num}" id="permit_num" />
		<s:hidden name="permit_id" value="" id="permit_id" />	
  </s:if>
  <s:else>
		<h3>Edit Excavation <s:property value="excavation.id" /></h3>
		
		Note:to change the location lat/long values, use 'Show on google map' to pick the right point and then click on 'Update'.
		<s:hidden name="excavation.id" value="%{excavation.id}" />
		<s:hidden name="excavation.address_id" value="%{excavation.address_id}" />
		<s:hidden name="excavation.address.id" value="%{excavation.address.id}" />
		<s:if test="excavation.hasPermit()">
			<s:hidden name="excavation.permit_num" value="%{excavation.permit_num}" id="permit_num" />
		</s:if>
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
		<caption>Excavation Info</caption>
		<tr>
			<td>
				<table width="100%">
					<tr>
						<th width="25%">Permit Number </th> 
						<td>
							<s:if test="excavation.hasPermit()">
								<s:property value="%{excavation.permit_num}" /> 
								<a href="<s:property value='#application.url' />permit.action?id=<s:property value='excavation.permit.id' />"> Related Permit</a>
							</s:if>
							<s:else>
								<s:textfield name="permit_lookup" size="20" value="" maxlength="30" id="permit_lookup"  /> *
							</s:else>
						</td>
					</tr>		  
					<tr>
						<th>Status</th> 
						<td><s:select name="excavation.status" value="%{excavation.status}" list="{'Not Started','In Progress','Complete','Temporary Patch'}"  headerKey="-1" headerValue="" /></td>
					</tr>
					<tr>
						<th>Cut Type</th> 
						<td><s:select name="excavation.cut_type" value="%{excavation.cut_type}" list="{'Street','Sidewalk','Bore','TreePlot','Other'}"  headerKey="-1" headerValue="" /></td>
					</tr>
					<tr>
						<th>Address </th> 
						<td>
							<s:textfield name="excavation.address.address" size="50" value="%{excavation.address}" maxlength="70" required="true" /> * 
							<s:if test="excavation.hasValidAddress()">
								<a href="<s:property value='#application.url' />map.action?excavation_id=<s:property value='id' />"> Show on google map </a>				
							</s:if>
						</td>
					</tr>
					<tr>
						<th>Location Lattitude</th> 
						<td><s:textfield name="excavation.address.loc_lat" size="12" value="%{excavation.address.loc_lat}" maxlength="12" id="loc_lat_id" />
						</td>
					</tr>
					<tr>
						<th>Location Longitude</th> 
						<td><s:textfield name="excavation.address.loc_long" size="12" value="%{excavation.address.loc_long}" maxlength="12" id="loc_long_id" /></td>
					</tr>		  
					<tr>
						<th>Depth </th> 
						<td><s:textfield name="excavation.depth" size="10" value="%{excavation.depth}" maxlength="10" required="true" /> *</td>
					</tr>
					<tr>
						<th>Width </th> 
						<td><s:textfield name="excavation.width" size="10" value="%{excavation.width}" maxlength="10" required="true" /> *</td>
					</tr>
					<tr>
						<th>Length </th> 
						<td><s:textfield name="excavation.length" size="10" value="%{excavation.length}" maxlength="10" required="true" /> *</td>
					</tr>
					<tr>
						<th>Utility </th> 
						<td><s:select name="excavation.utility_type_id" value="%{excavation.utility_type_id}" list="utility_types" listKey="id" listValue="name" headerKey="-1" headerValue="" /></td>
					</tr>
					<tr>
						<th valign="top">Cut Description *</th> 
						<td valign="top"><s:textarea name="excavation.cut_description" rows="5" value="%{excavation.cut_description}" cols="80" required="true" /> </td>
					</tr>		  
				</table> 
			</td>
		</tr>
		<tr>
			<s:if test="excavation.id == ''">
				<td align="right"><s:submit name="action" value="Save" /></td> 
			</s:if>
			<s:else>
				<td><table width="100%">
					<tr>
						<td align="left"><s:submit name="action" value="Update" /></td>
						<s:if test="#session.user != null && #session.user.isAdmin()" >
							<td align="right"><s:submit name="action" value="Delete" onclick="return validateDelete();" /></td>
						</s:if>
					</tr>
				</table></td>
			</s:else>
		</tr>
  </table>
</s:form>
<s:if test="excavation.id == ''">
	<s:if test="excavations != null ">
		<s:set var="excavationsTitle" value="excavationsTitle" />	
		<s:set var="excavations" value="excavations" />
		<%@  include file="excavations.jsp" %>	  
	</s:if>
</s:if>
<%@  include file="footer.jsp" %>
<script>
function validateDelete(){
  var x = confirm("Are you sure you want to delete?");
  if (x)
    return true;
  else
    return false;
}
</script>    






















































