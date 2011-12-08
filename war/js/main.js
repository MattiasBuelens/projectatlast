/**
 * Project AtLast
 * Main script
 */
(function($) {
	// Create namespace
	$.projectatlast = {};

	$(document).bind("mobileinit", function() {
		// Use custom select menus
		$.mobile.selectmenu.prototype.options.nativeMenu = false;
		// Degrade date inputs
		$.mobile.page.prototype.options.degradeInputs.date = "text";
	});
	
	/**
	 * Home
	 * Page: /home
	 */
	$("#home").live("pageshow", function() {
		$page = $(this);
		$.getJSON("/tracking/ajaxCurrentActivity", function(data) {
			var activity = data.activity, activityRunning = (activity !== null);
			$page.find(".start-activity").toggleClass("ui-screen-hidden", activityRunning);
			$page.find(".stop-cancel-activity").toggleClass("ui-screen-hidden", !activityRunning);
			if(activityRunning) {
				$page.find(".stop-cancel-activity dd").html(activity.title);
			}
		});
	});
})(jQuery);