package com.example.demo.controller;

import com.example.demo.PKCS7RSAMutliSignTest;
import com.example.demo.PKCS7SignTest;
import com.example.demo.dto.Sign;
import com.example.demo.dto.articleForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@Controller
public class pageController {
    @GetMapping(value = "/")
    public String home(Model model) throws Exception {
        model.addAttribute("name", "TestControllerModelData");
        return "Sample_Login";
    }
//    @PostMapping(value = "check")
//    public String test (Model model) throws Exception {
//        System.out.println(model);
//        System.out.println("test");
//        return "/ews_check";
//    }
    @PostMapping(value = "/sign")
    @ResponseBody
    public String sign(HttpServletResponse response,  @Validated @RequestBody Sign signData) throws Exception {
        String signType = "";
        String returnData = "";
        signType = signData.getSignType();
        //멀티사인테스트
        if(signType.equals("multli")) {
            PKCS7RSAMutliSignTest multliSignClass = new PKCS7RSAMutliSignTest();
            multliSignClass.MutiliSignTest(signData.getSignData());
        }
        //싱글사인테스트
        if(signType.equals("single")) {
            PKCS7SignTest signClass = new PKCS7SignTest();
            returnData = signClass.SignTest(signData.getSignData());
        }

        return returnData;
    }
}

