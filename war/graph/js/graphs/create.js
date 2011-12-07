$(document).ready(function(){
	
	//default
	$(".stacked").hide();
	$("#extra").hide();
	$(".scatter").hide('slow');
	
	$("#calculation1").html('Calculation: ');
	$("#calculation2").html('Calculation: ');
	
	
	
	function defaultCalculation(){
		$("#calculation1").html('Calculation: ');
		$("#calculation2").html('Calculation: ');
	}
	$("#stacked").bind("click", function(){ 

		$(".scatter").hide('slow');
		$(".notscatter").show('slow');
		$(".notstacked").hide('slow');
		$(".stacked").show('slow');
		defaultCalculation();
		
	}); 
	
	$("#normal").bind("click", function(){ 
		
		$(".scatter").hide('slow');
		$(".notscatter").show('slow');
		$(".notstacked").show('slow');
		$(".stacked").hide('slow');
		defaultCalculation();
	}); 
	
	$("#scatter").bind("click", function(){ 
		
		$(".scatter").show('slow');
		$(".notscatter").hide('slow');
		$("#calculation1").html('X-axis: ');
		$("#calculation2").html('Y-axis: ');
		
	}); 

	$("#extraoptions").bind("click",function(){
		$("#extra").toggle();

		
	});
	

	 $('#addparser').click(function() {
		 	
		 	parsers = $(".parserselect").length;
		 	


		 	
		 	
		 	parserhtml = ' \
		 		\
		 		\
		 		<div data-role="fieldcontain" class="parserselect" id="parserselect'+parsers+'">\
			<fieldset data-role="controlgroup" data-type="horizontal">\
		 		\
		 		\
				<label for="parser'+parsers+'">Calculation:</label> \
		 		<select name="parser" id="parser'+parsers+'" data-native-menu="false" class="parser">\
		 		\
		 		\
		 		\
				</select> \
				\
				<select name="parsefield" id="parsefield'+parsers+'" data-native-menu="false" class="parsefield">\
		 		\
				</select>\
			</fieldset>\
		</div>\
		\
		';
		 	
		 
		 	
		 $("#parserholder").append(parserhtml);

		 $("#parser"+parsers).append($("#parser").find("option").clone());
		 $("#parsefield"+parsers+"").append($("#parsefield").find("option").clone());

		  

		 	
		 $('select').selectmenu();
	

       });
	
	
});