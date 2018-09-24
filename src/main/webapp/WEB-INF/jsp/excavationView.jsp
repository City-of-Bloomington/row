<%@  include file="header.jsp" %>
<!-- 
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->

<h3> Excavation <s:property value="excavation.id" /></h3>
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
<table border="1" width="100%" cellpadding="0" cellspacing="0">
	<caption>Excavation Info</caption>
	<tr>
		<td>
			<table width="100%">
				<tr>
					<th width="25%">Permit Number </th> 
					<td>
						<s:if test="excavation.permit != null">
							<s:property value="%{excavation.permit_num}" />
						</s:if>
					</td>
				</tr>		  
				<tr>
					<th>Status</th> 
					<td><s:property value="%{excavation.status}" /></td>
				</tr>
				<tr>
					<th>Cut Type</th> 
					<td><s:property value="%{excavation.cut_type}" /></td>
				</tr>
				<tr>
					<th>Address </th> 
					<td>
						<s:property value="%{excavation.address}" />
					</td>
				</tr>
				<tr>
					<th>Location Lattitude</th> 
					<td><s:property value="%{excavation.address.loc_lat}" /></td>
				</tr>
				<tr>
					<th>Location Longitude</th> 
					<td><s:property value="%{excavation.address.loc_long}" /></td>
				</tr>		  
				<tr>
					<th>Depth </th> 
					<td><s:property value="%{excavation.depth}" /></td>
				</tr>
				<tr>
					<th>Width </th> 
					<td><s:property value="%{excavation.width}" /></td>
				</tr>
				<tr>
					<th>Length </th> 
					<td><s:property value="%{excavation.length}" /></td>
				</tr>
				<tr>
					<th>Utility </th> 
					<td><s:property value="%{excavation.utility_type}" /></td>
				</tr>
				<tr>
					<th valign="top">Cut Description </th> 
					<td><s:property value="%{excavation.cut_description}" /></td>
				</tr>		  
			</table> 
		</td>
	</tr>
	<s:if test="#session != null && #session.user != null">
		<tr>
			<td align="right">
				<a href="<s:property value='#application.url'/>excavation.action?id=<s:property value='excavation.id'/>">Edit</a>
			</td>
		</tr>
	</s:if>	
</table>

<%@  include file="footer.jsp" %>























































