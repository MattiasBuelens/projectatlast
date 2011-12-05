<%@ include file="/includes/header.jsp"%>

<%@ page import="projectatlast.milestone.*" %>
<%@ page import="projectatlast.data.Registry" %>
<%@ page import="projectatlast.student.*" %>
<%@ page import="java.util.List" %>
<link rel="stylesheet" href="/css/milestone.css" type="text/css">

<body>

	<div data-role="page">
		<div data-role="header">
			<h1>Milestones</h1>
		</div>
	
		<div data-role="content">
			<!-- Should retrieve a list of all milestones and group them -->
			<%
			Student cu = AuthController.getCurrentStudent();
			List<Milestone> milestones = Registry.milestoneFinder().getMilestones(cu);
			
			
			for(Milestone milestone:milestones){
			%>
			
			<%@ include file="/milestone/includes/milestone.jsp" %>
		
			<p>Study analysis for 12 hours this week.</p>
			<div class="milestone ui-btn-down-c ui-btn-corner-all">
				<div class="milestone-marker ui-btn-down-e" style="left: 25%">
					<strong>Start</strong> <span>2 uur</span>
					<div class="milestone-handle ui-btn-up-c ui-btn-corner-all"></div>
				</div>
				<div class="milestone-marker ui-btn-down-e" style="left: 40%">
					<strong>Current</strong> <span>3 uur</span>
					<div class="milestone-handle ui-btn-down-e ui-btn-corner-all">You</div>
				</div>
				<div class="milestone-bar milestone-bar-right ui-btn-down-e"
					style="width: 30%">
					<strong>Goal</strong> <span>5 uur</span>
				</div>
			</div>
			<div id="milestone1">&nbsp;</div>
			Study algebra till you die
			<div id="milestone2">&nbsp;</div>
		</div>
		<div data-role="footer">
			<%@ include file="/includes/copyright.jsp"%>
		</div>
	</div>
</body>
</html>