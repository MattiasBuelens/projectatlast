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
			$listview = $programCourses.find("ul:first").first(),
			$template = $programCourses.find("script.course-template:first").template();

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
						console.log(data);
						fillCoursesList(data.courses);
					}
				});
			}
		});
		$program.trigger("change");

		// Load program courses into list
		function fillCoursesList(courses) {
			var courses = courses || [];
			$listview.empty();
			$.each(courses, function(course) {
				$course = $template.tmpl(courses);
				if (/* course is checked */ true) {
					$course.find(":checkbox:first").prop("checked", true);
				}
				$course.appendTo($listview);
			});
			$listview.listview("refresh", true);
			$programCourses.toggleClass("ui-screen-hidden", false).slideDown();
		}
	};
})(jQuery);