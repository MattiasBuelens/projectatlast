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
	
	
	
});