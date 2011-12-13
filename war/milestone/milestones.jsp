<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="projectatlast.data.Registry"%>
<%@ page import="projectatlast.student.*"%>
<%@ page import="projectatlast.milestone.*"%>
<%@ page import="java.util.*"%>
<%@ include file="/includes/header.jsp"%>

<%
	Student student = AuthController.getCurrentStudent();
	Map<String, List<Milestone>> milestones = MilestoneController
			.getMilestones(student);

	// Divide milestones in running, completed and failed.
	List<Milestone> running = milestones.get("running");
	List<Milestone> success = milestones.get("completed");
	List<Milestone> failed = milestones.get("failed");
%>
<div data-role="page">

	<div data-role="header">
		<a href="/home" data-role="button" data-direction="reverse"
			data-icon="home" data-iconpos="notext">Home</a>
		<h1>Milestones</h1>
		<a href="/milestone/add" data-role="button" data-icon="plus"
			data-theme="b">Add</a>
	</div>

	<div data-role="content">
		<div class="milestones" data-role="collapsible-set" data-theme="a"
			data-content-theme="c">

			<div data-role="collapsible" data-theme="b" data-collapsed="false"
				class="<%=(running.isEmpty() ? "milestones-empty"
					: "milestones-list")%>">
				<h1>Running milestones</h1>
				<%
					for (Milestone milestone : running) {
						request.setAttribute("milestone", milestone);
				%>
				<jsp:include page="/milestone/includes/milestone.jsp" />
				<%
					}
					if (running.isEmpty()) {
				%>
				<p>No running milestones.</p>
				<%
					}
				%>
			</div>

			<div data-role="collapsible" data-theme="a"
				class="<%=(success.isEmpty() ? "milestones-empty"
					: "milestones-list")%>">
				<h1>Completed milestones</h1>
				<%
					for (Milestone milestone : success) {
						request.setAttribute("milestone", milestone);
				%>
				<jsp:include page="/milestone/includes/milestone.jsp" />
				<%
					}
					if (success.isEmpty()) {
				%>
				<p>No completed milestones.</p>
				<%
					}
				%>
			</div>

			<div data-role="collapsible" data-theme="c"
				class="<%=(failed.isEmpty() ? "milestones-empty"
					: "milestones-list")%>">
				<h1>Failed milestones</h1>
				<%
					for (Milestone milestone : failed) {
						request.setAttribute("milestone", milestone);
				%>
				<jsp:include page="/milestone/includes/milestone.jsp" />
				<%
					}
					if (failed.isEmpty()) {
				%>
				<p>No failed milestones.</p>
				<%
					}
				%>
			</div>
		</div>

		<div data-role="footer" data-theme="c">
			<%@ include file="/includes/copyright.jsp"%>
		</div>
	</div>
	<!-- content -->
</div>
</body>
</html>