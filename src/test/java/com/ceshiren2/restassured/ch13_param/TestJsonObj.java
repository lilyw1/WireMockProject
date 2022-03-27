package com.ceshiren2.restassured.ch13_param;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.given;

/**
 * @description Json格式传参解析
 * @author wyl
 * @create 2022-03-22 23:27
 */
public class TestJsonObj {
    //hashMap值传递post接口
    @Test
    void testJsonObj(){
        HashMap<String, String> jsonObj = new HashMap<String, String>();
        jsonObj.put("hello","hogwarts1");
        given()
                .contentType("application/json")
                .body(jsonObj)
                .log().headers()
                .log().body()
        .when()
                .post("https://httpbin.ceshiren.com/post")
        .then()
                //.log().all()
                .statusCode(200);
    }
}
