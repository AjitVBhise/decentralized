package com.decentralized.servlets;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

public class TestClass {
	public static void main(String[] args) {
		try {
			// StandardPBEStringEncryptor encryptorDecryptor = new
			// StandardPBEStringEncryptor();
			// encryptorDecryptor.setPassword("AB");
			// String encryptedFileContent =
			// encryptorDecryptor.encrypt(FileUtils.readFileToString(file));
			// FileUtils.writeStringToFile(file, encryptedFileContent);

			/*StandardPBEStringEncryptor encryptorDecryptor = new StandardPBEStringEncryptor();
			encryptorDecryptor.setPassword("AB");
			String encryptedFileContent = encryptorDecryptor.decrypt(FileUtils.readFileToString(file));
			FileUtils.writeStringToFile(file, encryptedFileContent);
			*/
			System.out.println("Complted");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static InputStream encryptFileContent(InputStream file,String key){
		try{
			System.out.println("iNSIDE testcontent"+key);
			String PREFIX = "stream2file";
			String SUFFIX = ".tmp";
			File file1 =File.createTempFile(PREFIX, SUFFIX);
			file1.deleteOnExit();
			FileOutputStream out = new FileOutputStream(file1); 
	        InputStream in =file;
			IOUtils.copy(in, out);
	        StandardPBEStringEncryptor encryptorDecryptor = new StandardPBEStringEncryptor();
	        if(key.contains(","))
	        	encryptorDecryptor.setPassword(",");
	        else
	        	encryptorDecryptor.setPassword(key);
			String decryptFileContent = encryptorDecryptor.encrypt(FileUtils.readFileToString(file1));
			FileUtils.writeStringToFile(file1, decryptFileContent);
			file = new FileInputStream(file1); 
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		 
		return file;
	}

	
	public static InputStream decriptFileContent(InputStream file,String key){
		try{
			System.out.println("decriptFileContent"+key);
			String PREFIX = "stream2file";
			String SUFFIX = ".tmp";
			File file1 =File.createTempFile(PREFIX, SUFFIX);
			file1.deleteOnExit();
			FileOutputStream out = new FileOutputStream(file1); 
	        InputStream in =file;
			IOUtils.copy(in, out);
	        StandardPBEStringEncryptor encryptorDecryptor = new StandardPBEStringEncryptor();
	        if(key.contains(","))
	        	encryptorDecryptor.setPassword(",");
	        else
	        	encryptorDecryptor.setPassword(key);
			String decryptFileContent = encryptorDecryptor.decrypt(FileUtils.readFileToString(file1));
			FileUtils.writeStringToFile(file1, decryptFileContent);
			file = new FileInputStream(file1); 
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		 
		return file;
	}

}
