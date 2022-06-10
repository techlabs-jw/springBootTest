package com.example.demo.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
public class articleForm {
    private String UserSignCert;
    private String UserSignValue;
    private String EncryptedSessionKeyForServer;
    private int EncryptedUserRandomNumber;
    private String EncryptedLoginPassword;
    private String EncryptedUserSSN;
    private String LoginID;
}

