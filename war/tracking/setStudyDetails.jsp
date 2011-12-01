<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/includes/header.jsp"%>
<%@ page import="projectatlast.tracking.*"%>
<%@ page import="projectatlast.student.*"%>
<%@ page import="java.util.*"%>

<%
	StudyActivity activity = (StudyActivity)request.getAttribute("activity");
%>

<div data-role="page">
	<div data-role="header">
		<h1>Study Details</h1>
	</div>

	<div data-role="content">
		<form action="/tracking/setStudyDetails" method="POST">
			<input type="hidden" name="activityId" value="<%=activity.getId() %>" />
			<fieldset data-role="controlgroup">
				<h3>Social:</h3>
				<%
					List<String> socials = new LinkedList<String>();
					socials.add("Alone");
					socials.add("Two");
					socials.add("Group");

					boolean isFirst = true;
					for (String social : socials) {
				%>
				<input type="radio" name="social" id="social-<%=social%>"
					value="<%=social%>" <%if (isFirst) {%> checked="checked" <%}%> />
				<label for="social-<%=social%>"><%=social%></label>
				<%
						isFirst = false;
					}
				%>
			</fieldset>

			<fieldset data-role="controlgroup">
				<h3>Tools:</h3>
				<%
					Student currentStudent = AuthController.getCurrentStudent();
					List<String> tools = StudentController.getTools(currentStudent);
					ListIterator<String> it = tools.listIterator();

					while (it.hasNext()) {
						int toolIndex = it.nextIndex();
						String tool = it.next();
				%>
				<input type="checkbox" name="tools" id="tool-<%=toolIndex%>"
					value="<%=tool%>" /> <label for="tool-<%=toolIndex%>"><%=tool%></label>
				<%
					}
				%>
				<label for="basic">Other:</label>
    			<input type="text" name="extraTool" id="extraTool" value=""  />
				
			</fieldset>
			
			<fieldset data-role="controlgroup">
				<h3>Location:</h3>
				<%
					/*Student currentStudent = AuthController.getCurrentStudent();
					List<String> locations = StudentController.getLocations(currentStudent);
					ListIterator<String> it = locations.listIterator();

					while (it.hasNext()) {
						int locationIndex = it.nextIndex();
						String location = it.next();
				% >
				<input type="radio" name="locations" id="location-<%=locationIndex% >"
					value="<%=location% >" /> <label for="location-<%=locationIndex% >"><%=location% ></label>
				< %
					}*/
				%>
				<label for="basic">Other:</label>
    			<input type="text" name="extraLocation" id="extraLocation" value=""  />
				
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