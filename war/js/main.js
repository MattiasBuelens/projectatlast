/**
 * Project AtLast
 * 
 * Main script
 * 
 * @author Mattias Buelens
 */
(function($) {
	// Create namespace
	$.projectatlast = {};

	$(document).bind("mobileinit", function() {
		// Use custom select menus
		$.mobile.selectmenu.prototype.options.nativeMenu = false;
		// Don't cache anything. Just, don't. Seriously.
		$.mobile.loadPage.defaults.reloadPage = true;
	});

})(jQuery);