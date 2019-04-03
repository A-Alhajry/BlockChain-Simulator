var LightDate = function(year, month, day) {
	this.year = year;
	this.month = month;
	this.day = day;
	
	this.printFormat = function() {
		return this.year + "-" + this.month + "-" + this.day;
	}
	
	this.apiFormat = function() {
		return this.year + "-" + this.month + "-" + this.day;
	}
}
var stocksData = null;

function showSeries(selector, stocks) {
	
	stocksData = stocks;
	
	console.log(stocksData);
	
	var actualSeries = [];
	var forecastedLines = [];
	var forecastedPoints = [];
	var isAnomaly = stocksData[0].isAnomaly;
	var subForecastedSeries = [];
	forecastedPoints.push(subForecastedSeries);
	
	for(var i = 0; i < stocksData.length; i++) {
		
		var stock = stocksData[i];
		
		var date = stock.date;
		
		actualSeries.push([i, stock.actualValue]);
		forecastedLines.push([i, stock.forecastedValue]);
		
		if (stock.isAnomaly != isAnomaly) {
			subForecastedSeries = [];
			forecastedPoints.push(subForecastedSeries);
			isAnomaly = stock.isAnomaly;
		}
		
		subForecastedSeries.push(
				[i, stock.forecastedValue]
				
			)

	}
	
    console.log(actualSeries);
    //console.log(forecastedSeries);
    var series = [];
    series.push({
    	data: actualSeries,
    	lines: {
    		show: true
    	},
    	points: {
    		show: true
    	},
    	color: '#add9f9'
    	
    });
    
    series.push({
    	data: forecastedLines,
    	lines: {
    		show: true
    	},
    	points: {
    		show: false
    	},
    	color: '#edc447'
    })
    
    for(var i = 0; i < forecastedPoints.length; i++) {
    	var data = forecastedPoints[i];
    	var symbol = stocksData[i].isAnomaly ? 'cross' : 'circle';
    	
    	console.log(data);
    	//console.log(symbol);
    	
    	series.push({
    		data: data,
    		lines: {
    			show: false
    		},
    		points: {
    			show: true,
    			symbol: symbol
    		},
    		color: '#edc447'
    	})
    }
    
    
    var options = {
    		grid: {
    			hoverable: true,
    			clickable: true
    		},
    		zoom: {
    			interactive: true
    		},
    		pan: {
    			interactive: true,
    			enableTouch: true
    		},
    		legend: {
    			show: true
    		}
    };
    
    $.plot(selector, series, options);
    $(selector).bind('plothover', onHover);
    
    
}

function showKendoSeries(placeholder, stocks, companyName, fromDate, toDate) {
	stocksData = [];
	var title = "Anomaly Stocks Chart for " + companyName + " from " + fromDate.printFormat() + " to " + toDate.printFormat();
	var actualSeries = [];
	var forecastedSeries1 = [];
	var forecastedSeries2 = [];
	var minValue = 1000;
	var maxValue = 0;
	var isAnomaly = true;
	var labels = [];
	var icons = [];
	
	for(var i = 0; i < stocks.length; i++) {
		var stock = stocks[i];
		stocksData.push(stock);
		stocksData.push(stock);
		actualSeries.push(stock.actualValue);
		//actualSeries.push(stock.actualValue);
		forecastedSeries1.push(stock.forecastedValue);
		forecastedSeries2.push(stock.forecastedValue);
		labels.push(stock.date.day);
		
		maxValue = Math.round(Math.max(maxValue, stock.actualValue, stock.forecastedValue));
		minValue = Math.round(Math.min(minValue, stock.actualValue, stock.forecastedValue));
		
//		if (!stock.isAnomaly) {
//			//forecastedSeries.push(stock.forecastedValue);
//		}
//		var subSeries = [];
//		labels.push(stock.date.day);
//		var forecastedSubSeries = {isAnomaly: isAnomaly, data: []};
//		for(var j = 0; j < stocksData.length; j++) {
//			subSeries.push(stocksData[j].forecastedValue);
//			if (j == i) {
//				//forecastedSubSeries.data.push(stock.forecastedValue);
//			}
//			
//			else {
//				//forecastedSubSeries.data.push(null);
//			}
//			
//		}
		//forecastedSeries.push(stock.forecastedValue);

		//forecastedSeries.push(forecastedSubSeries);
	}
	
	console.log(stocks.length);
	console.log(stocksData.length);
	//forecastedSeries = getForecastedStocksSubSeries(stocksData);
	var kendoSeries = [];
	kendoSeries.push({
		name: 'Actual',
		data: actualSeries,
		color: '#28a745'
	});
	
	addForecastedSeries(stocks, kendoSeries, forecastedSeries1, 'ltr');
	addForecastedSeries(stocks, kendoSeries, forecastedSeries2, 'rtl');
	

	
	$(placeholder).kendoChart({
		title: {
			text: title
		},
		seriesDefaults: {
			type: 'line',
			style: 'smooth'
		},
		series: kendoSeries,
		categoryAxis: {
			categories: labels
		},
		valueAxis: {
			min: minValue - 20,
			max: maxValue + 20
		}
		
		
	});
	
	kendo.resize($(".chart-wrapper"));
	
}

function showStocksChart(startDate, endDate, companyName, placeholder) {
	
	$(".input-group.date").datetimepicker({format: 'L'});
	var url = "api/basic?compname=" + companyName + "&startdate=" + startDate.apiFormat() + "&enddate=" + endDate.apiFormat();
	$.get({
		url: url,
		success: function(stocks) {
			$(placeholder).show();
			showKendoSeries(placeholder, stocks, companyName, startDate, endDate);
		},
		error: function(e) {
			alert("Error Occurred !");
		}
	})
}

function onHover(event, pos, item) {
	

	if (item) {
		$("#Tooltip").html(pos);
		$("#Tooltip").show();
		
		console.log(item.dataIndex);
		
		var targetStock = stocksData[item.dataIndex];
		var tooltip = targetStock.date.month + "/" + targetStock.date.day;
		
		$("#Tooltip").html(tooltip).css({top: item.pageY+5, left: item.pageX+5}).fadeIn(200);
		

	}
	
	else {
		$("#Tooltip").hide();
	}
}

function onDrawSymbol(ctx, x, y, radio, shadow, fill) {
	alert(true);
}

function isDateInRange(date, startDate, endDate) {
	
	if ((date.year < startDate.year) || (date.year >= startDate.year && date.month < startDate.month)) {
		return false;
	}
	
	else if ((date.year > endDate.year) || ((date.year <= endDate.year) && date.month > endDate.month)) {
		return false;
	}
	
	else {
		return true;
	}
}

function getForecastedStocksSubSeries(stocks) {
	
	if (stocks == null || stocks.length == 0) {
		return [];
	}
	
	var mainSeries = [];
	var subSeries = [];
	var isAnomaly = stocks[0].isAnomaly;
	mainSeries.push({isAnomaly: isAnomaly, index: 0, data: subSeries});
	
	for(var i = 0; i < stocks.length; i++) {
		var stock = stocks[i];
				
		if (isAnomaly != stock.isAnomaly || i == stocks.length - 1) {
			subSeries = [];
			mainSeries.push({isAnomaly: isAnomaly, index: i, data: subSeries});
			isAnomaly = isAnomaly;
		}
		
		subSeries.push(stock.forecastedValue);
		isAnomaly = stock.isAnomaly;
		
	}
	
	return mainSeries;
}

function extractDate(date) {
	var dateParts = date.split('/');
	return new LightDate(dateParts[2], dateParts[0], dateParts[1]);
}

function addForecastedSeries(stocks, kendoSeries, forecastedSeries, strokeDirection) {
	kendoSeries.push({
		name: 'Forecasted',
		data: forecastedSeries,
		color: '#ff8d00',
		markers: {
			visual: function(e) {
				var stock = $.grep(stocks, function(n, i) {
					return stocks[i].date.day == e.category;
				})[0];
				
				if (!stock.isAnomaly) {
					return e.createVisual();
				}		
				
				var path = new kendo.drawing.Path({
					stroke: {
						color: '#dc3545',
						width: 3
					},
					
					fill: {
						color: 'white'
					}
				});
				
				var topLeft = e.rect.topLeft();
				var topRight = e.rect.topRight();
				var bottomLeft = e.rect.bottomLeft();
				var bottomRight = e.rect.bottomRight();
				
				if (strokeDirection == 'ltr') {
					path.moveTo(topLeft.x, topLeft.y).lineTo(bottomRight.x, bottomRight.y).close();
				}
				
				
				else {
					path.moveTo(topRight.x, topRight.y).lineTo(bottomLeft.x, bottomLeft.y).close();
				}
				
				return path;
				
			}
		}
	});

}

