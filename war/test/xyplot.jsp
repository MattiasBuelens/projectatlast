<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/includes/header.jsp"%>
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
<script src="js/highcharts.js" type="text/javascript"></script>
<div id="login" data-role="page" data-url="/student/login" data-theme="a">
	<div data-role="header">
		<h1>Log in</h1>
	</div>

	<div data-role="content">
		<div id="container" style="width: 100%; height: 400px"></div>
		<%
			//FETCH DATA
			Query q = new Query();
				Date now = new Date();
				Date tomorrow = new Date(now.getTime() + 86400000);
				q.addOption(new DateFilter(now, tomorrow));

				out.println("XYPlot test:");
				Course analyse = new Course("H01A0BN", "Analyse, deel 1", 10);
				Course mechanica = new Course("H01B0AN",
				"Toegepaste mechanica, deel 1", 10);

				Registry.dao().ofy().put(analyse, mechanica);

				Student student = AuthController.getCurrentStudent();
				List<Object> xdata =null;
				List<Long> ydata = null;

				
				if (student == null) {
			out.println("No logged in student to work with.");
				} else {
			String type = "theory";
			StudyActivity an = new StudyActivity(student, type, Registry.dao()
					.key(analyse));
			StudyActivity me = new StudyActivity(student, type, Registry.dao()
					.key(mechanica));
			StudyActivity an2 = new StudyActivity(student, type, Registry.dao()
					.key(analyse));

			an.setStart(now);
			an.setDuration(10000);
			an2.setStart(tomorrow);
			an2.setDuration(20000);

			/** TEST QUERY **/
			
			Query query = new Query();
			Date from = new Date();
			from.setMonth(9);
			Date to = new Date();
			DateFilter d = new DateFilter(from, to);
			query.addOption(d);
			
			List<Activity> activities = query.exec();

			//Group grouper = new Group(SortField.COURSE);

			// show xy
			XYGraph plot = new XYGraph(activities, SortField.COURSE,
					ParseField.DURATION, Parser.MAX);

			XYData data = plot.generateXYData();
			xdata = data.getX();
			ydata = data.getY();
			

			for (Object o : xdata) {
				out.println("x: " + o.toString());
			}

			for (long o : ydata) {
				out.println("y: " + o);
			}
				}
		%>
		
		
		<script>
		
		
		var chart;
		$(document).ready(function() {
		   chart = new Highcharts.Chart({
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
		         categories: [
		                    <%  
		                  	for (Object o : xdata) {
		        				out.println("'"+ o.toString()+"',");
		        			}
		        			%>
		         
		         ]
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
		           series: [{
		         name: 'Value',
		         data: [<%
		    			for (long o : ydata) {
		    				out.println(o+",");
		    			}%>]
		   
		      }]
		   });
		   
		   
		});
		   
		</script>
		
		
	</div>
	
	<div data-role="footer" data-position="fixed">
		<%@ include file="/includes/copyright.jsp"%>
	</div>
</div>

</body>
</html>