package com.example.demo;//package test.pki2048;

import signgate.crypto.util.Base64Util;
import signgate.crypto.util.MDUtil;
import signgate.crypto.util.TimeUtil;

public class SHA1HashTest {

	public static void main(String[] args) throws Exception
	{	
		try
		{		
			byte[] data = "Hash ������".getBytes();
			
			TimeUtil timeutil = new TimeUtil();						
			MDUtil mu = new MDUtil("SHA1");			
			
			mu.update(data);
			byte[] temp = mu.digest();
			
			System.out.println("SHA1 �ؽ�  base64: " + Base64Util.encode(temp) + " length: " + temp.length);			
			timeutil.check();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
}

