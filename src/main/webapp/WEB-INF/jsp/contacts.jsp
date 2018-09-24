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
<h3><s:property value="#contactsTitle" /></h3>
<table border="1" width="100%">
  <tr>
		<td>ID</td>
		<td>Full Name</td>
		<td>Title </td>
		<td>Category</td>
		<td>Address</td>
		<td>City, State Zip</td>
		<td>Phones</td>
		<td>Email</td>
  </tr>
  <s:iterator var="one" value="#contacts" status="conStatus">
		<tr <s:if test="conStatus.even">style="background:lightgray"</s:if>>
			<td>
				<a href="<s:property value='#application.url' />contact.action?id=<s:property value='id' />"> <s:property value="id" /></a>
			</td>				  
			<td>
				<s:property value="fullName" />
			</td>
			<td>
				<s:property value="title" />
			</td>
			<td>
				<s:property value="type" />
			</td>	
			<td>
				<s:property value="address" />
			</td>
			<td>
				<s:property value="cityStateZip" />
			</td>		
			<td>
				<s:property value="phones" />
			</td>		
			<td>			
				<s:property value="email" />
			</td>
		</tr>
	</s:iterator>
</table>






















































