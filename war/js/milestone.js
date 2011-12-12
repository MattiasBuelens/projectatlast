/**
 * Project AtLast
 * 
 * Milestone package
 */
(function($) {
	/**
	 * Add milestone
	 * 
	 * Page: /milestone/add
	 */
	$("#milestone-add").live("pageinit", function() {
		AddMilestone(this).init();
	}).live("pagebeforeshow", function() {
		AddMilestone(this).show();
	}).live("pagehide", function() {
		AddMilestone.destroy();
	});
	
	var AddMilestone = function(page) {
		if(AddMilestone.instance) {
			// Already have an instance
			return AddMilestone.instance;
		} else if(this instanceof AddMilestone && !this.page) {
			// Called as constructor
			AddMilestone.instance = this;
		} else {
			// Called as function
			return new AddMilestone(page);
		}
		// Call superclass constructor
		$.projectatlast.CreatePage.apply(this, arguments);
	};
	
	AddMilestone.destroy = function() {
		this.instance && this.instance.destroy();
		this.instance = null;
	};

	AddMilestone.prototype = new $.projectatlast.CreatePage;

	$.extend(AddMilestone.prototype, {
		super : $.projectatlast.CreatePage.prototype,

		init : function() {
			this.super.init.apply(this, arguments);

			var self = this;
			this.$("input[name='goal']").change(function(){
				self.checkLimit();
			});
			this.$("input[name='activity-type']").change(function() {
				self.createLayout();
			});
			this.$("select[name='parsefield']").change(function(){
				self.setGoalUnit();
				self.checkLimit();
			});
			this.$("select, input").change(function() {
				self.createSentence();
			});
			this.$("form").submit(function(e) {
				self.checkLimit();
				self.validateInput(e);
			});
		},

		show : function() {
			this.super.show.apply(this, arguments);

			this.createLayout();
			this.setGoalUnit();
			this.calendarStart();
			this.calendarStop();
			this.createSentence();
		},
		
		destroy : function() {
			this.super.destroy.apply(this, arguments);
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
			this.enableControls(this.$(":jqmData(kind='freetime')"), false);
			this.enableControls(this.$(":jqmData(kind='study')"), true);
		},

		createFreeTimeLayout : function() {
			this.enableControls(this.$(":jqmData(kind='study')"), false);
			this.enableControls(this.$(":jqmData(kind='freetime')"), true);
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

		getGoalUnit : function() {
			var $option = this.getSelected("select[name='parsefield']");
			return ($option && $option.data("unit")) || "";
		},

		setGoalUnit : function() {
			this.$(".goal-unit").html(this.getGoalUnit());
		},
		
		getGoalLimit : function() {
			var $option = this.getSelected("select[name='parsefield']");
			return ($option && $option.data("limit")) || -1;
		},
		
		checkLimit : function() {
			var limit = this.getGoalLimit();
			var $goal = this.$("input[name='goal']");
			var val = $goal.val();
			if(val == "" || isNaN(val) || val < 0) {
				$goal.val(0);
			}
			if(limit >= 0 && val > limit) {
				$goal.val(limit);
			}
		},
		
		validateInput : function(e) {
			var error = this.$(".error"), hasError = false,
				goal = this.$("input[name='goal']").val(),
				startDate = this.$("input[name='startdate']").data("datebox").theDate,
				stopDate = this.$("input[name='stopdate']").data("datebox").theDate;

			// Check goal
			if(goal <= 0) {
				error.html("Invalid goal.");
				hasError = true;
			}

			// Check dates
			if(startDate > stopDate) {
				error.html("Start date must be before deadline.");
				hasError = true;
			}

			// Show error and prevent form submission
			error.toggleClass("ui-screen-hidden", !hasError);
			if(hasError) {
				e.preventDefault();
			}
		}
	});
})(jQuery);