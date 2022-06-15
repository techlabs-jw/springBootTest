package com.example.demo;//package test.pki2048;
/**********************************************************************************
 * ȭ �� �� �� : PKCS7FileTest.java
 * �ֱ� ������ : 2003/11/07
 * �ֱ� ������ : �� �� ��
 * ���� �ۼ��� : �� �� ��
 * �� �� �� �� : Java signgate toolkit test ���α׷�
 * ��       �� : 
 * ��       �� : 
 **********************************************************************************/
import signgate.crypto.util.CertUtil;
import signgate.crypto.util.PKCS7Util;
import signgate.crypto.util.FileUtil;
import signgate.crypto.util.TimeUtil;

import java.io.IOException;
import java.security.cert.CertificateException;

// PKCS7 ���ڼ���޽��� ���� ����
public class PKCS7RSASignedAndEnvelopedTest
{
	public static void main(String[] args) throws Exception
	{		
		String testData = "�׽�Ʈ ������";
		
		String signCert = "signCert.der";
		String signPri = "signPri.key";		
		String kmCert = "kmCert.der";
		String kmPri = "kmPri.key";

		String signCertPath = SelfConfig.path + signCert;
		String signPriPath = SelfConfig.path + signPri;
		String kmCertPath = SelfConfig.path + kmCert;
		String kmPriPath = SelfConfig.path + kmPri;
			
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
		//서명용 개인키, 개인키패스워드, 서명을 검증할 인증서, 암호화에 사용할인증서, 원문 데이터
		//서명용 개인키, 서명용패스워드, 서명용 검증할 인정서, 서명할데이터
		
		CertUtil certutil = null;
		try {
			certutil = new CertUtil(signcertBytes);
			System.out.println(certutil.getSigAlgName());
		} catch (CertificateException e1) {
			System.out.println("���� ����: " + certutil.getErrorMsg() );
			System.out.println("Exception: " + e1.toString() );
		}
		
		PKCS7Util p7util = new PKCS7Util();
		//p7util.setEncryptionAlgorithm("ARIA");
		
		/***** PKCS#7 SignedAndEnvelopedMessage start*******/			
		String signedAndenvelopedData = "";
		try
		{			
			signedAndenvelopedData = p7util.genSignedAndEnvelopedData(signkeyBytes, SelfConfig.passwd, signcertBytes, kmcertBytes, testData.getBytes());
			
			if(signedAndenvelopedData !=null){
				System.out.println("PKCS7 " + certutil.getSigAlgName() + " ���ڼ��� �� ��ȣȭ�޽��� ���� ����\n" + signedAndenvelopedData);
			}else{
				System.out.println("PKCS7 " + certutil.getSigAlgName() + " ���ڼ��� �� ��ȣȭ�޽��� ���� ����");
			}
		}catch(Exception e){
			System.out.println("Error : [" + p7util.getErrorMsg() + "]");
			return;
		}
		System.out.println("");
		timeutil.check();
		
		/***** PKCS#7 SignedAndEnvelopedMessage end*******/	
		
		/***** PKCS#7 SignedAndEnvelopedMessage ���� start*/	
		timeutil = new TimeUtil();
		
		PKCS7Util p7utilverify = new PKCS7Util();
			
		try{
			boolean verify_check = p7utilverify.verify(signedAndenvelopedData, kmkeyBytes, SelfConfig.passwd);
			
			if(verify_check){
				System.out.println("PKCS7 " + certutil.getSigAlgName() + " ���ڼ��� �� ��ȣȭ�޽��� ���� ����");				
			}else{
				System.out.println("PKCS7 " + certutil.getSigAlgName() + " ���ڼ��� �� ��ȣȭ�޽��� ���� ����");
				System.out.println("Error : [" + p7utilverify.getErrorMsg() + "]");
			}
		}catch(Exception e){
			System.out.println("Error : [" + p7utilverify.getErrorMsg() + "]");
			return;
		}		
		
		System.out.println("");
		timeutil.check();
		/***** PKCS#7 SignedAndEnvelopedMessage  ���� end*/

		/* PKCS#7 ��ȿ�� ���� start */
		
		//String crlPath = "C:/KICASecuXML/CRL";
		try{
			 boolean valid_check = p7utilverify.isValidCertificate(true, SelfConfig.crlPath);
			 if(valid_check){
				 System.out.println("������ ��ȿ�� ���� ����");

				 // ���� ����
				 byte[] orgbyte = p7utilverify.getRecvData();
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