<%@  include file="header.jsp" %>
<!-- 
 * @copyright Copyright (C) 2014-2015 City of Bloomington, Indiana. All rights reserved.
 * @license http://www.gnu.org/copyleft/gpl.html GNU/GPL, see LICENSE.txt
 * @author W. Sibo <sibow@bloomington.in.gov>
 *
	-->
  
<div id="map" style="width: 700px; height: 500px"></div>
<br />
<%@  include file="colorScheme.jsp" %>
<h3>Excavation Locations </h3>
<s:if test="hasActionMessages()">
	<div class="welcome">
		<s:actionmessage/>
	</div>
</s:if>  
<p>Note: you can click on any marker to show related info</p>
<!--  
<ul id="ul_div">
	<s:iterator value="excavations" status="addrStatus" >
		<li id="<s:property value='id' />" class="loc_temp"><s:property value="address" /> (<s:property value="address.loc_lat" />,<s:property value="address.loc_long" />)</li>
	</s:iterator>
</ul>
-->
<script src="https://maps.googleapis.com/maps/api/js?key=<s:property value='key' />" type="text/javascript"></script>	

<script>

var addresses = [
	<s:iterator value="cuts" status="addrStatus" >
	["<s:property value="id" />", "<s:property value="address" />",<s:property value="address.loc_lat" />,<s:property value="address.loc_long" />,<s:property value="utility_type_id" />,"<s:property value="status" />","<s:property value="permit.company" />","<s:property value="permit_num" />"]
	<s:if test="!#addrStatus.last" >,</s:if>
	</s:iterator>
];
var marker = null;
var markers = new Object(); // or {}
var showing = [];
var new_batch = [];
var map;
var firstTime = true;
var colors = ['orange-dot.png','pink-dot.png','yellow-dot.png','ltblue-dot.png','green-dot.png','red-dot.png','blue-dot.png','purple-dot.png'];
			 
var myStyles =[{
    featureType: "poi",
    elementType: "labels",
    stylers: [{ visibility: "off" }]
}];
var bounds = new google.maps.LatLngBounds();
function setAllMap(map) {
    for (var i = 0; i < markers.length; i++) {
        markers[i].setMap(map);
    }
}

// Removes the markers from the map, but keeps them in the array.
function clearMarkers() {
    setAllMap(null);
}
// remove one marker from the map
function clearMarker(id) {
	if(markers.hasOwnProperty(id)){
		// var len = Object.keys(markers).length;
		markers[id].setMap(null);
		delete markers[id];
	}
}

function initialize() {
	
    var mapOptions = {
		styles: myStyles,
		zoom:16,
		center: new google.maps.LatLng(<s:property value="%{midPoint.loc_lat}" />,<s:property value="%{midPoint.loc_long}" />)
    };
    map = new google.maps.Map(document.getElementById('map'), mapOptions);
	
    placeMarker(addresses, map);
	map.fitBounds(bounds);
	//
	// this is when the user pan or zoom
	//
	google.maps.event.addListener(map, 'idle', function () {
		drawMapFromCurrentBounds(map);
    });
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
			markers[addr[0]] = marker;
			showing.push(addr[0]); // id
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

function drawMapFromCurrentBounds(map) {
  if(firstTime){
		firstTime = false;
		return;
	}
  var bounds = map.getBounds();
  var swp = bounds.getSouthWest();
  var nep = bounds.getNorthEast();
	//
  // get lat/longs 
  var lat_from = swp.lat();
  var long_from = swp.lng();
  var lat_to = nep.lat();
  var long_to = nep.lng();
	//
	// we need to delete some of the old items that are not showing 
	// and add only the new ones
	//
	var url = "<s:property value='#application.url' />ExcavationService?lat_from="+lat_from+"&long_from="+long_from+"&lat_to="+lat_to+"&long_to="+long_to;
	$.getJSON(url, function (data) {
		new_batch = new Array();
		for(var j=0;j<data.length;j++){
			var cut = data[j];
			new_batch.push(cut.id);						
			if(markers.hasOwnProperty(cut.id)){
				var jj = $.inArray(cut.id, showing);
				if(jj > -1){
					showing.splice(jj, 1); // remove from this list
				}
			}
			else{
				var util_id = cut.utility_type_id;
				if(util_id == '' || util_id === undefined){
					util_id = 0;
				}
				marker = new google.maps.Marker({
					position: new google.maps.LatLng(cut.loc_lat,cut.loc_long),
					title: cut.address,
					icon: '<s:property value="#application.url" />js/images/'+colors[util_id],
					map: map
				}); 
				attachMessage2(marker, cut);
				// var listItem= "<li id=\""+cut.id+"\">"+cut.address+" ("+cut.loc_lat+" "+cut.loc_long+")</li>";
				// $("#ul_div").append(listItem);
				markers[cut.id] = marker;
			}
		} // end of loop
		removeObsecured();
		showing = new_batch;
	}) // end of $.getJSON
}
// remove all the markers that are not showing anymore
function removeObsecured(){
	for(var j=0;j<showing.length;j++){
		clearMarker(showing[j]);
		var node = document.getElementById(showing[j]);
		if(node.parentNode){
			node.parentNode.removeChild(node);
		}
	}
}
function attachMessage2(marker, cut) {
    var infowindow = new google.maps.InfoWindow({
		content: cut.address+"<br /><a href=\"<s:property value='#application.url' />excavationView.action?id="+cut.id+"\">"+cut.permit_num+"</a><br />"+cut.status
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
























































