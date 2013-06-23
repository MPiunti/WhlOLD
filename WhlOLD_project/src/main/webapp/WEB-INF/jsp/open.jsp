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

	<!-- Bootstrap -->
    <script src="<c:url value='/resources/js/bootstrap.min.js'/>"></script>
    <script type='text/javascript' src='<c:url value="/resources/js/jquery-1.6.4.min.js"/>'></script>
	<script type='text/javascript' src='<c:url value="/resources/js/custom_open.js"/>'></script>

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
	});
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
		
		<button type='button' id='geoBtn' class="btn btn-primary" data-loading-text="<i class='icon-upload icon-white'></i>"> Geo </button>
		<button type='button' id='dbpediaBtn' class="btn btn-primary" data-loading-text="<i class='icon-upload icon-white'></i>"> DBPedia </button>
		<button type='button' id='alchemyBtn' class="btn btn-primary" data-loading-text="<i class='icon-upload icon-white'></i>"> Alchemy Disambiguation </button>
		<button type='button' id='deezerBtn' class="btn btn-primary" data-loading-text="<i class='icon-upload icon-white'></i>"> Deezer </button>
		<input type='button' value='Delete' id='deleteBtn' class="btn btn-primary" data-loading-text="<i class='icon-upload icon-white'></i>"/>		
		<input type='button' value='Quit All' id='quitBtn' class="btn btn-primary" data-loading-text="<i class='icon-upload icon-white'></i>" />
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
</body>
</html>
