<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="projectatlast.data.Registry"%>
<%@ page import="projectatlast.student.*"%>
<%@ page import="projectatlast.milestone.*"%>
<%@ page import="com.googlecode.objectify.*"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>

<%@ include file="/includes/header.jsp"%>

<div data-role="page">

	<div data-role="header">
		<a href="/home" data-role="button" data-direction="reverse"
			data-icon="home" data-iconpos="notext">Home</a>
		<h1>Milestones</h1>
		<a href="/milestone/add" data-role="button" data-icon="plus"
			data-theme="b">Add</a>
	</div>

	<div data-role="content">

		<%
			Student student = AuthController.getCurrentStudent();
			// TODO No registry!
			Registry.milestoneFinder().updateCompletion(student);

			// Divide milestones in running, completed and failed.
			List<Milestone> running = Registry.milestoneFinder()
					.getRunningMilestones(student);
			List<Milestone> success = Registry.milestoneFinder()
					.getCompletedMilestones(student);
			List<Milestone> failed = Registry.milestoneFinder()
					.getFailedMilestones(student);
		%>

		<div data-role="collapsible" data-theme="a">
			<h1>Running milestones</h1>
			<%
				for (Milestone milestone : running) {
					request.setAttribute("milestone", milestone);
			%>
			<jsp:include page="/milestone/includes/milestone.jsp" />
			<%
				}
			%>
		</div>

		<div data-role="collapsible" data-theme="a">
			<h1>Completed milestones</h1>
			<%
				for (Milestone milestone : success) {
					request.setAttribute("milestone", milestone);
			%><jsp:include page="/milestone/includes/milestone.jsp" />
			<%
				}
			%>
		</div>

		<div data-role="collapsible" data-theme="a">
			<h1>Failed milestones</h1>
			<%
				for (Milestone milestone : failed) {
					request.setAttribute("milestone", milestone);
			%><jsp:include page="/milestone/includes/milestone.jsp" />
			<%
				}
			%>
		</div>

		<div data-role="footer" data-theme="c">
			<%@ include file="/includes/copyright.jsp"%>
		</div>
	</div>
	<!-- content -->
</div>
</body>
</html>