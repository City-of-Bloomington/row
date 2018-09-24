<!-- 
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->

<h3><s:property value="#permitsTitle" /></h3>
<table border="1" width="100%">
  <tr>
		<td>ID</td>
		<td>Permit Number</td>
		<td>Status</td>
		<td>Type</td>
		<td>Project</td>
		<td>Reviewer</td>
		<td>Permit Date</td>
		<td>Start Date</td>
		<td>Fee</td>
		<td>Notes</td>
  </tr>
  <s:iterator var="one" value="#permits" status="conStatus">
		<tr <s:if test="conStatus.even">style="background:lightgray"</s:if>>
			<td>
				<a href="<s:property value='#application.url' />permit.action?id=<s:property value='id' />"> <s:property value="id" /></a>
			</td>				  
			<td>
				<s:property value="permit_num" />
			</td>		
			<td>
				<s:property value="status" />
			</td>	
			<td>
				<s:property value="permit_type" />
			</td>
			<td>
				<s:property value="project" />
			</td>
			<td>
				<s:property value="reviewer" />
			</td>	
			<td>
				<s:property value="date" />
			</td>
			<td>
				<s:property value="start_date" />
			</td>		
			<td align="right">$<s:property value="fee" />
			</td>
			<td>			
				<s:property value="notes" />
			</td>
		</tr>
	</s:iterator>
</table>






















































