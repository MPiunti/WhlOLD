<%@ page import="org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link rel='stylesheet' type='text/css' media='screen' href='<c:url value="/resources/css/style.css"/>'/>
		<!-- Bootstrap -->
		<link href="<c:url value='/resources/css/bootstrap.min.css'/>" rel="stylesheet" media="screen">

		<!-- Bootstrap -->
	    <script src="<c:url value='/resources/js/bootstrap.min.js'/>"></script>
	    <script type='text/javascript' src='<c:url value="/resources/js/jquery-1.10.1.min.js"/>'></script>
		<script type='text/javascript' src='<c:url value="/resources/js/custom_home.js"/>'></script>
		<title>Login</title>
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
	              <li><a href="<c:url value="/"/>">Log In</a></li>
	            </ul>
	          </div><!--/.nav-collapse -->
	        </div>
	      </div>
	    </div>
	    
	    <br/>
	    <br/>
	    <div class="form-actions">
			<h2>Register as a New User</h2>
			<div class="error">${error}</div>
			
			<c:url value="/home/" var="homeUrl"/>
			<c:url value="/auth/register" var="registerUrl"/>
			
			<form action="${registerUrl}" method="post" >
		    	<fieldset>
		    	
		    		<label for="j_displayname">Name:</label>
			        <input id="j_displayname" name="j_displayname" type="text" value="${j_displayname}" placeholder="Display Name"/>
		    	
			        <label for="j_username">Login:</label>
			        <input id="j_username" name="j_username" type="text" value="${j_username}" placeholder="User Name"/>	        
			     
			        <label for="j_password">Password:</label>
			        <input id="j_password" name="j_password" type="password" placeholder="Password"/>
			      
			      	<input  type="submit" value="Register" class="btn btn-primary" />
				</fieldset>
		    </form>	
		</div>	

	</body>
</html>
