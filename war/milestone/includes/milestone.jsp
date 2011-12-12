<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="projectatlast.milestone.*"%>
<%@ page import="java.lang.Math"%>
<%@ page import="java.text.*"%>
<%@ page import="java.util.Locale"%>
<%
	Milestone milestone = (Milestone) request.getAttribute("milestone");
	String unit = milestone.getParseField().unit();

	// Get values
	double current = MilestoneController.calculateProgress(milestone);
	double start = milestone.getStartValue();
	double goal = milestone.getGoal();

	// Maximum value
	double max = Math.max(current, Math.max(start, goal));
	max = 9 * max / 8;

	// Format labels
	Format decimal = new DecimalFormat("###.###");
	String currentValue = decimal.format(current) + " " + unit;
	String startValue = decimal.format(start) + " " + unit;
	String goalValue = decimal.format(goal) + " " + unit;

	// Format percentages
	Format percentage = new DecimalFormat("#.###%");
	String currentPercentage = percentage.format(100 * current / max);
	String startPercentage = percentage.format(100 * start / max);

	// Align goal to right when using greater than operator
	boolean isGoalRightAligned = (milestone.getOperator() == ComparativeOperator.GREATER_THAN);

	// When goal is right aligned, calculate percentage from right side
	String goalPercentage;
	if (isGoalRightAligned) {
		goalPercentage = percentage.format(1 - goal / max);
	} else {
		goalPercentage = percentage.format(goal / max);
	}

	// Only show current if there is enough space
	boolean showCurrent = Math.abs(goal - current) > max / 10
			&& Math.abs(start - current) > max / 10;
%>
<div class="milestone-item ui-body ui-body-c">
	<p><%=milestone.getSentence()%></p>
	<div class="milestone ui-btn-down-c ui-btn-corner-all">
		<div class="milestone-marker ui-btn-down-e"
			style="left: <%=startPercentage%>%">
			<strong>Start</strong> <span><%=startValue%></span>
			<div class="milestone-handle ui-btn-up-c ui-btn-corner-all"></div>
		</div>

		<div class="milestone-marker ui-btn-down-e"
			style="left: <%=currentPercentage%>%">
			<div class="milestone-handle ui-bar-b ui-btn-corner-all">You</div>
			<%
				if (showCurrent) {
			%><span><%=currentValue%></span>
			<%
				}
			%>
		</div>
		<div class="milestone-bar ui-btn-down-e
			<%=isGoalRightAligned ? "milestone-bar-right" : "milestone-bar-left"%>"
			style="width: <%=goalPercentage%>">
			<div class="milestone-bar milestone-bar-left ui-btn-down-e"
				style="width: <%=goalPercentage%>">
				<strong>Goal</strong> <span><%=goalValue%></span>
			</div>
		</div>
	</div>
</div>