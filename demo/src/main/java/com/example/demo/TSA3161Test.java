package com.example.demo;

import java.util.Arrays;
import signgate.crypto.util.Base64Util;
import signgate.crypto.util.MDUtil;
import signgate.crypto.util.TSPUtil;
import signgate.crypto.util.TimeUtil;

/**
 * ����Ȯ���ϴ� ����
 * 
 * @author �ѱ���������
 * @version 4.0.1, 2020-03-24
 */
public class TSA3161Test {
	public static void main(String[] args) {
		try {
			// ���̼��� �������
			// ���̼��� ����

			// Timestamp �� �� ���� �Է�
			String org = "Test_Data+1234562315633~!@#$%^&*";

			// ������ �׽�Ʈ ����
			String tsaURL = "http://catest.signgate.com:4700"; //����
			//String tsaURL = "http://tsa.signgate.com:5299"; //� 5299
			String id = "testtsa";  //���߼��� ID
			String passwd = "testtsa";   // ���߼��� PW
			// ����� ID / PW �� ����������� ��û
			
			TSPUtil tu = new TSPUtil(tsaURL, id, passwd);

			tu.setTimeout(5000);
			tu.setRFCType("RFC3161");
			tu.setHashAlgorithms("SHA-256");
			// Ÿ�ӽ�������ū ��Ʈ���� ������
			String timeStampToken = tu.getTimeStamp(org);

			if (timeStampToken == null) {
				// �����ð��� ���������ð��� �־��.
				System.out.println("����Ȯ�ν���: Timestamp token is null: " + tu.getErrorMsg());
			} else {
				System.out.println("����Ȯ�μ���");
				// Timestamp token value
				System.out.println("Timestamp Token: " + timeStampToken);
				// GeneralizedTime
				System.out.println("Generalized Time: " + tu.getGTime());
				// LocalTime
				System.out.println("�ѱ��ð� Time : " + TimeUtil.convertUTCtoLocalTime(tu.getGTime()));

				// SerialNumber
				System.out.println("SerialNumber : " + tu.getSerialNumber());

				// Timestamp token verification
				TSPUtil tu1 = new TSPUtil(timeStampToken);
				if (tu1.verifyTimeStampToken() == true) {
					System.out.println("TimeStampToken ���� ����");
				} else {
					System.out.println("TimeStampToken ���� ���� : " + tu1.getErrorMsg());
				}

				// TSA ��������
				MDUtil mu = new MDUtil("SHA-256");
				mu.update(org.getBytes());
				byte[] orgHash = mu.digest();
				System.out.println("���� Hash : " + Base64Util.encode(orgHash));

				byte[] tokenHash = tu1.getTSTInfoHashedBytes();

				if (tokenHash != null) {
					System.out.println("Token Hash : " + Base64Util.encode(tokenHash));
				} else {
					System.out.println("Token ���� ���� �ؽ����� �����ϴ�.");
					return;
				}

				if (Arrays.equals(orgHash, tokenHash)) {
					System.out.println("���� Ȯ�� ����");
				} else {
					System.out.println("������ ������ �Ǿ����ϴ�.");
				}
			}

		} catch (Exception ex) {
			ex.getStackTrace();
		}
	}
}