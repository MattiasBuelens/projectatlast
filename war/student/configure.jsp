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

<div id="student-configure" data-role="page" data-url="/student/configure">

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
		<div>
			<label for="study-program" class="select">Study program:</label> <select
				name="study-program" id="study-program">
				<option value="">Select...</option>
				<% /* TODO Remove test data */ %>
				<option value="Bbi">Bachelor in de ingenieurswetenschappen</option>
				<option value="Bira">Bachelor in de ingenieurswetenschappen: architectuur</option>
				<%
					for (StudyProgram program : studyPrograms) {
				%>
				<option value="<%=program.getId()%>"><%=program.getName()%></option>
				<%
					}
				%>
			</select>
		</div>
		<fieldset id="study-program-courses" data-role="controlgroup" class="ui-listview-with-checkboxes ui-screen-hidden">
			<legend>Select courses from study program:</legend>
			<ul data-role="listview" data-inset="true" data-filter="true"
				data-filter-placeholder="Search for courses&hellip;">
				<% /* TODO Remove test data */ %>
				<li><input type="checkbox" id="program-course-H001"
					name="program-course" value="H001" /> <label
					for="program-course-H001">Analyse, deel 1</label></li>
				<li><input type="checkbox" id="program-course-H002"
					name="program-course" value="H002" /> <label
					for="program-course-H002">Analyse, deel 2</label></li>
				<li><input type="checkbox" id="program-course-H101"
					name="program-course" value="H101" /> <label
					for="program-course-H101">Toegepaste mechanica, deel 1</label></li>
			</ul>
			<script class="course-template" type="text/x-jquery-tmpl">
				<li>
					<input type="checkbox" id="\${listId}-\${id}" name="\${checkboxName}" value="\${value}" />
					<label for="\${listId}-\${id}">\${name}</label>
				</li>
			</script>
		</fieldset>
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