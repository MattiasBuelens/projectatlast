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
	@SuppressWarnings("unchecked")
	List<String> studyTools = (List<String>) request
			.getAttribute("studyTools");
	@SuppressWarnings("unchecked")
	List<String> studyLocations = (List<String>) request
			.getAttribute("studyLocations");
	@SuppressWarnings("unchecked")
	List<String> freeTimeTypes = (List<String>) request
			.getAttribute("freeTimeTypes");
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
			<label for="study-program" class="select">Study program</label> <select
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
			<legend>Select courses from study program</legend>
			<ul data-role="listview" data-inset="true" data-filter="true"
				data-filter-placeholder="Search for courses&hellip;">
			</ul>
		</fieldset>
		<form action="/student/configure" method="POST">
			<fieldset id="enrolled-courses" data-role="controlgroup"
				class="ui-controlgroup-checkboxes">
				<legend>Enrolled courses</legend>
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
			<fieldset data-role="controlgroup">
				<legend>Tools</legend>
				<p>Check the tools you want to remove.</p>
				<% 
					ListIterator<String> it = studyTools.listIterator();
					while (it.hasNext()) {
						int toolIndex = it.nextIndex();
						String tool = it.next();
				%>
				<input type="checkbox" name="tools" id="tool-<%=toolIndex%>"
					value="<%=tool%>" /> <label for="tool-<%=toolIndex%>"><%=tool%></label>
				<%
					}
				%>
			</fieldset>

			<fieldset data-role="controlgroup">
				<legend>Locations</legend>
				<p>Check the locations you want to remove.</p>
				<% 
					it = studyLocations.listIterator();
					while (it.hasNext()) {
						int locationIndex = it.nextIndex();
						String location = it.next();
					%>
					<input type="checkbox" name="locations" id="location-<%=locationIndex%>"
						value="<%=location%>" /> <label for="location-<%=locationIndex%>"><%=location%></label>
				<%
					}
				%>
			</fieldset>
			
			<fieldset data-role="controlgroup">
				<legend>Free time activities</legend>
				<p>Check the activity types you want to remove.</p>
				<% 
					it = freeTimeTypes.listIterator();
					while (it.hasNext()) {
						int typeIndex = it.nextIndex();
						String type = it.next();
				%>
				<input type="checkbox" name="freeTimeTypes" id="freeTimeType-<%=typeIndex%>"
					value="<%=type%>" /> <label for="freeTimeType-<%=typeIndex%>"><%=type%></label>
				<%
					}
				%>
				
			</fieldset>
			<%
				}
			%>
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