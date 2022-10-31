<!DOCTYPE html>

<%@page import="java.util.ArrayList"%>
<%@page import="com.ahad.model.Vlog"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="com.ahad.model.User"%>
<%@include file="vlog_nav.jsp"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<title>Vlog</title>
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
		Vlog vlog =(Vlog) request.getAttribute("display_single_vlog");
		
	%>
	<div class="container pt-1 pb-3">

				<div class="card bg-secondary">
					<!-- vlog start -->
						<div class="card-body">
							<div class="flex-grow-1 ms-3">
								<h5 class="mb-1"><%=vlog.getUser().getName() %></h5>
								<p class="mb-2 pb-1" style="color: #2b2a2a;"><%=vlog.getDescription() %></p>
							</div>
							<div id="carouselExampleIndicators" class="carousel slide"
								data-bs-ride="true">
								<div class="carousel-indicators">
								<%for(int i = 0; i<vlog.getImageUrl().size();i++){ 
									if(i==0){%>
										<button type="button"
											data-bs-target="#carouselExampleIndicators"
											data-bs-slide-to="0" class="active" aria-current="true"
											aria-label="Slide 1"></button>
									<%}
									else{%>
										<button type="button"
											data-bs-target="#carouselExampleIndicators"
											data-bs-slide-to="<%=i %>" aria-label="Slide <%=i+1 %>"></button>
									<%}
								} %>
								</div>
								
								<div class="carousel-inner">
								<%for(int i = 0; i<vlog.getImageUrl().size();i++){ %>
									<%if(i == 0){%>
										<div class="carousel-item active">
										<img
											src="http://localhost:8080/travel-vlog/FetchFile?fileName=<%=vlog.getImageUrl().get(i)%>"
											class="d-block h-80 w-100" alt="...">
										</div>
									<%}
									else{%>
										<div class="carousel-item">
										<img
											src="http://localhost:8080/travel-vlog/FetchFile?fileName=<%=vlog.getImageUrl().get(i)%>"
											class="d-block h-80 w-100" alt="...">
										</div>
									<%}
								} %>
								</div>
								<button class="carousel-control-prev" type="button"
									data-bs-target="#carouselExampleIndicators" data-bs-slide="prev">
									<span class="carousel-control-prev-icon" aria-hidden="true"></span>
									<span class="visually-hidden">Previous</span>
								</button>
								<button class="carousel-control-next" type="button"
									data-bs-target="#carouselExampleIndicators" data-bs-slide="next">
									<span class="carousel-control-next-icon" aria-hidden="true"></span>
									<span class="visually-hidden">Next</span>
								</button>
							</div>
						</div>
					<!-- vlog end -->
				</div>
			
			
			<%for(int i = 0; i<vlog.getVideoUrl().size();i++){ %>
				<div class="embed-responsive embed-responsive-16by9">
				  <iframe class="embed-responsive-item h-80 w-100" 
				  src="http://localhost:8080/travel-vlog/FetchFile?fileName=<%=vlog.getVideoUrl().get(i)%>"></iframe>
				</div>				
			<%} %>
				
			

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