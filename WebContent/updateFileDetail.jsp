<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.decentralized.connection.DBManager"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
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
<center><h1 class="header">Key Policy - Attribute Based Encryption</h1></center>
<div id='cssmenu'>
<ul style="font-weight: bold !important;">
   <li><a href='#'><span>Upload File</span></a></li>
   <li class='active'><a href='ModifyFiles.jsp'><span>Modify File</span></a></li>
   <!-- <li><a href='ViewFiles.jsp'><span>View Files</span></a></li> -->
   <li><a href='ModifyUser.jsp'><span>Modify User Details</span></a></li>
   <li class='last' style="float:right"><a href='index.jsp'><span>Logout</span></a></li>
</ul>
</div>

<body>
<form name="access" action="AddAccessPolicy" method="GET" enctype="multipart/form-data">
<ul>
    <li>
    <%
    DBManager dbm= new DBManager();
    int fileId = Integer.parseInt(request.getParameter("fileId"));
	session.setAttribute("fileId", fileId);
    String AccessExpression = dbm.getAccessExpression(fileId);
    String designation1 ="--Select--",designation2="--Select--",team1="--Select--",team2="--Select--",str1="--Select--",str2="--Select--",operation="--Select--";
    System.out.print(AccessExpression);
    if(AccessExpression.contains("AND")){
    	operation= "AND";
    	String str[]=AccessExpression.split("AND");
    	designation1=str[0];
    	team1=str[1];
    }else if(AccessExpression.contains("ORX")){
    	operation= "ORX";
    	String str[]=AccessExpression.split("ORX");
    	designation1=str[0];
    	team1=str[1];
    	}
     else if(AccessExpression.contains("OR")){
    	operation = "OR";
    	String str[]=AccessExpression.split("OR");
		str1=str[0];
		str2=str[1];
		if(str1.equals("CM")||str1.equals("TM")||str1.equals("SSE")||str1.equals("JSE")||str1.equals("NA"))
			designation1=str1;
		else if(str1.equals("PP")||str1.equals("EM")||str1.equals("HPS")||str1.equals("NTZ")||str1.equals("EB")||str1.equals("MX")||str1.equals("NA"))
			team1=str1;
		if(str2.equals("CM")||str2.equals("TM")||str2.equals("SSE")||str2.equals("JSE")||str2.equals("NA"))
			designation2=str2;
		else if(str2.equals("PP")||str2.equals("EM")||str2.equals("HPS")||str2.equals("NTZ")||str2.equals("EB")||str2.equals("MX")||str2.equals("NA"))
			team2=str2;
	}
    else if(AccessExpression.equals("CM")||AccessExpression.equals("TM")||AccessExpression.equals("SSE")||AccessExpression.equals("JSE")||AccessExpression.equals("NA"))
   		designation1=AccessExpression;
    else if(AccessExpression.equals("PP")||AccessExpression.equals("EM")||AccessExpression.equals("HPS")||AccessExpression.equals("NTZ")||AccessExpression.equals("EB")||AccessExpression.equals("NA"))
    	team1=AccessExpression;
    
     String DesignationName1 = "";
    if(designation1.equals("CM"))
       DesignationName1="Client Manager";
    else if(designation1.equals("TM"))
       DesignationName1="Team Manager";
    else if(designation1.equals("SSE"))
   	   DesignationName1="Sr.Software Engineer";
    else if(designation1.equals("JSE"))
        DesignationName1="Jr.Software Engineer";
    else if(designation1.equals("NA"))
        DesignationName1="Not Applicable";
    
    String DesignationName2 = "";
    if(designation2.equals("CM"))
        DesignationName2="Client Manager";
     else if(designation2.equals("TM"))
        DesignationName2="Team Manager";
     else if(designation2.equals("SSE"))
    	   DesignationName2="Sr.Software Engineer";
     else if(designation2.equals("JSE"))
         DesignationName2="Jr.Software Engineer";
     else if(designation2.equals("NA"))
         DesignationName2="Not Applicable";
     
    String TeamName1 = "";
    if(team1.equals("PP"))
    	TeamName1="PAXPRO";
    else if(team1.equals("EM"))
    	TeamName1="EMEE";
    else if(team1.equals("HPS"))
    	TeamName1="HPS";
    else if(team1.equals("NTZ"))
    	TeamName1="Netezza";
    else if(team1.equals("EB"))
    	TeamName1="ExportBlue";
    else if(team1.equals("MX"))
    	TeamName1="AmEx";
    else if(team1.equals("NA"))
    	TeamName1="Not Applicable";
    
    String TeamName2 = "";
    if(team2.equals("PP"))
    	TeamName2="PAXPRO";
    else if(team2.equals("EM"))
    	TeamName2="EMEE";
    else if(team2.equals("HPS"))
    	TeamName2="HPS";
    else if(team2.equals("NTZ"))
    	TeamName2="Netezza";
    else if(team2.equals("EB"))
    	TeamName2="ExportBlue";
    else if(team2.equals("MX"))
    	TeamName2="AmEx";
    else if(team2.equals("NA"))
    	TeamName2="Not Applicable";
    
    %>
<script type="text/javascript">
$(document).ready(function(){
	$("#operation").change(function(){
	    document.access.designation1.style.visibility="hidden";
	    document.access.designation2.style.visibility="hidden";
		document.access.team1.style.visibility="hidden";
		document.access.team2.style.visibility="hidden";
		$("#designation1").val("--Select--");
		$("#designation2").val("--Select--");
		$("#team1").val("--Select--");
		$("#team2").val("--Select--");
	});
	if(document.access.operation.value == "AND"){
		document.access.operation.style.visibility="visible";
		document.access.designation1.style.visibility="visible";
		document.access.team1.style.visibility="visible";
	}
	if(document.access.operation.value == "ORX"){
		document.access.operation.style.visibility="visible";
		document.access.designation1.style.visibility="visible";
		document.access.team1.style.visibility="visible";
	}
	else if((document.access.operation.value == "OR")){
		document.access.operation.style.visibility="visible";
		
		if((document.access.designation1.value!="--Select--")){
			
			document.access.designation1.style.visibility="visible";
			document.access.designation2.style.visibility="visible";
		}
		else{ 
			if(document.access.team1.value){
				document.access.team1.style.visibility="visible";
				document.access.team2.style.visibility="visible";
				}
			}
		}
	else if(document.access.operation.value == "--Select--"){
		document.access.operation.style.visibility="hidden";	
		if(document.access.designation1.value!="--Select--"){
			document.access.designation1.style.visibility="visible";
			
		}		
		else if(document.access.team1.value!="--Select--"){
			 document.access.team1.style.visibility="visible";
			 }
		}
});
function enableSelectOperation(){
	document.access.operation.style.visibility="visible";
}
function enableDesignation()
    {
		if(document.access.operation.value=="OR"){
	    	document.access.designation1.style.visibility="visible";
	    	document.access.designation2.style.visibility="visible";
	    	document.access.team1.style.visibility="hidden";
	    	document.access.team2.style.visibility="hidden";
		}
		else if(document.access.operation.value=="AND"){
			document.access.designation1.style.visibility="visible";
			document.access.designation2.style.visibility="hidden";
			document.access.team2.style.visibility="hidden";
		}
		else if(document.access.operation.value=="ORX"){
			document.access.designation1.style.visibility="visible";
			document.access.designation2.style.visibility="hidden";
			document.access.team2.style.visibility="hidden";
		}
		else if(document.access.operation.value=="--Select--"){
			$("#designation2").val("--Select--");
			$("#team1").val("--Select--");
			$("#team2").val("--Select--");
			document.access.designation1.style.visibility="visible";
			document.access.designation2.style.visibility="hidden";
			document.access.team1.style.visibility="hidden";
			document.access.team2.style.visibility="hidden";
		}
    }

function enableTeam(){
	if(document.access.operation.value=="OR"){
		document.access.designation1.style.visibility="hidden";
		document.access.designation2.style.visibility="hidden";
		document.access.team1.style.visibility="visible";
		document.access.team2.style.visibility="visible";
	}
	else if(document.access.operation.value=="AND"){
		document.access.designation2.style.visibility="hidden";
		document.access.team1.style.visibility="visible";
		document.access.team2.style.visibility="hidden";
	}
	else if(document.access.operation.value=="ORX"){
		document.access.designation2.style.visibility="hidden";
		document.access.team1.style.visibility="visible";
		document.access.team2.style.visibility="hidden";
	}
	
	else if(document.access.operation.value=="--Select--"){
		$("#designation1").val("--Select--");
		$("#designation2").val("--Select--");
		$("#team2").val("--Select--"); 
		
		document.access.designation1.style.visibility="hidden";
		document.access.designation2.style.visibility="hidden";
		document.access.team1.style.visibility="visible";
		document.access.team2.style.visibility="hidden";
	}
}
</script>
 		<li>
 		<center><font color="white" style="aling:center"><h2><%=request.getParameter("fileName")%></h2></font></center><br>	
    	 <font color="white" onclick="enableSelectOperation()"> Select Operation:</font>
		 <select id="operation" name="operation" style="visibility:hidden">
		     	<option value="<%=operation%>"><%=operation%></option>
		     	<option value="--Select--">--</option>
		        <option value="OR">Multiple OR</option>
		        <option value="AND">AND</option>
		         <option value="ORX">OR</option>
		    	 </select>&nbsp;&nbsp;
		</li>
     	<li>
     	<font color="white" onclick="enableDesignation()">Designation1:</font>
     	   	<select id="designation1" name="designation1" style="visibility:hidden">
				<option value="<%=designation1%>"><%=DesignationName1%></option>
		     	<option value="--Select--">---</option>
				<option value="CM">Client Manager</option>
				<option value="TM">Team Manager</option>
				<option value="SSE">Sr.Software Engineer</option>
				<option value="JSE">Jr.Software Engineer</option>
				<option value="NA">Not Applicable</option>
			</select>
		</li>
		<li>
			<font color="white" >Designation2:</font>
     	   	<select id="designation2" name="designation2" style="visibility:hidden">
				<option value="<%=designation2%>"><%=DesignationName2%></option>
		     	<option value="--Select--">---</option>
				<option value="CM">Client Manager</option>
				<option value="TM">Team Manager</option>
				<option value="SSE">Sr.Software Engineer</option>
				<option value="JSE">Jr.Software Engineer</option>
				<option value="NA">Not Applicable</option>
			</select>
		</li>
		<li> 	 
			<font color="white" onclick="enableTeam()">Team1:</font>
			<select id="team1" name="team1" style="visibility:hidden">
	          	<option value="<%=team1%>"><%=TeamName1 %></option>
	          	<option value="--Select--">---</option>
				<option value="PP">PAXPRO</option>
				<option value="EM">EMEE</option>
				<option value="HPS">HPS</option>
				<option value="NTZ">Netezza</option>
				<option value="EB">ExportBlue</option>
				<option value="MX">AmEx</option>
				<option value="NA">Not Applicable</option>
		    </select>&nbsp;&nbsp;
    	</li>
   		<li> 	 
			<font color="white" ">Team2</font>
			<select id="team2" name="team2" style="visibility:hidden">
	          	<option value="<%=team2%>"><%=TeamName2 %></option>
	          	<option value="--Select--">--</option>
				<option value="PP">PAXPRO</option>
				<option value="EM">EMEE</option>
				<option value="HPS">HPS</option>
				<option value="NTZ">Netezza</option>
				<option value="EB">ExportBlue</option>
				<option value="MX">AmEx</option>
				<option value="NA">Not Applicable</option>
		    </select>&nbsp;&nbsp;
    	</li>
   
 </ul> 
 <p>
    <input type="submit" id="submit" value="Update" class="stylebutton"/>
    <button><a href="ModifyFiles.jsp" >Cancel</a></button>
 </p>
</form>
	<%session.setAttribute("submit",request.getParameter("submit"));%>
	<%session.setAttribute("fileName",request.getParameter("fileName"));%>			           	
</body>
</html>