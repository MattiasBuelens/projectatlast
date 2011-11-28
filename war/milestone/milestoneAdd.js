function createSentence() {
	var activityType = $("input[type='radio'][name='activity-type']:selected").val();
	
	if(activityType == "activity-type-study"){
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
	sentence += getReadableValue($("#parseField")) + " ";
	sentence += getReadableValue($("#operator")) + " ";

	$(".milestoneString").val(sentence);

}

function createFreeTimeSentence() {
	
	var sentence = "I want to ";
	
	sentence += getReadableValue($("#parser")) + " ";
	sentence += getReadableValue($("#parseField")) + " ";
	sentence += getReadableValue($("#operator")) + " ";

	$(".milestoneString").val(sentence);
}

function getReadableValue($select) {
	if(!$select || !$select.length) return "";
	var select = $select[0],
		option = select.options[select.selectedIndex],
		$option = $(option);
	var readable = $option.data("readable") || $option.val();
	return readable;
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
		
	    $('#stop-date').val($('#start-date').val());
	    var temp = new Date(),
	        diff = parseInt(($('#start-date').data('datebox').theDate - temp) / ( 1000 * 60 * 60 * 24 ));
	        diffstrt = (diff * -1)-1; // If you want a minimum of 1 day between, make this -2 instead of -1

	    $('#stop-date').data('datebox').options.minDays = diffstrt;

}

function calendarStart(){
	
    $('#stop-date').val($('#start-date').val());
    var temp = new Date(),
        diff = parseInt(($('#start-date').data('datebox').theDate - temp) / ( 1000 * 60 * 60 * 24 ));

    $('#stop-date').data('datebox').options.minDays = diff;

}

function calendarStop(){
	
    var temp = new Date(),
    
    diff = parseInt(($('#stop-date').data('datebox').theDate - temp) / ( 1000 * 60 * 60 * 24 ));
    $('#start-date').data('datebox').options.maxDays = diff;

}