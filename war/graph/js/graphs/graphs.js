$(document).ready(function() {
	var currentGraph = $(".graph").first().attr('id');
	
	//request all graphs
	$.each($(".graph"), function(index) { 
			request(this.id);
	});


	function getKey(graph){
			found = 0;
			$.each(graphs, function(key, value) { 
				
				if(value==graph){
					//alert(key);
					found=key;
				}
			});
			
			return found;
		}
		
		
		function nextGraph(){
			next = $("#"+currentGraph).next().attr('id');

			scrollTo(next);
			
			if(currentGraph!=$(".graph").last().attr('id')){
				currentGraph =next;
			}
		
			disableButtons();
		
		
		
		}
		
		function disableButtons(){
			if(currentGraph==$(".graph").last().attr('id')){
				$('#nextGraph').addClass('ui-disabled'); 
			}else{
				$('#nextGraph').removeClass('ui-disabled'); 
			}
			
			if(currentGraph==$(".graph").first().attr('id')){
				$('#prevGraph').addClass('ui-disabled'); 
			}else{
				$('#prevGraph').removeClass('ui-disabled'); 
			}
		
		}
		
		function prevGraph(){
	
		
			
			prev = $("#"+currentGraph).prev().attr('id');
			scrollTo(prev);
			if(currentGraph!=$(".graph").first().attr('id')){
				currentGraph =prev;
			}

			
			disableButtons();	
		}
		

		$( '.widget' ).live( 'hover',function(event){
		      currentGraph= getKey(this.id);
		     // alert(currentGraph);
		});
		
		
		$('#nextGraph').live('click',function(event){

			nextGraph();		
			
		});
		
		
		$('#prevGraph').live('click',function(event){
			
			prevGraph();
		});
		
		
		$('#navbar').live('hover',function(event){
			$(this).css('opacity','1');
		});
		
		$('#navbar').live('mouseleave',function(event){
			$(this).css('opacity','0.8');
		});
		
		$('#listGraphs').live('click',function(event){
			alert("QuickScroll: This will show a list of all your plots, select one	to quickly scroll to it");
			
		});
		
		
		//DEFAULTS
		$.mobile.fixedToolbars.setTouchToggleEnabled(false);
		$('#prevGraph').addClass('ui-disabled'); 
		$('#listGraphs').css('opacity','1');
		
		
	
		
		
		
		
		
		function getURLParameter(name) {
		    return decodeURI(
		        (RegExp(name + '=' + '(.+?)(&|$)').exec(location.search)||[,null])[1]
		    );
		}
		
		function scrollTo(id){
			
			var trgt = id;

			//get the top offset of the target anchor
			var target_offset = $("#"+trgt).offset();
			var target_top = target_offset.top;

			//goto that anchor by setting the body scroll top to anchor top
			$('html, body').animate({scrollTop:target_top}, 500);
	}

	
});