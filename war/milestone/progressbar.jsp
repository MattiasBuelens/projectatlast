<%@ include file="/includes/header.jsp" %>

<script type="text/javascript">
$("document").ready(function(){
	var arg1 = {
			id: 					"milestone1",
			goalValue: 					20,
			operator: 				"greater",
			currentValue: 			40,
			startValue: 			80,
			startTime:   			1000000,
			currentTime: 			1015420,
			stopTime:    			1054230,  
	};
	
	var arg2 = {
			id: "milestone2",
			goalValue: 100,
			operator: "lesser",
			currentValue: 20,
			startValue: 0,
			startTime: 0,
			stopTime:  40,
			currentTime: 20
	};
	
	drawMilestone(arg1);
	drawMilestone(arg2);
});	


</script>


<body>

<div data-role="page">
		<div data-role="header"> <h1>Milestones</h1> </div>
		<div data-role="content">
			Study analysis for 12 hours this week.
			<div id="milestone1">&nbsp;</div>
			Study algebra till you die
			<div id="milestone2">&nbsp;</div>
		</div>
		<div data-role="footer">
<%@ include file="/includes/copyright.jsp"%>
</div>
</div>
</body>
</html>