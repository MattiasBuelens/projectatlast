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
	
	var groups=1;
	 $('#addgroup').click(function() {
		 	
		 	groups++;
		 
		 
		 	groupstandard = '<div data-role="fieldcontain" class="groupby" id="group'+groups+'"><fieldset data-role="controlgroup" id="field'+groups+'"><label for="sortfield'+groups+'">Group by:</label> </fieldset></div>';
			
		 	$("#groupholder").append(groupstandard);
		 	
		 	
			select=$("#sortfield").clone().attr('name',"sortfield"+groups).attr('id',"sortfield"+groups);
			
			$('#field'+groups+'').append(select);
			$("#group"+groups).addClass($("#group1").attr('class'));
			
			$('select').selectmenu();

		 

       });
	
	
});