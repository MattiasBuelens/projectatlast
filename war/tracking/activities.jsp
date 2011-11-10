<%@page import="projectatlast.data.Registry"%>
<%@page import="projectatlast.student.*"%>
<%@page import="projectatlast.tracking.*"%>
<%@page import="com.googlecode.objectify.*" %>
<%@page import="java.util.List" %>


<div data-role="page">

	<div data-role="header">
		<h1>Start Tracking</h1>
	</div>

	<div data-role="content">
		<div class="content-primary">
			<ul data-role="listview">
			<% 		
			Student student = AuthController.getCurrentStudent();
			Query<Activity>  activities= Registry.dao().ofy().query(Activity.class).filter("student =", student); 
			List<Activity> list = activities.list();
			
			for(Activity a:list){
			%>
			
				<li><a href=""><% out.println(a.getType()); %></a></li>

			<%} %>
			</ul>
		</div>

		<%@ include file="/parts/footer.jsp"%>
	</div>
	</div>
	</body>
	</html>