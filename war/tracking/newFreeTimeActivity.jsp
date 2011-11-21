<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/includes/header.jsp"%>

<div data-role="page">
	<div data-role="header">
		<a href="/" data-role="button" data-rel="back" data-icon="home"
			data-iconpos="notext">Home</a>
		<h1>Start Free Time</h1>
	</div>

	<div data-role="content">
		<form action="/tracking/startFreeTimeActivity" method="POST">
			<fieldset data-role="controlgroup">
				<legend>Type:</legend>

				<input type="radio" name="type" id="type-gaming" value="gaming" checked="checked" />
				<label for="type-gaming">Gaming</label>

				<input type="radio" name="type" id="type-sports" value="sports" />
				<label for="type-sports">Sports</label>

				<input type="radio" name="type" id="type-bar" value="bar" />
				<label for="type-bar">Bar</label>
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