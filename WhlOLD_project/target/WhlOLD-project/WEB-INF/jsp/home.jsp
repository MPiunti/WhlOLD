<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>

<c:url value="/home/records" var="recordsUrl"/>
<c:url value="/home/quit" var="quitUrl"/>
<c:url value="/open" var="opendataUrl"/>


<c:url value="/uploader/data" var="uploadUrl"/>

<html>
<head>
	<link rel='stylesheet' type='text/css' media='screen' href='<c:url value="/resources/css/style.css"/>'/>
	<script type='text/javascript' src='<c:url value="/resources/js/jquery-1.6.4.min.js"/>'></script>
	<script type='text/javascript' src='<c:url value="/resources/js/custom_home.js"/>'></script>

	<title>Open Data List</title>
	
	<script type='text/javascript'>
	$(function() {
		urlHolder.add = '${addUrl}';
		urlHolder.records = '${recordsUrl}';
		urlHolder.quit = '${quitUrl}';
		urlHolder.opendata = '${opendataUrl}';

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
	
	});
	</script>
</head>

<body>
	<h1 id='banner'>Open Data Dashboard</h1>
	<hr/>
	
	<table id='tableOpenDataList'>
		<thead></thead>
		<tbody></tbody>
	</table>
	
	<div id='controlBar'>
		<input type='button' value='New' id='newBtn' />
		<input type='button' value='Delete' id='deleteBtn' />
		<input type='button' value='Edit' id='editBtn' />
		<input type='button' value='Reload' id='reloadBtn' />
		<input type='button' value='Quit All' id='quitBtn' />
	</div>


	
	<a href="${uploadUrl}" target="new">UPLOAD NEW DOCUMENT</a>
</body>
</html>