$(document).ready(function(){
	
	//default
	$(".stacked").hide();
	$("#extra").hide();
	
	$("#stacked").bind("click", function(){ 


	
		$(".notstacked").hide('slow');
		$(".stacked").show('slow');
		
		
	}); 
	
	$("#normal").bind("click", function(){ 
		

		$(".notstacked").show('slow');
		$(".stacked").hide('slow');
		
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