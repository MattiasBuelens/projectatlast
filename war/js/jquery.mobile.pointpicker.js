/**
 * jQuery Mobile Point Picker Widget
 * 
 * A two-dimensional plot widget to pick a single point.
 * 
 * @author Mattias Buelens
 */

(function($, undefined) {
	$.widget("mobile.pointpicker", $.mobile.widget, {
		// Default options
		options : {
			initSelector : ":jqmData(role='pointpicker')",
			x : ":jqmData(role='pointpicker-x')",
			y : ":jqmData(role='pointpicker-y')",
			area : ":jqmData(role='pointpicker-area')",
			baseClass : "ui-pointpicker"
		},
		// Initialise widget
		_create : function() {
			var o = this.options;
			this.element.addClass(o.baseClass);
			this.theme = o.theme || $.mobile.getInheritedTheme(this.element, "c");
			this.pointTheme = o.pointTheme || o.theme;
			this.createArea();
			this.createPoint();
			this.inputs = {
				x : $(o.x, this.element),
				y : $(o.y, this.element)
			};
			this.axis = {
				x : this.createAxis(this.inputs.x, "x"),
				y : this.createAxis(this.inputs.y, "y")
			};
			this.bindEvents();
			this.refresh();
		},
		// Refresh widget
		refresh : function() {
			this.updateFromInput(this.inputs.x, "left");
			this.updateFromInput(this.inputs.y, "top");
			this.updateLayout();
		},
		// Update widget layout
		updateLayout : function() {
			// Remain square at all times
			this.area.height(this.area.width());
		},
		// Bind events
		bindEvents : function() {
			// Update layout
			var updateLayout = $.proxy(this.updateLayout, this);
			$(document).one("pageshow.pointpicker", updateLayout);
			$(window).bind("resize.pointpicker", updateLayout);
			$(document).bind("updatelayout.pointpicker", updateLayout);
		},
		// Destroy widget
		destroy : function() {
			$.Widget.prototype.destroy.apply(this, arguments);
			this.area.remove();

			// Unbind document events
			$(document).unbind("pageshow.pointpicker", this.updateLayout);
			$(window).unbind("resize.pointpicker", this.updateLayout);
			$(document).unbind("updatelayout.pointpicker", this.updateLayout);

			$(document).unbind("mousemove.pointpicker", this.doDrag);
			$(document).unbind("mouseup.pointpicker", this.endDrag);
		},
		// Create area
		createArea : function() {
			var o = this.options, area = $(o.area, this.element);
			if (area.length == 0)
				area = $("<div/>").appendTo(this.element);
			// Styling
			area.addClass(o.baseClass + "-area");
			area.addClass("ui-body-" + this.theme);
			// Click area
			area.bind("mousedown.pointpicker", $.proxy( this.placePoint, this));
			return this.area = area;
		},
		// Create point
		createPoint : function() {
			var o = this.options, point = $("<a/>");
			// Styling
			point.addClass(o.baseClass + "-point");
			point.buttonMarkup({
				shadow : true,
				corners : true,
				theme : this.pointTheme
			});
			point.appendTo(this.area);
			// Drag point
			point.bind("mousedown.pointpicker", $.proxy( this.startDrag, this));
			$(document).bind("mousemove.pointpicker", $.proxy(this.doDrag, this));
			$(document).bind("mouseup.pointpicker", $.proxy(this.endDrag, this));
			return this.point = point;
		},
		// Create an axis
		createAxis : function(input, coord) {
			var o = this.options, axis = $("<div/>");
			// Styling
			axis.addClass(o.baseClass + "-axis");
			axis.addClass(o.baseClass + "-axis-" + coord);
			// Bind
			var cssCoord = coord === "x" ? "left" : "top";
			this.bindAxisInput(input, coord, cssCoord);
			// Axis label
			var label, labelText = coord;
			if (input[0].id) {
				label = $("label[for='" + input[0].id + "']");
				if (label.length) labelText = label.text();
			}
			var axisLabel = $("<span/>").html(labelText);
			axisLabel.addClass(o.baseClass + "-axis-label");
			axisLabel.appendTo(axis);
			axis.appendTo(this.area);
			return axis;
		},
		// Bind input control to point coordinate
		bindAxisInput : function(input, coord, cssCoord) {
			var self = this;
			// Point to input
			this.element.bind("pointpickerchange", function(e, coords) {
				self.updateFromPoint(input, coords[coord]);
			});
			// Input to point
			input.change(function() {
				self.updateFromInput(input, cssCoord);
			});
		},
		// Update input with current point
		updateFromPoint: function(input, coord) {
			this.setInputValue(input, coord);
			input.trigger("change");
		},
		// Update point with current point
		updateFromInput: function(input, cssCoord) {
			var val = this.getInputValue(input);
			if (cssCoord === "top")
				val = 100 - val;
			this.point.css(cssCoord, val + "%");
		},
		// Get input value as percentage
		getInputValue : function(input) {
			var min = 1*(input.data("min") || input.attr("min") || 0),
				max = 1*(input.data("max") || input.attr("max") || 100),
				val = input.val();
			return Math.round(100 * (val - min) / (max - min));
		},
		// Set input value with percentage
		setInputValue : function(input, percent) {
			var min = 1*(input.data("min") || input.attr("min") || 0),
				max = 1*(input.data("max") || input.attr("max") || 100),
				step = 1*(input.data("step") || input.attr("step") || 1),
				val;
			val = Math.round((percent / 100) * (max - min)) + min;
			val = Math.round(val / step) * step;
			input.val(val);
			return val;
		},
		// Place point by clicking the area
		placePoint : function(e) {
			// Place point
			if (this.dragPoint(e, true)) {
				// Start dragging for smooth experience
				this.startDrag(e);
			}
		},
		// Drag point by clicking the point
		dragPoint : function(e, checkInside) {
			var coords = this.toAreaCoords(e.pageX, e.pageY);
			// Check whether the coordinates are inside the area
			// bounds
			if (checkInside && !this.coordsInArea(coords))
				return false;
			// Constrain to area
			coords = this.constrainToArea(coords);
			// Get percentages
			percents = this.toAreaPercents(coords);
			// Update
			this.triggerChange(percents);
			return true;
		},
		triggerChange : function(percents) {
			var data = {
				x : percents.x,
				y : 100 - percents.y
			};
			this._trigger("change", null, data);
		},
		// Drag point
		dragPointQuick : function(e) {
			var coords = this.toAreaCoords(e.pageX, e.pageY);
			// Constrain to area
			coords = this.constrainToArea(coords);
			// Set absolute positioning
			this.point.css({
				left : coords.x,
				top : coords.y
			});
		},
		// Start dragging
		startDrag : function(e) {
			this.dragging = true;
			e.preventDefault();
		},
		// While dragging
		doDrag : function(e) {
			if (this.dragging) {
				//this.dragPointQuick(e);
				this.dragPoint(e);
				e.preventDefault();
			}
		},
		// End dragging
		endDrag : function(e) {
			if (this.dragging) {
				this.dragging = false;
				this.dragPoint(e);
				e.preventDefault();
			}
		},
		// Convert page coordinates to area coordinates
		toAreaCoords : function(pageX, pageY) {
			var offset = this.area.offset();
			return {
				x : pageX - offset.left,
				y : pageY - offset.top
			};
		},
		// Check whether the coords fall in the area bounds
		coordsInArea : function(coords) {
			var width = this.area.width(), height = this.area
					.height();
			return (coords.x >= 0 && coords.x <= width
					&& coords.y >= 0 && coords.y <= height);
		},
		// Constrain coordinates to area bounds
		constrainToArea : function(coords) {
			var width = this.area.width(), height = this.area
					.height();
			return {
				x : Math.max(0, Math.min(coords.x, width)),
				y : Math.max(0, Math.min(coords.y, height))
			};
		},
		// Convert absolute area coordinates to percentages
		toAreaPercents : function(coords) {
			var width = this.area.width(), height = this.area
					.height();
			return {
				x : Math.round(100 * coords.x / width),
				y : Math.round(100 * coords.y / height)
			};
		}
	});
	// Initialise widget on pagecreate
	$(document).bind("pagecreate create", function(e) {
		$.mobile.pointpicker.prototype.enhanceWithin(e.target);
	});
})(jQuery);