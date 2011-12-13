<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="projectatlast.milestone.*"%>
<%@ page import="java.lang.Math"%>
<%@ page import="java.text.*"%>
<%@ page import="java.util.Locale"%>
<%
	Milestone milestone = (Milestone) request.getAttribute("milestone");
	String unit = milestone.getParseField().unit();

	// Get values
	double current = milestone.getProgress();
	double start = milestone.getStartValue();
	double goal = milestone.getGoal();

	// Maximum value
	double max = Math.max(current, Math.max(start, goal));
	max = 9 * max / 8;

	// Format labels
	DecimalFormat format = (DecimalFormat)NumberFormat.getInstance(Locale.ENGLISH);
	format.applyPattern("#.###");
	String currentValue = format.format(current) + " " + unit;
	String startValue = format.format(start) + " " + unit;
	String goalValue = format.format(goal) + " " + unit;

	// Format percentages
	format.applyPattern("#.##%");
	String currentPercentage = format.format(current / max);
	String startPercentage = format.format(start / max);

	// Align goal to right when using greater than operator
	boolean isGoalRightAligned = (milestone.getOperator() == ComparativeOperator.GREATER_THAN);

	// When goal is right aligned, calculate percentage from right side
	String goalPercentage;
	if (isGoalRightAligned) {
		goalPercentage = format.format(1 - goal / max);
	} else {
		goalPercentage = format.format(goal / max);
	}

	// Only show current if there is enough space
	boolean showCurrent = Math.abs(goal - current) > max / 10
			&& Math.abs(start - current) > max / 10;
%>
<div class="milestone-item ui-body ui-body-c">
	<p><%=milestone.getSentence()%></p>
	<div class="milestone ui-btn-down-c ui-btn-corner-all">
		<div class="milestone-marker ui-btn-down-e"
			style="left: <%=startPercentage%>">
			<strong>Start</strong> <span><%=startValue%></span>
			<div class="milestone-handle ui-btn-up-c ui-btn-corner-all"></div>
		</div>

		<div class="milestone-marker ui-btn-down-e"
			style="left: <%=currentPercentage%>">
			<div class="milestone-handle ui-bar-b ui-btn-corner-all">You</div>
			<%
				if (showCurrent) {
			%><span><%=currentValue%></span>
			<%
				}
			%>
		</div>
		<div class="milestone-bar ui-btn-down-e <%=isGoalRightAligned ? "milestone-bar-right" : "milestone-bar-left"%>"
			style="width: <%=goalPercentage%>">
			<strong>Goal</strong> <span><%=goalValue%></span>
		</div>
	</div>
</div>