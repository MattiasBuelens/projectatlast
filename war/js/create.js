/**
 * Project AtLast
 */
(function($) {
	$.projectatlast = $.projectatlast || {};

	$.projectatlast.CreatePage = function(page) {
		// Allow instantiation without initializing for simple inheritance
		if(arguments.length) {
			this.page = $(page);
		}
	};

	$.projectatlast.CreatePage.prototype = {
		page : null,

		$ : function() {
			return this.page.find.apply(this.page, arguments);
		},

		init : function() {
			var self = this;
			this.$("input[name='startdate']").change(function(){
				self.calendarStart();
			});
			this.$("input[name='stopdate']").change(function(){
				self.calendarStop();
			});
		},

		show : function() { },
		
		destroy : function() { },
		
		enableControls : function(elem, enable) {
			elem.toggle(enable);
			var options = elem.closest("option");
			options.prop("disabled", !enable);
			var selects = options.closest("select");
			selects.find("option:disabled:selected").each(function() {
				$(this).siblings("option:enabled").first().prop("selected", true);
			});
			selects.selectmenu("refresh", true);
		},
		
		formatDate : function(date, format) {
			return $.mobile.datebox.prototype._formatter(format, date);
		},
		
		getSelected : function(elem) {
			if(typeof elem === "string")
				elem = this.$(elem);
			if(elem.length)
				elem = elem[0];
			if (elem.options) {
				return this.$(elem.options[elem.selectedIndex]);
			}
			return null;
		},

		calendarStart : function() {
			var now = new Date(),
				startDate = this.$("input[name='startdate']").data("datebox").theDate,
				diff = Math.floor((now - startDate) / (1000 * 60 * 60 * 24)),
				stopDateBox = this.$("input[name='stopdate']").data("datebox");
			// Stop date must come after start date
			if(stopDateBox.theDate < startDate) {
				stopDateBox.theDate = startDate;
				stopDateBox.input.trigger('datebox', {'method':'doset'});
			}
			stopDateBox.options.minDays = diff;
		},

		calendarStop : function() {
			var now = new Date(),
				stopDate = this.$("input[name='stopdate']").data("datebox").theDate,
				diff = Math.floor((stopDate - now) / (1000 * 60 * 60 * 24)) + 1,
				startDateBox = this.$("input[name='startdate']").data("datebox");
			// Start date must come before stop date
			if(startDateBox.theDate > stopDate) {
				startDateBox.theDate = stopDate;
				startDateBox.input.trigger('datebox', {'method':'doset'});
			}
			startDateBox.options.maxDays = diff;
		}
	};
})(jQuery);