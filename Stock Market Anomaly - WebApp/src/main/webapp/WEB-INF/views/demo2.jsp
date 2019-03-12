<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<!DOCTYPE htm>

<html>
	<head>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
		<jsp:include page="kendo-ui-imports.jsp"></jsp:include>
		<script src="js/script.js"></script>
	</head>
	<script>
		stocks = ${Stocks}
	</script>
	<body>
		<div id="Content" class="container">
			<div class="demo-section k-content wide">
				<div id="KendoChart">
					
				</div>
			</div>
		</div>
	</body>
	<script>
		$(document).ready(function() {
			showKendoSeries("#KendoChart", stocks);
		})
		$(document).bind("kendo:skinChange", function() {
			showKendoSeries("#KendoChart", stocks);
		})
	</script>
</html>