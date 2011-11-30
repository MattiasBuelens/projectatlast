<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/includes/header.jsp"%>

<div id="confirmStop" data-role="page">
	<div data-role="header">
		<a href="/home" data-role="button" data-rel="back" data-icon="home"
			data-iconpos="notext">Home</a>
		<h1>Stop Activity</h1>
	</div>

	<div data-role="content" data-theme="c">
		<p>You cannot start a new activity while there is still another activity running.</p>
		<a data-role="button" data-theme="b" href="/tracking/stopActivity">Stop activity</a>
		<a data-role="button" data-theme="a" href="/tracking/cancelActivity">Cancel activity</a>
		<a data-role="button" href="/home" data-rel="back">Do nothing</a>
	</div>
	
	<div data-role="footer" data-theme="c">
		<%@ include file="/includes/copyright.jsp"%>
	</div>
</div>

</body>
</html>