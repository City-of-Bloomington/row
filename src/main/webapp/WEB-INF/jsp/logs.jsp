<!-- 
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->

<table border="1" width="80%">
  <caption>Citation Update History</caption> 
	<tr>
		<td>Date & Time</td>  
		<td>User</td>
		<td>Citation Type</td>
		<td>Status</td>
		<td>Notes</td>
	</tr> 
	<s:iterator value="#logs">
		<tr>
			<td><s:property value="date" /></td>	
			<td><s:property value="user" /></td>
			<td><s:property value="type" /></td>
			<td><s:property value="status" /></td>
			<td><s:property value="notes" /></td>
		</tr> 
	</s:iterator>
</table>























































