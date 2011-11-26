
/**
 * Request data from the server, add it to the graph and renderTo (container)
 */


function request(container,args){     
	
	/* X: names --> legenda*/
	  $.getJSON('/graph/XYDataServlet?id='+container, function(data) {

	        	
				options.title.text= data.title;
				options.chart.renderTo = container;
				//options.xAxis.categories = options.xAxis.categories.concat(data.x);
	  			
				options.series = [];
				$.each(data.x, function(key, value) { 
					options.series.push({'name':value,'data':data.y[key]});
				});
	  			
	  			
	  			new Highcharts.Chart(options);

	 			
		  });	
	
	/* X: categories
	
		  $.getJSON('/graph/XYDataServlet?id='+container, function(data) {

	        	
				options.title.text= data.title;
				options.chart.renderTo = container;
				options.xAxis.categories = options.xAxis.categories.concat(data.x);
	  			
				options.series = [];
	  			options.series.push({'name':'duration','data':data.y});
	  			
	  			new Highcharts.Chart(options);

	 			
		  });	
	
	*/
		
	   
}	
     