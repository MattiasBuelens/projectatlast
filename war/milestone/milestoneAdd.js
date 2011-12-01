(function($) {
	$("#addMilestone").live("pageinit", function() {
		pageLoad();

		$("#activity-type-study, #activity-type-freeTime").click(function() {
			createLayout();
		});
		$("#parsefield").change(function(){
			setGoalUnit();
		});
		$("select, input").change(function() {
			createSentence();
		});
		$("#start-date").change(function(){
			calendarStart();
		});
		$("#stop-date").change(function(){
			calendarStop();
		});
	});

	function pageLoad() {
		createLayout();
		createSentence();
		setGoalUnit();
	}

	function getFormType() {
		return $("input[type='radio'][name='activity-type']:checked").val();
	}

	function createLayout() {
		var activityType = getFormType();
		if (activityType == "study") {
			createStudyLayout();
		} else {
			createFreeTimeLayout();
		}
	}

	function createSentence() {
		var activityType = getFormType(), sentence = "";
		if (activityType == "study") {
			sentence = createStudySentence();
		} else {
			sentence = createFreeTimeSentence();
		}

		sentence += getReadableValue($("#parser")) + " ";
		sentence += getReadableValue($("#parsefield")) + " ";
		sentence += getReadableValue($("#operator")) + " ";
		sentence += getReadableValue($("#goal")) + " ";
		sentence += getGoalUnit() + " ";
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
		var startDate = $("#start-date").data("datebox").theDate,
			stopDate = $("#stop-date").data("datebox").theDate;
		
		var sentence = "";
		sentence += "between "
		sentence += startDate.toDateString();
		sentence += " and "
		sentence += stopDate.toDateString();
		
		return sentence;
	}

	function getReadableValue($elem) {
		if (!$elem || !$elem.length)
			return "";
		var $option = null, readable = "";
		if ($option = getSelected($elem)) {
			// Select
			readable = $option.data("readable") || $option.val();
		} else {
			// Input
			readable = $elem.val();
		}
		return readable;
	}
	
	function getSelected($elem) {
		var elem = $elem[0];
		if (elem.options) {
			return $(elem.options[elem.selectedIndex]);
		}
		return null;
	}

	function createStudyLayout() {
		$("#page-form").show();
		$("#study-fields").show();
	}

	function createFreeTimeLayout() {
		$("#page-form").show();
		$("#study-fields").hide();
	}

	function getGoalUnit() {
		var $option = getSelected($("#parsefield"));
		return $option.data("unit") || "";
	}

	function setGoalUnit() {
		$("#goal-unit").html(getGoalUnit());
	}

	function calendarStart() {
		var now = new Date(),
			diff = Math.floor((now - $("#start-date").data("datebox").theDate)
				/ (1000 * 60 * 60 * 24));
		$("#stop-date").data("datebox").options.minDays = diff;
	}

	function calendarStop() {
		var now = new Date(),
			diff = Math.floor(($("#stop-date").data("datebox").theDate - now)
				/ (1000 * 60 * 60 * 24));
		diff++;
		$("#start-date").data("datebox").options.maxDays = diff;
	}
})(jQuery);