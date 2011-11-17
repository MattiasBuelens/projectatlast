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

	// Use custom select menus
	$(document).bind('mobileinit', function() {
		$.mobile.selectmenu.prototype.options.nativeMenu = false;
	});

	// Prevent pages marked with data-cache="never" from being cached
	$(document).bind("pagehide", function(event, ui) {
		var page = jQuery(event.target);
		if (page.data('cache') === 'never') {
			// Remove page from DOM so it has to be pulled back from the server
			// on the next load
			page.remove();
		}
	});
})(jQuery);