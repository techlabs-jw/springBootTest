package com.example.demo;//package test.pki2048;

import java.security.cert.CertificateException;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import signgate.crypto.util.CertUtil;
import signgate.crypto.util.FileUtil;
import signgate.crypto.util.PKCS7Util;
import signgate.crypto.util.TimeUtil;

/**********************************************************************************
 * ȭ �� �� �� : PKCS7MutliSignTest.java
 * �ֱ� ������ : 2003/11/07
 * �ֱ� ������ : �� �� ��
 * ���� �ۼ��� : �� �� ��
 * �� �� �� �� : Java signgate toolkit test ���α׷�
 * ��       �� : 
 * ��       �� : 
 **********************************************************************************/


// PKCS7 ��ƼSign ����
public class PKCS7RSAMutliSignTest
{
	DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
	public void MutiliSignTest(String encryptionData) throws Exception {

		String path = null;
		Resource resource = resourceLoader.getResource("classpath:static/cert/");
		path = resource.getURI().getPath();

		String signCertPath = path + "signCert.der";
		String signPriPath = path + "signPri.key";

		byte[] certBytes = FileUtil.readBytesFromFileName(signCertPath);
		byte[] keyBytes = FileUtil.readBytesFromFileName(signPriPath);

		//String testData = "1234567890abcdefghijklmnopqrstuvwxyz���ѹα�";
		String testData = encryptionData;
		System.out.println(testData);
		
		CertUtil certutil = null;
		try {
			certutil = new CertUtil(certBytes);
			System.out.println(certutil.getSigAlgName());
		} catch (CertificateException e1) {
			System.out.println("errorMessage: " + certutil.getErrorMsg() );
			System.out.println("Exception: " + e1.toString() );
		}

		TimeUtil timeutil = new TimeUtil();
		
		PKCS7Util p7util = new PKCS7Util();
		PKCS7Util p7utilverify = new PKCS7Util();
		
		System.out.println("********************************************************");
		System.out.println("** PKCS7MutliSignTest TEST ");
		System.out.println("********************************************************");
		System.out.println("");
		String p7sign1 = null;
		
		try{
			p7sign1 = p7util.genSignedData(keyBytes, SelfConfig.passwd, certBytes, testData.getBytes());
		}catch(Exception e){
			System.out.println("Error : [" + p7util.getErrorMsg() + "]");
			return;
		}
		timeutil.check();

		System.out.println("PKCS#7 SignedData Information1: \n" + p7sign1);
		System.out.println("");
		timeutil = new TimeUtil();

		boolean p7sign1_check = p7utilverify.verify(p7sign1);

		System.out.println("PKCS#7 SignedData check1: " + p7sign1_check);
		System.out.println("");
		timeutil.check();

		String p7sign2 = null;

		timeutil = new TimeUtil();
		try{
			p7sign2 = p7util.addSign(p7sign1, keyBytes, SelfConfig.passwd, certBytes);
		}catch(Exception e){
			System.out.println("Error : [" + p7util.getErrorMsg() + "]");
			return;
		}

		System.out.println("PKCS#7 SignedData Information2: \n" + p7sign2);
		System.out.println("");
		timeutil.check();
		
		timeutil = new TimeUtil();
		boolean p7sign2_check = p7utilverify.verify(p7sign2);

		System.out.println("PKCS#7 SignedData Check2: " + p7sign2_check);
		System.out.println("");
		timeutil.check();
		
		timeutil = new TimeUtil();
		String p7sign3 = null;
		try{
			p7sign3 = p7util.addSign(p7sign2, keyBytes, SelfConfig.passwd, certBytes);
		}catch(Exception e){
			System.out.println("Error : [" + p7util.getErrorMsg() + "]");
			return;
		}
		timeutil.check();
		System.out.println("PKCS#7 SignedData Information3: \n" + p7sign3);
		timeutil = new TimeUtil();
		
		boolean p7sign3_check = p7utilverify.verify(p7sign3);

		System.out.println("PKCS#7 SignedData Check3: " + p7sign3_check);
		timeutil.check();
	}

}