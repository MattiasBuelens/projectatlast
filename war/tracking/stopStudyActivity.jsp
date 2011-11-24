<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/includes/header.jsp"%>
<%@ page import="projectatlast.tracking.StopStudyActivityServlet"%>
<%@ page import="projectatlast.student.*"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.LinkedList" %>

<div data-role="page">
	<div data-role="header">
		<h1>Study Details</h1>
	</div>

	<div data-role="content">
		<form action="/tracking/stopStudyActivity" method="POST">
			<fieldset data-role="controlgroup">
				<h3>Social:</h3>
				<%
					List<String> socials = new LinkedList<String>();
					socials.add("Alone");
					socials.add("Two");
					socials.add("Group ");

					boolean isFirst = true;
					for (String social : socials) {
				%>
				<input type="radio" name="social" id="type-<%=social%>"
					value="<%=social%>" <%if (isFirst) {%> checked="checked" <%}%> />
				<label for="type-<%=social%>"><%=social%></label>
				<%
						isFirst = false;
					}
				%>
				</fieldset>
				
				<fieldset data-role="controlgroup">
				<br />
				<h3>Tools:</h3>
				<%
					Student currentStudent = AuthController.getCurrentStudent();
					List<String> tools = StudentController.getTools(currentStudent);	

					for (String tool : tools) {
				%>
				<input type="checkbox" name="tools" id="type-<%=tool%>"
					value="<%=tool%>" />
				<label for="type-<%=tool%>"><%=tool%></label>
				<%
					}
				%>
			</fieldset>

			<button type="submit" data-theme="b">Save</button>
		</form>
		
	</div>

	<div data-role="footer" data-theme="c">
		<%@ include file="/includes/copyright.jsp"%>
	</div>
</div>

</body>
</html>