<!DOCTYPE html>

<%@page import="java.util.Map"%>
<%@page import="com.ahad.model.User"%>
<%@include file="profile_nav.jsp"%>
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
		String myImage = "data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD/2wCEAAkGBwsPDw4NEBANDQ0NDgoNDw0NCg8NDg0OFREWFhURFRMYHSggGBoxGxUTITEhJSkrLi4uFx8zODM2NyguLjcBCgoKDQ0NDg0ODisZFRkrKysrKysrKysrKysrKysrKysrKysrKysrKysrKysrKysrKysrKysrKysrKysrKysrK//AABEIAOEA4QMBIgACEQEDEQH/xAAaAAEBAQEBAQEAAAAAAAAAAAAAAQUEAwIH/8QALxABAAEDAgMHAgYDAAAAAAAAAAECAxEEITFBURIiMmFxkbEF0UJygYKhwVJi8f/EABUBAQEAAAAAAAAAAAAAAAAAAAAB/8QAFBEBAAAAAAAAAAAAAAAAAAAAAP/aAAwDAQACEQMRAD8A/VEFVEFAQUBBQEFAQUBBQEFAQUBBQEFAQUBBQEFARQBFRQAAAAAAAAAAAAAAAAAAAAAAAAAAAARUUAAAAAAAAAAAAAAAAAAAAAAAAAAAAEVFAAAAAAAAAB43NRRTtM79I3kHsk1RHSPWcM69q654d2PLi559/UGxFdPWPeH0xMPSm7XHCqfcGuM2jWVxx70ebts36a+HHpzB6gAAAAAAAAAAAiooAAAAAAAOXX3cRFMfi+Aeeq1X4aZ9avs4wUAAAAFpmYnMbT1QBqaa924844x/b2ZWmudmqJ5TtPo1UAAAAAAAAAAEVFAAAAAAAZerr7Vc9I7rTmcRnpuxgAFAAAAAABraerNFM/6x9mS1NL4KfT+0HsAAAAAAAAACKigAAAAAA87/AIavy1fDJa1/wVflq+GSAAoAAAAAANPReCn93yzGlovBT+75QdAAAAAAAAAAIqKAAAAAADj192qMUxtExOXC7PqMb0z6uMABQAAAAAAdehu1Z7HLE7dHI6NBHf8ASJQaQAAAAAAAAAIqKAAAAAADm19Oac9JhnNiumJiYnhMYlm6mx2Mb5ic8geICgAAAAAA7fp1Piq9Ic1i125xnG2c4adq3FMRTHL+UH2AAAAAAAAACKigAAAAAAPDV2+1TPWN4e4DFHvq7PZnMeGrePKejwUAAAAAeli1Nc45c58gdegt4p7X+Xw60iIjaOEbKgAAAAAAAAAAiooAAAAAAAIDx1sdyfLEsxpa6qIomOc4ZoACgAA7/p8d2Z6y4Hd9OqjExzyg7BFAAAAAAAAAABFRQAAAABHnc1FFPGd+kbyD0cus1FVMxTG2Yznm87uumfDGPOd5ctVUzOZnM+YJMzO87z5gKAAAABAA7NJqK5mKZ34784dzFiZjeNpdNrW1Rx70e0oNEeFvVW6ueJ6Ts9gUAAAAAAAEVFAHzXXFMZnaIZ1/VVVcO7T0B3XL9FPGd+kby5rmu/xj9Z+zjAeld+urjM+kbQ8wUAAAAAAAAAAAAH3Rdrp4TMeXJ8AOu3rp/FET6TifZ029Tbq54npOzLEGyrKs366eG8dJaNq5FUZj/k9AegAAAIqAOH6hc3ijlG8uR6amc11T549tnmoAAAAAAAAAAAAAAAAAAAAPfRXMVY5VbfryeC0ziYnpMT/INkRUAAEFAZF/xVfmq+XwCgAAAAAAAAAAAAAAAAAAAAADZUEAAH//2Q==";
	
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
	</div>
		

	<script
		src="${pageContext.request.contextPath}/webjars/jquery/1.9.1/jquery.min.js"
		defer></script>
	<script
		src="${pageContext.request.contextPath}/webjars/bootstrap/5.1.3/js/bootstrap.min.js"
		defer></script>


</body>
</html>