
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
	<body>  -->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/Profile.css">
		<div class="content container-fluid">
			<div class="header">
				<div class="row">
					<div class="title-container col-xs-8 col-sm-8 col-md-8 col-lg-8">
						<c:if test = "${sessionScope.signedIn}">
							Welcome <c:out value = "${sessionScope.currUser.getUsername()}"/>
						</c:if>
						<c:if test = "${!sessionScope.signedIn}">
							Welcome Guest
						</c:if>
					</div>
					<div class="title-buffer col-xs-4 col-sm-4 col-md-4 col-lg-4">
					</div>
				</div>
			</div>
			<div class="main">
				<div class="row">
					<div class="classes-container col-xs-4 col-sm-4 col-md-4 col-lg-4">
						<div class="header">
							Classes
						</div>
						<div class="main">
							<c:if test = "${sessionScope.signedIn}">
								<%
									Vector<Classroom> enrollments = JDBCQuery.getUserEnrollments(currUser.getUserID());
									for(Classroom enrollment:enrollments) {
								%>
									<div class="class-container">
										<form id= "classItem" action="${pageContext.request.contextPath}/GoToClassServlet">
											<input type="text" class="hidden" name="classroom" value=<%=enrollment.getClassname()%>> <!-- need to hide this field -->
											<button type="submit" class="btn btn-secondary"><%=enrollment.getClassname()%></button>
										</form>
									</div>
								<% 
									}
								%>
							</c:if>
						</div>
					</div>
					<div class="resources-container col-xs-8 col-sm-8 col-md-8 col-lg-8">
						<div class="header">
							Resources
						</div>
						<div class="main">
							<c:if test = "${sessionScope.signedIn}">
								<%
									Vector<UserDocument> myDocuments = JDBCQuery.getUserDocuments(currUser.getUserID());
									for(UserDocument document:myDocuments) {
								%>
									<div class="resource-container">
										<a href="ViewFile.jsp?id=<%=document.getDocID()%>"><%=document.getName()%></a>
									</div>
								<% 
									}
								%>
							</c:if>
						</div>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>