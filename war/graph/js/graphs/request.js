
/**
 * Request data from the server, add it to the graph and renderTo (container)
 */


// BAR COLUMN
function request(container,args){     

	if(!$("#"+container).data('stacked')){
	
	/* X: names --> legenda */
	  $.getJSON('/graph/requestData?id='+container, function(data) {

	        	var options = getDefaultOptions();
				options.title.text= data.title;
				options.chart.renderTo = container;
				// options.xAxis.categories =
				// options.xAxis.categories.concat(data.x);
				options.chart.defaultSeriesType= data.graphtype;
				options.xAxis.categories=[""];
				
				//set axis names
				options.xAxis.title.text = data.xaxis;
				options.yAxis.title.text = data.yaxis;
				
				if(data.graphtype=="pie" || data.graphtype=="scatter"){
					requestPie(container,data);
				}else{
				
				options.series = [];
				$.each(data.x, function(key, value) { 
					options.series.push({'name':value,'data':data.y[key]});
				});
	  			
	  			
	  			new Highcharts.Chart(options);
	  			}
	 			
		  });	
	
	
	}else{
		requestStacked(container,args);
	}
		
	   
}	


function requestPie(container,data){
	// X: categories
    $.getJSON('/graph/requestData?id='+container, function(data) {

        
        options.title.text= data.title;
        options.chart.defaultSeriesType= data.graphtype;
        options.chart.renderTo = container;
        
        options.xAxis.categories=[];
        options.series = [];
        
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
    $.getJSON('/graph/requestData?id='+container, function(data) {

        
		
		options.title.text= data.title;
		options.chart.defaultSeriesType= data.graphtype;
		options.chart.renderTo = container;
		
		options.xAxis.categories = options.xAxis.categories.concat(data.groups);
		options.series = [];
		
		// options.series.push({'name':'duration','data':data.y});
		
		//set axis names
		options.xAxis.title.text = data.xaxis;
		options.yAxis.title.text = data.yaxis;
		
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
			         text: 'Ceci n\'est pas un plot'
			      },
			      xAxis: {
			         categories: [],
			      		title: {
			            text: 'x'
			         }
			      },
			      yAxis: {
			       
			         title: {
			            text: 'y'
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
			        	 }else if(this.series.type=='scatter'){
			     			
			        		  return '('+this.x+','+this.y+')';
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
  	         categories: [],
  	         title: {
   	            text: 'x'
   	         }
  	      },
  	      yAxis: {
  	         min: 0,
  	         title: {
  	            text: 'y'
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
  	    	 align: 'left',
  
  	         verticalAlign: 'top',
  	         floating: true,
  	         backgroundColor: (Highcharts.theme && Highcharts.theme.legendBackgroundColorSolid) || 'white',
  	         borderColor: '#CCC',
  	         borderWidth: 1,
  	         shadow: true,
  	         layout: 'vertical',
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