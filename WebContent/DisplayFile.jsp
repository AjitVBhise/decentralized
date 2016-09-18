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




<center><h1 class="header">Annonymous Decentralized Access- Attribute Based Encryption</h1></center>
<a href="ViewFiles.jsp" style="float: right;margin-right: 10px;text-decoration: none;">Back</a>
<body id="home">
<div align="center" style="font-size: 20px;color: red;margin-top: 1%;"><%=session.getAttribute("msg")==null?"":session.getAttribute("msg")%></div>
		<div style="background-color: white;width:1000px;height:600px;margin: 0 auto">
		<p style="color:black;"><%=(String) session.getAttribute("fileContent")%></p>
		</div>
		<%session.setAttribute("msg",null);%>
</body>
	


</html>