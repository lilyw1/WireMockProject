package com.ceshiren2.restassured;

import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.specification.ProxySpecification.host;

/**
 * @author wyl
 * @description 两种设置代理的方式
 * @create 2022-03-25 22:29
 */
public class TestProxy {
    @Test
    //设置局部代理
    void testProxy(){
        given()
                //.proxy(7778)
                .proxy("127.0.0.1",7778)
                .relaxedHTTPSValidation()
        .when()
                .get("https://httpbin.ceshiren.com/get")
        .then()
                .statusCode(200)
                .log().all();
    }
    @Test
        //设置全局代理
    void testProxy2(){
        RestAssured.proxy = host("localhost").withPort(7778);
        RestAssured.useRelaxedHTTPSValidation();
        given()
                .when()
                .get("https://httpbin.ceshiren.com/get")
                .then()
                .statusCode(200)
                .log().all();
    }
}
