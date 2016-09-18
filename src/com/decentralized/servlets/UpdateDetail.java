package com.decentralized.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.decentralized.connection.DBManager;

/**
 * Servlet implementation class UpdateDetail
 */
public class UpdateDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateDetail() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		DBManager dbm= new DBManager();
		HttpSession session = request.getSession();
		int userId = Integer.parseInt(request.getParameter("userId"));
		if(dbm.updatetUser(request.getParameter("designation"),request.getParameter("team"),request.getParameter("department"),userId)){
        	//session.setAttribute("fileId", fileId);
        	session.setAttribute("msg","Update Successfull!");
        	request.getRequestDispatcher("ModifyUser.jsp").forward(request, response);
        }else{
        	session.setAttribute("msg","Update Failed!");
        	request.getRequestDispatcher("ModifyUser.jsp").forward(request, response);
        }
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		request.getRequestDispatcher("HomePage.jsp").forward(request, response);
	}

}
