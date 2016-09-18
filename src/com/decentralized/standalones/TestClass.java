package com.decentralized.standalones;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Blob;

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
			System.out.println("Complted");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public File encryptFileContent(File file ) {
		try{
			 StandardPBEStringEncryptor encryptorEncryptor = new StandardPBEStringEncryptor();
			 encryptorEncryptor.setPassword("AB");
			 String encryptedFileContent =
				 encryptorEncryptor.encrypt(FileUtils.readFileToString(file));
			 FileUtils.writeStringToFile(file, encryptedFileContent);

		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return file;
	}
	
	public InputStream decriptFileContent(InputStream file){
		try{
			String PREFIX = "stream2file";
			String SUFFIX = ".tmp";
			File file1 =File.createTempFile(PREFIX, SUFFIX);
			file1.deleteOnExit();
			FileOutputStream out = new FileOutputStream(file1); 
	        InputStream in = null;
			IOUtils.copy(in, out);
	        StandardPBEStringEncryptor encryptorDecryptor = new StandardPBEStringEncryptor();
			encryptorDecryptor.setPassword("AB");
			String decryptFileContent = encryptorDecryptor.decrypt(FileUtils.readFileToString(file1));
			FileUtils.writeStringToFile(file1, decryptFileContent);
			
			}
		catch (Exception e) {
			e.printStackTrace();
		}
		 
		return file;
	}

}
