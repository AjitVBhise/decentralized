package com.decentralized.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.decentralized.connection.DBManager;



@SuppressWarnings("serial")
public class RegistrationServlet extends HttpServlet{
	
	public void doPost(HttpServletRequest request,HttpServletResponse response)
throws ServletException, IOException {
		String username= request.getParameter("username"); 
		String password= request.getParameter("password"); 
		String firstName= request.getParameter("firstName"); 
		String lastName= request.getParameter("lastName");
		String email= request.getParameter("email"); 
		String contact= request.getParameter("contact"); 
		String role= request.getParameter("role"); 
		String department= request.getParameter("department"); 
		String year= request.getParameter("year");
		String accessToken= request.getParameter("accessToken");
		DBManager dbm= new DBManager();
		HttpSession session = request.getSession();
		if(dbm.validateKDCToken(accessToken)){
		if(!dbm.validUserName(username)){
			dbm.insertUser(firstName, lastName, username, password, email, contact, accessToken, department, year,role);
			session.setAttribute("msg","Registration Successful!");
			request.getRequestDispatcher("index.jsp").forward(request, response);
		}
		else{
			
			session.setAttribute("msg", "Username already exists!!");
			request.getRequestDispatcher("Registration.jsp").forward(request, response);
		}
		}else{
			session.setAttribute("msg", "Invalid KDC Token!!");
			request.getRequestDispatcher("Registration.jsp").forward(request, response);
		}
}

}
