/**
 * Contains custom JavaScript code
 */
var urlHolder = new Object();

function loadTable() {


	$.get(urlHolder.documents, function(response) {
		console.log(response);	
		$('#tableOpenDataList').find('tbody').children().remove();

 		for (var i=0; i<response.openDocuments.length; i++) {
			var row = '<tr>';
			//row += '<td><input type="radio" name="index" id="index" value="'+i+'"></td>';
			row += '<td>' + response.openDocuments[i].name + '</td>';
			if(response.openDocuments[i].visible === 0)
			  row += '<td>Public</td>';
			else
				row += '<td>Private</td>';
			row += '<td><a href="'+urlHolder.opendata+'/?name='+response.openDocuments[i].name+'&id='+response.openDocuments[i].id+'&unique='+response.openDocuments[i].unique+'">link</a></td>';
			row += '</tr>';

			//console.log(row);	
			$('#tableOpenDataList').find('tbody').append(row);
 		}
 		
 		$('#tableOpenDataList').data('model', response.openNodes);
		toggleForms('hide'); 
 	});
}


function submitDeleteRecord() {
	var selected = $('input:radio[name=index]:checked').val();
	
	$.post(urlHolder.del, {
			username: $('#tableUsers').data('model')[selected].username
		}, 
		function(response) {
			if (response == true) {
				loadTable(urlHolder.records);
				alert('Success! Record has been deleted.');
			} else {
				alert('Failure! An error has occurred!');
			}
		}
	);
}

function quitAllRecord() {
	
	$.post( urlHolder.quit, 
			{ }, 
		function(response) {
			if (response == true) {
				loadTable(urlHolder.records);
				alert('Success! All the records have been deleted.');
			} else {
				alert('Failure! An error has occurred!');
			}
		}
	);
}


function hasSelected() {
	var selected = $('input:radio[name=index]:checked').val();
	if (selected == undefined) { 
		alert('Select a record first!');
		return false;
	}
	
	return true;
}


function toggleForms(id) {
	if (id == 'hide') {
		$('#newForm').hide();
		$('#editForm').hide();
		
	} else if (id == 'new') {
 		resetNewForm();
 		$('#newForm').show();
 		$('#editForm').hide();
 		
	} else if (id == 'edit') {
 		resetEditForm();
 		$('#newForm').hide();
 		$('#editForm').show();
	}
}

function toggleCrudButtons(id) {
	if (id == 'show') {
		$('#newBtn').removeAttr('disabled');
		$('#editBtn').removeAttr('disabled');
		$('#deleteBtn').removeAttr('disabled');
		$('#reloadBtn').removeAttr('disabled');
		
	} else if (id == 'hide'){
		$('#newBtn').attr('disabled', 'disabled');
		$('#editBtn').attr('disabled', 'disabled');
		$('#deleteBtn').attr('disabled', 'disabled');
		$('#reloadBtn').attr('disabled', 'disabled');
	}
}
