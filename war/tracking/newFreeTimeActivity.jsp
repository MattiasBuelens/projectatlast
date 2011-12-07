<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="projectatlast.tracking.ActivityController"%>
<%@ page import="projectatlast.student.*"%>
<%@ page import="java.util.*"%>
<%@ include file="/includes/header.jsp"%>

<div id="newFreeTimeActivity" data-role="page">
	<script type="text/javascript">
	// TODO Make sure this only works on this page
	$("#newFreeTimeActivity").bind("pageinit", function() {
		$("#type-other-input").click(function(){
			$("input[type='radio'][name='type']").prop("checked",false).checkboxradio("refresh");
			$("input[type='radio'][id='type-other']").prop("checked",true).checkboxradio("refresh");
		});
		$("input[type='radio'][name='type']").change(function(){
			$("#type-other-input").val("");
		});
	});
</script>

	<div data-role="header">
		<a href="/home" data-role="button" data-direction="reverse"
			data-icon="home" data-iconpos="notext">Home</a>
		<h1>Start Free Time</h1>
	</div>

	<div data-role="content">
		<form action="/tracking/startFreeTimeActivity" method="POST">

			<fieldset data-role="controlgroup">
				<legend>Type:</legend>
				<%
					Student currentStudent = AuthController.getCurrentStudent();
					List<String> freeTimeTypes = StudentController
							.getFreeTimeTypes(currentStudent);
					ListIterator<String> it = freeTimeTypes.listIterator();

					while (it.hasNext()) {
						int typeIndex = it.nextIndex();
						String type = it.next();
						boolean isFirst = (typeIndex == 0);
				%>
				<input type="radio" name="type" id="type-<%=typeIndex%>"
					value="<%=type%>" <%if (isFirst) {%> checked="checked" <%}%> /> <label
					for="type-<%=typeIndex%>"><%=type%></label>
				<%
					}
				%>
				<input type="radio" name="type" id="type-other" value="other" />
				<label for="type-other">Other:</label>
				<input type="text" name="type-other" id="type-other-input" value="" />
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