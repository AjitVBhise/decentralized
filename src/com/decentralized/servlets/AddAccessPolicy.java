package com.decentralized.servlets;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import com.decentralized.connection.DBManager;

/**
 * Servlet implementation class AddAccessPolicy
 */
public class AddAccessPolicy extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddAccessPolicy() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	String[] roles = request.getParameterValues("role");
	String[] department = request.getParameterValues("department");
	String[] year = request.getParameterValues("year");
	List<String> listAccessExpressions = new ArrayList<String>();
	List<String> listAccessValues = new ArrayList<String>();
	HttpSession session= request.getSession();
	byte[] fileBytes = (byte[]) session.getAttribute("fileBytes");
	String fileName = (String) session.getAttribute("fileName");
	InputStream myInputStream = new ByteArrayInputStream(fileBytes); 
	//COndition 1
	if((roles[0].equals("NA")&&department[0].equals("NA"))&&year[0].equals("NA")){
		StringBuilder accessExpression= new StringBuilder();
		StringBuilder accessValue= new StringBuilder();
		accessExpression.append("NA");
		accessValue.append("NA");
		listAccessExpressions.add(accessExpression.toString());
		listAccessValues.add(accessValue.toString());
	}
	//COndition 2
	if((!roles[0].equals("NA")&&department[0].equals("NA"))){
		for(String role:roles){
			
		if(role.equals("student"))
		if(!year[0].equals("NA")){
		listAccessExpressions.add("role&year");
		listAccessValues = performAnd("student",year);
		}
		else{
			listAccessExpressions.add("role");
			listAccessValues.add("student");
		}
		if(role.equals("professor")){
			listAccessExpressions.add("role");
			listAccessValues.add("professor");
		}
		if(role.equals("hod")){
			listAccessExpressions.add("role");
			listAccessValues.add("hod");
		}
		}
	}
	//COndition 3
	if((roles[0].equals("NA")&&!department[0].equals("NA"))){
		
		listAccessExpressions.add("department");
		for(String dep:department)
		listAccessValues .add(dep);
		}

	
	//Condition 4
	if((!roles[0].equals("NA")&&!department[0].equals("NA"))){
		for(String role:roles){
			
			if(role.equals("student"))
			if(!year[0].equals("NA")){
			listAccessExpressions.add("role&department&year");
			listAccessValues.addAll(performAnd2("student",department,year));
			}
			else{
				listAccessExpressions.add("role&department");
				listAccessValues.addAll(performAnd("student",department));
			}
			if(role.equals("professor")){
				listAccessExpressions.add("role&department");
				listAccessValues.addAll(performAnd("professor",department));
			}
			if(role.equals("hod")){
				listAccessExpressions.add("role&department");
				listAccessValues.addAll(performAnd("hod",department));
			}
			}
	}
	DBManager dbm = new DBManager();
	String ownerToken =  (String) session.getAttribute("userToken");
	//System.out.println("Owner TOken Access::"+ownerToken);
	if(dbm.addAccessPolicyAndFile(listAccessExpressions,listAccessValues,myInputStream,fileName,ownerToken)){
		session.setAttribute("msg","Application of access policy successful");
		response.sendRedirect("ViewFiles.jsp");
	}
	else{
		session.setAttribute("msg","Apply access policy failed");
		response.sendRedirect("AccessStructure.jsp");
	}
}

	private List<String> performAnd2(String role, String[] department,String[] year) {
		List<String> operations = new ArrayList<String>();
		for(String dep:department){
			for(String yr:year){
				operations.add(role+"&"+dep+"&"+yr);
			}
		}
		return operations;
	}

	private List<String> performAnd(String role, String[] str) {
		List<String> operations = new ArrayList<String>();
		for(String string:str){
			operations.add(role+"&"+string);
		}
		return operations;
	}
}
