package com.example.demo;//package test.pki2048;

import java.util.Iterator;
import java.util.Set;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import signgate.crypto.util.CertUtil;
import signgate.crypto.util.FileUtil;
import signgate.crypto.util.PKCS7Util;
import signgate.crypto.util.TimeUtil;

// PKCS7 ���� ���� �� �������� ���� ����
public class PKCS7SignTest
{
	public static void main(String[] args) throws Exception
	{	
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
		
		String testData = "���� ���ڼ����� �����̿�~";
		
		TimeUtil timeutil = new TimeUtil();
		
		PKCS7Util p7util = new PKCS7Util();
		
		String signed = null;
		
		try{
			signed = p7util.genSignedData(keyBytes, SelfConfig.passwd, certBytes, testData.getBytes());
		}catch(Exception e){
			System.out.println("Error : [" + p7util.getErrorMsg() + "]");
			return;
		}
		
		System.out.println("PKCS7 " + certutil.getSigAlgName() + " SignedData\n" + signed);
		
		timeutil.check();
		
////////////////////////////////////////////////////////////////////////////////////////////////////
		
		boolean bverify = false;

		//PKCS7Type�� ��(0), ���ڼ���(1), ��ȣȭ(2), ���ڼ���+��ȣȭ(3) Ÿ���� ����
		System.out.println("Type: " + p7util.getPKCS7Type(signed.getBytes()));
		
		timeutil = new TimeUtil();
		
		PKCS7Util p7utilverify = new PKCS7Util();
		/**
			p7 ���ڼ��� ������ ��ȿ���� üũ�Ѵ�.
		*/
		bverify = p7utilverify.verify(signed, null, null);
		
		if(bverify){
			System.out.println("PKCS7 " + certutil.getSigAlgName() + " ����: " + bverify );
		}else{
			System.out.println("Error : [" + p7utilverify.getErrorMsg() + "]");
		}

		System.out.println(new String(p7utilverify.getRecvData()));

		Set certSet  = p7utilverify.getCertificateSet();
		Iterator it = certSet.iterator();
		byte[] signcert = null;

		if(bverify)
		{
			while( it.hasNext() ) {
				X509Certificate cert = (X509Certificate)it.next();
				signcert = cert.getEncoded();
				CertUtil cu = new CertUtil( signcert);

				///
				//	�ش� �������� Ư�����(3����) ������ ���� üũ �� ������ 
				//	������� 15���̳��� �������� Ư�����(1��)���� �����ϵ��� �����Ѵ�.
				//
				System.out.println("�������� ��å Oid: " + cu.getPolicyOid());
				System.out.println("������ �������: " + cu.getNotAfter());			
			}
		}
		else
		{
			System.out.println("P7 ���ڼ��� ���� ����");
		}
		timeutil.check();
	}
}