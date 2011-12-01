<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/includes/header.jsp"%>
<%@ page import="projectatlast.student.*"%>
<%@ page import="projectatlast.data.*"%>
<%@ page import="projectatlast.query.*"%>
<%@ page import="projectatlast.graph.*"%>
<%@ page import="projectatlast.tracking.*"%>
<%@ page import="java.util.*"%>
<style type="text/css">
.graph {
	width: 100%;
	height: 500px;
	margin-bottom: 20px;
}
</style>

<script src="js/highcharts.js" type="text/javascript"></script>
<script src="js/graphs/swipe.js" type="text/javascript"></script>
<script src="js/graphs/scroll.js" type="text/javascript"></script>
<script src="js/graphs/request.js" type="text/javascript"></script>
<script src="js/graphs/graphs.js" type="text/javascript" ></script>
<script src="js/jquery.viewport.js" type="text/javascript"></script>

<script type="text/javascript" >
$(document).ready(function() {
	scrollTo(<%=request.getParameter("scrollto")%>);
});
</script>

<div id="list-graphs" data-role="page" data-url="/graph/graphs.jsp">



	<div data-role="header">
		<a href="/home" data-role="button" data-direction="reverse" data-icon="home"
			data-iconpos="notext">Home</a>
		<h1>Statistics</h1>
		<a id="createGraph" class="ui-btn-right" data-theme="b"
			data-role="button" data-icon="plus" href="/graph/create.jsp"
			data-rel="dialog">Create Graph</a>
	</div>

	<div id="navbar" class="ui-header ui-bar-b ui-grid-b" data-theme="b" data-position="fixed">
		<div class="ui-bar-small">
			<div class="ui-block-a align-left">
				<a id="prevGraph" data-role="button" data-icon="arrow-l">Previous</a>
			</div>

			<div class="ui-block-b align-center">
				<a id="editGraph" data-icon="refresh" data-role="button">Edit</a>
				<a id="deleteGraph" data-icon="delete" data-role="button" href="#delete" data-rel="dialog">Delete</a>
				<a href="#quickscroll" data-rel="dialog"
					data-role="button" data-icon="grid" data-transition="pop">QuickScroll</a>
			</div>

			<div class="ui-block-c align-right">
				<a id="nextGraph" data-role="button" data-icon="arrow-r"
					data-iconpos="right">Next</a>
			</div>
		</div>
	</div>
	<div data-role="content" data-theme="b">
		<div id="graphs">

			<%
				ArrayList<Graph> graphs = new ArrayList<Graph>(Registry
						.graphFinder()
						.getGraphs(AuthController.getCurrentStudent()));
				int count = 0;
				for (Graph graph : graphs) {
			%>
			<!--  Create graph containers + loader image -->
			<div id="<%=graph.getId()%>" class="graph" data-nr="<%=count%>" data-stacked="<%=graph instanceof StackedGraph%>"><img src="img/ajaxloader.gif" /></div>

			<%
				count++;
				}
			%>


			<!-- END OF WIDGETS -->
		</div>
		<!-- END OF CONTENT -->
	</div>

	<div data-role="footer" data-theme="c">
		<%@ include file="/includes/copyright.jsp"%>
	</div>

</div>

<div data-role="page" id="quickscroll">
	<div data-role="header">
		<h1>QuickScroll</h1>
	</div>

	<div data-role="content" data-theme="c">
		<p>Interesting stuff goes here.</p>
	</div>
</div>

<!-- DELETE PAGE -->
<div data-role="page" id="delete">
	<div data-role="header">
		<h1>Delete graph: Are you sure?</h1>
	</div>

	<div data-role="content" data-theme="c">
	<form method="post" action="delete" >
		<input type="hidden" id="delid" name="delid" value="0"/>
		<button  id="sure" type="submit" data-theme="b" name="submit"
				value="submit-value">Yes! Remove the goddamn thing.</button>

	</form>

		<div id="nope" data-role="button" data-direction="reverse">Nope, I changed my mind.</div>
	</div>
</div>


</body>
</html>