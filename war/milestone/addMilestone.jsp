<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/includes/header.jsp"%>
<%@ page import="projectatlast.course.Course"%>
<%@ page import="java.util.List"%>


<div data-role="page">
	<div data-role="header">
		<a href="/home" data-role="button" data-rel="back" data-icon="home"
			data-iconpos="notext">Home</a>
		<h1>Add Milestone</h1>
	</div>

	<div data-role="content">

		<form action="/tracking/startStudyActivity" method="POST">
			<fieldset data-type="horizontal" data-role="controlgroup">
				<input type="radio" name="type" id="type-study" value="study" /> <label
					for="type-study">Study</label> <input type="radio" name="type"
					id="type-freeTime" value="freeTime" /> <label for="type-freeTime">Free
					Time</label>
			</fieldset>

			<div data-role="fieldcontain">
				<label for="select-course" class="select">Course:</label> <select
					name="select-course" id="select-course">
					<option value="all">All Courses</option>
					<%
						@SuppressWarnings("unchecked")
						List<Course> courses = (List<Course>) request
								.getAttribute("studentCourses");

						for (Course course : courses) {
							String name = course.getName();
					%>
					<option value="<%=name%>"><%=name%></option>
					<%
						}
					%>
				</select>
			</div>

			<div data-role="fieldcontain">
				<label for="select-type" class="select">Type:</label> <select
					name="select-type" id="select-type">
					<option value="exercises">Exercises</option>
				</select>
			</div>
			<div data-role="fieldcontain">
				<label for="select-param" class="select">Parameter:</label> <select
					name="select-param" id="select-param">
					<option value="exercises">Exercises</option>
				</select>
			</div>

			<button type="submit" data-theme="b">Start</button>
		</form>

	</div>

	<div data-role="footer" data-theme="c">
		<%@ include file="/includes/copyright.jsp"%>
	</div>
</div>

</body>
</html>