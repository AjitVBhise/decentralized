<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Home</title>

<link rel="stylesheet" href="ui-darkness/jquery-ui.min.css" />
<link rel="stylesheet" href="ui-darkness/jquery-ui.theme.css" />
<link rel="stylesheet" type="text/css" href="css/jquery.ui.datepicker.css" />
<link rel="stylesheet" type="text/css" href="css/style.css" />
<link rel='stylesheet' type='text/css' href='css/menu-styles.css' />
<script src="js/jquery-1.9.1.js" type="text/javascript"></script>
<script src="js/jquery-ui.min.js" type="text/javascript"></script>
<script src="js/jquery.ui.datepicker.min.js" type="text/javascript"></script>

<script type="text/javascript">

</script>
<style>
.header {
    font-size: 35px;
    font-family: 'Helvetica','Lucida Grande','Arial',sans-serif;
    height: 100px;
    margin-bottom: 0;
    margin-top: 0;
    padding: 15px;
    text-shadow: -4px 4px 3px #999999, 1px -1px 2px #000000;
    color: #DDDDDD;
}

.header3 {
    font-size: 20px;
    font-family: 'Helvetica','Lucida Grande','Arial',sans-serif;
    margin-bottom: 0;
    margin-top: 0;
    text-shadow: -4px 4px 3px #999999, 1px -1px 2px #000000;
    color: #DDDDDD;
}
.stylebutton{
background: -moz-linear-gradient(center top , #F2F2F2 0%, #E8E8E8 100%) repeat scroll 0 0 rgba(0, 0, 0, 0);
    border-color: #EEECED #EEECED #9B9B9B;
    border-radius: 20px;
    border-style: solid;
    border-width: 1px;
    box-shadow: 0 1px 0 0 #FFFFFF inset;
    cursor: pointer;
    font-weight: bold;
    padding: 5px 30px;
    text-shadow: 0 1px 0 #FFFFFF;
    margin-left: 40%;
}
</style>
</head>
<body>

<center><h1 class="header">Anonymous Decentralized Access- Attribute Based Encryption</h1></center>
<div><h2 align="right" class="header3"><span>Welcome <%=session.getAttribute("username")%></span></h2></div>
<%session.setAttribute("username",session.getAttribute("username")); %>
<div id='cssmenu'>
<ul style="font-weight: bold !important;">
<% if(session.getAttribute("username").equals("admin")){ %>
   <li class='active'><a href='#'><span>Upload File</span></a></li>
   <li><a href='ModifyFiles.jsp'><span>Modify File</span></a></li>
   <!-- <li><a href='ViewFiles.jsp'><span>View Files</span></a></li> -->
   <li><a href='ModifyUser.jsp'><span>Modify User Details</span></a></li>
   <%} else {%>
   <li><a class='active' href='ViewFiles.jsp'><span>View Files</span></a></li>
   <%}%>
   <li class='last' style="float:right"><a href='index.jsp'><span>Logout</span></a></li>
</ul>
</div>
<%-- <div style="margin: 0 auto;"<%=session.getAttribute("msg")==null?"":session.getAttribute("msg")%>> --%>
<div align="center" style="font-size: 20px;color: red;margin-top: 1%;"><%=session.getAttribute("msg")==null?"":session.getAttribute("msg")%></div>
<font color="white" style="font-size: 20px;">Select a file to upload</font>
</div>
<br></br>
<form action="UploadServlet" method="post" enctype="multipart/form-data">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
Encryptor Key:<input type="text" name="encKey" style="margin-left: 12%;width: 235px;"/><br><br>
<input type="file" id="file" name="file" size="50" style="margin-left: 30%;" onchange="enable()"/>
<br></br>
<input type="submit" id="submit" value="Upload File" class="stylebutton"/>
</form>
<%
session.setAttribute("msg","");
%>
</body>
</html>