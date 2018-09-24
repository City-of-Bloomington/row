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
<h3><s:property value="#companiesTitle" /></h3>
<table border="1" width="100%">
  <tr>
		<td>Company ID</td>
		<td>Name</td>
		<td>Address</td>
		<td>City, State Zip</td>
		<td>Phone</td>
		<td>Website</td>
  </tr>
  <s:iterator var="one" value="#companies" status="compStatus">
		<tr <s:if test="compStatus.even">style="background:lightgray"</s:if>>
			<td>
				<a href="<s:property value='#application.url' />company.action?id=<s:property value='id' />"> <s:property value="id" /></a>
			</td>				  
			<td>
				<s:property value="name" />
			</td>
			<td>
				<s:property value="address" />
			</td>
			<td>
				<s:property value="cityStateZip" />
			</td>		
			<td>
				<s:property value="phone" />
			</td>		
			<td>			
				<s:property value="website" />
			</td>
		</tr>
	</s:iterator>
</table>






















































