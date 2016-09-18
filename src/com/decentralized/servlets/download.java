package com.decentralized.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
 
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;

/**
 * Servlet implementation class download
 */
public class download extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 private static final int BUFFER_SIZE = 4096;
	 InputStream inputStream=null; 
    /**
     * @see HttpServlet#HttpServlet()
     */
    public download() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		 
		 
		/**
		 * A servlet that retrieves a file from MySQL database and lets the client
		 * downloads the file.
		 * @author www.codejava.net
		 */
		/*@WebServlet("/downloadFileServlet")
		public class DBFileDownloadServlet extends HttpServlet {
		 
		    // size of byte buffer to send file
		      
		     
		    // database connection settings
		    private String dbURL = "jdbc:mysql://localhost:3306/FileDB";
		    private String dbUser = "root";
		    private String dbPass = "secret";
		     
		    protected void doGet(HttpServletRequest request,
		            HttpServletResponse response) throws ServletException, IOException {
		*/        // get upload id from URL's parameters
		        int uploadId = Integer.parseInt(request.getParameter("id"));
		         
		       // Connection conn = null; // connection to the database
		         
		        try {
		            // connects to the database
		           // DriverManager.registerDriver(new com.mysql.jdbc.Driver());
		           // conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
		 
		            // queries the database
		            //String sql = "SELECT * FROM files_upload WHERE upload_id = ?";
		           // PreparedStatement statement = conn.prepareStatement(sql);
		            //statement.setInt(1, uploadId);
		 
		            //ResultSet result = statement.executeQuery();
		            //if (result.next()) {
		                // gets file name and file blob data
		              //  String fileName = result.getString("file_name");
		               // Blob blob = result.getBlob("file_data");
		                //InputStream inputStream = blob.getBinaryStream();
		                //int fileLength = inputStream.available();
		                 
		                //System.out.println("fileLength = " + fileLength);
		 
		                ServletContext context = getServletContext();
		 
		                // sets MIME type for the file download
		                Object fileName = null;
						String mimeType = context.getMimeType((String) fileName);
		                if (mimeType == null) {        
		                    mimeType = "application/octet-stream";
		                }              
		                 
		                // set content properties and header attributes for the response
		                response.setContentType(mimeType);
		                int fileLength = 0;
						response.setContentLength(fileLength);
		                String headerKey = "Content-Disposition";
		                String headerValue = String.format("attachment; filename=\"%s\"", fileName);
		                response.setHeader(headerKey, headerValue);
		 
		                // writes the file to the client
		                OutputStream outStream = response.getOutputStream();
		                 
		                byte[] buffer = new byte[BUFFER_SIZE];
		                int bytesRead = -1;
		                 
		                while ((bytesRead = inputStream.read(buffer)) != -1) {
		                    outStream.write(buffer, 0, bytesRead);
		                }
		                 
		                inputStream.close();
		                outStream.close();             
		            /*} else {
		                // no file found
		                response.getWriter().print("File not found for the id: " + uploadId);  
		            }*/
		        } catch (IOException ex) {
		            ex.printStackTrace();
		            response.getWriter().print("IO Error: " + ex.getMessage());
		        } finally {
		            /*if (conn != null) {
		                // closes the database connection
		                try {
		                    conn.close();
		                } catch (SQLException ex) {
		                    ex.printStackTrace();
		                }
		            }*/          
		        }
		    }
		
		
		
		
		
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
