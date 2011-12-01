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

<div id="addMilestone" data-role="page">
	<script type="text/javascript" src="/milestone/milestoneAdd.js"></script>

	<div data-role="header">
		<a href="/home" data-role="button" data-direction="reverse" data-icon="home"
			data-iconpos="notext">Home</a>
		<h1>Create Milestone</h1>
	</div>

	<div data-role="content">
		<form method="post" action="/milestone/add" data-ajax="false">

			<div class="ui-body ui-bar-b ui-corner-all">
				<span>Milestone:</span>
				<p class="milestoneString">Welcome!</p>
			</div>

			<div data-role="fieldcontain">
				<fieldset data-type="horizontal" data-role="controlgroup">
					<legend>Type:</legend>
					<input type="radio" name="activity-type" id="activity-type-study" value="study" checked="checked" />
					<label for="activity-type-study">Study</label>
					<input type="radio" name="activity-type" id="activity-type-freeTime" value="freeTime" />
					<label for="activity-type-freeTime">Free Time</label>
				</fieldset>
			</div>

			<div id="study-fields" data-role="collapsible" data-theme="c" data-content-theme="d">
				<h3>Course/Type</h3>
				<div data-role="fieldcontain">
					<select name="type" id="type">
						<option value="" disabled="disabled">Type</option>
						<option value="all" data-readable="work">All types</option>
						<option value="exercise" data-readable="do exercises">Exercise</option>
						<option value="theory" data-readable="study theory">Theory</option>
					</select>
				</div>

				<div data-role="fieldcontain">
					<select name="course" id="course">
						<option value="" disabled="disabled">Course</option>
						<option value="all" data-readable="for all courses">All courses</option>
						<%
							@SuppressWarnings("unchecked")
							List<Course> courses = (List<Course>) request
									.getAttribute("studentCourses");

							for (Course course : courses) {
								String name = course.getName();
								String humanReadable = "for " + name.toLowerCase();
						%>
						<option value="<%=course.getId()%>" data-readable="<%=humanReadable%>"><%=name%></option>
						<%
							}
						%>
					</select>
				</div>
			</div>

			<div data-role="collapsible-set" data-theme="c" data-content-theme="d">
				<div id="goal-collapsible" data-role="collapsible">
					<h3>Goal</h3>
					<div data-role="fieldcontain">
						<label for="basic">Goal:</label>
						<fieldset data-type="horizontal" data-role="controlgroup">
							<%
								Parser[] parsers = Parser.values();
							%>
							<select name="parser" id="parser" data-native-menu="false">
								<%
									for (Parser obj : parsers) {
								%>

								<option value="<%=obj.id()%>" data-readable="<%=obj.humanReadable()%>">
									<%=obj.humanReadable()%> of</option>
								<%
									}
								%>
							</select>

							<%
								ParseField[] parsefields = ParseField.values();
							%>

							<select name="parsefield" id="parsefield"
								data-native-menu="false">
								<%
									for (ParseField obj : parsefields) {
								%>

								<option value="<%=obj.id()%>" data-readable="<%=obj.humanReadable()%>" data-unit="<%=obj.unit()%>">
									<%=obj.humanReadable()%> is </option>
								<%
									}
								%>
							</select>

							<%
								ComparativeOperator[] operators = ComparativeOperator.values();
							%>
							<select name="operator" id="operator" data-native-menu="false">
								<%
									for (ComparativeOperator obj : operators) {
								%>

								<option value="<%=obj.id()%>" data-readable="<%=obj.humanReadable()%>">
									<%=obj.symbol()%></option>
								<%
									}
								%>
							</select>

						</fieldset>
						<input type="number" name="goal" id="goal" value="" />
						<span class="goal-unit" id="goal-unit"></span>
					</div>
				</div>

				<div id="date-collapsible" data-role="collapsible">
					<h3>Date range</h3>
					<%
						String startDate = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
						String stopDate = startDate;
					%>
					<label for="start-date">Start monitoring from:</label>
					<input name="startdate" id="start-date" type="date" value="<%=startDate%>"
						data-role="datebox" data-options='{"mode": "calbox", "disableManualInput": true, "dateFormat": "DD/MM/YYYY"}'>
					<label for="stop-date">Deadline:</label>
					<input name="stopdate" id="stop-date" type="date" value="<%=stopDate%>"
						data-role="datebox" data-options='{"mode": "calbox", "disableManualInput": true, "dateFormat": "DD/MM/YYYY"}'>
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