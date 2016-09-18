package com.decentralized.connection;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.decentralized.objects.UserInfo;
public class DBManager {
	public static Connection conn;
	
	public DBManager(){	
        try {
            String dbURL = "jdbc:mysql://localhost:3306/decentralization";
            String username = "root";
            String password = "admin";

            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(dbURL, username, password);

	}catch(Exception e){
		e.printStackTrace();
	}
	}
	
 /* Method to validate an employee and register its device into database*/
	public boolean validateUser(String username,String password) throws SQLException {
		boolean result = false;
		Statement stmt = conn.createStatement();
		String strSelect = "select username,password from user";
        ResultSet rset = stmt.executeQuery(strSelect);
        while(rset.next()){
        	if(username.equals(rset.getString("username"))&& password.equals(rset.getString("password"))){
        		result= true;
        	}
        }
        stmt.close();
        return result;
	}
	
	
	/* Method to insert user into database*/
	public boolean insertUser(String firstName,String lastName, String username, String password, String email,String contact,String accessToken,String department,String year,String role){
		boolean result = false;
		
		
		try{
		PreparedStatement pstmt= conn.prepareStatement("insert into user(first_name,last_name,username,password,email,contact,access_token,department,year,role) values(?,?,?,?,?,?,?,?,?,?)");
		pstmt.setString(1,firstName);
		pstmt.setString(2,lastName);
		pstmt.setString(3,username);
		pstmt.setString(4,password);
		pstmt.setString(5,email);
		pstmt.setString(6,contact);
		pstmt.setString(7,accessToken);
		pstmt.setString(8,department);
		pstmt.setString(9,year);
		pstmt.setString(10,role);
		if(pstmt.executeUpdate()==1){
			result = true;
		}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/* Method to insert user into database*/
	public boolean updatetUser( String designation,String team,
			String department,int userId){
		boolean result = false;
		try{
			PreparedStatement pstmt= conn.prepareStatement("update  user set designation=?,team=?,department=? where id=?");
			pstmt.setString(1,designation);
			pstmt.setString(2,team);
			pstmt.setString(3,department);
			pstmt.setInt(4,userId);
			
			if(pstmt.executeUpdate()==1){
				result = true;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	/*Method to validate login credentials for portal*/
	public boolean validateAdmin(String adminname, String password) throws SQLException { 
		boolean result = false;
		Statement stmt = conn.createStatement();
		String strSelect = "select admin_name, admin_password from admin";
        ResultSet rset = stmt.executeQuery(strSelect);
        while(rset.next()){
        	if(adminname.equals(rset.getString("admin_name"))&& password.equals(rset.getString("admin_password"))){
        		result= true;
        	}
        }
        stmt.close();
        return result;

	}

	/* Method to validate a user by checking username */
	public boolean validUserName(String username){
		boolean result = false;
		Statement stmt;
		try {
			stmt = conn.createStatement();
		
		String strSelect = "select username from user";
        ResultSet rset = stmt.executeQuery(strSelect);
        while(rset.next()){
        	if(username.equals(rset.getString("username"))){
        		result= true;
        		break;
        	}
        }
        stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
		 return result;	
	}
	public boolean isFileExist(String fileName){
		boolean result= false;
		try{
		PreparedStatement pstmt= conn.prepareStatement("select file_id from file where file_name = ?");
		pstmt.setString(1,fileName); 
		ResultSet rset = pstmt.executeQuery();
		if(rset.next()){
				result = true;
				
		}
		pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	 return result;

		
	}
	public boolean saveFileToDatabase(String fileName, FileInputStream fstream) {
		boolean result = false;
		try{
		PreparedStatement pstmt= conn.prepareStatement("insert into file(file_name,file_data) values(?,?)");
		pstmt.setString(1,fileName); 
		pstmt.setBlob(2,fstream);
		if(pstmt.executeUpdate()==1){
			result = true;
		}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
    
	public int getFileIDByName(String fileName) {
		int result = 0;
		try {
			PreparedStatement pstmt= conn.prepareStatement("select file_id from file where file_name = ?");
			pstmt.setString(1,fileName); 
        ResultSet rset = pstmt.executeQuery();
        while(rset.next()){
        		result= rset.getInt("file_id");
        		break;
        }
        pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
		 return result;
	}

	public boolean updateFileAccessPolicy(String access_expression,String access_value,InputStream inputStream, int fileId)
	{
		boolean result = false;
		//System.out.println("Inaccess_expression:::"+access_expression);
		System.out.println("Inaccess_value::"+access_value);
		System.out.println("fileId::"+fileId);
		try{
			PreparedStatement pstmt= conn.prepareStatement("update file SET access_expression = ?,access_value=?,file_data=? WHERE id = ?");
			pstmt.setString(1,access_expression);
			pstmt.setString(2,access_value);
			pstmt.setBlob(3, inputStream);
			pstmt.setInt(4,fileId);
			if(pstmt.executeUpdate()==1){
				result = true;
			}
			}catch (Exception e) {
				e.printStackTrace();
			}
			return result;
		}
	
	public HashMap<Integer,String> getViewFiles()
	{
		HashMap<Integer,String> result = new HashMap<Integer,String>();
		try{
			Statement stmt= conn.createStatement();
			ResultSet rset = stmt.executeQuery("select * from file");
			while(rset.next()){
	        	result.put(rset.getInt("file_id"),rset.getString("file_name"));
	        	}
	         stmt.close();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		
		return result;
		
	}
	public ResultSet getViewFilesNew()
	{
		ResultSet rset =null;
		try{
			Statement stmt= conn.createStatement();
			rset = stmt.executeQuery("select * from file");
			
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		return rset;
	}
	public String getAccessExpression(int fileId)
	{
		String accessExpression = null;
		try {
			System.out.println("Inside db :"+fileId);
			PreparedStatement pstmt= conn.prepareStatement("select access_expression ,file_name from file where id =?");
			pstmt.setInt(1,fileId); 
			System.out.println(pstmt);
			ResultSet rset = pstmt.executeQuery();
			while(rset.next()){
				accessExpression= rset.getString("access_expression");
				break;
        	}
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return accessExpression;
		
	}
	
	public boolean isAccess_permission(UserInfo user,String fileName)
	{	boolean result = false;
		try{
		PreparedStatement pstmt= conn.prepareStatement("select access_expression,access_value,file_name from file where file_name=?");
		pstmt.setString(1, fileName);
		ResultSet rset = pstmt.executeQuery();
		if(rset.next())
		{
		  String accessExpression = rset.getString("access_expression");
		  String accessValue = rset.getString("access_value");
		  List<String> accessExprList = convertStringToList(accessExpression);
		  List<String> accessValList = convertStringToList(accessValue);
		  for(String expr:accessExprList){
			 if(expr.equals("role")){
				 if(accessValList.contains(user.getRole())){
					 result=true;
					 break;
				 }
			 }
			 if(expr.equals("department")){
				 if(accessValList.contains(user.getDepartment())){
					 result=true;
					 break;
				 }
			 }
			 if(expr.equals("role&year")){
				 if(accessValList.contains(user.getRole()+"&"+user.getYear())){
					 result=true;
					 break;
				 }
			 }
			 if(expr.equals("role&department")){
				 if(accessValList.contains(user.getRole()+"&"+user.getDepartment())){
					 result=true;
					 break;
				 }
			 }
			 if(expr.equals("role&department&year")){
				 if(accessValList.contains(user.getRole()+"&"+user.getDepartment()+"&"+user.getYear())){
					 result=true;
					 break;
				 }
			 }
			 if(expr.equals("NA")){
				 result=true;
				 break;
			 }
		  }
		}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
		
	}
	
	private List<String> convertStringToList(String accessExpression) {
		List<String> result = new ArrayList<String>();
		String[] expressions = accessExpression.split("#");
		for(String str:expressions){
			result.add(str);
		}
		return result;
	}

	public UserInfo getUserInfo(String username)
	{   UserInfo userInfo = new UserInfo();
		
	try{
		PreparedStatement pstmt = conn.prepareStatement("select * from user where username=?");
		pstmt.setString(1, username);
        ResultSet rset = pstmt.executeQuery();
		while(rset.next()){
        	userInfo.setId(rset.getString("id"));
        	userInfo.setFirstName(rset.getString("first_name"));
        	userInfo.setLastName(rset.getString("last_name"));
        	userInfo.setUsername(rset.getString("username"));
        	userInfo.setEmail(rset.getString("email"));
        	userInfo.setContact(rset.getString("contact"));
        	userInfo.setAccessToken(rset.getString("access_token"));
        	userInfo.setDepartment(rset.getString("department"));
        	userInfo.setYear(rset.getString("year"));
        	userInfo.setRole(rset.getString("role"));
        	}
        }
		catch (Exception e) {
			e.printStackTrace();
		}
		return userInfo;
	}

	public UserInfo getUserDetail(int userId)
	{   UserInfo userInfo = new UserInfo();
		
	try{
		PreparedStatement pstmt = conn.prepareStatement("select * from user where id=?");
		pstmt.setInt(1, userId);
        ResultSet rset = pstmt.executeQuery();
        while(rset.next()){
        	userInfo.setId(rset.getString("id"));
        	userInfo.setFirstName(rset.getString("first_name"));
        	userInfo.setLastName(rset.getString("last_name"));
        	userInfo.setUsername(rset.getString("username"));
        	userInfo.setEmail(rset.getString("email"));
        	userInfo.setContact(rset.getString("contact"));
        	userInfo.setAccessToken(rset.getString("access_token"));
        	userInfo.setDepartment(rset.getString("department"));
        	userInfo.setYear(rset.getString("year"));
        	userInfo.setRole(rset.getString("role"));
        	System.out.println("Department"+rset.getString("designation"));
        	}
        }
		catch (Exception e) {
			e.printStackTrace();
		}
	
		return userInfo;
		
	}
	public Blob download(String fileName) {
		Blob blob = null;
		try{
			PreparedStatement pstmt= conn.prepareStatement("select * from file");
			ResultSet rset = pstmt.executeQuery();
			
			while(rset.next())
			{
			  if(fileName.equals(rset.getString("file_name"))){
				  blob = rset.getBlob("file_data");
				  break;
			  }
			}
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		return blob;
	}
	public ArrayList<UserInfo> userInfo()
	{ 
		ArrayList<UserInfo> userInfoList =new ArrayList<UserInfo>();
		try{
		PreparedStatement pstmt = conn.prepareStatement("select * from user");
		 ResultSet rset = pstmt.executeQuery();
		System.out.println(pstmt);
		while(rset.next()){
			UserInfo userInfo = new UserInfo();
        	userInfo.setId(rset.getString("id"));
        	userInfo.setFirstName(rset.getString("first_name"));
        	userInfo.setLastName(rset.getString("last_name"));
        	userInfo.setUsername(rset.getString("username"));
        	userInfo.setEmail(rset.getString("email"));
        	userInfo.setContact(rset.getString("contact"));
        	userInfo.setAccessToken(rset.getString("access_token"));
        	userInfo.setDepartment(rset.getString("department"));
        	userInfo.setYear(rset.getString("year"));
        	userInfo.setRole(rset.getString("role"));
        	userInfoList.add(userInfo);
        	}
        }catch (Exception e) {
			// TODO: handle exception
        	e.printStackTrace();
		}
		
		return userInfoList;
		
	}

	public String getValue(int fileId) {
		String acc_val=null;
		try {
			System.out.println("Inside db :"+fileId);
			PreparedStatement pstmt= conn.prepareStatement("select access_value from file where id =?");
			pstmt.setInt(1,fileId); 
			System.out.println(pstmt);
			ResultSet rset = pstmt.executeQuery();
			while(rset.next()){
				acc_val= rset.getString("access_value");
				break;
        	}
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return acc_val;
	}

	public boolean addNewUserWithTokenTrustee(String uniqueId, String token, String idType) {
		boolean result = false;
		try{
		PreparedStatement pstmt= conn.prepareStatement("insert into trustee(unique_id,trustee_token,id_type) values(?,?,?)");
		pstmt.setString(1,uniqueId); 
		pstmt.setString(2,token);
		pstmt.setString(3,idType);
		if(pstmt.executeUpdate()==1){
			result = true;
		}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public boolean addNewUserWithTokenKDC(String trusteeToken, String token,String status) {
		boolean result = false;
		try{
		PreparedStatement pstmt= conn.prepareStatement("insert into kdc(trustee_token,access_token,validity,status) values(?,?,?,?)");
		pstmt.setString(1,trusteeToken); 
		pstmt.setString(2,token);
		pstmt.setString(3,"valid");
		pstmt.setString(4,status);
		if(pstmt.executeUpdate()==1){
			result = true;
		}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return result;
		
	}

	public boolean validTrusteeToken(String trusteeToken) { 
		boolean result = false;
		try{
		PreparedStatement pstmt= conn.prepareStatement("select id from trustee where trustee_token=?");
		pstmt.setString(1,trusteeToken); 
		ResultSet rs = pstmt.executeQuery();
		if(rs.next()){
			result = true;
		}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public boolean validateKDCToken(String accessToken) {
		boolean result = false;
		try{
		PreparedStatement pstmt= conn.prepareStatement("select validity,status from kdc where access_token=?");
		pstmt.setString(1,accessToken); 
		ResultSet rs= pstmt.executeQuery();
		if(rs.next()){
			if(rs.getString("status").equals("Active")){
				result=true;
			}
		}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public boolean updateAccessPolicy(List<String> listAccessExpressions,List<String> listAccessValues, Integer fileId) {
		
		boolean result = false;
		String accessExpression = convertListToCSVString(listAccessExpressions);
		String accessValue = convertListToCSVString(listAccessValues);
		try{
			PreparedStatement pstmt= conn.prepareStatement("update file SET access_expression = ?,access_value=? WHERE file_id = ?");
			pstmt.setString(1,accessExpression);
			pstmt.setString(2,accessValue);
			pstmt.setInt(3, fileId);
			if(pstmt.executeUpdate()==1){
				result = true;
			}
			}catch (Exception e) {
				e.printStackTrace();
			}
			return result;
	}

	private String convertListToCSVString(List<String> listAccessExpressions) {
		StringBuilder sb = new StringBuilder();
		for (String string : listAccessExpressions) {
			sb.append(string).append("#");
		}
		return sb.substring(0,sb.lastIndexOf("#")).toString();
	}

	public boolean addAccessPolicyAndFile(List<String> listAccessExpressions,List<String> listAccessValues, InputStream myInputStream,String fileName,String ownerToken) {
		boolean result = false;
		String accessExpression = convertListToCSVString(listAccessExpressions);
		String accessValue = convertListToCSVString(listAccessValues);
		try{
		PreparedStatement pstmt= conn.prepareStatement("insert into file(file_name,file_data,access_expression,access_value,owner_token) values(?,?,?,?,?)");
		pstmt.setString(1,fileName); 
		pstmt.setBlob(2,myInputStream);
		pstmt.setString(3,accessExpression);
		pstmt.setString(4, accessValue);
		pstmt.setString(5, ownerToken);
		if(pstmt.executeUpdate()==1){
			result = true;
		}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public HashMap<Integer,String> getViewFilesByUserToken(String token)
	{
		HashMap<Integer,String> result = new HashMap<Integer,String>();
		try{
			PreparedStatement pstmt= conn.prepareStatement("select * from file where owner_token=?");
			pstmt.setString(1,token);
			ResultSet rset = pstmt.executeQuery();
			while(rset.next()){
	        	result.put(rset.getInt("file_id"),rset.getString("file_name"));
	        	}
			pstmt.close();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		
		return result;
		
	}
}
