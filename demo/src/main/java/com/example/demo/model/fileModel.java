package com.example.demo.model;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class fileModel {
    private int index;
    private String mode;
    private String name;
    private String ext;
    private String originName;
    private String originDir;
    private String date;
    private String size;
}
