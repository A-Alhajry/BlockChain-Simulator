<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html dir="rtl">
	<head>
		<meta charset="UTF-8">
		<link href="css/bootstrap.min.css" rel="stylesheet">
		<link href="css/style.css" rel="stylesheet">
		<link href="https://fonts.googleapis.com/css?family=Reem+Kufi" rel="stylesheet">
		
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
		<script src="js/bootstrap.min.js"></script>
		<script src="js/script.js"></script>
		
		<style>
			h1 {
				font-family : 'Reem Kufi';
			}
		</style>
	</head>
	<body>
		<div class="container">
			<div class="row h-100 align-items-center">
				<div class="col text-center">
					 <h1 class="site-font">محرك بحث فتاوى إسلامية</h1>
					 <br/>
					 <form id="SearchForm" action="/IslamicSearchEngine/search" method="get" class="col-md-12">
					 	<div class="form-group col-md-7">
					 		<input type="text" name="text" class="form-control" placeholder="	ابحث ........" />
					 	</div>
					 	<div class="form-group col-md-1 padd-0">
					 		<button type="submit" class="btn btn-primary" style="vertical-align: baseline; width: 100%;">   إذهب</button>
					 	</div>
					 </form>
				</div>
			</div>
		</div>
	</body>
</html>
