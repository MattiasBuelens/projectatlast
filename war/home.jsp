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

	<div class="ui-bar-large ui-bar-d">
		<%
			if (student.isInActivity()) {
		%>
		<div class="ui-grid-a">
			<div class="ui-block-a">
				<a data-role="button" data-theme="b" href="/tracking/stopActivity">Stop Activity</a>
			</div>

			<div class="ui-block-b">
				<a data-role="button" data-theme="a" href="/tracking/cancelActivity">Cancel Activity</a>
			</div>
		</div>
		<%
			} else {
		%>
		<div>
			<a data-role="button" href="/tracking/startTracking.jsp"
				data-rel="dialog" data-transition="pop" data-theme="b">Start Activity</a>
		</div>
		<%
			}
		%>
	</div>

	<div data-role="content">
		<a data-role="button" href="/tracking/activities.jsp">
			<span class="ui-li-heading">Activities</span>
			<span class="ui-li-desc">View and edit activities</span>
		</a>
		<a data-role="button" href="/milestone/add">
			<span class="ui-li-heading">Milestones</span>
			<span class="ui-li-desc">Set up milestones and achieve your goals</span>
		</a>
		<a data-role="button" href="/graph/graphs.jsp" data-ajax="false">
			<span class="ui-li-heading">Statistics</span>
			<span class="ui-li-desc">Analyze your efforts visually</span>
		</a>
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