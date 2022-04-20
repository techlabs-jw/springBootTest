package com.example.demo;//package test.pki2048;

public class SGSecuKitVersion {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		System.out.println("SG SecuKit version: " + signgate.crypto.util.Version.getVersion());
		System.out.println("SG SecuKit lastUpdate Date: " + signgate.crypto.util.Version.getUpdateDate());
	}

}
