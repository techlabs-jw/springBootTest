package com.example.demo;//package test.pki2048;
import java.security.NoSuchAlgorithmException;

import signgate.crypto.util.RandomUtil;
import signgate.crypto.util.Base64Util;
import signgate.crypto.util.TimeUtil;

public class RandomTest {

	public static void main(String[] arg) throws NoSuchAlgorithmException{
		TimeUtil timeutil = new TimeUtil();		
		System.out.println("������ base64\n" + Base64Util.encode(RandomUtil.genRand(20)));
		timeutil.check();
	}
}
