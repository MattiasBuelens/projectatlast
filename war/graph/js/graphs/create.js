/**
 * Project AtLast Graph package
 */
(function($) {
	/**
	 * Create graph Page: /graph/create.jsp
	 */
	var $page;
	$("#graph-create").live("pageinit", function() {
		$page = $(this);

		$page.find("#stacked").bind("click", function() {
			getControls(".normal, .scatter").hide();
			getControls(".stacked").show();
			if ($page.find("#chart-type-pie").prop("checked")) {
				$page.find("#chart-type-pie").prop("checked",
						false).checkboxradio("refresh");
				$page.find("#chart-type-bar").prop("checked",
						true).checkboxradio("refresh");
			}
			defaultCalculation();
		});

		$page.find("#normal").bind("click", function() {
			getControls(".scatter, .stacked").hide();
			getControls(".normal").show();

			defaultCalculation();
		});

		$page.find("#scatter").bind("click", function() {
			getControls(".normal, .stacked").hide();
			getControls(".scatter").show();

			scatterCalculation();
		});

		$page.find("#extra").bind("click", function() {

				var result;
			
						if($("#dateselector").attr("value") =="true"){
							result = false;
						}else{
							result =  true;
						}
				$("#dateselector").attr("value",result);
			
		});
	}).live("pagebeforeshow", function() {
		$page = $(this);
		// Defaults
		getControls(".stacked,.scatter").hide();
		getControls(".normal").show();
		$page.find("#calculation1").html("Calculation: ");
		$page.find("#calculation2").html("Calculation: ");
	});
	
	function getControls(selector) {
		return $page.find(selector).closest(".ui-select").andSelf();
	}

	function defaultCalculation() {
		$page.find("#calculation1").html("Calculation: ");
		$page.find("#calculation2").html("Calculation: ");
	}
	
	function scatterCalculation() {
		$page.find("#calculation1").html("X-axis: ");
		$page.find("#calculation2").html("Y-axis: ");
	}
})(jQuery);