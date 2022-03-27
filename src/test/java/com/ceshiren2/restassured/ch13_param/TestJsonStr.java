package com.ceshiren2.restassured.ch13_param;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

/**
 * @description Json字符串格式传参解析
 * @author wyl
 * @create 2022-03-22 23:27
 */
public class TestJsonStr {
    //Json字符串传递post接口
    @Test
    void testJsonStr(){
        String jsonStr = "{\"hello\":\"hogwarts\"}";
        given()
                .contentType("application/json")//声明传参类型
                .body(jsonStr)
                .log().headers()
                .log().body()
        .when()
                .post("https://httpbin.ceshiren.com/post")
        .then()
                //.log().all()
                .statusCode(200);
    }
}
