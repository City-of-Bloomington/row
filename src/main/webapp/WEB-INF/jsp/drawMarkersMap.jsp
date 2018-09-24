<%@  include file="header.jsp" %>
<!-- 
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
<h3>Excavation Locations </h3>
<s:if test="hasActionMessages()">
	<div class="welcome">
		<s:actionmessage/>
	</div>
</s:if>  
<div id="map" style="width: 700px; height: 500px"></div>
<br />
<%@  include file="colorScheme.jsp" %>  

<p>Note: you can click on each marker to show the related info</p>
<!--  
<ul>
<s:iterator value="excavations" status="addrStatus" >
<li><s:property value="address" /> (<s:property value="address.loc_lat" />,<s:property value="address.loc_long" />)</li>
</s:iterator>
</ul>
-->
<script src="https://maps.googleapis.com/maps/api/js?key=<s:property value='key' />" type="text/javascript"></script>	
<script>
var addresses = [
	<s:iterator value="excavations" status="addrStatus" >
	[<s:property value="id" />,"<s:property value="address" />",<s:property value="address.loc_lat" />,<s:property value="address.loc_long" />,<s:property value="utility_type_id" />,"<s:property value="status" />","<s:property value="permit.company" />","<s:property value="permit_num" />"]
	<s:if test="!#addrStatus.last">,</s:if>
  </s:iterator>
];
var marker = null;
var colors = ['orange-dot.png','pink-dot.png','yellow-dot.png','ltblue-dot.png','green-dot.png','red-dot.png','blue-dot.png','purple-dot.png'];
var myStyles =[{
  featureType: "poi",
  elementType: "labels",
  stylers: [{ visibility: "off" }]
}];
var bounds = new google.maps.LatLngBounds();  
function initialize() {
  var mapOptions = {
    styles: myStyles,
		zoom:16,
    center: new google.maps.LatLng(<s:property value="%{address.loc_lat}" />,<s:property value="%{address.loc_long}" />)
  };
  var map = new google.maps.Map(document.getElementById('map'), mapOptions);
	
  placeMarker(addresses, map);
	map.fitBounds(bounds);
}
  
function placeMarker(locations, map) {
  for(var j=0;j<locations.length;j++){
		var addr = locations[j];
		var util_id = addr[4];
		if(util_id == '' || util_id === undefined){
			util_id = 0;
		}
		marker = new google.maps.Marker({
			position: new google.maps.LatLng(addr[2],addr[3]),
			title: addr[1],
			icon: '<s:property value="#application.url" />js/images/'+colors[util_id],
			map: map
		});
		attachMessage(marker, addr);
		bounds.extend(marker.position);
	}
}
var prev_infowindow = false;
function attachMessage(marker, addr) {
	var infowindow = new google.maps.InfoWindow({
		content: addr[1]+"<br /><a href=\"<s:property value='#application.url' />excavationView.action?id="+addr[0]+"\">"+addr[7]+"</a><br />"+addr[5]
	});
  google.maps.event.addListener(marker, 'click', function() {
		if(prev_infowindow ) {
      prev_infowindow.close();
    }
    prev_infowindow = infowindow;
    infowindow.open(marker.get('map'), marker);
  });
}

google.maps.event.addDomListener(window, 'load', initialize);
</script>  
<%@  include file="footer.jsp" %>
























































