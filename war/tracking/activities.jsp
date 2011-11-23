<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/includes/header.jsp"%>
<%@ page import="projectatlast.data.Registry"%>
<%@ page import="projectatlast.student.*"%>
<%@ page import="projectatlast.tracking.*"%>
<%@ page import="projectatlast.course.Course"%>
<%@ page import="java.util.List"%>
<%@ page import="java.text.DateFormat"%>
<%
	Student student = AuthController.getCurrentStudent();
	List<Activity> activities = ActivityController
			.getAllFromStudent(student);
	DateFormat dateFormat = DateFormat.getDateTimeInstance(
			DateFormat.LONG, DateFormat.MEDIUM);
%>

<div data-role="page">
	<div data-role="header">
		<a href="/home" data-role="button" data-rel="back" data-icon="home"
			data-iconpos="notext">Home</a>
		<h1>List Activities</h1>
	</div>

	<div data-role="content">
		<ul data-role="listview">
			<%
				for (Activity activity : activities) {
			%>
			<li>
				<%
					if (activity instanceof StudyActivity) {
							Course course = ((StudyActivity) activity).getCourse();
							if (course != null) {
				%>
				<h3><%=((StudyActivity) activity).getCourse().getName()%>: <%=activity.getType()%></h3>
				<% } else { %>
				<h3><%=activity.getType()%></h3>
				<% } } else { %>
				<h3><%=activity.getType()%></h3>
				<% } %>
				<dl>
					<%
						if (activity.getStart() != null) {
					%>
					<dt>From</dt>
					<dd><%=dateFormat.format(activity.getStart())%></dd>
					<%
						}
					%>
					<%
						if (activity.getEnd() != null) {
					%>
					<dt>To</dt>
					<dd><%=dateFormat.format(activity.getEnd())%></dd>
					<%
						}
					%>
				</dl>
			</li>
			<%
				}
			%>
		</ul>
	</div>

	<div data-role="footer" data-theme="c">
		<%@ include file="/includes/copyright.jsp"%>
	</div>
</div>
</body>
</html>