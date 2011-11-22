<html>
<head>
<link rel="stylesheet" href="http://code.jquery.com/mobile/1.0/jquery.mobile-1.0.min.css" />
	<script type="text/javascript" src="http://code.jquery.com/jquery-1.6.4.min.js"></script>
	<script type="text/javascript" src="http://code.jquery.com/mobile/1.0/jquery.mobile-1.0.min.js"></script>

  <script type="text/javascript"
		src="milestone.js">
</script> 


<script type="text/javascript">
$("document").ready(function(){
	var arg1 = {
			id: 					"milestone1",
			goal: 					20,
			operator: 				"greater",
			currentValue: 			40,
			startValue: 			80,
			startTime:   			1000000,
			currentTime: 			1035420,
			stopTime:    			1054230,  
			description:	"Study analysis averagely 1 hour a day"
	};
	
	var arg2 = {
			id: "milestone2",
			goal: 100,
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


<link rel="stylesheet" href="milestone.css" type="text/css"></link> 
</head>

<body>

<div data-role="page">
		<div data-role="header"> <h1>Milestones</h1> </div>
		<div data-role="content">
			<div id="milestone1">&nbsp;</div>
			<div id="milestone2">&nbsp;</div>
		</div>
		<div data-role="footer"> ProjectAtlast</div>
</div>

</body>



</html>''