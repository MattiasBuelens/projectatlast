
		var chart;
		var options = {
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
		