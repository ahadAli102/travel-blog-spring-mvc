<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>


<link href="webjars/bootstrap/5.1.3/css/bootstrap.min.css"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath}/webjars/bootstrap/5.1.3/css/bootstrap.min.css"
	rel="stylesheet">


</head>
<style>

body {
	background:
		url('https://cdn.pixabay.com/photo/2016/10/14/19/21/canyon-1740973_960_720.jpg')
		no-repeat center center fixed;
	-webkit-background-size: cover;
	-moz-background-size: cover;
	background-size: cover;
	-o-background-size: cover;
}
</style>
<body>



	<%
	String message = null;
	if (request.getAttribute("reg_verify_succesfull") != null) {
		message = "Your account has been verified";
	%>
	<div class="container"
		style="display: flex; justify-content: center; align-items: center; height: 100vh">
		<div class="w-50">
			<div class="alert alert-success" role="alert">
				<h4 class="alert-heading">Congratulation!!</h4>
				<p><%=message %></p>
				<hr>
				<p class="mb-0">You can login now</p>
			</div>
		</div>
	</div>





	<%} else if (request.getAttribute("reg_verify_exception_occured") != null) {
		message = (String) request.getAttribute("reg_verify_exception_message");%>

	<div class="container"
		style="display: flex; justify-content: center; align-items: center; height: 100vh">
		<div class="w-50">
			<div class="alert alert-danger" role="alert">
				<h4 class="alert-heading">Error!!</h4>
				<p><%=message %></p>
				<hr>
				<p class="mb-0">Please go to email again</p>
			</div>
		</div>
	</div>

	<%}%>



	<script src="webjars/jquery/1.9.1/jquery.min.js"></script>
	<script src="webjars/bootstrap/5.1.3/js/bootstrap.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/webjars/jquery/1.9.1/jquery.min.js"
		defer></script>
	<script
		src="${pageContext.request.contextPath}/webjars/bootstrap/5.1.3/js/bootstrap.min.js"
		defer></script>
</body>
</html>