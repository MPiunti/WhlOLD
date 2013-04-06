<%@page contentType="text/html;charset=UTF-8" %>
<%@page pageEncoding="UTF-8" %>
<%@ page session="false" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
 
<html>
    <head>
        <META http-equiv="Content-Type" content="text/html;charset=UTF-8">
        <link rel='stylesheet' type='text/css' media='screen' href='<c:url value="/resources/css/style.css"/>'/>
		<!-- Bootstrap -->
		<link href="<c:url value='/resources/css/bootstrap.min.css'/>" rel="stylesheet" media="screen">
        <!-- Bootstrap -->
	    <script src="<c:url value='/resources/js/bootstrap.min.js'/>"></script>
	    <script type='text/javascript' src='<c:url value="/resources/js/jquery-1.6.4.min.js"/>'></script>
		<script type='text/javascript' src='<c:url value="/resources/js/custom_home.js"/>'></script>
        <title>Open Data - Upload</title>
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
	          <a class="brand" href="#">Open Linked Data</a>
	          <div class="nav-collapse collapse">
	            <ul class="nav">
	              <li><a href="<c:url value="/home/u"/>">Home</a></li>
	              <li class="active"><a href="${uploadUrl}">Upload Data</a></li>
	              <li><a href="${logoutUrl}">Logout</a></li>
	            </ul>
	          </div><!--/.nav-collapse -->
	        </div>
	      </div>
	    </div>
    	
    	<br/>
		<br/>
    	
        <div>
           <h2>Upload Document:</h2>
        </div>
    
        <form:form modelAttribute="uploadItem" method="post" enctype="multipart/form-data">
            <fieldset>
                <legend>Upload Fields</legend>
 
                <p>
                    <form:label for="name" path="name">Name</form:label><br/>
                    <form:input path="name"/>
                </p>
                <p>
                   <form:label for="status" path="status">Stato</form:label><br/>
                   <form:select path="status">
						<option value="">Seleziona Stato</option>
						<option value="0" selected>Visibile</option>
						<option value="1">Privato</option>
				   </form:select>
                </p> 
                <p>
                    <form:label for="fileData" path="fileData">File</form:label><br/>
                    <form:input path="fileData" type="file"/>
                </p>
 
                <p>
                    <input type="submit" value="Load Document" class="btn btn-primary btn-large"/>
                </p>
 
            </fieldset>
        </form:form>
        
        
    </body>
</html>