<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/includes/header.jsp"%>
<%@ page import="projectatlast.data.Registry"%>
<%@ page import="projectatlast.student.*"%>
<%@ page import="projectatlast.tracking.*"%>
<%@ page import="com.googlecode.objectify.*"%>
<%@ page import="java.util.List"%>
<%
	Student student = AuthController.getCurrentStudent();
	List<Activity> activities = ActivityController
			.getAllFromStudent(student);
%>

<div data-role="page">
	<div data-role="header">
		<a href="/" data-role="button" data-rel="back" data-icon="home"
			data-iconpos="notext">Home</a>
		<h1>List Activities</h1>
	</div>

	<div data-role="content">
		<div class="content-primary">
			<ul data-role="listview">
				<%
					for (Activity activity : activities) {
				%>
				<li><a href="#"><%=activity.getType()%></a></li>
				<%
					}
				%>
			</ul>
		</div>
	</div>

	<%@ include file="/includes/footer.jsp"%>
</div>
</body>
</html>