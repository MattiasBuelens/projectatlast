
/**
 * Request data from the server, add it to the graph and renderTo (container)
 */


// BAR COLUMN
function request(container,args){     

	if(!$("#"+container).data('stacked')){
	
	/* X: names --> legenda */
	  $.getJSON('/graph/XYDataServlet?id='+container, function(data) {

	        	var options = getDefaultOptions();
				options.title.text= data.title;
				options.chart.renderTo = container;
				// options.xAxis.categories =
				// options.xAxis.categories.concat(data.x);
				options.chart.defaultSeriesType= data.graphtype;
				
				if(data.graphtype=="pie"){
					
					requestPie(container,data);
				}else{
				
				options.series = [];
				$.each(data.x, function(key, value) { 
					options.series.push({'name':value,'data':data.y[key]});
				});
	  			
	  			
	  			new Highcharts.Chart(options);
	  			}
	 			
		  });	
	
	/*
	 * X: categories
	 * 
	 * $.getJSON('/graph/XYDataServlet?id='+container, function(data) {
	 * 
	 * 
	 * options.title.text= data.title; options.chart.renderTo = container;
	 * options.xAxis.categories = options.xAxis.categories.concat(data.x);
	 * 
	 * options.series = [];
	 * options.series.push({'name':'duration','data':data.y});
	 * 
	 * new Highcharts.Chart(options);
	 * 
	 * 
	 * });
	 * 
	 */
	  
	}else{
		requestStacked(container,args);
	}
		
	   
}	


function requestPie(container,data){
	// X: categories
    $.getJSON('/graph/XYDataServlet?id='+container, function(data) {

        
        options.title.text= data.title;
        options.chart.defaultSeriesType= data.graphtype;
        options.chart.renderTo = container;
        
        options.xAxis.categories=[]
        options.series = [];
        
        // options.series.push({'name':'duration','data':data.y});
        chartData = new Array();
        
        $.each(data.x, function(key, value) { 
                chartData[key]=[value,data.y[key]];
        });
        
        options.series.push({'data':chartData});
        new Highcharts.Chart(options);

        
});   




	
}


function requestStacked(container,args){     

	
	var options = getStackedOptions();
    $.getJSON('/graph/StackedDataServlet?id='+container, function(data) {

        
		
		options.title.text= data.title;
		options.chart.defaultSeriesType= data.graphtype;
		options.chart.renderTo = container;
		
		options.xAxis.categories = options.xAxis.categories.concat(data.groups);
		options.series = [];
		
		// options.series.push({'name':'duration','data':data.y});
		
		counter =0;
		
	
		$.each(data.subgroups, function(subkey,subgroup) { 
			chartData = new Array();
			$.each(data.groups, function(groupkey,group) { 
				result = data.results[groupkey][subkey];
				if(result == null){result=0}
				//console && console.log(result);
				chartData[groupkey] = result;
		
			});
			options.series.push({'name':subgroup,'data':chartData});
		});
		//console && console.log(options);
		new Highcharts.Chart(options);
		//console && console.log(new Date);
		});
		
		
		
    return  options;
		
	  
	  
	  
}	
    

function getDefaultOptions(){
	options = {
		      chart: {
			         renderTo: '',
			         defaultSeriesType: 'bar'
			      },
			      title: {
			         text: 'Ceci n\'est pas un plot'
			      },
			      subtitle: {
			         text: 'Pure Awesomeness'
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
			         x: '',
			         y: '',
			         floating: true,
			         shadow: true
			      },plotOptions: {
			          pie: {
			              allowPointSelect: true,
			              cursor: 'pointer',
			              dataLabels: {
			                 enabled: false
			              },
			              showInLegend: true
			           }
			        },
			      tooltip: {
			    	  
			    	  
			         formatter: function() {
			        	 
			        	 if(this.series.type=='pie'){
			
			        		  return ''+this.point.name +': '+ Math.round(this.percentage) +'%';
			        	 }else{
			   
			        	 	return ''+this.series.name +': '+ this.y;
			         	}
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
	return options;
}

function getStackedOptions(){
	

      
  	options = {
  	      chart: {
  	         renderTo: 'container',
  	         defaultSeriesType: 'column'
  	      },
  	      title: {
  	         text: 'Stacked column chart'
  	      },
  	      xAxis: {
  	         categories: []
  	      },
  	      yAxis: {
  	         min: 0,
  	         title: {
  	            text: 'Total fruit consumption'
  	         },
  	         stackLabels: {
  	            enabled: true,
  	            style: {
  	               fontWeight: 'bold',
  	               color: (Highcharts.theme && Highcharts.theme.textColor) || 'gray'
  	            }
  	         }
  	      },
  	      legend: {
  	         align: 'right',
  	         x: -100,
  	         verticalAlign: 'top',
  	         y: 20,
  	         floating: true,
  	         backgroundColor: (Highcharts.theme && Highcharts.theme.legendBackgroundColorSolid) || 'white',
  	         borderColor: '#CCC',
  	         borderWidth: 1,
  	         shadow: false
  	      },
  	      tooltip: {
  	         formatter: function() {
  	            return '<b>'+ this.x +'</b><br/>'+
  	                this.series.name +': '+ this.y +'<br/>'+
  	                'Total: '+ this.point.stackTotal;
  	         }
  	      },
  	      plotOptions: {
  	         column: {
  	            stacking: 'normal',
  	            dataLabels: {
  	               enabled: true,
  	               color: (Highcharts.theme && Highcharts.theme.dataLabelsColor) || 'white'
  	            }
  	         }
  	      },
  	       series: []
  	   };
  	   
  	   
  	
	return options;
}