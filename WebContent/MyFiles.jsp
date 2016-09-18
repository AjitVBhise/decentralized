<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="com.decentralized.connection.DBManager"%>
	<%@ page import="java.util.HashMap"%>
	<%@ page import="java.util.Set"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Download File</title>
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
    margin-left: 10%;
}
table {
width: 70em;
border: medium solid #FFF;
border-collapse:collapse;
}
th, td {
width:10em;
padding:10px;
border: thin solid #FFF;
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
   
   <li><a href='ModifyFiles.jsp'><span>Modify File</span></a></li>
   <li class='active'><a href='ViewFiles.jsp'><span>View Files</span></a></li>
   <li><a href='ModifyUser.jsp'><span>Modify User Details</span></a></li>
   <%} else {%>
   <li ><a  href='ViewFiles.jsp'><span>View Files</span></a></li>
   <li><a href='HomePage.jsp'><span>Upload File</span></a></li>
    <li class='active'><a href='MyFiles.jsp'><span>My Files</span></a></li>
   <%}%>
   <li class='last' style="float:right"><a href='index.jsp'><span>Logout</span></a></li>
</ul>
</div>
<!-- <a href="./HomePage.jsp">Home Page</a> -->
<%
DBManager dbm= new DBManager();
	String ownerToken =  (String) session.getAttribute("userToken");
	 HashMap result=dbm.getViewFilesByUserToken(ownerToken);
	 Set<Integer> keys = result.keySet();
	%>

<!-- <script type="text/javascript">
function getEnable()
{   //window.confirm("Inside");
	//document.downloadFile.download.style.visibility="visible";
	document.downloadFile.download.style.visibility="visible";
	window.confirm("Inside");
}
</script>
 -->
 <ul>
 	  <!-- <li> -->
        <div align="center" style="font-size: 20px;color: red;margin-top: 1%;"><%=session.getAttribute("msg")==null?"":session.getAttribute("msg")%></div>
          <div align="center" class="ex">
			<table  border=4 bordercolor="red">
			 <tr><th>Id</th><th>File Name</th><th>Action</th></tr>
	        	<% for (Integer key : keys) { %>
	             <form action="UpdateAccessPolicyServlet" method="post">
	               	<tr>
	            	<td><font><%=key%></font></td>
	    			<td align="center"><font style="text-align: left;"><%=result.get(key)%></font></td>
	    			<input type ="hidden" name="fileId" id="fileId" value="<%=key%>"/>
	           	 	<input type ="hidden" name="filename" id="filename" value="<%=result.get(key)%>"/>
	            	<td><input type="submit" name="submit" value="Update Policy" onclick= "<%session.setAttribute("msg",""); %>" class="stylebutton"/></td>
	           	   <!--  <td><input type="text" name="decKey" required="required"/></td> -->
	           	    </tr>
	           	   </form>
	            <% } %>
        	</table>
        </div>
      <!-- </li> -->
   </ul>
   	<%	session.setAttribute("msg",null);
		session.setAttribute("username",session.getAttribute("username"));%>
</body>
</html>