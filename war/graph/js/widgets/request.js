
/**
 * Request data from the server, add it to the graph and renderTo (container)
 */


//BAR COLUMN
function request(container,args){     
	
	/* X: names --> legenda*/
	  $.getJSON('/graph/XYDataServlet?id='+container, function(data) {

	        	
				options.title.text= data.title;
				options.chart.renderTo = container;
				//options.xAxis.categories = options.xAxis.categories.concat(data.x);
				options.chart.defaultSeriesType= data.graphtype;
				
				if(data.graphtype=="pie"){
					
					requestPie(container,args);
				}else{
				
				options.series = [];
				$.each(data.x, function(key, value) { 
					options.series.push({'name':value,'data':data.y[key]});
				});
	  			
	  			
	  			new Highcharts.Chart(options);
	  			}
	 			
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


function requestPie(container,args){
	// X: categories
	
	  $.getJSON('/graph/XYDataServlet?id='+container, function(data) {

      	
			options.title.text= data.title;
			options.chart.defaultSeriesType= data.graphtype;
			options.chart.renderTo = container;
			
			options.xAxis.categories=[]
			options.series = [];
			
			//options.series.push({'name':'duration','data':data.y});
			chartData = new Array();
			
			$.each(data.x, function(key, value) { 
				chartData[key]=[value,data.y[key]];
			});
			
			options.series.push({'data':chartData});
			new Highcharts.Chart(options);

			
	  });	


	
}
     