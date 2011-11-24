function createSentence() {

	var sentence = "I want to ";

	sentence += getReadableValue($("#type")) + " ";
	sentence += getReadableValue($("#course")) + " ";
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