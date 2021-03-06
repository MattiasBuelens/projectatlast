<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/includes/header.jsp"%>
<%@ page import="com.google.appengine.api.users.UserServiceFactory"%>
<%@ page import="projectatlast.student.AuthController"%>
<%
	String logoutUrl = AuthController.createLogoutURL();
%>

<div id="logout" data-role="page" data-url="/student/logout">
	<div data-role="header">
		<h1>Log out</h1>
	</div>

	<div data-role="content" data-theme="c">
		<p>Are you sure you want to log out?</p>
		<a data-role="button" rel="external" data-theme="b" href="<%=logoutUrl%>">Log out</a>
		<a data-role="button" rel="back" href="/home">Cancel</a>
	</div>
</div>

</body>
</html>