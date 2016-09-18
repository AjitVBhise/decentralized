<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="java.util.List"%> 
<%@page import="java.util.Date"%>
<%@page import="java.util.Calendar"%>     
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Home</title>
<link rel="stylesheet" href="ui-darkness/jquery-ui.min.css" />
<link rel="stylesheet" href="ui-darkness/jquery-ui.theme.css" />
<link rel="stylesheet" type="text/css" href="css/jquery.ui.datepicker.css" />
<link rel="stylesheet" type="text/css" href="css/style.css" />
<link rel='stylesheet' type='text/css' href='css/menu-styles.css' />
<link rel="stylesheet" href="css/jquery-multi-step-form.css">
<script src="js/jquery-1.9.1.js" type="text/javascript"></script>
<script src="js/jquery-ui.min.js" type="text/javascript"></script>
<script src="js/jquery.ui.datepicker.min.js" type="text/javascript"></script>
<script src="js/easing.js" type="text/javascript"></script>
<script src="js/jquery-multi-step-form.js" type="text/javascript"></script>
<script type="text/javascript">
$(document).ready(function() {
    $.multistepform({
        container:'multistepform-example-container',
        form_method:'GET',
    });
    
   
        $('#noRole').click(function(event) {  //on click
            if(this.checked) { // check select status
                $('.role').each(function() { //loop through each checkbox
                    this.checked = false;  //select all checkboxes with class "checkbox1" 
                    this.disabled = true;
                });
            }else{
                $('.role').each(function() { //loop through each checkbox
                    this.disabled = false; //deselect all checkboxes with class "checkbox1"                      
                });        
            }
        });
        $('#noDep').click(function(event) {  //on click
            if(this.checked) { // check select status
                $('.department').each(function() { //loop through each checkbox
                    this.checked = false;  //select all checkboxes with class "checkbox1" 
                    this.disabled = true;
                });
            }else{
                $('.department').each(function() { //loop through each checkbox
                    this.disabled = false; //deselect all checkboxes with class "checkbox1"                      
                });        
            }
        });
        $('#noYear').click(function(event) {  //on click
            if(this.checked) { // check select status
                $('.year').each(function() { //loop through each checkbox
                    this.checked = false;  //select all checkboxes with class "checkbox1" 
                    this.disabled = true;
                });
            }else{
                $('.year').each(function() { //loop through each checkbox
                    this.disabled = false; //deselect all checkboxes with class "checkbox1"                      
                });        
            }
        });
       
    
    
} );


</script>

<br>
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
form {
    color: #000 !important; 
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
<br>
<div>
<!-- multistep form -->
<div id="multistepform-example-container">
<!-- <ul id="multistepform-progressbar">
<li class="active">Index Cut-Off</li>
<li>Process Constraint</li>
<li>Utilization Constraint</li>
<li>Appreciation Constraint</li>
<li>Rewards Constraint</li>
</ul> -->
<% 
 String updation = (String) session.getAttribute("updation");
Integer fileId = (Integer) session.getAttribute("fileId");
if(updation!=null && updation.equals("true")){
%>
<form action="UpdateAccessPolicy" method="post">
<%}else{ %>
<form action="AddAccessPolicy" method="post">
<%} %>
<div class="form">
<h2 class="fs-title">Select roles to be considered for access</h2>
<h3 class="fs-subtitle">Select few of the following roles</h3>
<input type="checkbox" class="role" name="role" value="student"><span> Student</span>
<input type="checkbox" class="role" name="role" value="professor"><span> Professor</span>
<input type="checkbox" class="role" name="role"  value="hod"><span>HOD</span>
<input type="checkbox" id="noRole"  name="role"  value="NA">Not Applicable<br>
<input type="button" name="next" class="next button" value="Next">
<!-- </form> -->
</div>
<div class="form">
<!-- <form action=""> -->
<h2 class="fs-title">Select the concerned Department</h2>
<h3 class="fs-subtitle">Select few of the following departments</h3>
<input type="checkbox" class="department" name="department" value="ETC"><span> E&TC</span>
<input type="checkbox" class="department" name="department" value="Computer"><span> Computer</span>
<input type="checkbox" class="department" name="department"  value="IT"><span>IT</span>
<input type="checkbox" class="department" name="department"  value="Electrical"><span>Electrical</span>
<input type="checkbox" class="department" name="department"  value="Mechanical"><span>Mechanical</span>
<input type="checkbox"  id="noDep" name="department"  value="NA" >Not Applicable<br>
<input type="button"  class="previous button" value="Previous">
<input type="button" name="next" class="next button" value="Next">
<!-- </form> -->
</div>
 <div class="form"> 
<!-- <form action=""> -->
<h2 class="fs-title">Select the concerned Year(If student is applicable)</h2>
<h3 class="fs-subtitle">Select few of the following departments</h3>
<input type="checkbox" class="year" name="year" value="FY"><span> First Year</span>
<input type="checkbox" class="year" name="year" value="SY"><span> Second Year</span>
<input type="checkbox" class="year" name="year"  value="TY"><span>Third Year</span>
<input type="checkbox" class="year" name="year"  value="LY"><span>Final Year</span>
<input type="checkbox" id="noYear"  name="year"  value="NA" >Not Applicable<br>
<input type="button" name="previous" class="previous button" value="Previous">
<input type="button" name="next" class="next button" value="Next">
</div>

<div class="form"> 
<!-- <form action=""> -->
<h2 class="fs-title">Apply the access policy></h2>
<h3 class="fs-subtitle"> Please recheck your access policy before you finish it</h3>
<input type="button" name="previous" class="previous button" value="Previous">
<input type="submit" name="submit" class="next button" value="Finish">
</div>
</form>
</div>
</div>
</body>
</html>