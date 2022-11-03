<!DOCTYPE html>

<%@page import="com.ahad.model.Comment"%>
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
		Map<String,Object> ratingMap = (Map<String,Object>) request.getAttribute("vlog_rating_info");
		String email = ((User) request.getSession().getAttribute("login_user")).getEmail();
		List<Comment> comments = (ArrayList<Comment>) request.getAttribute("vlog_comments");
	%>
	<div class="container pt-1 pb-3">

		<div class="card bg-secondary">
		<!-- vlog start -->
			<div class="card-body">
				<div class="flex-grow-1 ms-3">
					<h5 class="mb-1"><%=vlog.getUser().getName() %></h5>
					<p class="mb-2 pb-1" style="color: #2b2a2a;"><%=vlog.getDescription() %></p>
				</div>
				<%
				if(vlog.getUser().getEmail().equals(email)){%>
					<form action="/travel-vlog/vlogs/delete" class="p-2" method="post">
						<input type="hidden" name="vlogId" value="<%=vlog.getId()%>" />
						<input type="submit" value="DELETE"
						class="btn btn-primary btn-lg" />
					</form>
				
				<%}%>
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
								data-bs-target="#carouselExampleIndicators" data-bs-slide="next">								<span class="carousel-control-next-icon" aria-hidden="true"></span>
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
				
		<div class="row">
			<div class="col card pb-2 mb-2">
				<div class="row">
					<p class="fs-5">
						Vlog Average Rating :
						<%=ratingMap.get("avg_rating")%></p>
				</div>
				<div class="row">
					<p class="fs-5">
						Total votes:
						<%=ratingMap.get("total_votes")%></p>
				</div>
				<div class="row">
					<form action="/travel-vlog/vlogs/rate-vlog" method="post">
						<label for="customRange2" class="form-label">Rate the article (OUT OF 10)</label> 
						<input 
							type="range" class="form-range" min="1" max="10" id="customRange2" name="rating"> 
						<input
							type="hidden" name="vlogId" value="<%=vlog.getId()%>" />
							
						<%String status = (String) session.getAttribute("rate_vlog_status");
						if (status != null) {%>
							<label class="form-label"><%=status%></label>
								
							<%session.removeAttribute("rate_vlog_status");
						}%>
						<div class="text-center">
							<button class="btn btn-outline-success" type="submit">
								POST</button>
						</div>
					</form>
				</div>
			</div>
		</div>
		<div class="row card p-3">
			<form action="/travel-vlog/vlogs/rate-author" method="POST">
				<label for="customRange2" class="form-label">Rate the author (OUT OF 10)</label> 
				<input 
					type="range" class="form-range" min="1" max="10" id="customRange2" name="rating"> 
				<input 
					type="hidden" name="authorEmail" value="<%=vlog.getUser().getEmail()%>" />
				<input
					type="hidden" name="vlogId" value="<%=vlog.getId()%>" />
				<%
					String authorStatus = (String) session.getAttribute("rate_author_status");
					if (authorStatus != null) {
					%>
					<label class="form-label"><%=authorStatus%></label>
					<%
						session.removeAttribute("rate_author_status");
					}
					%>
					<div class="text-center">
						<button class="btn btn-outline-success" type="submit">
							POST</button>
					</div>
			</form>
		</div>
		
		<div class="row d-flex justify-content-center mt-2">
		  <div class="col">
		    <div class="card shadow-0 border" style="background-color: #f0f2f5;">
		      <div class="card-body p-4">
		        <form class="form-outline mb-4" method="post" action="/travel-vlog/vlogs/<%=vlog.getId()%>/comment">
		          <input type="text" id="addANote" name="comment" class="form-control" placeholder="Type comment..." />
		          <input type="submit" value="POST"
							class="btn btn-primary btn-lg mt-1" />
		        </form>
		        <p>Comments</p>
				<%for(Comment comment : comments){ %>
			        <div class="card mb-4">
		        		<div class="card-body">
		        			<div class="d-flex flex-row align-items-center">
				                <img src="http://localhost:8080/travel-vlog/FetchFile?fileName=<%=comment.getCommenterImage()%>" width="25"
				                  height="25" />
				                <p class="small mb-0 ms-2"><%=comment.getCommenterName() %></p>
				            </div>
				          	<p><%=comment.getComment() %></p>
				        </div>
		      		</div>
		      <%} %>
		    </div>
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