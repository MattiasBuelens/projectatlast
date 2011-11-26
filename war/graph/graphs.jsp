<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/includes/header.jsp"%>
<%@ page import="com.google.appengine.api.users.UserServiceFactory"%>
<%@ page import="projectatlast.student.*"%>
<%@ page import="projectatlast.data.*"%>
<%@ page import="projectatlast.query.*"%>
<%@ page import="projectatlast.graph.*"%>
<%@ page import="projectatlast.tracking.*"%>
<%@ page import="java.util.*"%>

<style>
		
		.graph {
			width:100%;
			height:500px;
			margin-bottom:20px;
		
		}

		
	</style>

<script src="js/highcharts.js" type="text/javascript"></script>
<script src="js/widgets/swipe.js" type="text/javascript"></script>

	<script src="js/widgets/chart.js" type="text/javascript"></script>
		

		
<script src="js/widgets/request.js" type="text/javascript"></script>

<div data-role="page" class="type-interior">

	<div data-role="header" >
		<h1>Graphs</h1>
	</div>
<div id="navbar" data-role="header" data-position="fixed" data-theme="b" style="text-align:center; opacity : 0.5 "> 
			<div id="prevGraph" data-role="button" style="margin-right:100px;">Previous</div>
			<div id="createGraph" data-role="button" style="margin-right:100px;">Create New</div>
			<div id="nextGraph" data-role="button" >Next</div>
</div>
	<div data-role="content" data-theme="b" >


<div id="graphs">

<%
List<Graph> g =  Registry.graphFinder().getGraphs(AuthController.getCurrentStudent());
ArrayList<Graph> graphs = new ArrayList<Graph>(g);

for(Graph graph: graphs){
	%>

	<div id="<%=graph.getId()%>" class="graph"  ></div>
	
	
	
	
	<% if(graphs.indexOf(graph) != (graphs.size()-1)){ 
		Graph next = graphs.get(graphs.indexOf(graph)+1);
	%>



	<%} %>
	
<%}%>


<!-- END OF WIDGETS -->
</div>
<!-- END OF CONTENT -->
 
 
 </div>

 

 
 
 

 
 
 
 
 
 <script src="js/widgets/scroll.js" type="text/javascript" ></script>
<script type="text/javascript">
$(document).ready(function() {
	var graphs = new Array();
	<%
	int i=0;
	for(Graph graph: graphs){
		%>
		request('<%=graph.getId()%>');
		graphs[<%=i%>]=<%=graph.getId()%>;

		<%
		i++;
		
	}
	%>
	
	

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
			scrollTo(graphs[currentGraph+1]);
			if(currentGraph<graphs.length-1){
				currentGraph +=1;
			}
			disableButtons();
		
		
		
		}
		
		function disableButtons(){
			if(currentGraph==graphs.length-1){
				$('#nextGraph').addClass('ui-disabled'); 
			}else{
				$('#nextGraph').removeClass('ui-disabled'); 
			}
			
			if(currentGraph==0){
				$('#prevGraph').addClass('ui-disabled'); 
			}else{
				$('#prevGraph').removeClass('ui-disabled'); 
			}
		
		}
		
		function prevGraph(){
			scrollTo(graphs[currentGraph-1]);
			if(currentGraph>0){
			currentGraph +=-1;
			}
			
			disableButtons();	
		}
		var currentGraph=0;

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
			$(this).css('opacity','0.5');
		});
		
		
		//DEFAULTS
		$.mobile.fixedToolbars.setTouchToggleEnabled(false);
		$('#prevGraph').addClass('ui-disabled'); 
		
		
		
		
		function scrollTo(id){
		
				var trgt = id;

				//get the top offset of the target anchor
				var target_offset = $("#"+trgt).offset();
				var target_top = target_offset.top;

				//goto that anchor by setting the body scroll top to anchor top
				$('html, body').animate({scrollTop:target_top}, 500);
		}
		
		$(".scroll").click(function(event){
			//prevent the default action for the click event
			event.preventDefault();

			//get the full url - like mysitecom/index.htm#home
			var full_url = this.href;

			//split the url by # and get the anchor target name - home in mysitecom/index.htm#home
			var parts = full_url.split("#");
			var trgt = parts[1];

			//get the top offset of the target anchor
			var target_offset = $("#"+trgt).offset();
			var target_top = target_offset.top;

			//goto that anchor by setting the body scroll top to anchor top
			$('html, body').animate({scrollTop:target_top}, 500);
		});

	
});</script>

 

</div>
