package com.example.demo;//package test.pki2048;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.cert.CertificateException;

import signgate.crypto.util.Base64Util;
import signgate.crypto.util.CertUtil;
import signgate.crypto.util.FileUtil;
import signgate.crypto.util.SignUtil;
import signgate.crypto.util.TimeUtil;

public class RSASignFileTest {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
				
		InputStream orgins = new FileInputStream(SelfConfig.orgFileName);
		InputStream orgins1 = new FileInputStream(SelfConfig.orgFileName);
		
		String keyFilePath = SelfConfig.path + "signPri.key";
		String certFilePath = SelfConfig.path + "signCert.der";		
		
		byte[] certBytes = FileUtil.readBytesFromFileName( certFilePath );
		byte[] keyBytes = FileUtil.readBytesFromFileName( keyFilePath );
		
		CertUtil certutil = null;
		try {
			certutil = new CertUtil(certBytes);
			System.out.println(certutil.getSigAlgName());
		} catch (CertificateException e1) {
			System.out.println("���� ����: " + certutil.getErrorMsg() );
			System.out.println("Exception: " + e1.toString() );
		}
				
		byte[] sign = null;
		
		TimeUtil timeutil = new TimeUtil();
		
		//�����ϱ�		
		SignUtil signgen = new SignUtil(certutil.getSigAlgName());
		
		try
		{
			//���� ��ü �ʱ�ȭ
			signgen.signInit( keyBytes, SelfConfig.passwd );
			
			//���� ��ü ������Ʈ
			signgen.signUpdate(orgins);
			
			//���� ����	
			sign = signgen.signFinal();
			
			System.out.println("sign byte length: " + sign.length);
			System.out.println("��뷮 ���� SHA1withRSA ���� ���� base64: \n[" + Base64Util.encode(sign) + "]");		
		}
		catch(Exception e) 
		{			
			//e.getStackTrace();
			System.out.println("���� ����: " + signgen.getErrorMsg() );
			System.out.println("Exception: " + signgen.getStackTraceMsg() );
		}
		
		System.out.println("");
		timeutil.check();
				
////////////////////////////////////////////////////////////////////////////////////////////////////
		
		timeutil = new TimeUtil();
		
		SignUtil signverify = new SignUtil(certutil.getSigAlgName());
		//���� �����ϱ�
		try
		{
			signverify.verifyInit(certBytes);
			
			//���� ��ü ������Ʈ
			signverify.verifyUpdate(orgins1);
			
			//���� ����
			if(signverify.verifyFinal(sign))
			{
				System.out.println("��뷮 ���� SHA1withRSA ���� ��������");
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
