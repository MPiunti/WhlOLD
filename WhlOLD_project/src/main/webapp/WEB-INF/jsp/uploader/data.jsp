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
		<link href="<c:url value='/resources/css/bootstrap.min.css'/>" rel="stylesheet" media="screen">

		<link href="<c:url value='/resources/css/bootstrap-fileupload.min.css'/>" rel="stylesheet" media="screen">
		
		<!-- Latest compiled and minified CSS -->
		<link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/jasny-bootstrap/3.1.3/css/jasny-bootstrap.min.css">
	    
		<!-- Bootstrap --> 
	    <script src="<c:url value='/resources/js/bootstrap.min.js'/>"></script>
		<script type='text/javascript' src='<c:url value="/resources/js/jquery-1.10.1.min.js"/>'></script>
		<!-- Latest compiled and minified JavaScript -->
		<script src="//cdnjs.cloudflare.com/ajax/libs/jasny-bootstrap/3.1.3/js/jasny-bootstrap.min.js"></script>		
		<script src="<c:url value='/resources/js/bootstrap-fileupload.min.js'/>"></script>
	

        <title>Open Data - Upload</title>
    </head>
    <body>    	
    	<c:url value="/home/u" var="homeUrl"/>
    	<c:url value="/uploader/data" var="uploadUrl"/>
    	<c:url value="/auth/logout" var="logoutUrl"/>   
    	<c:url value="http://localhost:3000/graph.html" var="linkuriousUrl"/> 
    
    	<div class="navbar navbar-inverse navbar-fixed-top">
	      <div class="navbar-inner">
	          <button type="button" class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
	            <span class="icon-bar"></span>
	            <span class="icon-bar"></span>
	            <span class="icon-bar"></span>
	            <span class="icon-bar"></span>
	          </button>
	          <a class="brand" href="${homeUrl}">Open Linked Data</a>
	          <div class="nav-collapse collapse">
	            <ul class="nav">
	              <li><a href="${homeUrl}">Home</a></li>
	              <li class="active"><a href="${uploadUrl}">Upload Data</a></li>
	              <li><a href="${linkuriousUrl}">Graph</a></li>
	              <li><a href="${logoutUrl}">Logout</a></li>
	            </ul>
	          </div><!--/.nav-collapse -->
	      </div>
	    </div>
    	
    	<br/>
		<br/>
    	
        <div>
           <h3>Data Ingestion</h3>
        </div>
        
        
        <div class="container">
			<br/>
			<br/>
			<div class="form-actions">	
    
       		<form:form modelAttribute="uploadItem" method="post" enctype="multipart/form-data" >
            
                <legend>Upload Fields</legend>
 
       
                   <form:label for="name" path="name">Name</form:label>
                   <form:input path="name" placeholder="Docuement Name"/>

                   <form:label for="status" path="status">State</form:label>
                   <form:select path="status">
						<option value="">Select state</option>
						<option value="0" selected>Visible</option>
						<option value="1">Private</option>
				   </form:select>
        
                  <%--   <form:label for="fileData" path="fileData">File</form:label>
                   <form:input path="fileData" type="file"/> --%>
                   
                   <div class="fileupload fileupload-new" data-provides="fileupload">
				     <div class="input-append">
				        <div class="uneditable-input span3">
				        	<i class="icon-file fileupload-exists"></i> 
				        	<span class="fileupload-preview"></span>
				        </div>
				        <span class="btn btn-file"><span class="fileupload-new">Select file</span>
				        <span class="fileupload-exists">Change</span>
				         <form:input path="fileData" type="file"/>
				        </span>
				        <a href="#" class="btn fileupload-exists" data-dismiss="fileupload">Remove</a>
					 </div>
				   </div>
				   
				   <input type="submit" value="Load Document" class="btn btn-primary btn-large" />
        		</form:form>
        	</div>
        </div>
        
 
    </body>
</html>
