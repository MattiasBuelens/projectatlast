<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<% 
	UserService userService= UserServiceFactory.getUserService();
%>

<%@ include file="/parts/header.jsp"%>
<div data-role="page">
	<div data-role="header">
		<h1>Log Out</h1>
	</div>

	<div data-role="content" data-theme="e">
		<a>Are you sure you want to log out?</a>
		<fieldset class="ui-grid-a">
			<div class="ui-block-a">
				<a data-role="button" href='<%= userService.createLogoutURL("/authentication/login.jsp")%>'>Yes</a>
			</div>
			<div class="ui-block-b">
				<a data-role="button" href="">No</a>
			</div>
		</fieldset>
	</div>

	<%@ include file="/parts/footer.jsp" %>
</div>

</body>
</html>