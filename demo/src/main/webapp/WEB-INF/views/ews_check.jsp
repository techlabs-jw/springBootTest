
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="signgate.crypto.util.* "%>
<%@ page import="com.example.demo.dto.articleForm" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	
    <title>SecuKitNX Client Lgoin Test Page</title>

</head>
</script>

${ID}

<%--<%--%>

<%--	String strUserLoginID	=	request.getParameter( "LoginID" ) == null ? "" : new String( request.getParameter( "LoginID" ).getBytes( "8859_1" ), "utf-8" );--%>
<%--	String strUserSignCert	=	request.getParameter( "UserSignCert" ) == null ? "" : request.getParameter( "UserSignCert" );--%>
<%--	String strUserSignValue	=	request.getParameter( "UserSignValue" ) == null ? "" : request.getParameter( "UserSignValue" );--%>
<%--	String strEncryptedSessionKeyForServer	=	request.getParameter( "EncryptedSessionKeyForServer" ) == null ? "" : request.getParameter( "EncryptedSessionKeyForServer" );--%>
<%--	String strEncryptedUserSSN		=	request.getParameter( "EncryptedUserSSN" ) == null ? "" : request.getParameter( "EncryptedUserSSN" );--%>
<%--	String strEncryptedLoginPassword	=	request.getParameter( "EncryptedLoginPassword" ) == null ? "" : request.getParameter( "EncryptedLoginPassword" );--%>
<%--	String strEncryptedUserRandomNumber	=	request.getParameter( "EncryptedUserRandomNumber" ) == null ? "" : request.getParameter( "EncryptedUserRandomNumber" );--%>
<%--%>--%>
<%--&lt;%&ndash;	if ( strUserLoginID.equals("") )&ndash;%&gt;--%>
<%--&lt;%&ndash;	{&ndash;%&gt;--%>
<%--&lt;%&ndash;		%><script>alert( 'ID가 없습니다.' ); location.href = LogoutPageUrl;</script>&lt;%&ndash;%>--%>
<%--&lt;%&ndash;		return;&ndash;%&gt;--%>
<%--&lt;%&ndash;	}&ndash;%&gt;--%>

<%--&lt;%&ndash;	if ( strUserSignCert.equals("") )&ndash;%&gt;--%>
<%--&lt;%&ndash;	{&ndash;%&gt;--%>
<%--&lt;%&ndash;		%><script>alert( '인증서가 없습니다.' ); location.href = LogoutPageUrl;</script>&lt;%&ndash;%>--%>
<%--&lt;%&ndash;		return;&ndash;%&gt;--%>
<%--&lt;%&ndash;	}&ndash;%&gt;--%>
<%--&lt;%&ndash;	if ( strUserSignValue.equals("") )&ndash;%&gt;--%>
<%--&lt;%&ndash;	{&ndash;%&gt;--%>
<%--&lt;%&ndash;		%><script>alert( '전자서명이 없습니다.' ); location.href = LogoutPageUrl;</script>&lt;%&ndash;%>--%>
<%--&lt;%&ndash;		return;&ndash;%&gt;--%>
<%--&lt;%&ndash;	}&ndash;%&gt;--%>
<%--&lt;%&ndash;	if ( strEncryptedSessionKeyForServer.equals("") )&ndash;%&gt;--%>
<%--&lt;%&ndash;	{&ndash;%&gt;--%>
<%--&lt;%&ndash;		%><script>alert( '대칭키가 없습니다.' ); location.href = LogoutPageUrl;</script>&lt;%&ndash;%>--%>
<%--&lt;%&ndash;		return;&ndash;%&gt;--%>
<%--&lt;%&ndash;	}&ndash;%&gt;--%>
<%--&lt;%&ndash;	if ( strEncryptedUserSSN.equals("") )&ndash;%&gt;--%>
<%--&lt;%&ndash;	{&ndash;%&gt;--%>
<%--&lt;%&ndash;		%><script>alert( '주민등록번호 및 사업자등록번호가 없습니다.' ); location.href = LogoutPageUrl;</script>&lt;%&ndash;%>--%>
<%--&lt;%&ndash;		return;&ndash;%&gt;--%>
<%--&lt;%&ndash;	}&ndash;%&gt;--%>
<%--&lt;%&ndash;	if ( strEncryptedLoginPassword.equals("") )&ndash;%&gt;--%>
<%--&lt;%&ndash;	{&ndash;%&gt;--%>
<%--&lt;%&ndash;		%><script>alert( '비밀번호가 없습니다.' ); location.href = LogoutPageUrl;</script>&lt;%&ndash;%>--%>
<%--&lt;%&ndash;		return;&ndash;%&gt;--%>
<%--&lt;%&ndash;	}&ndash;%&gt;--%>
<%--&lt;%&ndash;	if ( strEncryptedUserRandomNumber.equals("") )&ndash;%&gt;--%>
<%--&lt;%&ndash;	{&ndash;%&gt;--%>
<%--&lt;%&ndash;		%><script>alert( '신원확인정보가 없습니다.' ); location.href = LogoutPageUrl;</script>&lt;%&ndash;%>--%>
<%--&lt;%&ndash;		return;&ndash;%&gt;--%>
<%--&lt;%&ndash;	}&ndash;%&gt;--%>

<%--<%--%>

<%--	// 허용할 인증서 정책 OID 리스트 (범용 인증서 OID 리스트)--%>
<%--	String[] AllowedPolicyOIDs = {--%>
<%--		"1.2.410.200004.5.2.1.2",	//한국정보인증(개인)--%>
<%--		"1.2.410.200004.5.2.1.1",	//한국정보인증(법인)--%>
<%--		"1.2.410.200004.5.1.1.5",	//증권전산(개인)--%>
<%--		"1.2.410.200004.5.1.1.7",	//증권전산(법인)--%>
<%--		"1.2.410.200005.1.1.1",		//금융결제원(개인)--%>
<%--		"1.2.410.200005.1.1.5",		//금융결제원(법인)--%>
<%--		"1.2.410.200004.5.3.1.9",	//한국전산원(개인)--%>
<%--		"1.2.410.200004.5.3.1.2",	//한국전산원(법인)--%>
<%--		"1.2.410.200004.5.3.1.1",	//한국전산원(기관)--%>
<%--		"1.2.410.200004.5.4.1.1",	//전자인증(개인)--%>
<%--		"1.2.410.200004.5.4.1.2",	//전자인증(법인)--%>
<%--		"1.2.410.200012.1.1.1",		//한국무역정보통신(개인)--%>
<%--		"1.2.410.200012.1.1.3",		//한국무역정보통신(법인)--%>
<%--	};--%>

<%--	CipherUtil cipher = new CipherUtil();--%>
<%--	byte[] UserRandomNumber = null;--%>
<%--	byte[] LoginPassword = null;--%>
<%--	byte[] UserSSN = null;--%>
<%--	byte[] SessionKeyForServer = null;--%>

<%--	try--%>
<%--	{--%>
<%--		String ServerCertPath = "D:/tomcat7/webapps/ROOT/SecuKitNXS/WebSample/cert/";--%>
<%--		byte[] ServerKmKey = FileUtil.readBytesFromFileName(ServerCertPath + "kmPri.key");--%>

<%--		SessionKeyForServer = cipher.decryptRSA( ServerKmKey, "signgate1!", strEncryptedSessionKeyForServer );--%>
<%--		//SessionKeyForServer = CipherUtil.decryptEncPassRSA( ServerKmKey, ServerKeyEncPassword, strEncryptedSessionKeyForServer );--%>
<%--		System.out.println("ServerKmKey : " + ServerKmKey);--%>
<%--		System.out.println("strEncryptedSessionKeyForServer : " + strEncryptedSessionKeyForServer);--%>
<%--		System.out.println("SessionKeyForServer : " + SessionKeyForServer);--%>

<%--		if ( SessionKeyForServer == null )--%>
<%--		{--%>
<%--			%><script>alert('대칭키 복호화에 실패하였습니다.'); //location.href = LogoutPageUrl;</script><%--%>
<%--			return;--%>
<%--		}--%>
<%--	}--%>
<%--	catch(Exception e)--%>
<%--	{--%>
<%--		%><script>alert('<%=e.toString()%>'); //location.href = LogoutPageUrl;</script><%--%>
<%--		return;--%>
<%--	}--%>

<%--	// WAS 서버의 암호화용 개인키로 복호화한 대칭키를 사용하여 SEED 알고리즘으로 암호화된 데이터를 복호화한다.--%>
<%--	try--%>
<%--	{--%>
<%--		cipher.decryptInit( SessionKeyForServer );--%>
<%--		UserRandomNumber	= cipher.decryptUpdate( Base64Util.decode( strEncryptedUserRandomNumber ));--%>
<%--		LoginPassword	= cipher.decryptUpdate( Base64Util.decode( strEncryptedLoginPassword ));--%>
<%--		UserSSN 		= cipher.decryptUpdate( Base64Util.decode( strEncryptedUserSSN ));--%>
<%--		cipher.decryptFinal();--%>

<%--		if ( UserRandomNumber == null || LoginPassword == null || UserSSN == null )--%>
<%--		{--%>
<%--			%><script>alert('<%=cipher.getErrorMsg()%>'); location.href = LogoutPageUrl;</script><%--%>
<%--			return;--%>
<%--		}--%>
<%--	}--%>
<%--	catch(Exception e)--%>
<%--	{--%>
<%--		%><script>alert('<%=cipher.getErrorMsg()%>'); location.href = LogoutPageUrl;</script><%--%>
<%--		return;--%>
<%--	}--%>

<%--	String strUserRandomNumber = new String( UserRandomNumber );--%>
<%--	String strUserSSN 		= new String( UserSSN );--%>
<%--	String strLoginPassword 	= new String( LoginPassword );--%>

<%--	SignUtil sign = new SignUtil();--%>
<%--	String strOriginalMessage =strLoginPassword + strUserSSN + strUserLoginID;--%>
<%--	//String strOriginalMessage = "1234";--%>

<%--	--%>
<%--	try--%>
<%--	{--%>
<%--		sign.verifyInit( strUserSignCert.getBytes() );--%>
<%--		//sign.verifyInit( CertUtil.pemToDer(strUserSignCert));--%>
<%--		sign.verifyUpdate( strOriginalMessage.getBytes() );--%>
<%--		--%>
<%--		if ( !sign.verifyFinal( Base64Util.decode( strUserSignValue ) ))--%>
<%--		{--%>
<%--			%><script>alert('<%=sign.getErrorMsg()%>'); location.href = LogoutPageUrl;</script><%--%>
<%--			return;--%>
<%--		}--%>
<%--	}--%>
<%--	catch (Exception e) --%>
<%--	{--%>
<%--		%><script>alert('<%=sign.getErrorMsg()%>'); location.href = LogoutPageUrl;</script><%--%>
<%--		return;--%>
<%--	}--%>
<%--	--%>

<%--	// 사용자의 전자서명용 인증서에서 각종 정보를 구한다.--%>
<%--	CertUtil cert = null;--%>
<%--	String strSubjectDn = null;--%>
<%--	String strIssuerDn = null;--%>
<%--	String strSerialNumber = null;--%>
<%--	String strNotBefore = null;--%>
<%--	String strNotAfter = null;--%>
<%--	int nRemainDays = 0;--%>
<%--	String strPolicyOid = null;--%>
<%--	try--%>
<%--	{--%>
<%--		cert = new CertUtil( strUserSignCert.getBytes() );--%>
<%--		strSubjectDn = cert.getSubjectDN();--%>
<%--		strIssuerDn = cert.getIssuerDN();--%>
<%--		strSerialNumber = cert.getSerialNumber();--%>
<%--		strNotBefore = cert.getNotBefore();--%>
<%--		strNotAfter = cert.getNotAfter();--%>
<%--		nRemainDays = cert.getRemainDay();--%>
<%--		strPolicyOid = cert.getPolicyOid();--%>

<%--	}--%>
<%--	catch (Exception e) --%>
<%--	{--%>
<%--		%><script>alert('올바른 인증서가 아닙니다.'); location.href = LogoutPageUrl;</script><%--%>
<%--		return;--%>
<%--	}--%>

<%--	// 사용자의 인증서가 서비스에 사용할 수 있는 등급의 인증서인지 검사한다.--%>
<%--	// 예) 특수 목적용 인증스는 일반적인 서비스에 사용할 수 없다. 법인용 서비스를 이용하기 위해서 개인용 인증서를 사용할 수는 없다.--%>
<%--	if ( !cert.isValidPolicyOid( AllowedPolicyOIDs ) )--%>
<%--	{--%>
<%--		%><script>alert('허용되지 않는 정책의 인증서입니다'); location.href = LogoutPageUrl;</script><%--%>
<%--		return;--%>
<%--	}--%>
<%--	--%>

<%--	if ( !cert.isValidUser( strUserSSN, strUserRandomNumber) )--%>
<%--	{--%>
<%--		%><script>alert('사용자 본인확인 검사에 실패하였습니다'); location.href = LogoutPageUrl;</script><%--%>
<%--		return;--%>
<%--	}--%>
<%--	// 유효성 검증 체크 해제 - 실제 운용시에는 유효성 검사 주석 제거해야함--%>
<%--	try --%>
<%--	{--%>
<%--		if ( !cert.isValid( true, "D:/tomcat7/webapps/ROOT/SecuKitNXS/WebSample/cert/crl" ) )--%>
<%--		{--%>
<%--			%><script>alert('인증서 유효성 검사 실패 : ' + '<%=cert.getErrorMsg()%>');--%>
<%--				location.href = LogoutPageUrl;</script><%--%>
<%--			return;--%>
<%--		}--%>
<%--	} --%>
<%--	catch (Exception e)--%>
<%--	 {--%>
<%--	 	%><script>alert('인증서 유효성 검사 실패 : ' + '<%=cert.getErrorMsg()%>');--%>
<%--				location.href = LogoutPageUrl;</script><%--%>
<%--		return;--%>
<%--	}--%>
<%--	--%>
<%--	session.setAttribute( "LOGINID", strUserLoginID );--%>
<%--	session.setAttribute( "SESSIONKEY", SessionKeyForServer );--%>
<%--	session.setAttribute( "CERT_SUBJECT_DN", strSubjectDn );--%>
<%--	session.setAttribute( "SIGN_CERT_SERIAL", strSerialNumber );--%>
<%--%>--%>

<%--<center> <h1>SecuKitNX Client Login Test</h1> </center>--%>
<%--<table frame='border' width="1100px" align="center"'>--%>
<%--	<tr>--%>
<%--		<td align ="center" style='padding: 20px 0 20px 0;'>--%>
<%--			이 페이지는 사용자 정보 등록 페이지 또는 로그인 데이터 검사 페이지에서 사용될 수 있는 샘플 코드를 보여주는 페이지 입니다.--%>
<%--		</td>--%>
<%--	</tr>--%>
<%--	<tr>--%>
<%--		<td style='padding: 0 0 5px 15px;'><font size='2'>암호화된 주민등록번호/사업자등록번호: <strong><%= strEncryptedUserSSN %></strong></td>--%>
<%--	</tr>--%>
<%--	<tr>--%>
<%--		<td style='padding: 0 0 5px 15px;'><font size='2'>암호화된 로그인 비밀번호 : <strong><%= strEncryptedLoginPassword %></strong></td>--%>
<%--	</tr>--%>
<%--	<tr>--%>
<%--		<td style='padding: 0 0 5px 15px;'><font size='2'>복호화된 주민등록번호/사업자등록번호 :<strong> <%= strUserSSN %></strong></td>--%>
<%--	</tr>--%>
<%--	<tr>--%>
<%--		<td style='padding: 0 0 5px 15px;'><font size='2'>복호화된 로그인 비밀번호 : <strong><%= strLoginPassword %></strong></td>--%>
<%--	</tr>--%>
<%--	<tr>--%>
<%--		<td style='padding: 0 0 5px 15px;'><font size='2'>복호화된 인증서 랜덤값  : <strong><%= strUserRandomNumber%></strong></td>--%>
<%--	</tr>--%>
<%--	<tr>--%>
<%--		<td style='padding: 0 0 5px 15px;'><font size='2'>전자서명 원문 데이터 :<strong> <%= strOriginalMessage %></strong></td>--%>
<%--	</tr>--%>
<%--	<tr>--%>
<%--		<td style='padding: 0 0 5px 15px;'><font size='2'>사용자 전자서명용 인증서의 소유자 식별명칭: <strong><%= strSubjectDn %></strong></td>--%>
<%--	</tr>--%>
<%--	<tr>--%>
<%--		<td style='padding: 0 0 5px 15px;'><font size='2'>사용자의 전자서명용 인증서의 발급자 식별명칭:  <strong><%= strIssuerDn %></strong></td>--%>
<%--	</tr>--%>
<%--	<tr>--%>
<%--		<td style='padding: 0 0 5px 15px;'><font size='2'>사용자의 전자서명용 인증서의 일련번호:  <strong><%= strSerialNumber %></strong></td>--%>
<%--	</tr>--%>
<%--	<tr>--%>
<%--		<td style='padding: 0 0 5px 15px;'><font size='2'>사용자의 전자서명용 인증서의 유효기간 시작:  <strong><%= strNotBefore %></strong></td>--%>
<%--	</tr>--%>
<%--	<tr>--%>
<%--		<td style='padding: 0 0 5px 15px;'><font size='2'>사용자의 전자서명용 인증서의 유효기간 만료: <strong><%= strNotAfter %></strong></td>--%>
<%--	</tr>--%>
<%--	<tr>--%>
<%--		<td style='padding: 0 0 5px 15px;'><font size='2'>사용자의 전자서명용 인증서의 유효기간의 남아있는 일 수: <strong><%= nRemainDays %></strong></td>--%>
<%--	</tr>--%>
<%--	<tr>--%>
<%--		<td style='padding: 0 0 5px 15px;'><font size='2'>사용자의 전자서명용 인증서의 정책 OID: <strong><%= strPolicyOid %></strong></td>--%>
<%--	</tr>--%>
<%--	<tr>--%>
<%--		<td style='padding: 0 0 5px 15px;'><font size='2'>전자서명 검증결과 :  <strong> true</strong></td>--%>
<%--	</tr>--%>
<%--	<tr>--%>
<%--		<td style='padding: 0 0 5px 15px;'><font size='2'>인증서 유효성 검증결과 : <strong> true</strong> </td>--%>
<%--	</tr>--%>
<%--	<tr>--%>
<%--		<td style='padding: 0 0 5px 15px;'><font size='2'>인증서 본인확인 검증결과 : <strong> true</strong> </td>--%>
<%--	</tr>--%>
<%--	<tr>--%>
<%--		<td style='padding: 0 0 15px 15px;'><font size='2'>인증서 정책 검증결과:  <strong> true</strong> </td>--%>
<%--	</tr>--%>
<%--	<tr>--%>
<%--		<td style='padding: 0 0 5px 15px;'>[ <strong><%=session.getAttribute( "LOGINID" )%> </strong>]님이 로그인하였습니다. </td>--%>
<%--	</tr>--%>
<%--	<tr>--%>
<%--		<td style='padding: 0 0 15px 15px;'><a href= './Sample_Login.jsp';>로그아웃</a> </td>--%>
<%--	</tr>--%>
<%--</table>--%>

</body>
</html>