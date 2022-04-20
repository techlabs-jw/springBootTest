package com.example.demo;//package test.pki2048;

import signgate.crypto.util.Base64Util;
import signgate.crypto.util.MDUtil;
import signgate.crypto.util.TimeUtil;

public class SHA512HashTest {

	public static void main(String[] args) throws Exception
	{	
		try
		{		
			byte[] data = "Hash ������".getBytes();
			
			TimeUtil timeutil = new TimeUtil();
			MDUtil mu = new MDUtil("SHA-512");			
			
			mu.update(data);			
			byte[] temp = mu.digest();
			
			System.out.println("SHA-512 �ؽ�  base64: " + Base64Util.encode(temp));	
			timeutil.check();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
}
