<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/includes/header.jsp"%>
<%@ page import="java.util.List"%>
<%@ page import="projectatlast.student.Student"%>
<%@ page import="projectatlast.course.Course"%>
<%@ page import="projectatlast.course.StudyProgram"%>
<%
	Student student = (Student) request.getAttribute("student");
	boolean isConfigured = student.isConfigured();
	@SuppressWarnings("unchecked")
	List<StudyProgram> studyPrograms = (List<StudyProgram>) request
			.getAttribute("studyPrograms");
	@SuppressWarnings("unchecked")
	List<Course> studentCourses = (List<Course>) request
			.getAttribute("studentCourses");
%>

<div id="configure" data-role="page" data-url="/student/configure">

	<div data-role="header">
		<%
			if (isConfigured) {
		%>
		<a href="/" data-role="button" data-rel="back" data-icon="home"
			data-iconpos="notext">Home</a>
		<%
			} else {
		%>
		<a href="/student/logout" data-role="button" data-icon="delete"
			data-rel="dialog" data-transition="slidedown">Log out</a>
		<%
			}
		%>
		<h1>Configure</h1>
	</div>
	<!-- /header -->

	<div data-role="content">
		<div data-role="fieldcontain">
			<label for="study-program" class="select">Study program:</label>
			<select name="study-program" id="study-program">
				<option>Select...</option>
				<%
					for (StudyProgram program : studyPrograms) {
				%>
				<option value="<%=program.getId()%>"><%=program.getName()%></option>
				<%
					}
				%>
			</select>
		</div>
		<form action="/student/saveConfiguration" method="POST">
			<button type="submit">Save</button>
		</form>
		<a href="/" data-role="button">Take me home (Country roads)</a>
	</div>
	<!-- /content -->

	<%@ include file="/includes/footer.jsp"%>
</div>
<!-- /page -->

</body>
</html>