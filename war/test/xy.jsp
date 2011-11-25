<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<%@ page import="com.google.appengine.api.users.UserServiceFactory"%>
<%@ page import="projectatlast.student.AuthController"%>
<%@ page import="projectatlast.plotting.*"%>
<%@ page import="projectatlast.*"%>
<%@ page import="projectatlast.query.*"%>
<%@ page import="projectatlast.data.*"%>
<%@ page import="projectatlast.course.*"%>
<%@ page import="projectatlast.student.*"%>
<%@ page import="projectatlast.tracking.*"%>
<%@ page import="java.util.*"%>
<%

%>
<script type="text/javascript" src="/js/jquery-1.6.4.min.js"></script>
<script src="js/highcharts.js" type="text/javascript"></script>

		<div id="container" style="width: 100%; height: 400px;"></div>
	
		<script type="text/javascript">
		



		var chart;
		var options = {
			      chart: {
				         renderTo: 'container',
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
		




		
