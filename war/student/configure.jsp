<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/includes/header.jsp"%>
<%@ page import="projectatlast.student.Student"%>
<%@ page import="projectatlast.student.AuthController"%>
<%
	Student student = AuthController.getCurrentStudent();
	boolean isConfigured = student.isConfigured();
%>

<div id="configure" data-role="page" data-url="/student/configure">

	<div data-role="header">
		<%
			if (isConfigured) {
		%>
		<a href="/" data-role="button" data-rel="back" data-icon="home"
			data-iconpos="notext">Home</a>
		<%
			} else {
		%>
		<a href="/student/logout" data-role="button" data-icon="delete"
			data-rel="dialog" data-transition="slidedown">Log out</a>
		<%
			}
		%>
		<h1>Configure</h1>
	</div>
	<!-- /header -->

	<div data-role="content">
		<p>[Form]</p>
		<a href="/" data-role="button">Take me home (Country roads)</a>
	</div>
	<!-- /content -->

	<%@ include file="/includes/footer.jsp"%>
</div>
<!-- /page -->

</body>
</html>