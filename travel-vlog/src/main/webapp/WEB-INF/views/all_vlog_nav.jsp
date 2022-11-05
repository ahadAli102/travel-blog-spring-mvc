<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link href="webjars/bootstrap/5.1.3/css/bootstrap.min.css"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath}/webjars/bootstrap/5.1.3/css/bootstrap.min.css"
	rel="stylesheet">
</head>
<body>
	<nav
		class="navbar navbar-expand-lg navbar-light bg-light base-background text-center">
		<div class="container-fluid">
			<a class="navbar-brand" href="#">Travel vlog</a>
			<button class="navbar-toggler" type="button"
				data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
				aria-controls="navbarSupportedContent" aria-expanded="false"
				aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarSupportedContent">
				<ul class="navbar-nav me-auto mb-2 mb-lg-0">
					<li class="nav-item"><a class="nav-link active"
						aria-current="page"
						href="http://localhost:8080/travel-vlog/home">Feed</a></li>

					<li class="nav-item"><a class="nav-link active"
						aria-current="page"
						href="http://localhost:8080/travel-vlog/profile">Profile</a></li>
					
					<li class="nav-item"><a class="nav-link active"
						aria-current="page"
						href="http://localhost:8080/travel-vlog/vlogs/my">My Vlog</a></li>

				</ul>
				<form class="d-flex" action="/travel-vlog/home" method="get">
					<input class="form-control me-2" type="search" placeholder="Search"
						aria-label="Search" name="q">
					<button class="btn btn-outline-success me-2" type="submit">Search</button>
				</form>
				<form class="ml-2" method="get" action="/travel-vlog/logout">
					<button class="btn btn-outline-success" type="submit">Log
						Out</button>
				</form>
			</div>
		</div>
	</nav>
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