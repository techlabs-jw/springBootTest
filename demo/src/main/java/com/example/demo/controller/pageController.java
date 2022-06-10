package com.example.demo.controller;

import com.example.demo.PKCS7RSAMutliSignTest;
import com.example.demo.PKCS7SignTest;
import com.example.demo.dto.Sign;
import com.example.demo.dto.articleForm;
import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Controller
public class pageController {
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
    public String sign(HttpServletResponse response,  @Validated @RequestBody Sign signData) throws Exception {
        Gson gson = new Gson();
        String signType = "";
        List<Map<String, String>> returnData = null;
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

            System.out.println(returnData);
        }

        return gson.toJson(returnData);
    }
}

