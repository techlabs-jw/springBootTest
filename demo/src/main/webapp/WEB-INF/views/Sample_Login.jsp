<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="signgate.crypto.util.*" %>
<%@ page import="java.io.IOException" %>
<%@ page import="org.springframework.core.io.DefaultResourceLoader" %>
<%@ page import="org.springframework.core.io.Resource" %>


<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=Edge" />
    <title>한국정보인증(주) SecuKit NX Sample - Login Sample</title>

    <!-- KICA SecuKit NXS -->
    <link rel="stylesheet" type="text/css" href="static/css/base.css" />
    <script type="text/javascript" src="static/js/jquery-1.8.2.min.js"></script>
    <script type="text/javascript" src="static/js/nx_config.js"></script>
    <script type="text/javascript" src="static/js/LoadSecukitnx.js"></script>
    <script type="text/javascript">
        window.onload = function () {
            // KICA WebUI append
            $('#KICA_SECUKITNXDIV_ID').append(KICA_SECUKITNXDIV);
            secunx_Loading();
        };

        function SecuKitNX_Ready(res) {
            if (res) {
                alert('SecuKitNX Ready');
            }
        }
    </script>
    <!-- //KICA SecuKit NXS -->
    <%
        String ServerKmCertPem = "";
        DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
        String path = null;
        Resource resource = resourceLoader.getResource("classpath:static/cert/kmCert.der");
        //path = resource.getURI().getPath();
        //서버인증서 가져오기(kmCert.der)
        try {
            byte[] ServerKmCert = FileUtil.readBytesFromFileName(resource.getURI().getPath());
             ServerKmCertPem = CertUtil.derToPem( ServerKmCert );
        } catch(IOException e) {
            System.out.println("IOE error jsp");

        }
    %>

    <script>
        // 타겟 미디어 비활성화 Set
        // HDD, USB, HSM, BIOHSM, USIM, EXTENSION
        //NXsetMediaDisable('BIOHSM'); // 지문보안토큰 비활성화

        // 로그인 정보
        var strDomainName = null;
        var strCertID = null;
        var strServerKmCert = null;
        var strUserSSN = null;
        var strLoginPassword = null;
        var strLoginID = null;

        // 로그인 로직 수행 후 생성된 값을 담는 변수
        var UserSignCert = null;
        var UserSignValue = null;
        var EncryptedSessionKeyForServer = null;
        var EncryptedUserRandomNumber = null;
        var EncryptedLoginPassword = null;
        var EncryptedUserSSN = null;

        // 샘플페이지 출력 데이터
        var UserOID = null;
        var UserSignCert = null;
        var UserKmCert = null;

        // 함수 호출 결과값 리턴
        function SecuKitNXS_RESULT(cmd, res) {
            // Error Check
            var Err = 999;
            try {
                // Error Code가 포함되었는지 확인
                Err = res.ERROR_CODE;
            } catch (e) {
                console.log(e);
            }

            if (Err === undefined) {
                var val = null;
                switch (cmd) {
                    // 인증서 로드 확인
                    case 'LoadCertificate':
                        val = res.isLoadedCert;
                        alert(val);
                        break;

                    // 세션키로 로그인 PWD 암호화
                    case 'LOGIN':
                        var algorithm = 'SEED/CBC/PADDING';     // 암호화 알고리즘
                        var keyName = strCertID;        // Key Name
                        var sourceString = strLoginPassword;    // 암호화 원문

                        var cmd = 'EncPwd.symmetricEncryptData';
                        var Data = {
                            'algorithm': algorithm,
                            'keyName': keyName,
                            'sourceString': sourceString
                        };
                        var param = JSON.stringify(Data);
                        secukitnxInterface.SecuKitNXS(cmd, param);
                        break;

                    // 세션키로 로그인 PWD 암호화 결과값 및 세션키로 SSN 암호화
                    case 'EncPwd':
                        EncryptedLoginPassword = res.symmetricEncryptData; // 세션키로 암호화 한 PWD 값

                        // 세션키로 SSN 암호화
                        var algorithm = 'SEED/CBC/PADDING';
                        var keyName = strCertID;
                        var sourceString = strUserSSN;

                        var cmd = 'EncSSN.symmetricEncryptData';
                        var Data = {
                            'algorithm': algorithm,
                            'keyName': keyName,
                            'sourceString': sourceString
                        };
                        var param = JSON.stringify(Data);
                        secukitnxInterface.SecuKitNXS(cmd, param);
                        break;

                    // 암호화 된 SSN값 및 인증서 정보 추출
                    case 'EncSSN':
                        EncryptedUserSSN = res.symmetricEncryptData;                             // 세션키로 암호화 한 SSN 값

                        var isViewVID = '1';													 // 0 : VID 추출 안함,  1 : VID 추출
                        var certID = certListInfo.getCertID();                                   // 선택된 인증서 ID
                        var cmd = 'GetCertInfo.viewCertInfomationWithVID';
                        var Data = {
                            'certType': 'signCert',
                            'certID': certID,
                            'isViewVID': isViewVID
                        };
                        var param = JSON.stringify(Data);
                        secukitnxInterface.SecuKitNXS(cmd, param);
                        break;

                    // 추출된 인증서 정보 및 VID값 암호화
                    case 'GetCertInfo':
                        UserSignCert = res.certPEM;             // SignCert 정보

                        UserOID = res.policy;                         // OID 정보
                        UserKmCert = res.encryptCertPEM;              // KmCert 정보
                        UserDN = res.userDN;                          // DN 정보

                        // 세션키로 SSN 암호화
                        var algorithm = 'SEED/CBC/PADDING';
                        var keyName = strCertID;
                        var sourceString = res.vidRandom;       // VID Random 값 (신원확인 정보)

                        var cmd = 'EncVID.symmetricEncryptData';
                        var Data = {
                            'algorithm': algorithm,
                            'keyName': keyName,
                            'sourceString': sourceString
                        };
                        var param = JSON.stringify(Data);
                        secukitnxInterface.SecuKitNXS(cmd, param);
                        break;

                    // 암호화 된 VID(신원확인 값) 및 서버 인증서로 대칭키 암호화에 사용된 키 RSA 암호화 추 추출
                    case 'EncVID':
                        EncryptedUserRandomNumber = res.symmetricEncryptData;

                        // 서버 인증서로 대칭키 암호화에 사용된 키 RSA 암호화 추 추출
                        var serverCert = strServerKmCert;       // 서버 인증서 정보
                        var rsaVersion = 'V15';                 // RSA 버전 (V15 / V21)
                        var keyName = strCertID;        // Key Name

                        var cmd = 'GetKeyIV.getSymmetricKeyIV';
                        var Data = {
                            'serverCert': removePEMHeader(removeCRLF(serverCert)),
                            'rsaVersion': rsaVersion,
                            'keyName': keyName
                        };
                        var param = JSON.stringify(Data);
                        secukitnxInterface.SecuKitNXS(cmd, param);
                        break;

                    case 'GetKeyIV':
                        EncryptedSessionKeyForServer = res.getSymmetricKeyIV;   // 서버인증서로 RSA 암호화 된 암호화 키 정보

                        // 전자서명 수행
                        var certType = 'SignCert';
                        var sourceString = strLoginPassword + strUserSSN + strLoginID; // 서명 원문 : loginPwd + loginSSN + loginID
                        var algorithm = 'SHA256';                                                          // 전자 서명에 사용할 서명 알고리즘 ('SHA1', 'SHA224', 'SHA256', 'SHA384', 'SHA512')
                        var certID = certListInfo.getCertID();                                             // 선택된 인증서 ID

                        var cmd = 'GenSign.generateSignatureData';
                        var Data = {
                            'certType': certType,
                            'sourceString': sourceString,
                            'algorithm': algorithm,
                            'certID': certID
                        };
                        var param = JSON.stringify(Data);
                        secukitnxInterface.SecuKitNXS(cmd, param);
                        break;

                    case 'GenSign':
                        UserSignValue = res.generateSignatureData; // 전자서명 값

                        // Set form
                        //do_submit();

                        // 샘플 페이지 출력
                        document.getElementById('encPasswd').value = EncryptedLoginPassword;
                        document.getElementById('encRvalue').value = EncryptedUserRandomNumber;
                        document.getElementById('encssn').value = EncryptedUserSSN;
                        document.getElementById('encSessionKey').value = EncryptedSessionKeyForServer;
                        document.getElementById('userSignValue').value = UserSignValue;
                        document.getElementById('userOID').value = UserOID;
                        document.getElementById('userDN').value = UserDN;
                        document.getElementById('userKmCert').value = UserKmCert;
                        document.getElementById('userSignCert').value = UserSignCert;

                        break;

                    default: break;
                }

            } else {

                if(res.ERROR_CODE === '338755584')
                {
                    alert("인증서 정보가 없습니다. 인증서를 선택 후 진행해 주시기 바랍니다.");

                }else{

                    // Error Message 출력
                    $('.nx-cert-select').hide(); $('#nx-pki-ui-wrapper').hide(); KICA_Error.init();
                    KICA_Error.setError(res.ERROR_CODE, res.ERROR_MESSAGE);
                    var errorMsg = KICA_Error.getError();
                    alert(errorMsg);
                }

            }
        }

        function clearCertInfo() {
            //메모리에 저장된 인증서 clear
            try {
                var certID = certListInfo.getCertID();
                NXinitCert(certID);
            } catch (e) { console.log(e); }
        }

        function check_form() {
            if (document.input_form.LoginID.value == "" || document.input_form.LoginID.value == null) {
                alert("ID를 입력하시오");
                return false;
            }
            if (document.input_form.UserSSN.value == "" || document.input_form.UserSSN.value == null) {
                alert("주민등록번호를 입력하시오");
                return false;
            }
            if (document.input_form.LoginPassword.value == "" || document.input_form.LoginPassword.value == null) {
                alert("비빌번호를 입력하시오");
                return false;
            }
            if (document.input_form.ServerKmCert.value == "" || document.input_form.ServerKmCert.value == null) {
                alert("서버 인증서 정보가 없습니다.");
                return false;
            }
            return true;
        }

        function do_login() {
            if (!check_form()) return;

            strDomainName = '@signgate.com';
            strCertID = document.input_form.LoginID.value + strDomainName;
            strCertID = strCertID;
            strServerKmCert = document.input_form.ServerKmCert.value;
            strUserSSN = document.input_form.UserSSN.value;
            strLoginPassword = document.input_form.LoginPassword.value;
            strLoginID = document.input_form.LoginID.value;

            //로직 구분
            processLogic.init();
            processLogic.setProcessLogic('LOGIN');

            //CertID 세팅
            certListInfo.init();
            certListInfo.setCertID(strCertID);

            // 인증서 선택창 호출
            NX_ShowDialog();
        }

        function do_submit() {
            // 서버 전송 Form
            document.submit_form.UserSignCert.value = UserSignCert;
            document.submit_form.UserSignValue.value = UserSignValue;
            document.submit_form.EncryptedSessionKeyForServer.value = EncryptedSessionKeyForServer;
            document.submit_form.EncryptedUserRandomNumber.value = EncryptedUserRandomNumber;
            document.submit_form.EncryptedLoginPassword. value = EncryptedLoginPassword;
            document.submit_form.EncryptedUserSSN.value = EncryptedUserSSN;
            document.submit_form.LoginID.value = strLoginID;


            document.submit_form.method = "post";
            document.submit_form.action = "${pageContext.request.contextPath}/check"
            document.submit_form.submit();
        }

        //인증서 선택창에 출력될 인증서 정책 설정
        function setCertOID() {
            // nx_config.js 전역변수
            NX_AnyPolicy = '';               //=> 'Y' 로 설정한 경우 POLICIES 값에 상관 없이 모든 인증서 정책 출력
            //   OID별로 출력하는 경우 해당 변수의 값은 '' 또는 NULL 로 세팅
            NX_POLICIES = $('#oid').val();   //=> 출력할 인증서 정책(OID)
            //   정책을 다중 설정하는 경우 '|'로 구분하여 입력
            NXsetCommonInfo();
        }

        //인증서 CertID로 로드되었는지 확인
        function isLoadCertificate() {
            var certID = certListInfo.getCertID();
            alert('CertID :' + certID);

            if(certID !== '')
            {
                var cmd = 'LoadCertificate.isLoadedCert';
                var Data = {
                    'ID': certID
                };
                var param = JSON.stringify(Data);
                secukitnxInterface.SecuKitNXS(cmd, param);
            }else{
                alert("선택된 인증서 정보가 없습니다.");
            }
        }

        function setIntegration() {
            var type = $('#setType option:selected').val();
            var vender = $('#vender option:selected').val();
            var downloadUrl = '';
            if (vender === 0004) {
                downloadUrl = 'http://www.mobileusim.com/download/install';
            }

            NXsetIntegration(type, vender, '', '', '', '', '', '', '', '', '');
            NXsetIntegrationIssue(type, vender, '', '', '', '', '', '', '', '', '');
        }

    </script>
</head>
<body>

<form name="submit_form">
    <input type="hidden" name="UserSignCert">
    <input type="hidden" name="UserSignValue">
    <input type="hidden" name="EncryptedSessionKeyForServer">
    <input type="hidden" name="EncryptedUserRandomNumber">
    <input type="hidden" name="EncryptedLoginPassword">
    <input type="hidden" name="EncryptedUserSSN">
    <input type="hidden" name="LoginID" value="aa">
</form>

<!-- 한국정보인증 WebUI DIV 영역 -->
<div id="KICA_SECUKITNXDIV_ID"></div>

<form name="input_form">
    <table frame='border' width="1100px" align="center" style="padding: 0 1px 0 0;">
        <tr>
            <td align="center" colspan="2">
                <strong>
                    <br />
                    <font size="4">인증서 기반 로그인 또는 사용자 등록(인증서 등록) 페이지 예제</font>
                </strong>
            </td>
        </tr>
        <tr>
            <td align="center" colspan="2">
                <br />
                <font size='2'>이 페이지는 사용자 정보를 등록하는 페이지나 로그인 정보를 입력하는 페이지에서 사용될 수 있는 샘플 코드를 보여주는 페이지입니다.</font><br />
                <font size="3" color="red">서버 인증서와 챌린지값을 받는 부분 수정이 필요합니다. : </font>
                <font size="3" color="red">"singgate_common.jsp"의 "CRLCacheDirectory" 및 "ServerCertPath" 경로 수정 필요.</font>
                <br />
            </td>
        </tr>
        <tr>
            <td>
                USIM Vender Setting :
                <select id="setType">
                    <option value="USIM" selected="selected">USIM</option>
                </select>
                <select id="vender">
                    <option value="0001">라온시큐어</option>
                    <option value="0002" selected="selected">드림시큐리티</option>
                    <option value="0004">수미온</option>
                </select>
                <input type="button" value="환경 설정" onclick="setIntegration();">
            </td>
        </tr>
        <!-- 인증서 선택창 OID별로 출력 START-->
        <tr>
            <td>&nbsp; OID : <input type="text" size="50" id="oid" value="1.2.410.200004.5.2.1.1" />&nbsp;&nbsp;<input type="button" value="OID 설정" onclick="setCertOID();" /></td>
            <td></td>
        </tr>
        <tr>
            <td>
                <font size="2" color="red">&nbsp;&nbsp;&nbsp; OID 설정을 여러개 설정하는 경우 '|'로 구분하여 입력</font><br />
                <font size="2" color="red">&nbsp;&nbsp;&nbsp; 1.2.410.200004.5.2.1.1|1.2.410.200004.5.2.1.2</font>
            </td>
            <td></td>
        </tr>
        <!-- 인증서 선택창 OID별로 출력 END-->
        <tr>
            <td style="padding: 5px 0 0 10px;" width="550">
                <input type="text" id="LoginID" name="LoginID" value="TestPerson" size='20'/>
                <font size='2'>&nbsp;&nbsp;&nbsp;사용자 로그인 ID
            </td>
            <td width="550" align='right' style="padding: 5px 10px 0 0;"><font size='2'>VID R값 &nbsp;<input type="text" id="encRvalue" name="encRvalue" size='60'></td>
        </tr>
        <tr>
            <td style="padding: 5px 10px 0 10px;">
                <input type="password" name="LoginPassword" value="ji83sf46" size='21'/><font size='2'>
                &nbsp;&nbsp;&nbsp;로그인 비밀번호
            </td>
            <td align='right' style="padding: 5px 10px 0 0;">
                <font size='2'>암호화 된 비밀번호 <input type="text" id="encPasswd" name="encPasswd" size='60'>
            </td>
        </tr>
        <tr>
            <td style="padding: 5px 0 0 10px;">
                <input type="text" name="UserSSN" value="1234561234563" size='20'/>
                <font size='2'>&nbsp;&nbsp;&nbsp;주민등록번호
            </td>
            <td align='right' style="padding: 5px 10px 0 0;">
                <font size='2'>암호화 된 주민번호 <input type="text" id="encssn" name="encssn" size='60'>
            </td>
        </tr>
        <tr>
            <td style="padding: 15px 15px 15px 10px;">
                <input type="button" value="로그인 정보생성" onclick="javascript: do_login()" />&nbsp;&nbsp;&nbsp;&nbsp;
                <input type="button" value="로그인 서버검증" onclick="do_submit();" />  &nbsp;&nbsp;&nbsp;&nbsp;
                <input type="button" style='width: auto;' value="인증서 정보 초기화" onclick=" clearCertInfo();" />
                <input type="button" value="인증서 로드 확인" onclick="isLoadCertificate();" />
            </td>
            <td align='right' style="padding: 5px 10px 0 0;"><font size='2'>사용자인증서정책 &nbsp;<input type='text' id="userOID" name='userOID' size='60'></td>
        </tr>
        <br />
        <tr>
            <td align='left' style="padding: 5px 0 5px 10px;" colspan='2'><font size='2'>사용자인증서 DN &nbsp;<input type='text' id="userDN" name='userDN' size="158"></td>
        </tr>
        <tr>
            <td align='left' style="padding: 5px 0 5px 10px;">
                <font size='2'>
                    사용자 암호화용 인증서 <br>
                    <textarea rows="8" cols="64" id="userKmCert" name='userKmCert'></textarea>
            </td>
            <td style="padding: 5px 0 5px 0;">
                <font size='2'>
                    사용자 서명용 인증서 <br>
                    <textarea rows="8" cols="64" id="userSignCert" name='userSignCert'></textarea>
            </td>
        </tr>
        <tr>
            <td align='left' style="padding: 5px 0 10px 10px;">
                <font size='2'>
                    전자서명 값 <br>
                    <textarea rows="5" cols="64" id="userSignValue" name='userSignValue'></textarea>
            </td>
            <td style="padding: 5px 0 10px 0;">
                <font size='2'>
                    암호화된 대칭키 값 <br>
                    <textarea rows="5" cols="64" id="encSessionKey" name='encSessionKey' '></font>
            </td>
            <br />
            <br />
        </tr>

        <!-- 테스트용 서버 인증서 정보 -->
        <!-- 대칭키를 RSA 알고리즘으로 암호화하기 위한 WAS 서버의 암호화용 인증서  -->
        <input type="hidden" id="ServerKmCert" name="ServerKmCert" value="<%=ServerKmCertPem%>" />


    </table>
</form>

</body>

</html>