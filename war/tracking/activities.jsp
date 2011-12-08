<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/includes/header.jsp"%>
<%@ page import="projectatlast.student.*"%>
<%@ page import="projectatlast.tracking.*"%>
<%@ page import="projectatlast.course.Course"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.*"%>
<%
	Student student = AuthController.getCurrentStudent();
	Activity currentActivity = StudentController.getCurrentActivity(student);
	List<Activity> activities = ActivityController
			.getAllFromStudent(student);

	DateFormat dateFormat = new SimpleDateFormat("EEEE, d MMMM yyyy HH:mm:ss zz", Locale.ENGLISH);
	request.setAttribute("dateFormat", dateFormat);
%>

<div data-role="page">
	<div data-role="header">
		<a href="/home" data-role="button" data-direction="reverse"
			data-icon="home" data-iconpos="notext">Home</a>
		<h1>List Activities</h1>
	</div>
	<form action="/tracking/deleteActivity" method="POST">
	<div data-role="content">
		<ul data-role="listview">
			<%
				for (Activity activity : activities) {
					request.setAttribute("activity", activity);
					
					if(!activity.equals(currentActivity)){
					%>
						<button type="submit" name="delete-button"  data-icon="delete" 
						data-inline="true" value="<%=activity.getId() %>" data-iconpos="notext"></button>
				<% 
					}
					else {
					%>
					<h3>Activity is still running ...</h3>
					<% } 
					
					if (activity instanceof StudyActivity) {
			%>
			<jsp:include page="/tracking/includes/studyActivity.jsp" />
			<%
					} else if (activity instanceof FreeTimeActivity) {
			%>
			<jsp:include page="/tracking/includes/freeTimeActivity.jsp" />
			<%
					}
					
				}
			%>
		</ul>
	</div>
	</form>
	<div data-role="footer" data-theme="c">
		<%@ include file="/includes/copyright.jsp"%>
	</div>
</div>
</body>
</html>