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
		url('https://images.unsplash.com/photo-1585409677983-0f6c41ca9c3b?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1469&q=80')
		no-repeat center center fixed;
	-webkit-background-size: cover;
	-moz-background-size: cover;
	background-size: cover;
	-o-background-size: cover;
}
</style>
<body>


	<div class="row justify-content-center">
		<div class="col-md-10 col-lg-6 col-xl-5 order-2 order-lg-1">

			<p class="text-center h1 fw-bold mb-5 mx-1 mx-md-4 mt-4">Sign up</p>
			<%
						if ( request.getAttribute("reg_error_occured") != null) {
						%>
			<div class="alert alert-danger" role="alert">
				<form:errors path="user.*" />
			</div>
			<%
							}
						%>

			<form action="/travel-vlog/signup/process" method="post"
				class="mx-1 mx-md-4">

				<div class="d-flex flex-row align-items-center mb-4">
					<i class="fas fa-user fa-lg me-3 fa-fw"></i>
					<div class="form-outline flex-fill mb-0">
						<input type="text" name="name" id="form3Example1c"
							class="form-control" /> <label class="form-label text-white"
							for="form3Example1c">Your Name</label>
					</div>
				</div>

				<div class="d-flex flex-row align-items-center mb-4">
					<i class="fas fa-envelope fa-lg me-3 fa-fw"></i>
					<div class="form-outline flex-fill mb-0">
						<input type="email" name="email" id="form3Example3c"
							class="form-control" /> <label class="form-label text-white"
							for="form3Example3c">Your Email</label>
					</div>
				</div>

				<div class="d-flex flex-row align-items-center mb-4">
					<i class="fas fa-lock fa-lg me-3 fa-fw"></i>
					<div class="form-outline flex-fill mb-0">
						<input type="password" name="password" id="form3Example4c"
							class="form-control" /> <label class="form-label text-white"
							for="form3Example4c">Password</label>
					</div>
				</div>

				<div class="d-flex flex-row align-items-center mb-4">
					<i class="fas fa-key fa-lg me-3 fa-fw"></i>
					<div class="form-outline flex-fill mb-0">
						<input type="password" name="rePassword" id="form3Example4cd"
							class="form-control" /> <label class="form-label text-white"
							for="form3Example4cd">Repeat your password</label>
					</div>
				</div>

				<div class="form-check d-flex justify-content-center mb-2">
					<input class="form-check-input me-2" type="checkbox"
						name="condition" value="accepted" id="form2Example3c" /> <label
						class="form-check-label text-white" for="form2Example3"> I
						agree all statements in <a href="#!">Terms of service</a>
					</label>
				</div>
				<%
						if ( request.getAttribute("reg_succesfull") != null) {
				%>
							<label
								class="form-check-label text-white" for="form2Example3"> You have successfully registered Please click on verification mail
								<a href="/travel-vlog/login">Login</a>
							</label>
				<%
						}
				%>

				<div class="d-flex justify-content-center mx-4 mb-3 mb-lg-4">
					<input type="submit" value="Register"
						class="btn btn-primary btn-lg" />
				</div>

			</form>

		</div>


	</div>

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