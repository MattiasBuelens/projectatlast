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
				<legend>Type:</legend>
					<%
						@SuppressWarnings("unchecked")
						List<Course> courses = (List<Course>)request.getAttribute("studentCourses");

						for (Course course : courses) {
							String name = course.getName();
					%>
						<input type="radio" name="type" id="type-<%=name%>" value="<%=name%>" />
						<label for="type-<%=name%>"><%=name%></label>
					<%
						}
					%>
			</fieldset>

			<button type="submit" data-theme="b">Start</button>
		</form>
	</div>

	<%@ include file="/includes/footer.jsp"%>
</div>

</body>
</html>