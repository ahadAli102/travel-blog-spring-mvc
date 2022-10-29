<!DOCTYPE html>

<%@page import="java.util.Map"%>
<%@page import="com.ahad.model.User"%>
<%@include file="my_vlog_nav.jsp"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<title>Profile</title>
<meta charset="ISO-8859-1">
<link href="webjars/bootstrap/5.1.3/css/bootstrap.min.css"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath}/webjars/bootstrap/5.1.3/css/bootstrap.min.css"
	rel="stylesheet">
</head>
<style>
body {
	background:
		url('https://images.pexels.com/photos/572897/pexels-photo-572897.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1')
		no-repeat center center fixed;
	-webkit-background-size: cover;
	-moz-background-size: cover;
	background-size: cover;
	-o-background-size: cover;
}
</style>
<body>
	<%
		User user =(User) request.getSession().getAttribute("login_user");
		Map<String, Object> rating = null;
		String myImage = String.valueOf(request.getSession().getAttribute("image"));
	
	%>
	<div class="container pt-1 pb-3">
		
		<div class="card container mt-2 bg-secondary bg-gradient">
			<h3 class="text-center">Add Vlog</h3>
			<%
			if (request.getSession().getAttribute("my_vlog_value_error") != null) {
				System.out.println("my_vlog_value_error");
			%>
			<div class="alert alert-danger" role="alert">
				<form:errors path="vlog.*" />
			</div>
			<%request.getSession().removeAttribute("my_vlog_value_error");
			}%>
			<%
			if (request.getSession().getAttribute("my_vlog_exception_message") != null) {
			%>
			<div class="alert alert-danger" role="alert">
				<%="please select image and videos"%>
			</div>
			<%request.getSession().removeAttribute("my_vlog_exception_message");
			}%>
			<form method="post" class="row row-cols-1 row-cols-md-2 g-4 mt-4" enctype="multipart/form-data"
				  action="/travel-vlog/vlogs/my/uploadVlog">
				<!-- user profile -->
				<div class="col mt-3">
					<div class="d-flex flex-row align-items-center mb-4">
						<i class="fas fa-user fa-lg me-3 fa-fw"></i>
						<div class="form-outline flex-fill mb-0">
							<input type="text" name="location" id="form3Example1c"
								class="form-control" /> <label class="form-label"
								for="form3Example1c">Location</label>
						</div>
				    </div>
				    <div class="d-flex flex-row align-items-center mb-4 ">
						<i class="fas fa-envelope fa-lg me-3 fa-fw"></i>
						<div class="form-outline flex-fill mb-0 rows=3">
							<textarea class="form-control" id="form3Example3c" rows="3"
								name="description"></textarea>
							<label class="form-label" for="form3Example3c">Description</label>
						</div>
					</div>
				</div>
				<div class="col mt-3">
					<div class="row pl-1 pe-2">
			            <label for="formFileMultipleImage" class="form-label">Select images</label>
					    <input class="form-control" accept="image/*" name="images" type="file" id="formFileMultipleImage" multiple />
			        </div>
			        <div class="row pl-1 pe-2">
			            <label for="formFileMultipleVideo" class="form-label">Select Videos</label>
					    <input class="form-control" accept="video/*" name="videos" type="file" id="formFileMultipleVideo" multiple />
			        </div>
			        <div class="row mt-1 pl-1 pe-2">
			        	<div class="text-center">
			      	    	<button type="submit" class="btn btn-primary text-center" style="width: 100px">UPLOAD</button>
			        	</div>
			        </div>
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