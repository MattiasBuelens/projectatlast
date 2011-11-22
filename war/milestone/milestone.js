/**
 * drawMilestone adapts the html of a div to create a milestone of it.
 * 
 * example of a set of params
 * 
 * {
 * 		id:			"milestone1",
 * 		operator:	"lesser",
 * 		goal:		20,
 * 		currentpos:	40,
 * 		startpos:	50
 * }
 *   
 */
function drawMilestone(args)
{
	args.id = "#"+args.id;
	
	
	var milestoneDiv = $(args.id);
	var htmlString = 	'<div class="milestone-description">' + args.description + '</div>\n'
						+ '<div class="milestone-advancement ">  \n'
							+'<div class="milestone-left-div   ">  &nbsp; </div> \n'
							+'<div class="milestone-right-div  " > &nbsp; </div> \n'
							+'<div class="milestone-info       " > &nbsp; </div>'
		  				+'</div>\n'
		  				+'<div class="milestone-timer"> &nbsp; </div>';
	
	
	milestoneDiv.html(htmlString);
	milestoneDiv.addClass("milestone");
	
	var advancement    = milestoneDiv.children(".milestone-advancement" );
	var milestoneTimer = milestoneDiv.children(".milestone-timer"       );
	
	var leftPart   = advancement.children(".milestone-left-div"  );
	var rightPart  = advancement.children(".milestone-right-div" );
	var infoBox    = advancement.children(".milestone-info"      );
	
	
	advancement.css( "width", "90%");
	
	var maxValue = 1.10*Math.max(args.goal, Math.max(args.currentValue, args.startValue));
	
	var currentPercentage = args.currentValue/maxValue*100;
	var startPercentage   = args.startValue/maxValue*100;
	var goalPercentage    = args.goal/maxValue*100; 
	var timePercentage    = (args.currentTime-args.startTime)/(args.stopTime-args.startTime)*100;
	
	
	leftPart.css(  "width" ,  goalPercentage      + "%");
	rightPart.css( "width" , (100-goalPercentage) + "%");
	
	leftPart.addClass(  "ui-corner-left"  );
	rightPart.addClass( "ui-corner-right" );
	
	var operator = args.operator.toLowerCase();
	if(operator =="lesser"){
		leftPart.addClass(   "milestone-goal");
		rightPart.addClass(  "milestone-fail");
	}
	
	else if(operator =="greater"){
		rightPart.addClass( "milestone-goal");
		leftPart.addClass(  "milestone-fail");
	}
	
	
	var htmlString = 	'<a class="milestone-info-user" >You   </a> \n' +
						'<a class="milestone-info-start">Start </a> \n';
	
	infoBox.html(htmlString);
	
	infoBox.children( ".milestone-info-user"   ).css("left" , currentPercentage + "%");
	infoBox.children( ".milestone-info-start"  ).css("left" , startPercentage   + "%");
	
	var args_user=
		{
			id				: args.id,
			goalPercentage	: goalPercentage,
			blockPercentage	: currentPercentage,
			blockClass		: "milestone-user"
		}
	
	var args_start=
		{
			id				: args.id,
			goalPercentage	: goalPercentage,
			blockPercentage	: startPercentage,
			blockClass		: "milestone-start"
		}
	

	
	drawBlock(args_user);
	drawBlock(args_start);
	
	var clockNumber = Math.floor(timePercentage/100*16);
	if       (clockNumber>16) clockNumber=16;
	else if  (clockNumber<0)  clockNumber=0;
	
	var clockName = "clock"+clockNumber+".png";
	
	milestoneTimer.html('<img class="milestone-clockimage" src="images/'+clockName+'"alt="clock"/>')
	
	
}

function drawBlock(args){
	var selector = args.id;
	
	var leftPart  = $(selector).children(".milestone-advancement").children(".milestone-left-div" );
	var rightPart = $(selector).children(".milestone-advancement").children(".milestone-right-div");
	
	
	
	var blockPercentage = args.blockPercentage;
	var blockClass		= args.blockClass;
	var goalPercentage	= args.goalPercentage;
	
	
	
	if(blockPercentage>goalPercentage){
		var innerHTML = rightPart.html();
		innerHTML =innerHTML +  '<div class="'+blockClass+'"> &nbsp; </div>\n';
		rightPart.html(innerHTML);
		
		var leftOffset = (blockPercentage-goalPercentage)/(100-goalPercentage)*100;
		rightPart.children("."+blockClass).css("left", leftOffset + "%" );
	}
	
	
	else
	{
		var innerHTML = leftPart.html();
		innerHTML =innerHTML +  '<div class="'+blockClass+'"> &nbsp; </div>\n';
		leftPart.html(innerHTML);
		
		var leftOffset = blockPercentage/goalPercentage*100;
		leftPart.children("."+blockClass).css("left", leftOffset + "%" );	
	}
}
