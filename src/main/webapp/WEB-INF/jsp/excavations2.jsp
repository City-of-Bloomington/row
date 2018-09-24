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
		<td>Cut Type</td>
		<td>Permit Number</td>
		<td>Status</td>	
		<td>Address</td>
		<td>Location (lat)</td>
		<td>Location (long)</td>
  </tr>
  <s:iterator var="one" value="#excavations" status="conStatus">
		<tr <s:if test="conStatus.even">style="background:lightgray"</s:if>>
			<td><s:property value="id" /></td>				  
			<td>&nbsp;
				<s:property value="permit.company" />
			</td>
			<td>
				<s:property value="cut_type" />
			</td>
			<td>
				<s:property value="permit_num" />
			</td>
			<td>
				<s:property value="status" />
			</td>	
			<td>
				<s:property value="address" />
			</td>	
			<td>
				<s:property value="address.loc_lat" />
			</td>
			<td>
				<s:property value="address.loc_long" />
			</td>	
		</tr>
	</s:iterator>

</table>






















































