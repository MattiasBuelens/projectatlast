<!DOCTYPE html> 

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>


<html>
<%@ include file="header.jsp" %>

<% UserService userService = UserServiceFactory.getUserService();


%>


<body> 
<div data-role="page">
	<div data-role="header">
		<h1>Log In</h1>
	</div>

	<div data-role="content">
		<img src="images/logo.jpg"></img>
		<p><a href="<%= userService.createLoginURL("/postlogin")%>" data-role="button">log In</a></p>
	</div>
	
<%@ include file="footer.jsp" %>
</div>

</body>
</html>
