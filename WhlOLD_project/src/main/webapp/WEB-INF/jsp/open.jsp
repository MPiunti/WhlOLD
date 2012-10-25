<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<c:url value="/open/${doc_name}" var="opendataUrl"/>
<c:url value="/open/records/${doc_name}" var="recordsUrl"/>
<c:url value="/open/headers/${doc_name}" var="headersUrl"/>
<c:url value="/open/create" var="addUrl"/>
<c:url value="/open/update" var="editUrl"/>
<c:url value="/open/delete" var="deleteUrl"/>
<c:url value="/open/quit" var="quitUrl"/>

<c:url value="/uploader/open" var="uploadUrl"/>

<html>
<head>
	<link rel='stylesheet' type='text/css' media='screen' href='<c:url value="/resources/css/style.css"/>'/>
	<script type='text/javascript' src='<c:url value="/resources/js/jquery-1.6.4.min.js"/>'></script>
	<script type='text/javascript' src='<c:url value="/resources/js/custom_open.js"/>'></script>

	<title>Open Data Records</title>
	
	<script type='text/javascript'>
	$(function() {
		// init
		urlHolder.opendata = '${opendataUrl}';
		urlHolder.headers = '${headersUrl}';
		urlHolder.records = '${recordsUrl}';		
		
		urlHolder.add = '${addUrl}';
		urlHolder.edit = '${editUrl}';
		urlHolder.del = '${deleteUrl}';
		urlHolder.quit = '${quitUrl}';
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
	});
	</script>
</head>

<body>
	<h1 id='banner'>Open Data Records</h1>
	<hr/>
	
	<table id='tableOpenData'>
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
	<s:url value="/" var="url"/>
    <a href="${url}">Home Dashboard</a>
</body>
</html>
