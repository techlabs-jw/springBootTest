package com.example.demo;//package test.pki2048;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.Set;

import signgate.crypto.util.CertUtil;
import signgate.crypto.util.FileUtil;
import signgate.crypto.util.PKCS7Util;
import signgate.crypto.util.TimeUtil;

public class PKCS7RSASignFileTest {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws CertificateException 
	 */
	public static void main(String[] args) throws IOException, CertificateException {
				
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
				
		String p7signedFileName = SelfConfig.orgFileName+".p7s";
		
		TimeUtil timeutil = new TimeUtil();
		
		PKCS7Util p7util = new PKCS7Util();
		
		boolean issign = false;
		try{
			
			issign = p7util.genDetachedSignedData( keyBytes, SelfConfig.passwd, certBytes, SelfConfig.orgFileName, p7signedFileName, 1 );
		}catch(Exception e){
			System.out.println("Error : [" + p7util.getErrorMsg() + "]");
			return;
		}
		timeutil.check();
		System.out.println("PKCS7 " + certutil.getSigAlgName() + " detached Signed ���� �������: \n" + issign);
		
		boolean bverify = false;
		
////////////////////////////////////////////////////////////////////////////////////////////////////
		
		timeutil = new TimeUtil();
		PKCS7Util p7utilverify = new PKCS7Util();
		/**
			p7 ���ڼ��� ������ ��ȿ���� üũ�Ѵ�.
		*/
		bverify = p7utilverify.verifyDetachedFromFile(SelfConfig.orgFileName, p7signedFileName, 1);
		
		if(bverify){
			System.out.println("PKCS7 " + certutil.getSigAlgName() + " detached Signed ���� �������: " + bverify );
		}else{
			System.out.println("Error : [" + p7utilverify.getErrorMsg() + "]");
		}
		timeutil.check();

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
	}

}
