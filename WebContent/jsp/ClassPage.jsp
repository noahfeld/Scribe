
<!-- Scribe
  -
  - Vincent Rodriguez
  - Micahel Takla
  - Felipe Mansilla
  - Noah Feldman
  -
  -->

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
boolean signedIn = (boolean)request.getSession().getAttribute("signedIn");
User currUser = (User)request.getSession().getAttribute("currUser");
Classroom currClass = (Classroom)request.getSession().getAttribute("currClass");
%>
<%@include file="NavBar.jsp"%>
<!--  included in NavBar.jsp
<html>
	<body> -->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/Class.css">
		<c:if test="${signedIn}">
			<noscript id="username"><%=currUser.getUsername()%></noscript>
			<noscript id="userId"><%=currUser.getUserID()%></noscript>
			<noscript id="classId"><%=currClass.getClassID()%></noscript>
		</c:if>
		<div class="content container-fluid">
			<div class="header">
				<div class="row">
					<div class="title-container col-xs-8 col-sm-8 col-md-8 col-lg-8">
						Welcome to <%= currClass.getClassname()%>
					</div>
					<div class="requestbutton-container col-xs-4 col-sm-4 col-md-4 col-lg-4">
					<%
						if(signedIn && !JDBCQuery.isUserEnrolledInClass(currClass.getClassID(), currUser.getUserID())) {
							if(currClass.isPrivate()) { %>
								<button onclick="sendRequest()" class="btn btn-secondary" id="requestButton" name="requestButton">Request to Join Class</button>
							<%} else { %>
								<button onclick="joinClass()" class="btn btn-secondary" id="joinButton" name="joinButton">Join Class</button>
							<%}
						}
					%>
					</div>
				</div>
			</div>
			<div class="main">
				<div class="row">
					<div class="members-container col-xs-2 col-sm-2 col-md-2 col-lg-2">
						<div class="header">
							Members
						</div>
						<div class="main">
						<%
							Vector<User> students = JDBCQuery.getUsersEnrolledInClass(currClass.getClassID());
							for(User student:students)
							{
						%>
							<div class="student-container">
								<%= student.getUsername() %>
							</div>
						<%
							}					
						%>
						</div>
					</div>
					<div class="discussion-container col-xs-6 col-sm-6 col-md-6 col-lg-6">
						<div class="header">
							Discussion
						</div>
						<div class="main" id="discussion-main">
						<%
							if(signedIn && (!currClass.isPrivate() || JDBCQuery.isUserEnrolledInClass(currClass.getClassID(), currUser.getUserID()))) {
								Vector<User> requests = JDBCQuery.getUsersWithRequests(currClass.getClassID());
								for(int i = requests.size() - 1; i >=0; i--) { %>
									<p><%=requests.get(i).getUsername()%> has requested to join this group! <noscript id="requestName"><%=requests.get(i).getUsername()%></noscript><noscript id="requestId"><%=requests.get(i).getUserID()%></noscript><button class="button" id="acceptButton" onclick="acceptRequest()">Accept</button></p>
							<%	}
								Vector<ClassMessage> classMessages = JDBCQuery.getMessagesFromClass(currClass.getClassID());
								for(int i = classMessages.size()-1; i >= 0; i--)
								{
							%>
								<p><%=classMessages.get(i).getContent()%></p>
							<%
								}
							}
						%>
						</div>
						<div class="footer">
						<%
							if(signedIn && (!currClass.isPrivate() || JDBCQuery.isUserEnrolledInClass(currClass.getClassID(), currUser.getUserID()))) {
						%>
		
								<form name="discussionBoard" onsubmit="return sendMessage();">
									<div class="form-group">
										<input type="text" class="form-control form-element" name="message" placeholder="Type Here!" />
									</div>
									<div class="form-group">
										<button type="submit" class="btn btn-secondary">Send</button>
									</div>
								</form>
						<%
							}
						%>
						</div>
					</div>
					<div class="resources-container col-xs-4 col-sm-4 col-md-4 col-lg-4">
						<div class="header">
							Resources
						</div>
						<div class="main">
						<%
							if(signedIn && (!currClass.isPrivate() || JDBCQuery.isUserEnrolledInClass(currClass.getClassID(), currUser.getUserID()))) {
								Vector<UserDocument> classDocuments = JDBCQuery.getClassUploads(currClass.getClassID());
								for(UserDocument document:classDocuments)
								{ %>
									<div class="resource-container">
										<a href="ViewFile.jsp?id=<%=document.getDocID()%>"><%=document.getName()%></a>
									</div>
								<%}
							} else {
								%>
									<div class="disabled-container">
									</div>
								<%
							}
						%>
						</div>
						<div class="footer">
						<%
							if(signedIn && (!currClass.isPrivate() || JDBCQuery.isUserEnrolledInClass(currClass.getClassID(), currUser.getUserID()))) {
						%>
							<form action="${pageContext.request.contextPath}/FileUploadServlet" method="POST" enctype="multipart/form-data" accept="mp3, mp4, docx">
								<div class="form-group">
									<input type="file" class="form-control form-element" id="file" name="file"/>
								</div>
								<div class="form-group" style="color: #FF0000;">
									<button type="submit" class="btn btn-secondary" id="button-upload">Upload</button>
								</div>
							</form>
						<%
							}
						%>
						</div>
					</div>
				</div>
			</div>
		</div>
		<script>document.getElementById("requestButton").style.display="none";</script>
		
		<%
			if(signedIn && !JDBCQuery.isUserEnrolledInClass(currClass.getClassID(),currUser.getUserID())) 
			{ 
		%>
			<script>document.getElementById("requestButton").style.display="inline";</script>
		<%
			}
		%>
	</body>
</html>