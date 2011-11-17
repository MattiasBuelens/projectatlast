/**
 * Project AtLast
 * 
 * Main script
 */
(function($) {
	// Create namespace
	$.projectatlast = {};

	// Use custom select menus
	$(document).bind('mobileinit', function() {
		$.mobile.selectmenu.prototype.options.nativeMenu = false;
	});
})(jQuery);