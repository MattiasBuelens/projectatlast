<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="projectatlast.tracking.ActivityController"%>
<%@ page import="projectatlast.student.*"%>
<%@ page import="java.util.*"%>
<%@ include file="/includes/header.jsp"%>

<div id="newFreeTimeActivity" data-role="page">
<script type="text/javascript">
	// TODO Make sure this only works on this page
	$("#newFreeTimeActivity").bind("pageinit", function() {
		$("#extraFTAct").change(function(){
			$("input[type='radio'][name='type']").prop("checked",false).checkboxradio("refresh");
		});
		$("input[type='radio'][name='type']").change(function(){
			$("#extraFTAct").val("");
		});
	});
</script>

	<div data-role="header">
		<a href="/home" data-role="button" data-direction="reverse" data-icon="home"
			data-iconpos="notext">Home</a>
		<h1>Start Free Time</h1>
	</div>

	<div data-role="content">
		<form action="/tracking/startFreeTimeActivity" method="POST">
			
			<fieldset data-role="controlgroup">
				<legend>Type:</legend>
				<%
					Student currentStudent = AuthController.getCurrentStudent();
					List<String> fTActs = StudentController.getFTActs(currentStudent);
					ListIterator<String> it = fTActs.listIterator();
					boolean isFirst = true;
					
					while (it.hasNext()) {
						int fTActIndex = it.nextIndex();
						String fTAct = it.next();
				%>
				<input type="radio" name="type" id="type-<%=fTActIndex%>"
					value="<%=fTActIndex%>" <%if (isFirst) {%> checked="checked" <%}%> /> 
				<label for="type-<%=fTActIndex%>"><%=fTAct%></label>
				<%
					isFirst = false;
					}
				%>
				<label for="basic">Other:</label>
    			<input type="text" name="type" id="extraFTAct" value=""  />
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