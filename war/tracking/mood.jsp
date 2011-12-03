<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/includes/header.jsp"%>
<%@ page import="projectatlast.student.*"%>
<%@ page import="projectatlast.tracking.*"%>
<%
	Student student = AuthController.getCurrentStudent();
%>

<div data-role="page">
	<div data-role="header">
		<a href="/home" data-role="button" data-direction="reverse"
			data-icon="home" data-iconpos="notext">Home</a>
		<h1>Mood</h1>
	</div>

	<div data-role="content">
		<div data-role="pointpicker">
			<div data-role="fieldcontain">
				<label for="mood-interest">Interest</label>
				<input type="range" id="mood-interest" name="mood-interest"
					data-role="pointpicker-x" min="0" max="100" step="1" value="0" />
			</div>
			<div data-role="fieldcontain">
				<label for="mood-comprehension">Comprehension</label>
				<input type="range" id="mood-comprehension" name="mood-comprehension"
					data-role="pointpicker-y" min="0" max="100" step="1" value="0" />
			</div>
			<div class="mood-picker" data-role="pointpicker-area"></div>
		</div>
	</div>

	<div data-role="footer" data-theme="c">
		<%@ include file="/includes/copyright.jsp"%>
	</div>
</div>
</body>
</html>