function createSentence() {
	var activityType = $("input[type='radio'][name='activity-type']:checked").val();
	
	if(activityType == "study"){
		createStudySentence();
	}
	else{
		createFreeTimeSentence();
	}
}

function createStudySentence() {

	var sentence = "I want to ";

	sentence += getReadableValue($("#type")) + " ";
	sentence += getReadableValue($("#course")) + " ";
	sentence += getReadableValue($("#parser")) + " ";
	sentence += getReadableValue($("#parsefield")) + " ";
	sentence += getReadableValue($("#operator")) + " ";
	sentence += getReadableValue($("#goal")) + ".";

	$(".milestoneString").val(sentence);

}

function createFreeTimeSentence() {
	
	var sentence = "I want to ";
	
	sentence += getReadableValue($("#parser")) + " ";
	sentence += getReadableValue($("#parseField")) + " ";
	sentence += getReadableValue($("#operator")) + " ";

	$(".milestoneString").val(sentence);
}

function getReadableValue($elem) {
	if(!$elem || !$elem.length) return "";
	var elem = $elem[0], readable = "";
	if(elem.options) {
		var option = elem.options[elem.selectedIndex],
			$option = $(option);
		readable = $option.data("readable") || $option.val();
	} else {
		readable = $elem.val();
	}
	return readable.toLowerCase();
}

function createStudyLayout(){
	$("#page-form").show();
	$("#study-fields").show();
}

function createFreeTimeLayout(){
	$("#page-form").show();
	$("#study-fields").hide();
}

function calendarStart(){
    var temp = new Date(),
    
    diff = Math.floor((temp - $('#start-date').data('datebox').theDate) / ( 1000 * 60 * 60 * 24 ));
    $('#stop-date').data('datebox').options.minDays = diff;
}

function calendarStop(){
    var temp = new Date(),
    
    diff = Math.floor(($('#stop-date').data('datebox').theDate - temp) / ( 1000 * 60 * 60 * 24 ));
    diff++;
    $('#start-date').data('datebox').options.maxDays = diff;
}