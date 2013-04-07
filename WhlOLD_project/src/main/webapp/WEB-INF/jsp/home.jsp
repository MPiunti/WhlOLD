<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>

<c:url value="/home/documents" var="documentsUrl"/>
<c:url value="/home/quit" var="quitUrl"/>
<c:url value="/open" var="opendataUrl"/>


<c:url value="/home/u" var="homeUrl"/>
<c:url value="/uploader/data" var="uploadUrl"/>
<c:url value="/auth/logout" var="logoutUrl"/>


<html>
<head>
	<link rel='stylesheet' type='text/css' media='screen' href='<c:url value="/resources/css/style.css"/>'/>
	<!-- Bootstrap -->
	<link href="<c:url value='/resources/css/bootstrap.min.css'/>" rel="stylesheet" media="screen">

	<!-- Bootstrap -->
    <script src="<c:url value='/resources/js/bootstrap.min.js'/>"></script>
    <script type='text/javascript' src='<c:url value="/resources/js/jquery-1.6.4.min.js"/>'></script>
	<script type='text/javascript' src='<c:url value="/resources/js/custom_home.js"/>'></script>

	<title>Open Data List</title>
	
	<script type='text/javascript'>
	$(function() {

		urlHolder.documents = '${documentsUrl}';
		urlHolder.quit = '${quitUrl}';
		urlHolder.opendata = '${opendataUrl}';

		loadTable();
		
		$('#newBtn').click(function() { 
			console.log("Clicked!");
			window.location.href = '<c:url value="/uploader/data" />';
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
              <li class="active"><a href="${homeUrl}">Home</a></li>
              <li><a href="${uploadUrl}">Upload Data</a></li>
              <li><a href="${logoutUrl}">Logout</a></li>
            </ul>
          </div><!--/.nav-collapse -->
        </div>
      </div>
    </div>

	<div class="container">
	<br/><br/>
	<h2>Open Data Dashboard</h2>

	
	<table id='tableOpenDataList' class="table">
		<thead></thead>
		<tbody></tbody>
	</table>
	
	<div id='controlBar'>
		<input type='button' value='New' id='newBtn' class="btn btn-primary"/>
		<input type='button' value='Delete' id='deleteBtn' class="btn btn-primary"/>
		<input type='button' value='Edit' id='editBtn' class="btn btn-primary"/>
		<input type='button' value='Reload' id='reloadBtn' class="btn btn-primary"/>
		<input type='button' value='Quit All' id='quitBtn' class="btn btn-primary" />
	</div>

    <br />
	<br />

	</div>

</body>
</html>
