<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/includes/header.jsp"%>

<div data-role="page">
	<div data-role="header">
		<a href="/" data-role="button" data-rel="back" data-icon="home"
			data-iconpos="notext">Home</a>
		<h1>Start Freetime</h1>
	</div>

	<div data-role="content">
		<form action="/tracking/startActivity" method="GET">
			<input type="hidden" name="activityType" value="freetime" />

			<fieldset data-role="controlgroup">
				<legend>Type:</legend>

				<input type="radio" name="type" id="type-gaming" value="gaming" checked="checked" />
				<label for="type-gaming">Gaming</label>

				<input type="radio" name="type" id="type-sports" value="sports" />
				<label for="type-sports">Sports</label>

				<input type="radio" name="type" id="type-bar" value="bar" />
				<label for="type-bar">Bar</label>
			</fieldset>

			<button type="submit">Start</button>
		</form>
	</div>

	<%@ include file="/includes/footer.jsp"%>
</div>

</body>
</html>