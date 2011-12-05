<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/includes/header.jsp"%>
<%@ page import="projectatlast.student.Student"%>
<%@ page import="projectatlast.course.Course"%>
<%@ page import="projectatlast.course.StudyProgram"%>
<%@ page import="java.util.*"%>
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
		<a href="/home" data-role="button" data-direction="reverse" data-icon="home"
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
				<%
					for (StudyProgram program : studyPrograms) {
				%>
				<option value="<%=program.getId()%>"><%=program.getName()%></option>
				<%
					}
				%>
			</select>
		</div>
		<fieldset id="study-program-courses" data-role="controlgroup"
			class="ui-listview-checkboxes ui-screen-hidden">
			<legend>Select courses from study program:</legend>
			<ul data-role="listview" data-inset="true" data-filter="true"
				data-filter-placeholder="Search for courses&hellip;">
			</ul>
		</fieldset>
		<form action="/student/configure" method="POST">
			<fieldset id="enrolled-courses" data-role="controlgroup"
				class="ui-controlgroup-checkboxes">
				<legend>Enrolled courses:</legend>
				<%
					for (Course course : studentCourses) {
				%>
				<input type="checkbox" name="courses"
					id="enrolled-course-<%=course.getId()%>"
					value="<%=course.getId()%>" checked="checked" />
				<label for="enrolled-course-<%=course.getId()%>"><%=course.getName()%></label>
				<%
					}
				%>
			</fieldset>
			
			<%
			if (isConfigured) {
			%>
			<fieldset  data-role="controlgroup">
			<legend>My tools:</legend>
			<br><p>Check the tools you want to remove from the tools list:</p>
			<% 
			List<String> tools = student.getTools();
			ListIterator<String> it1 = tools.listIterator();

					while (it1.hasNext()) {
						int toolIndex = it1.nextIndex();
						String tool = it1.next();
				%>
				<input type="checkbox" name="tools" id="tool-<%=toolIndex%>"
					value="<%=tool%>" /> <label for="tool-<%=toolIndex%>"><%=tool%></label>
				<%
					}
			
				%>
			</fieldset>
			<fieldset  data-role="controlgroup">
			<legend>My locations:</legend>
			<br><p>Check the locations you want to remove from the locations list:</p>
			<% 
			List<String> locations = student.getLocations();
			ListIterator<String> it2 = locations.listIterator();

					while (it2.hasNext()) {
						int locationIndex = it2.nextIndex();
						String location = it2.next();
				%>
				<input type="checkbox" name="locations" id="location-<%=locationIndex%>"
					value="<%=location%>" /> <label for="location-<%=locationIndex%>"><%=location%></label>
				<%
					}
			}
				%>
			</fieldset>
			<button type="submit" data-theme="b">Save</button>
		</form>
	</div>
	<!-- /content -->

	<div data-role="footer" data-theme="c">
		<%@ include file="/includes/copyright.jsp"%>
	</div>
</div>
<!-- /page -->

</body>
</html>