<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<c:url value="/open/${doc.name}" var="opendataUrl"/>
<c:url value="/open/records/${doc.name}" var="recordsUrl"/>
<c:url value="/open/headers/${doc.name}" var="headersUrl"/>
<c:url value="/enrich/geo/${doc.name}" var="geoUrl"/>
<c:url value="/enrich/dbpedia/${doc.name}" var="dbpediaUrl"/>
<c:url value="/enrich/alchemy/${doc.name}" var="alchemyUrl"/>
<c:url value="/enrich/deezer/${doc.name}" var="deezerUrl"/>

<c:url value="/open/create" var="addUrl"/>
<c:url value="/open/update" var="editUrl"/>
<c:url value="/open/delete" var="deleteUrl"/>
<c:url value="/open/quit" var="quitUrl"/>

<c:url value="/home/u" var="homeUrl"/>
<c:url value="/uploader/data" var="uploadUrl"/>
<c:url value="/auth/logout" var="logoutUrl"/>

<html>
<head>
	<!-- <link rel='stylesheet' type='text/css' media='screen' href='<c:url value="/resources/css/style.css"/>'/>
	Bootstrap -->
	<link href="<c:url value='/resources/css/bootstrap.min.css'/>" rel="stylesheet" media="screen">
	<link href="<c:url value='/resources/css/font-awesome.min.css'/>" rel="stylesheet" media="screen">

	<!-- Bootstrap -->
    <script src="<c:url value='/resources/js/bootstrap.min.js'/>"></script>
    <script type='text/javascript' src='<c:url value="/resources/js/jquery-1.10.1.min.js"/>'></script>
	<script type='text/javascript' src='<c:url value="/resources/js/custom_open.js"/>'></script>
	<script type='text/javascript' src='<c:url value="/resources/js/bootstrap-modal.js"/>'></script>
	
	<script type='text/javascript' src="<c:url value='/resources/js/graph/jquery.address-1.4.min.js'/>"></script>
	<script type='text/javascript' src="<c:url value='/resources/js/graph/arbor.js '/>"></script>
	<script type='text/javascript' src="<c:url value='/resources/js/graph/arbor-tween.js '/>"></script>
	<script type='text/javascript' src="<c:url value='/resources/js/graph/arbor-graphics.js '/>"></script>
	<script type='text/javascript' src="<c:url value='/resources/js/graph/arbor_engine.js '/>"></script>

	<title>Open Data Records</title>
	
	<script type='text/javascript'>
	$(function() {		
		// init
		urlHolder.opendata = '${opendataUrl}';
		urlHolder.headers = '${headersUrl}';
		urlHolder.records = '${recordsUrl}';	
		urlHolder.geo = '${geoUrl}';	
		urlHolder.dbpedia = '${dbpediaUrl}';	
		urlHolder.alchemy = '${alchemyUrl}';
		urlHolder.deezer= '${deezerUrl}';
		
		urlHolder.add = '${addUrl}';
		urlHolder.edit = '${editUrl}';
		urlHolder.del = '${deleteUrl}';
		urlHolder.quit = '${quitUrl}';
		
		// fill-it!
		loadTable();
		
		$('#newBtn').click(function() { 
			toggleForms('new');
			toggleCrudButtons('hide');
		});
		
		$('#editBtn').click(function() { 
			if (hasSelected()) {
				toggleForms('edit');
				toggleCrudButtons('hide');
				fillEditForm();
			}
		});
		
		$('#reloadBtn').click(function() { 
			loadTable();
		});

		$('#deleteBtn').click(function() {
			if (hasSelected()) { 
				submitDeleteRecord();
			}
		});

		$('#quitBtn').click(function() {			
			quitAllRecord();
		});
		
		$('#newForm').submit(function() {
			event.preventDefault();
			submitNewRecord();
		});
		
		$('#editForm').submit(function() {
			event.preventDefault();
			submitUpdateRecord();
		});

		$('#closeNewForm').click(function() { 
			toggleForms('hide'); 
			toggleCrudButtons('show');
		});
		
		$('#closeEditForm').click(function() { 
			toggleForms('hide'); 
			toggleCrudButtons('show');
		});
		
		$('#geoBtn').click( function() {	
			//$(this).button('loading');
			geo();
		});
		
		$('#dbpediaBtn').click( function() {	
			//$(this).button('loading');
			dbpedia();
		});
		
		$('#alchemyBtn').click( function() {	
			//$(this).button('loading');
			alchemy();
		});
		
		$('#deezerBtn').click( function() {	
			//$(this).button('loading');
			deezer();
		});
		
		$('a.graph').click( function() {	
			console.log('click graph!');
			plot();
		});
		

	});
	</script>
	<style type="text/css">
		#myModal {
			width: 1100px; 
			//height: 800px;
			margin: 0 0 0 -450px; 
		}

       #map-canvas {
        margin: 0;
        padding: 0;
        height: 100%;
      }
    </style>

	<%-- GOOGLE MAPS API --%>
	<meta name="viewport" content="initial-scale=1.0, user-scalable=no">
    <meta charset="utf-8">
      <!--<link href="/maps/documentation/javascript/examples/default.css" rel="stylesheet">
  
    Include the maps javascript with sensor=true because this code is using a
    sensor (a GPS locator) to determine the user's location.
    See: https://developers.google.com/apis/maps/documentation/javascript/basics#SpecifyingSensor
    -->
    <script src="https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=true"></script>

    <script>
		var map;
		
		function initialize() {
		  var mapOptions = {
		    zoom: 6,
		    mapTypeId: google.maps.MapTypeId.ROADMAP
		  };
		  map = new google.maps.Map(document.getElementById('map-canvas'),
		      mapOptions);
		
		  // Try HTML5 geolocation
		  if(navigator.geolocation) {
		    navigator.geolocation.getCurrentPosition(function(position) {
		      var pos = new google.maps.LatLng(position.coords.latitude,
		                                       position.coords.longitude);
		
		      var infowindow = new google.maps.InfoWindow({
		        map: map,
		        position: pos,
		        content: 'Location found using HTML5.'
		      });
		
		      map.setCenter(pos);
		    }, function() {
		      handleNoGeolocation(true);
		    });
		  } else {
		    // Browser doesn't support Geolocation
		    handleNoGeolocation(false);
		  }
		}
		
		function handleNoGeolocation(errorFlag) {
		  if (errorFlag) {
		    var content = 'Error: The Geolocation service failed.';
		  } else {
		    var content = 'Error: Your browser doesn\'t support geolocation.';
		  }
		
		  var options = {
		    map: map,
		    position: new google.maps.LatLng(60, 105),
		    content: content
		  };
		
		  var infowindow = new google.maps.InfoWindow(options);
		  map.setCenter(options.position);
		}
		
		google.maps.event.addDomListener(window, 'load', initialize);

    </script>

</head>

<body>

    <div class="navbar navbar-inverse navbar-fixed-top">
      <div class="navbar-inner">
        <div class="container">
          <button type="button" class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="brand" href="${homeUrl}">Open Linked Data</a>
          <div class="nav-collapse collapse">
            <ul class="nav">
              <li><a href="${homeUrl}">Home</a></li>
              <li><a href="${uploadUrl}">Upload Data</a></li>
              <li><a href="${logoutUrl}">Logout</a></li>
            </ul>
          </div><!--/.nav-collapse -->
        </div>
      </div>
    </div>
	<br/>
	<br/>

	<div class="container">
	    <h3>${doc.name}</h3>
		<h5>Open Data Records</h5>
		
		
	
		<table id='tableOpenData' class="table table-hover">
			<thead></thead>
			<tbody></tbody>
		</table>
		
	
		<div id='controlBar'>
			<!-- <input type='button' value='New' id='newBtn' />
			<input type='button' value='Edit' id='editBtn' />
			<input type='button' value='Reload' id='reloadBtn' />-->
			
			<a class="btn btn btn-primary" id='geoBtn'>
				<i class="icon-map-marker"></i>
				Geo
			</a>
			<a class="btn btn btn-primary" id='dbpediaBtn'>
				<i class="icon-group"></i>
				DBPedia
			</a>
			<a class="btn btn btn-primary" id='alchemyBtn'>
				<i class="icon-font"></i>
				Alchemy
			</a>
			<a class="btn btn btn-primary" id='deezerBtn'>
				<i class="icon-play"></i>
				Deezer
			</a>
			
			<!-- <button type='button' id='geoBtn' class="btn btn-primary" data-loading-text="<i class='icon-upload icon-white'></i>"> Geo </button> 
			<button type='button' id='dbpediaBtn' class="btn btn-primary" data-loading-text="<i class='icon-upload icon-white'></i>"> DBPedia </button> 
			<button type='button' id='alchemyBtn' class="btn btn-primary" data-loading-text="<i class='icon-upload icon-white'></i>"> Alchemy Disambiguation </button>
			<button type='button' id='deezerBtn' class="btn btn-primary" data-loading-text="<i class='icon-upload icon-white'></i>"> Deezer </button> -->
			<input type='button' value='Delete' id='deleteBtn' class="btn" data-loading-text="<i class='icon-upload icon-white'></i>"/>		
			<input type='button' value='Quit All' id='quitBtn' class="btn" data-loading-text="<i class='icon-upload icon-white'></i>" />
		</div>
		
		<div id='newForm'>
			<form>
	  			<fieldset>
					<legend>Create New Record</legend>
					<label for='newUsername'>Username</label><input type='text' id='newUsername'/><br/>
					<label for='newPassword'>Password</label><input type='password' id='newPassword'/><br/>
					<label for='newFirstName'>First Name</label><input type='text' id='newFirstName'/><br/>
					<label for='newLastName'>Last Name</label><input type='text' id='newLastName'/><br/>
					<label for='newRole'>Role</label>
						<select id='newRole'>
							<option value='1'>Admin</option>
							<option value='2' selected='selected'>Regular</option>
						</select>
	  			</fieldset>
				<input type='button' value='Close' id='closeNewForm' />
				<input type='submit' value='Submit'/>
			</form>
		</div>
		
		<div id='editForm'>
			<form>
	  			<fieldset>
					<legend>Edit Record</legend>
					<input type='hidden' id='editUsername'/>
					<label for='editFirstName'>First Name</label><input type='text' id='editFirstName'/><br/>
					<label for='editLastName'>Last Name</label><input type='text' id='editLastName'/><br/>
					<label for='editRole'>Role</label>
						<select id='editRole'>
							<option value='1'>Admin</option>
							<option value='2' selected='selected'>Regular</option>
						</select>
				</fieldset>
				<input type='button' value='Close' id='closeEditForm' />
				<input type='submit' value='Submit'/>
			</form>
		</div>
		
		<br/>
		<br/>
		<a class="btn btn-large btn-info graph" data-toggle="modal" href="#myModal">
			<i class="icon-bullseye"></i>
			Graph it
		</a>
		
		<div class="modal hide" id="myModal"><!-- note the use of "hide" class -->
		  <div class="modal-header">
		    <button class="close" data-dismiss="modal">x</button>
		    <h3>Document Graph</h3>
		  </div>
		  <div class="modal-body">
		    <!--  <p>One fine Graph </p>-->
		    <canvas id="thegraph" width="400" height="200"></canvas>
		  </div>
		  <div class="modal-footer">
		    <a href="#" class="btn" data-dismiss="modal">Close</a> <!-- note the use of "data-dismiss" -->
		    <!-- <a href="#" class="btn btn-primary">Save changes</a>-->
		  </div>
		</div>
		<div id="map-canvas"></div>
	</div>

</body>
</html>
