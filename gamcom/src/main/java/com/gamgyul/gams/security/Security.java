package com.gamgyul.gams.security;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Security {
	
	public static String getSecurity(String target) {
		String result = "";
		if(target != null && target != "") {
			result = getSha512(target);
		}else {
			System.out.println("target 값이 비어있음 > "+target);
		}
		return result;
	}
	
	private static String getSha512(String target) {
		String encTarget = "";
		
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-512"); // SHA-512 내장 메소드
			byte[] bytes = target.getBytes(Charset.forName("UTF-8"));
			md.update(bytes); // 암호화 처리 된 비밀번호가 bytes안에 있음
			
			encTarget = Base64.getEncoder().encodeToString(md.digest());
		}catch(NoSuchAlgorithmException e) {
			e.printStackTrace();
		}catch(NullPointerException e2) {
			e2.printStackTrace();
		}
		
		return encTarget;
		
	}
}
