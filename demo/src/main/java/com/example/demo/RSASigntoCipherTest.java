package com.example.demo;//package test.pki2048;

import java.io.IOException;
import java.security.cert.CertificateException;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import signgate.crypto.util.Base64Util;
import signgate.crypto.util.CertUtil;
import signgate.crypto.util.CipherUtil;
import signgate.crypto.util.FileUtil;
import signgate.crypto.util.SignUtil;
import signgate.crypto.util.TimeUtil;

public class RSASigntoCipherTest {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		
		String data = "sg";
		
		String keyFilePath = "C:/KICASecuXML/cert/web/signPri.key";
		String certFilePath = "C:/KICASecuXML/cert/web/signCert.der";
		
		DefaultResourceLoader resourceLoader = null;
		Resource resource = resourceLoader.getResource("classpath:/static/cert/");
		SelfConfig.path = resource.getURI().getPath();

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
		//�����ϱ�		
		SignUtil signgen = new SignUtil(certutil.getSigAlgName());		
		try
		{
			//���� ��ü �ʱ�ȭ
			signgen.signInit( keyBytes, "a123456A" );			
			//���� ��ü ������Ʈ
			signgen.signUpdate(data.getBytes());			
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
		
		String ServerKmCertFile = SelfConfig.path + "kmCert.der";
		String ServerKmKeyFile =  SelfConfig.path + "kmPri.key";		
		byte[] ServerKmCert = FileUtil.readBytesFromFileName(ServerKmCertFile);
		byte[] ServerKmKey = FileUtil.readBytesFromFileName(ServerKmKeyFile);
		
		CipherUtil enccipher = new CipherUtil("RSA");
		
		enccipher.encryptInit(ServerKmCert);
		byte[] encdatabyte = enccipher.encryptUpdate(data.getBytes());
		if(encdatabyte == null){
			System.out.println(enccipher.getErrorMsg());
		}		
		enccipher.encryptFinal();
		
		System.out.println("RSA ����Ű ��ȣȭ\n" + Base64Util.encode(encdatabyte) + "length: " + encdatabyte.length);
		
////////////////////////////////////////////////////////////////////////////////////////////////////
		
		CipherUtil deccipher = new CipherUtil("RSA");
		
		deccipher.decryptInit(ServerKmKey, SelfConfig.passwd);
		byte[] decdata = deccipher.decryptUpdate(encdatabyte);
		deccipher.decryptFinal();
		
		System.out.println();
		if(decdata != null){
			System.out.println("RSA ����Ű ��ȣȭ\n" + new String(decdata));
		}else{
			System.out.println(deccipher.getErrorMsg());
		}
		
		SignUtil signverify = new SignUtil(certutil.getSigAlgName());
		//���� �����ϱ�
		try
		{
			signverify.verifyInit(certBytes);
			
			//���� ��ü ������Ʈ
			signverify.verifyUpdate(decdata);
			
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
	}

}
