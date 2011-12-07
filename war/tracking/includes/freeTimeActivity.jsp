<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="projectatlast.tracking.*"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.DateFormat"%>
<%
	FreeTimeActivity activity = (FreeTimeActivity) request
			.getAttribute("activity");
	DateFormat dateFormat = (DateFormat) request
			.getAttribute("dateFormat");

	String title = activity.getType();
	Date startDate = activity.getStart();
	Date stopDate = activity.getEnd();
	Mood mood = activity.getMood();
%>
<li>
	<h3><%=title%></h3>
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