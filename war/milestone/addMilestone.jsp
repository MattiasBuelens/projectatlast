<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/includes/header.jsp"%>
<%@ page import="projectatlast.data.Registry"%>
<%@ page import="projectatlast.student.*"%>
<%@ page import="projectatlast.tracking.*"%>
<%@ page import="projectatlast.milestone.*"%>
<%@ page import="com.googlecode.objectify.*"%>
<%@ page import="projectatlast.query.*"%>
<%@ page import="projectatlast.course.Course"%>

<%@ page import="java.util.List"%>

<script type="text/javascript" src="milestoneAdd.js"></script>
<script type="text/javascript">

$("document").ready(function(){
	$(".milestoneString").textinput('disable');
	$(".milestoneString").attr("value","This is text");
	$("select").change(function(){
		createSentence()
	});


});




</script>

<div data-role="page">
	<div data-role="header">
		<a href="/home" data-role="button" data-rel="back" data-icon="home"
			data-iconpos="notext">Home</a>
		<h1>Create Milestone</h1>
	</div>


	<div data-role="content">
		<form method="get" action="/milestone/add">
				
			<div data-role="fieldcontain">
			    <label for="milestone">Milestone:</label>
			    <input type="text" name="milestoneString" id="milestone" class="milestoneString" value=""  />
			</div>	


			<fieldset data-type="horizontal" data-role="controlgroup">
				<legend>Type:</legend>
				<input type="radio" name="activity-type" id="activity-type-study"
					value="study" /> <label for="activity-type-study">Study</label> <input
					type="radio" name="activity-type" id="activity-type-freeTime"
					value="freeTime" /> <label for="activity-type-freeTime">Free
					Time</label>
			</fieldset>
			
			<div data-role="fieldcontain">
				<select name="type" id="type" >
					<option value=""			> Type </option>
					<option value="exercises"	> Exercise</option>
					<option value="theory"		> Theory</option>
				</select>
			</div>
			
			
			<div data-role="fieldcontain">
				<label for="course">Course:</label> <select name="course"
					id="course">
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
				<%
					Parser[] parsers = Parser.values();
				%>
				<label for="parser">Comparative Operator:</label> <select
					name="parser" id="parser" data-native-menu="false">
					<%
						for (Parser obj : parsers) {
					%>

					<option value="<%=obj.id()%>"><%=obj.humanReadable()%></option>
					<%
						}
					%>
				</select>
			</div>

			<div data-role="fieldcontain">
				<%
					ParseField[] parsefields = ParseField.values();
				%>

				<label for="parsefield">ParseField:</label> <select
					name="parsefield" id="parsefield" data-native-menu="false">
					<%
						for (ParseField obj : parsefields) {
					%>

					<option value="<%=obj.id()%>"><%=obj.humanReadable()%></option>
					<%
						}
					%>
				</select>
			</div>

			<div data-role="fieldcontain">
				<label for="basic">Goal:</label> <input type="text" name="goal"
					id="goal" value="" />
			</div>
			
			
			<div data-role="fieldcontain">
				<%
					ComparativeOperator[] operators = ComparativeOperator.values();
				%>
				<label for="operator">Comparative operator:</label> <select
					name="operator" id="operator" data-native-menu="false">
					<%
						for (ComparativeOperator obj : operators) {
					%>

					<option value="<%=obj.id()%>"><%=obj.humanReadable()%></option>
					<%
						}
					%>
				</select>
			</div>


			<div data-role="fieldcontain">
				<label for="start-date">Startdate:</label> 
				<input type="date" name="startDate" id="start-date" value="" />
				<label for="stop-date">Stopdate:</label> 
				<input type="date" name="stopDate" id="stop-date" value="" />
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