<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Registration </title>

<link rel="stylesheet" href="ui-darkness/jquery-ui.min.css" />
<link rel="stylesheet" href="ui-darkness/jquery.ui.theme.css" />
<link rel="stylesheet" type="text/css" href="css/jquery.ui.datepicker.css" />
<link rel="stylesheet" type="text/css" href="css/style.css" />
<link rel='stylesheet' type='text/css' href='css/menu-styles.css' />
<script src="js/jquery-1.9.1.js" type="text/javascript"></script>
<script src="js/jquery-ui.min.js" type="text/javascript"></script>
<script src="js/jquery.ui.datepicker.min.js" type="text/javascript"></script>

<script type="text/javascript">
function toggleYear(){
	var role = $("#role").val();
	if(role=="student"){
		$("#year").css("display","");
		$("#yearLabel").css("display","");
	}else{
		$("#year").css("display","none");
		$("#yearLabel").css("display","none");
	}
}
</script>
<style>
.header {
    font-size: 50px;
    font-family: 'Helvetica','Lucida Grande','Arial',sans-serif;
    height: 100px;
    margin-bottom: 0;
    margin-top: 0;
    padding: 15px;
    text-shadow: -4px 4px 3px #999999, 1px -1px 2px #000000;
    color: #DDDDDD;
}

.header3 {
    font-size: 35px;
    font-family: 'Helvetica','Lucida Grande','Arial',sans-serif;
    height: 50px;
    margin-bottom: 0;
    margin-top: 0;
    text-shadow: -4px 4px 3px #999999, 1px -1px 2px #000000;
    color: #DDDDDD;
}
</style>
</head>
<body>
<center><h1 class="header">Annonymous Decentralized Access- Attribute Based Encryption</h1></center>
<br>
<br>
<br>
<h3 class="header3">Registration</h3><a href="index.jsp" style="float:right;text-decoration: none;margin-right: 30%;">Back</a>
<div style="font-size: 20px;color: yellow;margin-top:1%;"><%=session.getAttribute("msg")==null?"":session.getAttribute("msg")%></div>
<form action="RegistrationServlet" method="post">
	<ul>
        <li>
        	<label for="firstName">First Name</label>
            <input type="text" id="firstName" name="firstName" required="required" />&nbsp;&nbsp;<font color="white">*</font>
        </li>
        <li>
        	<label for="lastName">Last Name</label>
            <input type="text" id="lastName" name="lastName" required="required" />&nbsp;&nbsp;<font color="white">*</font>
        </li>
         <li>
        	<label for="username">Username</label>
            <input type="text" id="username" name="username" required="required" />&nbsp;&nbsp;<font color="white">*</font>
        </li>
         <li>
        	<label for="username">Password</label>
            <input type="password" id="password" name="password" required="required" />&nbsp;&nbsp;<font color="white">*</font>
        </li>
         
        <li>
        <label for="email">Email</label>
            <label><input type="email" name="email" id="email" /></label>
        </li>
         
        <li>
        <label for="contact">Contact</label>
            <label><input type="text" name="contact" id="contact" /></label>
        </li>
         <li>
        <label for="contact">KDC Access Token</label>
            <label><input type="text" name="accessToken" id="accessToken" required="required"/></label>&nbsp;&nbsp;<font color="white">*</font>
        </li>
        <li>
            <label for="role">Role</label>
            <select id="role" name="role" required="required" onchange="toggleYear()">
                <option value="select">Select</option>
                <option value="student">Student</option>
                <option value="professor">Professor</option>
                <option value="hod">HOD</option>
            </select>&nbsp;&nbsp;<font color="white">*</font>
        </li>
        <li>
        	<label for="department">Department</label>
            <select id="department" name="department" required="required">
              <option value="select">Select</option>
                <option value="ETC">E&TC</option>
                <option value="Computer">Computers</option>
                <option value="Electrical">Electrical</option>
                <option value="IT">IT</option>
                <option value="Mechanical">Mechanical</option>
            </select>&nbsp;&nbsp;<font color="white">*</font>
           
        </li>
        <li>
        	<label for="year" id="yearLabel" style="display:none">Year</label>
           <select id="year" name="year" required="required" style="display:none">
             <option value="select">Select</option>
          		<option value="FY">First Year</option>
           		<option value="SY">Second Year</option>
                <option value="TY">Third Year</option>
                <option value="LY">Final Year</option>
            </select>&nbsp;&nbsp;<!-- <font color="white">*</font> -->
        </li>
       
	</ul>
    <p>
        <button type="submit">Register</button>
        <button type="reset" class="right">Reset</button>
    </p>
</form>
<div style="font-size: 20px;color: white;margin-top:1%;">NOTE: Fields marked by * are compulsory</div>
<%
session.setAttribute("msg","");
%>
</body>
</html>