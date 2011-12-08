/**
 * Project AtLast
 * Milestone package
 */
(function($) {
	/**
	 * Add milestone
	 * Page: /milestone/add
	 */
	$("#milestone-add").live("pageinit", function() {
		new AddMilestone(this).init();
	}).live("pageshow", function() {
		new AddMilestone(this).show();
	});
	
	var AddMilestone = function(page) {
		this.page = $(page);
	};

	AddMilestone.prototype = {
		page : null,

		$ : function() {
			return this.page.find.apply(this.page, arguments);
		},

		init : function() {
			var self = this;
			this.$("select, input").change(function() {
				self.createSentence();
			});
			this.$("input[name='activity-type']").change(function() {
				self.createLayout();
			});
			this.$("select[name='parsefield']").change(function(){
				self.setGoalUnit();
			});
			this.$("input[name='startdate']").change(function(){
				self.calendarStart();
			});
			this.$("input[name='stopdate']").change(function(){
				self.calendarStop();
			});
		},

		show : function() {
			this.createLayout();
			this.setGoalUnit();
			this.calendarStart();
			this.calendarStop();
			this.createSentence();
		},

		createLayout : function() {
			var activityType = this.getFormType();
			if (activityType == "study") {
				this.createStudyLayout();
			} else {
				this.createFreeTimeLayout();
			}
		},

		createStudyLayout : function() {
            this.$(".freeTimeField").hide();
			this.$(".studyField").show();
		},

		createFreeTimeLayout : function() {
			this.$(".studyField").hide();
            this.$(".freeTimeField").show();
		},

		getFormType : function() {
			return this.$("input[name='activity-type']:checked").val();
		},

		createSentence : function() {
			var activityType = this.getFormType(), sentence = "";
			if (activityType == "study") {
				sentence = this.createStudySentence();
			} else {
				sentence = this.createFreeTimeSentence();
			}
			this.$(".milestoneString").html(sentence);
		},
		
		createGoalSentence : function() {
			var parser = this.getReadableValue("select[name='parser']");
			var goal = this.getReadableValue("select[name='operator']") + " ";
			goal += this.getReadableValue("input[name='goal']") + " ";
			goal += this.getGoalUnit();
			return this.getReadableValue("select[name='parsefield']").replace("{parser}", parser).replace("{goal}", goal) + " ";
		},

		createStudySentence : function() {
			var sentence = "I want to ";
			sentence += this.createGoalSentence();
			sentence += this.getReadableValue("select[name='study-type']") + " ";
			sentence += this.getReadableValue("select[name='course']") + " ";
			sentence += this.createDateSentence() + ".";
			return sentence;
		},

		createFreeTimeSentence : function() {
			var sentence = "I want to ";
			sentence += this.createGoalSentence();
            sentence += this.getReadableValue($("select[name='freetime-type']")) + " ";
			sentence += this.createDateSentence() + ".";
			return sentence;
		},

		createDateSentence : function() {
			var startDate = $("input[name='startdate']").data("datebox").theDate,
				stopDate = $("input[name='stopdate']").data("datebox").theDate,
				dateFormat = "ddd, dd mmm YYYY";
			
			var sentence = "between ";
			sentence += this.formatDate(startDate, dateFormat);
			sentence += " and ";
			sentence += this.formatDate(stopDate, dateFormat);
			
			return sentence;
		},
		
		formatDate : function(date, format) {
			return $.mobile.datebox.prototype._formatter(format, date);
		},

		getReadableValue : function(elem) {
			if(typeof elem === "string")
				elem = this.$(elem);
			if (!elem || !elem.length)
				return "";
			var option = null, readable = "";
			if (option = this.getSelected(elem)) {
				// Select
				readable = option.data("readable") || option.val();
			} else {
				// Input
				readable = elem.val();
			}
			return readable;
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

		getGoalUnit : function() {
			var $option = this.getSelected("select[name='parsefield']");
			return ($option && $option.data("unit")) || "";
		},

		setGoalUnit : function() {
			this.$(".goal-unit").html(this.getGoalUnit());
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