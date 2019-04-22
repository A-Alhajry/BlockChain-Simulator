<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<html>
	<head>
		<meta charset="UTF-8">
		<link href="css/bootstrap.min.css" rel="stylesheet">
		<link href="css/style.css" rel="stylesheet">
		<link href="https://fonts.googleapis.com/css?family=Reem+Kufi" rel="stylesheet">
		
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
		<script src="js/bootstrap.min.js"></script>
		<script src="js/script.js"></script>
	</head>
	
	<body dir="rtl">
	
		<div class="container" style="margin-top: 40px; text-align: right; margin-right: 40px;">
			<div class="row">
				<div class="col-md-12">
					<span class="col-md-4 arabic-font inline inline-form-title">محرك بحث فتاوى إسلامية</span>
					<form id="SearchForm" action="/IslamicSearchEngine/search" method="get" class="col-md-8 inline">
						<div class="form-group inline col-md-10" style="width: 80%;">
							<input id="SearchInput" name="text" class="form-control" type="text" value="${SearchText}" placeholder="	ابحث ........"/>
						</div>
						<div class="form-group inline col-md-2">
							<button type="submit" class="btn btn-primary vert-base">إذهب</button>
						</div>
					</form>
			    </div>
			</div>
			
			<div class="row" style="margin-top: 40px;">
				<c:forEach var="result" items="${requestScope.Result}">
					<div class="col-md-12">
						<div class="search-result">
						<a href="${result.url}" target="_blank">
							<span class="search-result-title"><strong>${result.title}</strong></span>
						</a>
						<p class="search-result-body">
							${result.snippet}
						</p>
						<span><strong>${result.sourceEngine.engineName} : </strong>Source</span>	
						</div>
					</div>
					
					<hr/>
				</c:forEach>
			</div>
			
		</div>
<%-- 		<c:forEach var="result" items="${requestScope.Result}"> --%>
<%-- 			<a href="${result.url}">${result.title}</a>		 --%>
<%-- 			<p>${result.snippet}</p> --%>
<!-- 			<br/>	 -->
<%-- 		</c:forEach> --%>
	</body>
</html>