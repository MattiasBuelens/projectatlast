/**
 * Project AtLast
 * 
 * Graph package
 */
(function($) {
	/**
	 * Create graph
	 * 
	 * Page: /graph/create.jsp
	 */
	$("#graph-create").live("pageinit", function() {
		CreateGraph(this).init();
	}).live("pagebeforeshow", function() {
		CreateGraph(this).show();
	}).live("pagehide", function() {
		CreateGraph.destroy();
	});
	
	var CreateGraph = function(page) {
		if(CreateGraph.instance) {
			// Already have an instance
			return CreateGraph.instance;
		} else if(this instanceof CreateGraph && !this.page) {
			// Called as constructor
			CreateGraph.instance = this;
		} else {
			// Called as function
			return new CreateGraph(page);
		}
		// Call superclass constructor
		$.projectatlast.CreatePage.apply(this, arguments);
	};
	
	CreateGraph.destroy = function() {
		this.instance && this.instance.destroy();
		this.instance = null;
	};

	CreateGraph.prototype = new $.projectatlast.CreatePage;

	$.extend(CreateGraph.prototype, {
		super : $.projectatlast.CreatePage.prototype,

		init : function() {
			this.super.init.apply(this, arguments);

			var self = this;
			this.$("#stacked").change(function() {
				self.showStacked();
			});

			this.$("#normal").change(function() {
				self.showNormal();
			});

			this.$("#scatter").change(function() {
				self.showScatter();
			});

			this.$("#daterangegroup").bind("expand collapse", function(event) {
				var isExpanded = (event.type === "expand");
				self.$("#daterange").prop("checked", isExpanded);
			});
		},

		show : function() {
			this.super.show.apply(this, arguments);

			// Defaults
			this.getControls(".stacked,.scatter").hide();
			this.getControls(".normal").show();
			this.defaultCalculation();
		},
		
		destroy : function() {
			this.super.destroy.apply(this, arguments);
		},
		
		showNormal : function() {
			this.getControls(".scatter, .stacked").hide();
			this.getControls(".normal").show();

			this.defaultCalculation();
		},
		
		showScatter : function() {
			this.getControls(".normal, .stacked").hide();
			this.getControls(".scatter").show();

			this.scatterCalculation();
		},
		
		showStacked : function() {
			this.getControls(".normal, .scatter").hide();
			this.getControls(".stacked").show();
			if (this.$("#chart-type-pie").prop("checked")) {
				this.$("#chart-type-pie").prop("checked",
						false).checkboxradio("refresh");
				this.$("#chart-type-bar").prop("checked",
						true).checkboxradio("refresh");
			}
			this.defaultCalculation();
		},

		defaultCalculation : function() {
			this.$("#calculation1").html("Calculation: ");
			this.$("#calculation2").html("Calculation: ");
		},
		
		scatterCalculation : function() {
			this.$("#calculation1").html("X-axis: ");
			this.$("#calculation2").html("Y-axis: ");
		},

		getControls : function(selector) {
			return this.$(selector).closest(".ui-select").andSelf();
		}
	});
})(jQuery);