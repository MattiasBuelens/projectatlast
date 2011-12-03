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
			baseClass : "ui-pointpicker",
			pointTheme : "b"
		},
		// Initialise widget
		_create : function() {
			var o = this.options;
			this.element.addClass(o.baseClass);
			this.theme = o.theme || $.mobile.getInheritedTheme(this.element, "c");
			this._createArea();
			this._createPoint();
			this.inputs = {
				x : $(o.x, this.element),
				y : $(o.y, this.element)
			};
			this.axis = {
				x : this._createAxis(this.inputs.x, "x"),
				y : this._createAxis(this.inputs.y, "y")
			};
			this._bindEvents();
			this.refresh();
		},
		// Refresh widget
		refresh : function() {
			this._updateFromInput(this.inputs.x, "left");
			this._updateFromInput(this.inputs.y, "top");
			this._updateLayout();
		},
		// Destroy widget
		destroy : function() {
			$.Widget.prototype.destroy.apply(this, arguments);
			this.area.remove();

			// Unbind document events
			$(document).unbind("pageshow.pointpicker", this._updateLayout);
			$(window).unbind("resize.pointpicker", this._updateLayout);
			$(document).unbind("updatelayout.pointpicker", this._updateLayout);

			$(document).unbind("mousemove.pointpicker", this._dragging);
			$(document).unbind("mouseup.pointpicker", this._endDrag);
		},
		// Create area
		_createArea : function() {
			var o = this.options, area = $(o.area, this.element);
			if (area.length == 0)
				area = $("<div/>").appendTo(this.element);
			// Styling
			area.addClass(o.baseClass + "-area");
			area.addClass("ui-body-" + this.theme);
			// Click area
			area.bind("mousedown.pointpicker", $.proxy( this._placePoint, this));
			return this.area = area;
		},
		// Create point
		_createPoint : function() {
			var o = this.options, point = $("<a/>");
			// Styling
			point.addClass(o.baseClass + "-point");
			point.buttonMarkup({
				shadow : false,
				corners : true,
				theme : o.pointTheme
			});
			point.appendTo(this.area);
			// Drag point
			point.bind("mousedown.pointpicker", $.proxy( this._startDrag, this));
			$(document).bind("mousemove.pointpicker", $.proxy(this._dragging, this));
			$(document).bind("mouseup.pointpicker", $.proxy(this._endDrag, this));
			return this.point = point;
		},
		// Create an axis
		_createAxis : function(input, coord) {
			var o = this.options, axis = $("<div/>");
			// Styling
			axis.addClass(o.baseClass + "-axis");
			axis.addClass(o.baseClass + "-axis-" + coord);
			// Bind
			var cssCoord = coord === "x" ? "left" : "top";
			this._bindAxisInput(input, coord, cssCoord);
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
		_bindAxisInput : function(input, coord, cssCoord) {
			var self = this;
			// Point to input
			this.element.bind("pointpickerchange", function(e, coords) {
				self._updateFromPoint(input, coords[coord]);
			});
			// Input to point
			input.change(function() {
				self._updateFromInput(input, cssCoord);
			});
		},
		// Update input with current point
		_updateFromPoint: function(input, coord) {
			this._setInputValue(input, coord);
			input.trigger("change");
		},
		// Update point with current point
		_updateFromInput: function(input, cssCoord) {
			var val = this._getInputValue(input);
			if (cssCoord === "top")
				val = 100 - val;
			this.point.css(cssCoord, val + "%");
		},
		// Get input value as percentage
		_getInputValue : function(input) {
			var min = 1*(input.data("min") || input.attr("min") || 0),
				max = 1*(input.data("max") || input.attr("max") || 100),
				val = input.val();
			return Math.round(100 * (val - min) / (max - min));
		},
		// Set input value with percentage
		_setInputValue : function(input, percent) {
			var min = 1*(input.data("min") || input.attr("min") || 0),
				max = 1*(input.data("max") || input.attr("max") || 100),
				step = 1*(input.data("step") || input.attr("step") || 1),
				val;
			val = Math.round((percent / 100) * (max - min)) + min;
			val = Math.round(val / step) * step;
			input.val(val);
			return val;
		},
		// Bind events
		_bindEvents : function() {
			// Update layout
			var updateLayout = $.proxy(this._updateLayout, this);
			$(document).one("pageshow.pointpicker", updateLayout);
			$(window).bind("resize.pointpicker", updateLayout);
			$(document).bind("updatelayout.pointpicker", updateLayout);
		},
		// Update widget layout
		_updateLayout : function() {
			// Remain square at all times
			this.area.height(this.area.width());
		},
		// Place point by clicking the area
		_placePoint : function(e) {
			// Place point
			if (this._dragPoint(e, true)) {
				// Start dragging for smooth experience
				this._startDrag(e);
			}
		},
		// Drag point by clicking the point
		_dragPoint : function(e, checkInside) {
			var coords = this._toAreaCoords(e.pageX, e.pageY);
			// Check whether the coordinates are inside the area
			// bounds
			if (checkInside && !this._coordsInArea(coords))
				return false;
			// Constrain to area
			coords = this._constrainToArea(coords);
			// Get percentages
			percents = this._toAreaPercents(coords);
			// Update
			this._triggerChange(percents);
			return true;
		},
		_triggerChange : function(percents) {
			var data = {
				x : percents.x,
				y : 100 - percents.y
			};
			this._trigger("change", null, data);
		},
		// Drag point
		_dragPointQuick : function(e) {
			var coords = this._toAreaCoords(e.pageX, e.pageY);
			// Constrain to area
			coords = this._constrainToArea(coords);
			// Set absolute positioning
			this.point.css({
				left : coords.x,
				top : coords.y
			});
		},
		// Start dragging
		_startDrag : function(e) {
			this.point.data("dragging.pointpicker", true);
			e.preventDefault();
		},
		// While dragging
		_dragging : function(e) {
			if (this.point.data("dragging.pointpicker")) {
				this._dragPointQuick(e);
				e.preventDefault();
			}
		},
		// End dragging
		_endDrag : function(e) {
			if (this.point.data("dragging.pointpicker")) {
				this.point.data("dragging.pointpicker", false);
				this._dragPoint(e);
				e.preventDefault();
			}
		},
		// Convert page coordinates to area coordinates
		_toAreaCoords : function(pageX, pageY) {
			var offset = this.area.offset();
			return {
				x : pageX - offset.left,
				y : pageY - offset.top
			};
		},
		// Check whether the coords fall in the area bounds
		_coordsInArea : function(coords) {
			var width = this.area.width(), height = this.area
					.height();
			return (coords.x >= 0 && coords.x <= width
					&& coords.y >= 0 && coords.y <= height);
		},
		// Constrain coordinates to area bounds
		_constrainToArea : function(coords) {
			var width = this.area.width(), height = this.area
					.height();
			return {
				x : Math.max(0, Math.min(coords.x, width)),
				y : Math.max(0, Math.min(coords.y, height))
			};
		},
		// Convert absolute area coordinates to percentages
		_toAreaPercents : function(coords) {
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