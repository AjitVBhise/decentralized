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
import com.decentralized.objects.UserInfo;

@SuppressWarnings("serial")
public class LoginCheck extends HttpServlet{
	
	public void doPost(HttpServletRequest request,HttpServletResponse response)
throws ServletException, IOException {
		String username= request.getParameter("username"); 
		String password= request.getParameter("password");
		DBManager dbm= new DBManager();
		try {
			
				if(dbm.validateUser(username, password)){
					HttpSession session = request.getSession();
					session.setAttribute("username",username);
					UserInfo user = dbm.getUserInfo(username);
					session.setAttribute("userToken", user.getAccessToken());
					System.out.println("Owner TOken Logincheck::"+user.getAccessToken());
					response.sendRedirect("ViewFiles.jsp");
				}else{
					HttpSession session = request.getSession();
					session.setAttribute("msg", "Incorrect username or password");
					request.getRequestDispatcher("index.jsp").forward(request, response);
				}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
}

}
