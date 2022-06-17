package com.example.demo;//package test.pki2048;
import signgate.crypto.util.FileUtil;
import signgate.crypto.util.TSPUtil;

public class TSATest {

	public static void main(String[] args) throws Exception
	{
		try
		{
			//String tsaServer = "211.35.96.51";
			//String tsaServer = "ftca.signgate.com";// 61.72.247.152
			String tsaServer = "ocsptest.signgate.com";// 61.72.247.221
			//String tsaServer = "tsa.signgate.com";// 211.35.96.51
			 
			//tsa���� ��Ʈ
			//int port = 4299;
			//int port = 4506;
			int port = 4700;			
			//�ѱ��������� TSA�� ��ϵ�
			// ���̵�
			//String id = "abc";
			//String id = "kica";
			//String id = "test";
			String id = "kepco2";
			// �н�����
			//String passwd = "abc";
			//String passwd = "kica$";
			//String passwd = "test";
			String passwd = "kepco145";
			TSPUtil tu = new TSPUtil(tsaServer, port, id, passwd);
			String signFile = FileUtil.readStringFromFileName("FilePath");
			tu.setTimeout(5000);
			//Ÿ�ӽ�������ū ��Ʈ���� ������
			String timeStampToken = tu.getTimeStamp("aa");
	
			if(timeStampToken == null)
			{
				//�����ð��� ���������ð��� �־��.
				System.out.println( "����������Ȯ�ν���: " + tu.getErrorMsg());
			}
			else
			{
				System.out.println( "����������Ȯ�μ���");
				
				// ���ڼ���
				System.out.println("���ڼ���: " + timeStampToken);
				// GeneralizedTime
				System.out.println("Generalized Time: " + tu.getGTime());				
				// LocalTime
				System.out.println("Local Time: " + tu.getLTime());
				
				System.out.println("Time: " + tu.getTime());	
				// SerialNumber
				System.out.println("SerialNumber: " + tu.getSerialNumber());
				
				TSPUtil tu1 = new TSPUtil(timeStampToken);
				System.out.println("TimeStampToken ���� ���: " + tu1.verifyTimeStampToken());
				
			}
		}catch(Exception ex) 
		{
			ex.getStackTrace();
		}
	}
}
