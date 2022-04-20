<!--
	Filename : signgate_common.jsp
-->

<%@ page import="signgate.crypto.util.*" %>

<%!

	public static final String CRLCacheDirectory = "D:\\SG_ToolKit\\CRL";
	//public static final String ServerCertPath = "D:\\SG_ToolKit\\cert\\pem\\";
	//public static final String ServerCertPath = "D:\\SG_ToolKit\\cert\\";
	//public static final String ServerCertPath = "D:\\SG_ToolKit\\cert\\2\\";
	public static final String ServerCertPath = "D:\\SG_ToolKit\\cert\\test\\";
	public static final String ServerSignCertFile = ServerCertPath + "signCert.der";	// 서버 전자서명용 인증서 파일명
	public static final String ServerSignKeyFile = ServerCertPath + "signPri.key";		// 서버 전자서명용 개인키 파일명
	public static final String ServerKmCertFile = ServerCertPath + "kmCert.der";		// 서버 암호화용 인증서 파일명
	public static final String ServerKmKeyFile = ServerCertPath + "kmPri.key";			// 서버 암호화용 개인키 파일명
	//public static final String ServerKeyPassword = "a123456A"; // 서버 인증서(개인키) 비밀번호
	//public static final String ServerKeyEncPasswordPath = ServerCertPath + "encPasswd";	// 암호화된 서버 인증서(개인키) 비밀번호
	public static final String ServerKeyEncPasswordPath = ServerCertPath + "EncPasswd";	// 암호화된 서
	// 대칭키 암호화 알고리즘 정보 설정
	public static final String ServerEncryptAlg = "SEED/CBC";		// SEED/CBC or ARIA/CBC


	// 허용할 인증서 정책 OID 리스트 (범용 인증서 OID 리스트)
	public static final String[] AllowedPolicyOIDs = {
		"1.2.410.200004.5.2.1.2",	//한국정보인증(개인)
		"1.2.410.200004.5.2.1.1",	//한국정보인증(법인)
		"1.2.410.200004.5.1.1.5",	//증권전산(개인)
		"1.2.410.200004.5.1.1.7",	//증권전산(법인)
		"1.2.410.200005.1.1.1",		//금융결제원(개인)
		"1.2.410.200005.1.1.5",		//금융결제원(법인)
		"1.2.410.200004.5.3.1.9",	//한국전산원(개인)
		"1.2.410.200004.5.3.1.2",	//한국전산원(법인)
		"1.2.410.200004.5.3.1.1",	//한국전산원(기관)
		"1.2.410.200004.5.4.1.1",	//전자인증(개인)
		"1.2.410.200004.5.4.1.2",	//전자인증(법인)
		"1.2.410.200012.1.1.1",		//한국무역정보통신(개인)
		"1.2.410.200012.1.1.3",		//한국무역정보통신(법인)
	};

	byte[] ServerSignCert = null;
	byte[] ServerSignKey = null;
	byte[] ServerKmCert = null;
	byte[] ServerKmKey = null;
	String ServerSignCertPem = "";
	String ServerKmCertPem = "";
	String ServerKeyEncPassword = "";
	


%>

<%
	response.setHeader("Cache-Control","no-cache,no-store");
	response.setHeader("Pragma","no-cache,no-store");

	try
		{
			// 서버의 암호화용 인증서 및 개인키 Read
			
			ServerSignCert = FileUtil.readBytesFromFileName(ServerSignCertFile);
			ServerSignKey = FileUtil.readBytesFromFileName(ServerSignKeyFile);
			ServerKmCert = FileUtil.readBytesFromFileName(ServerKmCertFile);
			ServerKmKey = FileUtil.readBytesFromFileName(ServerKmKeyFile);
			
			// 서버인증서를 PEM형태로 사용시 사용
			/*
			ServerSignCert = Base64Util.decode(FileUtil.readStringFromFileName(ServerSignCertFile));
			ServerSignKey = Base64Util.decode(FileUtil.readStringFromFileName(ServerSignKeyFile));
			ServerKmCert = Base64Util.decode(FileUtil.readStringFromFileName(ServerKmCertFile));
			ServerKmKey = Base64Util.decode(FileUtil.readStringFromFileName(ServerKmKeyFile));
			//ServerKmKey = FileUtil.readBytesFromFileName(ServerKmKeyFile);
			//String ServerKmKey = ServerCertPath + "kmPri.key";
			*/
			// PEM type 인증서로 변환
			ServerSignCertPem = CertUtil.derToPem( ServerSignCert );
			ServerKmCertPem = CertUtil.derToPem( ServerKmCert );

			// 암호화된 서버의 개인키 비밀번호 파일 Read
			ServerKeyEncPassword = FileUtil.readStringFromFileName(ServerKeyEncPasswordPath);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
%>