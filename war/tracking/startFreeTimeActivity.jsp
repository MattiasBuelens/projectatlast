<%@ include file="/parts/header.jsp"%>

<div data-role="page">
	<div data-role="header">
		<h1>Start Freetime</h1>
	</div>

	<div data-role="content">
		<form action="/createActivity" method="GET">
			<input type="hidden" name="activityType" value="freetime" />

			<fieldset data-role="controlgroup">
				<legend>Type:</legend>

				<input type="radio" name="type" id="type-gaming" value="gaming" />
				<label for="type-gaming">Gaming</label>

				<input type="radio" name="type" id="type-sports" value="sports" />
				<label for="type-sports">Sports</label>

				<input type="radio" name="type" id="type-bar" value="bar" />
				<label for="type-bar">Bar</label>
			</fieldset>

			<button type="submit">GO!</button>
		</form>
	</div>
	<%@ include file="/parts/footer.jsp"%>
</div>

</body>
</html>