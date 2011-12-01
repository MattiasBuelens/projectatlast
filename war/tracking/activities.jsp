<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
		<a href="/home" data-role="button" data-direction="reverse" data-icon="home"
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
					String title = activity.getType();
					String social = null;
					List<String> tools = null;

					if (activity instanceof StudyActivity) {
						StudyActivity studyActivity = (StudyActivity)activity;
						Course course = studyActivity.getCourse();
						social = studyActivity.getSocial();
						tools = studyActivity.getTools();
					
						if(course != null) {
							title = course.getName() + ": " + studyActivity.getType();
						}
					}
				%>
				<h3><%=title%></h3>
				<dl>
					<%
						if (activity.getStart() != null) {
					%>
					<dt>From</dt>
					<dd><%=dateFormat.format(activity.getStart())%></dd>
					<%
						}
						if (activity.getEnd() != null) {
					%>
					<dt>To</dt>
					<dd><%=dateFormat.format(activity.getEnd())%></dd>
					<%
						}
					%>
					<%
						if(social != null && !social.isEmpty()) {
					%>
					<dt>Social:</dt>
					<dd><%=social%></dd>
					<%
						}
						if(tools != null && !tools.isEmpty()) {
					%>
					<dt>Tools:</dt>
					<dd>
						<ul>
							<% for(String tool : tools) { %>
							<li><%=tool%></li>
							<% } %>
						</ul>
					</dd>
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