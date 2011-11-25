<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/includes/header.jsp"%>
<%@ page import="com.google.appengine.api.users.UserServiceFactory"%>
<%@ page import="projectatlast.student.*"%>
<%@ page import="projectatlast.data.*"%>
<%@ page import="projectatlast.query.*"%>
<%@ page import="projectatlast.graph.*"%>
<%@ page import="projectatlast.tracking.*"%>
<%@ page import="java.util.*"%>

<%

		

%><script src="js/highcharts.js" type="text/javascript"></script>
<script src=js/modules/exporting.js" type="text/javascript" ></script>
<script type="text/javascript" src="http://canvg.googlecode.com/svn/trunk/rgbcolor.js"></script> 
<script type="text/javascript" src="http://canvg.googlecode.com/svn/trunk/canvg.js"></script> 
	<script type="text/javascript">
		



		var chart;
		var options = {
			      chart: {
				         renderTo: '',
				         defaultSeriesType: 'column'
				      },
				      title: {
				         text: 'Monthly Average Rainfall'
				      },
				      subtitle: {
				         text: 'Source: WorldClimate.com'
				      },
				      xAxis: {
				         categories: []
				      },
				      yAxis: {
				         min: 0,
				         title: {
				            text: 'Y-axos'
				         }
				      },
				      legend: {
				         layout: 'vertical',

				         align: 'left',
				         verticalAlign: 'top',
				         x: 100,
				         y: 70,
				         floating: true,
				         shadow: true
				      },
				      tooltip: {
				         formatter: function() {
				            return ''+
				               this.x +': '+ this.y +' mm';
				         }
				      },
				      plotOptions: {
				         column: {
				            pointPadding: 0.2,
				            borderWidth: 0
				         }
				      },
				           series: []
				   };
		
		
		   
		
		
		
		</script>
		

		
<script type="text/javascript">

/**
 * Request data from the server, add it to the graph and renderTo (container)
 */


function request(container,args){     
	
	      
	
		  $.getJSON('/graph/XYDataServlet?id='+container, function(data) {

	        	
				options.title.text= data.title;
				options.chart.renderTo = container;
				options.xAxis.categories = options.xAxis.categories.concat(data.x);
	  			
				options.series = [];
	  			options.series.push({'name':'duration','data':data.y});
	  			
	  			new Highcharts.Chart(options);

	 			
		  });	
		
	   
}	
         

</script>


<div id="widgets" data-role="page" data-theme="a">
	<div data-role="header">
		<h1>Widgets</h1>
	</div>
	
	<div data-role="content" >


 
<%
List<Graph> g =  Registry.graphFinder().getGraphs(AuthController.getCurrentStudent());
ArrayList<Graph> graphs = new ArrayList<Graph>(g);

for(Graph graph: graphs){
	%>
	<div id="<%=graph.getId()%>" style="width:100%;height=500px;margin-bottom:20px;"></div>
	<%
}

%>
<script type="text/javascript">$(document).ready(function() {

	<%

	for(Graph graph: graphs){
		%>
		request('<%=graph.getId()%>');
		<%
	}
	%>
	
});</script>
<%

%>
%>
 

</div>
</div>