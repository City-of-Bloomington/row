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
<h3><s:property value="#typesTitle" /></h3>
<table border="1">
  <tr>
		<td>ID</td>
		<td>Name</td>
  </tr>
  <s:iterator var="one" value="#types" status="typeStatus">
		<tr <s:if test="conStatus.even">style="background:lightgray"</s:if>>
			<td>
				<a href="<s:property value='#application.url' />type.action?id=<s:property value='id' />&table_name=<s:property value='table_name' />"> <s:property value="id" /></a>
			</td>				  
			<td>
				<s:property value="name" />
			</td>	
		</tr>
	</s:iterator>
</table>






















































