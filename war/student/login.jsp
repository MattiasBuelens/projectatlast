<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/includes/header.jsp"%>
<%@ page import="com.google.appengine.api.users.UserServiceFactory"%>
<%@ page import="projectatlast.student.AuthController"%>
<%
	String loginUrl = AuthController.createLoginURL();
%>

<div id="login" data-role="page" data-url="/student/login">
	<div data-role="header">
		<h1>Log in</h1>
	</div>

	<div data-role="content">
		<img src="/images/logo.jpg" />
		<a href="<%=loginUrl%>" data-role="button">Log in</a>
	</div>

	<%@ include file="/includes/footer.jsp"%>
</div>

</body>
</html>