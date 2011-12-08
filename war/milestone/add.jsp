<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/includes/header.jsp"%>
<%@ page import="projectatlast.data.Registry"%>
<%@ page import="projectatlast.student.*"%>
<%@ page import="projectatlast.tracking.*"%>
<%@ page import="projectatlast.milestone.*"%>
<%@ page import="com.googlecode.objectify.*"%>
<%@ page import="projectatlast.query.*"%>
<%@ page import="projectatlast.course.Course"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.SimpleDateFormat"%>

<div id="milestone-add" data-role="page" data-url="/milestone/add">
	<div data-role="header">
		<a href="/home" data-role="button" data-direction="reverse"
			data-icon="home" data-iconpos="notext">Home</a>
		<h1>Add Milestone</h1>
	</div>

	<div data-role="content">
		<form method="post" action="/milestone/add" data-ajax="false">
			<%
				String errorLog=(String)request.getAttribute("error");
				if(errorLog != null) {
			%>
					 <div class="error ui-body ui-body-e ui-corner-all"><%= errorLog %></div>
			<%
				} else {
			%>
					 <div class="error ui-body ui-body-e ui-corner-all ui-screen-hidden">&nbsp;</div>
			<%
				}
			%>

			<div class="ui-body ui-bar-b ui-corner-all">
				<span>Milestone:</span>
				<p class="milestoneString"></p>
			</div>

			<div data-role="fieldcontain">
				<fieldset data-type="horizontal" data-role="controlgroup">
					<legend>Type:</legend>
					<input type="radio" name="activity-type" id="activity-type-study"
						value="study" checked="checked" /> <label
						for="activity-type-study">Study</label> <input type="radio"
						name="activity-type" id="activity-type-freeTime" value="freeTime" />
					<label for="activity-type-freeTime">Free Time</label>
				</fieldset>
			</div>

			<div class="studyField" data-role="collapsible" data-theme="c"
				data-content-theme="d">
				<h3>Course/Type</h3>
				<div data-role="fieldcontain">
					<label for="study-course">Course</label> <select name="course"
						id="study-course">
						<option value="" disabled="disabled">Course</option>
						<option value="all" data-readable="all my courses">All my
							courses</option>
						<%
							@SuppressWarnings("unchecked")
							List<Course> courses = (List<Course>) request
									.getAttribute("studentCourses");

							for (Course course : courses) {
								String name = course.getName();
								String humanReadable = name.toLowerCase();
						%>
						<option value="<%=course.getId()%>"
							data-readable="<%=humanReadable%>"><%=name%></option>
						<%
							}
						%>
					</select>
				</div>

				<div data-role="fieldcontain">
					<label for="study-type">Type</label> <select name="study-type"
						id="study-type">
						<option value="" disabled="disabled">Type</option>
						<option value="all" data-readable="on">All types</option>
						<option value="exercises" data-readable="on exercises for">Exercise</option>
						<option value="theory" data-readable="on theory for">Theory</option>
					</select>
				</div>
			</div>

			<div class="freeTimeField" data-role="collapsible" data-theme="c"
				data-content-theme="d">
				<h3>Type</h3>
				<div data-role='fieldcontain'>
					<select name="freetime-type" id="freetime-type">
						<option value="" disabled="disabled">Type</option>
						<option value="all"
							data-readable="on all types of free time activities">All
							types</option>
						<%
							@SuppressWarnings("unchecked")
							List<String> types = (List<String>) request
									.getAttribute("freeTimeTypes");

							for (String type : types) {
								String humanReadable = type.toLowerCase();
						%>
						<option value="<%=type%>" data-readable="on <%=humanReadable%>"><%=type%></option>
						<%
							}
						%>
					</select>
				</div>
			</div>

			<div data-role="collapsible-set" data-theme="c"
				data-content-theme="d">
				<div id="goal-collapsible" data-role="collapsible">
					<h3>Goal</h3>
					<div data-role="fieldcontain">
						<label for="basic">Goal:</label>
						<fieldset data-type="horizontal" data-role="controlgroup">
							<%
								Parser[] parsers = Parser.values();
							%>
							<select name="parser" id="parser">
								<%
									for (Parser obj : parsers) {
								%>

								<option value="<%=obj.id()%>"
									data-readable="<%=obj.sentence()%>">
									<%=obj.humanReadable()%>
									of
								</option>
								<%
									}
								%>
							</select>

							<%
								// TODO Study activity has different parse fields, need another select
								List<ParseField> parsefields = ParseField.values(Activity.class);
							%>

							<select name="parsefield" id="parsefield">
								<%
									for (ParseField obj : parsefields) {
								%>

								<option value="<%=obj.id()%>" data-readable="<%=obj.sentence()%>"
									data-unit="<%=obj.unit()%>" data-limit="<%=obj.limit()%>">
									<%=obj.humanReadable()%> is
								</option>
								<%
									}
								%>
							</select>

							<%
								ComparativeOperator[] operators = ComparativeOperator.values();
							%>
							<select name="operator" id="operator">
								<%
									for (ComparativeOperator obj : operators) {
								%>

								<option value="<%=obj.id()%>"
									data-readable="<%=obj.humanReadable()%>">
									<%=obj.symbol()%></option>
								<%
									}
								%>
							</select>

						</fieldset>
						<input type="number" name="goal" id="goal" value="" min="0" /> <span
							class="goal-unit" id="goal-unit"></span>
					</div>
				</div>

				<div id="date-collapsible" data-role="collapsible">
					<h3>Date range</h3>
					<%
						String startDate = new SimpleDateFormat("dd-MM-yyyy")
								.format(new Date());
						String stopDate = startDate;
					%>
					<label for="start-date">Start monitoring from:</label> <input
						name="startdate" id="start-date" type="date"
						value="<%=startDate%>" data-role="datebox"
						data-options='{"mode": "calbox", "disableManualInput": true, "dateFormat": "DD-MM-YYYY"}'>
					<label for="stop-date">Deadline:</label> <input name="stopdate"
						id="stop-date" type="date" value="<%=stopDate%>"
						data-role="datebox"
						data-options='{"mode": "calbox", "disableManualInput": true, "dateFormat": "DD-MM-YYYY"}'>
				</div>

			</div>

			<button type="submit" data-theme="b" name="submit"
				value="submit-value">Create milestone</button>
		</form>
	</div>

	<div data-role="footer" data-theme="c">
		<%@ include file="/includes/copyright.jsp"%>
	</div>
</div>
</body>
</html>