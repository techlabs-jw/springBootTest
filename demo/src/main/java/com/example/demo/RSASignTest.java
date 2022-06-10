package com.example.demo;//package test.pki2048;

import java.io.IOException;
import java.security.cert.CertificateException;

import org.springframework.context.annotation.Import;
import signgate.crypto.util.Base64Util;
import signgate.crypto.util.CertUtil;
import signgate.crypto.util.FileUtil;
import signgate.crypto.util.SignUtil;
import signgate.crypto.util.TimeUtil;


//SHA1withRSA ���ڼ��� ����
public class RSASignTest {

	/**
	  * @param args
	  * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		String keyFilePath = SelfConfig.path + "signPri.key";
		String certFilePath = SelfConfig.path + "signCert.der";
		
		byte[] certBytes = FileUtil.readBytesFromFileName( certFilePath );
		byte[] keyBytes = FileUtil.readBytesFromFileName( keyFilePath );
				
		byte[] sign = null;
		
		TimeUtil timeutil = new TimeUtil();
		
		String testData = "abcdeweqwedwqeeqw";		
		
		CertUtil certutil = null;
		try {
			certutil = new CertUtil(certBytes);
			System.out.println(certutil.getSigAlgName());
		} catch (CertificateException e1) {
			System.out.println("���� ����: " + certutil.getErrorMsg() );
			System.out.println("Exception: " + e1.toString() );
		}
		
		//�����ϱ�		
		SignUtil signgen = new SignUtil(certutil.getSigAlgName());
		
		try
		{
			//���� ��ü �ʱ�ȭ
			signgen.signInit( keyBytes, SelfConfig.passwd );
			
			//���� ��ü ������Ʈ
			signgen.signUpdate(testData.getBytes());
			
			//���� ����	
			sign = signgen.signFinal();
			
			System.out.println("sign byte length: " + sign.length);
			System.out.println("SHA1withRSA ���� ���� base64: \n[" + Base64Util.encode(sign) + "]");
		}
		catch(Exception e) 
		{			
			//e.getStackTrace();
			System.out.println("���� ����: " + signgen.getErrorMsg() );
			System.out.println("Exception: " + signgen.getStackTraceMsg() );
		}
		timeutil.check();
		System.out.println("");
		
////////////////////////////////////////////////////////////////////////////////////////////////////		

		timeutil = new TimeUtil();
		SignUtil signverify = new SignUtil(certutil.getSigAlgName());
		//���� �����ϱ�
		try
		{
			signverify.verifyInit(certBytes);
			
			//���� ��ü ������Ʈ
			signverify.verifyUpdate(testData.getBytes());
			
			//���� ����
			if(signverify.verifyFinal(sign))
			{
				System.out.println("SHA1withRSA ���� ��������");
			}
			else
			{
				System.out.println("���� ����: " + signverify.getErrorMsg() + " " + signverify.getStackTraceMsg());
			}
		}
		catch(Exception e)
		{
			//e.getStackTrace();
			System.out.println("Exception: " + e.getMessage() + " ���� ����: " + signverify.getErrorMsg() );
		}
		
		timeutil.check();
	}

}
