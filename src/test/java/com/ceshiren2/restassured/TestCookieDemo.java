package com.ceshiren2.restassured;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

/**
 * @author wyl
 * @description 两种cookie设置测试
 * @create 2022-03-24 20:52
 */
public class TestCookieDemo {
    //通过header()添加cookie信息
    //通过cookie()添加
    @Test
    void testSetCookie() {

        // 配置本地代理，方便监听请求信息，抓包
//        RestAssured.proxy = host("localhost").withPort(7778);
//        // 忽略HTTPS校验
//        RestAssured.useRelaxedHTTPSValidation();

        given().log().all() // 打印完整请求信息
                //.header("Cookie","My-Cookie=Hogwarts")//通过header添加
                //.cookie("my-cookie","hogwarts")//通过cookie添加
                .cookies("my-cookie1","hogwarts1","my-cookie2","hogwarts2")
                .relaxedHTTPSValidation()  // 忽略HTTPS校验
                .when()
                .get("https://httpbin.ceshiren.com/get")  // 发送请求
                .then()
                .log().all()  // 打印完整响应信息
                .statusCode(200);  // 响应断言
    }
}

