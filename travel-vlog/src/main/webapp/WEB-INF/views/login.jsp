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
<style>
.divider:after, .divider:before {
	content: "";
	flex: 1;
	height: 1px;
	background: #eee;
}

.h-custom {
	height: calc(100% - 73px);
}

@media ( max-width : 450px) {
	.h-custom {
		height: 100%;
	}
}
body {
	background:
		url('https://cdn.pixabay.com/photo/2017/11/16/09/59/camping-2953935_960_720.jpg')
		no-repeat center center fixed;
	-webkit-background-size: cover;
	-moz-background-size: cover;
	background-size: cover;
	-o-background-size: cover;
}
</style>
<body>
<%
	System.out.println(request.getAttribute("login_exception_message"));

%>

	<div class="row justify-content-center">
		<div class="col-md-10 col-lg-6 col-xl-5 order-2 order-lg-1">
			<p class="text-center h1 fw-bold mb-5 mx-1 mx-md-4 mt-4">Login</p>
			
			<%
			if (request.getAttribute("login_exception_message") != null) {
			%>
			<div class="alert alert-danger" role="alert">
				<%=request.getAttribute("login_exception_message")%>
			</div>
			<%}%>
			<form action="/travel-vlog/login/process" method="post">

				<!-- Email input -->
				<div class="form-outline mb-4">
					<input type="email" name="email" id="form3Example3"
						class="form-control form-control-lg"
						placeholder="Enter a valid email address" /> <label
						class="form-label" for="form3Example3">Email address</label>
				</div>

				<!-- Password input -->
				<div class="form-outline mb-3">
					<input type="password" name="password" id="form3Example4"
						class="form-control form-control-lg" placeholder="Enter password" />
					<label class="form-label" for="form3Example4">Password</label>
				</div>

				<div class="d-flex justify-content-between align-items-center">
					<a href="/travel-vlog/reset/password" class="text-body" >Forgot password?</a>
				</div>

				<div class="text-center text-lg-start mt-4 pt-2">
					<div class="d-flex justify-content-center mx-4 mb-3 mb-lg-4">
						<input type="submit" value="login"
							class="btn btn-primary btn-lg" />
					</div>
					<p class="small fw-bold mt-2 pt-1 mb-0 text-center text-white">
						Don't have an account? <a href="/travel-vlog/signup" class="link-danger">Register</a>
					</p>
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