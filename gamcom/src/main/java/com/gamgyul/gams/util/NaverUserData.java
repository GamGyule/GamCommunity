package com.gamgyul.gams.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NaverUserData {
	public static String getUserData(String accessToken) {
		String token = accessToken;
	    String header = "Bearer " + token;
	    try {
	        String apiURL = "https://openapi.naver.com/v1/nid/me";
	        URL url = new URL(apiURL);
	        HttpURLConnection con = (HttpURLConnection)url.openConnection();
	        con.setRequestMethod("GET");
	        con.setRequestProperty("Authorization", header);
	        int responseCode = con.getResponseCode();
	        BufferedReader br;
	        if(responseCode==200) { // 통신 성공
	            br = new BufferedReader(new InputStreamReader(con.getInputStream()));
	        } else {  // 통신 실패
	            br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
	        }
	        String inputLine;
	        StringBuffer response = new StringBuffer();
	        while ((inputLine = br.readLine()) != null) {
	            response.append(inputLine);
	        }
	        br.close();
	        return response.toString();
	    } catch (Exception e) {
	        System.out.println(e);
	    }
		return null;
	}
}
