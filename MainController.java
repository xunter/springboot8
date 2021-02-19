package com.example1.demo1;

import java.util.*;
import java.security.MessageDigest;
import java.io.*;
import java.nio.charset.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;//UTF-8
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class MainController {	

	@GetMapping(value = "/")
	public String getIndex() {
		return "index";
	}

	@GetMapping(value = "/date")
	public String getDate() {
		return new Date().toString();
	}

	@GetMapping(value = "/uuid")
	public String getUUID() {
		return java.util.UUID.randomUUID().toString();
	}

	@PostMapping(value = "/md5")
	public String getMD5(@RequestBody String cleartext) {
		try {
		 MessageDigest md = MessageDigest.getInstance("MD5");
		 byte[] cleartextBytes = cleartext.getBytes("UTF-8");
		 byte[] hashBytes = md.digest(cleartextBytes);
		 StringBuffer sb = new StringBuffer();
		 for (byte b : hashBytes) {
		  sb.append(String.format("%x", b));
		 }
		 //String hashString = new String(hashBytes, "UTF-8");
		 String hashString = sb.toString();
		 return hashString;
		} catch (java.security.NoSuchAlgorithmException err) {
			return err.toString();
		} catch (java.io.UnsupportedEncodingException err) {
			return err.toString();
		} catch (Exception err) {
			return err.toString();
		}
	}

	@PostMapping(value = "/cmd")
	public String runProcess(@RequestBody String commandLine) throws java.lang.InterruptedException, java.io.IOException {
	 Process process = Runtime.getRuntime().exec("cmd /c start cmd.exe /K \"" + commandLine + "\"");
	 //ProcessBuilder pb = new ProcessBuilder("cmd /c start cmd.exe /K \"" + commandLine + "\"");
	 //Process process = pb.start();
         process.waitFor();
	 BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
	 String line = null;
	 StringBuilder sb = new StringBuilder();
	 while ((line = reader.readLine()) != null) {
	  sb.append(line);
   	  sb.append(System.getProperty("line.separator"));
	 }
	 //String outputString = new String(baos.toByteArray(), StandardCharsets.UTF_8);
	 String outputString = sb.toString();
	 return outputString;
	}
}