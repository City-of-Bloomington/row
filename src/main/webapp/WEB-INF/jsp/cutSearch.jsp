<%@  include file="header.jsp" %>
<!-- 
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->

<h3>Search Excavations</h3>
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
Notes:
<ul>
	<li>To show the search locations as markerts on google map, try to narrow your search down as few as possible so that the map will not be crowded with markers, then click on 'Show on map' option.</li>
	<li>To search for the excavations in certain rectangular region, set the South-West point (lower left corner) and North-East point (top right corner) using 'Pick point on map'.</li>
	<li>Please note that when showing locations on the map there is 80-points limit max.</li>
</ul>
<s:form action="cutSearch" method="post" id="form_id" >
	<s:hidden name="action2" id="action_id" value="" />  
	<table border="1" width="100%" cellpadding="0" cellspacing="0">
		<tr>
			<td>
				<table width="100%" cellpadding="0" cellspacing="0">
					<tr>
						<th width="25%">Excavation ID </th>
						<td><s:textfield name="cutList.id" size="10" maxlength="10" value="%{cutList.id}" /></td>
					</tr>
					<tr>
						<th>Permit Number </th>
						<td><s:textfield name="cutList.permit_num" size="12" maxlength="12" value="%{cutList.permit_num}" /></td>
					</tr>		  
					<tr>
						<th>Company Name </th>
						<td><s:textfield name="company_name" size="30" maxlength="30" value="%{company_name}" id="company_name2" /> Company ID <s:textfield name="cutList.company_id" value="%{cutList.company_id}" id="company_id" />
						</td>
					</tr>
					<tr>
						<th>Contact Name </th>
						<td><s:textfield name="contact_name" size="30" maxlength="30" value="%{contact_name}" id="contact_name" /> Contact ID <s:textfield name="cutList.contact_id" value="%{cutList.contact_id}" id="contact_id" />
						</td>
					</tr>
					<tr>
						<th>Address </th>
						<td><s:textfield name="cutList.address" size="30" maxlength="30" value="%{cutList.address}" />
						</td>
					</tr>
					<tr>
						<td colspan="2" align="left">
							<table width="100%">
								<tr><th width="25%">&nbsp;</th><td width="15%">Latitude</td><td width="15%">Longitude</td><td width="20%">Pick Point on Map</td><td>&nbsp;</td></tr>
								<tr>
									<th>North-East Point </th>
									<td>
										<s:textfield name="cutList.lat_to" size="12" maxlength="12" value="%{cutList.lat_to}" id="end_lat" />
									</td>
									<td> 
										<s:textfield name="cutList.long_to" size="12" maxlength="12" value="%{cutList.long_to}" id="end_long" />
									</td>
									<td>
										<input type="button" onclick="window.open('PickPoint?point_type=end','PickPoint','toolbar=0,location=0,directories=0,status=0,menubar=1,scrollbars=1,top=100,left=100,resizable=1,width=500,height=500');" value="North-East Point" />
									</td>
									<td>|- - - - *|	  
									</td>
								</tr>
								<tr>
									<th>South-West Point </th>
									<td>
										<s:textfield name="cutList.lat_from" size="12" maxlength="12" value="%{cutList.lat_from}" id="start_lat" />
									</td>
									<td>
										<s:textfield name="cutList.long_from" size="12" maxlength="12" value="%{cutList.long_from}" id="start_long" />
									</td>
									<td>
										<input type="button" onclick="window.open('PickPoint?point_type=start','PickPoint','toolbar=0,location=0,directories=0,status=0,menubar=1,scrollbars=1,top=100,left=100,resizable=1,width=500,height=500');" value="South-West Point" />
									</td>
									<td>|* - - - -|			
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<th>Status </th>
						<td><s:select name="cutList.status" value="%{cutList.status}" list="{'Not Started','In Progress','Complete','Temporary Patch','Closed','Violation'}"  headerKey="-1" headerValue="All"  /></td>
					</tr>
					<tr>
						<th>Cut Type </th>
						<td><s:select name="cutList.cut_type" value="%{cutList.cut_type}" list="{'Street','Sidewalk','Bore','TreePlot','Other'}"  headerKey="-1" headerValue="All"  /></td>
					</tr>
					<tr>
						<th>Utility </th>
						<td><s:select name="cutList.utility_type_id" value="%{cutList.utility_type_id}" list="utility_types" listKey="id" listValue="name" headerKey="-1" headerValue="All"  /></td>
					</tr>
					<tr>
						<td colspan="2">
							<table width="100%">
								<tr>
									<th width="25%"></th><td width="20%">From</td><td>To</td>
								</tr>
								<tr>
									<th>Start Date </th>
									<td>
										<s:textfield name="cutList.date_from" size="10" maxlength="10" value="%{cutList.date_from}" cssClass="date" />
									</td>
									<td>
										<s:textfield name="cutList.date_to" size="10" maxlength="10" value="%{cutList.date_to}" cssClass="date" />
									</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<th>Sort by </th>
						<td align="left"><s:radio name="cutList.sort_by" value="%{cutList.sort_by}" list="#{'c.id DESC':'ID','c.permit_num':'Permit Number','a.address':'Address','c.status':'Status','c.cut_type':'Cut Type'}" /></td>
					</tr>		 
				</table>
			</td>
		</tr>
		<tr>
			<td><table width="100%">
				<tr>
					<td align="left"><s:submit name="action" value="Submit"/></td>
					<td align="center"><s:submit name="action" value="Export as CSV"/></td>							
					<td align="right"><s:submit name="action" value="Show on map" /></td>
				</tr>
			</table>
			</td>
		</tr>
		<s:if test="#session.user != null">
			<tr>
				<td align="left">
					To add a new excavation click <a href="<s:property value='#application.url' />excavation.action">here</a>
				</td>
			</tr>	
		</s:if>
	</table>
</s:form>
<s:if test="hasCuts()">
	<s:set var="cutsTitle" value="cutsTitle" />	
	<s:set var="cuts" value="cuts" />
	<s:set var="apikey" value="key" />
	<%@  include file="cuts.jsp" %>	  
</s:if>
			
<%@  include file="footer.jsp" %>
























































