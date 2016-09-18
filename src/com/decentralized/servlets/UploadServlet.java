package com.decentralized.servlets;

import java.beans.Transient;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Blob;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

import com.decentralized.connection.DBManager;
import com.decentralized.util.Constants;
import com.google.gson.Gson;

@SuppressWarnings("serial")
@WebServlet("/UploadServlet")
public class UploadServlet extends HttpServlet {
   
   private boolean isMultipart;
   private String filePath;
   private int maxFileSize = 1024 * 1024 *4; //4MB
   private int maxMemSize = 1023 * 1024 *4; //4MB
   private File file ;
   String fileName;
   String encKey;
   public void init( ){
      // Get the file location where it would be stored.
      filePath = 
             getServletContext().getInitParameter("file-upload"); 
   }
   @SuppressWarnings("unchecked")
public void doPost(HttpServletRequest request, 
               HttpServletResponse response)
              throws ServletException, java.io.IOException {
	   HttpSession session = request.getSession();
	   DBManager dbm= new DBManager();
	   Gson gson = new Gson();
      // Check that we have a file upload request
      isMultipart = ServletFileUpload.isMultipartContent(request);
      response.setContentType("text/html");
      java.io.PrintWriter out = response.getWriter( );
      if( !isMultipart ){
              }
      DiskFileItemFactory factory = new DiskFileItemFactory();
      // maximum size that will be stored in memory
      factory.setSizeThreshold(maxMemSize);
      // Location to save data that is larger than maxMemSize.
      factory.setRepository(new File("c:\\temp"));

      // Create a new file upload handler
      ServletFileUpload upload = new ServletFileUpload(factory);
      // maximum file size to be uploaded.
      upload.setSizeMax( maxFileSize );

      try{ 
      // Parse the request to get file items.
      List fileItems = upload.parseRequest(request);
      
      // Process the uploaded file items
      Iterator i = fileItems.iterator();
      while ( i.hasNext () ) 
      {
         FileItem fi = (FileItem)i.next();
         if ( !fi.isFormField () )	
         {
            // Get the uploaded file parameters
            String fieldName = fi.getFieldName();
            fileName = fi.getName();
            String contentType = fi.getContentType();
            boolean isInMemory = fi.isInMemory();
            long sizeInBytes = fi.getSize();
            // Write the file
            if( fileName.lastIndexOf("\\") >= 0 ){
            	//System.out.println("In if"); 
               file = new File( filePath + 
               fileName.substring( fileName.lastIndexOf("\\"))) ;
            }else{
            	//System.out.println("In else"+fieldName);
               file = new File( filePath + 
               fileName.substring(fileName.lastIndexOf("\\")+1)) ;
            }
            fi.write( file ) ;
         }
         if(fi.isFormField ()){
        	 if(fi.getFieldName().equals("encKey")){
        		 encKey = fi.getString();
        		if(encKey==null || encKey.equals("")){
        			break;
        		}
        	 }
         }
      }
      if(encKey.equals("0123456789abcdef")){
            // Encrypt the file content. . .
            
            FileInputStream fstream = new FileInputStream(file);
            byte[] bFile = IOUtils.toByteArray(fstream);
           // encKey = Constants.performEncryption("0123456789abcdef0123456789abcdef0123456789abcdef");
          //  encKey = "0123456789abcdef";
            bFile = encryptStreamByAES(bFile,encKey);
           
            if(!dbm.isFileExist(fileName)){
            	session.setAttribute("fileBytes", bFile);
          
            	session.setAttribute("msg","Upload Successfull!");
            	session.setAttribute("fileName",fileName);
            	request.getRequestDispatcher("AccessStructure.jsp").forward(request, response);
           
            }else{
            	session.setAttribute("msg","Upload Failed. File Already Exist!!!!");
    			request.getRequestDispatcher("HomePage.jsp").forward(request, response);
            
            }
      }else{
    	  session.setAttribute("msg","Wrong encryption key!!");
			request.getRequestDispatcher("HomePage.jsp").forward(request, response);
      }
 } catch (FileNotFoundException e) {
          e.printStackTrace();
      } catch (IOException e) {
          e.printStackTrace();
      } catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
   }
   
   /*public File encryptFileStream(File file) {
	   try {
		   StandardPBEStringEncryptor encryptorDecryptor = new StandardPBEStringEncryptor();
		   encryptorDecryptor.setPassword("AB");
		   String encryptedFileContent = encryptorDecryptor.encrypt(FileUtils.readFileToString(file));
		   FileUtils.writeStringToFile(file, encryptedFileContent);
	   } catch(Exception e) {
	   e.printStackTrace();
	   }
	   
	   return file;
   }
   
   public File decryptFileStream(File file) {
	   try {
		   StandardPBEStringEncryptor encryptorDecryptor = new StandardPBEStringEncryptor();
		   encryptorDecryptor.setPassword("AB");
		   String encryptedFileContent = encryptorDecryptor.decrypt(FileUtils.readFileToString(file));
		   FileUtils.writeStringToFile(file, encryptedFileContent);
   } catch(Exception e) {
		   e.printStackTrace();
	   }
	   
	   return file;
   }*/
   
   public byte[] encryptStreamByAES(byte[] fin,String key){
	  /* MessageDigest md = null;
	   try {
		 md = MessageDigest.getInstance("SHA-256");
	} catch (NoSuchAlgorithmException e1) {
		e1.printStackTrace();
	}*/
	   		 byte[] keyBytes = key.getBytes();//md.digest(key.getBytes());
	   		 byte[] encryptedData = null;
			   Cipher c;
			try {
				c = Cipher.getInstance("AES");
			   SecretKeySpec k =
			     new SecretKeySpec(keyBytes, "AES");
				c.init(Cipher.ENCRYPT_MODE, k);
				 encryptedData = c.doFinal(fin);
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
			  
			   return encryptedData;
   }
   
   
}

