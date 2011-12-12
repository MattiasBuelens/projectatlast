<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="projectatlast.tracking.*"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.DateFormat"%>
<%
	FreeTimeActivity activity = (FreeTimeActivity) request
			.getAttribute("activity");
	Activity currentActivity = (Activity) request
			.getAttribute("currentActivity");
	DateFormat dateFormat = (DateFormat) request
			.getAttribute("dateFormat");

	Date startDate = activity.getStart();
	Date stopDate = activity.getEnd();
	Mood mood = activity.getMood();
%>
<li>
	<h3><%=activity.getTitle()%>
		<%
			if (activity.equals(currentActivity)) {
		%>
		<span class="activity-status activity-running ui-body-c ui-corner-all">Running&hellip;</span>
		<%
			} else {
		%>
		<a class="activity-status activity-delete"
			href="/tracking/deleteActivity?id=<%=activity.getId()%>"
			data-ajax="false" data-role="button" data-icon="delete"
			data-inline="true" data-iconpos="notext">Delete</a>
		<%
			}
		%>
	</h3>
	<dl>
		<%
			if (startDate != null) {
		%>
		<dt>From</dt>
		<dd><%=dateFormat.format(startDate)%></dd>
		<%
			}
			if (stopDate != null) {
		%>
		<dt>To</dt>
		<dd><%=dateFormat.format(stopDate)%></dd>
		<%
			}
			if (mood != null) {
		%>
		<dt>Mood:</dt>
		<dd>
			Interest:
			<%=mood.getInterest() + "%"%>
		</dd>
		<dd>
			Comprehension:
			<%=mood.getComprehension() + "%"%>
		</dd>
		<%
			}
		%>
	</dl>
</li>