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
			$enrolledCourses = this.find("#enrolled-courses > .ui-controlgroup-controls").first(),
			$programCoursesList = $programCourses.find("ul").first();

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
			$programCoursesList.empty();
			// Iterate over all study program courses
			$.each(courses, function(idx, course) {
				// Create list item with check box and label
				var checkboxId = 'program-course-' + course.id,
					isChecked = $enrolledCourses
						.find("input[type='checkbox'][name='"+course.id+"']")
						.first().prop('checked');
				var $item = $('<li/>');
				var $checkbox = $('<input type="checkbox"/>').attr({
					id : checkboxId,
					name : course.id,
					value : course.name
				}).data("course", course); // save course in checkbox
				var $label = $('<label/>').attr({
					'for': checkboxId
				}).html(course.name);
				// Append item to study program courses list
				$item.append($checkbox, $label).appendTo($programCoursesList);
				// Check and enhance
				$checkbox.prop('checked', isChecked);
				$checkbox.checkboxradio();
			});
			// Refresh and show list
			syncProgramCourses();
			$programCoursesList.listview("refresh", true);
			$programCourses.toggleClass("ui-screen-hidden", false).slideDown();
		}
		
		function syncEnrolledCourse(programCourse) {
			// Find in enrolled course
			var course = $(programCourse).data("course"),
				$course = $enrolledCourses
					.find("input[type='checkbox'][value='"+programCourse.name+"']");
			if(!$course.length) {
				// Create checkbox in enrolled courses
				var checkboxId = 'enrolled-course-' + course.id;
				var $course = $('<input type="checkbox"/>').attr({
					id : checkboxId, 
					name : "courses",
					value : course.id
				});
				var $label = $('<label/>').attr({
					'for' : checkboxId
				}).html(course.name);
				// Append to enrolled courses
				$enrolledCourses.append($course, $label);
				// Enhance and refresh
				$course.checkboxradio();
				refreshEnrolled();
			}
			// Synchronize check state
			$course.prop("checked", programCourse.checked)
				.checkboxradio("refresh");
		}
		
		function syncProgramCourse(enrolledCourse) {
			// Find in program course
			var courseId = enrolledCourse.value,
				$course = $programCourses
					.find("input[type='checkbox'][name='"+courseId+"']");
			if($course.length) {
				// Synchronize check state
				$course.prop("checked", enrolledCourse.checked)
					.checkboxradio("refresh");
			}
		}
		
		function syncProgramCourses() {
			$enrolledCourses.find("input[type='checkbox']").each(function() {
				syncProgramCourse(this);
			});
		}

		// Refresh layout of enrolled courses list
		function refreshEnrolled() {
			$enrolledCourses.find(".ui-btn, .ui-btn-inner")
				.removeClass("ui-corner-top ui-corner-bottom ui-controlgroup-last");
			$enrolledCourses.controlgroup();
		}
		
		// Synchronize checkboxes in study program courses and enrolled courses
		$programCourses.delegate("input[type='checkbox']", "change", function() {
			syncEnrolledCourse(this);
		});
		
		$enrolledCourses.delegate("input[type='checkbox']", "change", function() {
			syncProgramCourse(this);
		});
	};
})(jQuery);