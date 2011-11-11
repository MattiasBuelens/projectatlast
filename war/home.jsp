<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/includes/header.jsp"%>
<%@ page import="projectatlast.student.Student"%>
<%@ page import="projectatlast.student.AuthController"%>
<%
	Student student = AuthController.getCurrentStudent();
%>

<div id="home" data-role="page" data-url="/">

	<div data-role="header">
		<a href="/student/logout" data-role="button" data-icon="delete"
			data-rel="dialog" data-transition="pop">Log out</a>
		<h1>Home</h1>
	</div>
	<!-- /header -->

	<div data-role="content">
		<div>
			<%
				if (student.isInActivity()) {
			%>
			<a data-role="button" href="/tracking/stopActivity">Stop Activity</a>
			<%
				} else {
			%>
			<a data-role="button" href="/tracking/startTracking.jsp"
				data-rel="dialog" data-transition="pop">Start Activity</a>
			<%
				}
			%>

			<a data-role="button" href="/tracking/activities.jsp">List Activities</a>
		</div>

		<fieldset class="ui-grid-a">
			<div class="ui-block-a">[Graphs]</div>
			<div class="ui-block-b">[Milestones]</div>
		</fieldset>

	</div>
	<!-- /content -->

	<%@ include file="/includes/footer.jsp"%>
</div>
<!-- /page -->

</body>
</html>