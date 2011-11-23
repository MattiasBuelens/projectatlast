<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/includes/header.jsp"%>
<%@ page import="projectatlast.student.AuthController"%>
<%@ page import="projectatlast.student.Student"%>
<%@ page import="projectatlast.student.SettingsController"%>
<%@ page import="projectatlast.course.Course"%>
<%@ page import="java.util.List"%>
<%
	// Get enrolled courses of current student
	Student student = AuthController.getCurrentStudent();
	List<Course> courses = SettingsController.getCourses(student);
%>

<div data-role="page">
	<div data-role="header">
		<a href="/home" data-role="button" data-rel="back" data-icon="home"
			data-iconpos="notext">Home</a>
		<h1>Start Study</h1>
	</div>

	<div data-role="content">
		<form action="/tracking/startStudyActivity" method="POST">
			<fieldset data-role="controlgroup">
				<legend>Course:</legend>
				<%
					boolean isFirst = true;
					for (Course course : courses) {
						String name = course.getName();
						String id = course.getId();
				%>
				<input type="radio" name="course" id="course-<%=id%>"
					value="<%=id%>" <%if (isFirst) {%> checked="checked" <%}%> /> <label
					for="course-<%=id%>"><%=name%></label>
				<%
					isFirst = false;
					}
				%>
			</fieldset>
			
			<input type="hidden" name="type" value="Exercices" />

			<button type="submit" data-theme="b">Start</button>
		</form>
	</div>

	<div data-role="footer" data-theme="c">
		<%@ include file="/includes/copyright.jsp"%>
	</div>
</div>

</body>
</html>