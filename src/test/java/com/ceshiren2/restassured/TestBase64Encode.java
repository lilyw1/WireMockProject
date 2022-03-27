package com.ceshiren2.restassured;

import io.restassured.RestAssured;
import org.apache.commons.codec.binary.Base64;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;

import static io.restassured.RestAssured.given;
import static io.restassured.specification.ProxySpecification.host;

/**
 * @author wyl
 * @description Base64加密和解密，及post请求表单解密
 * @create 2022-03-25 23:28
 */
public class TestBase64Encode {
    @Test
    void testBase64(){
        //Base64加密过程
        //指定utf-8格式,避免出现乱码
        byte[] arr = "hogwarts".getBytes(StandardCharsets.UTF_8);
        String secretMsg = Base64.encodeBase64String(arr);
        System.out.println(secretMsg);//aG9nd2FydHM=

        //解密过程
        byte[] arr2 = Base64.decodeBase64(secretMsg);
        String decodeMsg = new String(arr2,StandardCharsets.UTF_8);
        System.out.println(decodeMsg);
    }

    //实际请求中的解密运用
    @Test
    void testHttpBinDecode(){
        //先加密下要传入的字符串
        byte[] data = "ceshiren".getBytes(StandardCharsets.UTF_8);
        String secretMsg = Base64.encodeBase64String(data);

        //指定代理
        RestAssured.proxy = host("127.0.0.1").withPort(7778);
        //忽略https校验
        RestAssured.useRelaxedHTTPSValidation();

        //post请求
        LinkedHashMap<String,String> responseForm = given()
                .formParam("msg",secretMsg)
        .when()
                .post("https://httpbin.ceshiren.com/post")
        .then()
                .extract().path("form");//取出传入的form表单数据
        System.out.println(responseForm);//{msg=Y2VzaGlyZW4=}

        //取出传入的加密字符串
        String arr = responseForm.get("msg");
        System.out.println(arr);

        //解密字符串
        byte[] decodeMsg = Base64.decodeBase64(arr);
        String secretData = new String(decodeMsg,StandardCharsets.UTF_8);
        System.out.println(secretData);

        //添加断言
        Assertions.assertEquals("ceshiren",secretData);

    }
}
