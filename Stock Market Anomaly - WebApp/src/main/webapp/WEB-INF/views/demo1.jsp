<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<jsp:include page="jquery-flot-imports.jsp"></jsp:include>
<link href="css/bootstrap.min.css">
<script src="js/script.js"></script>
<style>
#Content {
	height: 100%;
	width: 100%;
}

.chart-div {
	height: 800px;
	width: 800px;
	margin: 0 auto;
}

.placeholder {
	height: 100%;
	width: 100%;
	margin: 100px auto;
}

#Tooltip {
	display: none;
	position: absolute;
	border: 1px solid #fdd;
	padding: 2px;
	background-color: #fee;
	opacity: 0.80;
}
</style>
</head>

<script>
	stocks = ${Stocks};
	console.log(stocks);
</script>
<body>

	<div id="Content" class="container">
		<div class="chart-div">
			<div id="Demo1" class="placeholder"></div>
			<div id="Demo2" class="placeholder"></div>
		</div>
		
		<div id="Tooltip">
			
		</div>
		
	</div>

</body>
<script>
	$(document).ready(function() {
		showSeries('#Demo1', stocks);
		//showSeries("#Demo2", stocks, {year: 2017, month: 1}, {year: 2017, month: 12});
	});

	
</script>
</html>