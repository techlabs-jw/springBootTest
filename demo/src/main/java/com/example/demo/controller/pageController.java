package com.example.demo.controller;

import com.example.demo.PKCS7RSAMutliSignTest;
import com.example.demo.PKCS7RSASignFileTest;
import com.example.demo.PKCS7SignTest;
import com.example.demo.dto.Sign;
import com.example.demo.model.fileModel;
import com.example.demo.fileService;
import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
public class pageController {
    @Autowired
    fileService fileService;
    @GetMapping(value = "/")
    public String home(Model model) throws Exception {
        model.addAttribute("name", "TestControllerModelData");
        return "index";
    }
//    @PostMapping(value = "check")
//    public String test (Model model) throws Exception {
//        System.out.println(model);
//        System.out.println("test");
//        return "/ews_check";
//    }
    @PostMapping(value = "/sign", consumes ={MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_OCTET_STREAM_VALUE})
    @ResponseBody
    //RequestBody-> ModelAttribute
    public String sign(HttpServletResponse response, HttpServletRequest req, @RequestPart(value = "file", required = false) MultipartFile file,
                                                                             @RequestPart(value = "data", required = false) Sign signDao) throws Exception {
        Gson gson = new Gson();
        String signType = "";
        String signKind = "";
        String signText = "";
        String memberIndex = "";
        DefaultResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource("classpath:static/doctor/cert");
        String serverPath = req.getSession().getServletContext().getRealPath("doctor/cert/");
        List<Map<String, String>> returnData = null;

        signType    = signDao.getSignType();
        signKind    = signDao.getSignKind();
        signText    = signDao.getSignData();
        memberIndex = signDao.getMemberIndex();

        System.out.println(signType);
        System.out.println(signKind);
        System.out.println(signText);
        System.out.println(memberIndex);
        System.out.println(file);

        String fileName = "doctor" + "_" + memberIndex + "_" + file.getOriginalFilename();
        File uploadPath = new File(serverPath, fileName);
        file.transferTo(uploadPath);

//        UUID uuid = UUID.randomUUID();
        //????????? ????????????
//        if(signType.equals("multli")) {
//            PKCS7RSAMutliSignTest multliSignClass = new PKCS7RSAMutliSignTest();
//            multliSignClass.MutiliSignTest(signText);
//        }
        //????????? ????????????
        if(signType.equals("single")) {
            if(signKind.equals("text")) {
                PKCS7SignTest signClass = new PKCS7SignTest();
                returnData = signClass.textSign(signText);
            }
            //?????? ????????????
            if(signKind.equals("file")) {
                PKCS7RSASignFileTest fileSignClass = new PKCS7RSASignFileTest();
                String uploadFilePath = serverPath + fileName;
                //String uploadFilePath = serverPath + "kmCert.der";
                returnData = fileSignClass.fileSign(uploadFilePath, fileName);

            }
        }
        //return "a";
        return gson.toJson(returnData);
    }
}

