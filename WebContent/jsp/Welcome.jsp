
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

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Scribe</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/Reset.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/Scribe.css">
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/Welcome.css">
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css" integrity="sha384-PsH8R72JQ3SOdhVi3uxftmaW6Vc51MKb0q5P2rRUpPvrszuE4W1povHYgTpBfshb" crossorigin="anonymous">
		<link rel='shortcut icon' type='image/x-icon' href='${pageContext.request.contextPath}/imgs/ScribeFavicon.ico'/>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
	</head>
	<body>
		<div class="content container-fluid">
			<div class="row">
				<div class="content-buffer col-xs-2 col-sm-2 col-md-2 col-lg-2"></div>
				<div class="content-container col-xs-8 col-sm-8 col-md-8 col-lg-8">
					<div class="header">
						<div class="row">
							<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
								<div class="img-buffer">
								</div>
								<div class="img-container">
									<img src="../imgs/ScribeLogo.png"/>
								</div>
								<div class="title-container">
									<p>Scribe</p>
								</div>
								<div class="title-buffer">
								</div>
							</div>
						</div>
					</div>
					<div class="main">
						<div class="row">
							<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
								<form class="loginForm" method="POST">
									<div class="form-group" style="color: #FF0000;">${error}</div>
									<div class="form-group username">
										<input type="text" class="form-control form-element" id="username" placeholder="Enter username" name="username">
									</div>
									<div class="form-group password">
										<input type="password" class="form-control form-element" id="password" placeholder="Enter password" name="password">
									</div>
									<div class="form-group buttons">
										<div class="row">
											<div class="button-container col-xs-6 col-sm-6 col-md-6 col-lg-6" id="button-signin">
												<button type="submit" class="btn btn-secondary">Sign In</button>
											</div>
											<div class="button-container col-xs-6 col-sm-6 col-md-6 col-lg-6" id="button-signup">
												<button type="submit" class="btn btn-secondary">Sign Up</button>
											</div>
										</div>
										<div class="row">
											<div class="button-container col-xs-12 col-sm-12 col-md-12 col-lg-12" id="button-guest">
												<button type="submit" class="btn btn-secondary">Continue as Guest</button>
											</div>
										</div>
									</div>
								</form>
							</div>
						</div>
					</div>
				</div>
				<div class="<content-buffer col-xs-2 col-sm-2 col-md-2 col-lg-2"></div>
			</div>
		</div>
		
		<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.3/umd/popper.min.js" integrity="sha384-vFJXuSJphROIrBnz7yo7oB41mKfc8JzQZiCq4NCceLEaO4IHwicKwpJf9c9IpFgh" crossorigin="anonymous"></script>
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/js/bootstrap.min.js" integrity="sha384-alpBpkh1PFOepccYVYDB4do5UnbKysX5WZXm3XxPqe5iKTfUKjNkCk9SaVuEZflJ" crossorigin="anonymous"></script>
		<script>
		//Buttons
		var loginMember = document.getElementById("button-signin");		
		var signUp = document.getElementById("button-signup");		
		var loginGuest = document.getElementById("button-guest");

		loginMember.onclick = function() {
			$('form').get(0).setAttribute('action', '${pageContext.request.contextPath}/LoginMemberServlet');
		}
		
		signUp.onclick = function() {
			$('form').get(0).setAttribute('action', '${pageContext.request.contextPath}/SignUpServlet');
		}
		
		loginGuest.onclick = function() {
			$('form').get(0).setAttribute('action', '${pageContext.request.contextPath}/LoginGuestServlet');
		}
		</script>
	</body>
</html>