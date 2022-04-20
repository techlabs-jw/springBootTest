package com.example.demo;//package test.pki2048;
/**********************************************************************************
 * ȭ �� �� �� : SEEDTest.java
 * �ֱ� ������ : 2003/11/07
 * �ֱ� ������ : �� �� ��
 * ���� �ۼ��� : �� �� ��
 * �� �� �� �� : Java signgate toolkit test ���α׷�
 * ��       �� : 
 * ��       �� : 
 **********************************************************************************/
import signgate.crypto.util.CipherUtil;
import signgate.crypto.util.RandomUtil;
import signgate.crypto.util.Base64Util;
import signgate.crypto.util.TimeUtil;

// SEED �Ϻ�ȣȭ ����
public class SEEDCipherTest
{
	public static void main(String[] args) throws Exception
	{		
		String testData = "1234567890";		
		
		System.out.println("");
		System.out.println("*************************************************");
		System.out.println("** SEED Cipher ");
		System.out.println("*************************************************");
		System.out.println("��  �� : [" + testData + "]");		
		System.out.println("��  �� ����: [" + testData.getBytes().length + "]");
		
		////////////// seedKey ���� ( ���� �ѹ� �����ؼ� Ȱ��) /////////////////////////
		
		byte[] seedKey = RandomUtil.genRand(16);
		byte[] iv = RandomUtil.genRand(16);
		TimeUtil timeutil = new TimeUtil();
///////////////////////////////////��ȣȭ////////////////////////////////////////////////////////////	
		CipherUtil enccipher = new CipherUtil("SEED/CBC/PKCS5");				
				
		if ( !enccipher.encryptInit( seedKey, iv ) )
		{
			System.out.println( enccipher.getErrorMsg() );
			return;
		}
		
		byte[] encrypted = enccipher.encryptUpdate( testData.getBytes());
		if ( encrypted == null )
		{
			System.out.println( enccipher.getErrorMsg() );
			return;
		}
		enccipher.encryptFinal();
		
		System.out.println("��ȣ�� : [" + Base64Util.encode(encrypted) + "]");
		timeutil.check();			
///////////////////////////////////��ȣȭ////////////////////////////////////////////////////////////
		
		timeutil = new TimeUtil();
		CipherUtil deccipher = new CipherUtil("SEED/CBC/PKCS5");
		
		if ( !deccipher.decryptInit( seedKey, iv ) )
	   {
			System.out.println("��ȣȭ�� ���� �ʱ�ȭ�� �����߽��ϴ�.");
			System.out.println( deccipher.getErrorMsg() );
			return;
		}
		byte[] decrypted = deccipher.decryptUpdate( encrypted );
		if ( decrypted == null )
		{
			System.out.println( deccipher.getErrorMsg() );
			return;
		}
		deccipher.decryptFinal();
		System.out.println("��ȣ�� : [" + new String(decrypted) + "]");
		timeutil.check();	
	}
}