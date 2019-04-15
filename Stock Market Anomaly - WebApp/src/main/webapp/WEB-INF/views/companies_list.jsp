<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false"%>


<head>
<link href="css/bootstrap.min.css" rel="stylesheet">
<!-- <link href="css/bootstrap-datepicker3.min.css" rel="stylesheet"> -->
<link href="css/style.css" rel="stylesheet">
<link href="https://use.fontawesome.com/releases/v5.7.2/css/all.css"
	rel="stylesheet">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="http://code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
<script src="js/bootstrap.min.js"></script>
<!-- <script src="js/bootstrap-datepicker.js"></script> -->

<jsp:include page="kendo-ui-imports.jsp"></jsp:include>
<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.24.0/moment.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/tempusdominus-bootstrap-4/5.0.0-alpha14/js/tempusdominus-bootstrap-4.min.js"></script>
<link href="https://cdnjs.cloudflare.com/ajax/libs/tempusdominus-bootstrap-4/5.0.0-alpha14/css/tempusdominus-bootstrap-4.min.css" rel="stylesheet">
<script src="js/script.js"></script>
</head>
<script>
	startDate = ${startDate};
	endDate = ${endDate};

</script>

	<div >
		<div class="comp-summ">
			<h2>Qatar Stock Exchane - Today Index Summary</h2>
				<div class="row">
					<div class="col-md-12">
						<div class="col-md-3 comp-col" style="top: 70px;">
							<a href="#${company.shortName}" class="comp-link"><img src="images/${Market.logo}"
								class="img-fluid"></a>
						</div>
						<div class="col-md-3 comp-col" style="top: 90px; left: 300px;">
							<strong>${Market.name} </strong>
							<p>Qatar</p>
							<p>${LocalDate.now()}
						</div>
						<div class="col-md-3 comp-col" style="top: 90px; left: 550px">

							
							<strong><i class="fas fa-arrow-up comp-ind-up"></i>
							</strong> <strong><span class="comp-ind-up">30</span></strong>
							<strong><span class="comp-ind-up">(0.48%)</span></strong>
							<br /> <br />
							<p>
								<strong >Actual :  <i style="font-size: 22px;" class=""></i></strong>${Market.actualIndex}<strong><br/>Forecasted
									: </strong>${Market.forecastedIndex} <br/> <strong>Total Companies: </strong> ${Market.totalStocks} <br/>
									<strong>Total Anomalies: </strong> ${Market.totalAnomals}
							</p>
						</div>
					</div>
					
					<hr style="height: 30px;" />
				</div>

				

		</div>
		<br/>
		
		<div class="comp-summ comp-div" style="margin-top: 20px;">
			<h2>Qatar Stock Exchane - Registered Companies Summaries</h2>
			<c:forEach items="${CompaniesData}" var="company">
				<div class="row"
					
					data-comp-name="${company.shortName}">
					<div class="col-md-12">
						<div class="col-md-3 comp-col" style="top: 70px;">
							<a href="#${company.shortName}" class="comp-link"><img src="images/${company.iconUrl}"
								class="img-fluid"></a>
						</div>
						<div class="col-md-3 comp-col" style="top: 90px; left: 300px;">
							<strong>${company.fullName}</strong>
							<p>${company.shortName}</p>
							<p>${company.date}
						</div>
						<div class="col-md-3 comp-col" style="top: 90px; left: 550px">

							<c:choose>
								<c:when test="${company.indicator.value == 1}">
									<c:set var="indCss" scope="session" value='${"comp-ind-up"}'></c:set>
									<c:set var="indArr" scope="session" value='${"up"}'></c:set>
								</c:when>
								<c:when test="${company.indicator.value == 2}">
									<c:set var="indCss" scope="session" value='${"comp-ind-down"}'></c:set>
									<c:set var="indArr" scope="session" value='${"down"}'></c:set>
								</c:when>
								<c:otherwise>
									<c:set var="indCss" scope="session" value='${""}'></c:set>
									<c:set var="indArr" scope="session" value='${""}'></c:set>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${company.isAnomaly == true}">
									<c:set var="statusIcon" scope="session" value='${"fas fa-exclamation"}'></c:set>
									<c:set var="statusCss" scope="session" value='${"comp-ind-down"}'></c:set>
								</c:when>
								<c:otherwise>
<%-- 									<c:set var="statusIcon" scope="session"  value='${"fas fa-check"}'></c:set> --%>
									<c:set var="statusIcon" scope="session"  value='${""}'></c:set>
									<c:set var="statusCss" scope="session" value='${"comp-ind-up"}'></c:set>
								</c:otherwise>
							</c:choose>
							<strong><i class="fas fa-arrow-${indArr} ${indCss}"></i>
							</strong> <strong><span class="${indCss}">${company.differenceValue}</span></strong>
							<strong><span class="${indCss}">(${company.differencePercentage}%)</span></strong>
							<br /> <br />
							<p>
								<strong >Actual : </strong>${company.actualValue} <i style="font-size: 22px;" class="${statusIcon} ${statusCss}"></i><strong><br/>Forecasted
									: </strong>${company.forecastedValue}
							</p>
						</div>
					</div>
					
					<hr style="height: 30px;" />
				</div>

				

			</c:forEach>
		</div>
		
		<div id="TableDiv" class="container" style="margin-top: 20px; display: none;">
			<div class="row">
				<table class="table table-striped table-bordered">
					<tbody>
						<tr id="DateTr">
							<td><strong>Date</strong></td>
							<td>18/06/2017</td>
							<td>19/06/2017</td>
							<td>20/06/2017</td>
							<td>21/06/2017</td>
							<td>22/06/2017</td>
						</tr>
						<tr id="ActualTr">
							<td><strong>Actual</strong></td>
							<td>54.700001</td>
							<td>54.299999</td>
							<td>53.599998</td>
							<td>54.099998</td>
							<td>55.599998</td>
						</tr>
						<tr id="ForecastedTr">
							<td><strong>Forecasted</strong></td>
							<td>60.49984851</td>
							<td>54.44897792</td>
							<td>52.14795235</td>
							<td>52.76207297</td>
							<td>54.18635548</td>
						</tr>
						<tr id="StatusTr">
							<td ><strong>Status</strong></td>
							<td class="comp-ind-down">Anomaly</td>
							<td class="comp-ind-up">Normal</td>
							<td class="comp-ind-up">Normal</td>
							<td class="comp-ind-up">Normal</td>
							<td class="comp-ind-up">Normal</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>

		<div id="FormDiv" class="container" >
			<div class="row">
				<div class="col-md-5">
					<div class="form-group">
						<label class="control-label">From: </label>
						<div class="input-group date" id="FromDate" data-target-input="nearest">
							<input type="text" class="form-control datetimepicker-input" data-target="#FromDate"/>
							<div class="input-group-append" data-target="#FromDate" data-toggle="datetimepicker">
								<div class="input-group-text"><i class="fa fa-calendar"></i></div>
							</div>
						</div>
					</div>
				</div>
				<div class="col-md-5">
					<div class="form-group">
						<label class="control-label">To:</label>
						<div class="input-group date" id="ToDate" data-target-input="nearest">
							<input type="text" class="form-control datetimepicker-input" data-target="#ToDate"/>
							<div class="input-group-append" data-target="#ToDate" data-toggle="datetimepicker">
								<div class="input-group-text"><i class="fa fa-calendar"></i></div>
							</div>
						</div>
					</div>
				</div>
				<div class="col-md-2" style="margin-top: 30px;">
					<div class="form-group">
						<button id="GetButton" class="btn btn-primary">Get</button>
					</div>
				</div>
			</div>
		</div>
		<div class="demo-section k-content wide chart-wrapper container">
			<div id="KendoChart"></div>
		</div>
	</div>
	<!-- 	<nav class="navbar nabvar-expand-sm bg-dark navbar-dark"> -->
	<!-- 		<ul class="navbar-nav"> -->
	<!-- 			<li class="nav-item active"> -->
	<!-- 				<a href="/home" class="nav-link">Home</a> -->
	<!-- 			</li> -->
	<!-- 			<li class="nav-item"> -->
	<!-- 				<a href="basic?demo=1&company=qp&startdate=2017-01-01&enddate=2017-01-31" target="_blank" class="nav-link">Demo 1</a> -->
	<!-- 			</li> -->
	<!-- 			<li class="nav-item"> -->
	<!-- 				<a href="basic?demo=2&company=qp&startdate=2017-01-01&enddate=2017-01-31" target="_blank" class="nav-link">Demo 2</a> -->
	<!-- 			</li> -->
	<!-- 		</ul> -->
	<!-- 	</nav> -->

	<!-- 	<br/>  -->
	<!-- 	<table class="table"> -->
	<!-- 		<thead> -->
	<!-- 			<tr> -->
	<!-- 				<th>Date</th> -->
	<!-- 				<th>Actual</th> -->
	<!-- 				<th>Forecasted</th> -->
	<!-- 				<th>Anomaly</th> -->
	<!-- 			</tr> -->
	<!-- 		</thead> -->
	<!-- 		<tbody> -->
	<%-- 			<c:forEach items="${Stocks}" var="stock"> --%>
	<!-- 				<tr> -->
	<%-- 					<td> ${stock.date} </td> --%>
	<%-- 					<td>${stock.actualValue}</td> --%>
	<%-- 										<td>${stock.forecastedValue} --%>
	<%-- 					<td>${stock.isAnomaly}</td> --%>
	<!-- 				</tr> -->
	<%-- 			</c:forEach> --%>
	<!-- 		</tbody> -->
	<!-- 	</table> -->
<script>
	$(document).ready(function() {

		
		$("#KendoChart").hide();
		$("#FormDiv").hide();
		$(".comp-link").click(function(e) {
			//$("hr").hide();
			//e.preventDefault();
			var parentRow = $(this).closest(".row");
			var companyName = parentRow.data("comp-name");
			fromDate = new LightDate(startDate.year, startDate.month, startDate.day);
			toDate = new LightDate(endDate.year, endDate.month, endDate.day);
			showStocksChart(fromDate, toDate, companyName, '#KendoChart');
			showStocksTable(companyName, "#TableDiv tbody");
			$(".comp-div .row").hide();
			parentRow.show();
			$("#FormDiv").show();
			$("#TableDiv").show();
			$("#FormDiv").data('compname', companyName);

		});

		$("#GetButton").click(function() {
			var fromDate = extractDate($("#FromDate input").val());
			var toDate = extractDate($("#ToDate input").val());
			var companyName = $(this).closest("#FormDiv").data('compname');

			showStocksChart(fromDate, toDate, companyName, '#KendoChart');

		});

		$(window).bind("hashchange", function(e) {
			var url = location.href;
			if (!url.includes('#')) {
				 $("#FormDiv").hide();
				 $("#KendoChart").hide();
				 $(".comp-div .row").show();
			}
		})
	});

	$(function() {
		//$("#FromDate").datetimepicker({format: 'L'});
		//$("#ToDate").datetimepicker({format: 'L'});
	})
</script>
