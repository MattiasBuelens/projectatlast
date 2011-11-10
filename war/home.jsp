<%@ include file="/parts/header.jsp"%>
<div id="home" data-role="page">

	<div data-role="header">
		<a data-role="button" href="/authentication/logoutConfirmation.jsp"
					data-rel="dialog" data-transition="pop">Log Out</a>
		<h1>Home</h1>
	</div>
	<!-- /header -->

	<div data-role="content">

		<div>
			<p>
				<a data-role="button" href="/tracking/startTracking.jsp"
					data-rel="dialog" data-transition="pop">Start</a>
			</p>
		</div>

		<fieldset class="ui-grid-a">
			<div class="ui-block-a">Graphs needs to be here</div>
			<div class="ui-block-b">Milestones needs to be here</div>

		</fieldset>

	</div>
	<!-- /content -->

	<%@ include file="/parts/footer.jsp"%>
</div>
<!-- /page -->

</body>
</html>