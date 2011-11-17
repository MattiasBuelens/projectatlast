<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/includes/header.jsp"%>
<%@ page import="com.google.appengine.api.users.UserServiceFactory"%>
<%@ page import="projectatlast.student.AuthController"%>
<%
	String loginUrl = AuthController.createLoginURL();
%>

<div id="login" data-role="page" data-url="/student/login" data-theme="a">
	<div data-role="header">
		<h1>Log in</h1>
	</div>

	<div data-role="content">
		<div class="logo">
			<img src="/images/logo.png" alt="Project AtLast" />
			<a href="<%=loginUrl%>" data-role="button" data-inline="true" data-theme="b">Log in</a>
		</div>
	</div>
	
	<div data-role="footer" data-position="fixed">
		<%@ include file="/includes/copyright.jsp"%>
	</div>
</div>

</body>
</html>