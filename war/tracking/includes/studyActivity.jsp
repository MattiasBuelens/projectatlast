<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="projectatlast.tracking.*"%>
<%@ page import="projectatlast.course.Course"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.DateFormat"%>
<%
	StudyActivity activity = (StudyActivity) request
			.getAttribute("activity");
	DateFormat dateFormat = (DateFormat) request
			.getAttribute("dateFormat");

	String title = activity.getTypeName();
	Date startDate = activity.getStart();
	Date stopDate = activity.getEnd();
	String social = activity.getSocial();
	String location = activity.getLocation();
	List<String> tools = activity.getTools();
	Mood mood = activity.getMood();
	Course course = activity.getCourse();

	if (course != null) {
		title = course.getName() + ": " + title;
	}
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
			if (social != null && !social.isEmpty()) {
		%>
		<dt>Social:</dt>
		<dd><%=social%></dd>
		<%
			}
			if (tools != null && !tools.isEmpty()) {
		%>
		<dt>Tools:</dt>
		<dd>
			<ul>
				<%
					for (String tool : tools) {
				%>
				<li><%=tool%></li>
				<%
					}
				%>
			</ul>
		</dd>
		<%
			}
			if (location != null && !location.isEmpty()) {
		%>
		<dt>Location:</dt>
		<dd><%=location%></dd>
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