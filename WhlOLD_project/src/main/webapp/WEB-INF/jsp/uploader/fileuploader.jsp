<%@page contentType="text/html;charset=UTF-8" %>
<%@page pageEncoding="UTF-8" %>
<%@ page session="false" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
 
<html>
    <head>
        <META http-equiv="Content-Type" content="text/html;charset=UTF-8">
        <!--<link rel='stylesheet' type='text/css' media='screen' href='<c:url value="/resources/css/style.css"/>'/>
		 Bootstrap -->
		
	   
        <title>Open Data - Upload</title>
    </head>
    <body>    	
    	<c:url value="/home/u" var="homeUrl"/>
    	<c:url value="/uploader/data" var="uploadUrl"/>
    	<c:url value="/auth/logout" var="logoutUrl"/>    
    
    	<div class="navbar navbar-inverse navbar-fixed-top">
	      <div class="navbar-inner">
	          <button type="button" class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
	            <span class="icon-bar"></span>
	            <span class="icon-bar"></span>
	            <span class="icon-bar"></span>
	          </button>
	          <a class="brand" href="${homeUrl}">Open Linked Data</a>
	          <div class="nav-collapse collapse">
	            <ul class="nav">
	              <li><a href="${homeUrl}">Home</a></li>
	              <li class="active"><a href="${uploadUrl}">Upload Data</a></li>
	              <li><a href="${logoutUrl}">Logout</a></li>
	            </ul>
	          </div><!--/.nav-collapse -->
	      </div>
	    </div>
    	
    	<br/>
		<br/>
    	
        <div>
           <h3>Upload Document Plugin :</h3>
        </div>
        
        <c:url value="/uploader/blueimp" var="uploadUrl"/>    
        <input id="fileupload" type="file" name="files[]" data-url="${uploadUrl}" multiple>
        
        
        <link href="<c:url value='/resources/css/bootstrap.min.css'/>" rel="stylesheet" media="screen">
		<!-- <link href="<c:url value='/resources/css/jasny-bootstrap.min.css'/>" rel="stylesheet" media="screen">-->
		
		<!-- blueimp Gallery styles -->
		<link rel="stylesheet" href="http://blueimp.github.io/Gallery/css/blueimp-gallery.min.css">
		<!-- CSS to style the file input field as button and adjust the Bootstrap progress bars -->
		<link rel="stylesheet" href="<c:url value="/resources/css/jquery.fileupload.css"/>">
		<link rel="stylesheet" href="<c:url value="/resources/css/jquery.fileupload-ui.css"/>">

		
	    <script type='text/javascript' src='<c:url value="/resources/js/jquery-1.10.1.min.js"/>'></script>
        <!-- Bootstrap 
	    <script src="<c:url value='/resources/js/bootstrap.min.js'/>"></script>-->
	    <script src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>

	    <script type='text/javascript' src='<c:url value="/resources/js/custom_home.js"/>'></script>
	    		
         <!--  UPLOADER BLUEIMP -->
	    <script src="<c:url value="/resources/js/fileuploader/vendor/jquery.ui.widget.js"/>"></script>
		<script src="<c:url value="/resources/js/fileuploader/jquery.iframe-transport.js"/>"></script>
		<script src="<c:url value="/resources/js/fileuploader/jquery.fileupload.js"/>"></script>
        
    
  		<script type='text/javascript' >
  		$(document).ready(function(){
  			$('#fileupload').fileupload({
  		        dataType: 'json',
  		        add: function (e, data) {
  		            data.context = $('<button/>').text('Upload')
  		                .appendTo(document.body)
  		                .click(function () {
  		                    data.context = $('<p/>').text('Uploading...').replaceAll($(this));
  		                    data.submit();
  		                });
  		        },
  		        done: function (e, data) {
  		            data.context.text('Upload finished.');
  		        }
  		    });
			});
		</script>
    </body>
</html>