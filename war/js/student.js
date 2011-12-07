/**
 * Project AtLast
 * Student package
 */
(function($) {
	/**
	 * Configure student
	 * Page: /student/configure
	 */
	$("#student-configure").live("pageinit", function() {
		var xhr = null, $this = $(this),
			$program = $this.find("#study-program").first(),
			$programCourses = $this.find("#study-program-courses").first(),
			$enrolledCourses = $this.find("#enrolled-courses > .ui-controlgroup-controls").first(),
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

		// Load program courses into list
		function fillCoursesList(courses) {
			courses = courses || [];
			$programCoursesList.empty();
			// Iterate over all study program courses
			$.each(courses, function(idx, course) {
				// Create list item with check box and label
				var checkboxId = "program-course-" + course.id,
					isChecked = $enrolledCourses
						.find("input[type='checkbox'][name='"+course.id+"']")
						.first().prop("checked");
				var $item = $("<li/>");
				var $checkbox = $("<input type='checkbox'/>").attr({
					id : checkboxId,
					name : course.id,
					value : course.name
				}).data("course", course); // save course in check box
				var $label = $("<label/>").attr({
					"for" : checkboxId
				}).html(course.name);
				// Append item to study program courses list
				$item.append($checkbox, $label).appendTo($programCoursesList);
				// Check and enhance
				$checkbox.prop("checked", isChecked);
				$checkbox.checkboxradio();
			});
			// Refresh and show list
			syncProgramCourses();
			$programCoursesList.listview("refresh", true);
			$programCourses.toggleClass("ui-screen-hidden", false).slideDown();
		}
		
		// Synchronize an enrolled course with the data from a program course
		function syncEnrolledCourse(programCourse) {
			// Find in enrolled course
			var course = $(programCourse).data("course"),
				$course = $enrolledCourses
					.find("input[type='checkbox'][value='"+programCourse.name+"']");
			if(!$course.length) {
				// Create check box in enrolled courses
				var checkboxId = "enrolled-course-" + course.id;
				var $course = $("<input type='checkbox'/>").attr({
					id : checkboxId, 
					name : "courses",
					value : course.id
				});
				var $label = $("<label/>").attr({
					"for" : checkboxId
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

		// Synchronize a program course with the data from an enrolled course
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
		
		// Synchronize all program courses with the data from the enrolled courses
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
		
		// Synchronize check boxes in study program courses and enrolled courses
		$programCourses.delegate("input[type='checkbox']", "change", function() {
			syncEnrolledCourse(this);
		});
		
		$enrolledCourses.delegate("input[type='checkbox']", "change", function() {
			syncProgramCourse(this);
		});
	}).live("pagebeforeshow", function() {
		var $this = $(this),
			$program = $this.find("#study-program").first();
		$program.trigger("change");
	});
})(jQuery);