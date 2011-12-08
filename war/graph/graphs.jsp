<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/includes/header.jsp"%>
<%@ page import="projectatlast.student.*"%>
<%@ page import="projectatlast.data.*"%>
<%@ page import="projectatlast.query.*"%>
<%@ page import="projectatlast.graph.*"%>
<%@ page import="projectatlast.tracking.*"%>
<%@ page import="java.util.*"%>
<%
	Student student = AuthController.getCurrentStudent();
%>

<div id="list-graphs" data-role="page" data-url="/graph/graphs.jsp">

<style type="text/css">
.graph {
	width: 100%;
	height: 500px;
	margin-bottom: 80px;
}
</style>
	<script src="js/highcharts-android.js" type="text/javascript" ></script>
	<script src="js/graphs/request.js" type="text/javascript"></script>
	<script src="js/graphs/graphs.js" type="text/javascript"></script>
	<script src="js/jquery.viewport.js" type="text/javascript"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			scrollTo(<%=request.getParameter("scrollto")%>);
		});
		
		

		
	</script>

	<div data-role="header" data-position="fixed">
		<a href="/home" data-role="button" data-direction="reverse"
			data-icon="home" data-iconpos="notext">Home</a>
		<h1>Statistics</h1>
		<a id="createGraph" data-role="button" data-theme="b" data-icon="plus"
			href="/graph/create.jsp" data-rel="dialog">Create Graph</a>
	</div>
	<!-- /header -->

	<div data-role="content" data-theme="b">
		<div id="graphs">
			<%
				List<Graph> graphs = GraphController.getAllFromStudent(student);
				ListIterator<Graph> it = graphs.listIterator();
				while (it.hasNext()) {
					int graphIndex = it.nextIndex();
					Graph graph = it.next();
					boolean isStacked = (graph instanceof StackedGraph);
			%>
			<!--  Create graph containers + loader image -->
			<div id="<%=graph.getId()%>" class="graph" data-nr="<%=graphIndex%>"
				data-stacked="<%=isStacked%>">
				<img src="img/ajaxloader.gif" />
			</div>
			<%
				}
			%>
		</div>
		<!-- /graphs -->
	</div>
	<!-- /content -->

	<div id="navbar" data-role="footer" data-theme="b"
		data-position="fixed">
		<div data-role="navbar">
			<ul>
				<li><a id="prevGraph" data-role="button" data-icon="arrow-l">Previous</a>
				</li>
		
				<li><a id="deleteGraph" data-icon="delete" data-role="button"
					href="#delete" data-rel="dialog">Delete</a></li>
				<!-- 
				<li><a href="#quickscroll"
					data-rel="dialog" data-role="button" data-icon="grid"
					data-transition="pop">QuickScroll</a>
				</li>-->
				<li><a id="nextGraph" data-role="button" data-icon="arrow-r"
					data-iconpos="right">Next</a></li>
			</ul>
		</div>
	</div>
	<!-- /navbar -->

	<div data-role="footer" data-theme="c">
		<%@ include file="/includes/copyright.jsp"%>
	</div>

</div>

<!-- <div data-role="page" id="quickscroll">
	<div data-role="header">
		<h1>QuickScroll</h1>
	</div>

	<div data-role="content" data-theme="c">
		<p>Interesting stuff goes here.</p>
	</div>
</div>-->

<!-- DELETE PAGE -->
<div data-role="page" id="delete">
	<div data-role="header">
		<h1>Delete graph</h1>
	</div>

	<div data-role="content" data-theme="c">
		<p>Are you sure you want to delete this graph?</p>
		<form method="post" action="/graph/delete" data-ajax="false">
			<input type="hidden" id="delid" name="delid" value="0" />
			<button id="sure" type="submit" data-theme="b" name="submit">Yes, delete it.</button>
			<a data-role="button" data-rel="back" data-direction="reverse">No, keep it.</a>
		</form>
	</div>
</div>

</body>
</html>