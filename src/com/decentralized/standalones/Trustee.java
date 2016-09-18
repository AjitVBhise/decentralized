package com.decentralized.standalones;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.decentralized.connection.DBManager;

public class Trustee extends JFrame {

	private ArrayList<String> id_types = new ArrayList<String>();
	private JTextField text_unique_id;
	private JComboBox combo_id_type;
	JButton button_generate;

	public Trustee() {
		super("Trustee");
		id_types.add("PAN Card");
		id_types.add("Aadhar Card");
		id_types.add("Driving Licence");
		id_types.add("Passport");
		setSize(500, 500);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new FlowLayout());
		JLabel label_id = new JLabel("Unique ID");
		add(label_id);
		text_unique_id = new JTextField(35);
		add(text_unique_id);
		JLabel lable_type = new JLabel("ID Type");
		add(lable_type);
		combo_id_type = new JComboBox();
		combo_id_type.addItem("Select ID Type");
		for(String type : id_types) {
			combo_id_type.addItem(type);
		}
		add(combo_id_type);
		button_generate = new JButton("Generate");
		add(button_generate);
		button_generate.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String unique_id = text_unique_id.getText().trim();
				String output = "";
				if(unique_id.length() <= 0) {
					JOptionPane.showMessageDialog(button_generate, "Please Enter unique ID.", "Error", JOptionPane.ERROR_MESSAGE);
					text_unique_id.requestFocus();
					return;
				}
				if(combo_id_type.getSelectedIndex() <= 0) {
					JOptionPane.showMessageDialog(button_generate, "Please select ID Type.", "Error", JOptionPane.ERROR_MESSAGE);
					combo_id_type.requestFocus();
					return;
				}
				String id_type = combo_id_type.getSelectedItem().toString().trim();
				
				try {
					output = generate(unique_id, id_type);
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				JOptionPane.showMessageDialog(button_generate, "Token generated Success.\n" + output, "Success", JOptionPane.INFORMATION_MESSAGE);
			}
		});
	}

	private String generate(String uniqueId, String idType) throws NoSuchAlgorithmException {
		 //get its digest
		  DBManager dbm = new DBManager();
	      MessageDigest sha = MessageDigest.getInstance("SHA-1");
	      byte[] result =  sha.digest(uniqueId.getBytes());

	      String token = hexEncode(result);
	      System.out.println("ID Generated: " + uniqueId);
	      System.out.println("Message digest: " + token);
	      
	      dbm.addNewUserWithTokenTrustee(uniqueId,token,idType);
		return "Your KDC token is ::"+token;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
			 Trustee t = new Trustee();
			 t.setVisible(true);
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
