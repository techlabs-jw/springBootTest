package com.example.demo;//package test.pki2048;

import java.io.IOException;

import signgate.crypto.util.Base64Util;
import signgate.crypto.util.CipherUtil;
import signgate.crypto.util.FileUtil;
import signgate.crypto.util.InvalidBase64Exception;

public class RSACipherTest {

	public static void main(String[] args) throws IOException, InvalidBase64Exception{
				
		String ServerKmCertFile = SelfConfig.path + "kmCert.der";
		String ServerKmKeyFile =  SelfConfig.path + "kmPri.key";
		String data = "sg";
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
	}
}
