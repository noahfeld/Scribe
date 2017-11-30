
<!-- Scribe
  -
  - Vincent Rodriguez
  - Micahel Takla
  - Felipe Mansilla
  - Noah Feldman
  -
  -->

<%User currUser = (User)request.getSession().getAttribute("currUser");%>
<%@include file="NavBar.jsp"%>
<!--  the start tags here that are commented out are included in the nav bar JSP's
<html>
	<body> -->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/Search.css">
		<div class="content container-fluid">
			<div class="row">
				<div class="class-title-container col-xs-12 col-sm-12 col-md-12 col-lg-12">
					<div class="header">
						<div class="row">
							<div class="title-container col-xs-8 col-sm-8 col-md-8 col-lg-8">
								Search Results for "<%= request.getParameter("search")%>"
							</div>
							<div class="title-buffer col-xs-4 col-sm-4 col-md-4 col-lg-4">
							</div>
						</div>
					</div>
					<div class="main">
						<div class="results-container">
						<%
							Classroom classroom = JDBCQuery.getClassFromClassname(request.getParameter("search"));
							if(classroom != null)
							{
						%>
							<div class="result-container">
								<div class="row">
									<div class="class-title-container col-xs-6 col-sm-6 col-md-6 col-lg-6">
										<form id= "classItem" action="${pageContext.request.contextPath}/GoToClassServlet">
											<input type="text" class="hidden" name="classroom" value=<%=classroom.getClassname()%>> <!-- need to hide this field -->
											<button type="submit" class="btn btn-secondary"><%=classroom.getClassname()%></button>
										</form>
									</div>
									<div class="class-buffer col-xs-2 col-sm-2 col-md-2 col-lg-2">
									</div>
									<div class="class-info-container col-xs-4 col-sm-4 col-md-4 col-lg-4">
										<div class="row">
											<div class="members-container col-xs-6 col-sm-6 col-md-6 col-lg-6">
												Members: <%=JDBCQuery.getUsersEnrolledInClass(classroom.getClassID()).size() %>
											</div>
											<div class="privacy-container col-xs-6 col-sm-6 col-md-6 col-lg-6">
											<%
												if(classroom.isPrivate())
												{
											%>
												Private
											<%
												} else {
											%>
												Public
											<%
												}
											%>
											</div>
										</div>
									</div>
								</div>
							</div>
						<%
							}
						%>
						</div>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>