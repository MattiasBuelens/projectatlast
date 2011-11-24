/**
 * Author: Erik De Smedt
 */

//The required parameters 
//
//args={
//		id:    			the id of the div where the milestone will be drawn in
//		goalValue:		the goalValue the user must reach
//		currentValue:	the currnet value
//		startValue:		the value from where the user has started
//		
//		currentTime:	A long which represents the current time
//		stopTime:		A long which represents the moment of the deadline
//		startTime:		A long which represents the moment on which the milestone started
//}
//

function drawMilestone(args)
{
	args.id = "#"+args.id;
	
	var milestoneDiv = $(args.id);
	var htmlString = 	'<div class="milestone-advancement ">  \n'
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
	
	var maxValue = 1.10*Math.max(args.goalValue, Math.max(args.currentValue, args.startValue));
	
	//Calculates the required percentage
	var currentPercentage = args.currentValue/maxValue*100;
	var startPercentage   = args.startValue/maxValue*100;
	var goalPercentage    = args.goalValue/maxValue*100; 
	var timePercentage    = (args.currentTime-args.startTime)/(args.stopTime-args.startTime)*100;
	
	leftPart.css(  "width" ,  goalPercentage      + "%");
	rightPart.css( "width" , (100-goalPercentage) + "%");
	
	leftPart.addClass(  "ui-corner-left"  );
	rightPart.addClass( "ui-corner-right" );
	
	
	//colors the green and the red part
	var operator = args.operator.toLowerCase();
	if(operator =="lesser"){
		leftPart.addClass(   "milestone-goal");
		rightPart.addClass(  "milestone-fail");
	}
	
	else if(operator =="greater"){
		rightPart.addClass( "milestone-goal");
		leftPart.addClass(  "milestone-fail");
	}
	
	
	//Places info about the user in the box.
	var htmlString = 	'<a class="milestone-info-user" >You   </a> \n' +
						'<a class="milestone-info-start">Start </a> \n';
	
	infoBox.html(htmlString);
	
	infoBox.children( ".milestone-info-user"   ).css("left" , currentPercentage + "%");
	infoBox.children( ".milestone-info-start"  ).css("left" , startPercentage   + "%");
	
	//Draws little marks in the bar
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
	
	
	//Draws the timer
	//The idea is based on the following blog article: http://atomicnoggin.ca/blog/2010/02/20/pure-css3-pie-charts/
	milestoneTimer.html(
			'<div class="milestone-timer-hold1"> \n'
				+ '<div class="milestone-timer-pie1">&nbsp;</div> \n'
			+'</div> \n'
			+'<div class="milestone-timer-hold2">\n'
				+'<div class="milestone-timer-pie2">&nbsp;</div> \n'
			+'</div> \n'
			+'<div class="milestone-timer-border"> &nbsp; </div>'
	);

	var pie1 = milestoneTimer.children(".milestone-timer-hold1").children(".milestone-timer-pie1");
	var pie2 = milestoneTimer.children(".milestone-timer-hold2").children(".milestone-timer-pie2");

	var angle=timePercentage*3.6;
	
	if(angle<180){
		rotate(pie1,180-angle);
		pie2.hide();
	}
	
	if(angle>180){
		rotate(pie2,360-angle);
	}
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

function rotate(object, angle){
	object.css("-moz-transform",     "rotate(" + -angle + "deg)");
	object.css("-webkit-transform",  "rotate(" + -angle + "deg)");
	object.css("-o-transform",       "rotate(" + -angle + "deg)");
	object.css("transform",          "rotate(" + -angle + "deg)");
}

