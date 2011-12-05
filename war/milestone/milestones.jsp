<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/includes/header.jsp"%>
<%@ page import="projectatlast.data.Registry"%>
<%@ page import="projectatlast.student.*"%>
<%@ page import="projectatlast.milestone.*"%>
<%@ page import="com.googlecode.objectify.*"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List" %>
<link rel="stylesheet" href="/css/milestone.css" type="text/css">
<div data-role="page">

	<div data-role="header">
		<a href="/home" data-role="button" data-direction="reverse"
			data-icon="home" data-iconpos="notext">Home</a>
		<h1>Milestone Menu</h1>
	</div>
	<!-- data-role="header" -->

	<div data-role="content">
	
	<%
	Student cu = AuthController.getCurrentStudent();
	Registry.milestoneFinder().updateCompletion(cu);
	
	//Splits the milestones in current, completed and past.
	
	List<Milestone> current	 	= Registry.milestoneFinder().getRunningMilestones(cu);
	List<Milestone> succes    	= Registry.milestoneFinder().getCompletedMilestones(cu);
	List<Milestone> failed      = Registry.milestoneFinder().getFailedMilestones(cu);
	
	%>	

	<div data-role="collapsible" data-theme="a">
		<h1>Running milestones</h1>
		<%
			for(Milestone milestone: current){
				%><%@ include file="/milestone/includes/milestone.jsp"%><%
			}
		%>
	</div>
	
	<div data-role="collapsible" data-theme="a">
		<h1>Completed milestones</h1>
		<%
			for(Milestone milestone: succes){
				%><%@ include file="/milestone/includes/milestone.jsp"%><%
			}
		%>
	</div>
	
	<div data-role="collapsible" data-theme="a">
			<h1>Failed milestones</h1>
			<%
			for(Milestone milestone: failed){
				%><%@ include file="/milestone/includes/milestone.jsp"%><%
			}
		%>
	</div>

	<div data-role="footer" data-theme="c">
		<%@ include file="/includes/copyright.jsp"%>
	</div>
</div>
</body>
</html>