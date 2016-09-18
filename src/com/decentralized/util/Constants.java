package com.decentralized.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Constants {

	public static String performEncryption(String encKey) {
		char[] newKeyArray = new char[encKey.length()];
		char[] keyArray = encKey.toCharArray();
		for (int i=0;i<keyArray.length;i++) {
			newKeyArray[i] = (char) (keyArray[i]^13);
		}
		return newKeyArray.toString();
	}
	
	public static List<String> rsaTokens = new ArrayList<String>();
	static{
		rsaTokens.add("11_7");
		rsaTokens.add("5_7");
		rsaTokens.add("17_7");
		rsaTokens.add("27_5");
		rsaTokens.add("3_11");
	
	}
}
