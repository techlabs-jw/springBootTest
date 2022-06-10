package com.example.demo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class urlTest {
    public static void main(String[] args) {
        String apikey = "xxxxxxx";
        String camp = "17";
        try {
            URL url = new URL("http://localshost:7777");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();


            con.setRequestMethod("POST");


            con.setInstanceFollowRedirects(true);

            String postData = "apikey" + apikey + "apif=ge" + "camp" + camp; // I need somthing like this
            con.setRequestProperty("Content-length", String.valueOf(postData.length()));

            con.setDoOutput(true);
            con.setDoInput(true);

            DataOutputStream output = new DataOutputStream(con.getOutputStream());
            output.writeBytes(postData);
            output.close();


            int code = con.getResponseCode(); // 200 = HTTP_OK
            System.out.println("Response    (Code):" + code);
            System.out.println("Response (Message):" + con.getResponseMessage());


            DataInputStream input = new DataInputStream(con.getInputStream());
            int c;
            StringBuilder resultBuf = new StringBuilder();
            while ((c = input.read()) != -1) {
                resultBuf.append((char) c);
            }
            input.close();
        } catch (Exception e) {

        }
    }
}
