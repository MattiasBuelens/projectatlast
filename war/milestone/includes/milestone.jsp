<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="projectatlast.milestone.*"%>
<%@ page import="java.lang.Math"%>
<%
	Milestone milestone = (Milestone) request.getAttribute("milestone");
	long currentValue = MilestoneController.calculateProgress(milestone);
	long startValue = milestone.getStartValue();
	long goalValue = milestone.getGoal();

	long maxValue = Math.max(currentValue,
			Math.max(startValue, goalValue));
	maxValue = 9 * maxValue / 8;

	long currentPercentage = 100 * currentValue / maxValue;
	long startPercentage = 100 * startValue / maxValue;
	long goalPercentage = 100 * goalValue / maxValue;
%>
<div class="ui-body ui-body-c">
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
				if (Math.abs(goalPercentage - currentPercentage) > 10
						&& Math.abs(startPercentage - currentPercentage) > 10) {
			%><span><%=currentValue%></span>
			<%
				}
			%>
		</div>

		<%
			if (milestone.getOperator() == ComparativeOperator.GREATER_THAN) {
		%>
		<div class="milestone-bar milestone-bar-right ui-btn-down-e"
			style="width: <%=100 - goalPercentage%>%">
			<strong>Goal</strong> <span><%=goalValue%></span>
		</div>
		<%
			}
		%>

		<%
			if (milestone.getOperator() == ComparativeOperator.LESS_THAN) {
		%>
		<div class="milestone-bar milestone-bar-left ui-btn-down-e"
			style="width: <%=goalPercentage%>%">
			<strong class="right"> Goal</strong> <span class="right"><%=goalValue%></span>
		</div>
		<%
			}
		%>
	</div>
</div>