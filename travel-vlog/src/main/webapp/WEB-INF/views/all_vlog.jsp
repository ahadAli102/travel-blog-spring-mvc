<!DOCTYPE html>

<%@page import="java.util.ArrayList"%>
<%@page import="com.ahad.model.Vlog"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="com.ahad.model.User"%>
<%@include file="all_vlog_nav.jsp"%>
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
		User user = (User) request.getSession().getAttribute("login_user");
		Map<String, Object> rating = null;
		List<Vlog> vlogs = (ArrayList<Vlog>) request.getAttribute("all_vlogs");
		int previous = (Integer) request.getAttribute("previous_page");
		int current = (Integer) request.getAttribute("current_page");
		int next = (Integer) request.getAttribute("next_page");
		System.out.println(previous+"-"+current+"-"+next);
		String query = null;
		if(request.getAttribute("all_vlogs_query") != null){
			query = new StringBuffer().append("&q=").append(request.getAttribute("all_vlogs_query").toString()).toString();
		}
		else{
			query="";
		}
	%>
	<div class="container pt-1 pb-3">
		<div class="row mt-2">
		<%for(Vlog vlog : vlogs){%>
			<div class="col-sm-6">
				<div class="card bg-secondary">
					<!-- vlog start -->
					<div class="card-header">
						<div class="d-flex text-black">
							<div class="flex-shrink-0">
								<img src="http://localhost:8080/travel-vlog/FetchFile?fileName=<%=vlog.getUser().getImage()%>"
									alt="Generic placeholder image" class="img-fluid"
									style="width: 100px; border-radius: 10px;">
							</div>
							<div class="flex-grow-1 ms-3">
								<h5 class="mb-1"><%=vlog.getUser().getName() %></h5>
								<%
									String desc = vlog.getDescription().length()<100 ?
											vlog.getDescription() : vlog.getDescription().substring(0,90).concat(" more...");
								%>
								<p class="mb-2 pb-1" style="color: #2b2a2a;"><%=desc %></p>
								
								
							</div>
						</div>

						<!-- vlog end -->
					</div>
					<div class="card-body">
						<div class="album">
							<div class="row justify-content-around">
								<div class="column">
									<img
										src="http://localhost:8080/travel-vlog/FetchFile?fileName=<%=vlog.getImageUrl().get(0)%>"
										alt="image 1" class="img-fluid"
										style="border-radius: 10px;">
								</div>
							</div>
						</div>
					</div>
					<div class="card-footer">
						<a class="text-black"
							href="http://localhost:8080/travel-vlog/vlogs/<%=vlog.getId()%>">SHOW
							MORE</a>
					</div>
				</div>
			</div>
			<%} %>
		</div>
		
		<div class="card text-center">
			<div class="card-body d-flex" >
				<%if(previous==0){ %>
					<a class="nav-link disabled" aria-current="page"
						href="http://localhost:8080/travel-vlog/home?page=<%=previous+query %>"> Previous</a>
				<%}else { %>
					<a class="nav-link active" aria-current="page"
						href="http://localhost:8080/travel-vlog/home?page=<%=previous+query %>"> Previous</a>
				<%} %>
				
				<h3> <%=current %>  </h3>
				<%if(vlogs.size()==4){ %>
					<a class="nav-link active" aria-current="page"
						href="http://localhost:8080/travel-vlog/home?page=<%=next+query %>"> Next</a>
				<%} else{%>
					<a class="nav-link disabled" aria-current="page"
						href="http://localhost:8080/travel-vlog/home?page=<%=next+query %>"> Next</a>
				<%} %>
				
			</div>
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