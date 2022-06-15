package com.example.demo;//package test.pki2048;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import signgate.crypto.util.CertUtil;
import signgate.crypto.util.FileUtil;
import signgate.crypto.util.PKCS7Util;
import signgate.crypto.util.TimeUtil;

public class PKCS7RSASignFileTest {

	List<Map<String, String>> list = new ArrayList<Map<String, String>>();
	HashMap<String, String> hashMap = new HashMap<String, String>();
	DefaultResourceLoader resourceLoader = new DefaultResourceLoader();

	public List<Map<String, String>> fileSign(String encryptionFile, String memberIndex) throws IOException, CertificateException
	{
		String policy = null;
		String startPoint = null;
		String endPoint = null;
		String path = null;
		Resource resource = resourceLoader.getResource("classpath:static/cert/");
		path = resource.getURI().getPath();

		String keyFilePath = path + "signPri.key";
		String certFilePath = path + "signCert.der";

		byte[] certBytes = FileUtil.readBytesFromFileName( certFilePath );
		byte[] keyBytes = FileUtil.readBytesFromFileName( keyFilePath );

		CertUtil certutil = null;
		try {
			certutil = new CertUtil(certBytes);
			System.out.println(certutil.getSigAlgName());
		} catch (CertificateException e1) {
			System.out.println("���� ����: " + certutil.getErrorMsg());
			System.out.println("Exception: " + e1.toString());
		}

		//서명되어 저장될 파일이름
		String p7signedFileName = SelfConfig.orgFileName + memberIndex + ".p7s";

		TimeUtil timeutil = new TimeUtil();

		PKCS7Util p7util = new PKCS7Util();

		boolean issign = false;
		try {

			issign = p7util.genDetachedSignedData(keyBytes, SelfConfig.passwd, certBytes, SelfConfig.orgFileName, p7signedFileName, 1);
		} catch (Exception e) {
			System.out.println("Error : [" + p7util.getErrorMsg() + "]");
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

		if (bverify) {
			System.out.println("PKCS7 " + certutil.getSigAlgName() + " detached Signed ���� �������: " + bverify);
		} else {
			System.out.println("Error : [" + p7utilverify.getErrorMsg() + "]");
		}
		timeutil.check();

		Set certSet = p7utilverify.getCertificateSet();
		Iterator it = certSet.iterator();
		byte[] signcert = null;

		if (bverify) {
			while (it.hasNext()) {
				X509Certificate cert = (X509Certificate) it.next();
				signcert = cert.getEncoded();
				CertUtil cu = new CertUtil(signcert);

				///
				//	�ش� �������� Ư�����(3����) ������ ���� üũ �� ������ 
				//	������� 15���̳��� �������� Ư�����(1��)���� �����ϵ��� �����Ѵ�.
				//
				policy = cu.getPolicyOid();
				startPoint = cu.getNotBefore();
				endPoint   = cu.getNotAfter();
			}
		} else {
			System.out.println("P7 ���ڼ��� ���� ����");
		}
		//hashMap.put("sign", issign);
		hashMap.put("policy", policy);
		hashMap.put("startPoint", startPoint);
		hashMap.put("endPoint", endPoint);

		list.add(hashMap);
		return list;
	}
}
