package com.example.demo;//package test.pki2048;
/**********************************************************************************
 * ȭ �� �� �� : EnvPKCS7Test.java
 * �ֱ� ������ : 2003/11/07
 * �ֱ� ������ : �� �� ��
 * ���� �ۼ��� : �� �� ��
 * �� �� �� �� : Java signgate toolkit test ���α׷�
 * ��       �� : 
 * ��       �� : 
 **********************************************************************************/
import org.springframework.core.io.ClassPathResource;
import signgate.crypto.util.PKCS7Util;
import signgate.crypto.util.FileUtil;
import signgate.crypto.util.TimeUtil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

// PKCS#7 Enveloped Data ���� �� ���� ����
public class PKCS7EnvelopedTest
{
	public static void main(String[] args) throws Exception
	{
		try {
			String testData = "abc";

			String kmCertPath = SelfConfig.path + "kmCert.der";
			String kmPriPath =  SelfConfig.path + "kmPri.key";
			TimeUtil timeutil = new TimeUtil();

			PKCS7Util p7util = new PKCS7Util();

			System.out.println("********************************************************");
			System.out.println("** PKCS7Util TEST ");
			System.out.println("********************************************************");

			byte[] kmCert = FileUtil.readBytesFromFileName(kmCertPath);
			byte[] kmPri = FileUtil.readBytesFromFileName(kmPriPath);

			String enveloped = null;
			try {
				enveloped = p7util.genEnvelopedData(kmCert, testData.getBytes());
			} catch (Exception e) {
				System.out.println("인증서 초기화 : [" + p7util.getErrorMsg() + "]");
				return;
			}

			timeutil.check();
			System.out.println("PKCS#7 Enveloped Data ���� ��: \n" + enveloped);

			timeutil = new TimeUtil();
			PKCS7Util p7utilverify = new PKCS7Util();

			boolean bverify = false;
			bverify = p7utilverify.verify(enveloped, kmPri, SelfConfig.passwd);

			System.out.println("PKCS#7 Enveloped Data ������: " + bverify);

			if (!bverify)
				System.out.println(p7utilverify.getErrorMsg());

			String DecryptedData = new String(p7utilverify.getRecvData());

			System.out.println("data: " + DecryptedData);
			timeutil.check();
		} catch (IOException e) {
			System.out.println("IOE error PKCS");
		}
	}
}