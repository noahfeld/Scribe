
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
<%@ page import="backend.JDBCQuery" %>
<%@ page import="databaseObjects.ClassMessage" %>
<%@ page import="databaseObjects.Classroom" %>
<%@ page import="databaseObjects.User" %>
<%@ page import="databaseObjects.UserDocument" %>
<%@ page import="java.util.Vector" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<title>Scribe</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/Reset.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/Scribe.css">
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css" integrity="sha384-PsH8R72JQ3SOdhVi3uxftmaW6Vc51MKb0q5P2rRUpPvrszuE4W1povHYgTpBfshb" crossorigin="anonymous">
		<link rel='shortcut icon' type='image/x-icon' href='${pageContext.request.contextPath}/imgs/ScribeFavicon.ico'/>
		<script src="${pageContext.request.contextPath}/js/jquery-3.2.1.min.js"></script>
		<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.3/umd/popper.min.js" integrity="sha384-vFJXuSJphROIrBnz7yo7oB41mKfc8JzQZiCq4NCceLEaO4IHwicKwpJf9c9IpFgh" crossorigin="anonymous"></script>
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/js/bootstrap.min.js" integrity="sha384-alpBpkh1PFOepccYVYDB4do5UnbKysX5WZXm3XxPqe5iKTfUKjNkCk9SaVuEZflJ" crossorigin="anonymous"></script>
		<script>
			var socket;
			var requestButton = document.getElementById("requestButton");
			var requestButton = document.getElementById("addClass");
			function connectToServer() {
				socket = new WebSocket("ws://localhost:8080/Scribe/discussion");
				socket.onmessage = function(event) {
					document.getElementById("discussion-menu").innerHTML = event.data + "<br />" + document.getElementById("discussion-main").innerHTML;
				}
			}
			function sendMessage() {
				var uname = document.getElementById("username").innerHTML;
				var classId = document.getElementById("classId").innerHTML;
				var userId = document.getElementById("userId").innerHTML;
				socket.send(classId + " " + userId + " " + uname + ": " + document.discussionBoard.message.value);
				document.getElementByName("message").value = "";
				return false;
			}
			function sendRequest() {
				var uname = document.getElementById("username").innerHTML;
				var classId = document.getElementById("classId").innerHTML;
				var userId = document.getElementById("userId").innerHTML;
				socket.send("REQUEST " + classId + " " + userId + " " + uname + " has requested to join this group! <noscript id=\"requestName\">" + uname + "</noscript><noscript id=\"requestId\">" + userId + "</noscript><button class=\"button\" id=\"acceptButton\" onclick=\"acceptRequest()\">Accept</button>");
			}
			function acceptRequest(u_name, user_Id) {
				var classId = document.getElementById("classId").innerHTML;
				var acceptButton = document.getElementById("acceptButton");
				socket.send("ACCEPT " + document.getElementById("requestName").innerHTML + " " + classId + " " + document.getElementById("requestId").innerHTML);
				location.reload();
			}
			function joinClass() {
				var classId = document.getElementById("classId").innerHTML;
				var userId = document.getElementById("userId").innerHTML;
				socket.send("JOIN " + classId + " " + userId);
			}
		</script>
	</head>
	<body onload="connectToServer()">
		<nav class="navbar navbar-expand-lg navbar-light justify-content-between" style="background-color: #fcf9e3;">
			<a class="navbar-brand" href="#">Scribe</a>
			<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarNav">
				<form class="form-inline my-2 my-lg-0" action="Search.jsp">
					<input class="form-control mr-sm-2" type="search" name="search" placeholder="Search" aria-label="Search">
			    </form>
			    <ul class="navbar-nav">
					<li class="nav-item">
						<c:if test = "${sessionScope.signedIn}">
							<div class="dropdown">
								<button class="btn btn-secondary dropdown-toggle" type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
									Add Class
								</button>
								<form class="dropdown-menu p-4" action="${pageContext.request.contextPath}/ClassCreateServlet">
									<div class="form-group p-2">
										<label>Class Name</label>
										<input type="text" class="form-control" name="classname" style="width: 300px;">
									</div>
									<div class="form-radio .mx-auto" style="justify-content: center;">
										<label class="form-radio-label p-1">
											<input type="radio" class="form-radio-input" name="privacy" value="false" checked>
											Public
										</label>
										<lable class="form-radio-label p-1">
											<input type="radio" class="form-radio-input" name="privacy" value="true" checked>
											Private
										</lable>
									</div>
									<button type="submit" class="btn btn-secondary p-2 m-2">Add</button>
								</form>
							</div>
						</c:if>
						<c:if test = "${!sessionScope.signedIn}">
							<button class="btn btn-secondary dropdown-toggle disabled" type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
								Add Class
							</button>
						</c:if>
					</li>
					<li class="nav-item">
						<c:if test = "${sessionScope.signedIn}">
							<a class="nav-link" href="Profile.jsp">Profile</a>
						</c:if>
						<c:if test = "${!sessionScope.signedIn}">
							<a class="nav-link" href="Profile.jsp">Profile</a>
						</c:if>
					</li>
					<li class="nav-item">
						<c:if test = "${sessionScope.signedIn}">
							<a class="nav-link" href="Welcome.jsp">Log Out</a>
						</c:if>
						<c:if test = "${!sessionScope.signedIn}">
							<a class="nav-link" href="Welcome.jsp">Sign Up</a>
						</c:if>
					</li>
				</ul>
			</div>
		</nav>
