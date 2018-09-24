<%@  include file="header.jsp" %>
<!-- 
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<s:form action="map" method="post" id="form_id" >    
  <s:hidden name="address.id" value="%{address.id}" />
  <s:hidden name="address.excavation_id" value="%{address.excavation_id}" />
  <s:hidden name="address.address" value="%{address.address}" />
  
  <div id="map" style="width: 400px; height: 250px"></div>
  <br />
  Note:if you make any changes, please click on 'Update'. <br />
  Update will take you back to the related excavation page.
  <table width="75%" border="1">
		<caption>Location Map</caption>
		<tr>
			<td>
				<table width="100%">
					<tr>
						<th>Excavation</th>
						<td>
							<a href="<s:property value='#application.url' />excavation.action?id=<s:property value='%{address.excavation_id}' />"> <s:property value="address.excavation_id" /></a>
						</td>
					</tr>
					<tr>
						<th>Address</th>
						<td><s:property value="%{address.address}" /></td>
					</tr>
					<tr>
						<th>Lat: </th>
						<td><s:textfield name="address.loc_lat" size="12" maxlength="12" value="%{address.loc_lat}" id="loc_lat_id" /></td>
					</tr>
					<tr>
						<th>Long: </th>
						<td><s:textfield name="address.loc_long" value="%{address.loc_long}" size="12" maxlength="12" id="loc_long_id" /></td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td align="right">
				<s:submit name="action" value="Update" />				
			</td>
		</tr>
  </table>
</s:form>
<script type="text/javascript" src="//maps.google.com/maps/api/js?sensor=false"></script>	
<script>
var marker = null;
  var myStyles =[{
		featureType: "poi",
		elementType: "labels",
		stylers: [
			{ visibility: "off" }
		]
  }
  ];	  
function initialize() {
  var mapOptions = {
		zoom: 17,
		styles: myStyles,
		center: new google.maps.LatLng(<s:property value="%{address.loc_lat}" />,<s:property value="%{address.loc_long}" />)
  };
  var map = new google.maps.Map(document.getElementById('map'), mapOptions);
  google.maps.event.addListener(map, 'click', function(e) {
		placeMarker(e.latLng, map);
  });
  placeMarker(mapOptions.center, map);
}
  
function placeMarker(position, map) {
  if(marker) marker.setMap(null);
  marker = new google.maps.Marker({
		position: position,
		map: map
  });
  map.panTo(position);
  document.getElementById('loc_lat_id').value=position.lat();
  document.getElementById('loc_long_id').value=position.lng();	  
}
google.maps.event.addDomListener(window, 'load', initialize);
</script>  
<%@  include file="footer.jsp" %>
























































