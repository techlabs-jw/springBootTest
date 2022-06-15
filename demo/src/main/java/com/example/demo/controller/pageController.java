package com.example.demo.controller;

import com.example.demo.PKCS7RSAMutliSignTest;
import com.example.demo.PKCS7RSASignFileTest;
import com.example.demo.PKCS7SignTest;
import com.example.demo.dto.Sign;
import com.example.demo.model.fileModel;
import com.example.demo.fileService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

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
    @PostMapping(value = "/sign")
    @ResponseBody
    //RequestBody-> ModelAttribute
    public String sign(HttpServletResponse response,  @Validated @ModelAttribute Sign signDao) throws Exception {
        Gson gson = new Gson();
        String signType = "";
        String signKind = "";
        String signText = "";
        String memberIndex = "";

        List<Map<String, String>> returnData = null;
        
        signType    = signDao.getSignType();
        signKind    = signDao.getSignKind();
        signText    = signDao.getSignData();
        memberIndex = signDao.getMemberIndex();

        //텍스트 멀티사인
        if(signType.equals("multli")) {
            PKCS7RSAMutliSignTest multliSignClass = new PKCS7RSAMutliSignTest();
            multliSignClass.MutiliSignTest(signText);
        }
        //텍스트 싱글사인
        if(signType.equals("single")) {
            PKCS7SignTest signClass = new PKCS7SignTest();
            returnData = signClass.textSign(signText);

            //List<fileModel> fileDataList = fileService.getFile();

           // System.out.println(fileDataList);
            //파일 싱글사인
            if(signKind.equals("file")) {
                System.out.println("files");
//                PKCS7RSASignFileTest fileSignClass = new PKCS7RSASignFileTest();
//                returnData = fileSignClass.fileSign(signText,memberIndex);
            }


        }

        return gson.toJson(returnData);
    }
}

