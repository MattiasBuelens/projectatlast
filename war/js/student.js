/**
 * Project AtLast Student scripts
 */
(function($) {
	// Create namespace
	$.projectatlast.student = {};
	// Bind to pagechange event
	$(document).bind("pagechange", function(toPage, options) {
		var page = options.toPage, pageId = page.get(0).id;
		switch (pageId) {
		case "student-configure":
			$.projectatlast.student.configure.call(page);
			break;
		}
	});

	/**
	 * Configure student
	 * student/configure
	 */
	$.projectatlast.student.configure = function() {
		var xhr = null,
			$program = this.find("#study-program").first(),
			$programCourses = this.find("#study-program-courses").first(),
			$enrolledCourses = this.find("#enrolled-courses").first(),
			$listview = $programCourses.find("ul:first").first();

		/*
		 * Load courses from study program
		 */
		$program.change(function() {
			var program = $program.val();
			// Abort previous request
			if (xhr && xhr.readyState != 4) {
				xhr.abort();
			}
			if (program == "") {
				// Hide program courses
				$programCourses.slideUp(function() {
					$programCourses.toggleClass("ui-screen-hidden", true);
				});
			} else {
				// Retrieve courses in study program
				xhr = $.ajax({
					url : "/course/ajaxGetProgramCourses",
					type : "get",
					data : { studyProgram : program },
					dataType : "json",
					success : function(data) {
						fillCoursesList(data.courses);
					}
				});
			}
		});
		$program.trigger("change");

		// Load program courses into list
		function fillCoursesList(courses) {
			courses = courses || [];
			$listview.empty();
			$.each(courses, function(idx, course) {
				console.log(course);
				var checkboxId = $programCourses.prop('id') + '-' + course.id,
					isChecked = $enrolledCourses.find(":checkbox[name='"+course.id+"']").first().prop('checked');
				var $item = $('<li/>');
				var $checkbox = $('<input type="checkbox"/>').attr({
					id : checkboxId,
					name : course.id,
					value : course.name
				});
				var $label = $('<label/>').attr({
					'for': checkboxId
				}).html(course.name);
				$item.append($checkbox, $label).appendTo($listview);
				$checkbox.prop('checked', isChecked);
				$checkbox.checkboxradio();
			});
			// Refresh and show list
			$listview.listview("refresh", true);
			$programCourses.toggleClass("ui-screen-hidden", false).slideDown();
		}
	};
})(jQuery);