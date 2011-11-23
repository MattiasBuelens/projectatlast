<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/includes/header.jsp"%>
<%@ page import="projectatlast.student.Student"%>
<%@ page import="projectatlast.student.AuthController"%>
<%
	Student student = AuthController.getCurrentStudent();
%>

<div id="home" data-role="page" data-url="/home">

	<div data-role="header">
		<h1>Home</h1>
	</div>
	<!-- /header -->

	<div data-role="content">
		<div>
			<%
				if (student.isInActivity()) {
			%>
			<fieldset class="ui-grid-a">
				<div class="ui-block-a">
					<a data-role="button" href="/tracking/stopActivity">Stop
						Activity</a>
				</div>

				<div class="ui-block-b">
					<a data-role="button" href="/tracking/cancelActivity">Cancel
						Activity</a>
				</div>
			</fieldset>
			<%
				} else {
			%>
			<a data-role="button" href="/tracking/startTracking.jsp"
				data-rel="dialog" data-transition="pop">Start Activity</a>
			<%
				}
			%>

			<a data-role="button" href="/tracking/activities.jsp">List
				Activities</a>
		</div>

		<% /*
		<fieldset class="ui-grid-a">
			<div class="ui-block-a">[Graphs]</div>
			<div class="ui-block-b">[Milestones]</div>
		</fieldset>
		*/ %>

	</div>
	<!-- /content -->

	<div data-role="footer" data-theme="c">
		<%@ include file="/includes/copyright.jsp"%>
	</div>

	<div data-role="footer" data-position="fixed">
		<div data-role="navbar">
			<ul>
				<li><a href="/student/configure" data-role="button"
					class="ui-icon-custom" data-icon="projectatlast-configure">Configure</a></li>
				<li><a href="/student/logout" data-role="button"
					class="ui-icon-custom" data-icon="projectatlast-logout"
					data-rel="dialog" data-transition="pop">Log out</a></li>
			</ul>
		</div>
	</div>
	<!-- /navbar -->
</div>
<!-- /page -->

</body>
</html>