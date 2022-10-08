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
	String message = null;
	if (request.getAttribute("reset_password_succesfull") != null) {
		message = "Your have succesfully reset your password";
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
	<%} %>
	
	<%
	String message1 = null;
	if (request.getAttribute("reset_email_sent") != null) {
		message1 = "An email has been sent to your account";
	%>
	<div class="container"
		style="display: flex; justify-content: center; align-items: center; height: 100vh">
		<div class="w-50">
			<div class="alert alert-success" role="alert">
				<h4 class="alert-heading">Next step!!</h4>
				<p><%=message1 %></p>
				<hr>
				<p class="mb-0">Open that email and click a link to continue further</p>
			</div>
		</div>
	</div>
	<%} %>

	<div class="row justify-content-center">
		<div class="col-md-10 col-lg-6 col-xl-5 order-2 order-lg-1">
			<p class="text-center h1 fw-bold mb-5 mx-1 mx-md-4 mt-4">Reset Password</p>
			
			<%
			if (request.getAttribute("reset_password_exception_message") != null) {
			%>
			<div class="alert alert-danger" role="alert">
				<%=request.getAttribute("reset_password_exception_message")%>
			</div>
			<%}%>
			
			<%
			if (request.getAttribute("reset_email_form") != null) {
			%>
			<form action="/travel-vlog/reset/password/email" method="post">

				<!-- Email input -->
				<div class="form-outline mb-4">
					<input type="email" name="email" id="form3Example3"
						class="form-control form-control-lg"
						placeholder="Enter a valid email address" /> <label
						class="form-label" for="form3Example3">Email address</label>
				</div>

				<div class="d-flex justify-content-between align-items-center">
					<p class="text-body" >Email will be sent to your account</p>
				</div>

				<div class="text-center text-lg-start mt-4 pt-2">
					<div class="d-flex justify-content-center mx-4 mb-3 mb-lg-4">
						<input type="submit" value="SEND"
							class="btn btn-primary btn-lg" />
					</div>
				</div>

			</form>
			<%}%>
			
			<%
			if (request.getAttribute("reset_password_form") != null) {
			%>
			<form action="/travel-vlog/reset/password/submit" method="post">

				<!-- Password input -->
				<div class="form-outline mb-3">
					<input type="password" name="password" id="form3Example4"
						class="form-control form-control-lg" placeholder="Enter password" />
					<label class="form-label" for="form3Example4">Password</label>
				</div>

				<!-- Password input -->
				<div class="form-outline mb-3">
					<input type="password" name="rePassword" id="form3Example4"
						class="form-control form-control-lg" placeholder="Enter password again" />
					<label class="form-label" for="form3Example4">Confirm Password</label>
				</div>

				<div class="text-center text-lg-start mt-4 pt-2">
					<div class="d-flex justify-content-center mx-4 mb-3 mb-lg-4">
						<input type="submit" value="CONFIRM"
							class="btn btn-primary btn-lg" />
					</div>
				</div>

			</form>
			<%}%>
			

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