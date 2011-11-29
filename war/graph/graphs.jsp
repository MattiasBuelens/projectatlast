<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/includes/header.jsp"%>
<%@ page import="com.google.appengine.api.users.UserServiceFactory"%>
<%@ page import="projectatlast.student.*"%>
<%@ page import="projectatlast.data.*"%>
<%@ page import="projectatlast.query.*"%>
<%@ page import="projectatlast.graph.*"%>
<%@ page import="projectatlast.tracking.*"%>
<%@ page import="java.util.*"%>

<style>
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

<script type="text/javascript" >
$(document).ready(function(){
	
	scrollTo(<%=request.getParameter("scrollto")%>);
});
</script>

<div id="page-graph" data-role="page" class="type-interior">

	<div data-role="header">
		<h1>Graphs</h1>
	</div>
	<div id="navbar" data-role="header" data-position="fixed"
		data-theme="b" style="text-align: center;">
		<a href="#quickscroll" id="listGraphs" data-rel="dialog"
			data-role="button" data-icon="grid" data-iconpos="notext"
			data-theme="b" data-inline="true" style="float: left; opacity: 1">QuickScroll</a>
		

			
		<div id="prevGraph" data-role="button" style="margin-right: 30px;">Previous</div>
		
		<div style="margin-right:30px;">
		<a data-icon="plus" 
			href="/graph/create.jsp" id="createGraph" data-role="button"
		data-rel="dialog">Create Graph</a>
		</div>

		
		<div id="nextGraph" data-role="button">Next</div>
		
			
		<div id="delGraph" data-icon="delete" data-iconpos="notext" data-theme="b"
			data-inline="true" style="opacity: 1;float:right;"
			data-role="button">Delete Current Graph</div>	
		
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
			<div id="<%=graph.getId()%>" class="graph" data-nr="<%=count%>"><img src="img/ajaxloader.gif" /></div>

			<%
	
				count++;
				}
			%>


			<!-- END OF WIDGETS -->
		</div>
		<!-- END OF CONTENT -->


	</div>
















</div>
<script src="js/graphs/graphs.js"></script>
<div id="quickscroll" data-role="page">test</div>
