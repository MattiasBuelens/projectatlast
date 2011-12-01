<%@ include file="/includes/header.jsp"%>

<script type="text/javascript">
	$("document").ready(function() {
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

.milestone-bar, .milestone-marker {
	position: absolute;
	height: 30px;
	z-index: 1;
	color: #333333;
	text-align: center;
}

.milestone-marker {
	width: 0;
}

.milestone-bar {
	border: 0;
	text-shadow: 0 1px 0 #FFFFFF;
}

.milestone strong,.milestone span {
	display: block;
	position: absolute;
	left: 0;
	margin: 10px 0 5px -5em;
	z-index: 1;
	width: 10em;
	height: 1.5em;
	text-align: center;
}

.milestone strong {
	bottom: 30px;
}

.milestone span {
	top: 30px;
}

.milestone-handle {
	display: block;
	position: relative;
	left: 50%;
	top: 0;
	width: 40px;
	height: 40px;
	z-index: -1;
	margin: -5px 0 0 -20px;
	-moz-border-radius: 2em;
	-webkit-border-radius: 2em;
	border-radius: 2em;
}

.milestone-bar-right {
	right: 0;
	-moz-border-radius-topright: 1em;
	-webkit-border-top-right-radius: 1em;
	border-top-right-radius: 1em;
	-moz-border-radius-bottomright: 1em;
	-webkit-border-bottom-right-radius: 1em;
	border-bottom-right-radius: 1em;
}

.milestone-bar-left {
	left: 0;
	-moz-border-radius-topleft: 1em;
	-webkit-border-top-left-radius: 1em;
	border-top-left-radius: 1em;
	-moz-border-radius-bottomleft: 1em;
	-webkit-border-bottom-left-radius: 1em;
	border-bottom-left-radius: 1em;
}
</style>

<body>

	<div data-role="page">
		<div data-role="header">
			<h1>Milestones</h1>
		</div>
		<div data-role="content">
			<p>Study analysis for 12 hours this week.</p>
			<div class="milestone ui-btn-down-c ui-btn-corner-all">
				<div class="milestone-marker ui-btn-down-e" style="left: 25%">
					<strong>Start</strong> <span>2 uur</span>
					<div class="milestone-handle ui-btn-up-c ui-btn-corner-all"></div>
				</div>
				<div class="milestone-marker ui-btn-down-e" style="left: 40%">
					<strong>Current</strong> <span>3 uur</span>
					<div class="milestone-handle ui-btn-up-c ui-btn-corner-all"></div>
				</div>
				<div class="milestone-bar milestone-bar-right ui-btn-down-e"
					style="width: 30%">
					<strong>Goal</strong> <span>5 uur</span>
				</div>
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