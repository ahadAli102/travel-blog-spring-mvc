<!DOCTYPE html>

<%@page import="java.util.Map"%>
<%@page import="com.ahad.model.User"%>
<%@include file="profile_nav.jsp"%>
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
		
		<div class="row row-cols-1 row-cols-md-2 g-4 mt-2">
			<!-- user profile -->
			<div class="col mt-1" style="border-radius: 15px;">
				<div class="card p-4">
					<div class="d-flex text-black">
						<div class="flex-shrink-0">
							<img src=<%=myImage%> alt="Generic placeholder image"
								class="img-fluid" style="width: 180px; border-radius: 10px;">
						</div>
						<div class="flex-grow-1 ms-3">
							<h5 class="mb-1"><%=user.getName() %></h5>
							<p class="mb-2 pb-1" style="color: #2b2a2a;"><%=user.getEmail() %></p>
							<div class="d-flex justify-content-start rounded-3 p-2 mb-2"
								style="background-color: #efefef;">
								<div>
									<p class="small text-muted mb-1">Vlogs</p>
									<p class="mb-0"><%= 50%></p>
								</div>
								<div class="px-3">
									<p class="small text-muted mb-1">Ratings</p>
									<p class="mb-0"><%= 100%></p>
								</div>
								<div>
									<p class="small text-muted mb-1">Rating</p>
									<p class="mb-0"><%= 5.10%></p>
								</div>
							</div>
							
						</div>
					</div>
				</div>
			</div>
			<div class="col mt-1" style="border-radius: 15px;">
				<form class="card p-5" action="/travel-vlog/profile/uploadImage" method="post" 
					enctype="multipart/form-data">
					<label class="form-label" for="customFile">Select image to
						change profile</label> 
					<input type="file" accept="image/*"
						class="form-control" id="customFile" name="file" /> 

					<div class="text-center mt-4">
						<button type="button" class="btn btn-primary"
							data-bs-toggle="modal" data-bs-target="#exampleModal"
							style="width: 100px">UPLOAD</button>
					</div>
					<!-- Vertically centered modal -->
					<div class="modal fade" id="exampleModal" tabindex="-1"
						aria-labelledby="exampleModalLabel" aria-hidden="true">
						<div class="modal-dialog modal-dialog-centered">
							<div class="modal-content">
								<div class="modal-header">
									<h5 class="modal-title" id="exampleModalLabel"><%="Hey "+user.getName()+"!"%></h5>
									<button type="button" class="btn-close" data-bs-dismiss="modal"
										aria-label="Close"></button>
								</div>
								<div class="modal-body">Do you want to change your
									existing profile picture?</div>
								<div class="modal-footer">
									<button type="button" class="btn btn-secondary"
										data-bs-dismiss="modal">NO</button>
									<button type="submit" class="btn btn-primary">YES</button>
								</div>
							</div>
						</div>
					</div>
				</form>
			</div>
		</div>
		<div class="card container mt-2 bg-secondary bg-gradient">
			<h3 class="text-center">Add Vlog</h3>
			<%
			if (request.getSession().getAttribute("profile_add_vlog_value_error") != null) {
				System.out.println("profile_add_vlog_value_error");
			%>
			<div class="alert alert-danger" role="alert">
				<form:errors path="vlog.*" />
			</div>
			<%request.getSession().removeAttribute("profile_add_vlog_value_error");
			}%>
			<%
			if (request.getSession().getAttribute("profile_add_vlog_exception_message") != null) {
			%>
			<div class="alert alert-danger" role="alert">
				<%="please select image and videos"%>
			</div>
			<%request.getSession().removeAttribute("profile_add_vlog_exception_message");
			}%>
			<form method="post" class="row row-cols-1 row-cols-md-2 g-4 mt-4" enctype="multipart/form-data"
				  action="/travel-vlog/profile/uploadVlog">
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