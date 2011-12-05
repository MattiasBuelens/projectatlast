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


<div data-role="page">
<script src="js/graphs/create.js" type="text/javascript" ></script>

	<div data-role="header">
		<h1>Create Graph</h1>
	</div>


	<div data-role="content">
		<form method="post" action="/graph/add" data-ajax="false" id="form">

		<div data-role="fieldcontain">
				<label for="title">Title:</label> <input type="text" name="title"
					id="title" value="" />
			</div>


			<fieldset data-type="horizontal" data-role="controlgroup" >
				<legend>Chart Type:</legend>
				<input type="radio" name="stacked" id="normal"
					value="" checked /> <label for="normal">Normal Graph</label> <input
					type="radio" name="stacked" id="stacked"
					value="true" /> <label for="stacked">Stacked Graph</label>
			</fieldset>
			
			<fieldset data-type="horizontal" data-role="controlgroup">
				<legend>Chart Type:</legend>
				<input type="radio" name="chart-type" id="chart-type-bar"
					value="BAR" checked /> 
					<label for="chart-type-bar">Bar</label>
					
						<input type="radio" name="chart-type" id="chart-type-pie"
					value="PIE" class="notstacked" /> <label class="notstacked" for="chart-type-pie">Pie</label>
					
					 <input	type="radio" name="chart-type" id="chart-type-column"
					value="COLUMN" /> <label for="chart-type-column">Column</label>

			
			</fieldset>

	<div id="groupholder">

	 <div id="input1" style="margin-bottom:4px;" class="clonedInput"></div>

			<div data-role="fieldcontain" class="groupby" id="group1">
				<fieldset data-role="controlgroup">
					<%
						GroupField[] sortFields = GroupField.values();
					%>
					 <label for="sortfield">Group by:</label> <select
						name="sortfield" id="sortfield" data-native-menu="false">
						<%
							for (GroupField obj : sortFields) {
						%>

						<option value="<%=obj.toString()%>"><%=obj.humanReadable()%></option>
						<%
							}
						%>
					</select>
				</fieldset>
			</div>
			</div>
			<div id="addgroup" data-role="button" data-icon="plus" data-theme="c" data-inline="true">Add Group</div>	

			
			<div data-role="fieldcontain" class="stacked">

				<fieldset data-role="controlgroup">
			
					<label for="subgroup">Subgroup:</label> <select
						name="subgroup" id="subgroup" data-native-menu="false">
						<%
							for (GroupField obj : sortFields) {
						%>

						<option value="<%=obj.toString()%>"><%=obj.humanReadable()%></option>
						<%
							}
						%>
					</select>
				</fieldset>
				
				
			</div>
			

			<div data-role="fieldcontain">
				<fieldset data-role="controlgroup" data-type="horizontal">
					<%
						Parser[] parsers = Parser.values();
					%>
					<label for="parser">Calculation:</label> <select name="parser"
						id="parser" data-native-menu="false">
						<%
							for (Parser obj : parsers) {
						%>

						<option value="<%=obj.toString()%>"><%=obj.humanReadable()%>
							of
						</option>
						<%
							}
						%>

						<%
							ParseField[] parsefields = ParseField.values();
						%>



					</select> <select name="parsefield" id="parsefield" data-native-menu="false">
						<%
							for (ParseField obj : parsefields) {
						%>

						<option value="<%=obj.toString()%>"><%=obj.humanReadable()%></option>
						<%
							}
						%>


					</select>
				</fieldset>
			</div>

		



			<div >
 			<div id="extraoptions" data-role="button" data-icon="arrow-r" data-theme="c" data-inline="true">Extra Options</div>	
			<div id="extra"  >
			
			<div id="date-collapsible" data-role="collapsible">
					<h3>Date Selector</h3>
					<%
						String startDate = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
						String stopDate = startDate;
					%>
					<label for="start-date">Start date:</label>
					<input name="startdate" id="start-date" type="date" value="<%=startDate%>"
						data-role="datebox" data-options='{"mode": "calbox", "disableManualInput": true, "dateFormat": "DD/MM/YYYY"}'>
					<label for="stop-date">Stop date:</label>
					<input name="stopdate" id="stop-date" type="date" value="<%=stopDate%>"
						data-role="datebox" data-options='{"mode": "calbox", "disableManualInput": true, "dateFormat": "DD/MM/YYYY"}'>
				</div>
			 
			 <div >Course Selector</div>
			</div>
			</div>


			<button type="submit" data-theme="b" name="submit"
				value="submit-value">Create Graph</button>
		</form>
	</div>

	<div data-role="footer" data-theme="c">
		<%@ include file="/includes/copyright.jsp"%>
	</div>
</div>



</body>
</html>