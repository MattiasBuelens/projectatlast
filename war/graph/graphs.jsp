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
	List<Graph> graphs = GraphController.getAllFromStudent(student);
	long currentGraph = 0;
	try {
		currentGraph = Long.parseLong(request.getParameter("showgraph"));
	} catch(NumberFormatException e) { }
%>

<div id="graph-list" data-role="page" data-url="/graph/graphs.jsp">
	<div data-role="header" data-position="fixed">
		<a href="/home" data-role="button" data-direction="reverse"
			data-icon="home" data-iconpos="notext">Home</a>
		<h1>Statistics</h1>
		<a id="createGraph" data-role="button" data-theme="b" data-icon="plus"
			href="/graph/create.jsp" data-rel="dialog">Add</a>
	</div>
	<!-- /header -->

	<div data-role="content" data-theme="b">
		<div class="graphs">
			<%
				ListIterator<Graph> it = graphs.listIterator();
				while (it.hasNext()) {
					int graphIndex = it.nextIndex();
					Graph graph = it.next();
					boolean isStacked = (graph instanceof StackedGraph);
					boolean isCurrent = (graph.getId().equals(currentGraph));
			%>
			<div id="<%=graph.getId()%>" class="graph<%=isCurrent?" current-graph":""%>"
				data-stacked="<%=isStacked%>">
				<span class="graph-loading">Loading graph&hellip;</span>
			</div>
			<%
				}
			%>
		</div>
		<!-- /graphs -->
	</div>
	<!-- /content -->

	<div data-role="footer" data-theme="c">
		<%@ include file="/includes/copyright.jsp"%>
	</div>
	<!-- /footer -->

	<div id="navbar" data-role="footer" data-theme="b" data-position="fixed">
		<div data-role="navbar">
			<ul>
				<li><a class="prevGraph" data-role="button" data-icon="arrow-l">Previous</a>
				</li>
		
				<li><a class="deleteGraph" data-icon="delete" data-role="button"
					href="#graph-delete" data-rel="dialog">Delete</a></li>
				<li><a class="nextGraph" data-role="button" data-icon="arrow-r"
					data-iconpos="right">Next</a></li>
			</ul>
		</div>
	</div>
	<!-- /navbar -->
</div>

<!-- Delete graph -->
<div data-role="page" id="graph-delete">
	<div data-role="header">
		<h1>Delete graph</h1>
	</div>

	<div data-role="content" data-theme="c">
		<p>Are you sure you want to delete this graph?</p>
		<form method="post" action="/graph/delete" data-ajax="false">
			<input type="hidden" name="deleteId" value="0" />
			<button id="sure" type="submit" data-theme="b" name="submit">Yes, delete it.</button>
			<a data-role="button" data-rel="back" data-direction="reverse">No, keep it.</a>
		</form>
	</div>
</div>

</body>
</html>