<!DOCTYPE html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<% 
	UserService userService= UserServiceFactory.getUserService();
%>

<html>
<%@ include file="../header.jsp"%>
<body>
<div data-role="header">
	<h1>Log Out</h1>
</div>

<div data-role="content" data-theme="e">
	<a>Are you sure you want to log out?</a>
	<fieldset class="ui-grid-a">
				<div class="ui-block-a">
					<p>
						<a data-role="button" href='<%= userService.createLogoutURL("/login.jsp")%>'>Yes</a>
					</p>
				</div>
				
				<div class="ui-block-b">
					<p>
					<a data-role="button" href="">No</a>
					</p>
				</div>

			</fieldset>
</div>


<%@ include file="../footer.jsp" %>

</body>
</html>