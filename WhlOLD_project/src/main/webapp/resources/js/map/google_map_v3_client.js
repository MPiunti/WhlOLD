/**
 * Google Map JS UTILTIES
 */

  var marker, i;
  
  // The Bermuda Triangle
  var polygonCoords = new Array();  
  // pushLoi(['Miami',new google.maps.LatLng(25.774252, -80.190262), 'this is some content for MIAMI.', 'http://google.com']);
 //  pushLoi(['Puerto Rico', new google.maps.LatLng(18.466465, -66.118292), 'this is some content for Puerto Rico.', 'http://google.com']);
  // pushLoi(['Bermuda',new google.maps.LatLng(32.321384, -64.757370), 'this is some content for BERMUDA', 'http://google.com']);

  
  /**
   * DRAW MAP INSIDE the map element
   */
  function drawMap(mapDom)  {
	  var bounds = new google.maps.LatLngBounds();
  
	  for (i = 0; i < polygonCoords.length; i++) {
	    bounds.extend(polygonCoords[i][1]);
	  }
	
	  // The Center of the Bermuda Triangle - (25.3939245, -72.473816)
	  //console.log(bounds.getCenter());
	
	  var map = new google.maps.Map(document.getElementById(mapDom), {
	    zoom: 10,
	    center: bounds.getCenter(),
	    mapTypeId: google.maps.MapTypeId.ROADMAP
	  });

	  var infowindow = new google.maps.InfoWindow();
	
	  for (i = 0; i < polygonCoords.length; i++) {  
	    marker = new google.maps.Marker({
	      position: polygonCoords[i][1], //new google.maps.LatLng(locations[i][1], locations[i][2]),
	      map: map
	  });

	  google.maps.event.addListener(marker, 'click', (function(marker, i) {
	      return function() {
	        infowindow.setContent(pushModlInfo(polygonCoords[i][0], polygonCoords[i][2], polygonCoords[i][3]));
	        infowindow.open(map, marker);
	      }
	    })(marker, i));
	  }
  }
  
  /**
  * push a location array in the LOI array
  */
  function pushLoi(loi){
		polygonCoords.push(loi);
  };
  
  /**
   * push a location array in the LOI array (using distinct Coordinates)
   */
  function pushLoi(title, Lat, Lng, content, url ){
	  pushLoi(title, new google.maps.LatLng(Lat, Lng), content, url);
  };
  
  /**
   * push a location array in the LOI array (using LatLng)
   */
  function pushLoi(title, LatLng, content, url ){
	  var low = new Array();
	  low.push(title);
	  low.push(LatLng);	  
	  low.push(content);
	  low.push(url);
	  polygonCoords.push(low);
  };
  

 
  
  /**
  * return a formatted content for Google Maps pop ups
  */
  function pushModlInfo(title, body, link){
    var contentString = '<div id="content">'+
		 '<div id="siteNotice">'+
  	 '</div>'+
	     '<h2 id="firstHeading" class="firstHeading">'+title+'</h2>'+
	     '<div id="bodyContent">'+
 		 	'<p><b>'+title+'</b>,' +body+'</p>'+
 		 	'<p>Web Space: <a href="'+link+'" target="_blank">'+title+'</a></p>'+
	     '</div>'+
	     '</div>';
    return contentString;
  }