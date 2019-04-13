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
	// console.log(forecastedSeries);
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
		// console.log(symbol);

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
		// actualSeries.push(stock.actualValue);
		forecastedSeries1.push(stock.forecastedValue);
		forecastedSeries2.push(stock.forecastedValue);
		labels.push(stock.date.day);

		maxValue = Math.round(Math.max(maxValue, stock.actualValue, stock.forecastedValue));
		minValue = Math.round(Math.min(minValue, stock.actualValue, stock.forecastedValue));

//		if (!stock.isAnomaly) {
//		//forecastedSeries.push(stock.forecastedValue);
//		}
//		var subSeries = [];
//		labels.push(stock.date.day);
//		var forecastedSubSeries = {isAnomaly: isAnomaly, data: []};
//		for(var j = 0; j < stocksData.length; j++) {
//		subSeries.push(stocksData[j].forecastedValue);
//		if (j == i) {
//		//forecastedSubSeries.data.push(stock.forecastedValue);
//		}

//		else {
//		//forecastedSubSeries.data.push(null);
//		}

//		}
		// forecastedSeries.push(stock.forecastedValue);

		// forecastedSeries.push(forecastedSubSeries);
	}

	console.log(stocks.length);
	console.log(stocksData.length);
	// forecastedSeries = getForecastedStocksSubSeries(stocksData);
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
	console.log($(".input-group.date").datetimepicker());
	var url = "api/service?action=comp_list&compname=" + companyName + "&startdate=" + startDate.apiFormat() + "&enddate=" + endDate.apiFormat();
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

function showStocksTable(companyName, tbody) {
	var url = "api/service?action=latest_data&compname=" + companyName;
	$.get({
		url: url,
		success: function(data) {
			$("#DateTr").html("<td><strong>Date</strong></td>");
			$("#ActualTr").html("<td><strong>Actual</strong></td>");
			$("#ForecastedTr").html("<td><strong>Forecasted</strong></td>");
			$("#StatusTr").html("<td><strong>Status</strong></td>");
			
			for(var i = 0; i < data.length; i++) {
				var stock = data[i];

				
				$("#DateTr").append("<td> " + stock.date.year + "/" + stock.date.month + "/" + stock.date.day + " </td>");
				$("#ActualTr").append("<td> " + stock.actualValue + " </td>");
				$("#ForecastedTr").append("<td> " + stock.forecastedValue + " </td>");
				if (stock.isAnomaly) {
					$("#StatusTr").append("<td class='comp-ind-down'> Anomaly </td>");
				}
				
				else {
					$("#StatusTr").append("<td class='comp-ind-up'> Regular </td>");
				}
			}
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





function loadMarketsSummaryDataTable(selector) {
	var datatable = $(selector).KTDatatable({
		data: {
			type: 'remote',
			source: {
				read: {
					url: 'api/service?action=markets_list'
				}
			},
			pageSize: 10,
			saveState: {
				cookie: false,
				webstorage: true
			},
			serverPaging: true,
			serverFiltering: true,
			serverSorting: true
		},

		layout: {
			theme: 'default',
			class: '',
			scroll: true,
			height: 400,
			footer: false
		},

		sortable: true,

		filterable: false,

		pagination: true,

		columns: [{
//			field: "MarketId",
//			title: "#",
//			sortable: false,
//			width: 40,
//			selector: {
//			class: 'kt-checkbox--solid'
//			},
//			textAlign: 'center'
//			}, {
			field: "name",
			title: "Market",
			width: 'auto',
			autoHide: false,
			// callback function support for column rendering
			template: function(data, i) {
				var marketName = data.name;
				var output = '\
					<div class="kt-user-card-v2">\
					<div class="kt-user-card-v2__pic">\
					<img src="images/' + data.logo + '" alt="photo">\
					</div>\
					<div class="kt-user-card-v2__details">\
					<a href="/StocksAnomalyWebApp/companylist?market=' + marketName +  '"class="kt-user-card-v2__name">' + data.name + '</a>\
					<span class="kt-user-card-v2__email">' +
					data.indexName + '</span>\
					</div>\
					</div>';

				return output;
			}
		}, 
		{
			field: "isOpen",
			title: "Status",
			width: 100,
			// callback function support for column rendering
			template: function(data) {

				var status;

				if (data.isOpen) {
					status = {
							'title': 'Open',
							'class': 'btn-label-success'
					};
				}

				else {
					status = {
							'title': 'Closed',
							'class': 'btn-label-danger'
					}
				}

				return '<span class="btn btn-bold btn-sm btn-font-sm ' + status.class + '">' + status.title + '</span>';
			}
		},
		{
			field: "totalStocks",
			title: "Total Companies",
			width: 100,
			template: function(data) {
				return '<span class="kt-font-bold">' + data.totalStocks + '</span>';
			}
		},
		{
			field: "totalAnomals",
			title: "Total Anomals",
			width: 100,
			template: function(data) {
				return '<span class="kt-font-bold">' + data.totalAnomals + '</span';
			}
		},


		{
			field: "health",
			title: "Health",
			width: 100,
			template: function(data) {

				var status = {}

				if (data.health == 'UNSTABLE') {
					status.class = 'btn-label-danger';
					status.title = 'Unstable';
				}

				else if (data.health == 'OK') {
					status.class = 'btn-label-warning';
					status.title = 'Ok';
				}

				else if (data.health == 'STABLE') {
					status.class = "btn-label-success",
					status.title = 'Stable';
				}

				else {
					status.class = 'btn-label-info';
					status.title = 'Unknown';
				}

				return '<span class="btn btn-bold btn-sm btn-font-sm ' + status.class + '">' + status.title + '</span';
			}

		}
//		,
//		{
//		field: "Actions",
//		width: 80,
//		title: "Actions",
//		sortable: false,
//		autoHide: false,
//		overflow: 'visible',
//		template: function() {
//		return '\
//		<div class="dropdown">\
//		<a href="javascript:;" class="btn btn-sm btn-clean btn-icon btn-icon-md"
//		data-toggle="dropdown">\
//		<i class="flaticon-more-1"></i>\
//		</a>\
//		<div class="dropdown-menu dropdown-menu-right">\
//		<ul class="kt-nav">\
//		<li class="kt-nav__item">\
//		<a href="#" class="kt-nav__link">\
//		<i class="kt-nav__link-icon flaticon2-expand"></i>\
//		<span class="kt-nav__link-text">View</span>\
//		</a>\
//		</li>\
//		<li class="kt-nav__item">\
//		<a href="#" class="kt-nav__link">\
//		<i class="kt-nav__link-icon flaticon2-contract"></i>\
//		<span class="kt-nav__link-text">Edit</span>\
//		</a>\
//		</li>\
//		<li class="kt-nav__item">\
//		<a href="#" class="kt-nav__link">\
//		<i class="kt-nav__link-icon flaticon2-trash"></i>\
//		<span class="kt-nav__link-text">Delete</span>\
//		</a>\
//		</li>\
//		<li class="kt-nav__item">\
//		<a href="#" class="kt-nav__link">\
//		<i class="kt-nav__link-icon flaticon2-mail-1"></i>\
//		<span class="kt-nav__link-text">Export</span>\
//		</a>\
//		</li>\
//		</ul>\
//		</div>\
//		</div>\
//		';
//		}
//		}
		]
	});
}

function loadMarketsBarsChart(id) {
	var chartsData = [];
	
	for(var i = 0; i < marketsHistory.length; i++) {
		var item = marketsHistory[i];
		var data = { };
		data["Index"] = item.name;
		data["Today"] = item.history[0].totalAnomaly;
		data["Yesterday"] = item.history[1].totalAnomaly;
		
		chartsData.push(data);
	}
	
	console.log(chartsData);
	
//	for(var i = 0; i < markets.length; i++) {
//		var item = markets[i];
//		var data = {
//				"c": item.country,
//				"actualIndex": item.actualIndex,
//				"forecastedIndex": item.forecastedIndex
//		}
//		chartsData.push(data);
//	}
	AmCharts.makeChart(id, {
        "theme": "light",
        "type": "serial",
        "dataProvider": chartsData,
        "valueAxes": [{
            "unit": "",
            "position": "left",
            "title": "Today vs Yesterday",
        }],
        "startDuration": 1,
        "graphs": [{
            "balloonText": "[[category]] Today Anomalitites : <b>[[value]]</b>",
            "fillAlphas": 0.9,
            "lineAlpha": 0.2,
            "title": "Today",
            "type": "column",
            "valueField": "Today"
        }, {
            "balloonText": "[[category]] Yesterday Anomalitites : <b>[[value]]</b>",
            "fillAlphas": 0.9,
            "lineAlpha": 0.2,
            "title": "Yesterday",
            "type": "column",
            "clustered": false,
            "columnWidth": 0.5,
            "valueField": "Yesterday"
        }],
        "plotAreaFillAlphas": 0.1,
        "categoryField": "Index",
        "categoryAxis": {
            "gridPosition": "start"
        },
        "export": {
            "enabled": true
        }

    });
	
	$("a[href='http://www.amcharts.com']");

}

function loadIndexStatsChart(id, index) {
	if (!KTUtil.getByID('kt_chart_profit_share')) {
        return;
    }

    var randomScalingFactor = function() {
        return Math.round(Math.random() * 100);
    };

    var config = {
        type: 'doughnut',
        data: {
            datasets: [{
                data: [
                    index.regulars, index.anomals
                ],
                backgroundColor: [
                    KTApp.getStateColor('success'),
                    KTApp.getStateColor('danger')
                    //KTApp.getStateColor('brand')
                ]
            }],
            labels: [
                'Regulars',
                'Anomals'
                //'HTML'
            ]
        },
        options: {
            cutoutPercentage: 75,
            responsive: true,
            maintainAspectRatio: false,
            legend: {
                display: false,
                position: 'top',
            },
            title: {
                display: false,
                text: 'Index Stats'
            },
            animation: {
                animateScale: true,
                animateRotate: true
            },
            tooltips: {
                enabled: true,
                intersect: false,
                mode: 'nearest',
                bodySpacing: 5,
                yPadding: 10,
                xPadding: 10, 
                caretPadding: 0,
                displayColors: false,
                backgroundColor: KTApp.getStateColor('brand'),
                titleFontColor: '#ffffff', 
                cornerRadius: 4,
                footerSpacing: 0,
                titleSpacing: 0
            }
        }
    };

    var ctx = KTUtil.getByID('kt_chart_profit_share').getContext('2d');
    var myDoughnut = new Chart(ctx, config);
    
    $(".reg_stats").html(index.regularsPerc + "% Regulars Stocks");
    $(".anm_stats").html(index.anomalsPerc + "% Anomals Stocks");
    $(".total_stocks").html(index.stocks);
}

function loadIndexHealthChart(id, index) {
	if ($('#' + id).length == 0) {
        return;
    }

    Morris.Donut({
        element: 'kt_chart_revenue_change',
        data: [{
                label: "Stables",
                value: index.stables
            },
            {
                label: "Ok",
                value: index.ok
            },
            {
                label: "Unstables",
                value: index.unstables
            },
            {
            	label: "Unknowns",
            	value: index.unknowns
            }
        ],
        colors: [
            KTApp.getStateColor('success'),
            KTApp.getStateColor('warning'),
            KTApp.getStateColor('danger'),
            KTApp.getStateColor('brand')
        ],
    });
}

function loadRegularsBandwithChart(id, data) {
    if ($('#' + id).length == 0) {
        return;
    }

    var ctx = document.getElementById(id).getContext("2d");
    var labels = [];
    var values = [];
    var totalCount = 0;
    
    for(var i = 0; i < data.length; i++) {
    	labels.push(data[i].label)
    	values.push(data[i].regularsCount);
    	totalCount += data[i].regularsCount;
    }

    $("#RegularsYearlyCount").html(totalCount);
    var gradient = ctx.createLinearGradient(0, 0, 0, 240);
    gradient.addColorStop(0, Chart.helpers.color('#d1f1ec').alpha(1).rgbString());
    gradient.addColorStop(1, Chart.helpers.color('#d1f1ec').alpha(0.3).rgbString());

    var config = {
        type: 'line',
        data: {
            labels: labels,
            datasets: [{
                label: "Regulars Stats",
                backgroundColor: gradient,
                borderColor: KTApp.getStateColor('success'),

                pointBackgroundColor: Chart.helpers.color('#000000').alpha(0).rgbString(),
                pointBorderColor: Chart.helpers.color('#000000').alpha(0).rgbString(),
                pointHoverBackgroundColor: KTApp.getStateColor('danger'),
                pointHoverBorderColor: Chart.helpers.color('#000000').alpha(0.1).rgbString(),

                //fill: 'start',
                data: values
            }]
        },
        options: {
            title: {
                display: false,
            },
            tooltips: {
                mode: 'nearest',
                intersect: false,
                position: 'nearest',
                xPadding: 10,
                yPadding: 10,
                caretPadding: 10
            },
            legend: {
                display: false
            },
            responsive: true,
            maintainAspectRatio: false,
            scales: {
                xAxes: [{
                    display: false,
                    gridLines: false,
                    scaleLabel: {
                        display: true,
                        labelString: 'Month'
                    }
                }],
                yAxes: [{
                    display: false,
                    gridLines: false,
                    scaleLabel: {
                        display: true,
                        labelString: 'Value'
                    },
                    ticks: {
                        beginAtZero: true
                    }
                }]
            },
            elements: {
                line: {
                    tension: 0.0000001
                },
                point: {
                    radius: 4,
                    borderWidth: 12
                }
            },
            layout: {
                padding: {
                    left: 0,
                    right: 0,
                    top: 10,
                    bottom: 0
                }
            }
        }
    };

    var chart = new Chart(ctx, config);
}


function loadAnomalsBandwithChart(id, data) {
    if ($('#' + id).length == 0) {
        return;
    }

    var ctx = document.getElementById(id).getContext("2d");
    var labels = [];
    var values = [];
    var totalCount = 0;
    
    for(var i = 0; i < data.length; i++) {
    	labels.push(data[i].label);
    	values.push(data[i].anomalsCount);
    	totalCount += data[i].anomalsCount;
    }
    
    $("#AnomalsYearlyCount").html(totalCount);

    var gradient = ctx.createLinearGradient(0, 0, 0, 240);
    gradient.addColorStop(0, Chart.helpers.color('#ffefce').alpha(1).rgbString());
    gradient.addColorStop(1, Chart.helpers.color('#ffefce').alpha(0.3).rgbString());

    var config = {
        type: 'line',
        data: {
            labels: labels,
            datasets: [{
                label: "Anomals Stats",
                backgroundColor: gradient,
                borderColor: KTApp.getStateColor('warning'),
                pointBackgroundColor: Chart.helpers.color('#000000').alpha(0).rgbString(),
                pointBorderColor: Chart.helpers.color('#000000').alpha(0).rgbString(),
                pointHoverBackgroundColor: KTApp.getStateColor('danger'),
                pointHoverBorderColor: Chart.helpers.color('#000000').alpha(0.1).rgbString(),

                //fill: 'start',
                data: values
            }]
        },
        options: {
            title: {
                display: false,
            },
            tooltips: {
                mode: 'nearest',
                intersect: false,
                position: 'nearest',
                xPadding: 10,
                yPadding: 10,
                caretPadding: 10
            },
            legend: {
                display: false
            },
            responsive: true,
            maintainAspectRatio: false,
            scales: {
                xAxes: [{
                    display: false,
                    gridLines: false,
                    scaleLabel: {
                        display: true,
                        labelString: 'Month'
                    }
                }],
                yAxes: [{
                    display: false,
                    gridLines: false,
                    scaleLabel: {
                        display: true,
                        labelString: 'Value'
                    },
                    ticks: {
                        beginAtZero: true
                    }
                }]
            },
            elements: {
                line: {
                    tension: 0.0000001
                },
                point: {
                    radius: 4,
                    borderWidth: 12
                }
            },
            layout: {
                padding: {
                    left: 0,
                    right: 0,
                    top: 10,
                    bottom: 0
                }
            }
        }
    };

    var chart = new Chart(ctx, config);
}
