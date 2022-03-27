package com.ceshiren2.restassured;

import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.specification.ProxySpecification.host;

/**
 * @author wyl
 * @description 三种header设置测试
 * @create 2022-03-24 20:52
 */
public class TestHeaderDemo {
    @Test
    void testSetHeader() {

        // 配置本地代理，方便监听请求信息，抓包
        RestAssured.proxy = host("localhost").withPort(7778);
        // 忽略HTTPS校验
        RestAssured.useRelaxedHTTPSValidation();

        given().log().all()
                //.header("User-Agent", "hogwarts")  // 设置请求头
                //.header("User-Agent", "hogwarts1","hogwarts2")//设置多个请求头,一个key多个value
                .headers("User-Agent1", "hogwarts1","User-Agent2", "hogwarts2")//多个键值对
                .relaxedHTTPSValidation()  // 忽略HTTPS校验
                .when()
                .get("https://httpbin.ceshiren.com/get")  // 发送请求
                .then()
                .log().all()  // 打印完整响应信息
                .statusCode(200);  // 响应断言
    }
}

