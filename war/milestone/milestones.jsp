<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/includes/header.jsp"%>
<%@ page import="projectatlast.data.Registry"%>
<%@ page import="projectatlast.student.*"%>
<%@ page import="projectatlast.milestone.*"%>
<%@ page import="projectatlast.tracking.*"%>
<%@ page import="com.googlecode.objectify.*"%>
<%@ page import="java.util.List"%>
<%@ page import="java.text.DateFormat"%>
<%
	Student student = AuthController.getCurrentStudent();

	List<Milestone> milestones = MilestoneController
			.getMilestones(student);
	System.out.println("size " + milestones.size());
	DateFormat dateFormat = DateFormat.getDateTimeInstance(
			DateFormat.LONG, DateFormat.MEDIUM);
%>
<style type="text/css">

.priceRangeInfo label                            /* moves label field */
.priceRangeInfo #buying_slider_min       /* moves first input field */ 
.priceRangeInfo #buying_slider_max      /* move second input field */ 
.priceRangeInfo div.ui-slider                   /* move both sliders - adressing 1st slider with CSS is hard */ 
.priceRangeInfo div:last-child                 /* correct 2nd slider position to fit exactly on top of 1st slider */

</style>
<script src="http://jqueryui.com/ui/jquery.ui.progressbar.js" type="text/javascript" ></script>
<link rel="stylesheet" type="text/css" href="http://jqueryui.com/themes/base/jquery.ui.all.css" />
	<script src="/themeroller/themeswitchertool/" type="text/javascript"></script>

<div data-role="page">
	<div data-role="header">
		<a href="/home" data-role="button" data-rel="back" data-icon="home"
			data-iconpos="notext">Home</a>
		<h1>List Milestones</h1>
	</div>

	<div data-role="content">
		<div class="content-primary">
			<ul data-role="listview">
				<%
					for (Milestone milestone : milestones) {
				%>
				<li>
					<h3>milestone</h3>
					<dl>
						<%
							if (milestone.getStartDate() != null) {
						%>
						<dt>From</dt>
						<dd><%=dateFormat.format(milestone.getStartDate())%></dd>
						<%
							}
						%>
						<%
							if (milestone.getDeadline() != null) {
						%>
						<dt>To</dt>
						<dd><%=dateFormat.format(milestone.getDeadline())%></dd>
						<%
							}
						%>
					</dl>
					<div data-role="fieldcontain">
						<label for="slider"></label> 
							<div class="progressbar" id="slider"></div>
					</div>
						
				</li>
				<%
					}
				%>
			</ul>
		</div>
	</div>
						
<div class="priceRangeInfo">
      <label for="buying_slider_min">Price</label>
      <input type="range" name="buying_slider_min" id="buying_slider_min" class="minBuyingSlider" value="0" min="0" max="100" />
      <input type="range" name="buying_slider_max" id="buying_slider_max" class="maxBuyingSlider" value="100" min="0" max="100" />
</div>
		

<meta charset="utf-8">
	
	
	
	
	
	
	
	<script>
	$(function() {
		$( ".progressbar" ).progressbar({
			value: 37
		});
	});
	</script>







<div class="demo-description">
<p>Default determinate progress bar.</p>
</div><!-- End demo-description -->



	<div data-role="footer" data-theme="c">
		<%@ include file="/includes/copyright.jsp"%>
	</div>
</div>
</body>
</html>