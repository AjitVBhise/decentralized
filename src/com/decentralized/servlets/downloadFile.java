package com.decentralized.servlets;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Blob;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;

import com.decentralized.connection.DBManager;
import com.decentralized.objects.RSA;
import com.decentralized.objects.UserInfo;
import com.decentralized.util.Constants;


/**
 * Servlet implementation class downloadFile
 */
public class downloadFile extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final int BUFFER_SIZE = 4096;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public downloadFile() {
        super();
     }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try	{
		HttpSession session = request.getSession();
		String fileName = request.getParameter("filename");
		String decKey = request.getParameter("decKey");
		String username = (String) session.getAttribute("username");
		
		DBManager dbm= new DBManager();
		UserInfo user=dbm.getUserInfo(username);
		String userToken = (String) session.getAttribute("userToken");
		//check the permission
		if(Constants.rsaTokens.contains(decKey)){
		if(dbm.isAccess_permission(user, fileName)){
			String permission = checkUserToken(userToken);
			if(permission.equalsIgnoreCase("W")){
			//download file if user have an permission
			try{
				//System.out.println("Download acess");
				session.setAttribute("msg","");
				Blob blob= dbm.download(fileName);
				InputStream inputStream = blob.getBinaryStream();
			//	decKey = Constants.performEncryption("0123456789abcdef");
				String[] decKeyArr = decKey.split("_");
				RSA rsa = new RSA(new BigInteger(decKeyArr[0]),new BigInteger(decKeyArr[1]));
				rsa.generateKeys();
				decKey = rsa.decrypt("1078648416596019810753369942434609319234140396171902781393316581467374305830924324695398829515387909505655668932431033809504544364801721971532688620337364276801056688839131189353114438593632");
			//	decKey = "0123456789abcdef";
				  byte[] bFile = IOUtils.toByteArray(inputStream);
				  bFile = decryptStreamByAES(bFile, decKey);
				  inputStream = new ByteArrayInputStream(bFile);
				//inputStream = TestClass.decriptFileContent(inputStream,access_val);
	            int fileLength = inputStream.available();
	            System.out.println("fileLength = " + fileLength);
	            ServletContext context = getServletContext();
	            
	            // sets MIME type for the file download
	            String mimeType = context.getMimeType((String) fileName);
	            if (mimeType == null) {        
	                mimeType = "application/octet-stream";
	            }              
             
            // set content properties and header attributes for the response
            response.setContentType(mimeType);
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
             session.setAttribute("username",username);
             response.sendRedirect("ViewFiles.jsp");
           //  request.getRequestDispatcher("ViewFiles.jsp").forward(request, response);
        /*} else {
            // no file found
            response.getWriter().print("File not found for the id: " + uploadId);  
        }*/
			}catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			Blob blob= dbm.download(fileName);
			InputStream inputStream = blob.getBinaryStream();
		//	decKey = Constants.performEncryption("0123456789abcdef");
			String[] decKeyArr = decKey.split("_");
			RSA rsa = new RSA(new BigInteger(decKeyArr[0]),new BigInteger(decKeyArr[1]));
			rsa.generateKeys();
			decKey = rsa.decrypt("1078648416596019810753369942434609319234140396171902781393316581467374305830924324695398829515387909505655668932431033809504544364801721971532688620337364276801056688839131189353114438593632");
		//	decKey = "0123456789abcdef";
			  byte[] bFile = IOUtils.toByteArray(inputStream);
			  bFile = decryptStreamByAES(bFile, decKey);
			  inputStream = new ByteArrayInputStream(bFile);
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
			StringBuilder sb = new StringBuilder();
			String inputLine;
			while ((inputLine = br.readLine()) != null) {
				sb.append(inputLine).append("\r\n");
			}
			session.setAttribute("fileContent", sb.toString());
			response.sendRedirect("DisplayFile.jsp");
			
		}
		}
		else
		{	System.out.println("Downlod Acess denied");
			//response.getWriter().print("you dosn't have  permission to access this file. ");	
			session.setAttribute("msg","You don't have  permission to access this file.");
			session.setAttribute("username",username);
			request.getRequestDispatcher("ViewFiles.jsp").forward(request, response);
		}
		}else{
			session.setAttribute("msg","Wrong Decryption key!!");
			session.setAttribute("username",username);
			request.getRequestDispatcher("ViewFiles.jsp").forward(request, response);
		}
		//redirect to view page
		
	}catch (Exception e) {
		e.printStackTrace();
	}
  }

	private String checkUserToken(String userToken) {
		String result=null;
		String lastChar =  userToken.substring(userToken.length() - 1);
		if(lastChar.equals("R")){
			result="R";
		}
		else
			result = "W";
		return result;
	}

	public byte[] decryptStreamByAES(byte[] fin,String key){
		 /* MessageDigest md = null;
		   try {
			 md = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		}*/
		   
		   		 byte[] keyBytes = key.getBytes();//md.digest(key.getBytes());
  		 byte[] decryptedData = null;
		   Cipher c;
		try {
			c = Cipher.getInstance("AES");
		   SecretKeySpec k =
		     new SecretKeySpec(keyBytes, "AES");
			c.init(Cipher.DECRYPT_MODE, k);
			decryptedData = c.doFinal(fin);
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		}	 catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) { 
			e.printStackTrace();
		} catch (BadPaddingException e) { 
			e.printStackTrace();
		}
		  
		   return decryptedData;
}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
