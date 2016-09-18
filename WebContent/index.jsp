<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login</title>
<link rel="stylesheet" type="text/css" href="css/loginPage.css" />
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.5.1/jquery.min.js"></script>
	<script>
		$(function(){
		  var $form_inputs =   $('form input');
		  var $rainbow_and_border = $('.rain, .border');
		  /* Used to provide loping animations in fallback mode */
		  $form_inputs.bind('focus', function(){
		  	$rainbow_and_border.addClass('end').removeClass('unfocus start');
		  });
		  $form_inputs.bind('blur', function(){
		  	$rainbow_and_border.addClass('unfocus start').removeClass('end');
		  });
		  $form_inputs.first().delay(800).queue(function() {
			$(this).focus();
		  });
		});
	</script>
</head>




<center><h1 class="header">Anonymous Decentralized Access- Attribute Based Encryption</h1></center>

<body id="home">
<!-- <a href="http://192.168.43.117:8081//DecentralizedKDC" style="float: right;text-decoration: none;margin-right: 10px;">KDC</a><br>
<a href="http://192.168.43.117:8081//DecentralizedTrustee" style="float: right;text-decoration: none;margin-right: 10px;">Trustee</a> -->
<div align="center" style="font-size: 20px;color: red;margin-top: 1%;"><%=session.getAttribute("msg")==null?"":session.getAttribute("msg")%></div>
		<div class="rain">
			<div class="border start">
				<form action="LoginCheck" method="post" name="LoginCheck">
					<label for="username">Username</label>
					<input name="username" id="username" type="text" placeholder="Username"/>
					<label for="password">Password</label>
					<input name="password" id="password" type="password" placeholder="Password"/>
					<!-- <label for="isAdmin">isAdmin</label>
					<input name="isAdmin" id="isAdmin" type="checkbox" value="true"/> -->
                    <input type="submit" value="Login" onclick="loginCheck"></button>
				</form>
				<a type="button" class="button" value="Register" href="Registration.jsp">Registration</a>
			</div>
		</div>
		<%
session.setAttribute("msg","");
%>
</body>
	


</html>