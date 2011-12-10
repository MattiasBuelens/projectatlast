/**
 * Request data from the server, add it to the graph and renderTo (container)
 */

function requestGraph(containerId) {
	var container = document.getElementById(containerId),
		$container = $(container);

	$.getJSON('/graph/requestData?id=' + containerId, function(data) {
		if(data.error) {
			$container.remove();
		} else if ($container.data('stacked')) {
			plotStacked(container, data);
		} else if (data.graphtype == "pie") {
			plotPie(container, data);
		} else if (data.graphtype == "scatter") {
			plotScatter(container, data);
		} else {
			plotBar(container, data);
		}
	});
}

// BAR COLUMN
function plotBar(container, data) {
	var options = getDefaultOptions();
	options.title.text = data.title;
	options.chart.renderTo = container;
	options.chart.defaultSeriesType = data.graphtype;
	options.xAxis.categories = [ "" ];

	// set axis names
	options.xAxis.title.text = data.xaxis;
	options.yAxis.title.text = data.yaxis;
		  	
	options.series = [];
	$.each(data.x, function(key, value) {
		options.series.push({
			'name' : value,
			'data' : data.y[key].toFixed(2)*1
		});
	});

	new Highcharts.Chart(options);
}

function plotScatter(container, data) {
	var options = getDefaultOptions();
	options.title.text = data.title;
	options.chart.renderTo = container;
	options.chart.defaultSeriesType = data.graphtype;
	options.xAxis.categories = [];
	options.series = [];

	var chartData = [];
	$.each(data.x, function(key, value) {
		chartData[key] = [ value.toFixed(2)*1, data.y[key].toFixed(2)*1 ];
	});

	options.series.push({
		'name' : data.title,
		'data' : chartData
	});
	new Highcharts.Chart(options);
}

function plotPie(container, data) {
	var options = getDefaultOptions();

	options.title.text = data.title;
	options.chart.defaultSeriesType = data.graphtype;
	options.chart.renderTo = container;
	options.xAxis.categories = [];
	options.series = [];

	var chartData = [];
	$.each(data.x, function(key, value) {
		chartData[key] = [ value, 1*data.y[key].toFixed(2)*1 ];
	});

	options.series.push({
		'data' : chartData
	});
	new Highcharts.Chart(options);
}

function plotStacked(container, data) {
	var options = getStackedOptions();

	options.title.text = data.title;
	options.chart.defaultSeriesType = data.graphtype;
	options.chart.renderTo = container;
	options.xAxis.categories = options.xAxis.categories.concat(data.groups);
	options.series = [];

	// set axis names
	options.xAxis.title.text = data.xaxis;
	options.yAxis.title.text = data.yaxis;

	$.each(data.subgroups, function(subkey, subgroup) {
		var chartData = [];
		$.each(data.groups, function(groupkey, group) {
			var result = data.results[groupkey][subkey] || 0;
			chartData[groupkey] = result.toFixed(2)*1;
		});
		options.series.push({
			'name' : subgroup,
			'data' : chartData
		});
	});
	new Highcharts.Chart(options);

}

function getDefaultOptions() {
	var options = {
		chart : {
			renderTo : '',
			defaultSeriesType : 'bar'
		},
		title : {
			text : 'Ceci n\'est pas un plot'
		},
		/*subtitle : {
			text : 'Ceci n\'est pas un plot'
		},*/
		xAxis : {
			categories : [],
			title : {
				text : 'x'
			}
		},
		yAxis : {
			title : {
				text : 'y'
			}
		},
		legend : {
			layout : 'vertical',
			align : 'left',
			verticalAlign : 'top',
			x : '',
			y : '',
			floating : true,
			shadow : true
		},
		plotOptions : {
			pie : {
				allowPointSelect : true,
				cursor : 'pointer',
				dataLabels : {
					enabled : false
				},
				showInLegend : true
			}
		},
		tooltip : {
			formatter : function() {
				if (this.series.type == 'pie') {
					return '' + this.point.name + ': '
							+ Math.round(this.percentage) + '%';
				} else if (this.series.type == 'scatter') {
					return '(' + this.x + ',' + this.y + ')';
				} else {
					return this.series.name + ': ' + this.y;
				}
			}
		},
		plotOptions : {
			column : {
				pointPadding : 0.2,
				borderWidth : 0
			}
		},
		series : []
	};
	return options;
}

function getStackedOptions() {
	var options = {
		chart : {
			renderTo : 'container',
			defaultSeriesType : 'column'
		},
		title : {
			text : 'Stacked column chart'
		},
		xAxis : {
			categories : [],
			title : {
				text : 'x'
			}
		},
		yAxis : {
			min : 0,
			title : {
				text : 'y'
			},
			stackLabels : {
				enabled : true,
				style : {
					fontWeight : 'bold',
					color : (Highcharts.theme && Highcharts.theme.textColor) || 'gray'
				}
			}
		},
		legend : {
			align : 'left',
			verticalAlign : 'top',
			floating : true,
			backgroundColor : (Highcharts.theme && Highcharts.theme.legendBackgroundColorSolid) || 'white',
			borderColor : '#CCC',
			borderWidth : 1,
			shadow : true,
			layout : 'vertical',
		},
		tooltip : {
			formatter : function() {
				return '<b>' + this.x + '</b><br/>' + this.series.name + ': '
						+ this.y + '<br/>' + 'Total: ' + this.point.stackTotal;
			}
		},
		plotOptions : {
			column : {
				stacking : 'normal',
				dataLabels : {
					enabled : true,
					color : (Highcharts.theme && Highcharts.theme.dataLabelsColor) || 'white'
				}
			}
		},
		series : []
	};

	return options;
}