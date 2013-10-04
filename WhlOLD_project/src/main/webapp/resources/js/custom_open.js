/**
 *  @MPiunti Reply.eu
 */
var urlHolder = new Object();
var Document = "Document";

var theUI = {
	nodes:{Document:{color:"#b94a48", shape:"dot", alpha:1} },
	edges:{Document:{}}
};

var CLR = {
    branch:"#b2b19d",
    code:"#3a87ad",
    doc:"#922E00",
    demo:"#468847"
};

function loadTable() {

	$.get(urlHolder.headers, function(response) {
		console.log(response);	
		$('#tableOpenData').find('thead').children().remove(); 
 		var row = '<tr><th><strong>REST URI</strong></th>';
 		for (var j=0; j<response.openNodes[0].row.length; j++) { 			
 			row += '<th>' + response.openNodes[0].row[j] + '</th>';
 			
 		}
 		row += '<th id="dbpedia"></th><th id="venue"></th></tr>';
 		console.log(row);
 		$('#tableOpenData').find('thead').append(row);
	});

	$.get(urlHolder.records, function(response) {
		//console.log(response);	
		$('#tableOpenData').find('tbody').children().remove();
		var map = false;


 		for (var i=0; i<response.openNodes.length; i++) {
			var row = '<tr>';
			     //  graph nodes and links
					theUI.nodes[response.openNodes[i].row[0] ] = {color:CLR.branch, shape:"dot", alpha:1};
					level1.push(response.openNodes[i].row[0]);
					theUI.edges[Document][response.openNodes[i].row[0]] = {length:.3, label:"INCLUDES"};	
			
			
			//row += '<td><input type="radio" name="index" id="index" value="'+i+'"></td>';	
			var nodeId = response.openNodes[i].id;
			row += '<td><a href="http://localhost:7474/db/data/node/'+nodeId+'" target="_blank">'+nodeId+'</a></td>';
			// put the ROW Array in the datatable
			for (var j=0; j<response.openNodes[i].row.length; j++) {
				row += '<td>' + response.openNodes[i].row[j] + '</td>';				
			}
			
			if(response.openNodes[i].dBPediaLinks!=null &&
 					response.openNodes[i].dBPediaLinks.length>0){
				if(response.openNodes[i].dBPediaLinks[0].type === "DEEZER_TRACKS"){
					row += '<td><audio src="'+response.openNodes[i].dBPediaLinks[0].uri+'" controls></audio>';
					//  graph nodes and links
						theUI.nodes[response.openNodes[i].dBPediaLinks[0].uri ] = {color:CLR.code, alpha:0, link:response.openNodes[i].dBPediaLinks[0].uri};
						theUI.edges[response.openNodes[i].row[0]] = {};
						theUI.edges[response.openNodes[i].row[0]][response.openNodes[i].dBPediaLinks[0].uri] = {length:.3, label:"DBP_LINKED"};	
					$('#dbpedia').html("Track");
				} else if (response.openNodes[i].dBPediaLinks[0].uri != null || response.openNodes[i].dBPediaLinks[0].description != null) {
	 				row += '<td><a href=\''+response.openNodes[i].dBPediaLinks[0].uri+'\'';
	 				row +=    'title=\''+response.openNodes[i].dBPediaLinks[0].description+'\'>';
	 				row += '<span class="label label-info">'+response.openNodes[i].dBPediaLinks[0].type+'</span></a>';
		 			//  graph nodes and links
						theUI.nodes[response.openNodes[i].dBPediaLinks[0].uri] = {color:CLR.code, alpha:0, link:response.openNodes[i].dBPediaLinks[0].uri};
						theUI.edges[response.openNodes[i].row[0]] = {};
						theUI.edges[response.openNodes[i].row[0]][response.openNodes[i].dBPediaLinks[0].uri] = {length:.3, label:"DBP_LINKED"};	
	 				$('#dbpedia').html("Linked Data");
				}
 				row += '</td>';
 				
 			} else {
 				row += '<td></td>';
 			}
 			if(response.openNodes[i].venues!=null &&
 					response.openNodes[i].venues.length>0){
 				var venue = response.openNodes[i].venues[0];
 				row += '<td><span class="label label-success"> Point('+venue.latitude+', '+venue.longitude+ ')</span></td>';
	 			//  graph nodes and links
					theUI.nodes[venue.wkt] = {color:CLR.demo, alpha:0, link:venue.wkt};
					theUI.edges[venue] = {};
					theUI.edges[venue][venue.wkt] = {length:.3, label:"LOCATED"};	
 				$('#venue').html("Venue");
 				map=true;
 			} else {

 				row += '<td></td>';
 			}
			row += '</tr>';
			//console.log(row);	
			$('#tableOpenData').find('tbody').append(row);
 		}
 		
 		$('#tableOpenData').data('model', response.openNodes);
		toggleForms('hide'); 
		
		if(!map) {
			$('#map-canvas').hide();
		}
 	});
	
		
}

function submitNewRecord() {
	$.post(urlHolder.add, {
			username: $('#newUsername').val(),
			password: $('#newPassword').val(),
			firstName: $('#newFirstName').val(),
			lastName: $('#newLastName').val(),
			role: $('#newRole').val()
		}, 
		function(response) {
			if (response != null) {
				loadTable();
				toggleForms('hide'); ;
				toggleCrudButtons('show');
				alert('Success! Record has been added.');
			} else {
				alert('Failure! An error has occurred!');
			}
		}
	);	
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

function submitUpdateRecord() {
	$.post(urlHolder.edit, {
			username: $('#editUsername').val(),
			firstName: $('#editFirstName').val(),
			lastName: $('#editLastName').val(),
			role: $('#editRole').val()
		}, 
		function(response) {
			if (response != null) {
				loadTable();
				toggleForms('hide'); ;
				toggleCrudButtons('show');
				alert('Success! Record has been edited.'+response);
			} else {
				alert('Failure! An error has occurred!'+response);
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

function fillEditForm() {
	var selected = $('input:radio[name=index]:checked').val();
	$('#editUsername').val( $('#tableUsers').data('model')[selected].username );
	$('#editFirstName').val( $('#tableUsers').data('model')[selected].firstName );
	$('#editLastName').val( $('#tableUsers').data('model')[selected].lastName );
	$('#editRole').val( $('#tableUsers').data('model')[selected].role );
}

function resetNewForm() {
	$('#newUsername').val('');
	$('#newPassword').val('');
	$('#newFirstName').val('');
	$('#newLastName').val('');
	$('#newRole').val('2');
}

function resetEditForm() {
	$('#editFirstName').val('');
	$('#editLastName').val('');
	$('#editRole').val('2');
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

/**
 * Enrich Function
 * adds GEOGRAPHICAL Coordinates using Google Maps API
 */
function geo() {
   $.ajax({
        url : urlHolder.geo,
        type: 'GET',
        beforeSend: function () {
        	$('#tableOpenData').find('thead').children().remove(); 
        	$('#tableOpenData').find('tbody').children().remove(); 
        },
        success : function (data, textStatus, xhr) {
        	loadTable();
        },
        error: function (xhr, textStatus, errorThrown) {
            console.log(textStatus);
        }
   });
}

/**
 * Enrich Function
 * adds DBPedia LINK using DBPedia Lookup Service API
 */
function dbpedia() {	
   $.ajax({
        url : urlHolder.dbpedia,
        type: 'GET',
        beforeSend: function () {
        	$('#tableOpenData').find('thead').children().remove(); 
        	$('#tableOpenData').find('tbody').children().remove(); 
        },
        success : function (data, textStatus, xhr) {
        	loadTable();
        },
        error: function (xhr, textStatus, errorThrown) {
            console.log(textStatus);
        }
   });
}

/**
 * Enrich Function
 * adds Alchemy disambiguation using ALCHEMY Entity Extraction API
 * http://www.alchemyapi.com/api/entity
 */
function alchemy() {	
   $.ajax({
        url : urlHolder.alchemy,
        type: 'GET',
        beforeSend: function () {
        	$('#tableOpenData').find('thead').children().remove(); 
        	$('#tableOpenData').find('tbody').children().remove(); 
        },
        success : function (data, textStatus, xhr) {
        	loadTable();
        },
        error: function (xhr, textStatus, errorThrown) {
            console.log(textStatus);
        }
   });
}
   
   /**
    * Enrich Function
    * adds Deezer Music API
    * http://api.deezer.com
    */
   function deezer() {	
      $.ajax({
           url : urlHolder.deezer,
           type: 'GET',
           beforeSend: function () {
           	$('#tableOpenData').find('thead').children().remove(); 
           	$('#tableOpenData').find('tbody').children().remove(); 
           },
           success : function (data, textStatus, xhr) {
           	loadTable();
           },
           error: function (xhr, textStatus, errorThrown) {
               console.log(textStatus);
           }
      });
   }
   
   
   function plot(){
	    var CLR = {
	      branch:"#b2b19d",
	      code:"orange",
	      doc:"#922E00",
	      demo:"#a7af00"
	    };

	   /* Max_Hernst = "Max Hernst";
	    var theUI = {
	      nodes:{"Artists":{color:"red", shape:"dot", alpha:1}, 
	      
	    	    Max_Hernst:{color:CLR.branch, shape:"dot", alpha:1}, 
	             halfviz:{color:CLR.demo, alpha:0, link:'#'},
	             atlas:{color:CLR.demo, alpha:0, link:'#'},
	             echolalia:{color:CLR.demo, alpha:0, link:'#'},

	            Luc_Besson:{color:CLR.branch, shape:"dot", alpha:1}, 
	             reference:{color:CLR.doc, alpha:0, link:'#'},
	             introduction:{color:CLR.doc, alpha:0, link:'#'},

	            Henry_Bergson:{color:CLR.branch, shape:"dot", alpha:1},
	             github:{color:CLR.code, alpha:0, link:'#'},
	             ".zip":{color:CLR.code, alpha:0, link:'#'},
	             ".tar.gz":{color:CLR.code, alpha:0, link:'#'},
	             
	            Jason_Pollock:{color:CLR.branch, shape:"dot", alpha:1},
	             github:{color:CLR.code, alpha:0, link:'#'},
	             "GitHub":{color:CLR.code, alpha:0, link:'#'},
	             "SourceForge":{color:CLR.code, alpha:0, link:'#'},
	             "Heroku":{color:CLR.code, alpha:0, link:'#'}
	            },
	      edges:{
	    	"Artists":{
	    		Max_Hernst:{length:.3, label:"OWNS"},
	    		Luc_Besson:{length:.3, label:"OWNS"},
	    		Henry_Bergson:{length:.3},
	    		Jason_Pollock:{length:.3, label:"OWNS"}
	        },
	        Max_Hernst:{
	        	halfviz:{label:"OWNS"},
	            atlas:{},
	            echolalia:{label:"OWNS"}
	        },
	        Luc_Besson:{
	        	reference:{},
	            introduction:{label:"OWNS"}
	        },
	        Henry_Bergson:{".zip":{},
	            ".tar.gz":{label:"OWNS"},
	            "github":{}
	        },
	        Jason_Pollock:{"GitHub":{},
	            "SourceForge":{label:"OWNS"},
	            "Heroku":{}
	        }
	      }
	    };*/


	    var sys = arbor.ParticleSystem();
	    sys.parameters({stiffness:900, repulsion:2000, gravity:true, dt:0.015});
	    sys.renderer = Renderer("#thegraph");
	    sys.graft(theUI);
	    
	    var nav = Nav("#nav");
	    $(sys.renderer).bind('navigate', nav.navigate);
	    $(nav).bind('mode', sys.renderer.switchMode);
	    nav.init();
	  }


