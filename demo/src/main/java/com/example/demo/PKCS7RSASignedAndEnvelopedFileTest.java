package com.example.demo;//package test.pki2048;

import java.io.IOException;
import java.security.cert.CertificateException;

import signgate.crypto.util.CertUtil;
import signgate.crypto.util.FileUtil;
import signgate.crypto.util.PKCS7Util;
import signgate.crypto.util.TimeUtil;

public class PKCS7RSASignedAndEnvelopedFileTest {

	public static void main(String[] args) throws Exception
	{			
		// ������ ��� ���� start	
		String signCertPath = SelfConfig.path + "signCert.der";
		String signPriPath = SelfConfig.path + "signPri.key";	
		String kmCertPath = SelfConfig.path + "kmCert.der";
		String kmPriPath = SelfConfig.path + "kmPri.key";
		// ������ ��� ���� end
				
		String p7filepath = SelfConfig.orgFileName+".p7se";
		String encFileName = SelfConfig.orgFileName+".enc";
		String decFileName = SelfConfig.orgFileName+".dec";
		
		TimeUtil timeutil = new TimeUtil();
		
		
		System.out.println("********************************************************");
		System.out.println("** PKCS7Util TEST ");
		System.out.println("********************************************************");
		System.out.println("");
		
		// ������, ������ Ű�� ����Ʈ�� ��ȯ start
		byte[] signcertBytes = null;
		byte[] signkeyBytes = null;

		byte[] kmcertBytes = null;
		byte[] kmkeyBytes = null;

		try{
			signcertBytes = FileUtil.readBytesFromFileName(signCertPath);	
			kmcertBytes = FileUtil.readBytesFromFileName(kmCertPath);	
		}catch(IOException io){
			System.out.println("Error : [" + io.getMessage() + "]");
			return;
		}catch(Exception e){
			if( signcertBytes == null || signcertBytes.length == 0)
				System.out.println("�������� �о���µ� �����ϼ̽��ϴ�.");	
			if( kmcertBytes == null || kmcertBytes.length == 0)
				System.out.println("�������� �о���µ� �����ϼ̽��ϴ�.");
			return;
		}

		if( signcertBytes == null || signcertBytes.length == 0){
				System.out.println("�������� �о���µ� �����ϼ̽��ϴ�.");			
				return;
		}

		if( kmcertBytes == null || kmcertBytes.length == 0){
				System.out.println("�������� �о���µ� �����ϼ̽��ϴ�.");			
				return;
		}


		try{			
			signkeyBytes = FileUtil.readBytesFromFileName(signPriPath);	
			kmkeyBytes = FileUtil.readBytesFromFileName(kmPriPath);	
		}catch(IOException io){
			System.out.println("Error : [" + io.getMessage() + "]");
			return;
		}catch(Exception e){
			if( signkeyBytes == null || signkeyBytes.length == 0)
				System.out.println("����Ű�� �о���µ� �����ϼ̽��ϴ�.");			
			return;
		}
			
		if( signkeyBytes == null || signkeyBytes.length == 0){
			System.out.println("����Ű�� �о���µ� �����ϼ̽��ϴ�.");			
			return;
		}

		if( kmkeyBytes == null || kmkeyBytes.length == 0){
			System.out.println("����Ű�� �о���µ� �����ϼ̽��ϴ�.");			
			return;
		}
		System.out.println("");
		// ������, ������ Ű�� ����Ʈ�� ��ȯ end

		CertUtil certutil = null;
		try {
			certutil = new CertUtil(signcertBytes);
			System.out.println(certutil.getSigAlgName());
		} catch (CertificateException e1) {
			System.out.println("���� ����: " + certutil.getErrorMsg() );
			System.out.println("Exception: " + e1.toString() );
		}
		
		/***** PKCS#7 SignedMessage�� ���Ϸκ��� �����Ͽ� ������ �޽����� ���Ϸ� ���� start*******/
		
		PKCS7Util p7util = new PKCS7Util();			
		try
		{			
			boolean file_save_check = p7util.genDetachedSignedAndEnvelopedData(signkeyBytes, SelfConfig.passwd, signcertBytes, kmcertBytes, SelfConfig.orgFileName, encFileName, p7filepath, 1, true);
			
			if(file_save_check){
				System.out.println("PKCS7 " + certutil.getSigAlgName() + " ���ڼ��� �� ��ȣȭ�޽����� ���Ϸ� ���� ����");
			}else{
				System.out.println("PKCS7 " + certutil.getSigAlgName() + " ���ڼ��� �� ��ȣȭ�޽����� ���Ϸ� ���� ����");
			}
		}catch(Exception e){
			System.out.println("Error : [" + p7util.getErrorMsg() + "]");
			return;
		}
		timeutil.check();
		System.out.println("");
		//***** PKCS#7 SignedMessage�� ���Ϸκ��� �����Ͽ� ������ �޽����� ���Ϸ� ���� end*******/	
		timeutil = new TimeUtil();
		/* PKCS#7 Signed And Enveloped ���ϸ� ���� start*/		
		PKCS7Util p7utilverify = new PKCS7Util();	
		
		try{
			boolean verify_check = p7utilverify.verifyDetachedFromFile(encFileName, decFileName, p7filepath, kmkeyBytes, SelfConfig.passwd, 1);
			if(verify_check){
				System.out.println("PKCS7 " + certutil.getSigAlgName() + " Signed And Enveloped ���� ���� ����");				
			}else{
				System.out.println("PKCS7 " + certutil.getSigAlgName() + " Signed And Enveloped ���� ���� ����");
				System.out.println("Error : [" + p7utilverify.getErrorMsg() + "]");
			}
		}catch(Exception e){
			System.out.println("Error : [" + p7utilverify.getErrorMsg() + "]");
			return;
		}		
		timeutil.check();
		System.out.println("");
		//* PKCS#7 Signed And Enveloped ���ϸ� ���� end*/

		/* PKCS#7 ��ȿ�� ���� start */
		
		try{
			 boolean valid_check = p7utilverify.isValidCertificate(true, SelfConfig.crlPath);
			 if(valid_check){
				 System.out.println("������ ��ȿ�� ���� ����");
			 }else{
				 System.out.println("������ ��ȿ�� ���� ���� " + p7utilverify.getErrorMsg());
			 }
		}catch(Exception e){
			System.out.println("Error : [" + p7utilverify.getErrorMsg() + "]");
			return;
		}
		
		/* PKCS#7 ��ȿ�� ���� end */
	}
}
