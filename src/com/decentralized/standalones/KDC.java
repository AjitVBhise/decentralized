package com.decentralized.standalones;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.decentralized.connection.DBManager;

public class KDC extends JFrame {

	private JTextField text_trustee_token = null;
	private JButton button_submit = null;
	private ArrayList<String> id_types = new ArrayList<String>();
	private JComboBox combo_id_type;
	public KDC() {
		super("KDC");
		id_types.add("Reader");
		id_types.add("Writer");
		setSize(500, 500);
		setResizable(false);
		setLayout(new FlowLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		JLabel label_trustee_token = new JLabel("Trustee Token");
		add(label_trustee_token);
		text_trustee_token = new JTextField(25);
		add(text_trustee_token);
		JLabel lable_type = new JLabel("Permission");
		add(lable_type);
		combo_id_type = new JComboBox();
		combo_id_type.addItem("Select Permission");
		for(String type : id_types) {
			combo_id_type.addItem(type);
		}
		add(combo_id_type);
		button_submit = new JButton("SUBMIT");
		add(button_submit);
		button_submit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String trusteeToken = text_trustee_token.getText().trim();
				if(trusteeToken.length() <= 0) {
					JOptionPane.showMessageDialog(button_submit, "Please Enter trustee token.", "Error", JOptionPane.ERROR_MESSAGE);
					text_trustee_token.requestFocus();
					return;
				}
				if(combo_id_type.getSelectedIndex() <= 0) {
					JOptionPane.showMessageDialog(button_submit, "Please Permission Type.", "Error", JOptionPane.ERROR_MESSAGE);
					combo_id_type.requestFocus();
					return;
				}
				String permission = combo_id_type.getSelectedItem().toString().trim();
				String output = generateToken(trusteeToken,permission);
				JOptionPane.showMessageDialog(button_submit, ".\n" + output, "Success", JOptionPane.INFORMATION_MESSAGE);
			}
		});
	}

	private String generateToken(String trusteeToken,String permission) {
		DBManager dbm = new DBManager();
		String msg = "";
		 String token="";
		 try {
		if(dbm.validTrusteeToken(trusteeToken)){
		      String status = "Active";
		      //get its digest
		      MessageDigest sha = MessageDigest.getInstance("MD5");
		      byte[] result =  sha.digest(trusteeToken.getBytes());
		     
		      token = hexEncode(result);
		      if(permission.equals("Reader"))
		      {
		    	  token=token+"R";
		      }else
		    	  token=token+"R";
		      System.out.println("ID Generated: " + trusteeToken);
		      System.out.println("Message digest: " + token);
		      if(dbm.addNewUserWithTokenKDC(trusteeToken,token,status)){
		    	  msg="Your KDC token is"+token;
		      }else{
		    	  msg="Failed to generate KDC token";
		      }
		      
		}}
		    catch (NoSuchAlgorithmException ex) {
			      System.err.println(ex);
			    }
		
		return msg;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
			 KDC kdc = new KDC();
			 kdc.setVisible(true);
			 DBManager dbm = new DBManager();

		      //generate a random number
		     
		      
		    
		  }

		  /**
		  * The byte[] returned by MessageDigest does not have a nice
		  * textual representation, so some form of encoding is usually performed.
		  *
		  * This implementation follows the example of David Flanagan's book
		  * "Java In A Nutshell", and converts a byte array into a String
		  * of hex characters.
		  *
		  * Another popular alternative is to use a "Base64" encoding.
		  */
		  static private String hexEncode(byte[] aInput){
		    StringBuilder result = new StringBuilder();
		    char[] digits = {'0', '1', '2', '3', '4','5','6','7','8','9','a','b','c','d','e','f'};
		    for (int idx = 0; idx < aInput.length; ++idx) {
		      byte b = aInput[idx];
		      result.append(digits[ (b&0xf0) >> 4 ]);
		      result.append(digits[ b&0x0f]);
		    }
		    return result.toString();
		  }


	

}
