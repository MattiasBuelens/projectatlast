<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="projectatlast.tracking.ActivityController"%>
<%@ page import="java.util.*"%>
<%@ include file="/includes/header.jsp"%>

<div data-role="page">
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
					Map<String, String> types = ActivityController.getFreeTimeTypes();
					boolean isFirst = true;
					for (Map.Entry<String, String> entry : types.entrySet()) {
						String typeId = entry.getKey();
						String typeName = entry.getValue();
				%>
				<input type="radio" name="type" id="type-<%=typeId%>"
					value="<%=typeId%>" <%if (isFirst) {%> checked="checked" <%}%> />
				<label for="type-<%=typeId%>"><%=typeName%></label>
				<%
					isFirst = false;
					}
				%>
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