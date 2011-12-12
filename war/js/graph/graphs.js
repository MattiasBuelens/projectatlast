/**
 * Project AtLast
 * 
 * Graph package
 */
(function($) {
	/**
	 * List graphs
	 * 
	 * Page: /graph/graphs.jsp
	 */
	$("#graph-list").live("pageinit", function() {
		ListGraphs(this).init();
	}).live("pagebeforeshow", function() {
		ListGraphs(this).show();
	}).live("pagehide", function() {
		ListGraphs.destroy();
	});

	var ListGraphs = function(page) {
		if (ListGraphs.instance) {
			// Already have an instance
			return ListGraphs.instance;
		} else if (this instanceof ListGraphs && !this.page) {
			// Called as constructor
			ListGraphs.instance = this;
		} else {
			// Called as function
			return new ListGraphs(page);
		}

		this.page = $(page);
		this.getCurrentGraph();
	};

	ListGraphs.destroy = function() {
		this.instance && this.instance.destroy();
		this.instance = null;
	};

	ListGraphs.prototype = {
		page : null,
		currentGraph : null,
		scrollDeferred : null,
		mobileScrolling : false,
		graphsInView : {},

		$ : function() {
			return this.page.find.apply(this.page, arguments);
		},

		init : function() {
			var self = this;

			$(document).bind("keydown.listgraphs", function(e) {
				if (e.which === 33) { // DOM_VK_PAGE_UP
					self.prevGraph();
					e.preventDefault();
				} else if (e.which === 34) { // DOM_VK_PAGE_DOWN
					self.nextGraph();
					e.preventDefault();
				}
			}).bind("scrollstart.listgraphs", function() {
				this.mobileScrolling = true;
			}).bind("scrollstop.listgraphs", function() {
				this.mobileScrolling = false;
			});

			this.$(".graph").hoverIntent({
				over : function() {
					self.graphEnter($(this));
				},
				out : function() {
					self.graphLeave($(this));
				},
				timeout : 1000
			}).bind("vmousedown", function() {
				self.graphEnter($(this));
			}).bind("enterviewport", function() {
				self.graphInView($(this));
			}).bind("leaveviewport", function() {
				self.graphOutView($(this));
			}).bullseye();

			this.$("a.prevGraph").click(function() {
				self.prevGraph();
			});
			this.$("a.nextGraph").click(function() {
				self.nextGraph();
			});
			this.$("a.deleteGraph").click(function() {
				self.deleteGraph();
			});
		},

		show : function() {
			var self = this;
			// Request graphs
			// TODO Do this lazily
			this.$(".graph").each(function() {
				self.requestGraph(this);
			});
			this.scrollToGraph(this.currentGraph);
			this.updateNavigation();
		},

		destroy : function() {
			$(document).unbind(".listgraphs");
		},

		getCurrentGraph : function() {
			var graphs = this.$(".graph"),
				graph = graphs.filter(".current-graph");
			if (!graph.length)
				graph = graphs.first();
			return this.setCurrentGraph(graph);
		},

		setCurrentGraph : function(graph) {
			if (!graph || !graph.length)
				return;
			this.$(".graph").removeClass("current-graph");
			graph.addClass("current-graph");
			this.currentGraph = graph;

			this.$(".graph:not(.current-graph)").stop().animate({
				opacity : 0.25
			}, 500);
			this.$(".current-graph").stop().animate({
				opacity : 1
			}, 500);
			
			return graph;
		},

		requestGraph : function(graph) {
			requestGraph(graph.id);
		},

		scrollToGraph : function(graph) {
			if (!graph || !graph.length)
				return;

			this.setCurrentGraph(graph);
			this.updateNavigation();

			if (this.scrollDeferred) {
				this.scrollDeferred.reject();
			}

			var self = this;
			var scroll = $("html, body").stop().animate({
				scrollTop : graph.offset().top
			}, 500).promise();

			this.scrollDeferred = $.Deferred();
			$.when(scroll).done(function() {
				self.scrollDeferred.resolve();
			});
		},

		isScrolling : function() {
			if (this.mobileScrolling)
				return true;
			var deferred = this.scrollDeferred;
			if (deferred) {
				if (deferred.state) { // jQuery 1.7+
					return "pending" === deferred.state();
				} else { // jQuery 1.6
					return !deferred.isRejected() && !deferred.isResolved();
				}
			}
			return false;
		},

		prevGraph : function() {
			return this.scrollToGraph(this.currentGraph.prev(".graph"));
		},

		nextGraph : function() {
			return this.scrollToGraph(this.currentGraph.next(".graph"));
		},

		deleteGraph : function() {
			var dialog = $("#graph-delete");
			dialog.find("input[name='graphId']").val(this.currentGraph[0].id);
		},

		graphEnter : function(graph) {
			if (!this.isScrolling()) {
				this.setCurrentGraph(graph);
				this.updateNavigation();
			}
		},

		graphLeave : function(graph) { },

		graphInView : function(graph) {
			this.graphsInView[graph[0].id] = graph;
		},

		graphOutView : function(graph) {
			delete this.graphsInView[graph[0].id];
			if (this.currentGraph[0] === graph[0]) {
				var newGraph = null, id;
				for (var id in this.graphsInView)
					break;
				if (id) {
					newGraph = this.graphsInView[id];
					this.scrollToGraph(newGraph);
				}
			}
		},

		updateNavigation : function() {
			if (!this.currentGraph || !this.currentGraph.length) {
				this.currentGraph = this.$(".graph").first();
			}
			var hasPrev = (this.currentGraph.prev(".graph").length === 1),
				hasNext = (this.currentGraph.next(".graph").length === 1);
			this.enableButton(this.$("a.prevGraph"), hasPrev);
			this.enableButton(this.$("a.nextGraph"), hasNext);
		},

		enableButton : function(button, enabled) {
			button.toggleClass("ui-disabled", !enabled)
				.attr("aria-disabled", !enabled);
		}
	};
})(jQuery);