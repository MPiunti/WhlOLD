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
		<!-- Bootstrap -->
		<link href="<c:url value='/resources/css/bootstrap.min.css'/>" rel="stylesheet" media="screen">

		<!-- Bootstrap -->
		<script src="<c:url value='/resources/js/jquery-1.6.4.min.js'/>"></script>
	    <script src="<c:url value='/resources/js/bootstrap.min.js'/>"></script>

		<title>Login</title>
	</head>
	
	<body>
	<c:url value="/home/u" var="homeUrl"/>

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
	            </ul>
	          </div><!--/.nav-collapse -->
	        </div>
	      </div>
	    </div>

		<c:url value="/j_spring_security_check" var="loginUrl"/>
		<c:url value="/auth/register" var="registerUrl"/>
		
  

		<div class="container">
			<br/>
			<br/>
	
			<form action="${loginUrl}" method="post" class="form-signin">
		    <h2 class="form-signin-heading">Login</h2>
			<div class="error"><font color="red">${error}</font></div>
		    
		      <p>
		        <label for="j_username">Login:</label>
		        <input id="j_username" name="j_username" type="text" class="input-block-level"
		            value="${not empty param.login_error ? sessionScope['SPRING_SECURITY_LAST_USERNAME'] : '' }" placeholder="password"/>
		      </p>
		      <p>
		        <label for="j_password">Password:</label>
		        <input id="j_password" name="j_password" type="password" class="input-block-level" 
		        placeholder="Password"/>
		      </p>
		      <p>
		        <label>Remember me:</label>
		        <input type="checkbox" name="_spring_security_remember_me" value="Remember me" />
		      </p>
		      <input  type="submit" class="btn btn-large btn-primary" value="Login"/>
		    </form>
	    </div>

</body>
</html>
