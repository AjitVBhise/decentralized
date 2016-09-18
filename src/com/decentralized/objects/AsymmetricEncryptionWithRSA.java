package com.decentralized.objects;

import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.codec.binary.Base64;

public class AsymmetricEncryptionWithRSA {
 
    private static KeyPair keyPair1;
    private static KeyPair keyPair2;
 
    private static KeyPair initKeyPair() {
        try {
            keyPair1 = KeyPairGenerator.getInstance("RSA").generateKeyPair();
            keyPair2 = KeyPairGenerator.getInstance("RSA").generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Algorithm not supported! " + e.getMessage() + "!");
        }
 
        return keyPair1;
    }
 
    public static void main(String[] args) {
        initKeyPair();
        try {
        	System.out.println("keyPair Public ::"+keyPair1.getPublic().getEncoded());
        	byte[] publickey = keyPair1.getPublic().getEncoded();
        	
        	System.out.println("keyPair Private ::"+keyPair1.getPrivate().getEncoded());
        	byte[] privatekey = keyPair1.getPublic().getEncoded();
             final Cipher cipher = Cipher.getInstance("RSA");
            final String plaintext = "0123456789abcdef";
            // ENCRYPT using the PUBLIC key
            cipher.init(Cipher.ENCRYPT_MODE, keyPair1.getPublic());
            byte[] encryptedBytes = cipher.doFinal(plaintext.getBytes());
            String chipertext = new String(toHexString(encryptedBytes));
            System.out.println("encrypted (chipertext) = " + chipertext);
 
            // DECRYPT using the PRIVATE key
            cipher.init(Cipher.DECRYPT_MODE, keyPair2.getPrivate());
            byte[] ciphertextBytes = toByteArray(chipertext);
            byte[] decryptedBytes = cipher.doFinal(ciphertextBytes);
            String decryptedString = new String(decryptedBytes);
            System.out.println("decrypted (plaintext) = " + decryptedString);
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Algorithm not supported! " + e.getMessage() + "!");
        } catch (NoSuchPaddingException | InvalidKeyException e) {
            System.err.println("Cipher cannot be created!");
            e.printStackTrace();
        } catch (BadPaddingException | IllegalBlockSizeException e) {
            System.err.println("An error occurred during the encryption!");
            e.printStackTrace();
        }
    }
    
    
    public static String toHexString(byte[] array) {
        return DatatypeConverter.printHexBinary(array);
    }

    public static byte[] toByteArray(String s) {
        return DatatypeConverter.parseHexBinary(s);
    }
}