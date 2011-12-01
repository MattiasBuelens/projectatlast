
	function pageLoad() {
		createLayout();
		createSentence();
		setGoalUnit();
	}

	function checkform() {
		return activityType = $(
				"input[type='radio'][name='activity-type']:checked").val();
	}

	function createSentence() {
		var activityType = checkform();
		var sentence = "";
		if (activityType == "study") {
			sentence = createStudySentence();
		} else {
			sentence = createFreeTimeSentence();
		}

		sentence += getReadableValue($("#parser")) + " ";
		sentence += getReadableValue($("#parsefield")) + " ";
		sentence += getReadableValue($("#operator")) + " ";
		sentence += getReadableValue($("#goal")) + " ";
		sentence += $("#goal-unit").html() + " ";
		sentence += createDateSentence() + ".";

		$(".milestoneString").html(sentence);
	}

	function createStudySentence() {

		var sentence = "I want to ";

		sentence += getReadableValue($("#type")) + " ";
		sentence += getReadableValue($("#course")) + " ";

		sentence += "with ";

		return sentence;

	}

	function createFreeTimeSentence() {

		var sentence = "I want to ";
		return sentence;

	}

	function createDateSentence() {
		var startDate = $('#start-date').data('datebox').theDate;
		var stopDate = $('#stop-date').data('datebox').theDate;
		
		var sentence="";
		sentence += "between "
		sentence += startDate.toDateString();
		sentence += " and "
		sentence += stopDate.toDateString();
		
		return sentence;
	}

	function getReadableValue($elem) {
		if (!$elem || !$elem.length)
			return "";
		var elem = $elem[0], readable = "";
		if (elem.options) {
			var option = elem.options[elem.selectedIndex], $option = $(option);
			readable = $option.data("readable") || $option.val();
		} else {
			readable = $elem.val();
		}
		return readable.toLowerCase();
	}

	function createLayout() {
		var activityType = checkform();

		if (activityType == "study") {
			createStudyLayout();
		} else {
			createFreeTimeLayout();
		}
		createSentence();
	}

	function createStudyLayout() {
		$("#page-form").show();
		$("#study-fields").show();
	}

	function createFreeTimeLayout() {
		$("#page-form").show();
		$("#study-fields").hide();
	}
	// Temporary function to set a usefull unit for the goal.
	function setGoalUnit() {
		var value = $("#parsefield").val();
		if (value == "DURATION") {
			$("#goal-unit").html("hours");
		} else {
			$("#goal-unit").html("");
		}
	}
	

	function calendarStart() {
		var temp = new Date(),

		diff = Math.floor((temp - $('#start-date').data('datebox').theDate)
				/ (1000 * 60 * 60 * 24));
		$('#stop-date').data('datebox').options.minDays = diff;
	}

	function calendarStop() {
		var temp = new Date(),

		diff = Math.floor(($('#stop-date').data('datebox').theDate - temp)
				/ (1000 * 60 * 60 * 24));
		diff++;
		$('#start-date').data('datebox').options.maxDays = diff;
	}