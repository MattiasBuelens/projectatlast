$(document).ready(function(){
	
	//default
	$(".normal").show();
	$(".stacked,.scatter").not(".normal").hide();
	


	$("#calculation1").html('Calculation: ');
	$("#calculation2").html('Calculation: ');

	
	
	
	function normalGraphLayout(){
		
	}
	
	function defaultCalculation(){
		$("#calculation1").html('Calculation: ');
		$("#calculation2").html('Calculation: ');
	}
	$("#stacked").bind("click", function(){ 
		$(".stacked").show('slow');
		$(".normal,.scatter").not(".stacked").hide('slow');
		if($("#chart-type-pie").prop('checked')){
			$("#chart-type-pie").prop('checked',false).checkboxradio("refresh");
			$("#chart-type-bar").prop('checked',true).checkboxradio("refresh");
		}
	}); 
	
	$("#normal").bind("click", function(){ 
		
		
		$(".normal").show();
		$(".scatter,.stacked").not(".normal").hide();

		defaultCalculation();


	}); 
	
	$("#scatter").bind("click", function(){ 
		$(".scatter").show();
		
		$(".normal,.stacked").not(".scatter").hide();
	

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