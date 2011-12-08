<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/includes/header.jsp"%>
<%@ page import="projectatlast.tracking.*"%>
<%@ page import="projectatlast.student.*"%>
<%@ page import="java.util.*"%>
<%
	Student currentStudent = AuthController.getCurrentStudent();
	StudyActivity activity = (StudyActivity) request
			.getAttribute("activity");
	long moodInterest = activity.getMood().getInterest();
	long moodComprehension = activity.getMood().getComprehension();
%>

<div id="setStudyDetails" data-role="page">
	<script>
	$("#setStudyDetails").live("pageinit", function(){
		$("#location-other-input").click(function(){
			$("input[type='radio'][name='location']").prop("checked",false).checkboxradio("refresh");
			$("input[type='radio'][id='location-other']").prop("checked",true).checkboxradio("refresh");
		});
		$("input[type='radio'][class='standardvalues']").change(function(){
			$("#location-other-input").val("");
		});
	});
</script>
	<div data-role="header">
		<a href="/home" data-role="button" data-direction="reverse" data-icon="home"
			data-iconpos="notext">Home</a>
		<h1>Study Details</h1>
	</div>

	<div data-role="content">
		
		<div class="ui-body ui-bar-c ui-corner-all">
				<span>Finished activity:</span>
				<p class="activityString">Course: <%=activity.getCourse().getName()%> <br>
				Type: <%=activity.getType()%></p>
		</div>
		
		<form action="/tracking/setStudyDetails" method="POST">
			<input type="hidden" name="activityId" value="<%=activity.getId()%>" />


			<div data-role="fieldcontain">
				<h3>Mood</h3>
				<div data-role="pointpicker">
					<div data-role="fieldcontain">
						<label for="mood-interest">Interest</label> <input type="range"
							id="mood-interest" name="mood-interest" data-role="pointpicker-x"
							min="0" max="100" step="1" value="<%=moodInterest %>" />
					</div>
					<div data-role="fieldcontain">
						<label for="mood-comprehension">Comprehension</label> <input
							type="range" id="mood-comprehension" name="mood-comprehension"
							data-role="pointpicker-y" min="0" max="100" step="1" value="<%=moodComprehension %>" />
					</div>
					<div class="mood-picker ui-shadow" data-role="pointpicker-area"></div>
				</div>
			</div>

			<%
				if (activity.getType().equalsIgnoreCase("study")) {
			%>
			<div data-role="fieldcontain">
				<h3>Study</h3>
				<label for="pages">Pages studied</label> <input type="number"
					name="pages" id="pages" value="0" min="0" step="1" />
			</div>
			<%
				}
			%>

			<fieldset data-role="controlgroup">
				<h3>Social</h3>
				<%
					List<String> socials = new LinkedList<String>();
					// TODO Pull from controller
					socials.add("Alone");
					socials.add("Two");
					socials.add("Group");
					ListIterator<String> it = socials.listIterator();

					while (it.hasNext()) {
						int socialIndex = it.nextIndex();
						boolean isFirst = (socialIndex == 0);
						String social = it.next();
				%>
				<input type="radio" name="social" id="social-<%=social%>"
					value="<%=social%>" <%if (isFirst) {%> checked="checked" <%}%> />
				<label for="social-<%=social%>"><%=social%></label>
				<%
					}
				%>
			</fieldset>

			<fieldset data-role="controlgroup">
				<h3>Tools</h3>
				<%
					List<String> tools = StudentController.getTools(currentStudent);
					it = tools.listIterator();

					while (it.hasNext()) {
						int toolIndex = it.nextIndex();
						String tool = it.next();
				%>
				<input type="checkbox" name="tools" id="tool-<%=toolIndex%>"
					value="<%=tool%>" /> <label for="tool-<%=toolIndex%>"><%=tool%></label>
				<%
					}
				%>
				<input type="checkbox" name="otherTool" id="tool-other"
					value="other" /> <label for="tool-other">Other:</label> <input
					type="text" name="tools" id="extraTool" value="" />

			</fieldset>

			<fieldset data-role="controlgroup">
				<h3>Location</h3>
				<%
					List<String> locations = StudentController
							.getLocations(currentStudent);
					it = locations.listIterator();
					while (it.hasNext()) {
						int locationIndex = it.nextIndex();
						boolean isFirst = (locationIndex == 0);
						String location = it.next();
				%>
				<input type="radio" class="standardvalues" name="location"
					id="location-<%=locationIndex%>" value="<%=location%>"
					<%if (isFirst) {%> checked="checked" <%}%> /> <label
					for="location-<%=locationIndex%>"><%=location%></label>
				<%
					}
				%>
				<input type="radio" name="location" id="location-other"
					value="other" /> <label for="location-other">Other:</label> <input
					type="text" name="location-other" id="location-other-input"
					value="" />

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