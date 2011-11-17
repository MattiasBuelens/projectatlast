<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/includes/header.jsp"%>
<%@ page import="projectatlast.tracking.NewStudyActivityServlet"%>
<%@ page import="projectatlast.course.Course"%>
<%@ page import="java.util.List"%>

<div data-role="page">
	<div data-role="header">
		<a href="/" data-role="button" data-rel="back" data-icon="home"
			data-iconpos="notext">Home</a>
		<h1>Start Study</h1>
	</div>

	<div data-role="content">
		<form action="/tracking/startStudyActivity" method="POST">
			<fieldset data-role="controlgroup">
				<legend>Course:</legend>
				<%
					@SuppressWarnings("unchecked")
					List<Course> courses = (List<Course>) request
							.getAttribute("studentCourses");

					boolean isFirst = true;
					for (Course course : courses) {
						String name = course.getName();
				%>
				<input type="radio" name="type" id="type-<%=name%>"
					value="<%=name%>" <%if (isFirst) {%> checked="checked" <%}%> />
				<label for="type-<%=name%>"><%=name%></label>
				<%
						isFirst = false;
					}
				%>
			</fieldset>

			<button type="submit" data-theme="b">Start</button>
		</form>
	</div>

	<div data-role="footer" data-theme="c">
		<%@ include file="/includes/copyright.jsp"%>
	</div>
</div>

</body>
</html>