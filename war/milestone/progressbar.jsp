<%@ include file="/includes/header.jsp"%>

<script type="text/javascript">
$("document").ready(function(){
	/*var arg1 = {
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
	drawMilestone(arg2);*/
});	


</script>

<style type="text/css">
.milestone {
	position: relative;
	margin: 2em 2% 2em;
	height: 30px;
}

.milestone-progress, .milestone-goal {
	position: absolute;
	height: 30px;
	text-indent: -9999em;
	z-index: 0;
	border: 0;
	color: #333333;
    text-shadow: 0 1px 0 #FFFFFF;
}

.milestone-progress {
	left: 0;
}

.milestone-goal {
	right: 0;
}

.milestone-goal::before, .milestone-progress::before {
	display: block;
	position: absolute;
	bottom: 30px;
	z-index: 1;
	margin: 5px;
	content: attr(title);
	width: 100%;
	height: 1.5em;
	text-align: center;
	text-indent: 0;
}

.milestone-progress::before {
	right: 0;
	margin-right: -50%;
}

.milestone-goal::before {
	left: 0;
	margin-left: -50%;
}

.milestone-handle {
	display: block;
	position: absolute;
	top: 0;
	margin-top: -5px;
	width: 40px;
	height: 40px;
	font-size: 2em;
	text-indent: -9999em;
	z-index: -1;
}

.milestone-progress .milestone-handle {
	right: 0;
	margin-right: -20px;
}

.milestone-goal .milestone-handle {
	left: 0;
	margin-left: -20px;
}
</style>

<body>

	<div data-role="page">
		<div data-role="header">
			<h1>Milestones</h1>
		</div>
		<div data-role="content">
			Study analysis for 12 hours this week.
				<div class="milestone ui-btn-down-c ui-btn-corner-all">
					<div class="milestone-progress ui-btn-up-b ui-btn-corner-left"
						style="width: 40%" title="Current">
						<div class="milestone-handle ui-btn-up-c ui-btn-corner-all">40%</div>
					</div>
					<div class="milestone-goal ui-btn-down-e ui-btn-corner-right"
						style="width: 30%" title="Goal">70%</div>
				</div>
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