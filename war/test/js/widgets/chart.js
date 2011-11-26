
		var chart;
		var options = {
			      chart: {
				         renderTo: '',
				         defaultSeriesType: 'column'
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
				         x: 100,
				         y: 70,
				         floating: true,
				         shadow: true
				      },
				      tooltip: {
				         formatter: function() {
				            return ''+
				               this.series.name +': '+ this.y;
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
		