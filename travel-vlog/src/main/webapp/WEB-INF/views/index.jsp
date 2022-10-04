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


</head>
<style>
body {
	background: url('https://wallpaperaccess.com/full/1431610.jpg')
		no-repeat center center fixed;
	-webkit-background-size: cover;
	-moz-background-size: cover;
	background-size: cover;
	-o-background-size: cover;
}
</style>
<body>


	<nav class="navbar navbar-expand-lg navbar-dark shadow-5-strong">
		<div class="container-fluid">
			<a class="navbar-brand" href="#">Travel Vlog</a>
			<button class="navbar-toggler" type="button"
				data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
				aria-controls="navbarSupportedContent" aria-expanded="false"
				aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarSupportedContent">
				<ul class="navbar-nav me-auto mb-2 mb-lg-0">
					<li class="nav-item"><a class="nav-link active"
						aria-current="page" href="home">Home</a></li>
					<li class="nav-item"><a class="nav-link" href="profile">Profile</a></li>
				</ul>
				<form class="d-flex">
					<a href="https://www.linkedin.com/in/ahad-ali-66266b193/"> <img
						src="https://img.icons8.com/color/48/000000/linkedin-circled--v1.png" />
					</a> <a href="https://github.com/ahadAli102"> <img
						src="https://img.icons8.com/ios-glyphs/48/000000/github.png" />
					</a>
				</form>
			</div>
		</div>
	</nav>

	<div class="container-fluid clip base-background p-6">
		<h1 class="text-white">The journey of a thousand miles begins
			with a single step.</h1>
		<p class="text-white">If a man stays in one place for a long time,
			he becomes monotonous. Travelling removes our monotony and gives
			pleasure.Travelling is an amazing way to learn a lot of things in
			life. A lot of people around the world travel every year to many
			places. Moreover, it is important to travel to humans. Some travel to
			learn more while some travel to take a break from their life.</p>
		<div class="col-md-12 text-center">
			<a href="login" type="button"
				class="btn btn-outline-secondary text-white">login</a> <a
				href="signup" type="button"
				class="btn btn-outline-secondary text-white">signup</a>

		</div>
	</div>

	<script src="webjars/jquery/1.9.1/jquery.min.js"></script>
	<script src="webjars/bootstrap/5.1.3/js/bootstrap.min.js"></script>
</body>
</html>