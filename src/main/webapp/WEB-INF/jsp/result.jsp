<%@  include file="header.jsp" %>
<!-- 
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<s:if test="nlist.size == 0 ">
	<h3>No match found </h3>
</s:if>
<s:else>
	<h3>Total matching records <s:property value="%{nlist.size}" /></h3>
</s:else>	  
<table border="1">
	<tr>
		<th><font Size="-1">ID</font></th>		
		<s:if test="oppts.name">
			<th><font Size="-1">Name</font></th>
		</s:if>
		<s:if test="oppts.addr">
		  <th><font Size="-1">Violation Address</font></th>
		</s:if>
		<s:if test="oppts.date">
			<th><font Size="-1">Violation Date & Time</font></th>
		</s:if>
		<s:if test="oppts.courtDate">
			<th><font Size="-1">Court Date & Time</font></th>
		</s:if>						
		<s:if test="oppts.fine">
			<th><font Size="-1">Fine</font></th>
		</s:if>						
		<s:if test="oppts.status">
			<th><font Size="-1">Status</font></th>
		</s:if>
		<s:if test="oppts.ownName">
			<th><font Size="-1">Owner</font></th>
		</s:if>
		<s:if test="oppts.ownAddr">
			<th><font Size="-1">Owner Address</font></th>
		</s:if>						
		<s:if test="oppts.ownCity">
			<th><font Size="-1">Owner City, State Zip</font></th>
		</s:if>
		<s:if test="oppts.ownPhone">
			<th><font Size="-1">Owner Phones</font></th>
		</s:if>
		<s:if test="oppts.ownName">
			<th><font Size="-1">Agent</font></th>
		</s:if>
		<s:if test="oppts.ownAddr">
			<th><font Size="-1">Agent Address</font></th>
		</s:if>						
		<s:if test="oppts.ownCity">
			<th><font Size="-1">Agent Zipcode</font></th>
		</s:if>
		<s:if test="oppts.ownPhone">
			<th><font Size="-1">Agent Phones</font></th>
		</s:if>						
	</tr>
	<s:iterator value="nlist.noisies" status="rowStatus">
		<s:if test="#rowStatus.odd">
		  <tr style="background: white">
		</s:if>
		<s:else>
		  <tr style="background: lightgrey">
		</s:else>
		<td><a href="<s:property value='#application.url' />view.action?id=<s:property value='id' />"><s:property value="id" /></a></td>
		<s:if test="oppts.name">
			<td><s:property value="fullName"/></td>
		</s:if>
		<s:if test="oppts.addr">
			<td><s:property value="addressStr"/></td>
		</s:if>
		<s:if test="oppts.date">
			<td><s:property value="dateTime"/></td>
		</s:if>							
		<s:if test="oppts.courtDate">
			<td><s:property value="courtDateTime"/></td>
		</s:if>	
		<s:if test="oppts.fine">
			<td><s:property value="fine"/></td>
		</s:if>
		<s:if test="oppts.status">
			<td><s:property value="status"/></td>
		</s:if>
		<s:if test="oppts.ownName">				   
			<td>&nbsp;<s:property value="ownersNames"/></td>
		</s:if>
		<s:if test="oppts.ownAddr">				   
			<td>&nbsp;<s:property value="ownersAddrs"/></td>
		</s:if>								  
		<s:if test="oppts.ownCity">
 			<td>&nbsp;<s:property value="ownersCities"/></td>
		</s:if>
		<s:if test="oppts.ownPhone">
			<td>&nbsp;<s:property value="ownersPhones"/></td>
		</s:if>
		<s:if test="oppts.ownName">				   
			<td>&nbsp;<s:property value="agent.name" /></td>
		</s:if>
		<s:if test="oppts.ownAddr">				   
			<td>&nbsp;<s:property value="agent.address" /></td>
		</s:if>								  
		<s:if test="oppts.ownCity">				   
			<td>&nbsp;<s:property value="agent.zip" /></td>
		</s:if>
		<s:if test="oppts.ownPhone">				   
			<td>&nbsp;<s:property value="agent.phones" /></td>
		</s:if>
			</tr>
	</s:iterator>					  
</table>
	   </div>
   </body>
</html>






















































